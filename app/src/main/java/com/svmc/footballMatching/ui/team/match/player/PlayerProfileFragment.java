package com.svmc.footballMatching.ui.team.match.player;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.svmc.footballMatching.R;
import com.svmc.footballMatching.data.enumeration.LoadDataState;
import com.svmc.footballMatching.data.enumeration.Result;
import com.svmc.footballMatching.data.model.Team;
import com.svmc.footballMatching.data.model.User;
import com.svmc.footballMatching.data.session.CurrentTeam;
import com.svmc.footballMatching.databinding.FragmentPlayerProfileBinding;

public class PlayerProfileFragment extends Fragment {
    private final String TAG = "PlayerProfileFragment";
    private FragmentPlayerProfileBinding binding;
    private PlayerProfileViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPlayerProfileBinding.inflate(inflater);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        if (args == null){
            Log.d(TAG, "Null arguments");
            detach();
            return;
        }
        User player = (User) args.getSerializable("player");
        if (player == null){
            Log.d(TAG, "Null player");
            detach();
            return;
        }
        binding.setPlayer(player);

        initComponents(view.getContext());
        observeLiveData(view.getContext());
    }

    private void initComponents(final Context context) {
        // Set up action bar
        binding.appBarLayout.setVisibility(View.VISIBLE);
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_back_white_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detach();
            }
        });

        viewModel.getPlayerLiveData().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User player) {
                if (player == null) return;
                // Change like icon if already liked
                if (player.isLikedBy(CurrentTeam.getInstance().getMyTeamLiveData().getValue().getId())){
                    binding.likeFab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorGray)));
                    binding.likeFab.setEnabled(false);
                } else {
                    // Set up click listener if not liked
                    binding.likeFab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            viewModel.likePlayer();
                            binding.likeFab.setEnabled(false);
                        }
                    });
                }
            }
        });

        binding.messageFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        binding.inviteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void observeLiveData(final Context context) {
        viewModel.getPlayerLoadStateLiveData().observe(getViewLifecycleOwner(), new Observer<LoadDataState>() {
            @Override
            public void onChanged(LoadDataState loadDataState) {
                if (loadDataState == null) return;
                if (loadDataState == LoadDataState.INIT) {
                    binding.loadingLayout.setVisibility(View.VISIBLE);
                } else if (loadDataState == LoadDataState.LOADING) {
                    binding.loadingLayout.setVisibility(View.VISIBLE);
                } else if (loadDataState == LoadDataState.LOADED) {
                    binding.loadingLayout.setVisibility(View.GONE);
                }
            }
        });

        viewModel.getLikePlayerResultLiveData().observe(getViewLifecycleOwner(), new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if (result == null) return;
                if (result == Result.SUCCESS) {
                    binding.likeFab.setImageResource(R.drawable.ic_baseline_check_24);
                    binding.likeFab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorGray)));
                    binding.likeFab.setEnabled(false);
                } else {
                    Toast.makeText(context, viewModel.likePlayerResultMessage, Toast.LENGTH_SHORT).show();
                    binding.likeFab.setImageResource(R.drawable.ic_thumb_up_white_24);
                    binding.likeFab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorPrimary)));
                    binding.likeFab.setEnabled(true);
                }
            }
        });
    }

    private void detach(){
        getParentFragmentManager().popBackStack();
        getParentFragmentManager()
                .beginTransaction()
                .detach(this)
                .commit();
    }
}
