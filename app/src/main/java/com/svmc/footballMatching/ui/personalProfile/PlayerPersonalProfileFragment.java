package com.svmc.footballMatching.ui.personalProfile;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.svmc.footballMatching.R;
import com.svmc.footballMatching.data.model.user.Player;
import com.svmc.footballMatching.databinding.FragmentPlayerPersonalProfileBinding;

import java.util.Map;

import static com.svmc.footballMatching.util.MapUtils.*;

public class PlayerPersonalProfileFragment extends Fragment {
    private final String TAG = "PlayerPersonalProfileFragment";

    private FragmentPlayerPersonalProfileBinding binding;
    private PlayerPersonalProfileViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPlayerPersonalProfileBinding.inflate(inflater);
        viewModel = new ViewModelProvider(this).get(PlayerPersonalProfileViewModel.class);
        binding.setLifecycleOwner(this);
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
                getParentFragmentManager().beginTransaction().detach(PlayerPersonalProfileFragment.this).commit();
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

        binding.editIntroductionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager()
                        .beginTransaction()
                        .add(R.id.container, new EditPlayerIntroductionFragment(), null)
                        .addToBackStack(null)
                        .commit();
            }
        });

        binding.editSpecsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager()
                        .beginTransaction()
                        .add(R.id.container, new EditPlayerSpecsFragment(), null)
                        .addToBackStack(null)
                        .commit();
            }
        });

        binding.editPreferredPositionsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager()
                        .beginTransaction()
                        .add(R.id.container, new EditPlayerPreferredPositionsFragment(), null)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void observeLiveData(Context context) {
        // Update preferred positions from live data
        viewModel.playerLiveData.observe(getViewLifecycleOwner(), new Observer<Player>() {
            @Override
            public void onChanged(Player player) {
                if (player == null) return;
                Map<String, Object> preferredPositions = player.getPreferredPositions();
                boolean goalkeeper = valueToBooleanOrFalse(preferredPositions, "goalkeeper");
                boolean defender = valueToBooleanOrFalse(preferredPositions, "defender");
                boolean attackingMidfielder = valueToBooleanOrFalse(preferredPositions, "attackingMidfielder");
                boolean centreMidfielder = valueToBooleanOrFalse(preferredPositions, "centreMidfielder");
                boolean defensiveMidfielder = valueToBooleanOrFalse(preferredPositions, "defensiveMidfielder");
                boolean forwardAttacker = valueToBooleanOrFalse(preferredPositions, "forwardAttacker");
                boolean centreAttacker = valueToBooleanOrFalse(preferredPositions, "centreAttacker");

                if (goalkeeper) {
                    binding.goalkeeperTextView
                            .setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_check_green_24, 0, 0, 0);
                }
                if (defender) {
                    binding.defenderTextView
                            .setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_check_green_24, 0, 0, 0);
                }
                if (attackingMidfielder) {
                    binding.attackingMidfielderTextView
                            .setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_check_green_24, 0, 0, 0);
                }
                if (centreMidfielder) {
                    binding.centreMidfielderTextView
                            .setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_check_green_24, 0, 0, 0);
                }
                if (defensiveMidfielder) {
                    binding.defensiveMidfielderTextView
                            .setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_check_green_24, 0, 0, 0);
                }
                if (forwardAttacker) {
                    binding.forwardAttackerTextView
                            .setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_check_green_24, 0, 0, 0);
                }
                if (centreAttacker) {
                    binding.centreAttackerTextView
                            .setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_check_green_24, 0, 0, 0);
                }
            }
        });
    }
}