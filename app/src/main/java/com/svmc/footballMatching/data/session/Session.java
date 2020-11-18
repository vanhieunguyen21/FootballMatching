package com.svmc.footballMatching.data.session;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.svmc.footballMatching.R;
import com.svmc.footballMatching.callBackInterface.GetTeamCallBack;
import com.svmc.footballMatching.callBackInterface.GetUserCallBack;
import com.svmc.footballMatching.data.model.Team;
import com.svmc.footballMatching.data.model.User;
import com.svmc.footballMatching.data.repository.TeamRepository;
import com.svmc.footballMatching.data.repository.UserRepository;
import com.svmc.footballMatching.notification.NotificationChannels;
import com.svmc.footballMatching.notification.NotificationIdGenerator;
import com.svmc.footballMatching.notification.NotificationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Session {
    private final String TAG = "Session";
    private static Context applicationContext;
    private static Session instance;
    private MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private UserRepository userRepository = UserRepository.getInstance();
    private TeamRepository teamRepository = TeamRepository.getInstance();
    private List<ListenerRegistration> teamLikedByTeamsListeners = new ArrayList<>();
    private List<ListenerRegistration> teamLikedByPlayersListeners = new ArrayList<>();
    private ListenerRegistration playerLikedByTeamsListeners = null;

    private Session() {
    }

    public static void initSession(Application application) {
        Session.applicationContext = application.getApplicationContext();
        if (instance == null) {
            instance = new Session();
        }
    }

    public static Session getInstance() {
        if (instance != null) {
            return instance;
        }
        throw new IllegalArgumentException("Session is not initiated");
    }

    public Context getApplicationContext() {
        return applicationContext;
    }

    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public void setUser(User user) {
        userLiveData.setValue(user);
        detachListeners(teamLikedByTeamsListeners);
        detachListeners(teamLikedByPlayersListeners);
        if (playerLikedByTeamsListeners != null) {
            playerLikedByTeamsListeners.remove();
            playerLikedByTeamsListeners = null;
        }
        if (user != null) {
            startListeningToTeamLikedByTeams(user);
            startListeningToTeamLikedByPlayers(user);
            startListeningToPlayerLikedByTeams(user);
        }
    }

    public void logout() {
        userRepository.logout();
        setUser(null);
    }

    private void startListeningToTeamLikedByTeams(User user) {
        for (final User.JoinedTeam joinedTeam : user.getJoinedTeams()) {
            if (joinedTeam.getTeam().isLeader(user.getId())) {
                ListenerRegistration listener = teamRepository.listenToTeamLikedByTeams(joinedTeam.getTeam().getId(), new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w(TAG, "listen:error ", error);
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            switch (dc.getType()) {
                                case ADDED:
                                    Map<String, Object> newLikedByTeam = dc.getDocument().getData();
                                    final String teamId = (String) newLikedByTeam.get("team");
                                    Timestamp likedTimestamp = (Timestamp) newLikedByTeam.get("actionTimestamp");
                                    if (joinedTeam.getTeam().getLastUpdateNotificationTimestamp() == null ||
                                            (joinedTeam.getTeam().getLastUpdateNotificationTimestamp() != null
                                                    && likedTimestamp.getSeconds() > joinedTeam.getTeam().getLastUpdateNotificationTimestamp().getSeconds()))
                                        teamRepository.getTeam(teamId, new GetTeamCallBack() {
                                            @Override
                                            public void onSuccess(Team team) {
                                                createNewLikeNotification(team.getName(), joinedTeam.getTeam().getName());
                                                teamRepository.updateLastUpdateNotification(joinedTeam.getTeam().getId());
                                            }

                                            @Override
                                            public void onFailure(String message) {

                                            }
                                        });
                                    break;
                            }
                        }
                    }
                });
                teamLikedByTeamsListeners.add(listener);
            }
        }
    }

    private void startListeningToTeamLikedByPlayers(User user) {
        for (final User.JoinedTeam joinedTeam : user.getJoinedTeams()) {
            if (joinedTeam.getTeam().isLeader(user.getId())) {
                ListenerRegistration listener = teamRepository.listenToTeamLikedByPlayers(joinedTeam.getTeam().getId(), new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w(TAG, "listen:error ", error);
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            switch (dc.getType()) {
                                case ADDED:
                                    Map<String, Object> newLikedByPlayer = dc.getDocument().getData();
                                    String playerId = (String) newLikedByPlayer.get("player");
                                    Timestamp likedTimestamp = (Timestamp) newLikedByPlayer.get("actionTimestamp");
                                    if (joinedTeam.getTeam().getLastUpdateNotificationTimestamp() == null ||
                                            (joinedTeam.getTeam().getLastUpdateNotificationTimestamp() != null
                                                    && likedTimestamp.getSeconds() > joinedTeam.getTeam().getLastUpdateNotificationTimestamp().getSeconds()))
                                        userRepository.getUser(playerId, new GetUserCallBack() {
                                            @Override
                                            public void onSuccess(User player) {
                                                createNewLikeNotification(player.getFullName(), joinedTeam.getTeam().getName());
                                                teamRepository.updateLastUpdateNotification(joinedTeam.getTeam().getId());
                                            }

                                            @Override
                                            public void onFailure(String message) {

                                            }
                                        });
                                    break;
                            }
                        }
                    }
                });
                teamLikedByTeamsListeners.add(listener);
            }
        }
    }

    private void startListeningToPlayerLikedByTeams(final User user) {
        playerLikedByTeamsListeners = UserRepository.getInstance().listenToPlayerLikedByTeams(user.getId(), new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w(TAG, "listen:error ", error);
                    return;
                }
                for (DocumentChange dc : value.getDocumentChanges()) {
                    switch (dc.getType()) {
                        case ADDED:
                            Map<String, Object> newLikedByTeam = dc.getDocument().getData();
                            final String teamId = (String) newLikedByTeam.get("team");
                            Timestamp likedTimestamp = (Timestamp) newLikedByTeam.get("actionTimestamp");
                            if (user.getLastUpdateNotificationTimestamp() == null ||
                                    (user.getLastUpdateNotificationTimestamp() != null
                                            && likedTimestamp.getSeconds() > user.getLastUpdateNotificationTimestamp().getSeconds()))
                                teamRepository.getTeam(teamId, new GetTeamCallBack() {
                                    @Override
                                    public void onSuccess(Team team) {
                                        createNewLikeNotification(team.getName(), "You");
                                        userRepository.updateLastUpdateNotification(user.getId());
                                    }

                                    @Override
                                    public void onFailure(String message) {

                                    }
                                });
                            break;
                    }
                }
            }
        });
    }

    private void createNewLikeNotification(String source, String destination) {
        String title = "New like";
        String content = destination + " is liked by " + source;
        if (destination.equals("You")) content = "You are liked by " + source;
        final NotificationManagerCompat notificationManager = NotificationManagerCompat.from(applicationContext);
        String channelId = NotificationUtils.createNotificationChannel(applicationContext, NotificationChannels.getLikedByTeamChannel());
        final NotificationCompat.Builder newLikedTeamNotificationBuilder = new NotificationCompat.Builder(applicationContext, channelId);
        newLikedTeamNotificationBuilder.setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_love_24)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        int notificationId = NotificationIdGenerator.getInstance().nextNotificationId();
        notificationManager.notify(notificationId, newLikedTeamNotificationBuilder.build());

    }

    private void detachListeners(List<ListenerRegistration> listenerRegistrations) {
        for (ListenerRegistration listenerRegistration : listenerRegistrations) {
            listenerRegistration.remove();
        }
        listenerRegistrations.clear();
    }
}
