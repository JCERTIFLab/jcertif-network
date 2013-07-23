package com.jcertif.reseaujcertif.interfaces.ihm;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jcertif.reseaujcertif.R;
import com.jcertif.reseaujcertif.com.ParsingCategories;
import com.jcertif.reseaujcertif.com.ParsingEvents;
import com.jcertif.reseaujcertif.com.ParsingNews;
import com.jcertif.reseaujcertif.com.ParsingPhotos;
import com.jcertif.reseaujcertif.com.ParsingVideos;
import com.jcertif.reseaujcertif.persistances.Category;
import com.jcertif.reseaujcertif.persistances.Event;
import com.jcertif.reseaujcertif.persistances.News;
import com.jcertif.reseaujcertif.persistances.Photo;
import com.jcertif.reseaujcertif.persistances.Video;
import com.jcertif.reseaujcertif.services.JCertifApplication;
import com.jcertif.reseaujcertif.services.JCertifManager;

public class AddRubrique extends Activity {
	
	private JCertifApplication jCertifApplication;
	private JCertifManager jCertifManager;
	private LinearLayout loading_body, body, add_news, add_event, add_categories, add_video, url_video_zone;
	private EditText title_new, url_news, url_img_news, content_news;
	private EditText url_img_category, title_category;
	private EditText title_video, url_video, url_img_video;
	private EditText title_event, url_event, url_img_event, ville_event, payes_event, date_debut_event, date_fin_event, content_event;
	private RelativeLayout img_profil;
	private Spinner list_evenement;
	private TextView auteur, title_activity;
	private TextView add, annuler;
	private ImageView retour_page;
	private boolean isSelected;
	private Handler mHandler;
	private Timer titleTimer;
	private int btnSelected;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_rubrique);
        
        titleTimer = new Timer();
		mHandler = new Handler();
		
		loading_body   = (LinearLayout) findViewById(R.id.loading_body);
		body 	 	   = (LinearLayout) findViewById(R.id.body);
		add_news 	   = (LinearLayout) findViewById(R.id.add_news);
		add_event	   = (LinearLayout) findViewById(R.id.add_event);
		add_categories = (LinearLayout) findViewById(R.id.add_categories);
		add_video 	   = (LinearLayout) findViewById(R.id.add_video);
		url_video_zone = (LinearLayout) findViewById(R.id.url_video_zone);
		
		retour_page  	 = (ImageView) findViewById(R.id.retour_page);
		
		title_video 	 = (EditText) findViewById(R.id.title_video);
		url_img_video 	 = (EditText) findViewById(R.id.url_img_video);
		url_video 	 	 = (EditText) findViewById(R.id.url_video);
		list_evenement 	 = (Spinner) findViewById(R.id.list_evenement);
        
        title_new 	 	 = (EditText) findViewById(R.id.title_new);
        url_news 	 	 = (EditText) findViewById(R.id.url_news);
        url_img_news 	 = (EditText) findViewById(R.id.url_img_news);
        content_news 	 = (EditText) findViewById(R.id.content_news);
        
        title_category 	 = (EditText) findViewById(R.id.title_category);
        url_img_category = (EditText) findViewById(R.id.url_img_category);
        
        title_event  	 = (EditText) findViewById(R.id.title_event);
        url_event  		 = (EditText) findViewById(R.id.url_event);
        url_img_event  	 = (EditText) findViewById(R.id.url_img_event);
        ville_event  	 = (EditText) findViewById(R.id.ville_event);
        payes_event  	 = (EditText) findViewById(R.id.payes_event);
        date_debut_event = (EditText) findViewById(R.id.date_debut_event);
        date_fin_event   = (EditText) findViewById(R.id.date_fin_event);
        content_event  	 = (EditText) findViewById(R.id.content_event);
        
        img_profil 	 	 = (RelativeLayout) findViewById(R.id.img_profil_edit);
        
        auteur 		 	 = (TextView) findViewById(R.id.auteur);
        add 		 	 = (TextView) findViewById(R.id.add);
        annuler 	 	 = (TextView) findViewById(R.id.annuler);
        title_activity 	 = (TextView) findViewById(R.id.title_activity);
        
        jCertifManager 	 = JCertifManager.getInstance();
        jCertifApplication =  (JCertifApplication) getApplicationContext();
        
        isSelected = false;
        
        setView();
        
        retour_page.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isSelected)
					enableReturn();
			}
		});
 
        annuler.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isSelected)
					enableCancel();
			}
		});
		
        add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isSelected){
					isSelected = true;
					enableAdd();
				}
			}
		});
        
        new Thread(){
        	@Override
        	public void run(){
        		try{
        			sleep(1000);
        		}catch(Exception e){}
        		
        		AddRubrique.this.runOnUiThread(new Runnable() {
		 			@Override public void run(){
		 				jCertifApplication.setPageInvisible(loading_body);
		 				jCertifApplication.setPageVisible(body);
		 			}
        		});
        	}
        }.start();
    }

	private void setView() {
		
		new AsyncTask<String, Long, Bitmap>() {
			protected void onPostExecute(Bitmap result) {
				if(result!=null){
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
		
		auteur.setText(jCertifManager.getCurrentUser().getFirstname()+" "+jCertifManager.getCurrentUser().getName());
		
		switch(jCertifManager.getItemSelected()){
		case 1:
			title_activity.setText("Ajouter une actualité");
			add_news.setVisibility(View.VISIBLE);
			break;
		case 2:
			title_activity.setText("Ajouter un évènement");
			add_event.setVisibility(View.VISIBLE);
			break;
		case 3:
			title_activity.setText("Ajouter une catégorie");
			add_categories.setVisibility(View.VISIBLE);	
			break;
		case 4:
			title_activity.setText("Ajouter une photo");
			add_video.setVisibility(View.VISIBLE);
			
			url_video_zone.setVisibility(View.GONE);

			ArrayList<String> list = new ArrayList<String>();
			
			for(Event event : jCertifManager.getListEvents())
				list.add(event.getTitle());

			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			list_evenement.setAdapter(dataAdapter);
			
			break;
		case 5:
			title_activity.setText("Ajouter une vidéo");
			add_video.setVisibility(View.VISIBLE);

			ArrayList<String> list2 = new ArrayList<String>();
			
			for(Event event : jCertifManager.getListEvents())
				list2.add(event.getTitle());

			ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list2);
			dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			list_evenement.setAdapter(dataAdapter2);

			break;
		}
	
	}
	
	protected void enableCancel() {
		isSelected = true;
		annuler.setBackgroundResource(R.drawable.shape);
		annuler.setTextColor(Color.rgb(255, 255, 255));
		btnSelected = 2;
		refreshTimer();
	}
	
	private void enableAdd(){
		add.setBackgroundResource(R.drawable.shape);
        add.setTextColor(Color.rgb(255, 255, 255));
		btnSelected = 1;
		refreshTimer();
	}

	protected void enableReturn() {
		isSelected = true;
		
		retour_page.setBackgroundResource(R.drawable.return_on);
		btnSelected = 3;
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
				 desableAdd();
				 break;
			 case 2:
				 desableCancel();
				 break;
			 case 3:
				 desableReturn();
				 break;
			 }
			 
		 }
	};
	
	private void desableAdd(){
		add.setBackgroundResource(R.drawable.shape_whate);
        add.setTextColor(Color.rgb(79, 129, 189));
        switch(jCertifManager.getItemSelected()){
        case 1:
        	addNews();
        	break;
        case 2:
        	addEvent();
        	break;
        case 3:
        	addCategory();
        	break;
        case 4:
        	addPhoto();
        	break;
        case 5:
        	addVideo();
        	break;
        }
	}
	 
	protected void desableReturn() {
		retour_page.setBackgroundResource(R.drawable.return_off);

		Intent intent = new Intent(AddRubrique.this, Principale.class);
    	startActivityForResult(intent, 1000);
    	finish();
	}

	private void addPhoto() {	
		new Thread(){
			@Override
			public void run(){
				AddRubrique.this.runOnUiThread(new Runnable() {
		 			@Override 
		 			public void run(){
		 				if(url_img_video.getText().toString().length()>0){
		 					
		 					jCertifApplication.setPageInvisible(body);
		 					jCertifApplication.setPageVisible(loading_body);
		 					
		 					Photo photo = new Photo();
		 					photo.setTitle(title_video.getText().toString());
		 					photo.setImg_url(url_img_video.getText().toString());
		 					photo.setEvent_id(jCertifManager.getListEvents().get(list_evenement.getSelectedItemPosition()).getId());
		 					photo.setUser(jCertifManager.getCurrentUser());
		 					
		 					if(new ParsingPhotos(jCertifManager).addPhoto(photo)){
		 						showWelcomMsg("Photo ajoutée avec succès");				
		 						Intent intent = new Intent(AddRubrique.this, Principale.class);
		 				    	startActivityForResult(intent, 1000);
		 				    	finish();
		 					}else{
		 						showWelcomMsg("Probléme lors de l'ajout de la photo");
		 						
		 						jCertifApplication.setPageInvisible(loading_body);
		 						jCertifApplication.setPageVisible(body);
		 					}
		 				}else
		 					showWelcomMsg("Veuillez indiquer au moin le titre et l'url de votre photo");	
		 				
		 				isSelected = false;
		 			}
				});
			}
		}.start();
	}

	private void addVideo() {
		
		new Thread(){
			@Override
			public void run(){
				AddRubrique.this.runOnUiThread(new Runnable() {
		 			@Override 
		 			public void run(){
		 				if(url_video.getText().toString().length()>0){
		 					
		 					jCertifApplication.setPageInvisible(body);
		 					jCertifApplication.setPageVisible(loading_body);
		 					
		 					Video video = new Video();
		 					video.setTitle(title_video.getText().toString());
		 					video.setUrl(url_video.getText().toString());
		 					video.setImg_url(url_img_video.getText().toString());
		 					video.setEvent_id(jCertifManager.getListEvents().get(list_evenement.getSelectedItemPosition()).getId());
		 					video.setUser(jCertifManager.getCurrentUser());
		 					
		 					if(new ParsingVideos(jCertifManager).addVideo(video)){
		 						showWelcomMsg("Vidéo ajoutée avec succès");
		 						Intent intent = new Intent(AddRubrique.this, Principale.class);
		 				    	startActivityForResult(intent, 1000);
		 				    	finish();
		 					}else{
		 						showWelcomMsg("Problème lors de l'ajout de la vidéo");
		 						
		 						jCertifApplication.setPageInvisible(loading_body);
		 						jCertifApplication.setPageVisible(body);
		 					}
		 				}else
		 					showWelcomMsg("Veuillez indiquer au moin le titre et l'url de votre vidéo");
		 				
		 				isSelected = false;
		 			}
				});
			}
		}.start();
		
	}

	private void addCategory() {
		new Thread(){
			@Override
			public void run(){
				AddRubrique.this.runOnUiThread(new Runnable() {
		 			@Override 
		 			public void run(){
		 				if(title_category.getText().toString().length()>0){
		 					
		 					jCertifApplication.setPageInvisible(body);
		 					jCertifApplication.setPageVisible(loading_body);
		 					
		 					Category category = new Category();
		 					category.setName(title_category.getText().toString());
		 					category.setUrl_img(url_img_category.getText().toString());
		 					
		 					if(new ParsingCategories(jCertifManager).addCategory(category)){
		 						showWelcomMsg("Catégorie ajouter avec succès");
		 						
		 						new Thread(){
		 							@Override
		 							public void run(){
		 								try{
		 									new ParsingCategories(jCertifManager).getAllCategories();
		 								}catch(Exception e){}
		 							}
		 						}.start();
		 						
		 						Intent intent = new Intent(AddRubrique.this, Principale.class);
		 				    	startActivityForResult(intent, 1000);
		 				    	finish();
		 					}else{
		 						showWelcomMsg("Problème lors de l'ajout de la catégorie");
		 						
		 						jCertifApplication.setPageInvisible(loading_body);
		 						jCertifApplication.setPageVisible(body);
		 					}
		 				}else
		 					showWelcomMsg("Veuillez indiquer au moin le titre de votre catégorie");

		 				isSelected = false;
		 			}
				});
			}
		}.start();
		
	}

	private void addEvent() {
		new Thread(){
			@Override
			public void run(){
				AddRubrique.this.runOnUiThread(new Runnable() {
		 			@Override 
		 			public void run(){
		 				if(title_event.getText().toString().length()>0 && content_event.getText().toString().length()>0){
		 					
		 					jCertifApplication.setPageInvisible(body);
		 					jCertifApplication.setPageVisible(loading_body);
		 					
		 					Event event = new Event();
		 					event.setTitle(title_event.getText().toString());
		 					event.setUrl(url_event.getText().toString());
		 					event.setImg_url(url_img_event.getText().toString());
		 					event.setVille(ville_event.getText().toString());
		 					event.setContry(payes_event.getText().toString());
		 					event.setDate_start(date_debut_event.getText().toString());
		 					event.setDate_finish(date_fin_event.getText().toString());
		 					event.setDescription(content_event.getText().toString());
		 					event.setUser(jCertifManager.getCurrentUser());
		 					
		 					if(new ParsingEvents(jCertifManager).addEvent(event)){
		 						showWelcomMsg("Evènement ajouté avec succès");
		 						
		 						new Thread(){
		 							@Override
		 							public void run(){
		 								try{
		 									new ParsingEvents(jCertifManager).getAllEvents();
		 								}catch(Exception e){}
		 							}
		 						}.start();
		 						
		 						Intent intent = new Intent(AddRubrique.this, Principale.class);
		 				    	startActivityForResult(intent, 1000);
		 				    	finish();
		 					}else{
		 						showWelcomMsg("Problème lors de l'ajout de l'évènement");	
		 						jCertifApplication.setPageInvisible(loading_body);
		 						jCertifApplication.setPageVisible(body);
		 					}
		 				}else
		 					showWelcomMsg("Veuillez indiquer au moin le titre et la description de votre évènement");	
		 				
		 				isSelected = false;
		 			}
				});
			}
		}.start();
		
	}

	private void addNews() {
		new Thread(){
			@Override
			public void run(){
				AddRubrique.this.runOnUiThread(new Runnable() {
		 			@Override 
		 			public void run(){
		 				if(title_new.getText().toString().length()>0 && content_news.getText().toString().length()>0){
		 					
		 					jCertifApplication.setPageInvisible(body);
		 					jCertifApplication.setPageVisible(loading_body);
		 					
		 					News news = new News();
		 					news.setTitle(title_new.getText().toString());
		 					news.setUrl(url_news.getText().toString());
		 					news.setImg_url(url_img_news.getText().toString());
		 					news.setContent(content_news.getText().toString());
		 					news.setUser(jCertifManager.getCurrentUser());
		 					
		 					if(new ParsingNews(jCertifManager).AddNews(news)){
		 						showWelcomMsg("Actualité ajoutée avec succès");
		 						
		 						new Thread(){
		 							@Override
		 							public void run(){
		 								try{
		 									new ParsingNews(jCertifManager).getAllNews();
		 								}catch(Exception e){}
		 							}
		 						}.start();
		 						
		 						Intent intent = new Intent(AddRubrique.this, Principale.class);
		 				    	startActivityForResult(intent, 1000);
		 				    	finish();
		 					}else{
		 						showWelcomMsg("Problème lors de l'ajout de l'actualité");	
		 						jCertifApplication.setPageInvisible(loading_body);
		 						jCertifApplication.setPageVisible(body);
		 					}
		 						
		 				}else
		 					showWelcomMsg("Veuillez indiquer au moin le titre et la description de votre actualité");
		 				
		 				isSelected = false;
		 			}
				});
			}
		}.start();		
		
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

	protected void desableCancel() {
		annuler.setBackgroundResource(R.drawable.shape_whate);
		annuler.setTextColor(Color.rgb(79, 129, 189));
		
		Intent intent = new Intent(AddRubrique.this, Principale.class);
    	startActivityForResult(intent, 1000);
    	finish();
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
