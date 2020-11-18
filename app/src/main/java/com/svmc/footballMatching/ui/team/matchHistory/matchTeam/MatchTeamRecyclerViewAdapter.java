package com.svmc.footballMatching.ui.team.matchHistory.matchTeam;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.svmc.footballMatching.data.model.LikedTeam;
import com.svmc.footballMatching.data.model.Team;
import com.svmc.footballMatching.databinding.RecyclerViewItemTeamBinding;
import com.svmc.footballMatching.ui.MainActivity;
import com.svmc.footballMatching.ui.team.teamProfile.TeamProfileFragment;

import java.util.ArrayList;
import java.util.List;

public class MatchTeamRecyclerViewAdapter extends RecyclerView.Adapter<MatchTeamRecyclerViewAdapter.MatchViewHolder> {
    private final String TAG = "MatchAdapter";
    private List<LikedTeam> likedTeams = new ArrayList<>();

    public void setLikedTeams(List<LikedTeam> likedTeams) {
        this.likedTeams = likedTeams;
    }

    public void addLikedTeams(List<LikedTeam> teams) {
        this.likedTeams.addAll(teams);
    }

    @NonNull
    @Override
    public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerViewItemTeamBinding binding = RecyclerViewItemTeamBinding.inflate(inflater, parent, false);
        // Set grid item size
        GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) binding.getRoot().getLayoutParams();
        params.width = (parent.getMeasuredWidth() / 2) - 50;
        params.height = params.width;
        params.setMargins(25, 25, 25, 25);
        binding.getRoot().setLayoutParams(params);
        return new MatchViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchViewHolder holder, int position) {
        final LikedTeam likedTeam = likedTeams.get(position);
        holder.bind(likedTeam.getTeam());
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) view.getContext();
                Bundle args = new Bundle();
                args.putSerializable("team", likedTeam.getTeam());
                mainActivity.addFragment(new TeamProfileFragment(), true, args, null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return likedTeams.size();
    }

    public static class MatchViewHolder extends RecyclerView.ViewHolder {
        final RecyclerViewItemTeamBinding binding;

        public MatchViewHolder(RecyclerViewItemTeamBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Team team) {
            binding.setTeam(team);
            binding.executePendingBindings();
        }
    }
}