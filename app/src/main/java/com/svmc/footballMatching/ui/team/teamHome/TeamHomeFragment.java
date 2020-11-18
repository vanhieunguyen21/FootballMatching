package com.svmc.footballMatching.ui.team.teamHome;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.svmc.footballMatching.data.model.Team;
import com.svmc.footballMatching.data.session.CurrentTeam;
import com.svmc.footballMatching.ui.MainActivity;

public class TeamHomeFragment extends Fragment {
    private final String TAG = "TeamHomeFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args == null){
            Log.d(TAG, "Argument is null");
            detach();
            return;
        }
        Team team = (Team) args.getSerializable("team");
        boolean isLeader = args.getBoolean("isLeader");
        if (team == null){
            Log.d(TAG, "Team is null");
            detach();
            return;
        }
        CurrentTeam.createInstance(team);
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            if (isLeader) {
                mainActivity.replaceFragment(new LeaderTeamHomeFragment(), true, null, null);
            } else {
                mainActivity.replaceFragment(new MemberTeamHomeFragment(), true, null, null);
            }
        }
    }

    private void detach(){
        getParentFragmentManager()
                .beginTransaction()
                .detach(this)
                .commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CurrentTeam.deleteInstance();
    }
}
