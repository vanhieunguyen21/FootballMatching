package com.svmc.footballMatching.ui.team.matchHistory;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;

import com.svmc.footballMatching.R;
import com.svmc.footballMatching.data.enumeration.LoadDataState;
import com.svmc.footballMatching.data.session.CurrentTeam;
import com.svmc.footballMatching.databinding.FragmentTeamMatchHistoryBinding;

public class MatchHistoryFragment extends Fragment {
    private FragmentTeamMatchHistoryBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTeamMatchHistoryBinding.inflate(inflater);
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

        // Set up view pager
        final MatchHistoryViewPagerAdapter matchHistoryViewPagerAdapter =
                new MatchHistoryViewPagerAdapter(getChildFragmentManager(),
                        FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        binding.viewPager.setOffscreenPageLimit(2);
        binding.viewPager.setAdapter(matchHistoryViewPagerAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        binding.teamChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.teamChip.setChecked(true);
                binding.playerChip.setChecked(false);
                matchHistoryViewPagerAdapter.setMode(0);
            }
        });
        binding.playerChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.teamChip.setChecked(false);
                binding.playerChip.setChecked(true);
                matchHistoryViewPagerAdapter.setMode(1);
            }
        });
    }

    private void observeLiveData(Context context) {
        CurrentTeam.getInstance().getTeamLoadStateLiveData().observe(getViewLifecycleOwner(), new Observer<LoadDataState>() {
            @Override
            public void onChanged(LoadDataState loadDataState) {
                if (loadDataState == null) return;
                if (loadDataState == LoadDataState.LOADING)
                    binding.loadingLayout.setVisibility(View.VISIBLE);
                if (loadDataState == LoadDataState.LOADED)
                    binding.loadingLayout.setVisibility(View.GONE);
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
}
