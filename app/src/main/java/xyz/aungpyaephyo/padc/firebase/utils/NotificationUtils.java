package xyz.aungpyaephyo.padc.firebase.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.TextUtils;

import com.bumptech.glide.Glide;

import java.util.concurrent.ExecutionException;

import xyz.aungpyaephyo.padc.firebase.R;
import xyz.aungpyaephyo.padc.firebase.activities.NewsFeedActivity;
import xyz.aungpyaephyo.padc.firebase.components.mmfont.MMFontUtils;

/**
 * Created by aung on 8/19/17.
 */

public class NotificationUtils {

    private static final int NOTIFICATION_ID_NEW_MESSAGE = 2001;

    private static final int REQUEST_ID_SAVE_NEWS = 3001;

    public static final String KEY_MESSAGE = "custom_msg";

    public static void notifyCustomMsg(Context context, String message) {
        //Notification Title
        String title = context.getString(R.string.launcher_name);

        //Supporting both unicode & zawgyi
        String mmMessage = MMFontUtils.mmTextUnicodeOrigin(message);

        //Large Icon
        Bitmap appIcon = encodeResourceToBitmap(context, R.mipmap.ic_news_from_people);

        //Message in BigText Style
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.bigText(mmMessage);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setColor(context.getResources().getColor(R.color.accent))
                .setSmallIcon(R.drawable.ic_news_from_people_noti)
                .setLargeIcon(appIcon)
                .setContentTitle(title)
                .setContentText(mmMessage)
                .setAutoCancel(true)
                .setStyle(bigTextStyle);

        //Notification action to Play Songs by Artist.
        saveNewsAction(context, NOTIFICATION_ID_NEW_MESSAGE, builder);

        //Open the app when user tap on notification
        Intent resultIntent = NewsFeedActivity.newIntentNotiBody(context);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFICATION_ID_NEW_MESSAGE, builder.build());
    }

    private static Bitmap encodeResourceToBitmap(Context context, int resourceId) {
        Bitmap bitmap = null;
        //Encode bitmap for large notification icon
        int largeIconWidth = context.getResources().getDimensionPixelSize(android.R.dimen.notification_large_icon_width);
        int largeIconHeight = context.getResources().getDimensionPixelSize(android.R.dimen.notification_large_icon_height);

        try {
            bitmap = Glide.with(context)
                    .load(resourceId)
                    .asBitmap()
                    .into(largeIconWidth, largeIconHeight)
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    private static void saveNewsAction(Context context, int notificationId, NotificationCompat.Builder builder) {
        //Intent to execute when user tap on Action Button.
        Intent saveNewsActionIntent = NewsFeedActivity.newIntentSaveNews(context, notificationId);
        PendingIntent playSongsByArtistActionPendingIntent = PendingIntent.getActivity(context, REQUEST_ID_SAVE_NEWS, saveNewsActionIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //"Save News" Action Label.
        String notiActionSaveNews = context.getString(R.string.noti_action_save_news);

        //Action Button Icon.
        int actionIcon = R.drawable.ic_bookmark_border_24dp;
        if (TextUtils.equals(Build.BRAND, FirebaseAppConstants.VENDOR_XIAOMI)) {
            //actionIcon = R.drawable.ic_other_bookmark_border_24dp;
        }

        NotificationCompat.Action playSongsByArtistAction =
                new NotificationCompat.Action(actionIcon,
                        notiActionSaveNews, playSongsByArtistActionPendingIntent);
        builder.addAction(playSongsByArtistAction);
    }
}
