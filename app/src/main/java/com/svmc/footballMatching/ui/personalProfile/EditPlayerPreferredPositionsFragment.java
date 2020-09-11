package com.svmc.footballMatching.ui.personalProfile;

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
import com.svmc.footballMatching.data.model.user.Player;
import com.svmc.footballMatching.databinding.CustomLoadingLayoutBinding;
import com.svmc.footballMatching.databinding.FragmentEditPlayerPreferredPositionsBinding;

import java.util.HashMap;
import java.util.Map;

public class EditPlayerPreferredPositionsFragment extends Fragment {
    private FragmentEditPlayerPreferredPositionsBinding binding;
    private EditPlayerPreferredPositionsViewModel viewModel;

    private Dialog loadingDialog;
    private CustomLoadingLayoutBinding customLoadingLayoutBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditPlayerPreferredPositionsBinding.inflate(inflater);
        binding.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this).get(EditPlayerPreferredPositionsViewModel.class);
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
                getParentFragmentManager().beginTransaction().detach(EditPlayerPreferredPositionsFragment.this).commit();
            }
        });

        binding.resetImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Player player = viewModel.playerLiveData.getValue();
                binding.forwardAttackerCheckBox.setChecked(player.isForwardAttacker());
                binding.centreAttackerCheckBox.setChecked(player.isCentreAttacker());
                binding.defenderCheckBox.setChecked(player.isDefender());
                binding.attackingMidfielderCheckBox.setChecked(player.isAttackingMidfielder());
                binding.centreMidfielderCheckBox.setChecked(player.isCentreMidfielder());
                binding.defensiveMidfielderCheckBox.setChecked(player.isDefensiveMidfielder());
                binding.goalkeeperCheckBox.setChecked(player.isGoalkeeper());
            }
        });

        binding.confirmImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initLoadingDialog(context);
                loadingDialog.show();
                viewModel.updateProfile(getUpdatePreferredPositions());
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
                    getParentFragmentManager().popBackStack();
                    getParentFragmentManager().beginTransaction().detach(EditPlayerPreferredPositionsFragment.this).commit();
                } else if (result == Result.FAILURE) {
                    loadingDialog.dismiss();
                    Toast.makeText(context, viewModel.getResultMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Map<String, Object> getUpdatePreferredPositions() {
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> preferredPositionsMap = new HashMap<>();
        preferredPositionsMap.put("forwardAttacker", binding.forwardAttackerCheckBox.isChecked());
        preferredPositionsMap.put("centreAttacker", binding.centreAttackerCheckBox.isChecked());
        preferredPositionsMap.put("defender", binding.defenderCheckBox.isChecked());
        preferredPositionsMap.put("attackingMidfielder", binding.attackingMidfielderCheckBox.isChecked());
        preferredPositionsMap.put("centreMidfielder", binding.centreMidfielderCheckBox.isChecked());
        preferredPositionsMap.put("defensiveMidfielder", binding.defensiveMidfielderCheckBox.isChecked());
        preferredPositionsMap.put("goalkeeper", binding.goalkeeperCheckBox.isChecked());
        data.put("preferredPositions", preferredPositionsMap);
        return data;
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
