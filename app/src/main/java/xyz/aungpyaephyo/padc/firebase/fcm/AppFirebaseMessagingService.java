package xyz.aungpyaephyo.padc.firebase.fcm;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import xyz.aungpyaephyo.padc.firebase.FirebaseApp;
import xyz.aungpyaephyo.padc.firebase.utils.NotificationUtils;

public class AppFirebaseMessagingService extends FirebaseMessagingService {

    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Handle data payload of FCM messages.
        Log.d(FirebaseApp.TAG, "FCM Message : " + remoteMessage.getMessageId());
        Log.d(FirebaseApp.TAG, "FCM Notification Message: " +
                remoteMessage.getNotification());
        Log.d(FirebaseApp.TAG, "FCM Data Message: " + remoteMessage.getData());

        Map<String, String> remoteMsgData = remoteMessage.getData();
        String message = remoteMsgData.get(NotificationUtils.KEY_MESSAGE);

        NotificationUtils.notifyCustomMsg(getApplicationContext(), message);
    }
}
