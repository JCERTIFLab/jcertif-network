package com.jcertif.reseaujcertif;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMConstants;
import com.jcertif.reseaujcertif.dao.DatabaseHandler;
import com.jcertif.reseaujcertif.persistances.Msg;
import com.jcertif.reseaujcertif.services.JCertifManager;

@SuppressLint("SimpleDateFormat")
public class GCMIntentService extends GCMBaseIntentService {

     private static final String TAG = "Push Notification Demo GCMIntentService";
     
     public GCMIntentService() {
         super("853948940057");
     }


    @Override
    protected void onError(Context context, String errorId) {

        if(GCMConstants.ERROR_ACCOUNT_MISSING.equalsIgnoreCase(errorId)) {
            Log.v(TAG, "Error Account Missing");
        } else if(GCMConstants.ERROR_AUTHENTICATION_FAILED.equalsIgnoreCase(errorId)) {
            Log.v(TAG, "Error Authentication Failed");
        } else if(GCMConstants.ERROR_INVALID_PARAMETERS.equalsIgnoreCase(errorId)) {
            Log.v(TAG, "Error Invalid Parameters");
        } else if(GCMConstants.ERROR_INVALID_SENDER.equalsIgnoreCase(errorId)) {
            Log.v(TAG, "Error Invalid Sender");
        } else if(GCMConstants.ERROR_PHONE_REGISTRATION_ERROR.equalsIgnoreCase(errorId)) {
            Log.v(TAG, "Error Phone Registration Error");
        } else if(GCMConstants.ERROR_SERVICE_NOT_AVAILABLE.equalsIgnoreCase(errorId)) {
            Log.v(TAG, "Error Service Not Available");
        } 
    }
    
    @Override
    protected void onRegistered(Context context, String regId) {

        Log.v(TAG, "Successfull Registration : "+regId);
    }

    @Override
    protected void onUnregistered(Context context, String regId) {

        Log.v(TAG, "Successfully Unregistred : "+regId);
    }

    @Override
    protected String[] getSenderIds(Context context) {
        return super.getSenderIds(context);
    }
    
    /**
     * Method called on receiving a deleted message
     * */
    @Override
    protected void onDeletedMessages(Context context, int total) {
        Log.i(TAG, "Received deleted messages notification");
        String message = getString(R.string.gcm_deleted, total);
        displayMessage(context, message);
        // notifies user
        generateNotification(context, message);
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        // log message
        Log.i(TAG, "Received recoverable error: " + errorId);
        displayMessage(context, getString(R.string.gcm_recoverable_error, errorId));
        return super.onRecoverableError(context, errorId);
    }

	public void displayMessage(Context context, String message) {
		Intent intent = new Intent("com.jcertif.reseaujcertif.DISPLAY_MESSAGE");
	    intent.putExtra("message", message);
	    context.sendBroadcast(intent);
	}
    
    /**
     * Method called on Receiving a new message
     * */
    @Override
    protected void onMessage(Context context, Intent intent) {
    	
        String message = intent.getExtras().getString("price"); 
        String sender_id = intent.getExtras().getString("sender_id"); 

        if(sender_id != null){
    		
    		generateNotification(context, message);
    		
        	JCertifManager jCertifManager = JCertifManager.getInstance();
            
            Msg msg = new Msg();
            msg.setUser(jCertifManager.getCurrentUser().getId()+"");
            msg.setId_Sender(sender_id);
            msg.setId_Resiver(jCertifManager.getCurrentUser().getGcm_regid());
            msg.setMsg(message);
            
            Date date = new Date();
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");  
            msg.setDate(df.format(date));
            
            new DatabaseHandler(context).addMessage(msg);
            
            jCertifManager.recevedNewMsg = true;
    
        }else
        	generateNotification2(context, message);
            
    }
    
    private static void generateNotification(Context context, String message) {
        
    	int icon = R.drawable.notification;
        long when = System.currentTimeMillis();
       
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(icon, message, when);
        
        Intent notificationIntent = new Intent(context, Authentification.class);
        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, context.getString(R.string.app_name), "Nouveau message : "+message, intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        
        // Play default notification sound
        notification.defaults |= Notification.DEFAULT_SOUND;
        
        //notification.sound = Uri.parse("android.resource://" + context.getPackageName() + "your_sound_file_name.mp3");
        
        // Vibrate if vibrate is enabled
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(0, notification);      

    }
    
    private static void generateNotification2(Context context, String message) {
               
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.drawable.push_notification, message, System.currentTimeMillis());
        
        Intent notificationIntent = new Intent(context, Authentification.class);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, context.getString(R.string.app_name), message, intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        
        notification.defaults |= Notification.DEFAULT_SOUND;
        
        //notification.sound = Uri.parse("android.resource://" + context.getPackageName() + "your_sound_file_name.mp3");
        
        // Vibrate if vibrate is enabled
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(0, notification);      

    }
    
}