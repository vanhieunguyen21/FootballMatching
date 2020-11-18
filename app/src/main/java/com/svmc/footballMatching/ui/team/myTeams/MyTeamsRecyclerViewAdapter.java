package com.svmc.footballMatching.ui.team.myTeams;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.svmc.footballMatching.data.model.User;
import com.svmc.footballMatching.data.session.CurrentTeam;
import com.svmc.footballMatching.data.session.Session;
import com.svmc.footballMatching.databinding.RecyclerViewItemMyTeamBinding;
import com.svmc.footballMatching.ui.MainActivity;
import com.svmc.footballMatching.ui.team.teamHome.LeaderTeamHomeFragment;
import com.svmc.footballMatching.ui.team.teamHome.MemberTeamHomeFragment;
import com.svmc.footballMatching.ui.team.teamHome.TeamHomeFragment;

import java.util.ArrayList;
import java.util.List;

public class MyTeamsRecyclerViewAdapter extends RecyclerView.Adapter<MyTeamsRecyclerViewAdapter.MyTeamViewHolder> {
    private List<User.JoinedTeam> joinedTeams = new ArrayList<>();

    public void setJoinedTeams(List<User.JoinedTeam> joinedTeams) {
        this.joinedTeams = joinedTeams;
    }

    @NonNull
    @Override
    public MyTeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerViewItemMyTeamBinding binding = RecyclerViewItemMyTeamBinding.inflate(inflater, parent, false);
        return new MyTeamViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyTeamViewHolder holder, int position) {
        final User.JoinedTeam joinedTeam = joinedTeams.get(position);
        holder.bind(joinedTeam);
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) view.getContext();
                Bundle args = new Bundle();
                args.putSerializable("team", joinedTeam.getTeam());
                boolean isLeader = false;
                if (joinedTeam.getTeam().getLeader().getId().equals(Session.getInstance().getUserLiveData().getValue().getId()))
                    isLeader = true;
                args.putBoolean("isLeader", isLeader);
                mainActivity.addFragmentWithoutView(new TeamHomeFragment(), args, "teamHome");
//                if (joinedTeam.getTeam().getLeader().getId().equals(Session.getInstance().getUserLiveData().getValue().getId())) {
//                    mainActivity.replaceFragment(new LeaderTeamHomeFragment(), true, args, "teamHome");
//                } else {
//                    mainActivity.replaceFragment(new MemberTeamHomeFragment(), true, args, "teamHome");
//                }
            }
        });
    }

    @Override
    public int getItemCount() {
        int count = 0;
        for (User.JoinedTeam joinedTeam : joinedTeams) {
            if (joinedTeam.getStatus().equals("joined")) count++;
        }
        return count;
    }

    static class MyTeamViewHolder extends RecyclerView.ViewHolder {
        final RecyclerViewItemMyTeamBinding binding;

        public MyTeamViewHolder(RecyclerViewItemMyTeamBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(User.JoinedTeam joinedTeam) {
            binding.setJoinedTeam(joinedTeam);
            binding.executePendingBindings();
        }
    }
}
