package com.svmc.footballMatching.ui.personalProfile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.svmc.footballMatching.R;
import com.svmc.footballMatching.databinding.FragmentOtherProfileBinding;

public class OtherPersonalProfileFragment extends Fragment {
    private FragmentOtherProfileBinding binding;
    private OtherPersonalProfileViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentOtherProfileBinding.inflate(inflater);
        binding.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this).get(OtherPersonalProfileViewModel.class);
        binding.setViewModel(viewModel);
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
                getParentFragmentManager().popBackStack();
                getParentFragmentManager().beginTransaction().detach(OtherPersonalProfileFragment.this).commit();
            }
        });

        binding.personalBasicInformationLayout.editBasicInformationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager()
                        .beginTransaction()
                        .add(R.id.container, new EditPersonalBasicInformationFragment(), null)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void observeLiveData(Context context) {
    }
}
