package com.svmc.footballMatching.ui.team.teamHome;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.svmc.footballMatching.ui.team.match.player.MatchPlayersFragment;
import com.svmc.footballMatching.ui.team.match.team.MatchTeamFragment;
import com.svmc.footballMatching.ui.team.teamProfile.TeamProfileFragment;

public class LeaderTeamHomeViewPagerAdapter extends FragmentStatePagerAdapter {
    private final String TAG = "LeaderPagerAdapter";

    public LeaderTeamHomeViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        Log.d(TAG, "LeaderTeamHomeViewPagerAdapter: ");
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Log.d(TAG, "getItem: "+position);
        switch (position) {
            case 0:
                return new MatchTeamFragment();
            case 1:
                return new MatchPlayersFragment();
            case 2:
                TeamProfileFragment teamProfileFragment = new TeamProfileFragment();
                Bundle teamProfileArgs = new Bundle();
                teamProfileArgs.putString("mode", "leader");
                teamProfileFragment.setArguments(teamProfileArgs);
                return teamProfileFragment;
            default:
                throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Match Team";
            case 1:
                return "Match Player";
            case 2:
                return "Team Profile";
            default:
                throw new IndexOutOfBoundsException();
        }
    }

    @Nullable
    @Override
    public Parcelable saveState() {
        return null;
    }
}
