package com.jcertif.reseaujcertif.interfaces.ihm;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcertif.reseaujcertif.R;
import com.jcertif.reseaujcertif.services.JCertifApplication;
import com.jcertif.reseaujcertif.services.JCertifManager;

public class ProfilMemebre extends Activity {
	
    private JCertifManager jCertifManager;
	private JCertifApplication jCertifApplication;
    private TextView date_naissance, name, ville, payes, mail_txt;
    private WebView web;
    private RelativeLayout img_profil;
    private LinearLayout send_email;
    private ImageView return_img;
    private boolean isSelected = false;
	private Handler mHandler;
	private Timer titleTimer;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil_memenbre);
        
        titleTimer = new Timer();
		mHandler = new Handler();
        
        jCertifManager = JCertifManager.getInstance();
		jCertifApplication =  (JCertifApplication) getApplicationContext();
        
	    send_email	 	= (LinearLayout) findViewById(R.id.send_email);
	    return_img   	= (ImageView) findViewById(R.id.return_img);
	    img_profil   	= (RelativeLayout) findViewById(R.id.img_profil);
	    
	    web 			= (WebView)	findViewById(R.id.web);
	    
	    date_naissance	= (TextView) findViewById(R.id.date_naissance);
	    name	 		= (TextView) findViewById(R.id.name);
	    ville	 		= (TextView) findViewById(R.id.ville);
	    payes	 		= (TextView) findViewById(R.id.payes);
	    mail_txt		= (TextView) findViewById(R.id.mail_txt);
	    
	    date_naissance.setSelected(true);
	    name.setSelected(true);
	    ville.setSelected(true);
	    payes.setSelected(true);
       
	    setInfosUser();
	    
        send_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	if(!isSelected){
            		isSelected = true;
            		sendEmail();
            		isSelected = false;
            	}
            }
        });

        return_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	if(!isSelected)
            		enableReturn();
            }
        });
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
        	Intent intent = new Intent(ProfilMemebre.this, Network.class);
			startActivityForResult(intent, 1000);
            return true;
        }
        
        return super.onKeyDown(keyCode, event);
    }

	protected void sendEmail() {
		String to = jCertifManager.getSelectedUser().getEmail();
		String subject = "";
		String message = "";
				
		Intent email = new Intent(Intent.ACTION_SEND);
		email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
		email.putExtra(Intent.EXTRA_SUBJECT, subject);
		email.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(message));

		email.setType("message/rfc822");

		startActivity(Intent.createChooser(email, "Veuillez selectionner un client Email :"));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==1000){
			finish();
		}
	}
	
    private void setInfosUser(){
    	try{
    		
    		if(!jCertifManager.getSelectedUser().getDate_naissance().equals("0000-00-00"))
        		date_naissance.setText(jCertifManager.getSelectedUser().getDate_naissance());
        	else
        		date_naissance.setVisibility(View.GONE);
        	
        	if(jCertifManager.getSelectedUser().getVille().length()>0)
        		ville.setText("Ville : "+jCertifManager.getSelectedUser().getVille());
        	else
        		ville.setVisibility(View.GONE);
        	
        	if(jCertifManager.getSelectedUser().getPayes().length()>0)
            	payes.setText("Payes : "+jCertifManager.getSelectedUser().getPayes());
        	else
        		payes.setVisibility(View.GONE);
        	
        	mail_txt.setText("Envoyer un mail à "+jCertifManager.getSelectedUser().getFirstname());
    		
	    	name.setText(jCertifManager.getSelectedUser().getFirstname()+" "+jCertifManager.getSelectedUser().getName());
	    	
	    	String text = "<html><head></head><body><p align=\"justify\"><font size='2'>" + jCertifManager.getSelectedUser().getDescription() +"</font></p>";
	    	web.loadDataWithBaseURL(text, text, "text/html", "utf-8", "");
	    	web.setWebViewClient(new WebViewClient());
			web.getSettings().setLoadWithOverviewMode(true);
			web.setBackgroundColor(0);
			web.setBackgroundColor(0x00000000);
    	}catch(Exception e){
    		
    	}
		
		new AsyncTask<String, Long, Bitmap>() {
			protected void onPostExecute(Bitmap result) {
				if(result != null){
					BitmapDrawable bitmapDrawable = new BitmapDrawable(result);
					img_profil.setBackgroundDrawable(bitmapDrawable);
				}
				this.cancel(true);
			}
			@Override
			protected Bitmap doInBackground(String... params) { 
				return jCertifApplication.ImageOperations(new com.jcertif.reseaujcertif.services.Parametres().getImgULR(jCertifManager.getSelectedUser().getPhoto_url()));    
			}
		}.execute("");
		
    }
    
    void refreshTimer(){
		stopTimer();
		startTitleTimeOut(750);
	}
	
	void stopTimer(){
		titleTimer.cancel(); 
		titleTimer.purge();
		titleTimer = null;
	}
	
	void startTitleTimeOut (int timeOut){
		
		titleTimer = new Timer();
		TimerTask titleTimerTask = new TimerTask(){
			public void run(){	
				mHandler.post(mUpdateResults);
			}
		};

		titleTimer.schedule(titleTimerTask, timeOut);
	}
	
	private int btnSelected;
	
	final Runnable mUpdateResults = new Runnable() {
		 public void run() {
			 switch(btnSelected){
			 case 1:
				 desableReturn();
				 break;
			 }		 
		 }
	};
	
	protected void enableReturn() {
		isSelected = true;
		return_img.setBackgroundResource(R.drawable.return_on);
		btnSelected = 1;
		refreshTimer();
	}
	
	private void desableReturn(){
		return_img.setBackgroundResource(R.drawable.return_off);
		Intent intent = new Intent(ProfilMemebre.this, Network.class);
		startActivityForResult(intent, 1000);
	}
    
}
