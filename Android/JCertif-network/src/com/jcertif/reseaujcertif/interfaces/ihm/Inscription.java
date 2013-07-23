package com.jcertif.reseaujcertif.interfaces.ihm;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;
import com.jcertif.reseaujcertif.Authentification;
import com.jcertif.reseaujcertif.R;
import com.jcertif.reseaujcertif.com.ParsingUsers;
import com.jcertif.reseaujcertif.interfaces.services.push.WakeLocker;
import com.jcertif.reseaujcertif.persistances.User;
import com.jcertif.reseaujcertif.services.JCertifApplication;
import com.jcertif.reseaujcertif.services.JCertifManager;
import com.jcertif.reseaujcertif.services.MyLocation;
import com.jcertif.reseaujcertif.services.MyLocation.LocationResult;

public class Inscription extends Activity {
	
	private TextView login_txt, password_txt, name_txt, firstname_txt, berth_day_txt, mail_txt, payes_txt, ville_txt, presentation_txt, inscription;
	private EditText login, password, name, firstname, berth_day, mail, payes, ville, presentation ;
	private LinearLayout date, loading;
	private ImageView return_icon;
	private RelativeLayout data_bg;
	private boolean isSelected, getLocalisation, dateVisible, reponse;
	private Button valider;
	private Handler mHandler;
	private Timer titleTimer;
	private User user;
	private double lat, lng;
	private WheelView month, year, day;
	private int curYear, curMonth;
	private JCertifApplication jCertifApplication;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscription);
        
        jCertifApplication =  (JCertifApplication) getApplicationContext();
        
        titleTimer = new Timer();
		mHandler = new Handler();
		
		getLocalisation = false;
		
		LocationResult locationResult = new LocationResult(){
		    @Override
		    public void gotLocation(Location location){
		    	lat = location.getLatitude();
		    	lng = location.getLongitude();
		    	
		    	getLocalisation = true;
		    }
		};
		
		MyLocation myLocation = new MyLocation();
		myLocation.getLocation(this, locationResult);
        
		return_icon 	 = (ImageView) findViewById(R.id.return_icon);
		
        date 			 = (LinearLayout) findViewById(R.id.date);
        loading		 	 = (LinearLayout) findViewById(R.id.loading);
        
        valider		 	 = (Button) findViewById(R.id.valider);
        
        data_bg		 	 = (RelativeLayout) findViewById(R.id.data_bg);
        
        login_txt 		 = (TextView) findViewById(R.id.login_txt);
        password_txt 	 = (TextView) findViewById(R.id.password_txt);
        name_txt 		 = (TextView) findViewById(R.id.name_txt);
        firstname_txt 	 = (TextView) findViewById(R.id.firstname_txt);
        berth_day_txt 	 = (TextView) findViewById(R.id.berth_day_txt);
        mail_txt 		 = (TextView) findViewById(R.id.mail_txt);
        payes_txt 		 = (TextView) findViewById(R.id.payes_txt);
        ville_txt 		 = (TextView) findViewById(R.id.ville_txt);
        presentation_txt = (TextView) findViewById(R.id.presentation_txt);
        inscription 	 = (TextView) findViewById(R.id.inscription);
        
        login 	  		 = (EditText) findViewById(R.id.login);
        password 	  	 = (EditText) findViewById(R.id.password);
        name 	  		 = (EditText) findViewById(R.id.name);
        firstname 	  	 = (EditText) findViewById(R.id.firstname);
        berth_day 	  	 = (EditText) findViewById(R.id.berth_day);
        mail 	  		 = (EditText) findViewById(R.id.mail);
        payes 			 = (EditText) findViewById(R.id.payes);
        ville 			 = (EditText) findViewById(R.id.ville);
        presentation 	 = (EditText) findViewById(R.id.presentation);
        
        month 			 = (WheelView) findViewById(R.id.month);
        year 			 = (WheelView) findViewById(R.id.year);
        day 			 = (WheelView) findViewById(R.id.day);
        
        login_txt.setSelected(true);
        password_txt.setSelected(true);
        name_txt.setSelected(true);
        firstname_txt.setSelected(true);
        berth_day_txt.setSelected(true);
        mail_txt.setSelected(true);
        payes_txt.setSelected(true);
        ville_txt.setSelected(true);
        presentation_txt.setSelected(true); 
        
        isSelected = false;
        dateVisible = false;
        
        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	if(!isSelected){
            		isSelected = true;
            		enableInscrire();
            	}
            }
        });
        
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	if(!isSelected){
            		isSelected = true;
            		setDateInvisible();
            		berth_day.setText((day.getCurrentItem()+1)+"-"+month.getCurrentItem()+"-"+(curYear-60+year.getCurrentItem()));
            		isSelected = false;
            	}
            }
        });
        
        return_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	if(!isSelected)
            		enableReturn();
            }
        });
        
        setLestenerEditText();
        
        setDate();
        
        new Thread(){
        	@Override
        	public void run(){
        		try{
        			sleep(1000);
        		}catch(Exception e){ }
        		
        		Inscription.this.runOnUiThread(new Runnable() {
 					@Override public void run(){
 						setBodyVisible();
 					}
        		});
        		
        	}
        }.start();
        
    }
    
	protected void returnMenuPrincipal() {
		
		new Thread(){
			@Override
			public void run(){
				Inscription.this.runOnUiThread(new Runnable() {
					@Override public void run(){
						setBodyInvisible();
					}
				});
				
				try{
					sleep(2000);
				}catch(Exception e){}
				
				Intent intent = new Intent(Inscription.this, Authentification.class);            		           		
        		startActivityForResult(intent, 1000);
        		finish();
				
			}
		}.start();
			
	}

	private void setBodyVisible(){
    	
    	Animation animation = new TranslateAnimation(0, 0, 1200, 0);
    	animation.setDuration(2000);
    	animation.setFillAfter(true);
    	data_bg.startAnimation(animation);
    	data_bg.setVisibility(View.VISIBLE);
    	loading.setVisibility(View.GONE);

    }
    
    private void setBodyInvisible(){
    	Animation animation = new TranslateAnimation(0, 0, 0, 1200);
    	animation.setDuration(2000);
    	animation.setFillAfter(true);
    	data_bg.startAnimation(animation);
    	data_bg.setVisibility(View.GONE);
    	loading.setVisibility(View.VISIBLE);
    }
    
	private void setDateVisible(){
		dateVisible = true;
//    	Animation animation = new TranslateAnimation(0, 0, 300, 0);
//    	animation.setDuration(1000);
//    	animation.setFillAfter(true);
//    	date.startAnimation(animation);
    	date.setVisibility(View.VISIBLE);

    }
    
    private void setDateInvisible(){
    	dateVisible = false;
//    	Animation animation = new TranslateAnimation(0, 0, 0, 300);
//    	animation.setDuration(1000);
//    	animation.setFillAfter(true);
//    	date.startAnimation(animation);
    	date.setVisibility(View.GONE);
    	
    }
    
    private void setDate(){
    	Calendar calendar = Calendar.getInstance();

        OnWheelChangedListener listener = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updateDays(year, month, day);
            }
        };

        // month
        curMonth = calendar.get(Calendar.MONTH);       
        month.setViewAdapter(new DateArrayAdapter(this, jCertifApplication.months, curMonth));
        month.setCurrentItem(curMonth);
        month.addChangingListener(listener);
    
        // year
        curYear = calendar.get(Calendar.YEAR);
        year.setViewAdapter(new DateNumericAdapter(this, curYear-60, curYear, 0));
        year.setCurrentItem(curYear);
        year.addChangingListener(listener);
        
        //day
        updateDays(year, month, day);
        day.setCurrentItem(calendar.get(Calendar.DAY_OF_MONTH) - 1);
    }

	private void setLestenerEditText() {
        login.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
            	login.setBackgroundResource(R.drawable.edit_text_selected);
            	password.setBackgroundResource(R.drawable.bg_btn1);
            	name.setBackgroundResource(R.drawable.bg_btn1);
            	firstname.setBackgroundResource(R.drawable.bg_btn1);
            	berth_day.setBackgroundResource(R.drawable.bg_btn1);
            	mail.setBackgroundResource(R.drawable.bg_btn1);
            	payes.setBackgroundResource(R.drawable.bg_btn1);
            	ville.setBackgroundResource(R.drawable.bg_btn1);
            	
            	if(dateVisible)
            		setDateInvisible();
            }
        });
        
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
            	login.setBackgroundResource(R.drawable.bg_btn1);
            	password.setBackgroundResource(R.drawable.edit_text_selected);
            	name.setBackgroundResource(R.drawable.bg_btn1);
            	firstname.setBackgroundResource(R.drawable.bg_btn1);
            	berth_day.setBackgroundResource(R.drawable.bg_btn1);
            	mail.setBackgroundResource(R.drawable.bg_btn1);
            	payes.setBackgroundResource(R.drawable.bg_btn1);
            	ville.setBackgroundResource(R.drawable.bg_btn1);
            	
            	if(dateVisible)
            		setDateInvisible();
            }
        });
        
        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
            	login.setBackgroundResource(R.drawable.bg_btn1);
            	password.setBackgroundResource(R.drawable.bg_btn1);
            	name.setBackgroundResource(R.drawable.edit_text_selected);
            	firstname.setBackgroundResource(R.drawable.bg_btn1);
            	berth_day.setBackgroundResource(R.drawable.bg_btn1);
            	mail.setBackgroundResource(R.drawable.bg_btn1);
            	payes.setBackgroundResource(R.drawable.bg_btn1);
            	ville.setBackgroundResource(R.drawable.bg_btn1);
            	
            	if(dateVisible)
            		setDateInvisible();
            }
        });
        
        firstname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
            	login.setBackgroundResource(R.drawable.bg_btn1);
            	password.setBackgroundResource(R.drawable.bg_btn1);
            	name.setBackgroundResource(R.drawable.bg_btn1);
            	firstname.setBackgroundResource(R.drawable.edit_text_selected);
            	berth_day.setBackgroundResource(R.drawable.bg_btn1);
            	mail.setBackgroundResource(R.drawable.bg_btn1);
            	payes.setBackgroundResource(R.drawable.bg_btn1);
            	ville.setBackgroundResource(R.drawable.bg_btn1);
            	
            	if(dateVisible)
            		setDateInvisible();
            }
        });
        
        berth_day.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
            	login.setBackgroundResource(R.drawable.bg_btn1);
            	password.setBackgroundResource(R.drawable.bg_btn1);
            	name.setBackgroundResource(R.drawable.bg_btn1);
            	firstname.setBackgroundResource(R.drawable.bg_btn1);
            	berth_day.setBackgroundResource(R.drawable.edit_text_selected);
            	mail.setBackgroundResource(R.drawable.bg_btn1);
            	payes.setBackgroundResource(R.drawable.bg_btn1);
            	ville.setBackgroundResource(R.drawable.bg_btn1);
            	
            	InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            	imm.hideSoftInputFromWindow(berth_day.getWindowToken(), 0);
            	
            	setDateVisible();
            }
        });
        
        mail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
            	login.setBackgroundResource(R.drawable.bg_btn1);
            	password.setBackgroundResource(R.drawable.bg_btn1);
            	name.setBackgroundResource(R.drawable.bg_btn1);
            	firstname.setBackgroundResource(R.drawable.bg_btn1);
            	berth_day.setBackgroundResource(R.drawable.bg_btn1);
            	mail.setBackgroundResource(R.drawable.edit_text_selected);
            	payes.setBackgroundResource(R.drawable.bg_btn1);
            	ville.setBackgroundResource(R.drawable.bg_btn1);
            	
            	if(dateVisible)
            		setDateInvisible();
            }
        });
        
        payes.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
            	login.setBackgroundResource(R.drawable.bg_btn1);
            	password.setBackgroundResource(R.drawable.bg_btn1);
            	name.setBackgroundResource(R.drawable.bg_btn1);
            	firstname.setBackgroundResource(R.drawable.bg_btn1);
            	berth_day.setBackgroundResource(R.drawable.bg_btn1);
            	mail.setBackgroundResource(R.drawable.bg_btn1);
            	payes.setBackgroundResource(R.drawable.edit_text_selected);
            	ville.setBackgroundResource(R.drawable.bg_btn1);
            	
            	if(dateVisible)
            		setDateInvisible();
            }
        });
        
        ville.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
            	login.setBackgroundResource(R.drawable.bg_btn1);
            	password.setBackgroundResource(R.drawable.bg_btn1);
            	name.setBackgroundResource(R.drawable.bg_btn1);
            	firstname.setBackgroundResource(R.drawable.bg_btn1);
            	berth_day.setBackgroundResource(R.drawable.bg_btn1);
            	mail.setBackgroundResource(R.drawable.bg_btn1);
            	payes.setBackgroundResource(R.drawable.bg_btn1);
            	ville.setBackgroundResource(R.drawable.edit_text_selected);
            	
            	if(dateVisible)
            		setDateInvisible();
            }
        });
        
        presentation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
            	login.setBackgroundResource(R.drawable.bg_btn1);
            	password.setBackgroundResource(R.drawable.bg_btn1);
            	name.setBackgroundResource(R.drawable.bg_btn1);
            	firstname.setBackgroundResource(R.drawable.bg_btn1);
            	berth_day.setBackgroundResource(R.drawable.bg_btn1);
            	mail.setBackgroundResource(R.drawable.bg_btn1);
            	payes.setBackgroundResource(R.drawable.bg_btn1);
            	ville.setBackgroundResource(R.drawable.bg_btn1);
            	
            	if(dateVisible)
            		setDateInvisible();
            }
        });
	}
	
	public int btnSelected;
	
	protected void enableReturn() {
		isSelected = true;
		return_icon.setBackgroundResource(R.drawable.return_on);
		btnSelected = 2;
		refreshTimer();
	}
	
	private void desableReturn(){
		return_icon.setBackgroundResource(R.drawable.return_off);
		returnMenuPrincipal();
		isSelected = false;
	}

	private void enableInscrire(){
		isSelected = true;
		btnSelected = 1;
		inscription.setBackgroundResource(R.drawable.btn_on);
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
				 desableReturn();
				 break;
			 }			 
		 }
	};
	
	private void desableInscription(){
		inscription.setBackgroundResource(R.drawable.btn_off);
		inscription();
		isSelected = false;
	}

	private void inscription() {
		
		if(login.getText().toString().length()==0 || password.getText().toString().length()==0){
			showWelcomMsg("Veuillez saisir le login et le mot de passe");
		}else{
			if((mail.getText().toString().length()>0 && mail.getText().toString().contains("@")) || (mail.getText().toString().length()==0)){

				user = new User();
				user.setAdmin(false);
				user.setLogin(login.getText().toString());
				user.setPassword(password.getText().toString());
				user.setName(name.getText().toString());
				user.setFirstname(firstname.getText().toString());
				user.setDate_naissance(berth_day.getText().toString());
				user.setEmail(mail.getText().toString());
				user.setTel("");
				user.setPayes(payes.getText().toString());
				user.setVille(ville.getText().toString());
				user.setDescription(presentation.getText().toString());			
				user.setStatus(true);
//				user.setPhoto_url("img");
				
		        GCMRegistrar.checkDevice(this);
		        GCMRegistrar.checkManifest(this);
		        registerReceiver(mHandleMessageReceiver, new IntentFilter(jCertifApplication.DISPLAY_MESSAGE_ACTION));
		        String regId = GCMRegistrar.getRegistrationId(this);
		     	if (regId.equals("")) {
		     		GCMRegistrar.register(this, jCertifApplication.SENDER_ID);
		     		regId = GCMRegistrar.getRegistrationId(this);
		     	}
		        
				user.setGcm_regid(regId);
				
				while(!getLocalisation){}
				
				user.setLongitude(lng+"");
				user.setLatitude(lat+"");
				
				AlertDialog alertDialog = new AlertDialog.Builder(this).create();

				alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
				alertDialog.setTitle("Créer un nouveau compte");	
				alertDialog.setMessage("Est ce que vous êtes sûr ?");
				alertDialog.setButton("Oui", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
											
						setBodyInvisible();
						
						new Thread(){
							@Override
							public void run(){
								
								reponse = new ParsingUsers(JCertifManager.getInstance()).addUser(user);
								try{
									sleep(2000);
								}catch(Exception e){}
								
								Inscription.this.runOnUiThread(new Runnable() {
									@Override public void run(){
										saveModification(user, reponse);
										setBodyVisible();
									}
								});
								
							}
						}.start();
		
						dialog.dismiss();
						
						return;
					} }); 
				alertDialog.setButton2("Non", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						return;
					}});


				alertDialog.show();
				
			}else{
				showWelcomMsg("Votre mail n'est pas valide");
			}
	
		}
		

		isSelected = false;
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
			// Waking up mobile if it is sleeping
			WakeLocker.acquire(getApplicationContext());
			
			/**
			 * Take appropriate action on this message
			 * depending upon your app requirement
			 * For now i am just displaying it on the screen
			 * */
			
			// Showing received message		
			Toast.makeText(getApplicationContext(), "New Message: " + newMessage, Toast.LENGTH_LONG).show();
			
			// Releasing wake lock
			WakeLocker.release();
		}
	};
	
	public void saveModification(User user, boolean rep){
		
		if(rep){
			showWelcomMsg("Membre ajouté avec succés");
			Intent intent = new Intent(Inscription.this, Authentification.class);
    		startActivityForResult(intent, 1000);
    		finish();
		}else{
			showWelcomMsg("Problème lors de l'ajout d'un utilsiateur");
			
		}
	}
	
	private void showWelcomMsg(String msg) {
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.toast_custom_layout, (ViewGroup) findViewById(R.id.toast_layout_root));

		TextView text = (TextView) layout.findViewById(R.id.text);
		text.setText(msg);

		Toast toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		toast.show();
	}
    
	 /**
     * Updates day wheel. Sets max days according to selected month and year
     */
    void updateDays(WheelView year, WheelView month, WheelView day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + year.getCurrentItem());
        calendar.set(Calendar.MONTH, month.getCurrentItem());
        
        int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        day.setViewAdapter(new DateNumericAdapter(this, 1, maxDays, calendar.get(Calendar.DAY_OF_MONTH) - 1));
        int curDay = Math.min(maxDays, day.getCurrentItem() + 1);
        day.setCurrentItem(curDay - 1, true);
    }
    
    /**
     * Adapter for numeric wheels. Highlights the current value.
     */
    private class DateNumericAdapter extends NumericWheelAdapter {
        // Index of current item
        int currentItem;
        // Index of item to be highlighted
        int currentValue;
        
        /**
         * Constructor
         */
        public DateNumericAdapter(Context context, int minValue, int maxValue, int current) {
            super(context, minValue, maxValue);
            this.currentValue = current;
            setTextSize(16);
        }
        
        @Override
        protected void configureTextView(TextView view) {
            super.configureTextView(view);
            if (currentItem == currentValue) {
                view.setTextColor(0xFF0000F0);
            }
            view.setTypeface(Typeface.SANS_SERIF);
        }
        
        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            currentItem = index;
            return super.getItem(index, cachedView, parent);
        }
    }
    
    /**
     * Adapter for string based wheel. Highlights the current value.
     */
    private class DateArrayAdapter extends ArrayWheelAdapter<String> {
        // Index of current item
        int currentItem;
        // Index of item to be highlighted
        int currentValue;
        
        /**
         * Constructor
         */
        public DateArrayAdapter(Context context, String[] items, int current) {
            super(context, items);
            this.currentValue = current;
            setTextSize(16);
        }
        
        @Override
        protected void configureTextView(TextView view) {
            super.configureTextView(view);
            if (currentItem == currentValue) {
                view.setTextColor(0xFF0000F0);
            }
            view.setTypeface(Typeface.SANS_SERIF);
        }
        
        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            currentItem = index;
            return super.getItem(index, cachedView, parent);
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
		alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		alertDialog.setTitle("Quitter");
		alertDialog.setMessage("Est ce que vous êtes sûr ?");
		alertDialog.setButton("Oui", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				System.exit(0);

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
