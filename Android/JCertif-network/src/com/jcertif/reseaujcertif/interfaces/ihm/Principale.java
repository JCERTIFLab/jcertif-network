package com.jcertif.reseaujcertif.interfaces.ihm;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anupcowkur.wheel_menu.WheelMenu;
import com.jcertif.reseaujcertif.Authentification;
import com.jcertif.reseaujcertif.R;
import com.jcertif.reseaujcertif.com.ParsingUsers;
import com.jcertif.reseaujcertif.persistances.User;
import com.jcertif.reseaujcertif.services.JCertifApplication;
import com.jcertif.reseaujcertif.services.JCertifManager;
import com.jcertif.reseaujcertif.services.Parametres;
import com.jcertif.reseaujcertif.transvers.OutilsInternet;

public class Principale extends Activity {

	private RelativeLayout img_profil, roue;
	private LinearLayout menu, sub_menu, change_status, my_profil, desconnexion, a_propos, about, web, return_page, add_rubrique, admin;
	private TextView name1, name2, titre, siteweb, desconnexion_txt, a_propos_txt, my_profil_txt, change_status_txt, add_rubrique_txt;
	private ImageView wheel, settings, status, change_status_img, fermer;
	private WebView webView1;
	private JCertifManager jCertifManager;
	private JCertifApplication jCertifApplication;
	private boolean subMenuVisble, isSelected;
	private Handler mHandler;
	private Timer titleTimer;
	private int btnSelected;
	private WheelMenu wheelMenu;	
	
    @SuppressLint("SimpleDateFormat")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carousel_main);
        
        titleTimer = new Timer();
		mHandler = new Handler();
		
		isSelected = false;
		subMenuVisble = false;
        
        jCertifManager = JCertifManager.getInstance();
        jCertifApplication =  (JCertifApplication) getApplicationContext();
        
        name1 			  = (TextView) findViewById(R.id.name1);
        name2 			  = (TextView) findViewById(R.id.name2);
        titre 			  = (TextView) findViewById(R.id.titre);
        siteweb		  	  = (TextView) findViewById(R.id.siteweb);
        change_status_txt = (TextView) findViewById(R.id.change_status_txt);
        desconnexion_txt  = (TextView) findViewById(R.id.desconnexion_txt);
        my_profil_txt 	  = (TextView) findViewById(R.id.my_profil_txt);
        add_rubrique_txt  = (TextView) findViewById(R.id.add_rubrique_txt);
        a_propos_txt  	  = (TextView) findViewById(R.id.a_propos_txt);
        
        webView1		  = (WebView) findViewById(R.id.webView1);
        
        wheel			  = (ImageView) findViewById(R.id.wheel);
        settings		  = (ImageView) findViewById(R.id.settings);
        status			  = (ImageView) findViewById(R.id.status);
        change_status_img = (ImageView) findViewById(R.id.change_status_img);
        fermer			  = (ImageView) findViewById(R.id.fermer);
        
        menu 		  	  = (LinearLayout) findViewById(R.id.menu);
        sub_menu	  	  = (LinearLayout) findViewById(R.id.sub_menu);
        change_status 	  = (LinearLayout) findViewById(R.id.change_status);
        my_profil	  	  = (LinearLayout) findViewById(R.id.my_profil);
        desconnexion  	  = (LinearLayout) findViewById(R.id.desconnexion);
        a_propos	  	  = (LinearLayout) findViewById(R.id.a_propos);
        about	  	  	  = (LinearLayout) findViewById(R.id.about);
        web	  	  	 	  = (LinearLayout) findViewById(R.id.web);
        return_page 	  = (LinearLayout) findViewById(R.id.return_page);
        add_rubrique	  = (LinearLayout) findViewById(R.id.add_rubrique);
        
        img_profil 		  = (RelativeLayout) findViewById(R.id.img_profil);
        roue 			  = (RelativeLayout) findViewById(R.id.menu_roue);
        
        name1.setText(jCertifManager.getCurrentUser().getFirstname() + " " + jCertifManager.getCurrentUser().getName());
        name1.setSelected(true);
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        name2.setText(dateFormat.format(cal.getTime()));
        name2.setSelected(true);
        
        wheelMenu = new WheelMenu(Principale.this, 8, R.drawable.wheel, wheel, true);
        
        if(jCertifManager.getCurrentUser().isStatus())
			status.setImageResource(R.drawable.login);
        else
        	status.setImageResource(R.drawable.logout);
        
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	if(!isSelected)
            		enableMenu();      	
            }
        });
        
        add_rubrique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	
            	if(!isSelected)
            		enableAddRubrique();            	
            }
        });
        
        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	if(jCertifManager.getCurrentUser().isStatus())
            		showWelcomMsg("Votre statut est : on ligne");
            	else
            		showWelcomMsg("Votre statut est : hors ligne");
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {       
            	if(!subMenuVisble){
            		subMenuVisble = true;
            		setSubMenuVisible();
            	}else{
            		subMenuVisble = false;
            		setSubMenuInvisible();
            	}           	
            }
        });
        
        return_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	web.setVisibility(View.GONE);
            }
        });
        
        siteweb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	setAboutInvisible();
            	web.setVisibility(View.VISIBLE);
            }
        });
        
        
        fermer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	setAboutInvisible();
            }
        });
        
        a_propos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	if(subMenuVisble)
            		if(!isSelected)
            			enableAPropos();
            }
        });
        
        change_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	if(subMenuVisble)
            		if(!isSelected)
            			enableChangeStatus();
            }
        });

        my_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	if(subMenuVisble)
            		if(!isSelected)
            			enableMyProfil();        		
            }
        });
        
        desconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  
            	if(subMenuVisble)
            		if(!isSelected)
            			enableDesconnexion();          	
            }
        });
        
        new Thread(){
        	@Override
        	public void run(){
        		
        		try{
        			sleep(500);
        		}catch(Exception e){}
        		
        		Principale.this.runOnUiThread(new Runnable() {
 					@Override public void run(){
		        		roue.setVisibility(View.VISIBLE);
		        		
		        		Animation a = AnimationUtils.loadAnimation(Principale.this, R.anim.alpha);
		                a.reset();
		                
		                roue.clearAnimation();
		                roue.startAnimation(a);
 					}
        		});
        	}
        }.start();
        
        new Thread(){
        	@Override
        	public void run(){
        		while(true){
        			Principale.this.runOnUiThread(new Runnable() {
     					@Override public void run(){
     						titre.setText(jCertifApplication.menuPrincipale[wheelMenu.getSelected()-1]);
     					}
        			});
        			
        			try{
        				sleep(200);
        			}catch(Exception e){}
        		}
        	}
        }.start();
        
        LoadingImg();
        
        setWebView();
        
        if(!jCertifManager.getCurrentUser().isAdmin())
        	add_rubrique.setVisibility(View.GONE);
    }
    
    private void enableAddRubrique() {

    	isSelected = true;
    	add_rubrique.setBackgroundColor(Color.rgb(79, 129, 189));
    	add_rubrique_txt.setTextColor(Color.rgb(255, 255, 255));
		btnSelected = 8;
		refreshTimer();
	}
    
    private void disableAddRubrique(){
    	isSelected = false;
    	ShowDialog();
    	setSubMenuInvisible();
    	subMenuVisble = false;
    	add_rubrique.setBackgroundColor(Color.rgb(255, 255, 255));
    	add_rubrique_txt.setTextColor(Color.rgb(79, 129, 189));
    }
    
    private void enableChangeStatus() {
    	isSelected = true;
    	change_status.setBackgroundColor(Color.rgb(79, 129, 189));
		change_status_txt.setTextColor(Color.rgb(255, 255, 255));	
		btnSelected = 9;
		refreshTimer();
	}
    
    private void disableChangeStatus(){
    	changeStatus();  
    	setSubMenuInvisible();
    	subMenuVisble = false;
    	isSelected = false;
    	change_status.setBackgroundColor(Color.rgb(255, 255, 255));
    	change_status_txt.setTextColor(Color.rgb(79, 129, 189));
    }
    
    private void enableMyProfil() {
    	isSelected = true;
    	my_profil.setBackgroundColor(Color.rgb(79, 129, 189));
		my_profil_txt.setTextColor(Color.rgb(255, 255, 255));		
		btnSelected = 12;
		refreshTimer();
	}
    
    private void disableMyProfil(){
		setSubMenuInvisible();
		isSelected = false;
		subMenuVisble = false;
    	Intent intent = new Intent(Principale.this, MyCount.class);
		startActivityForResult(intent, 1000);
		finish();
    }
    
    private void enableAPropos() {
    	isSelected = true;
    	a_propos.setBackgroundColor(Color.rgb(79, 129, 189));
		a_propos_txt.setTextColor(Color.rgb(255, 255, 255));	
		btnSelected = 10;
		refreshTimer();
	}
    
    private void disableAPropos(){
    	isSelected = false;
    	setSubMenuInvisible();
    	setAboutVisible();
    	subMenuVisble = false; 	
    	a_propos.setBackgroundColor(Color.rgb(255, 255, 255));
    	a_propos_txt.setTextColor(Color.rgb(79, 129, 189));
    }
    
    private void enableDesconnexion() {
    	isSelected = true;
    	desconnexion.setBackgroundColor(Color.rgb(79, 129, 189));
		desconnexion_txt.setTextColor(Color.rgb(255, 255, 255));	
		btnSelected = 11;
		refreshTimer();
	}
    
    private void disableDesconnexion(){
    	isSelected = false;
    	subMenuVisble = false; 
    	desconnexion.setBackgroundColor(Color.rgb(255, 255, 255));
    	desconnexion_txt.setTextColor(Color.rgb(79, 129, 189));
    	setSubMenuInvisible();
    	exit();
    }

	private void desableMenu(){
    	
    	menu.setBackgroundResource(R.drawable.logo1);
    	
    	if(OutilsInternet.HttpTest(Principale.this)){
    		
    		jCertifManager.setMenu_Selecteed(wheelMenu.getSelected());
        	
        	Intent intent = null;
        	if(wheelMenu.getSelected()==8)
        		intent = new Intent(Principale.this, Network.class);
        	else if(wheelMenu.getSelected()==5)
        		intent = new Intent(Principale.this, FindCars.class);
        	else
        		intent = new Intent(Principale.this, MainActivity.class);
        	
        	startActivityForResult(intent, 1000);
        	overridePendingTransition (R.anim.incoming, R.anim.outgoing);
        	finish();
    	}else{
    		Principale.this.runOnUiThread(new Runnable() {
				@Override public void run(){
					showWelcomMsg(getResources().getString(R.string.prob_connexion));
				}
    		});
		}   
    }
    
    protected void enableMenu() {
  		isSelected = true;
  		menu.setBackgroundResource(R.drawable.logo2);
  		btnSelected = 7;
  		refreshTimer();
  	}
    
    protected void enableNews() {
		isSelected = true;
		news.setBackgroundResource(R.drawable.cel);
		btnSelected = 1;
		refreshTimer();
	}
    
    private void desableNews(){		
		jCertifManager.setItemSelected(1);
		dialog.dismiss();	
		isSelected = false;
		Intent intent = new Intent(Principale.this, AddRubrique.class);
    	startActivityForResult(intent, 1000);
    	finish();
    }
    
    protected void enableEvent() {
		isSelected = true;
		events.setBackgroundResource(R.drawable.cel);
		btnSelected = 2;
		refreshTimer();
	}
    
    private void desableEvent(){		
    	dialog.dismiss();
    	isSelected = false;
		jCertifManager.setItemSelected(2);
		Intent intent = new Intent(Principale.this, AddRubrique.class);
    	startActivityForResult(intent, 1000);
    	finish();
    }
    
    protected void enableCategorie() {
		isSelected = true;
		categories.setBackgroundResource(R.drawable.cel);
		btnSelected = 3;
		refreshTimer();
	}
    
    private void desableCategorie(){		
    	dialog.dismiss();
    	isSelected = false;
		jCertifManager.setItemSelected(3);
		Intent intent = new Intent(Principale.this, AddRubrique.class);
    	startActivityForResult(intent, 1000);
    	finish();
    }
    
    protected void enablePhotos() {
		isSelected = true;
		photos.setBackgroundResource(R.drawable.cel);
		btnSelected = 4;
		refreshTimer();
	}
    
    private void desablePhotos(){		
    	dialog.dismiss();
    	isSelected = false;
		jCertifManager.setItemSelected(4);
		Intent intent = new Intent(Principale.this, AddRubrique.class);
    	startActivityForResult(intent, 1000);
    	finish();
    }
    
    protected void enableVideos() {
		isSelected = true;
		videos.setBackgroundResource(R.drawable.cel);
		btnSelected = 5;
		refreshTimer();
	}

    private void desableVideos(){		
    	dialog.dismiss();
    	isSelected = false;
		jCertifManager.setItemSelected(5);
		Intent intent = new Intent(Principale.this, AddRubrique.class);
    	startActivityForResult(intent, 1000);
    	finish();
    }
    
    protected void enableAdmin() {
    	isSelected = true;
		admin.setBackgroundResource(R.drawable.cel);
		btnSelected = 13;
		refreshTimer();
	}
    
    private void disableAdmin(){		
    	dialog.dismiss();
    	isSelected = false;
		ShowListMemebre();
    }
    
    protected void enableExit() {
		isSelected = true;
		exit.setBackgroundResource(R.drawable.cel);
		btnSelected = 6;
		refreshTimer();
	}
    
    private void desableExit(){		
    	dialog.dismiss();
    	isSelected = false;
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
				 desableNews();
				 break;
			 case 2:
				 desableEvent();
				 break;
			 case 3:
				 desableCategorie();
				 break;
			 case 4:
				 desablePhotos();
				 break;
			 case 5:
				 desableVideos();
				 break;
			 case 6:
				 desableExit();
				 break;
			 case 7:
				 desableMenu();
				 break;
			 case 8:
				 disableAddRubrique();
				 break;
			 case 9:
				 disableChangeStatus();
				 break;
			 case 10:
				 disableAPropos();
				 break;
			 case 11:
				 disableDesconnexion();
				 break;
			 case 12:
				 disableMyProfil();
				 break;
			 case 13:
				 disableAdmin();
				 break;
			 }
			 
		 }
	};
	
	private LinearLayout news, events, categories, photos, videos, exit;
	private Dialog dialog;
	
	private void ShowListMemebre(){
		dialog = new Dialog(this);
		dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		dialog.setContentView(R.layout.list_memebres);
		dialog.setTitle("Gestion des administrateurs");
		
		final LinearLayout loading = (LinearLayout) dialog.findViewById(R.id.loading_body); 
		final ListView list = (ListView) dialog.findViewById(R.id.list); 
		
		list.setOnItemClickListener(new OnItemClickListener() {    	 
			@Override
			public void onItemClick(AdapterView<?> a, View v, final int position, long id) {
				String msg = "";
				if(jCertifManager.getListUsers().get(position).isAdmin())
					msg ="Voullez-vous retirer les droits d'administrateur à "+jCertifManager.getListUsers().get(position).getFirstname()+" "+jCertifManager.getListUsers().get(position).getName();
				else
					msg ="Voullez-vous attribuer les droits d'administrateur à "+jCertifManager.getListUsers().get(position).getFirstname()+" "+jCertifManager.getListUsers().get(position).getName();
				
				AlertDialog alertDialog = new AlertDialog.Builder(Principale.this).create();
				alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
				alertDialog.setMessage(msg);
				alertDialog.setButton("Oui", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						new Thread(){
							@Override
							public void run(){
								Principale.this.runOnUiThread(new Runnable() {
						 			@Override public void run(){
						 				User user = jCertifManager.getListUsers().get(position);
										user.setAdmin(!user.isAdmin());
										if(new ParsingUsers(jCertifManager).updateUser(user)){
											showWelcomMsg("Modification éffectuée");
											jCertifManager.getListUsers().set(position, user);
							 				list.setAdapter(new MemebresAdapter(Principale.this, R.layout.single_forum, jCertifManager.getListUsers()));
										}else
											showWelcomMsg("Modification échouée");
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
			}
		});

		new AsyncTask<String, Long, Bitmap>() {

			protected void onPostExecute(Bitmap result) {
				Principale.this.runOnUiThread(new Runnable() {
		 			@Override public void run(){
		 				loading.setVisibility(View.GONE);
		 				list.setVisibility(View.VISIBLE);
		 				list.setAdapter(new MemebresAdapter(Principale.this, R.layout.single_forum, jCertifManager.getListUsers()));
		 			}
				});	
				
				this.cancel(true);
			}

			protected Bitmap doInBackground(String... params) {
				while(!jCertifManager.isParsingUsersFinish()){}
				return null;  
			}
		}.execute("");

		dialog.show();
	}
    
	public class MemebresAdapter extends ArrayAdapter<User>{

		public MemebresAdapter(Context context, int textViewResourceId, List<User> objects){
			super(context, textViewResourceId, objects);
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent){

			LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(R.layout.single_membre, null);

			TextView name 			  = (TextView) convertView.findViewById(R.id.name);
			final RelativeLayout img_profil = (RelativeLayout) convertView.findViewById(R.id.img_profil);
			LinearLayout statut 	  = (LinearLayout) convertView.findViewById(R.id.statut);
			
			if(!jCertifManager.getListUsers().get(position).isAdmin())
				statut.setVisibility(View.GONE);
			
			name.setText(jCertifManager.getListUsers().get(position).getFirstname()+" "+jCertifManager.getListUsers().get(position).getName());
			
			new AsyncTask<String, Long, Bitmap>() {
				protected void onPostExecute(Bitmap result) {
					if (result != null) {
						BitmapDrawable background = new BitmapDrawable(result);
						img_profil.setBackgroundDrawable(background);
					} 

					result = null;
					this.cancel(true);
				}
				@Override
				protected Bitmap doInBackground(String... params) { 
					return jCertifApplication.ImageOperations(new Parametres().getImgULR(jCertifManager.getListUsers().get(position).getPhoto_url()));    
				}
			}.execute("");
			
			return convertView;
		}
	}
	
    protected void ShowDialog() {
    	dialog = new Dialog(this);
		dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		dialog.setContentView(R.layout.menu_contextuel3);
		dialog.setTitle("Paramètres");

		admin		= (LinearLayout) dialog.findViewById(R.id.admin); 
		news 		= (LinearLayout) dialog.findViewById(R.id.news); 
		events  	= (LinearLayout) dialog.findViewById(R.id.events); 
		categories 	= (LinearLayout) dialog.findViewById(R.id.categories);
		photos 		= (LinearLayout) dialog.findViewById(R.id.photos); 
		videos  	= (LinearLayout) dialog.findViewById(R.id.videos); 
		exit 		= (LinearLayout) dialog.findViewById(R.id.exit);
	
		news.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isSelected)
					enableNews();
			}
		});
		
		events.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isSelected)
					enableEvent();
			}
		});
		
		categories.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isSelected)
					enableCategorie();
			}
		});
		
		photos.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isSelected)
					enablePhotos();
			}
		});
		
		videos.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isSelected)
					enableVideos();
			}
		});
		
		exit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isSelected)
					enableExit();
			}
		});
		
		admin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isSelected)
					enableAdmin();
			}
		});

		dialog.show();
	}

	
	private void setWebView() {
    	webView1.loadUrl("http://www.jcertif.com/faces/home/home.jsf");
        webView1.setWebViewClient(new WebViewClient());
		webView1.getSettings().setLoadWithOverviewMode(true);
		webView1.getSettings().setUseWideViewPort(true);
		webView1.getSettings().setBuiltInZoomControls(true);
		webView1.setBackgroundColor(0);
		webView1.setBackgroundColor(0x00000000);    
	}

	private void setAboutVisible(){
    	Animation a = AnimationUtils.loadAnimation(this, R.anim.translate_down);
        a.reset();
        about.clearAnimation();
        about.startAnimation(a);
        about.setVisibility(View.VISIBLE);
    }
    
    private void setAboutInvisible(){
    	Animation a = AnimationUtils.loadAnimation(this, R.anim.translate_up);
        a.reset();
        about.clearAnimation();
        about.startAnimation(a);
        about.setVisibility(View.GONE);
    }

	protected void changeStatus() {
		if(jCertifManager.getCurrentUser().isStatus()){
			jCertifManager.getCurrentUser().setStatus(false);
			if(new ParsingUsers(jCertifManager).updateUser(jCertifManager.getCurrentUser())){
				status.setImageResource(R.drawable.logout);
				change_status_txt.setText("se montrer");
				change_status_img.setImageResource(R.drawable.login);
				showWelcomMsg("Votre statut est : hors ligne");
			}else{
				jCertifManager.getCurrentUser().setStatus(true);
				showWelcomMsg("Probléme lors de la modifcation de status");
			}
		}else{
			jCertifManager.getCurrentUser().setStatus(true);
			if(new ParsingUsers(jCertifManager).updateUser(jCertifManager.getCurrentUser())){
				status.setImageResource(R.drawable.login);
				change_status_txt.setText("se cacher");
				change_status_img.setImageResource(R.drawable.logout);
				showWelcomMsg("Votre statut est : on ligne");
			}else{
				jCertifManager.getCurrentUser().setStatus(false);
				showWelcomMsg("Probléme lors de la modifcation de status");
			}
		}
	}

	private void setSubMenuVisible(){
    	
    	Animation animation = new TranslateAnimation(0, 0, 900, 0);
    	animation.setDuration(1000);
    	animation.setFillAfter(true);
    	sub_menu.startAnimation(animation);
    	sub_menu.setVisibility(View.VISIBLE);
    	
    	Animation rotation = AnimationUtils.loadAnimation(this, R.anim.rotate);
    	settings.startAnimation(rotation);

    }
    
    private void setSubMenuInvisible(){
    	Animation animation = new TranslateAnimation(0, 0, 0, 900);
    	animation.setDuration(1000);
    	animation.setFillAfter(true);
    	sub_menu.startAnimation(animation);
    	sub_menu.setVisibility(View.INVISIBLE);
    	
    	Animation rotation = AnimationUtils.loadAnimation(this, R.anim.rotate);
    	settings.startAnimation(rotation);
    }
	
	private void exit(){
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		alertDialog.setTitle("Déconnexion");
		alertDialog.setMessage("Est ce que vous êtes sûr ?");
		alertDialog.setButton("Oui", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				
				jCertifApplication.SaveDate("", "login.txt");
				jCertifManager.setCurrentUser(null);
				
				Intent intent = new Intent(Principale.this, Authentification.class);
				startActivityForResult(intent, 1000);
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
    
    private void LoadingImg() {
    	
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
				return jCertifApplication.ImageOperations(new com.jcertif.reseaujcertif.services.Parametres().getImgULR(jCertifManager.getCurrentUser().getPhoto_url()));    
			}
		}.execute("");
		
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