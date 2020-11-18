package com.svmc.footballMatching.ui.team.match.player;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.svmc.footballMatching.data.model.User;
import com.svmc.footballMatching.databinding.RecyclerViewItemPlayerBinding;

import java.util.ArrayList;
import java.util.List;

public class MatchPlayersRecyclerViewAdapter extends RecyclerView.Adapter<MatchPlayersRecyclerViewAdapter.TeamViewHolder> {

    private List<User> players = new ArrayList<>();

    public void setPlayers(List<User> players) {
        this.players = players;
    }

    public void addPlayers(List<User> players) {
        this.players.addAll(players);
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerViewItemPlayerBinding binding = RecyclerViewItemPlayerBinding.inflate(inflater, parent, false);
        // Set grid item size
        GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) binding.getRoot().getLayoutParams();
        params.width = (parent.getMeasuredWidth() / 2) - 50;
        params.height = params.width;
        params.setMargins(25, 25, 25, 25);
        binding.getRoot().setLayoutParams(params);
        return new TeamViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {
        User player = players.get(position);
        holder.bind(player);
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public static class TeamViewHolder extends RecyclerView.ViewHolder {
        final RecyclerViewItemPlayerBinding binding;

        public TeamViewHolder(RecyclerViewItemPlayerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(User player) {
            binding.setPlayer(player);
            binding.executePendingBindings();
        }
    }
}
