package com.jcertif.reseaujcertif;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;
import com.jcertif.reseaujcertif.com.ParsingEvents;
import com.jcertif.reseaujcertif.com.ParsingUsers;
import com.jcertif.reseaujcertif.interfaces.ihm.Inscription;
import com.jcertif.reseaujcertif.interfaces.ihm.Principale;
import com.jcertif.reseaujcertif.interfaces.services.push.WakeLocker;
import com.jcertif.reseaujcertif.persistances.User;
import com.jcertif.reseaujcertif.services.JCertifApplication;
import com.jcertif.reseaujcertif.services.JCertifManager;
import com.jcertif.reseaujcertif.transvers.OutilsInternet;

public class Authentification extends Activity {
	
	private LinearLayout loading, authentification_menu;
	private EditText login, password;
	private ImageView logo;
	private CheckBox remember_me;
	private LinearLayout inscrire, connecter;
	private boolean isSelected;
	private JCertifManager jCertifManager;
	private JCertifApplication jCertifApplication;
	private String login_txt, password_txt;
	private Handler mHandler;
	private Timer titleTimer;
	private int btnSelected;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentification);       

        titleTimer = new Timer();
		mHandler = new Handler();
        
        jCertifManager = JCertifManager.getInstance();
        jCertifApplication =  (JCertifApplication) getApplicationContext();
        
		new AsyncTask<Void, Void, Void>(){

			@Override
			protected Void doInBackground(Void... arg0) {
				try{
					if(OutilsInternet.HttpTest(Authentification.this)){
						new ParsingUsers(jCertifManager).getUsers();
						jCertifManager.setParsingUsersFinish(true);
					}
				}catch(Exception e){}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {}	
			
		}.execute();
		
		
		new AsyncTask<Void, Void, Void>(){

			@Override
			protected Void doInBackground(Void... arg0) {
				try{
					if(OutilsInternet.HttpTest(Authentification.this))
						new ParsingEvents(jCertifManager).getAllEvents();
				}catch(Exception e){}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				if(OutilsInternet.HttpTest(Authentification.this))
					jCertifManager.setParsingEventFinish(true);
			}	
			
		}.execute();			
        
        isSelected = false;
        
        loading 				= (LinearLayout) findViewById(R.id.loading);
        authentification_menu 	= (LinearLayout) findViewById(R.id.authentification_menu);
        connecter 				= (LinearLayout) findViewById(R.id.connecter);
        inscrire 				= (LinearLayout) findViewById(R.id.inscrire);
        
        login 					= (EditText) findViewById(R.id.ville);
        password 				= (EditText) findViewById(R.id.password);
        
        remember_me 			= (CheckBox) findViewById(R.id.remember_me);
        
        logo					= (ImageView) findViewById(R.id.logo);

        remember_me.setChecked(true);
		
		if (jCertifApplication.mMemoryCache == null)
			jCertifApplication.mMemoryCache = new LruCache<String, Bitmap>(jCertifApplication.cacheSize);

		jCertifApplication.setmMemoryCache(jCertifApplication.mMemoryCache);
        
        login.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
            	login.setBackgroundResource(R.drawable.edit_text_selected);
            	password.setBackgroundResource(R.drawable.bg_btn1);
            }
        });
        
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
            	password.setBackgroundResource(R.drawable.edit_text_selected);
            	login.setBackgroundResource(R.drawable.bg_btn1);
            }
        });
        
        connecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	if(!isSelected){
            		isSelected = true;
            		enableConnecter();
            	}
            }
        });
        
        inscrire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	if(!isSelected){
            		isSelected = true;
            		enableInscrire();
            	}
            }
        });
        
        new Thread(){
        	@Override
        	public void run(){
        		
        		if(OutilsInternet.HttpTest(Authentification.this)){
        			String str = jCertifApplication.ReadDate("login.txt");
            		if(str.length()>1){
            			
            			Authentification.this.runOnUiThread(new Runnable() {
    	 					@Override public void run(){
		            			authentification_menu.setVisibility(View.GONE);
		            			loading.setVisibility(View.VISIBLE);
    	 					}
            			});
            			
            			try{
            				sleep(1500);
            			}catch(Exception e){}
            			
            			final String login = str.substring(0, str.indexOf("|"));
            			final String password = str.substring(str.indexOf("|")+1, str.length());
            			
            			Authentification.this.runOnUiThread(new Runnable() {
    	 					@Override public void run(){
    							User user = null;
    							try{
    								user = new ParsingUsers(jCertifManager).authentification(login, password);
    								
    								setAnnimationBouton1();
    								jCertifManager.setCurrentUser(user);
    								showWelcomMsg("Bienvenu "+user.getFirstname()+" "+user.getName());
    								Intent intent = new Intent(Authentification.this,Principale.class);
    								startActivityForResult(intent, 1000);
    								finish();
    								
    							}catch(Exception e){
    								showWelcomMsg("Problème de connexion");
    							}       							
    	 					}
    					});
            			
            		}else{
            			try{
                			sleep(1400);
                		}catch(Exception e){}
                		
                		Authentification.this.runOnUiThread(new Runnable() {
         					@Override public void run(){
         						authentification_menu.setVisibility(View.VISIBLE);
         						logo.setVisibility(View.VISIBLE);
        		        		
        		        		Animation a = AnimationUtils.loadAnimation(Authentification.this, R.anim.alpha);
        		                a.reset();
        		                
        		                authentification_menu.clearAnimation();
        		                authentification_menu.startAnimation(a);
        		                
        		                logo.clearAnimation();
        		                logo.startAnimation(a);
         					}
                		});
            		}
        		}else{
            		Authentification.this.runOnUiThread(new Runnable() {
     					@Override public void run(){
     						showWelcomMsg(getResources().getString(R.string.prob_connexion));
     					}
            		});
        		}   
        	}
        }.start(); 
        
    }
    
	@Override
	protected void onDestroy() {
		try {
			unregisterReceiver(mHandleMessageReceiver);
			GCMRegistrar.onDestroy(this);
		} catch (Exception e) {
			Log.e("UnRegister Receiver Error", "> " + e.getMessage());
		}
		super.onDestroy();
	}
    
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString("message");

			WakeLocker.acquire(getApplicationContext());
			
			Toast.makeText(getApplicationContext(), "New Message: " + newMessage, Toast.LENGTH_LONG).show();
	
			WakeLocker.release();
		}
	};

	private void enableInscrire(){
		inscrire.setBackgroundResource(R.drawable.btn_on);
		btnSelected = 1;
		refreshTimer();
	}
	
	private void enableConnecter(){
		connecter.setBackgroundResource(R.drawable.btn_on);
		btnSelected = 2;
		refreshTimer();
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
	
	final Runnable mUpdateResults = new Runnable() {
		 public void run() {
			 switch(btnSelected){
			 case 1:
				 desableInscription();
				 break;
			 case 2:
				 desableConnecter();
				 break;
			 }
			 
		 }
	};
	
	private void desableConnecter(){
		connecter.setBackgroundResource(R.drawable.btn_off);
		authentification();
	}
	 
	private void desableInscription(){
		inscrire.setBackgroundResource(R.drawable.btn_off);
		Intent intent = new Intent(Authentification.this,Inscription.class);
		startActivityForResult(intent, 1000);
		finish();
	}
    
	private void showWelcomMsg(String msg) {
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.toast_custom_layout, (ViewGroup) findViewById(R.id.toast_layout_root));

		TextView text = (TextView) layout.findViewById(R.id.text);
		text.setText(msg);

		Toast toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();
	}
	
	private void setAnnimationBouton1(){
    	
		loading.setVisibility(View.VISIBLE);
		authentification_menu.setVisibility(View.GONE);
		logo.setVisibility(View.GONE);
    	
    	Animation a = AnimationUtils.loadAnimation(this, R.anim.alpha);
        a.reset();
        
        loading.clearAnimation();
        loading.startAnimation(a); 
        
        logo.clearAnimation();
        logo.startAnimation(a); 
        
    }

	protected void authentification() {

		login_txt = login.getText().toString();
		password_txt = password.getText().toString();
		
		if(login_txt.length()==0 || password_txt.length()==0){
			showWelcomMsg("Veuillez remplire tout les champs");
			isSelected = false;
		}else{
	
			new AsyncTask<Void, Void, Void>(){

				@Override
				protected Void doInBackground(Void... arg0) {
					return null;
				}

				@Override
				protected void onPostExecute(Void result) {
					Authentification.this.runOnUiThread(new Runnable() {
	 					@Override public void run(){
							User user = null;
							
							try{
								user = new ParsingUsers(jCertifManager).authentification(login_txt, password_txt);
							}catch(Exception e){
								showWelcomMsg("Problème de connexion");
							}
							
							if(user!=null){
								
								jCertifManager.setCurrentUser(user);
								
								if(remember_me.isChecked())
									jCertifApplication.SaveDate(login_txt+"|"+password_txt, "login.txt");
								
								showWelcomMsg("Bienvenu "+user.getFirstname());
								Intent intent = new Intent(Authentification.this,Principale.class);
								startActivityForResult(intent, 1000);
								finish();
							}else{
								showWelcomMsg("Login et/ou mot de passe incorrecte");
							}
							isSelected = false;		
	 					}
					});
				}	
				
			}.execute();
		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==1000){
			finish();
		}
	}
    
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			exitApp();
			return true;
		}
		return false;
	}

	private void exitApp(){
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();

		alertDialog.setTitle("Quitter");
		alertDialog.setMessage("Est ce que vous êtes sur ?");
		alertDialog.setButton("Oui", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				System.exit(0);
				finish();
				return;
			} }); 
		alertDialog.setButton2("Non", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				return;
			}});


		alertDialog.show();
	}
}
