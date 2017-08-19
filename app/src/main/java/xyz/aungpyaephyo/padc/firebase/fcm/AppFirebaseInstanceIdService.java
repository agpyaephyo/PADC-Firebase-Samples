package xyz.aungpyaephyo.padc.firebase.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import xyz.aungpyaephyo.padc.firebase.FirebaseApp;

public class AppFirebaseInstanceIdService extends FirebaseInstanceIdService {

    /**
     * The Application's current Instance ID token is no longer valid and thus a new one must be requested.
     */
    public void onTokenRefresh() {
        // If you need to handle the generation of a token, initially or
        // after a refresh this is where you should do that.
        String token = FirebaseInstanceId.getInstance().getToken();
        //TODO syncUserRegistrationId(token);
        Log.d(FirebaseApp.TAG, "FCM Token : " + token);
    }
}
