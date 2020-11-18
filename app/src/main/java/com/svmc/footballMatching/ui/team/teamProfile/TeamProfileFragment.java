package com.svmc.footballMatching.ui.team.teamProfile;

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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.svmc.footballMatching.R;
import com.svmc.footballMatching.data.enumeration.LoadDataState;
import com.svmc.footballMatching.data.enumeration.Result;
import com.svmc.footballMatching.data.model.Team;
import com.svmc.footballMatching.data.session.CurrentTeam;
import com.svmc.footballMatching.databinding.FragmentTeamProfileBinding;
import com.svmc.footballMatching.ui.MainActivity;

public class TeamProfileFragment extends Fragment {
    private final String TAG = "TeamProfileFragment";
    private FragmentTeamProfileBinding binding;
    private TeamProfileViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        binding = FragmentTeamProfileBinding.inflate(inflater);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated: ");
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        if (args == null) {
            Log.d(TAG, "Null arguments");
            detach();
            return;
        }
        String mode = args.getString("mode");
        if (mode == null) {
            mode = "view";
        }
        Team team = (Team) args.getSerializable("team");
        if (!mode.equals("leader") && !mode.equals("member") && team == null) {
            Log.d(TAG, "Null team");
            detach();
            return;
        }
        TeamProfileViewModel.TeamProfileViewModelFactory factory = new TeamProfileViewModel.TeamProfileViewModelFactory(CurrentTeam.getInstance().getMyTeamLiveData().getValue());
        viewModel = new ViewModelProvider(this, factory).get(TeamProfileViewModel.class);
        binding.setTeam(CurrentTeam.getInstance().getMyTeamLiveData().getValue());

        initComponents(view.getContext(), mode);
        observeLiveData(view.getContext());
    }

    private void initComponents(final Context context, String mode) {
        if (mode == null) {
            detach();
            return;
        }
        // TODO: photos

        // Team members recycler view
        TeamMemberRecyclerViewAdapter teamMemberRecyclerViewAdapter = viewModel.getTeamMemberRecyclerViewAdapter();
        binding.teamMembersRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        binding.teamMembersRecyclerView.setAdapter(teamMemberRecyclerViewAdapter);

        if (mode.equals("leader") || mode.equals("member")) {
            if (mode.equals("leader")) {
                binding.editIntroductionTextView.setVisibility(View.VISIBLE);
                binding.editIntroductionTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MainActivity mainActivity = (MainActivity) getActivity();
                        mainActivity.addFragment(new EditTeamIntroductionFragment(), true, null, null);
                    }
                });
                binding.editPhotosTextView.setVisibility(View.VISIBLE);
                binding.editPhotosTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MainActivity mainActivity = (MainActivity) getActivity();
                        mainActivity.addFragment(new EditTeamPhotosFragment(), true, null, null);
                    }
                });
            } else {

            }
        } else if (mode.equals("match") || mode.equals("view")) {
            // Set up action bar
            binding.appBarLayout.setVisibility(View.VISIBLE);
            binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_back_white_24);
            binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    detach();
                }
            });

            if (mode.equals("match")) {
                binding.likeFab.setVisibility(View.VISIBLE);
                viewModel.getTeamLiveData().observe(getViewLifecycleOwner(), new Observer<Team>() {
                    @Override
                    public void onChanged(Team team) {
                        if (team == null) return;
                        // Change like icon to liked if already liked
                        if (team.isLikedByTeam(CurrentTeam.getInstance().getMyTeamLiveData().getValue().getId())) {
                            binding.likeFab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorGray)));
                            binding.likeFab.setEnabled(false);
                        }
                        // Set up click listener if not liked
                        else {
                            binding.likeFab.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    viewModel.likeTeam();
                                }
                            });
                        }
                    }
                });
            }

            binding.messageFab.setVisibility(View.VISIBLE);
            binding.messageFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO
                }
            });
        }
    }

    private void observeLiveData(final Context context) {
        viewModel.getTeamLoadState().observe(getViewLifecycleOwner(), new Observer<LoadDataState>() {
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

        viewModel.getLikeTeamResultLiveData().observe(getViewLifecycleOwner(), new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if (result == null) return;
                if (result == Result.SUCCESS) {
                    binding.likeFab.setImageResource(R.drawable.ic_baseline_check_24);
                    binding.likeFab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorGray)));
                    binding.likeFab.setEnabled(false);
                } else {
                    Toast.makeText(context, viewModel.likeTeamResultMessage, Toast.LENGTH_SHORT).show();
                    binding.likeFab.setImageResource(R.drawable.ic_thumb_up_white_24);
                    binding.likeFab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorPrimary)));
                    binding.likeFab.setEnabled(true);
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
