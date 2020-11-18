package com.svmc.footballMatching.ui.team.matchHistory.matchTeam;

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
import androidx.recyclerview.widget.GridLayoutManager;

import com.svmc.footballMatching.data.model.Team;
import com.svmc.footballMatching.data.session.CurrentTeam;
import com.svmc.footballMatching.databinding.RecyclerViewStandaloneBinding;

public class LikedTeamsFragment extends Fragment {
    private RecyclerViewStandaloneBinding binding;
    private LikedTeamsViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = RecyclerViewStandaloneBinding.inflate(inflater);
        binding.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this).get(LikedTeamsViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        if (args == null)

        initComponents(view.getContext());
        observeLiveData(view.getContext());
    }

    private void initComponents(Context context) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        binding.recyclerView.setLayoutManager(gridLayoutManager);
        binding.recyclerView.setAdapter(viewModel.getAdapter());
    }

    private void observeLiveData(Context context) {
        CurrentTeam.getInstance().getMyTeamLiveData().observe(getViewLifecycleOwner(), new Observer<Team>() {
            @Override
            public void onChanged(Team team) {
                viewModel.setAdapterData(team.getLikedTeams());
            }
        });
    }


}
