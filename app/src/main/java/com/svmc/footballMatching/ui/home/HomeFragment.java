package com.svmc.footballMatching.ui.home;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.svmc.footballMatching.R;
import com.svmc.footballMatching.data.session.Session;
import com.svmc.footballMatching.databinding.FragmentHomeBinding;
import com.svmc.footballMatching.ui.personalProfile.PlayerPersonalProfileFragment;
import com.svmc.footballMatching.ui.team.teamHome.TeamHomeFragment;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view.getContext());
    }

    private void initComponents(Context context) {
        inflateMenu();

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceWithTeamHomeFragment();
            }
        });
    }

    private void inflateMenu() {
        binding.toolbar.inflateMenu(R.menu.main);
        binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.profile:
                        addProfileFragment();
                        break;
                    case R.id.logout:
                        Session.getInstance().logout();
                        break;
                }
                return true;
            }
        });
    }

    private void addProfileFragment() {
        getParentFragmentManager()
                .beginTransaction()
                .add(R.id.container, new PlayerPersonalProfileFragment(), null)
                .addToBackStack(null)
                .commit();
    }

    private void replaceWithTeamHomeFragment() {
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new TeamHomeFragment(), "teamHome")
                .addToBackStack(null)
                .commit();
    }
}