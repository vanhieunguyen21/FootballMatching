package com.svmc.footballMatching.ui.team.teamHome;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.svmc.footballMatching.ui.team.match.MatchPlayerFragment;
import com.svmc.footballMatching.ui.team.match.MatchTeamFragment;
import com.svmc.footballMatching.ui.team.teamProfile.TeamProfileFragment;

public class TeamHomeViewPagerAdapter extends FragmentPagerAdapter {
    public TeamHomeViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MatchTeamFragment();
            case 1:
                return new MatchPlayerFragment();
            case 2:
                return new TeamProfileFragment();
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


}
