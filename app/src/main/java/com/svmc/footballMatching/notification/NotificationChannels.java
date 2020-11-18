package com.svmc.footballMatching.notification;

import android.app.NotificationManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationChannels {
    public static LikedByTeamChannel getLikedByTeamChannel() {
        return LikedByTeamChannel.getInstance();
    }

    public static class LikedByTeamChannel extends NotificationChannelData {
        private static LikedByTeamChannel instance;

        public static LikedByTeamChannel getInstance(){
            if (instance == null) {
                instance = new LikedByTeamChannel();
            }
            return instance;
        }

        private LikedByTeamChannel(){
            channelId = "liked_by_team_channel";
            channelName = "Liked By Team";
            channelDescription = "Liked By Team Channel";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                channelImportance = NotificationManager.IMPORTANCE_DEFAULT;
            }
            channelEnableVibrate = false;
            channelLockScreenVisibility = NotificationCompat.VISIBILITY_PUBLIC;
        }
    }

    public abstract static class NotificationChannelData {
        protected String channelId;
        protected CharSequence channelName;
        protected String channelDescription;
        protected int channelImportance;
        protected boolean channelEnableVibrate;
        protected int channelLockScreenVisibility;

        public String getChannelId() {
            return channelId;
        }

        public CharSequence getChannelName() {
            return channelName;
        }

        public String getChannelDescription() {
            return channelDescription;
        }

        public int getChannelImportance() {
            return channelImportance;
        }

        public boolean isChannelEnableVibrate() {
            return channelEnableVibrate;
        }

        public int getChannelLockScreenVisibility() {
            return channelLockScreenVisibility;
        }
    }
}
