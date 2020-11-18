package com.svmc.footballMatching.ui.team.matchHistory;

import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.svmc.footballMatching.ui.team.matchHistory.matchPlayer.LikedByPlayersFragment;
import com.svmc.footballMatching.ui.team.matchHistory.matchPlayer.LikedPlayersFragment;
import com.svmc.footballMatching.ui.team.matchHistory.matchTeam.LikedByTeamsFragment;
import com.svmc.footballMatching.ui.team.matchHistory.matchTeam.LikedTeamsFragment;

public class MatchHistoryViewPagerAdapter extends FragmentStatePagerAdapter {
    private int mode = 0;

    public MatchHistoryViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public MatchHistoryViewPagerAdapter(@NonNull FragmentManager fm, int behavior, int mode) {
        super(fm, behavior);
        this.mode = mode;
    }

    public void setMode(int mode){
        if (this.mode == mode) return;
        this.mode = mode;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (mode == 0) {
            switch (position) {
                case 0:
                    return new LikedTeamsFragment();
                case 1:
                    return new LikedByTeamsFragment();
                default:
                    throw new IndexOutOfBoundsException();
            }
        } else {
            switch (position) {
                case 0:
                    return new LikedPlayersFragment();
                case 1:
                    return new LikedByPlayersFragment();
                default:
                    throw new IndexOutOfBoundsException();
            }
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Liked";
            case 1:
                return "Liked By";
            default:
                throw new IndexOutOfBoundsException();
        }
    }

    @Nullable
    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
