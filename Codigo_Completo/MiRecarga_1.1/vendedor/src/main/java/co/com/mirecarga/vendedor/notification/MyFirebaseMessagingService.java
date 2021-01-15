package co.com.mirecarga.vendedor.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import co.com.mirecarga.core.home.RespuestaInicio;
import co.com.mirecarga.vendedor.R;
import co.com.mirecarga.vendedor.business.NotificacionDato;
import co.com.mirecarga.vendedor.persistence.PersistenceException;
import co.com.mirecarga.vendedor.persistence.PersistenceMVP;
import co.com.mirecarga.vendedor.persistence.Persistence_Notificacion;

public class MyFirebaseMessagingService extends FirebaseMessagingService implements PersistenceMVP.View {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private String title;
    private String body;
    private String click_action;
    private String topic;
    private String tipo;

//    @Inject
//    NotificacionMVP.Presenter presenter = null;
    private Bundle bundle = null;

    @Override
    public void onCreate() {
        super.onCreate();

//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);

    }

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    @Override
    public void onMessageReceived(@NonNull final RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Context context = getApplicationContext();

//        presenter.setView(this);


        Intent intent = new Intent(click_action);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("link", title);

//        presenter.setView(MyFirebaseMessagingService.this);

        Persistence_Notificacion persistence_notifiaccion = new Persistence_Notificacion(context);



        Bundle bundle = new Bundle();
        for (Map.Entry<String, String> entry :remoteMessage.getData().entrySet()) {
            bundle.putString(entry.getKey(), entry.getValue());
        }

        String title = bundle.getString("title");
        String body = bundle.getString("body");
        String data = bundle.getString("data");
        NotificacionDato dato = new NotificacionDato(title, body, data);

        try {
            persistence_notifiaccion.insertNotificacion(dato);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }

//        if (bundle != null) {
//            try {
//                presenter.insertNotificacion();
//            } catch (PersistenceException e) {
//                e.printStackTrace();
//            }
//        }

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            scheduleJob(remoteMessage);

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

    }

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
    }

    /**
     * Schedule async work using WorkManager.
     */
    private void scheduleJob(RemoteMessage remoteMessage) {

        title = remoteMessage.getNotification().getTitle();
        body = remoteMessage.getNotification().getBody();
        click_action = remoteMessage.getNotification().getClickAction();
        topic = remoteMessage.getData().get("topic");
        tipo = remoteMessage.getData().get("tipo");

        if (NotificationListFragment.mAdapter != null) {
            NotificationListFragment.mAdapter.addItem(new NotificacionDato(title, body, tipo));
        }

        Context context = getApplicationContext();
        Intent intent = new Intent(click_action);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("title", title);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        String channelId = "some_channel_id";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }

        assert notificationManager != null;
        notificationManager.notify(0, notificationBuilder.build());

        Log.d(TAG, "Schedule async work using WorkManager.");
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    /**
     * Persist token to third-party servers.
     * <p>
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }

    @Override
    public Bundle getBundle() {
        return bundle;
    }

    @Override
    public RespuestaInicio getEntidad() {
        return null;
    }
}
