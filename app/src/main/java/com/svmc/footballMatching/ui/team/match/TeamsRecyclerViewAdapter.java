package com.svmc.footballMatching.ui.team.match;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.svmc.footballMatching.data.model.Team;
import com.svmc.footballMatching.databinding.RecyclerViewItemTeamBinding;

import java.util.ArrayList;
import java.util.List;

public class TeamsRecyclerViewAdapter extends RecyclerView.Adapter<TeamsRecyclerViewAdapter.TeamViewHolder> {
    private List<Team> teams = new ArrayList<>();

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public void addTeams(List<Team> teams) {
        this.teams.addAll(teams);
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerViewItemTeamBinding binding = RecyclerViewItemTeamBinding.inflate(inflater, parent, false);
        // Set grid item size
        GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) binding.getRoot().getLayoutParams();
        params.width = (parent.getMeasuredWidth() / 2) - 10;
        params.height = params.width;
        params.setMargins(5, 5, 5, 5);
        binding.getRoot().setLayoutParams(params);
        return new TeamViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {
        Team team = teams.get(position);
        holder.bind(team);
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    static class TeamViewHolder extends RecyclerView.ViewHolder {
        final RecyclerViewItemTeamBinding binding;

        public TeamViewHolder(RecyclerViewItemTeamBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Team team) {
            binding.setTeam(team);
            binding.executePendingBindings();
        }
    }
}
