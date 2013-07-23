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
import com.jcertif.reseaujcertif.com.ParsingComments;
import com.jcertif.reseaujcertif.com.ParsingNews;
import com.jcertif.reseaujcertif.interfaces.services.CommentsAdapter;
import com.jcertif.reseaujcertif.persistances.Comment;
import com.jcertif.reseaujcertif.persistances.News;
import com.jcertif.reseaujcertif.services.JCertifApplication;
import com.jcertif.reseaujcertif.services.JCertifManager;
import com.jcertif.reseaujcertif.services.Parametres;

public class NewsFragement extends SherlockFragment{
	
	private JCertifManager jCertifManager;
	private JCertifApplication jCertifApplication;
	private LinearLayout mViewGroupe, lastImg, loading, loading_body, news_all_layout, detail_news, return_page, loading_website, edit_news, add_comment;
	private RelativeLayout img_profil, website, img_profil_edit;
	private ListView list, list_comment;
	private TextView news_title, title_news, auteur, date, auteur_edit, date_edit, modifier, txt_add_comment, annuler;
	private Button afficher_web, mail, share;
	private CheckBox mFacebookBtn;
	private WebView content, page_web;
	private String path = "";
	private View head = null;
	private FragmentActivity context;
	private News newsSelected;
	private EditText title, url_article, content_news;
	private Handler mHandler;
	private Timer titleTimer;
	private int btnSelected;
	private boolean isSelected = false;
	private ProgressDialog mProgress;
	private Facebook mFacebook;
		

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		mViewGroupe	= (LinearLayout) inflater.inflate(R.layout.news_layout,null);
		
        titleTimer = new Timer();
		mHandler = new Handler();
		
		loading 		= (LinearLayout) mViewGroupe.findViewById(R.id.loading);
		lastImg 		= (LinearLayout) mViewGroupe.findViewById(R.id.last_news_img);
		loading_body	= (LinearLayout) mViewGroupe.findViewById(R.id.loading_body);
		news_all_layout	= (LinearLayout) mViewGroupe.findViewById(R.id.news_all_layout);
		detail_news		= (LinearLayout) mViewGroupe.findViewById(R.id.detail_news);
		return_page 	= (LinearLayout) mViewGroupe.findViewById(R.id.return_page);
		loading_website = (LinearLayout) mViewGroupe.findViewById(R.id.loading_website);
		edit_news		= (LinearLayout) mViewGroupe.findViewById(R.id.edit_news);

		website 		= (RelativeLayout) mViewGroupe.findViewById(R.id.website);
		img_profil_edit = (RelativeLayout) mViewGroupe.findViewById(R.id.img_profil_edit);
		
		list			= (ListView) mViewGroupe.findViewById(R.id.list);	
		list_comment	= (ListView) mViewGroupe.findViewById(R.id.list_comment);	
		
		title 			= (EditText) mViewGroupe.findViewById(R.id.title_new);
		url_article 	= (EditText) mViewGroupe.findViewById(R.id.url_img_news);
		content_news 	= (EditText) mViewGroupe.findViewById(R.id.content_news);
		
		page_web		= (WebView) mViewGroupe.findViewById(R.id.page_web);	
		
		news_title  	= (TextView) mViewGroupe.findViewById(R.id.news_title);	
		auteur_edit 	= (TextView) mViewGroupe.findViewById(R.id.auteur_edit);
		date_edit 		= (TextView) mViewGroupe.findViewById(R.id.date_edit);
		modifier 		= (TextView) mViewGroupe.findViewById(R.id.modifier);
		annuler 		= (TextView) mViewGroupe.findViewById(R.id.annuler);
		
		jCertifManager = JCertifManager.getInstance();
        jCertifApplication =  (JCertifApplication) getActivity().getApplicationContext();

		context = getActivity();
		
		jCertifManager.setRubrique("home");
		
	    View customView = inflater.inflate(R.layout.title_layout, null);
	    
	    TextView titleTV = (TextView) customView.findViewById(R.id.bar_title);
	    titleTV.setText("Actualités");
	    titleTV.setTextSize(20);
		
		getSherlockActivity().getSupportActionBar().setDisplayShowTitleEnabled(false);
	    getSherlockActivity().getSupportActionBar().setCustomView(customView);
	    getSherlockActivity().getSupportActionBar().setDisplayShowCustomEnabled(true);
	    
	    mProgress	= new ProgressDialog(context);	
		mFacebook 	= new Facebook(jCertifApplication.APP_ID);
		SessionStore.restore(mFacebook, context);

	    list.setOnItemLongClickListener(new OnItemLongClickListener() {
            public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, final long id) {
            	if(jCertifManager.getCurrentUser().isAdmin())
            		ShowDialog(position);
                return true;
            }
        });
	    
	    list_comment.setOnItemLongClickListener(new OnItemLongClickListener() {
            public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, final long id) {
            	
            	if(jCertifManager.getCurrentUser().isAdmin()){
            		AlertDialog alertDialog = new AlertDialog.Builder(context).create();

            		alertDialog.setTitle("Supprimer ce commentaire");
            		alertDialog.setMessage("Est ce que vous êtes sûr ?");
            		alertDialog.setButton("Oui", new DialogInterface.OnClickListener() {
            			public void onClick(DialogInterface dialog, int which) {
            				DelComment(position-1);
            				return;
            			} }); 
            		alertDialog.setButton2("Non", new DialogInterface.OnClickListener() {
            			public void onClick(DialogInterface dialog, int which) {
            				dialog.dismiss();
            				return;
            			}});


            		alertDialog.show();
            	}
            	
                return true;
            }
        });

		lastImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				newsSelected = jCertifManager.getListNews().get(0);
				jCertifManager.setRubrique("news");
				setComments();
			}
		});
		
		annuler.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isSelected)
					enableCancel();
			}
		});
		
		modifier.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isSelected){
					isSelected = true;
					enableModifier();
				}
			}
		});
		
		new AsyncTask<String, Long, Bitmap>() {

			protected void onPostExecute(Bitmap result) {
				context.runOnUiThread(new Runnable() {
		 			@Override public void run(){
		 				if(!jCertifManager.getListNews().isEmpty()){
		 					setBody();
		 					loading_body.setVisibility(View.GONE);
		 					jCertifApplication.setPageVisible(news_all_layout);		 					
		 				}else{
		 					jCertifApplication.setPageInvisible(loading_body);
		 					Toast.makeText(context, "Aucune actualité n'est disponible", Toast.LENGTH_LONG).show();	
		 				}
		 			}
				});	
				
				this.cancel(true);
			}

			protected Bitmap doInBackground(String... params) {
				try{
					if(jCertifManager.getListNews().size()==0)
						new ParsingNews(jCertifManager).getAllNews();
				}catch(Exception e){}
				return null;  
			}
		}.execute("");

		return mViewGroupe;
	}
	
	protected void DelComment(final int position) {
				
		new AsyncTask<String, Long, Bitmap>() {

			protected void onPostExecute(Bitmap result) {}

			protected Bitmap doInBackground(String... params) {
				if(new ParsingComments(jCertifManager).delComment(jCertifManager.getListComments().get(position).getId())){
					
					context.runOnUiThread(new Runnable() {
	 					@Override public void run(){
	 						showWelcomMsg("Suppression du commentaire effectuée"); 			
	 						
	 						try{
	 							new ParsingComments(jCertifManager).getCommentsByNews(newsSelected.getId());
	 						}catch(Exception e){
	 							Toast.makeText(context, "Problème de connexion", Toast.LENGTH_LONG).show();
	 						}
	 						
	 						list_comment.setAdapter(new CommentsAdapter(context, R.layout.single_news, jCertifManager.getListComments()));
	 					}
					});
										
				}else
					showWelcomMsg("Erreur lors de la suppression du commentaire");
				
				return null;  
			}
		}.execute("");
		
	}

	protected void enableCancel() {
		isSelected = true;
		annuler.setBackgroundResource(R.drawable.shape);
		annuler.setTextColor(Color.rgb(255, 255, 255));
		btnSelected = 3;
		refreshTimer();
	}
	
	private void enableModifier(){
		modifier.setBackgroundResource(R.drawable.shape);
		modifier.setTextColor(Color.rgb(255, 255, 255));
		btnSelected = 1;
		refreshTimer();
	}
	
	private void enableAddComment(){
		add_comment.setBackgroundResource(R.drawable.shape);
		txt_add_comment.setTextColor(Color.rgb(255, 255, 255));
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
				 desableAddComment();
				 break;
			 case 3:
				 desableCancel();
				 break;
			 case 4:
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
	 
	protected void desableCancel() {
		annuler.setBackgroundResource(R.drawable.shape_whate);
		annuler.setTextColor(Color.rgb(79, 129, 189));

		edit_news.setVisibility(View.GONE);
		news_all_layout.setVisibility(View.VISIBLE);
		
		isSelected = false;
	}

	protected void desableAddComment() {

        add_comment.setBackgroundResource(R.drawable.shape_whate);
        txt_add_comment.setTextColor(Color.rgb(79, 129, 189));
		
		LayoutInflater factory = LayoutInflater.from(getActivity());
        View alertDialogView = factory.inflate(R.layout.add_comment, null);

        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
 
        adb.setView(alertDialogView);
        adb.setTitle("Ajouter commentaire");
        adb.setIcon(android.R.drawable.ic_dialog_alert);
        
        final RelativeLayout img_my_profil = (RelativeLayout) alertDialogView.findViewById(R.id.img_profil);
        final EditText commentaire = (EditText) alertDialogView.findViewById(R.id.commentaire);
        
		new AsyncTask<String, Long, Bitmap>() {
			protected void onPostExecute(final Bitmap result) {
				context.runOnUiThread(new Runnable() {
 					@Override public void run(){
 						if(result != null){
 							BitmapDrawable bitmapDrawable = new BitmapDrawable(result);
 							img_my_profil.setBackgroundDrawable(bitmapDrawable);
 						}
 					}
				});
						
				this.cancel(true);
 					
			}
			@Override
			protected Bitmap doInBackground(String... params) { 
				return jCertifApplication.ImageOperations(new com.jcertif.reseaujcertif.services.Parametres().getImgULR(jCertifManager.getCurrentUser().getPhoto_url()));    
			}
		}.execute("");
        
        adb.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	
            	new Thread(){
            		@Override
            		public void run(){
            			context.runOnUiThread(new Runnable() {
	     					@Override public void run(){
	     						try{
		     						Comment comment = new Comment(commentaire.getText().toString(), jCertifManager.getCurrentUser(), newsSelected.getId(), 0, 0, 0);
		     						if(new ParsingComments(jCertifManager).addComment(comment)){
		     							new ParsingComments(jCertifManager).getCommentsByNews(newsSelected.getId());     							
		     							list_comment.setAdapter(new CommentsAdapter(context, R.layout.single_news, jCertifManager.getListComments()));	     							
		     						}	
	     						}catch(Exception e){
	     							Toast.makeText(context, "Problème de connexion", Toast.LENGTH_LONG).show();
	     						}
	    						isSelected = false;
	     					}
            			});
            		}
            	}.start();
				
            } 
        });
 
        adb.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	dialog.dismiss();
            	isSelected = false;
            } 
        });
 
        adb.show();
	}

	private void Modifier() {
		
		new AsyncTask<String, Long, Bitmap>() {

			protected void onPostExecute(Bitmap result) {
				edit_news.setVisibility(View.GONE);
				news_all_layout.setVisibility(View.VISIBLE);
				
				this.cancel(true);
			}

			protected Bitmap doInBackground(String... params) {
				context.runOnUiThread(new Runnable() {
 					@Override public void run(){
 						try{
 							
							newsSelected.setTitle(title.getText().toString());
							newsSelected.setUrl(url_article.getText().toString());
							newsSelected.setContent(content_news.getText().toString());
							
							if(new ParsingNews(jCertifManager).UpdateNews(newsSelected)){
								showWelcomMsg("Actualité modifier avec succés");		
								try{
									new ParsingNews(jCertifManager).getAllNews();
	 							}catch(Exception e){
	 								Toast.makeText(context, "Problème de connexion", Toast.LENGTH_LONG).show();
	 							}
								setBody();
								
							}else
								showWelcomMsg("Probléme lors de la modification de l'actualité");
							
							reLoadNews();
 						}catch(Exception e){
 							Toast.makeText(context, "Problème de connexion", Toast.LENGTH_LONG).show();
 						}
 					
 					}
				});
				
				return null;  
			}
		}.execute("");
				
	}

	public void reLoadNews(){
		

		new AsyncTask<String, Long, Bitmap>() {

			protected void onPostExecute(Bitmap result) {
				context.runOnUiThread(new Runnable() {
		 			@Override public void run(){
		 				if(!jCertifManager.getListNews().isEmpty()){
		 					setBody();
		 				}else
		 					Toast.makeText(context, "Aucune actualité n'est disponible", Toast.LENGTH_LONG).show();	
		 			}
				});	
				
				this.cancel(true);
			}

			protected Bitmap doInBackground(String... params) {
				try{
					new ParsingNews(jCertifManager).getAllNews();
				}catch(Exception e){
					Toast.makeText(context, "Problème de connexion", Toast.LENGTH_LONG).show();
				}
				
				return null;  
			}
		}.execute("");
	}
	
	public void ShowDialog(final int position){
		final Dialog dialog = new Dialog(context);
		dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		dialog.setContentView(R.layout.menu_contextuel);
		dialog.setTitle(jCertifManager.getListNews().get(position).getTitle());

		final LinearLayout edit = (LinearLayout) dialog.findViewById(R.id.edit); 
		final LinearLayout del  = (LinearLayout) dialog.findViewById(R.id.del); 
		final LinearLayout exit = (LinearLayout) dialog.findViewById(R.id.exit);
		
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
				delNews(position);
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
		edit_news.setVisibility(View.VISIBLE);
		
		jCertifManager.setRubrique("news");
		
		newsSelected = jCertifManager.getListNews().get(position);
		
		title.setText(newsSelected.getTitle());
		url_article.setText(newsSelected.getUrl());
		content_news.setText(newsSelected.getContent());
		
		auteur_edit.setText(newsSelected.getUser().getFirstname() + " " +newsSelected.getUser().getName());
		date_edit.setText(newsSelected.getCreated());
		
		path = new Parametres().getImgULR(newsSelected.getUser().getPhoto_url());

		new AsyncTask<String, Long, Bitmap>() {
			
			protected void onPostExecute(Bitmap result) {
				BitmapDrawable bitmapDrawable = new BitmapDrawable(result);
				img_profil_edit.setBackgroundDrawable(bitmapDrawable);
				this.cancel(true);
			}
			@Override
			protected Bitmap doInBackground(String... params) { 
				return jCertifApplication.ImageOperations(path);    
			}
		}.execute("");
	}

	protected void delNews(final int position) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();

		alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		alertDialog.setTitle("Supprimer");
		alertDialog.setMessage("Supprimer cette actualitée ?");
		alertDialog.setButton("Oui", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				new Thread(){
					@Override
					public void run(){
						context.runOnUiThread(new Runnable() {
		 					@Override public void run(){
		 						if(new ParsingNews(jCertifManager).DelNews(jCertifManager.getListNews().get(position).getId())){
		 							showWelcomMsg("Actualité supprimée avec succés");
		 							reLoadNews();
		 						}else
		 							showWelcomMsg("Porbléme lors de la suppression de l'actualité");
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
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		toast.show();
	}

	protected void sendMail() {
		String to = "";
		String subject = newsSelected.getTitle();
		String message = "<p align=\"justify\"><font size='2'><h3>" + newsSelected.getTitle() + "</h3></font></p>"
						+"<p align=\"justify\"><font size='2'>Publiée le " + newsSelected.getCreated() + ", par <b>"+newsSelected.getUser().getFirstname()+" "+newsSelected.getUser().getName()+"</b></font></p>"
						+"<p align=\"justify\"><font size='2'>" + newsSelected.getContent() + "</font></p>"
						+"<p align=\"justify\"><font size='2'><a href=\"" + newsSelected.getUrl() + "\">Lien de l'article</a></font></p>";
				
		Intent email = new Intent(Intent.ACTION_SEND);
		email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
		email.putExtra(Intent.EXTRA_SUBJECT, subject);
		email.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(message));

		email.setType("message/rfc822");

		startActivity(Intent.createChooser(email, "Veuillez selectionner un client Email :"));
	}

	private void setBody(){
		
		path = new Parametres().getImgULR(jCertifManager.getListNews().get(0).getImg_url());

		new AsyncTask<String, Long, Bitmap>() {
			
			protected void onPostExecute(Bitmap result) {
				BitmapDrawable bitmapDrawable = new BitmapDrawable(result);
				lastImg.setBackgroundDrawable(bitmapDrawable);
				loading.setVisibility(View.GONE);
				this.cancel(true);
			}
			@Override
			protected Bitmap doInBackground(String... params) { 
				return jCertifApplication.ImageOperations(path);    
			}
		}.execute("");
		
		
		news_title.setText(jCertifManager.getListNews().get(0).getTitle());
		
		list.setAdapter(new NewsAdapter(context, R.layout.single_news, jCertifManager.getListNews()));
		
		list.setOnItemClickListener(new OnItemClickListener() {    	 
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id) {
				newsSelected = jCertifManager.getListNews().get(position);
				jCertifManager.setRubrique("news");
				setComments();
			}
		});
		
	}
	
	public class NewsAdapter extends ArrayAdapter<News>{

		public NewsAdapter(Context context, int textViewResourceId, List<News> objects){
			super(context, textViewResourceId, objects);
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent){

			LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(R.layout.single_news, null);

			TextView title_news = (TextView) convertView.findViewById(R.id.title_news);
			TextView publier = (TextView) convertView.findViewById(R.id.publier);
			final LinearLayout loading = (LinearLayout) convertView.findViewById(R.id.loading);
			final ImageView img_news = (ImageView) convertView.findViewById(R.id.img_news);

			if(jCertifManager.getListNews().get(position).getTitle().length()>0)
				title_news.setText(jCertifManager.getListNews().get(position).getTitle());
			
			if(jCertifManager.getListNews().get(position).getCreated().length()>0)
				publier.setText("Publié le "+jCertifManager.getListNews().get(position).getCreated().substring(0, jCertifManager.getListNews().get(position).getCreated().indexOf(" ")));
			
			new AsyncTask<String, Long, Bitmap>() {
				protected void onPostExecute(Bitmap result) {
					if (result != null) {
						BitmapDrawable background = new BitmapDrawable(result);
						img_news.setBackgroundDrawable(background);
						
						loading.setVisibility(View.GONE);
						img_news.setVisibility(View.VISIBLE);
					} 

					result = null;
					this.cancel(true);
				}
				@Override
				protected Bitmap doInBackground(String... params) { 
					return jCertifApplication.ImageOperations(new Parametres().getImgULR(jCertifManager.getListNews().get(position).getImg_url()));    
				}
			}.execute("");

			return convertView;
		}

	}
	
	private void setComments(){
		new AsyncTask<Void, Void, Void>(){
			@Override
			protected Void doInBackground(Void... arg0) {
				context.runOnUiThread(new Runnable() {
 					@Override public void run(){
						try{
							new ParsingComments(jCertifManager).getCommentsByNews(newsSelected.getId());	
						}catch(Exception e){
							Toast.makeText(context, "Problème de connexion", Toast.LENGTH_LONG).show();
						}
 					}
				});
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				context.runOnUiThread(new Runnable() {
 					@Override public void run(){
 						
 						if(head != null)
 							list_comment.removeHeaderView(head);
 						
 						head = getHeader();
 						list_comment.addHeaderView(head);
 						
						list_comment.setAdapter(new CommentsAdapter(context, R.layout.single_news, jCertifManager.getListComments()));
						
						jCertifManager.setRubrique("news");
						
						jCertifApplication.setPageInvisible(news_all_layout);
						jCertifApplication.setPageVisible(detail_news);
 					}
				});
			}	
			
		}.execute();
	}
	
	private View getHeader(){
		
		View headerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.header_detail_news, null, false);

		content			= (WebView)  headerView.findViewById(R.id.content);	
		
		img_profil		= (RelativeLayout) headerView.findViewById(R.id.img_profil);
		
		auteur 			= (TextView) headerView.findViewById(R.id.auteur);
		date 			= (TextView) headerView.findViewById(R.id.date);	
		title_news  	= (TextView) headerView.findViewById(R.id.title_news);
		txt_add_comment = (TextView) headerView.findViewById(R.id.txt_add_comment);
		
		afficher_web    = (Button) headerView.findViewById(R.id.afficher_web);
		mail  			= (Button) headerView.findViewById(R.id.mail);
		share 			= (Button) headerView.findViewById(R.id.share);
		
		mFacebookBtn	= (CheckBox) headerView.findViewById(R.id.cb_facebook);

		add_comment 	= (LinearLayout) headerView.findViewById(R.id.add_comment);
		
        title_news.setSelected(true);
        auteur.setSelected(true);
        date.setSelected(true);

		title_news.setText(newsSelected.getTitle());
		String text = "<html><head></head><body><p align=\"justify\"><font size='2'>" + newsSelected.getContent() +"</font></p>";
    	content.loadDataWithBaseURL(text, text, "text/html", "utf-8", "");
    	content.setWebViewClient(new WebViewClient());
    	content.setBackgroundColor(0);
		content.setBackgroundColor(0x00000000);
		
		if (mFacebook.isSessionValid()) {
			mFacebookBtn.setChecked(true);
				
			String name = SessionStore.getName(context);
			name		= (name.equals("")) ? "Unknown" : name;
				
			mFacebookBtn.setText("connecté (" + name + ")");
		}	    
		
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
				return jCertifApplication.ImageOperations(new com.jcertif.reseaujcertif.services.Parametres().getImgULR(newsSelected.getUser().getPhoto_url()));    
			}
		}.execute("");
		
		auteur.setText(newsSelected.getUser().getFirstname()+" "+newsSelected.getUser().getName());
		date.setText("Publié le "+newsSelected.getCreated().substring(0, newsSelected.getCreated().indexOf(" ")));
		
		if(newsSelected.getUrl().length()>0){
			loading_website.setVisibility(View.VISIBLE);
			
			page_web.loadUrl(newsSelected.getUrl());
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
		}else
			afficher_web.setVisibility(View.GONE);
		
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
		
		afficher_web.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isSelected){
					isSelected = true;
					website.setVisibility(View.VISIBLE);
					detail_news.setVisibility(View.GONE);					
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
		
		add_comment.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
		        if(!isSelected){
		        	isSelected = true;
		        	enableAddComment();
		        }
				
			}
		});

		return headerView;
	}
	
	protected void enableReturn() {
		isSelected = true;
		return_page.setBackgroundResource(R.drawable.return_on);
		btnSelected = 4;
		refreshTimer();
	}
	
	protected void desableReturn() {
		return_page.setBackgroundResource(R.drawable.return_off);
		website.setVisibility(View.GONE);
		detail_news.setVisibility(View.VISIBLE);
		isSelected = false;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
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
           Toast.makeText(context, "Connection à Facebook echouée", Toast.LENGTH_SHORT).show();
           
           mFacebookBtn.setChecked(false);
        }
        
        public void onError(DialogError error) {
        	Toast.makeText(context, "Connection à Facebook echouée", Toast.LENGTH_SHORT).show(); 
        	
        	mFacebookBtn.setChecked(false);
        }

        public void onCancel() {
        	mFacebookBtn.setChecked(false);
        }
    }
    
	private void getFbName() {
		mProgress.setMessage("Finalisation ...");
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
		mProgress.setMessage("Désconnexion de Facebook...");
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
		         
		        Toast.makeText(context, "Connecté à Facebook : " + username, Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(context, "Connecté à Facebook", Toast.LENGTH_SHORT).show();
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
		params.putString("name", newsSelected.getTitle());
		params.putString("caption", "JCertif");
		params.putString("link", newsSelected.getUrl());
		params.putString("description", newsSelected.getContent());
		
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
