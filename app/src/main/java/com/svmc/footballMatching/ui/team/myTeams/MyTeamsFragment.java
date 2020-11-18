package com.svmc.footballMatching.ui.team.myTeams;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.svmc.footballMatching.R;
import com.svmc.footballMatching.data.model.User;
import com.svmc.footballMatching.databinding.FragmentMyTeamsBinding;
import com.svmc.footballMatching.ui.team.createTeam.CreateTeamDialogFragment;

import java.util.List;

public class MyTeamsFragment extends Fragment {
    private FragmentMyTeamsBinding binding;
    private MyTeamsViewModel viewModel;
    private boolean fabShowingMore = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMyTeamsBinding.inflate(inflater);
        binding.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this).get(MyTeamsViewModel.class);
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

        initFab(binding.invitationFab);
        initFab(binding.createTeamFab);

        binding.addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!fabShowingMore) {
                    binding.addFab.setImageResource(R.drawable.ic_close_white_24);
                    fabShowingMore = true;
                    showFab(binding.invitationFab);
                    showFab(binding.createTeamFab);
                } else {
                    binding.addFab.setImageResource(R.drawable.ic_more_horiz_24);
                    fabShowingMore = false;
                    hideFab(binding.invitationFab);
                    hideFab(binding.createTeamFab);
                }
            }
        });

        binding.invitationFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
            }
        });

        binding.createTeamFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateTeamDialogFragment createTeamDialogFragment = new CreateTeamDialogFragment();
                createTeamDialogFragment.setCancelable(false);
                createTeamDialogFragment.show(getParentFragmentManager(), "createTeam");
            }
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        MyTeamsRecyclerViewAdapter adapter = viewModel.getAdapter();
        binding.recyclerView.setAdapter(adapter);
    }

    private void observeLiveData(Context context) {
        viewModel.joinedTeamLiveData.observe(getViewLifecycleOwner(), new Observer<List<User.JoinedTeam>>() {
            @Override
            public void onChanged(List<User.JoinedTeam> joinedTeams) {
                viewModel.getAdapter().setJoinedTeams(joinedTeams);
                viewModel.getAdapter().notifyDataSetChanged();
            }
        });
    }

    private void initFab(FloatingActionButton floatingActionButton) {
        floatingActionButton.setVisibility(View.GONE);
        floatingActionButton.setTranslationY(floatingActionButton.getHeight());
        floatingActionButton.setAlpha(0f);
    }

    private void showFab(FloatingActionButton floatingActionButton) {
        floatingActionButton.setVisibility(View.VISIBLE);
        floatingActionButton.setAlpha(0f);
        floatingActionButton.setTranslationY(floatingActionButton.getHeight());
        floatingActionButton.animate().setDuration(200).translationY(0f).alpha(1f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        }).start();
    }

    private void hideFab(final FloatingActionButton floatingActionButton) {
        floatingActionButton.setVisibility(View.VISIBLE);
        floatingActionButton.setAlpha(1f);
        floatingActionButton.setTranslationY(0);
        floatingActionButton.animate().setDuration(200).translationY(floatingActionButton.getHeight()).alpha(0f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                floatingActionButton.setVisibility(View.GONE);
            }
        }).start();
    }

    private void detach(){
        getParentFragmentManager().popBackStack();
        getParentFragmentManager()
                .beginTransaction()
                .detach(this)
                .commit();
    }
}
