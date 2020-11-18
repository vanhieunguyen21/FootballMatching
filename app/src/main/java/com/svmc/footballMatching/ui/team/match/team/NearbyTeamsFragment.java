package com.svmc.footballMatching.ui.team.match.team;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.svmc.footballMatching.data.enumeration.LoadDataState;
import com.svmc.footballMatching.databinding.RecyclerViewWithProgressBarBinding;

public class NearbyTeamsFragment extends Fragment {
    private final String TAG = "NearbyTeamsFragment";
    private RecyclerViewWithProgressBarBinding binding;
    private NearbyTeamsViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = RecyclerViewWithProgressBarBinding.inflate(inflater);
        binding.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this).get(NearbyTeamsViewModel.class);
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
        binding.recyclerView.setAdapter(viewModel.getAdapter());
        binding.recyclerView.setLayoutManager(gridLayoutManager);
    }

    private void observeLiveData(Context context) {
        viewModel.getTeamsLoadStateLiveData().observe(getViewLifecycleOwner(), new Observer<LoadDataState>() {
            @Override
            public void onChanged(LoadDataState loadDataState) {
                if (loadDataState == LoadDataState.LOADED) {
                    binding.loadingProgressBar.setVisibility(View.GONE);
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
}