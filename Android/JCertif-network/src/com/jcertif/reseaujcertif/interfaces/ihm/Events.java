package com.jcertif.reseaujcertif.interfaces.ihm;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.BaseRequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.android.SessionStore;
import com.jcertif.reseaujcertif.R;
import com.jcertif.reseaujcertif.com.ParsingEvents;
import com.jcertif.reseaujcertif.persistances.Event;
import com.jcertif.reseaujcertif.services.JCertifApplication;
import com.jcertif.reseaujcertif.services.JCertifManager;
import com.jcertif.reseaujcertif.services.Parametres;

public class Events extends SherlockFragment{
	
	private JCertifManager jCertifManager;
	private JCertifApplication jCertifApplication;
	private LinearLayout mViewGroupe, lastImg, loading, loading_body, news_all_layout, detail_news, loading_website, lieu_layout, return_page, loading_img, img, date_debut_layout, date_fin_layout;
	private RelativeLayout website, edit_event;
	private ListView list;
	private EditText title, date_start, date_end, content_event, url_img, url_article, ville_forum, payes_forum;
	private TextView news_title, title_news, date_debut_txt, date_debut, date_fin_txt, date_fin, modifier, annuler, lieu_txt, lieu, url_txt, url_article_txt;
	private Button mail, link, share;
	private WebView content, page_web;
	private String path = "";
	private FragmentActivity context;
	private Event eventSelected;
	private boolean isSelected;
	private Handler mHandler;
	private Timer titleTimer;
	private int btnSelected;
	private CheckBox mFacebookBtn;
	private ProgressDialog mProgress;
	private Facebook mFacebook;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		mViewGroupe		= (LinearLayout) inflater.inflate(R.layout.events_layout,null);
		
		loading 		= (LinearLayout) mViewGroupe.findViewById(R.id.loading);
		lastImg 		= (LinearLayout) mViewGroupe.findViewById(R.id.last_news_img);
		loading_body	= (LinearLayout) mViewGroupe.findViewById(R.id.loading_body);
		loading_website = (LinearLayout) mViewGroupe.findViewById(R.id.loading_website);
		news_all_layout	= (LinearLayout) mViewGroupe.findViewById(R.id.news_all_layout);
		detail_news		= (LinearLayout) mViewGroupe.findViewById(R.id.detail_news);
		return_page		= (LinearLayout) mViewGroupe.findViewById(R.id.return_page);
		img 			= (LinearLayout) mViewGroupe.findViewById(R.id.img);
		lieu_layout		= (LinearLayout) mViewGroupe.findViewById(R.id.lieu_layout);
		loading_img		= (LinearLayout) mViewGroupe.findViewById(R.id.loading_img);
		date_fin_layout = (LinearLayout) mViewGroupe.findViewById(R.id.date_fin_layout);
		date_debut_layout = (LinearLayout) mViewGroupe.findViewById(R.id.date_debut_layout);
		
		website			= (RelativeLayout) mViewGroupe.findViewById(R.id.website);
		edit_event		= (RelativeLayout) mViewGroupe.findViewById(R.id.edit_event);
		
		list			= (ListView) mViewGroupe.findViewById(R.id.list);		
		
		content			= (WebView) mViewGroupe.findViewById(R.id.content);	
		page_web		= (WebView) mViewGroupe.findViewById(R.id.page_web);	
		
		title			= (EditText) mViewGroupe.findViewById(R.id.title_new);	
		date_start		= (EditText) mViewGroupe.findViewById(R.id.date_start);	
		date_end		= (EditText) mViewGroupe.findViewById(R.id.date_end);	
		content_event	= (EditText) mViewGroupe.findViewById(R.id.content_event);	
		url_img			= (EditText) mViewGroupe.findViewById(R.id.url_img);	
		url_article		= (EditText) mViewGroupe.findViewById(R.id.url_img_news);
		ville_forum		= (EditText) mViewGroupe.findViewById(R.id.ville_forum);
		payes_forum		= (EditText) mViewGroupe.findViewById(R.id.payes_forum);
		
		title_news  	= (TextView) mViewGroupe.findViewById(R.id.title_news);
		news_title  	= (TextView) mViewGroupe.findViewById(R.id.news_title);
		date_debut_txt  = (TextView) mViewGroupe.findViewById(R.id.date_debut_txt);
		date_debut  	= (TextView) mViewGroupe.findViewById(R.id.date_debut);
		date_fin_txt  	= (TextView) mViewGroupe.findViewById(R.id.date_fin_txt);
		date_fin  		= (TextView) mViewGroupe.findViewById(R.id.date_fin);
		annuler  		= (TextView) mViewGroupe.findViewById(R.id.annuler);
		modifier  		= (TextView) mViewGroupe.findViewById(R.id.modifier);
		lieu_txt  		= (TextView) mViewGroupe.findViewById(R.id.lieu_txt);
		lieu	  		= (TextView) mViewGroupe.findViewById(R.id.lieu);
		url_article_txt = (TextView) mViewGroupe.findViewById(R.id.url_article_txt);
		url_txt 		= (TextView) mViewGroupe.findViewById(R.id.url_txt);
		
		mFacebookBtn	= (CheckBox) mViewGroupe.findViewById(R.id.cb_facebook);
		
		share 			= (Button) mViewGroupe.findViewById(R.id.share);		
		mail  			= (Button) mViewGroupe.findViewById(R.id.mail);
		link 			= (Button) mViewGroupe.findViewById(R.id.link);
		
		news_title.setSelected(true);
		title_news.setSelected(true);
		date_debut_txt.setSelected(true);
		date_debut.setSelected(true);
		date_fin_txt.setSelected(true);
		date_fin.setSelected(true);
		lieu_txt.setSelected(true);
		lieu.setSelected(true);
		url_txt.setSelected(true);
		url_article_txt.setSelected(true);
		
		jCertifManager = JCertifManager.getInstance();
        jCertifApplication =  (JCertifApplication) getActivity().getApplicationContext();
		
		context = getActivity();
		titleTimer = new Timer();
		mHandler   = new Handler();
		 
		isSelected = false;
		jCertifManager.setRubrique("home");
		
	    View customView = inflater.inflate(R.layout.title_layout, null);
	    
	    TextView titleTV = (TextView) customView.findViewById(R.id.bar_title);
	    titleTV.setText("Evènements");
	    titleTV.setTextSize(20);
		
		getSherlockActivity().getSupportActionBar().setDisplayShowTitleEnabled(false);
	    getSherlockActivity().getSupportActionBar().setCustomView(customView);
	    getSherlockActivity().getSupportActionBar().setDisplayShowCustomEnabled(true);
	    
	    mProgress	= new ProgressDialog(context);	
		mFacebook 	= new Facebook(jCertifApplication.APP_ID);
		SessionStore.restore(mFacebook, context);
	    
	    if (mFacebook.isSessionValid()) {
			mFacebookBtn.setChecked(true);
				
			String name = SessionStore.getName(context);
			name		= (name.equals("")) ? "Unknown" : name;
				
			mFacebookBtn.setText("connecté (" + name + ")");
		}
	    
	    share.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isSelected){
					if(mFacebookBtn.isChecked()){
						isSelected = true;
		                ShowFacebookDialogue();
						isSelected = false;
					}else
						showWelcomMsg("Vous devez connecter à votre profil facebook");
				}
			}
		});

		mFacebookBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isSelected){
					isSelected = true;
					onFacebookClick();
					isSelected = false;
				}
			}
		});
	    
	    list.setOnItemLongClickListener(new OnItemLongClickListener() {
            public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, final long id) {
            	if(jCertifManager.getCurrentUser().isAdmin())
            		ShowDialog(position);
                return true;
            }
        });
	    
		lastImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isSelected){
					isSelected = true;
					eventSelected = jCertifManager.getListEvents().get(0);
					shewNewsDetail();
					isSelected = false;
				}
			}
		});
		
		link.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(eventSelected.getUrl().length()>0){
					if(!isSelected){
						isSelected = true;
						website.setVisibility(View.VISIBLE);
						detail_news.setVisibility(View.GONE);
						isSelected = false;
					}
				}else{
					showWelcomMsg("Aucun site n'est associé à cet évènement ");
				}
				
			}
		});
		
		mail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isSelected){
		        	isSelected = true;
		        	sendMail();
		        	isSelected = false;
				}
			}
		});
		
		return_page.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isSelected)
					enableReturn();
			}
		
		});	
		
		modifier.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isSelected){
					isSelected = true;
					EnableModifier();
				}			
			}
		});	
		
		annuler.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isSelected){
					isSelected = true;
					enableCancel();
				}			
			}
		});			
		
		new AsyncTask<String, Long, Bitmap>() {

			protected void onPostExecute(Bitmap result) {
				context.runOnUiThread(new Runnable() {
		 			@Override public void run(){
		 				if(!jCertifManager.getListEvents().isEmpty()){
		 					setBody();
		 					jCertifApplication.setPageInvisible(loading_body);
		 					jCertifApplication.setPageVisible(news_all_layout);
		 				}else
		 					Toast.makeText(context, "Aucun évenement n'est disponible", Toast.LENGTH_LONG).show();	
		 			}
				});	
				
				this.cancel(true);
			}

			protected Bitmap doInBackground(String... params) {
				if(jCertifManager.getListEvents().size()==0)
					new ParsingEvents(jCertifManager).getAllEvents();
				
				return null;
			}
		}.execute("");

		return mViewGroupe;
	}
	
	protected void enableReturn() {
		isSelected = true;
		return_page.setBackgroundResource(R.drawable.return_on);
		btnSelected = 3;
		refreshTimer();
	}

	protected void setWebView() {
		loading_website.setVisibility(View.VISIBLE);
		
		page_web.loadUrl(eventSelected.getUrl());
		page_web.setWebViewClient(new WebViewClient());
		page_web.getSettings().setLoadWithOverviewMode(true);
		page_web.getSettings().setUseWideViewPort(true);
		page_web.getSettings().setBuiltInZoomControls(true);
		page_web.setBackgroundColor(0);
		page_web.setBackgroundColor(0x00000000);
		
		page_web.setWebViewClient(new WebViewClient() {
			
			@Override
		    public boolean shouldOverrideUrlLoading(WebView view, String urlNewString) {
				return true;
			}
			
			@Override
			public void onPageFinished(WebView view, String url) {
				loading_website.setVisibility(View.GONE);
			}
		});
	}


	public void reLoadNews(){
		
		news_all_layout.setVisibility(View.GONE);
		loading_body.setVisibility(View.VISIBLE);
		
		new AsyncTask<String, Long, Bitmap>() {

			protected void onPostExecute(Bitmap result) {
				context.runOnUiThread(new Runnable() {
		 			@Override public void run(){
		 				loading_body.setVisibility(View.GONE);
	 					news_all_layout.setVisibility(View.VISIBLE);
	 					setBody();
		 			}
				});	
				
				this.cancel(true);
			}

			protected Bitmap doInBackground(String... params) {
				new ParsingEvents(jCertifManager).getAllEvents();
				return null;  
			}
		}.execute("");
	}
	
	public void ShowDialog(final int position){
		final Dialog dialog = new Dialog(context);
		dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		dialog.setContentView(R.layout.menu_contextuel);
		dialog.setTitle(jCertifManager.getListEvents().get(position).getTitle());

		final LinearLayout del  = (LinearLayout) dialog.findViewById(R.id.del); 
		final LinearLayout exit = (LinearLayout) dialog.findViewById(R.id.exit);
		final LinearLayout edit = (LinearLayout) dialog.findViewById(R.id.edit);
		
		final TextView edit_txt = (TextView) dialog.findViewById(R.id.edit_txt); 
		final TextView del_txt  = (TextView) dialog.findViewById(R.id.del_txt); 
		final TextView exit_txt = (TextView) dialog.findViewById(R.id.exit_txt);

		edit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				edit.setBackgroundColor(Color.rgb(79, 129, 189));
				edit_txt.setTextColor(Color.rgb(255, 255, 255));
				edit(position);
				dialog.dismiss();
			}
		});
		
		del.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				del.setBackgroundColor(Color.rgb(79, 129, 189));
				del_txt.setTextColor(Color.rgb(255, 255, 255));
				delEvent(position);
				dialog.dismiss();
			}
		});
		
		exit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				exit.setBackgroundColor(Color.rgb(79, 129, 189));
				exit_txt.setTextColor(Color.rgb(255, 255, 255));
				dialog.dismiss();
			}
		});

		dialog.show();
	}
	
	protected void edit(int position) {

		news_all_layout.setVisibility(View.GONE);
		edit_event.setVisibility(View.VISIBLE);
		
		jCertifManager.setRubrique("events");
		
		eventSelected = jCertifManager.getListEvents().get(position);
		
		title.setText(eventSelected.getTitle());
		url_img.setText(eventSelected.getImg_url());
		date_start.setText(eventSelected.getDate_start());
		date_end.setText(eventSelected.getDate_finish());
		content_event.setText(eventSelected.getDescription());		
		url_article.setText(eventSelected.getUrl());
		ville_forum.setText(eventSelected.getVille());
		payes_forum.setText(eventSelected.getContry());
	}

	protected void delEvent(final int position) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();

		alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		alertDialog.setTitle("Supprimer");
		alertDialog.setMessage("Supprimer cet évenement ?");
		alertDialog.setButton("Oui", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				new Thread(){
					@Override
					public void run(){
						context.runOnUiThread(new Runnable() {
		 					@Override public void run(){
		 						if(new ParsingEvents(jCertifManager).DelEvent(jCertifManager.getListEvents().get(position).getId())){
		 							showWelcomMsg("Evènement supprimé avec succès");
		 							reLoadNews();
		 						}else
		 							showWelcomMsg("Porblème lors de la supprission de l'évènement");
		 					}
						});
					}
				}.start();				
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
		LayoutInflater inflater = context.getLayoutInflater();
		View layout = inflater.inflate(R.layout.toast_custom_layout, (ViewGroup) context.findViewById(R.id.toast_layout_root));

		TextView text = (TextView) layout.findViewById(R.id.text);
		text.setText(msg);

		Toast toast = new Toast(context);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();
	}

	protected void sendMail() {
		String to = "";
		String subject = eventSelected.getTitle();
		String message = "<p align=\"justify\"><font size='2'><h3>" + eventSelected.getTitle() + "</h3></font></p>"
						+"<p align=\"justify\"><font size='2'>" + eventSelected.getDescription() + "</font></p>";
				
		Intent email = new Intent(Intent.ACTION_SEND);
		email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
		email.putExtra(Intent.EXTRA_SUBJECT, subject);
		email.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(message));

		email.setType("message/rfc822");

		startActivity(Intent.createChooser(email, "Veuillez selectionner un client Email :"));
	}

	private void setBody(){
		
		path = new Parametres().getImgULR(jCertifManager.getListEvents().get(0).getImg_url());

		new AsyncTask<String, Long, Bitmap>() {
			
			protected void onPostExecute(Bitmap result) {
				if(result!=null){
					BitmapDrawable bitmapDrawable = new BitmapDrawable(result);
					lastImg.setBackgroundDrawable(bitmapDrawable);
				}				
				loading.setVisibility(View.GONE);
				this.cancel(true);
			}
			@Override
			protected Bitmap doInBackground(String... params) { 
				return jCertifApplication.ImageOperations(path);    
			}
		}.execute("");
			
		news_title.setText(jCertifManager.getListEvents().get(0).getTitle());
		
		list.setAdapter(new EventsAdapter(context, R.layout.single_news, jCertifManager.getListEvents()));
		
		list.setOnItemClickListener(new OnItemClickListener() {    	 
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id) {
				eventSelected = jCertifManager.getListEvents().get(position);
				shewNewsDetail();
			}
		});
		
	}
	
	public class EventsAdapter extends ArrayAdapter<Event>{

		public EventsAdapter(Context context, int textViewResourceId, List<Event> objects){
			super(context, textViewResourceId, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent){

			LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(R.layout.single_event, null);

			TextView title_news = (TextView) convertView.findViewById(R.id.title_news);
			TextView debut = (TextView) convertView.findViewById(R.id.debut);
			TextView fin = (TextView) convertView.findViewById(R.id.fin);
			final LinearLayout loading = (LinearLayout) convertView.findViewById(R.id.loading);
			final ImageView img_news = (ImageView) convertView.findViewById(R.id.img_news);
			final String url = new Parametres().getImgULR(jCertifManager.getListEvents().get(position).getImg_url());

			if(jCertifManager.getListEvents().get(position).getTitle().length()>0)
				title_news.setText(jCertifManager.getListEvents().get(position).getTitle());
			
			if(!jCertifManager.getListEvents().get(position).getDate_start().equals("0000-00-00 00:00:00"))
				debut.setText("Début : "+jCertifManager.getListEvents().get(position).getDate_start().substring(0, jCertifManager.getListEvents().get(position).getDate_start().indexOf(" ")));
			
			if(!jCertifManager.getListEvents().get(position).getDate_finish().equals("0000-00-00 00:00:00"))
				fin.setText("Fin : "+jCertifManager.getListEvents().get(position).getDate_finish().substring(0, jCertifManager.getListEvents().get(position).getDate_finish().indexOf(" ")));
				
			new AsyncTask<String, Long, Bitmap>() {

				protected void onPostExecute(Bitmap result) {
					if (result != null) {
						BitmapDrawable background = new BitmapDrawable(result);
						img_news.setBackgroundDrawable(background);
					} 
					
					loading.setVisibility(View.GONE);
					img_news.setVisibility(View.VISIBLE);

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
	
	protected void shewNewsDetail() {
		setDetail();
		
		jCertifManager.setRubrique("events");
		
		jCertifApplication.setPageInvisible(news_all_layout);
		jCertifApplication.setPageVisible(detail_news);
	}

	protected void setDetail() {
		title_news.setText(eventSelected.getTitle());
		
		if(!eventSelected.getDate_start().equals("0000-00-00 00:00:00"))
			date_debut.setText(eventSelected.getDate_start());
		else
			date_debut_layout.setVisibility(View.GONE);
		
		if(!eventSelected.getDate_finish().equals("0000-00-00 00:00:00"))
			date_fin.setText(eventSelected.getDate_finish());
		else
			date_fin_layout.setVisibility(View.GONE);
		
		if(eventSelected.getContry().length()>0 || eventSelected.getVille().length()>0)
			lieu.setText(eventSelected.getContry()+", "+eventSelected.getVille());
		else
			lieu_layout.setVisibility(View.GONE);	
		
		if(eventSelected.getUrl().length()==0)
			link.setVisibility(View.GONE);
		else{
			link.setVisibility(View.VISIBLE);
			setWebView();		
		}
		
    	String text = "<html><head></head><body><p align=\"justify\"><font size='2'>" + eventSelected.getDescription() +"</font></p>";
    	content.loadDataWithBaseURL(text, text, "text/html", "utf-8", "");
    	content.setWebViewClient(new WebViewClient());
    	content.getSettings().setLoadWithOverviewMode(true);
    	content.setBackgroundColor(0);
		content.setBackgroundColor(0x00000000);
		
		new AsyncTask<String, Long, Bitmap>() {
			protected void onPostExecute(Bitmap result) {
				if(result != null){
					BitmapDrawable bitmapDrawable = new BitmapDrawable(result);
					img.setBackgroundDrawable(bitmapDrawable);
				}
				
				loading_img.setVisibility(View.GONE);
				this.cancel(true);
			}
			@Override
			protected Bitmap doInBackground(String... params) { 
				return jCertifApplication.ImageOperations(new com.jcertif.reseaujcertif.services.Parametres().getImgULR(eventSelected.getImg_url()));    
			}
		}.execute("");
				
	}
	
	protected void EnableModifier() {	
		modifier.setBackgroundResource(R.drawable.shape);
		modifier.setTextColor(Color.rgb(255, 255, 255));
		btnSelected = 1;
		refreshTimer();
	}
	
	private void enableCancel(){
		annuler.setBackgroundResource(R.drawable.shape);
		annuler.setTextColor(Color.rgb(255, 255, 255));
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
				 desableModifier();
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
	
	private void desableModifier(){
		modifier.setBackgroundResource(R.drawable.shape_whate);
		modifier.setTextColor(Color.rgb(79, 129, 189));
		Modifier();
		isSelected = false;
	}
	
	protected void desableReturn() {
		return_page.setBackgroundResource(R.drawable.return_off);
		website.setVisibility(View.GONE);
		detail_news.setVisibility(View.VISIBLE);
		isSelected = false;
	}

	private void Modifier() {
		
		edit_event.setVisibility(View.GONE);
		loading_body.setVisibility(View.VISIBLE);
		
		new Thread(){
			@Override
			public void run(){
				context.runOnUiThread(new Runnable() {
 					@Override public void run(){

 						Event event = eventSelected;
 						
 						event.setTitle(title.getText().toString());
 						event.setDescription(content_event.getText().toString());
 						event.setDate_start(date_start.getText().toString());
 						event.setDate_finish(date_end.getText().toString());
 						event.setImg_url(url_img.getText().toString());
 						event.setUrl(url_article.getText().toString());
 						event.setVille(ville_forum.getText().toString());
 						event.setContry(payes_forum.getText().toString());
 
 						if(new ParsingEvents(jCertifManager).updateEvent(event)){
 							showWelcomMsg("Modification éffectuée");
 							try{
 								new ParsingEvents(jCertifManager).getAllEvents();
 							}catch(Exception e){
 								Toast.makeText(context, "Problème de connexion", Toast.LENGTH_LONG).show();
 							}
 							setBody();
 							
 							loading_body.setVisibility(View.GONE);
 							news_all_layout.setVisibility(View.VISIBLE);
 							
 						}else{
 							showWelcomMsg("Erreur lors de la modification");
 							loading_body.setVisibility(View.GONE);
 							edit_event.setVisibility(View.VISIBLE);
 						}
 					}
				});
			}
		}.start();
		
	}

	protected void desableCancel() {
		
		annuler.setBackgroundResource(R.drawable.shape_whate);
		annuler.setTextColor(Color.rgb(79, 129, 189));

		edit_event.setVisibility(View.GONE);
		news_all_layout.setVisibility(View.VISIBLE);
		
		isSelected = false;
	}
	
	private void onFacebookClick() {
		if (mFacebook.isSessionValid()) {
			final AlertDialog.Builder builder = new AlertDialog.Builder(context);
			
			builder.setMessage("Delete current Facebook connection?")
			       .setCancelable(false)
			       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   fbLogout();
			           }
			       })
			       .setNegativeButton("No", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                dialog.cancel();
			                
			                mFacebookBtn.setChecked(true);
			           }
			       });
			
			final AlertDialog alert = builder.create();
			
			alert.show();
		} else {
			mFacebookBtn.setChecked(false);
			
			mFacebook.authorize(context, jCertifApplication.PERMISSIONS, -1, new FbLoginDialogListener());
		}
	}
    
    private final class FbLoginDialogListener implements DialogListener {
        public void onComplete(Bundle values) {
            SessionStore.save(mFacebook, context);
           
            mFacebookBtn.setText("non connecté");
            mFacebookBtn.setChecked(true);
			 
            getFbName();
        }

        public void onFacebookError(FacebookError error) {
           Toast.makeText(context, "Facebook connection failed", Toast.LENGTH_SHORT).show();
           
           mFacebookBtn.setChecked(false);
        }
        
        public void onError(DialogError error) {
        	Toast.makeText(context, "Facebook connection failed", Toast.LENGTH_SHORT).show(); 
        	
        	mFacebookBtn.setChecked(false);
        }

        public void onCancel() {
        	mFacebookBtn.setChecked(false);
        }
    }
    
	private void getFbName() {
		mProgress.setMessage("Finalizing ...");
		mProgress.show();
		
		new Thread() {
			@Override
			public void run() {
		        String name = "";
		        int what = 1;
		        
		        try {
		        	String me = mFacebook.request("me");
		        	
		        	JSONObject jsonObj = (JSONObject) new JSONTokener(me).nextValue();
		        	name = jsonObj.getString("name");
		        	what = 0;
		        } catch (Exception ex) {
		        	ex.printStackTrace();
		        }
		        
		        mFbHandler.sendMessage(mFbHandler.obtainMessage(what, name));
			}
		}.start();
	}
	
	private void fbLogout() {
		mProgress.setMessage("Disconnecting from Facebook");
		mProgress.show();
			
		new Thread() {
			@Override
			public void run() {
				SessionStore.clear(context);
		        	   
				int what = 1;
					
		        try {
		        	mFacebook.logout(context);
		        		 
		        	what = 0;
		        } catch (Exception ex) {
		        	ex.printStackTrace();
		        }
		        	
		        mHandler.sendMessage(mHandler.obtainMessage(what));
			}
		}.start();
	}
	
	private Handler mFbHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mProgress.dismiss();
			
			if (msg.what == 0) {
				String username = (String) msg.obj;
		        username = (username.equals("")) ? "No Name" : username;
		            
		        SessionStore.saveName(username, context);
		        
		        mFacebookBtn.setText("connecté (" + username + ")");
		         
		        Toast.makeText(context, "Connected to Facebook as " + username, Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(context, "Connected to Facebook", Toast.LENGTH_SHORT).show();
			}
		}
	};
	
	private void ShowFacebookDialogue(){
		LayoutInflater factory = LayoutInflater.from(getActivity());
        View alertDialogView = factory.inflate(R.layout.post, null);
        
        final AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
        
        adb.setView(alertDialogView);
        
        final EditText reviewEdit = (EditText) alertDialogView.findViewById(R.id.revieew);
        
        adb.setPositiveButton("Publier", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	String review = reviewEdit.getText().toString();				
				postToFacebook(review);		
				dialog.dismiss();
            } 
        });
 
        adb.show();
	}
	
	private void postToFacebook(String review) {	
		mProgress.setMessage("En cours de publication ...");
		mProgress.show();
		
		AsyncFacebookRunner mAsyncFbRunner = new AsyncFacebookRunner(mFacebook);
		
		Bundle params = new Bundle();
    		
		params.putString("message", review);
		params.putString("name", eventSelected.getTitle());
		params.putString("caption", "JCertif");
		if(eventSelected.getUrl().length()>0)
			params.putString("link", eventSelected.getUrl());
		params.putString("description", eventSelected.getDescription());
		
		mAsyncFbRunner.request("me/feed", params, "POST", new WallPostListener());
	}

	private final class WallPostListener extends BaseRequestListener {
        public void onComplete(final String response) {
        	mRunOnUi.post(new Runnable() {
        		@Override
        		public void run() {
        			mProgress.cancel();
        			
        			Toast.makeText(context, "Publié sur votre mure", Toast.LENGTH_SHORT).show();
        		}
        	});
        }
    }
	
	private Handler mRunOnUi = new Handler();
	
}
