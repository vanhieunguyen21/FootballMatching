package com.svmc.footballMatching.ui.team.match;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.svmc.footballMatching.R;
import com.svmc.footballMatching.databinding.FragmentMatchTeamBinding;

public class MatchTeamFragment extends Fragment {
    private FragmentMatchTeamBinding binding;
    private MatchTeamViewModel viewModel;
    private int currentSelectedFragment = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMatchTeamBinding.inflate(inflater);
        binding.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this).get(MatchTeamViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view.getContext());
        observeLiveData(view.getContext());
    }

    private void initComponents(Context context) {
        replaceWithNearbyTeamsFragment();

        binding.teamMatchTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == currentSelectedFragment) return;
                switch (i) {
                    case 0:
                        replaceWithNearbyTeamsFragment();
                        break;
                    case 1:
                        replaceWithSuggestedTeamsFragment();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void observeLiveData(Context context) {
    }

    private void replaceWithNearbyTeamsFragment() {
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.recycler_view_container, new NearbyTeamsFragment(), null)
                .commit();
    }

    private void replaceWithSuggestedTeamsFragment() {
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.recycler_view_container, new SuggestedTeamsFragment(), null)
                .commit();
    }
}
