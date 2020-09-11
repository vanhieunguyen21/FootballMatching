package com.svmc.footballMatching.ui.team.teamProfile;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.svmc.footballMatching.data.model.Team;
import com.svmc.footballMatching.databinding.RecyclerViewItemTeamMemberBinding;

import java.util.ArrayList;
import java.util.List;

public class TeamMemberRecyclerViewAdapter extends RecyclerView.Adapter<TeamMemberRecyclerViewAdapter.TeamMemberViewHolder> {
    private List<Team.TeamMember> teamMembers = new ArrayList<>();

    public void setTeamMembers(List<Team.TeamMember> teamMembers) {
        this.teamMembers = teamMembers;
    }

    @NonNull
    @Override
    public TeamMemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerViewItemTeamMemberBinding binding = RecyclerViewItemTeamMemberBinding.inflate(inflater, parent, false);
        return new TeamMemberViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamMemberViewHolder holder, int position) {
        Team.TeamMember teamMember = teamMembers.get(position);
        holder.bind(teamMember);
    }

    @Override
    public int getItemCount() {
        return teamMembers.size();
    }

    class TeamMemberViewHolder extends RecyclerView.ViewHolder {
        final RecyclerViewItemTeamMemberBinding binding;

        public TeamMemberViewHolder(RecyclerViewItemTeamMemberBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Team.TeamMember teamMember) {
            binding.setTeamMember(teamMember);
            binding.executePendingBindings();
        }
    }
}
