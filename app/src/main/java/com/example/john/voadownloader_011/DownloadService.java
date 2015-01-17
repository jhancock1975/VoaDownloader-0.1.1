package com.example.john.voadownloader_011;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by john on 2015/1/8.
 */
public class HelloIntentService extends IntentService {



    public static final int BUFFER_SIZE = 1024;

    public  void  logit(String msg ){
        Log.d(getString(R.string.SVC_TAG), msg);
    }

    /**
     * A constructor is required, and must call the super IntentService(String)
     * constructor with a name for the worker thread.
     */
    public HelloIntentService() {
        super("HelloIntentService");
    }

    /**
     * The IntentService calls this method from the default worker thread with
     * the intent that started the service. When this method returns, IntentService
     * stops the service, as appropriate.
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        String stringUrl = "http://www.voanews.com/mp3/voa/eap/mand/mand2200a.mp3";
        InputStream is = null;


        try {
            URL url = new URL(stringUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            logit( "The response is: " + response);
            is = conn.getInputStream();

            // save the input stream to a file
            writeToFile("/storage/sdcard0/Download/mand2200a.mp3", is);
            logit("Finished writing to file.");


            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch(IOException e){
                logit("could not close input stream");
            }
        }
        logit("in intent service");
    }

    private void writeToFile(String fileName, InputStream input){
        try {
            logit("Starting write to file.");
            File file = new File(fileName);
            OutputStream output = new FileOutputStream(file);
            try {
                try {
                    final byte[] buffer = new byte[BUFFER_SIZE];
                    int read;

                    while ((read = input.read(buffer)) != -1)
                        output.write(buffer, 0, read);

                    output.flush();
                } finally {
                    output.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }catch (IOException e){
        } finally {
            logit("i/o exception copying file " + fileName);
            try {
                input.close();
            } catch (IOException e){
                logit("I/o exception closing output file");
            }
        }
    }
}