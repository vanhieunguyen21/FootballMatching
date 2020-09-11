package com.svmc.footballMatching.ui.account;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.svmc.footballMatching.R;
import com.svmc.footballMatching.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment {
    private final String TAG = "RegisterFragment";
    private FragmentRegisterBinding binding;
    private RegisterViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater);
        binding.setLifecycleOwner(this);
        // Initiate view model and set binding variable
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}