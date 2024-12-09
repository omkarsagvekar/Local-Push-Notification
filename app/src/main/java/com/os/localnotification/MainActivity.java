package com.os.localnotification;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.drawable.IconCompat;


import com.os.localnotification.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static String CHANNEL_ID = "1";
    int counter = 0;

    ActivityMainBinding binding;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);
        // Create notification channel (required for Android 8.0+)
        createNotificationChannel();
        binding.btnCounter.setOnClickListener(view -> {
            //counter++;
            //binding.btnCounter.setText(""+counter);
           //if (counter == 5){
                //startNotification();
                startNotification2();
            //}
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Local Notification";
            String description = "Channel for local notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            // Register the channel with the system
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void startNotification2() {
        // Build the notification
        Intent i = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_IMMUTABLE);

        //For Toast action button
        Intent actionIntent = new Intent(this, Receiver.class);
        actionIntent.putExtra("toast", "This is a Notification Message");
        PendingIntent pendingAction = PendingIntent.getBroadcast(this, 0, actionIntent, PendingIntent.FLAG_IMMUTABLE);

        @SuppressLint("RestrictedApi") NotificationCompat.Action action = new NotificationCompat.Action.Builder(IconCompat.createFromIcon(Icon.createWithResource(this,R.drawable.baseline_circle_notifications_24)),
                "Toast Message", pendingAction).build();

        //For Dismiss action button
        Intent dissmissIntent = new Intent(this, ReceiverDismiss.class);
        PendingIntent dismissPending = PendingIntent.getBroadcast(this, 0, dissmissIntent, PendingIntent.FLAG_IMMUTABLE);
        @SuppressLint("RestrictedApi") NotificationCompat.Action dismissAction = new NotificationCompat.Action.Builder(IconCompat.createFromIcon(Icon.createWithResource(this,
                R.drawable.baseline_circle_notifications_24)), "Dismiss", dismissPending).build();

        //add image
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.flower);
        String longText = getResources().getString(R.string.long_text);


        @SuppressLint("ResourceAsColor") NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_circle_notifications_24) // Notification icon
                .setContentTitle("Local Notification") // Title
                .setContentText("This is a sample local notification!") // Message
                .setPriority(NotificationCompat.PRIORITY_DEFAULT) // Priority
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .addAction(action) // Auto-dismiss on click
                .addAction(dismissAction)
                //.setSmallIcon(IconCompat.createWithBitmap(icon))
                .setLargeIcon(icon)
                //.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(icon)); //Use for big picture to show in notification
                .setStyle(new NotificationCompat.BigTextStyle().bigText(longText));  //Use for Large text to show in notification

        // Show the notification
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build()); // Use a unique ID for the notification
    }

    private void startNotification() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "1", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);

            Notification.Builder builder = new Notification.Builder(this, CHANNEL_ID);
            builder.setSmallIcon(R.drawable.baseline_circle_notifications_24)
                    .setContentTitle("Title")
                    .setContentText("Notification Text")
                    .setPriority(Notification.PRIORITY_DEFAULT);

            NotificationManagerCompat compat = NotificationManagerCompat.from(this);
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

                compat.notify(1, builder.build());
            }

        }
    }
}