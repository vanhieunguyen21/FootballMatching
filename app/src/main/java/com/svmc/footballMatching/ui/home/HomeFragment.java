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
import com.svmc.footballMatching.ui.MainActivity;
import com.svmc.footballMatching.ui.account.LoginFragment;
import com.svmc.footballMatching.ui.account.match.PlayerFindTeamsFragment;
import com.svmc.footballMatching.ui.account.personalProfile.UserProfileFragment;
import com.svmc.footballMatching.ui.team.myTeams.MyTeamsFragment;

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

        binding.myTeamsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.replaceFragment(new MyTeamsFragment(), true, null, null);
            }
        });

        binding.findTeamCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.replaceFragment(new PlayerFindTeamsFragment(), true, null, null);
            }
        });

        binding.personalProfileCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.replaceFragment(new UserProfileFragment(), true, null, null);
            }
        });
    }

    private void inflateMenu() {
        binding.toolbar.inflateMenu(R.menu.main);
        binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                MainActivity mainActivity = (MainActivity) getActivity();
                switch (item.getItemId()) {
                    case R.id.profile:
                        mainActivity.replaceFragment(new UserProfileFragment(), true, null, null);
                        break;
                    case R.id.logout:
                        Session.getInstance().logout();
                        mainActivity.replaceFragment(new LoginFragment(), false, null, null);
                        break;
                }
                return true;
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