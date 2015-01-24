package com.example.john.voadownloader_011;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by john on 2015/1/5.
 */
public class AlarmReceiver extends BroadcastReceiver {

    public static final int REQUEST_CODE = 12345;

    @Override
    public void onReceive(Context context, Intent arg1) {
        // For our recurring task, we'll just display a message
        Toast.makeText(context, "Starting Download", Toast.LENGTH_SHORT).show();
        //Start the background service
        Intent intent = new Intent(context, DownloadService.class);
        context.startService(intent);
    }

}