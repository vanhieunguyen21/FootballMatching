package com.svmc.footballMatching.ui.team.teamHome;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;

import com.svmc.footballMatching.R;
import com.svmc.footballMatching.databinding.FragmentTeamHomeBinding;

public class TeamHomeFragment extends Fragment {
    private FragmentTeamHomeBinding binding;
    private TeamHomeViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTeamHomeBinding.inflate(inflater);
        binding.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this).get(TeamHomeViewModel.class);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view.getContext());
        observeLiveData(view.getContext());
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initComponents(Context context) {
        // ViewPager adapter and TabLayout setup
        TeamHomeViewPagerAdapter teamHomeViewPagerAdapter = new TeamHomeViewPagerAdapter(getParentFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        binding.viewPager.setAdapter(teamHomeViewPagerAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
        // Disable viewpager scrolling
        binding.viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
    }

    private void observeLiveData(Context context) {

    }

    public void addFragment(Fragment fragment) {
        getParentFragmentManager()
                .beginTransaction()
                .add(R.id.container, fragment, null)
                .addToBackStack(null)
                .commit();
    }
}
