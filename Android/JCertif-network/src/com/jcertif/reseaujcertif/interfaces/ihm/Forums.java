package com.jcertif.reseaujcertif.interfaces.ihm;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.jcertif.reseaujcertif.R;
import com.jcertif.reseaujcertif.com.ParsingCategories;
import com.jcertif.reseaujcertif.com.ParsingComments;
import com.jcertif.reseaujcertif.com.ParsingForum;
import com.jcertif.reseaujcertif.interfaces.services.CommentsAdapter;
import com.jcertif.reseaujcertif.persistances.Category;
import com.jcertif.reseaujcertif.persistances.Comment;
import com.jcertif.reseaujcertif.persistances.Forum;
import com.jcertif.reseaujcertif.services.JCertifApplication;
import com.jcertif.reseaujcertif.services.JCertifManager;
import com.jcertif.reseaujcertif.services.Parametres;

public class Forums extends SherlockFragment{
	
	private JCertifManager jCertifManager;
	private JCertifApplication jCertifApplication;
	private LinearLayout mViewGroupe, loading_body, news_all_layout, detail_news, add_comment, add_subject, ajouter_sujet, add;
	private RelativeLayout img_profil;
	private ListView list, list_comment;
	private TextView title_news, auteur, date,txt_add_comment, txt_add_subject, ajouter, annuler, titleTV; 
	private EditText contenu, titre;
	private WebView content;
	private FragmentActivity context;
	private Category categorySelected;
	private Forum forumSelected;
	private Handler mHandler;
	private Timer titleTimer;
	private int forumID, btnSelected, niveau = 1;
	private boolean isSelected, enableListForum;
	private String msg, etat;
	private View header = null;
	private List<String> listCategories;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		mViewGroupe	= (LinearLayout) inflater.inflate(R.layout.forum,null);
	    View customView = inflater.inflate(R.layout.title_layout, null);
		
        titleTimer = new Timer();
		mHandler = new Handler();
		
		listCategories = new ArrayList<String>();
		
		isSelected = false;
		enableListForum = false;
		
		loading_body	= (LinearLayout) mViewGroupe.findViewById(R.id.loading_body);
		news_all_layout	= (LinearLayout) mViewGroupe.findViewById(R.id.news_all_layout);
		detail_news		= (LinearLayout) mViewGroupe.findViewById(R.id.detail_news);
		add_subject		= (LinearLayout) mViewGroupe.findViewById(R.id.add_subject);
		ajouter_sujet	= (LinearLayout) mViewGroupe.findViewById(R.id.ajouter_sujet);
		add				= (LinearLayout) mViewGroupe.findViewById(R.id.add);
		
		contenu			= (EditText) mViewGroupe.findViewById(R.id.contenu);	
		titre			= (EditText) mViewGroupe.findViewById(R.id.titre);
		
		txt_add_subject	= (TextView) mViewGroupe.findViewById(R.id.txt_add_subject);
		ajouter			= (TextView) mViewGroupe.findViewById(R.id.ajouter);
		annuler			= (TextView) mViewGroupe.findViewById(R.id.annuler);
		titleTV 		= (TextView) customView.findViewById(R.id.bar_title);
		
		list			= (ListView) mViewGroupe.findViewById(R.id.list);	
		list_comment	= (ListView) mViewGroupe.findViewById(R.id.list_comment);
		
		jCertifManager = JCertifManager.getInstance();
        jCertifApplication =  (JCertifApplication) getActivity().getApplicationContext();
		
		context = getActivity();
		
		jCertifManager.setRubrique("home");
		
		add.setVisibility(View.GONE);
	   
	    titleTV.setText("Forums");
	    titleTV.setTextSize(19);
		
		getSherlockActivity().getSupportActionBar().setDisplayShowTitleEnabled(false);
	    getSherlockActivity().getSupportActionBar().setCustomView(customView);
	    getSherlockActivity().getSupportActionBar().setDisplayShowCustomEnabled(true);
	    
	    list.setOnItemLongClickListener(new OnItemLongClickListener() {
            public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, final long id) {
            	if(enableListForum && (jCertifManager.getCurrentUser().isAdmin() || (jCertifManager.getCurrentUser().getId() == jCertifManager.getListForums().get(position).getUser().getId()))){
            		forumSelected = jCertifManager.getListForums().get(position);
            		ShowDialog(position, view);
            	}else if(jCertifManager.getCurrentUser().isAdmin() && !enableListForum){
            		AlertDialog alertDialog = new AlertDialog.Builder(context).create();

            		alertDialog.setTitle("Supprimer cette catégorie");
            		alertDialog.setMessage("Est ce que vous êtes sûr ?");
            		alertDialog.setButton("Oui", new DialogInterface.OnClickListener() {
            			public void onClick(DialogInterface dialog, int which) {
            				DelCategori(position);
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
	    
	    add_subject.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {	
		        if(!isSelected){
		        	isSelected = true;
		        	enableAddSubkect();
		        }
				
			}
		});
	    
	    annuler.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
		        if(!isSelected)
		        	enableReturn();				
			}
		});
	    
	    ajouter.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {			
		        if(!isSelected)
		        	enableAjouter();
			}
		});
		
		new AsyncTask<String, Long, Bitmap>() {

			protected void onPostExecute(Bitmap result) {
				context.runOnUiThread(new Runnable() {
		 			@Override public void run(){
		 				if(!jCertifManager.getListCategories().isEmpty()){
		 					jCertifApplication.setPageInvisible(loading_body);
		 					jCertifApplication.setPageVisible(news_all_layout);
		 					
		 					setBody();
		 				}else{	
		 					showWelcomMsg("Aucune categorie n'est disponible");
		 					jCertifApplication.setPageInvisible(loading_body);
		 				}
		 			}
				});	
				
				this.cancel(true);
			}

			protected Bitmap doInBackground(String... params) {
				try{
					if(jCertifManager.getListCategories().size()==0)
						new ParsingCategories(jCertifManager).getAllCategories();
					
					for(Category category : jCertifManager.getListCategories())
						listCategories.add(category.getName());
					
				}catch(Exception e){
					Toast.makeText(context, "Problème de connexion", Toast.LENGTH_LONG).show();
				}
				return null;  
			}
		}.execute("");
		
		list_comment.setOnItemLongClickListener(new OnItemLongClickListener() {
            public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, final long id) {
            	
            	if(jCertifManager.getCurrentUser().isAdmin()){
            		AlertDialog alertDialog = new AlertDialog.Builder(context).create();

            		alertDialog.setTitle("Supprimer ce commentaire");
            		alertDialog.setMessage("Est ce que vous êtes sûr ?");
            		alertDialog.setButton("Oui", new DialogInterface.OnClickListener() {
            			public void onClick(DialogInterface dialog, int which) {
            				new Thread(){
            					@Override
            					public void run(){
                    				DelComment(position-1);
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
            	
                return true;
            }
        });

		return mViewGroupe;
	}
	
	private void DelCategori(final int position){
		
		new Thread(){
			@Override
			public void run(){
				context.runOnUiThread(new Runnable() {
					@Override public void run(){
						if(new ParsingCategories(jCertifManager).DelCategorie(jCertifManager.getListCategories().get(position).getId())){
							showWelcomMsg("Catégorie supprimée avec succés");
							new ParsingCategories(jCertifManager).getAllCategories();
							list.setAdapter(new CategoryAdapter(context, R.layout.single_photo, jCertifManager.getListCategories()));
						}else{
							showWelcomMsg("Problème lors de la suppression de la catégorie");
						}
					}
				});
			}
		}.start();

	}
	
	protected void DelComment(final int position) {
		
		new Thread(){
			@Override
			public void run(){
				context.runOnUiThread(new Runnable() {
					@Override public void run(){
						if(new ParsingComments(jCertifManager).delComment(jCertifManager.getListComments().get(position).getId())){
							showWelcomMsg("Suppression du commentaire effectuée"); 						
							new ParsingComments(jCertifManager).getCommentsByForum(forumSelected.getId());				
							list_comment.setAdapter(new CommentsAdapter(context, R.layout.single_news, jCertifManager.getListComments()));
						}else
							showWelcomMsg("Erreur lors de la suppression du commentaire");
					}
				});
			}
		}.start();

	}
	
	private void enableReturn(){
		isSelected = true;
		annuler.setBackgroundResource(R.drawable.shape);
		annuler.setTextColor(Color.rgb(255, 255, 255));
		btnSelected = 3;
		refreshTimer();
	}
	
	private void enableAjouter(){
		isSelected = true;
		ajouter.setBackgroundResource(R.drawable.shape);
		ajouter.setTextColor(Color.rgb(255, 255, 255));
		btnSelected = 4;
		refreshTimer();
	}
	
	private void enableAddSubkect(){
		
		add_subject.setBackgroundResource(R.drawable.shape);
		txt_add_subject.setTextColor(Color.rgb(255, 255, 255));
		
		btnSelected = 2;
		refreshTimer();
	}
	
	private void enableAddComment(){
		
		add_comment.setBackgroundResource(R.drawable.shape);
		txt_add_comment.setTextColor(Color.rgb(255, 255, 255));
		
		btnSelected = 1;
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
				 desableAddComment();
				 break;	
			 case 2:
				 desableAddSubject();
				 break;	
			 case 3:
				 desableReturn();
				 break;	
			 case 4:
				 desableAjouter();
				 break;	
			 }
		 }
	};
	
	protected void desableAjouter() {
		
		ajouter.setBackgroundResource(R.drawable.shape_whate);
		ajouter.setTextColor(Color.rgb(79, 129, 189));
		
		new Thread(){
			@Override
			public void run(){
				context.runOnUiThread(new Runnable() {
 					@Override public void run(){
 						
 						isSelected = false;
 						
 						if(titre.getText().toString().length()>0 && contenu.getText().toString().length()>0){
 							final Forum forum = new Forum();
 	 						forum.setUser(jCertifManager.getCurrentUser());
 	 						forum.setTitle(titre.getText().toString());
 	 						forum.setResolut("non");
 	 						forum.setContent(contenu.getText().toString());
 	 						forum.setCategory(jCertifManager.getListCategories().get(forumID));
 	 						
 	 						try{
 								if(new ParsingForum(jCertifManager).addForum(forum)){
 									showWelcomMsg("Forum ajouté");
 									
 									list.setAdapter(new CategoryAdapter(context, R.layout.single_photo, jCertifManager.getListCategories()));	
 									
 									ajouter_sujet.setVisibility(View.GONE);
 									news_all_layout.setVisibility(View.VISIBLE);
 																	
 								}else
 									showWelcomMsg("Erreur lors de l'ajout du sujet");
 	 						}catch(Exception e){}
 						}else{
 							showWelcomMsg("Vous devez indiquer un titre et un sujet à votre forum");
 						}
 						
 					}
				});			
			}
		}.start();
		
	}
	
	protected void desableAddSubject() {
		
		add_subject.setBackgroundResource(R.drawable.shape_whate);
		txt_add_subject.setTextColor(Color.rgb(79, 129, 189));
		
		news_all_layout.setVisibility(View.GONE);
		ajouter_sujet.setVisibility(View.VISIBLE);
		
		niveau = 1;
		add.setVisibility(View.GONE);
		
		isSelected = false;
		
	}
	
	protected void desableReturn() {
		
		isSelected = false;
		
		annuler.setBackgroundResource(R.drawable.shape_whate);
		annuler.setTextColor(Color.rgb(79, 129, 189));
		
		add.setVisibility(View.VISIBLE);
		
		titre.setText("");
		contenu.setText("");
		
		news_all_layout.setVisibility(View.VISIBLE);
		ajouter_sujet.setVisibility(View.GONE);
		
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
 						if(result!=null){
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
		     						Comment comment = new Comment(commentaire.getText().toString(), jCertifManager.getCurrentUser(), 0, 0, 0, forumSelected.getId());
		     						if(new ParsingComments(jCertifManager).addComment(comment)){
		     							new ParsingComments(jCertifManager).getCommentsByForum(forumSelected.getId());
		     							list_comment.setAdapter(new CommentsAdapter(context, R.layout.single_news, jCertifManager.getListComments()));
		     						}
	     						}catch(Exception e){
	     							Toast.makeText(context, "Problème de connexion", Toast.LENGTH_LONG).show();
	     						}
	     					}
						});
            		}
            	}.start();
				
            } 
        });
 
        adb.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	dialog.dismiss();
            } 
        });
 
        adb.show();
        
		isSelected = false;
	}	
	
	public void ShowDialog(final int position, View view){
		final Dialog dialog = new Dialog(context);
		dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		dialog.setContentView(R.layout.menu_contextuel2);
		dialog.setTitle(forumSelected.getTitle());

		final LinearLayout del  	 = (LinearLayout) dialog.findViewById(R.id.del); 
		final LinearLayout exit 	 = (LinearLayout) dialog.findViewById(R.id.exit);
		final LinearLayout resolut 	 = (LinearLayout) dialog.findViewById(R.id.resolut);
		
		final TextView txt_resolut 	 = (TextView) dialog.findViewById(R.id.txt_resolut);
		final TextView txt_del 	 	 = (TextView) dialog.findViewById(R.id.txt_del);
		final TextView txt_exit 	 = (TextView) dialog.findViewById(R.id.txt_exit);
		
		ImageView resolut_img = (ImageView) dialog.findViewById(R.id.resolut_img);
		
		if(forumSelected.getResolut().equals("oui")){
			txt_resolut.setText("Marquer comme non résolu");
			resolut_img.setImageResource(R.drawable.close_forum);
		}
		
		del.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				del.setBackgroundColor(Color.rgb(79, 129, 189));
				txt_del.setTextColor(Color.rgb(255, 255, 255));	
				delForum(position);
				dialog.dismiss();
			}
		});
		
		resolut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				resolut.setBackgroundColor(Color.rgb(79, 129, 189));
				txt_resolut.setTextColor(Color.rgb(255, 255, 255));				
				
				AlertDialog alertDialog = new AlertDialog.Builder(context).create();
				
				
				if(forumSelected.getResolut().equals("oui")){
					msg = "Marquer comme non résolu";
					etat = "non";
				}else{
					msg = "Marquer comme résolu";
					etat = "oui";
				}

				alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
				alertDialog.setTitle(msg);
				alertDialog.setMessage("Est ce que vous êtes sûr ?");
				alertDialog.setButton("Oui", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						
						jCertifManager.getListForums().get(position).setResolut(etat);
						
						new Thread(){
							@Override
							public void run(){
								setResolutForum(position);
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
				
				dialog.dismiss();

			}
		});
		
		exit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				exit.setBackgroundColor(Color.rgb(79, 129, 189));
				txt_exit.setTextColor(Color.rgb(255, 255, 255));
				dialog.dismiss();
			}
		});

		dialog.show();
	}

	protected void setResolutForum(final int position) {
		
		context.runOnUiThread(new Runnable() {
 			@Override public void run(){
				try{
					new ParsingForum(jCertifManager).updateForum(jCertifManager.getListForums().get(position));
				}catch(Exception e){
					Toast.makeText(context, "Problème de connexion", Toast.LENGTH_LONG).show();
				}
		
				list.setAdapter(new ForumsAdapter(context, R.layout.single_photo, jCertifManager.getListForums()));
 			}
		});
	}

	protected void delForum(final int position) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();

		alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		alertDialog.setTitle("Supprimer");
		alertDialog.setMessage("Supprimer ce sujet ?");
		alertDialog.setButton("Oui", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				
				new Thread(){
					@Override
					public void run(){
						context.runOnUiThread(new Runnable() {
				 			@Override public void run(){
								if(new ParsingForum(jCertifManager).DelForum(jCertifManager.getListForums().get(position).getId())){
									showWelcomMsg("Forum supprimé avec succés");
									setListForums();
								}else
									showWelcomMsg("Porbléme lors de la supprission du sujet");
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

	private void setBody(){		

		list.setAdapter(new CategoryAdapter(context, R.layout.single_photo, jCertifManager.getListCategories()));
		
		list.setOnItemClickListener(new OnItemClickListener() {    	 
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id) {
				if(!isSelected){
					isSelected = true;
					if(niveau == 1){
						niveau++;
						forumID = position;
						categorySelected = jCertifManager.getListCategories().get(position);
						titleTV.setText("Forums : "+categorySelected.getName());
						setListForums();											
					}else{
						forumSelected = jCertifManager.getListForums().get(position);
						shewForumsDetail();
					}	
					
					jCertifManager.setRubrique("forums");
				}		
			}
		});
		
	}
	
	protected void setListForums() {
		
		jCertifApplication.setPageInvisible(news_all_layout);
		jCertifApplication.setPageVisible(loading_body);
		
		new AsyncTask<String, Long, Bitmap>() {

			protected void onPostExecute(Bitmap result) {
				context.runOnUiThread(new Runnable() {
		 			@Override public void run(){
		 				jCertifApplication.setPageInvisible(loading_body);
	 					jCertifApplication.setPageVisible(news_all_layout);
		 				add.setVisibility(View.VISIBLE);
		 				
		 				enableListForum = true;
		 				
		 				list.setAdapter(new ForumsAdapter(context, R.layout.single_photo, jCertifManager.getListForums()));

						isSelected = false;
		 				
		 			}
				});	
				
				this.cancel(true);
			}

			protected Bitmap doInBackground(String... params) {
				jCertifManager.setListForums(new ArrayList<Forum>());
				new ParsingForum(jCertifManager).getAllForumByCategories(categorySelected.getId());
				return null;  
			}
		}.execute("");
	}
	
	public class ForumsAdapter extends ArrayAdapter<Forum>{

		public ForumsAdapter(Context context, int textViewResourceId, List<Forum> objects){
			super(context, textViewResourceId, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent){

			LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(R.layout.single_forum, null);

			TextView name = (TextView) convertView.findViewById(R.id.name);
			TextView auteur = (TextView) convertView.findViewById(R.id.auteur);
			LinearLayout indice = (LinearLayout) convertView.findViewById(R.id.indice);
			
			if(jCertifManager.getListForums().get(position).getResolut().equals("oui"))
				indice.setVisibility(View.VISIBLE);
			
			name.setText(jCertifManager.getListForums().get(position).getTitle());
			auteur.setText(jCertifManager.getListForums().get(position).getUser().getFirstname() + " " + jCertifManager.getListForums().get(position).getUser().getName());

			return convertView;
		}

	}

	public class CategoryAdapter extends ArrayAdapter<Category>{

		public CategoryAdapter(Context context, int textViewResourceId, List<Category> objects){
			super(context, textViewResourceId, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent){

			LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(R.layout.single_photo, null);

			LinearLayout title_zone = (LinearLayout) convertView.findViewById(R.id.title_zone);
			TextView title_new = (TextView) convertView.findViewById(R.id.title_new);
			final LinearLayout loading = (LinearLayout) convertView.findViewById(R.id.loading);
			final LinearLayout img = (LinearLayout) convertView.findViewById(R.id.img);
			final String url = new Parametres().getImgULR(jCertifManager.getListCategories().get(position).getUrl_img());

			if(jCertifManager.getListCategories().get(position).getUrl_img().length()>0){
				title_zone.setVisibility(View.GONE);
				new AsyncTask<String, Long, Bitmap>() {

					protected void onPostExecute(Bitmap result) {
						if (result != null) {
							BitmapDrawable background = new BitmapDrawable(result);
							img.setBackgroundDrawable(background);
						} 
						
						loading.setVisibility(View.GONE);
						img.setVisibility(View.VISIBLE);

						result = null;
						this.cancel(true);
					}

					protected Bitmap doInBackground(String... params) {
						return jCertifApplication.ImageOperations(url);  
					}
				}.execute("");
			}else{
				loading.setVisibility(View.GONE);
				img.setBackgroundResource(R.drawable.logo);
				title_new.setText(jCertifManager.getListCategories().get(position).getName());
			}
			

			return convertView;
		}

	}
	
	protected void shewForumsDetail() {
		
		jCertifApplication.setPageInvisible(news_all_layout);
		jCertifApplication.setPageVisible(loading_body);
		
		new AsyncTask<Void, Void, Void>(){
			@Override
			protected Void doInBackground(Void... arg0) {
				try{
					new ParsingComments(jCertifManager).getCommentsByForum(forumSelected.getId());	
				}catch(Exception e){
					Toast.makeText(context, "Problème de connexion", Toast.LENGTH_LONG).show();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				context.runOnUiThread(new Runnable() {
 					@Override public void run(){
						
 						if(header!=null)
 							list_comment.removeHeaderView(header);
 						
 						header = getHeader();
 						
 						list_comment.addHeaderView(header);
 						list_comment.setAdapter(new CommentsAdapter(context, R.layout.single_news, jCertifManager.getListComments()));
						
						jCertifApplication.setPageInvisible(loading_body);
						jCertifApplication.setPageVisible(detail_news);

						isSelected = false;
 					}
				});
			}	
			
		}.execute();
	}

	private View getHeader(){
		
		View headerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.header_detail_forum, null, false);

		img_profil		= (RelativeLayout) headerView.findViewById(R.id.img_profil);
		
		content			= (WebView)  headerView.findViewById(R.id.content);
		
		title_news  	= (TextView) headerView.findViewById(R.id.title_news);
		auteur 			= (TextView) headerView.findViewById(R.id.auteur);
		date 			= (TextView) headerView.findViewById(R.id.date);	
		txt_add_comment = (TextView) headerView.findViewById(R.id.txt_add_comment);	

		add_comment 	= (LinearLayout) headerView.findViewById(R.id.add_comment);
		
        title_news.setSelected(true);
        auteur.setSelected(true);
        date.setSelected(true);

		title_news.setText(forumSelected.getTitle());
		String text = "<html><head></head><body><p align=\"justify\"><font size='2'>" + forumSelected.getContent() +"</font></p>";
    	content.loadDataWithBaseURL(text, text, "text/html", "utf-8", "");
    	content.setWebViewClient(new WebViewClient());
    	content.getSettings().setLoadWithOverviewMode(true);
    	content.setBackgroundColor(0);
		content.setBackgroundColor(0x00000000);
		
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
				return jCertifApplication.ImageOperations(new com.jcertif.reseaujcertif.services.Parametres().getImgULR(forumSelected.getUser().getPhoto_url()));    
			}
		}.execute("");
		
		auteur.setText(forumSelected.getUser().getFirstname()+" "+forumSelected.getUser().getName());
		date.setText("Publié le "+forumSelected.getCreated().substring(0, forumSelected.getCreated().indexOf(" ")));
		
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
	
}
