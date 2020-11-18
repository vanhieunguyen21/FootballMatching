package com.svmc.footballMatching.ui.team.teamHome;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;

import com.svmc.footballMatching.R;
import com.svmc.footballMatching.data.enumeration.LoadDataState;
import com.svmc.footballMatching.data.session.CurrentTeam;
import com.svmc.footballMatching.databinding.FragmentLeaderTeamHomeBinding;
import com.svmc.footballMatching.ui.MainActivity;
import com.svmc.footballMatching.ui.team.matchHistory.MatchHistoryFragment;

public class LeaderTeamHomeFragment extends Fragment {
    private final String TAG = "LeaderTeamHomeFragment";
    private FragmentLeaderTeamHomeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        binding = FragmentLeaderTeamHomeBinding.inflate(inflater);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated: ");
        super.onViewCreated(view, savedInstanceState);
        initComponents(view.getContext());
        observeLiveData(view.getContext());
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initComponents(final Context context) {
        // Set up action bar
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_back_white_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detach();
            }
        });

        binding.matchHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.addFragment(new MatchHistoryFragment(), true, null, null);
            }
        });
    }

    private void observeLiveData(Context context) {
        CurrentTeam.getInstance().getTeamLoadStateLiveData().observe(getViewLifecycleOwner(), new Observer<LoadDataState>() {
            @Override
            public void onChanged(LoadDataState loadDataState) {
                if (loadDataState == null) return;
                if (loadDataState == LoadDataState.LOADED) {
                    binding.loadingProgressBar.setVisibility(View.GONE);
                    LeaderTeamHomeViewPagerAdapter leaderTeamHomeViewPagerAdapter =
                            new LeaderTeamHomeViewPagerAdapter(getChildFragmentManager(),
                                    FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
                    binding.viewPager.setOffscreenPageLimit(2);
                    binding.viewPager.setAdapter(leaderTeamHomeViewPagerAdapter);
                    binding.tabLayout.setupWithViewPager(binding.viewPager);
                }
            }
        });
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
