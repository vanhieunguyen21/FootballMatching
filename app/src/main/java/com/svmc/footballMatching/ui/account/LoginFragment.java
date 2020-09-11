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

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.svmc.footballMatching.R;
import com.svmc.footballMatching.data.enumeration.Result;
import com.svmc.footballMatching.data.model.LoginResult;
import com.svmc.footballMatching.data.model.user.User;
import com.svmc.footballMatching.data.session.Session;
import com.svmc.footballMatching.databinding.CustomLoadingLayoutBinding;
import com.svmc.footballMatching.databinding.FragmentLoginBinding;
import com.svmc.footballMatching.ui.home.HomeFragment;

public class LoginFragment extends Fragment {
    private final String TAG = "LoginFragment";
    private FragmentLoginBinding binding;
    private LoginViewModel viewModel;
    private Dialog loadingDialog;
    private CustomLoadingLayoutBinding customLoadingLayoutBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater);
        binding.setLifecycleOwner(this);
        // Initiate view model and set binding variable
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding.setViewModel(viewModel);
        // Set background
//        binding.getRoot().setBackgroundResource(R.drawable.login_background);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated: ");
        initComponents(view.getContext());
        observeLiveData(view.getContext());
        initLoadingDialog(view.getContext());

        // Try to re-log in with current auth account
        if (viewModel.isLoggedIn()) {
            loadingDialog.show();
            viewModel.loginWithCurrentAuthAccount();
        }
    }

    private void initComponents(Context context) {
        Log.d(TAG, "initComponents: ");

        binding.passwordTextInputEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    binding.loginButton.performClick();
                }
                return false;
            }
        });

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            @SuppressWarnings("ConstantConditions")
            public void onClick(View view) {
                // Show loading dialog
                loadingDialog.show();
                // Login
                viewModel.login(
                        binding.emailTextInputEditText.getText().toString(),
                        binding.passwordTextInputEditText.getText().toString()
                );
            }
        });

        binding.createAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceWithRegisterFragment();
            }
        });

        binding.forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: forgot password fragment
            }
        });
    }

    private void observeLiveData(final Context context) {
        Log.d(TAG, "observeLiveData: ");
        viewModel.getLoginResultLiveData().observe(getViewLifecycleOwner(), new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if (result == null) return;
                // Dismiss loading dialog
                loadingDialog.dismiss();
                if (result == Result.FAILURE) {
                    Toast.makeText(context, viewModel.getLoginMessage(), Toast.LENGTH_SHORT).show();
                } else if (result == Result.SUCCESS) {
                    replaceWithHomeFragment();
                    String welcomeText = getString(R.string.welcome) + " " + Session.getInstance().getUserLiveData().getValue().getFullName();
                    Toast.makeText(context, welcomeText, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void replaceWithRegisterFragment() {
        getParentFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter_from_bottom, R.anim.fade_out, R.anim.fade_in, R.anim.exit_to_bottom)
                .add(binding.container.getId(), new RegisterFragment())
                .addToBackStack(null)
                .commit();
    }

    private void replaceWithHomeFragment() {
        getParentFragmentManager()
                .beginTransaction()
//                .setCustomAnimations(R.anim.enter_from_bottom, R.anim.fade_out, R.anim.fade_in, R.anim.exit_to_bottom)
                .replace(binding.container.getId(), new HomeFragment())
                .commit();
    }

    private void initLoadingDialog(Context context) {
        loadingDialog = new Dialog(context);
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customLoadingLayoutBinding = CustomLoadingLayoutBinding.inflate(getLayoutInflater());
        loadingDialog.setContentView(customLoadingLayoutBinding.getRoot());
        customLoadingLayoutBinding.title.setText(R.string.logging_in);
        loadingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                viewModel.cancelLoginRequest();
            }
        });
    }
}