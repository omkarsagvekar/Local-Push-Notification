package com.os.localnotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String toast = intent.getStringExtra("toast");
        Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
    }
}
