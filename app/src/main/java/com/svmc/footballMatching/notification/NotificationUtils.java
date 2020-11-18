package com.svmc.footballMatching.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

public class NotificationUtils {
    public static String createNotificationChannel(Context context, NotificationChannels.NotificationChannelData notificationChannelData) {
        // NotificationChannels are required for Notifications on O (API 26) and above.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // The id of the channel.
            String channelId = notificationChannelData.getChannelId();

            // The user-visible name of the channel.
            CharSequence channelName = notificationChannelData.getChannelName();

            // The user-visible description of the channel.
            String channelDescription = notificationChannelData.getChannelDescription();
            int channelImportance = notificationChannelData.getChannelImportance();
            boolean channelEnableVibrate = notificationChannelData.isChannelEnableVibrate();
            int channelLockScreenVisibility = notificationChannelData.getChannelLockScreenVisibility();

            // Initializes NotificationChannel.
            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, channelImportance);
            notificationChannel.setDescription(channelDescription);
            notificationChannel.enableVibration(channelEnableVibrate);
            notificationChannel.setLockscreenVisibility(channelLockScreenVisibility);

            // Adds NotificationChannel to system.
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            } else {
                return null;
            }
            return channelId;
        } else {
            // Returns null for pre-O (26) devices.
            return null;
        }
    }
}
