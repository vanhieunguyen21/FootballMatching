package com.svmc.footballMatching.ui.team.match.player;

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

import com.svmc.footballMatching.data.enumeration.LoadDataState;
import com.svmc.footballMatching.databinding.RecyclerViewStandaloneBinding;
import com.svmc.footballMatching.databinding.RecyclerViewWithProgressBarBinding;

public class MatchPlayersFragment extends Fragment {
    private RecyclerViewWithProgressBarBinding binding;
    private MatchPlayersViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = RecyclerViewWithProgressBarBinding.inflate(inflater);
        binding.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this).get(MatchPlayersViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view.getContext());
        observeLiveData(view.getContext());
    }

    private void initComponents(Context context) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        binding.recyclerView.setLayoutManager(gridLayoutManager);
        binding.recyclerView.setAdapter(viewModel.getAdapter());
    }

    private void observeLiveData(Context context) {
        viewModel.getPlayersLoadStateLiveData().observe(getViewLifecycleOwner(), new Observer<LoadDataState>() {
            @Override
            public void onChanged(LoadDataState loadDataState) {
                if (loadDataState == LoadDataState.LOADED) {
                    binding.loadingProgressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}
