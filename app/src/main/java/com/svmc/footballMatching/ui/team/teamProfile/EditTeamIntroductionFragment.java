package com.svmc.footballMatching.ui.team.teamProfile;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.svmc.footballMatching.R;
import com.svmc.footballMatching.data.enumeration.Result;
import com.svmc.footballMatching.data.model.Team;
import com.svmc.footballMatching.databinding.CustomLoadingLayoutBinding;
import com.svmc.footballMatching.databinding.FragmentEditTeamIntroductionBinding;

import java.util.HashMap;
import java.util.Map;

public class EditTeamIntroductionFragment extends Fragment {
    private FragmentEditTeamIntroductionBinding binding;
    private EditTeamIntroductionViewModel viewModel;

    private Dialog loadingDialog;
    private CustomLoadingLayoutBinding customLoadingLayoutBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditTeamIntroductionBinding.inflate(inflater);
        binding.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this).get(EditTeamIntroductionViewModel.class);
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
        // Set up action bar
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_back_white_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().popBackStack();
                getParentFragmentManager().beginTransaction().detach(EditTeamIntroductionFragment.this).commit();
            }
        });

        binding.resetImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.introductionTextInputEditText.setText(viewModel.teamLiveData.getValue().getIntroduction());
            }
        });

        binding.confirmImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initLoadingDialog(context);
                loadingDialog.show();
                viewModel.updateTeamProfile(getUpdateIntroduction());
            }
        });
    }

    private void observeLiveData(final Context context) {
        viewModel.getResultLiveData().observe(getViewLifecycleOwner(), new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if (result == null) return;
                if (result == Result.SUCCESS) {
                    loadingDialog.dismiss();
                    Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
                    detach();
                } else if (result == Result.FAILURE) {
                    loadingDialog.dismiss();
                    Toast.makeText(context, viewModel.getResultMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Map<String, Object> getUpdateIntroduction() {
        Map<String, Object> data = new HashMap<>();
        data.put("introduction", binding.introductionTextInputEditText.getText().toString());
        return data;
    }

    private void detach() {
        getParentFragmentManager().popBackStack();
        getParentFragmentManager()
                .beginTransaction()
                .detach(this)
                .commit();
    }

    private void initLoadingDialog(Context context) {
        loadingDialog = new Dialog(context);
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customLoadingLayoutBinding = CustomLoadingLayoutBinding.inflate(getLayoutInflater());
        loadingDialog.setContentView(customLoadingLayoutBinding.getRoot());
        customLoadingLayoutBinding.title.setText(R.string.updating_information);
        loadingDialog.setCancelable(false);
    }
}
