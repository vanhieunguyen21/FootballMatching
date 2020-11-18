package com.svmc.footballMatching.ui.account;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.svmc.footballMatching.R;
import com.svmc.footballMatching.data.enumeration.Result;
import com.svmc.footballMatching.databinding.CustomLoadingLayoutBinding;
import com.svmc.footballMatching.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment {
    private final String TAG = "RegisterFragment";
    private FragmentRegisterBinding binding;
    private RegisterViewModel viewModel;
    private Dialog loadingDialog;
    private CustomLoadingLayoutBinding customLoadingLayoutBinding;

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

        initComponents(view.getContext());
        observeLiveData(view.getContext());
    }

    private void initComponents(final Context context) {
        initLoadingDialog(context);

        binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingDialog.show();
                String email = binding.emailTextInputEditText.getText().toString();
                String password = binding.passwordTextInputEditText.getText().toString();
                String fullName = binding.fullNameTextInputEditText.getText().toString();
                viewModel.register(email, password, fullName);
            }
        });
    }

    private void observeLiveData(final Context context) {
        viewModel.getRegisterResultLiveData().observe(getViewLifecycleOwner(), new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if (result == null) return;
                loadingDialog.dismiss();
                if (result == Result.SUCCESS) {
                    Toast.makeText(context, "Register successfully", Toast.LENGTH_SHORT).show();
                    detach();
                } else if (result == Result.FAILURE) {
                    Toast.makeText(context, viewModel.registerMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initLoadingDialog(Context context) {
        loadingDialog = new Dialog(context);
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customLoadingLayoutBinding = CustomLoadingLayoutBinding.inflate(getLayoutInflater());
        loadingDialog.setContentView(customLoadingLayoutBinding.getRoot());
        customLoadingLayoutBinding.title.setText(R.string.registering);
        loadingDialog.setCancelable(false);
    }

    private void detach() {
        getParentFragmentManager().popBackStack();
        getParentFragmentManager()
                .beginTransaction()
                .detach(this)
                .commit();

    }

}