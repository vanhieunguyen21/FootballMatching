package com.svmc.footballMatching.ui.team.teamProfile;

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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.svmc.footballMatching.R;
import com.svmc.footballMatching.data.model.Team;
import com.svmc.footballMatching.data.session.CurrentTeam;
import com.svmc.footballMatching.databinding.FragmentTeamProfileBinding;
import com.svmc.footballMatching.ui.team.teamHome.TeamHomeFragment;

public class TeamProfileFragment extends Fragment {
    private FragmentTeamProfileBinding binding;
    private TeamProfileViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTeamProfileBinding.inflate(inflater);
        binding.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this).get(TeamProfileViewModel.class);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initComponents(view.getContext());
        observeLiveData(view.getContext());
    }

    private void initComponents(Context context) {
        // TODO: photos

        // Team members
        TeamMemberRecyclerViewAdapter teamMemberRecyclerViewAdapter = viewModel.getTeamMemberRecyclerViewAdapter();
        binding.teamMembersRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        binding.teamMembersRecyclerView.setAdapter(teamMemberRecyclerViewAdapter);

        binding.editIntroductionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getParentFragmentManager()
//                        .beginTransaction()
//                        .add(R.id.container, new EditTeamIntroductionFragment(), null)
//                        .addToBackStack(null)
//                        .commit();
                TeamHomeFragment teamHomeFragment = (TeamHomeFragment) getParentFragmentManager().findFragmentByTag("teamHome");
                teamHomeFragment.addFragment(new EditTeamIntroductionFragment());
            }
        });
    }

    private void observeLiveData(Context context) {

    }

    private void detach() {
        getParentFragmentManager().popBackStack();
        getParentFragmentManager()
                .beginTransaction()
                .detach(this)
                .commit();
    }
}
