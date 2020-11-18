package com.svmc.footballMatching.ui.team.teamHome;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.svmc.footballMatching.R;
import com.svmc.footballMatching.data.enumeration.LoadDataState;
import com.svmc.footballMatching.data.model.Team;
import com.svmc.footballMatching.data.session.CurrentTeam;
import com.svmc.footballMatching.databinding.FragmentMemberTeamHomeBinding;
import com.svmc.footballMatching.ui.team.teamProfile.TeamProfileFragment;

public class MemberTeamHomeFragment extends Fragment {
    private FragmentMemberTeamHomeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMemberTeamHomeBinding.inflate(inflater);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initComponents(view.getContext());
        observeLiveData(view.getContext());
    }

    private void initComponents(Context context) {
        // Set up action bar
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_back_white_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detach();
            }
        });
    }

    private void observeLiveData(Context context) {
        CurrentTeam.getInstance().getTeamLoadStateLiveData().observe(getViewLifecycleOwner(), new Observer<LoadDataState>() {
            @Override
            public void onChanged(LoadDataState loadDataState) {
                if (loadDataState == LoadDataState.LOADED) {
                    binding.loadingProgressBar.setVisibility(View.GONE);
                    replaceTeamProfileFragment(CurrentTeam.getInstance().getMyTeamLiveData().getValue());
                }
            }
        });
    }

    private void replaceTeamProfileFragment(Team team) {
        Bundle args = new Bundle();
        args.putString("mode", "member");
        args.putSerializable("team", team);
        TeamProfileFragment teamProfileFragment = new TeamProfileFragment();
        teamProfileFragment.setArguments(args);
        getParentFragmentManager()
                .beginTransaction()
                .add(R.id.content_container, new TeamProfileFragment(), null)
                .commit();
    }

    private void detach() {
        getParentFragmentManager().popBackStack();
        getParentFragmentManager()
                .beginTransaction()
                .detach(this)
                .commit();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        TeamHomeFragment teamHomeFragment = (TeamHomeFragment) getParentFragmentManager().findFragmentByTag("teamHome");
        getParentFragmentManager()
                .beginTransaction()
                .detach(teamHomeFragment);
    }
}
