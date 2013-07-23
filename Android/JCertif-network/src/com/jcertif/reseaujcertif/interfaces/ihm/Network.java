package com.jcertif.reseaujcertif.interfaces.ihm;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import mapviewballoons.example.custom.CustomItemizedOverlay;
import mapviewballoons.example.custom.CustomOverlayItem;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.jcertif.reseaujcertif.R;
import com.jcertif.reseaujcertif.com.ParsingUsers;
import com.jcertif.reseaujcertif.persistances.User;
import com.jcertif.reseaujcertif.services.JCertifApplication;
import com.jcertif.reseaujcertif.services.JCertifManager;
import com.jcertif.reseaujcertif.services.Parametres;
import com.jcertif.reseaujcertif.transvers.AnimationFactory;
import com.jcertif.reseaujcertif.transvers.AnimationFactory.FlipDirection;


public class Network extends MapActivity {
	
	private MapView mapView;
	private List<Overlay> mapOverlays;
	private Drawable drawable;
	private GeoPoint point;
	private CustomItemizedOverlay<CustomOverlayItem> itemizedOverlay;
	private ArrayList<User> listUSERSelected, listUers;
	private JCertifManager jCertifManager;
	private JCertifApplication jCertifApplication;
	private LinearLayout map, liste, body, description, loading, send_email, remouve, list_memebre;
	private EditText search_txt;
	private ImageView return_page;
	private RelativeLayout img_profil;
	private ListView list;
	private ViewFlipper viewFlipper;
	private int mCurrentLayoutState;
	private TextView date_naissance, name, ville, payes, mail_txt;
    private WebView web;
    private User selectedUser;
    private boolean isSelected;
    private int niveau, btnSelected;
    private Handler mHandler;
	private Timer titleTimer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);	
		
		jCertifManager = JCertifManager.getInstance();
        
		mapView 	 = (MapView) findViewById(R.id.mapview);
		
		map			 = (LinearLayout) findViewById(R.id.map);
		liste		 = (LinearLayout) findViewById(R.id.liste);
		body		 = (LinearLayout) findViewById(R.id.body);
		loading		 = (LinearLayout) findViewById(R.id.loading);
		description  = (LinearLayout) findViewById(R.id.description);
		send_email	 = (LinearLayout) findViewById(R.id.send_email);
		remouve		 = (LinearLayout) findViewById(R.id.remouve);
		list_memebre = (LinearLayout) findViewById(R.id.list_memebre);
		
		img_profil 	 = (RelativeLayout) findViewById(R.id.img_profil);
		
		viewFlipper  = (ViewFlipper) findViewById(R.id.viewFlipper);
		
		list		 = (ListView) findViewById(R.id.list);
		
		search_txt	 = (EditText) findViewById(R.id.search_txt);

		return_page  = (ImageView) findViewById(R.id.return_page);

	    web 			= (WebView)	findViewById(R.id.web);
	    
	    date_naissance	= (TextView) findViewById(R.id.date_naissance);
	    name	 		= (TextView) findViewById(R.id.name);
	    ville	 		= (TextView) findViewById(R.id.ville);
	    payes	 		= (TextView) findViewById(R.id.payes);
	    mail_txt 		= (TextView) findViewById(R.id.mail_txt);
	    
	    search_txt.setHint("Recherche par prénom...");
	    
	    remouve.setVisibility(View.INVISIBLE);
	    
	    date_naissance.setSelected(true);
	    name.setSelected(true);
	    ville.setSelected(true);
	    payes.setSelected(true);
	    
	    titleTimer = new Timer();
		mHandler = new Handler();
		
		mCurrentLayoutState = 0;
		niveau = 0;
		isSelected = false;
		
		search_txt.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
					hideKeyboard(getApplicationContext(), search_txt);
					return true;
				}
				return false;
			}
		});
		
		search_txt.addTextChangedListener(new TextWatcher(){
	        public void afterTextChanged(Editable s) {
	        	if(search_txt.getText().toString().length()>0){
	        		remouve.setVisibility(View.VISIBLE);
	        		Search(search_txt.getText().toString());
	        	}else{
	        		listUers = jCertifManager.getListUsers();
	        		list.setAdapter(new MembresAdapter(Network.this, R.layout.single_news, listUers));
	        		remouve.setVisibility(View.INVISIBLE);      		        	      	
	        	}
	        }
	        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	        public void onTextChanged(CharSequence s, int start, int before, int count){
	        	
	        }
	    });
		
		map.setBackgroundColor(Color.rgb(127,200,255));
		liste.setBackgroundColor(Color.rgb(51,102,164));
		
		mapView.setBuiltInZoomControls(true);
		
		mapOverlays = mapView.getOverlays();
	
		drawable = getResources().getDrawable(R.drawable.pin);
		itemizedOverlay = new CustomItemizedOverlay<CustomOverlayItem>(drawable, mapView);
		
		listUSERSelected = new ArrayList<User>();	

		if(jCertifManager.getListUsers().size()>0){
			
			for(User user : jCertifManager.getListUsers()){
				
				if(!user.getLatitude().equals("")){		
					
					try{
						double longetude = Double.parseDouble(user.getLongitude());
						double laltitude = Double.parseDouble(user.getLatitude());
						
						listUSERSelected.add(user);
						
						point = new GeoPoint((int)(laltitude * 1E6),(int)(longetude * 1E6));
			
						itemizedOverlay.addOverlay(new CustomOverlayItem(point, user.getName(), user.getFirstname(), new Parametres().getImgULR(user.getPhoto_url())));	
						
					}catch(Exception e){
							
					}			
				}			
			}	
		}
		
		if(itemizedOverlay.size()>0)
			mapOverlays.add(itemizedOverlay);
		
		if(jCertifManager.getListUsers().size()>0)
			Zoom();
		
		remouve.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				search_txt.setText("");
				listUers = jCertifManager.getListUsers();
				list.setAdapter(new MembresAdapter(Network.this, R.layout.single_news, listUers));
			}
		});
	
		return_page.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isSelected)
					enableReturn();
			}
		});
		
		map.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				switchLayoutStateTo(0);
				map.setBackgroundColor(Color.rgb(127,200,255));
				liste.setBackgroundColor(Color.rgb(51,102,164));
				niveau = 0;
			}
		});
		
		liste.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				switchLayoutStateTo(1);
				liste.setBackgroundColor(Color.rgb(127,200,255));
				map.setBackgroundColor(Color.rgb(51,102,164));
				
				if(niveau == 1){
					description.setVisibility(View.VISIBLE);
		            list_memebre.setVisibility(View.GONE);
				}else{
					description.setVisibility(View.GONE);
		            list_memebre.setVisibility(View.VISIBLE);
				}
			}
		});
		
		viewFlipper.setOnClickListener(new OnClickListener() { 
        	@Override
        	public void onClick(View v) { 
        		AnimationFactory.flipTransition(viewFlipper, FlipDirection.LEFT_RIGHT);
        	}
        });
		
		jCertifManager = JCertifManager.getInstance();
        jCertifApplication = (JCertifApplication) getApplicationContext();
        
		new AsyncTask<String, Long, Bitmap>() {

			protected void onPostExecute(Bitmap result) {
				runOnUiThread(new Runnable() {
		 			@Override public void run(){
		 				if(!jCertifManager.getListUsers().isEmpty()){
		 					loading.setVisibility(View.GONE);
		 					body.setVisibility(View.VISIBLE);
		 					listUers = jCertifManager.getListUsers();
		 					list.setAdapter(new MembresAdapter(Network.this, R.layout.single_news, listUers));
		 				}else
		 					Toast.makeText(Network.this, "Aucun membre n'est inscrit", Toast.LENGTH_LONG).show();	
		 			}
				});	
				
				this.cancel(true);
			}

			protected Bitmap doInBackground(String... params) {
				try{
					if(jCertifManager.getListUsers().size()==0)
						new ParsingUsers(jCertifManager).getUsers();
				}catch(Exception e){
					Toast.makeText(Network.this, "Problème de connexion", Toast.LENGTH_LONG).show();	
				}
				return null;  
			}
		}.execute("");

		list.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
            	if(!isSelected){
					isSelected = true;
					niveau = 1;
					selectedUser = listUers.get(position);
	                setInfosUser();
	                description.setVisibility(View.VISIBLE);
	                list_memebre.setVisibility(View.GONE);
					isSelected = false;
            	}
            }
        });
        
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
		
	}
	
	protected void enableReturn() {
		return_page.setBackgroundResource(R.drawable.return_on);
		isSelected = true;
		btnSelected = 1;
		refreshTimer();
	}
	
	private void desableReturn(){
		return_page.setBackgroundResource(R.drawable.return_on);
		Intent intent = new Intent(Network.this, Principale.class);
    	startActivity(intent);
    	overridePendingTransition(R.anim.incoming, R.anim.outgoing);
   		finish();
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
				 desableReturn();
				 break;
			 }		
		 }
	};

	@SuppressLint("DefaultLocale")
	protected void Search(String name) {
		listUers = new ArrayList<User>();
		
		for(User user : jCertifManager.getListUsers()){
			if(user.getFirstname().contains(name) || user.getFirstname().contains(name.substring(0, 1).toUpperCase()+name.substring(1, name.length()))){
				listUers.add(user);
				Log.i("test",">>"+user.getFirstname());
			}
		}
		
		list.setAdapter(new MembresAdapter(Network.this, R.layout.single_news, listUers));
	}

	public void switchLayoutStateTo(int switchTo){
        while(mCurrentLayoutState != switchTo){
        	if(mCurrentLayoutState > switchTo){
        		mCurrentLayoutState--;
        		viewFlipper.setInAnimation(inFromLeftAnimation());
        		viewFlipper.setOutAnimation(outToRightAnimation());
        		viewFlipper.showPrevious();
            
        	} else {
        		mCurrentLayoutState++;
        		viewFlipper.setInAnimation(inFromRightAnimation());
                viewFlipper.setOutAnimation(outToLeftAnimation());
                viewFlipper.showNext();
            }          
               
        };
	}
	
	private void Zoom() {
	    int minLatitude = Integer.MAX_VALUE;
	    int maxLatitude = Integer.MIN_VALUE;
	    int minLongitude = Integer.MAX_VALUE;
	    int maxLongitude = Integer.MIN_VALUE;

	    if(jCertifManager.getListUsers().size()>0){
	    		  
	    	for (User user : jCertifManager.getListUsers()) {
	    		
		    	if(user.getLongitude().length()>0 && user.getLatitude().length()>0){
		    		
		    		try{
			    		double lg = Double.parseDouble(user.getLongitude());
						double la = Double.parseDouble(user.getLatitude());
						
						GeoPoint p = new GeoPoint((int)(la * 1E6),(int)(lg * 1E6));
				    	
				        int lati = p.getLatitudeE6();
				        int lon = p.getLongitudeE6();
	
				        maxLatitude = Math.max(lati, maxLatitude);
				        minLatitude = Math.min(lati, minLatitude);
				        maxLongitude = Math.max(lon, maxLongitude);
				        minLongitude = Math.min(lon, minLongitude);
		    		}catch(Exception e){
		    			
		    		}		       
		    	}		    	
		    }	
	    }
	    
	    mapView.getController().zoomToSpan(Math.abs(maxLatitude - minLatitude), Math.abs(maxLongitude - minLongitude));
	    mapView.getController().animateTo(new GeoPoint((maxLatitude + minLatitude) / 2, (maxLongitude + minLongitude) / 2));
	}
	 
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==1000){
			finish();
		}
	}
	
	public static void hideKeyboard(Context p_context, EditText p_editText){
		InputMethodManager l_imm = (InputMethodManager) p_context.getSystemService(Context.INPUT_METHOD_SERVICE);
		l_imm.hideSoftInputFromWindow(p_editText.getWindowToken(), 0);
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
        enableGPS(false);
    }
    
    @Override
    protected void onResume() {
        super.onResume();        
        overridePendingTransition(0,0);
        enableGPS(true);
    }
    
	private void enableGPS(boolean enable) {
	    String provider = Settings.Secure.getString(getContentResolver(), 
	        Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

	    if(provider.contains("gps") == enable) {
	        return; // the GPS is already in the requested state
	    }

	    final Intent poke = new Intent();
	    poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
	    poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
	    poke.setData(Uri.parse("3"));
	    sendBroadcast(poke);
	}
	
	protected Animation inFromRightAnimation() {
		 
        Animation inFromRight = new TranslateAnimation(
                        Animation.RELATIVE_TO_PARENT, +1.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromRight.setDuration(500);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        return inFromRight;
	}

	protected Animation outToLeftAnimation() {
        Animation outtoLeft = new TranslateAnimation(
                        Animation.RELATIVE_TO_PARENT, 0.0f,
                        Animation.RELATIVE_TO_PARENT, -1.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoLeft.setDuration(500);
        outtoLeft.setInterpolator(new AccelerateInterpolator());
        return outtoLeft;
	}

	protected Animation inFromLeftAnimation() {
        Animation inFromLeft = new TranslateAnimation(
                        Animation.RELATIVE_TO_PARENT, -1.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromLeft.setDuration(500);
        inFromLeft.setInterpolator(new AccelerateInterpolator());
        return inFromLeft;
	}

	protected Animation outToRightAnimation() {
        Animation outtoRight = new TranslateAnimation(
                        Animation.RELATIVE_TO_PARENT, 0.0f,
                        Animation.RELATIVE_TO_PARENT, +1.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoRight.setDuration(500);
        outtoRight.setInterpolator(new AccelerateInterpolator());
        return outtoRight;
	}
	
	public class MembresAdapter extends ArrayAdapter<User>{

		public MembresAdapter(Context context, int textViewResourceId, List<User> objects){
			super(context, textViewResourceId, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent){

			LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(R.layout.single_user, null);

			TextView name 					= (TextView) convertView.findViewById(R.id.name);
			TextView payes 					= (TextView) convertView.findViewById(R.id.payes);
			final RelativeLayout img_profil = (RelativeLayout) convertView.findViewById(R.id.img_profil);
			
			final String url = new Parametres().getImgULR(listUers.get(position).getPhoto_url());

			name.setText(listUers.get(position).getFirstname()+" "+listUers.get(position).getName());
			
			payes.setText(listUers.get(position).getPayes());

			new AsyncTask<String, Long, Bitmap>() {

				protected void onPostExecute(Bitmap result) {
					if (result != null) {
						BitmapDrawable background = new BitmapDrawable(result);
						img_profil.setBackgroundDrawable(background);
					} 

					result = null;
					this.cancel(true);
				}

				protected Bitmap doInBackground(String... params) {
					return jCertifApplication.ImageOperations(url);  
				}
			}.execute("");

			return convertView;
		}

	}
    
    protected void sendEmail() {
    	String to = selectedUser.getEmail();
		String subject = "";
		String message = "";
				
		Intent email = new Intent(Intent.ACTION_SEND);
		email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
		email.putExtra(Intent.EXTRA_SUBJECT, subject);
		email.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(message));

		email.setType("message/rfc822");

		startActivity(Intent.createChooser(email, "Veuillez selectionner un client Email :"));
	}
    
    private void setInfosUser(){
    	
    	name.setText(selectedUser.getFirstname()+" "+selectedUser.getName());
    	
    	if(!selectedUser.getDate_naissance().equals("0000-00-00"))
    		date_naissance.setText(selectedUser.getDate_naissance());
    	else
    		date_naissance.setVisibility(View.GONE);
    	
    	if(selectedUser.getVille().length()>0)
    		ville.setText("Ville : "+selectedUser.getVille());
    	else
    		ville.setVisibility(View.GONE);
    	
    	if(selectedUser.getPayes().length()>0)
        	payes.setText("Payes : "+selectedUser.getPayes());
    	else
    		payes.setVisibility(View.GONE);
    	
    	mail_txt.setText("Envoyer un mail à "+selectedUser.getFirstname());
    	
    	String text = "<html><head></head><body><p align=\"justify\"><font size='2'>" + selectedUser.getDescription() +"</font></p>";
    	web.loadDataWithBaseURL(text, text, "text/html", "utf-8", "");
    	web.setWebViewClient(new WebViewClient());
		web.getSettings().setLoadWithOverviewMode(true);
		web.setBackgroundColor(0);
		web.setBackgroundColor(0x00000000);
		
		if(selectedUser.getPhoto_url().length()>0){
			new AsyncTask<String, Long, Bitmap>() {
				protected void onPostExecute(Bitmap result) {
					img_profil.setBackgroundResource(R.drawable.puce_toque);
					if(result != null){
						BitmapDrawable bitmapDrawable = new BitmapDrawable(result);
						img_profil.setBackgroundDrawable(bitmapDrawable);
					}
					this.cancel(true);
				}
				@Override
				protected Bitmap doInBackground(String... params) { 
					return jCertifApplication.ImageOperations(new com.jcertif.reseaujcertif.services.Parametres().getImgULR(selectedUser.getPhoto_url()));    
				}
			}.execute("");
		}else
			img_profil.setBackgroundResource(R.drawable.puce_toque);
		
    }
    
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			if(niveau == 1){
				description.setVisibility(View.GONE);
	            list_memebre.setVisibility(View.VISIBLE);
	            
	            niveau = 0;
			}else
				exitApp();
			return true;
		}
		return false;
	}

	private void exitApp(){
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();

		alertDialog.setTitle("Quitter");
		alertDialog.setMessage("Est ce que vous êtes sûr ?");
		alertDialog.setButton("Oui", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				finish();
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