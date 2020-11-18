package com.svmc.footballMatching.ui.account.match;

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

import com.svmc.footballMatching.R;
import com.svmc.footballMatching.data.enumeration.LoadDataState;
import com.svmc.footballMatching.databinding.FragmentPlayerFindTeamBinding;
import com.svmc.footballMatching.databinding.RecyclerViewWithProgressBarBinding;

public class PlayerFindTeamsFragment extends Fragment {
    private final String TAG = "PlayerFindTeamsFragment";
    private FragmentPlayerFindTeamBinding binding;
    private PlayerFindTeamsViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPlayerFindTeamBinding.inflate(inflater);
        binding.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this).get(PlayerFindTeamsViewModel.class);
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

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        TeamsRecyclerViewAdapter adapter = viewModel.getAdapter();
        binding.recyclerView.setAdapter(adapter);
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