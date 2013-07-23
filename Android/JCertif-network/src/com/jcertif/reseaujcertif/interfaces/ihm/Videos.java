package com.jcertif.reseaujcertif.interfaces.ihm;

import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.actionbarsherlock.app.SherlockFragment;
import com.digitalaria.gama.carousel.Carousel;
import com.jcertif.reseaujcertif.R;
import com.jcertif.reseaujcertif.com.ParsingComments;
import com.jcertif.reseaujcertif.com.ParsingEvents;
import com.jcertif.reseaujcertif.com.ParsingVideos;
import com.jcertif.reseaujcertif.interfaces.services.CommentsAdapter;
import com.jcertif.reseaujcertif.interfaces.services.EventsAdapter;
import com.jcertif.reseaujcertif.persistances.Comment;
import com.jcertif.reseaujcertif.persistances.Event;
import com.jcertif.reseaujcertif.persistances.Video;
import com.jcertif.reseaujcertif.services.JCertifApplication;
import com.jcertif.reseaujcertif.services.JCertifManager;
import com.jcertif.reseaujcertif.services.Parametres;


public class Videos extends SherlockFragment {

	private LinearLayout mViewGroupe, all_videos, loading_body, fermer, info, add_comment, indice_up, loading_comment;
	private RelativeLayout info_photos, detail_photos, img_profil;
	private Carousel carousel;
	private VideoView mVideo;
	private FragmentActivity context;
	private JCertifApplication jCertifApplication;
	private JCertifManager jCertifManager;
	private ListView list, list_comment;
	private TextView title, name_poster, date_poster, txt_add_comment;
	private Video videoSelected;
	private boolean subMenuVisble, isSelected, annimationFinish;
	private int btnSelected;
	private Handler mHandler;
	private Timer titleTimer;
	
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
    	mViewGroupe	  	= (LinearLayout) inflater.inflate(R.layout.video_layout,null);

    	all_videos	  	= (LinearLayout) mViewGroupe.findViewById(R.id.all_photos);   	
    	loading_body  	= (LinearLayout) mViewGroupe.findViewById(R.id.loading_body);
    	fermer 	  	  	= (LinearLayout) mViewGroupe.findViewById(R.id.fermer);
    	indice_up		= (LinearLayout) mViewGroupe.findViewById(R.id.indice_up);
    	info 	  	  	= (LinearLayout) mViewGroupe.findViewById(R.id.info);
    	add_comment 	= (LinearLayout) mViewGroupe.findViewById(R.id.add_comment);
    	loading_comment = (LinearLayout) mViewGroupe.findViewById(R.id.loading_comment);
    	
    	detail_photos 	= (RelativeLayout) mViewGroupe.findViewById(R.id.detail_photos);
    	info_photos   	= (RelativeLayout) mViewGroupe.findViewById(R.id.info_photos);
    	img_profil    	= (RelativeLayout) mViewGroupe.findViewById(R.id.img_profil);
    	
    	carousel 	  	= (Carousel) mViewGroupe.findViewById(R.id.carousel);
    	
    	list 		  	= (ListView) mViewGroupe.findViewById(R.id.list);
    	list_comment  	= (ListView) mViewGroupe.findViewById(R.id.list_comment);
    	
    	name_poster   	= (TextView) mViewGroupe.findViewById(R.id.name_poster);
    	date_poster   	= (TextView) mViewGroupe.findViewById(R.id.date_poster);
    	txt_add_comment = (TextView) mViewGroupe.findViewById(R.id.txt_add_comment);
    	title		  	= (TextView) mViewGroupe.findViewById(R.id.title_new);
    	txt_add_comment = (TextView) mViewGroupe.findViewById(R.id.txt_add_comment);
    	
		mVideo 	  		= (VideoView) mViewGroupe.findViewById(R.id.video);
	 
		jCertifManager = JCertifManager.getInstance();
        jCertifApplication =  (JCertifApplication) getActivity().getApplicationContext();
	 
		jCertifManager 	   = JCertifManager.getInstance();
        jCertifApplication = (JCertifApplication) getActivity().getApplicationContext();
		
		context = getActivity();
		
		titleTimer = new Timer();
		mHandler = new Handler();
		
		jCertifManager.setRubrique("home");
		
		isSelected = false;
		subMenuVisble = false;
		annimationFinish = false;
		
	    View customView = inflater.inflate(R.layout.title_layout, null);
	    
	    TextView titleTV = (TextView) customView.findViewById(R.id.bar_title);
	    titleTV.setText("Vidéos");
	    titleTV.setTextSize(20);
		
		getSherlockActivity().getSupportActionBar().setDisplayShowTitleEnabled(false);
	    getSherlockActivity().getSupportActionBar().setCustomView(customView);
	    getSherlockActivity().getSupportActionBar().setDisplayShowCustomEnabled(true);

	    new AsyncTask<Void, Void, Void>(){

			@Override
			protected Void doInBackground(Void... arg0) {
				
				try{
					if(jCertifManager.getListEvents().isEmpty())
						new ParsingEvents(jCertifManager).getAllEvents();
				}catch(Exception e){
					Toast.makeText(context, "Problème de connexion", Toast.LENGTH_LONG).show();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				context.runOnUiThread(new Runnable() {
 					@Override public void run(){
 						list.setAdapter(new EventsAdapter(context, R.layout.single_news, jCertifManager.getListEvents()));
 						jCertifApplication.setPageInvisible(loading_body);
 						jCertifApplication.setPageVisible(all_videos);
 					}
				});
			}	
			
		}.execute();
		
		indice_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {       
            	if(annimationFinish){
            		if(!subMenuVisble){
                		subMenuVisble = true;
                		enableIndicDown();
                	}else{
                		subMenuVisble = false;
                		enableIndicUp();
                	} 
            	}          	
            }
        });
		
		fermer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("test","Fermer : "+ isSelected );
				if(!isSelected){
					isSelected = true;
					Log.i("test","Fermer");
					setPop_upInvisible();
					isSelected = false;
				}
			}
		});
	    
		list.setOnItemClickListener(new OnItemClickListener() {    	 
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id) {
				if(!isSelected){
					isSelected = true;
					setVideos(jCertifManager.getListEvents().get(position));
				}
			}
		});
		
		carousel.setOnItemClickListener(new OnItemClickListener() {    	 
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id) {
				if(!isSelected){
					isSelected = true;
					videoSelected = jCertifManager.getListVideos().get(position);
					setVideoDetail();
				}			
			}
		});
		
		info.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setPop_upVisible();
				
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
		
		add_comment.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isSelected){
					isSelected = true;
					enableAddComment();
				}
			}
		});
		
		list_comment.setOnItemLongClickListener(new OnItemLongClickListener() {
            public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, final long id) {
            	
            	if(jCertifManager.getCurrentUser().isAdmin()){
            		AlertDialog alertDialog = new AlertDialog.Builder(context).create();
    				alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            		alertDialog.setTitle("Supprimer ce commentaire");
            		alertDialog.setMessage("Est ce que vous êtes sûr ?");
            		alertDialog.setButton("Oui", new DialogInterface.OnClickListener() {
            			public void onClick(DialogInterface dialog, int which) {
            				DelComment(position);
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
    
    protected void enableIndicDown() {
    	indice_up.setBackgroundResource(R.drawable.indice_up_on);
    	btnSelected = 2;
    	refreshTimer();
	}
    
    private void disableIndicDown(){
    	isSelected = false;
		carousel.setVisibility(View.VISIBLE);
		indice_up.setBackgroundResource(R.drawable.indice_down_off);
    }
    
    protected void enableIndicUp() {
    	indice_up.setBackgroundResource(R.drawable.indice_down_on);
    	btnSelected = 3;
    	refreshTimer();
	}
    
    private void disableIndicUp(){
    	isSelected = false;
		carousel.setVisibility(View.GONE);
		indice_up.setBackgroundResource(R.drawable.indice_up_off);
    }
    
    protected void DelComment(final int position) {
    	
    	if(jCertifManager.getCurrentUser().isAdmin()){
    		loading_comment.setVisibility(View.VISIBLE);
            list_comment.setVisibility(View.GONE);
    		
    		new AsyncTask<String, Long, Bitmap>() {

    			protected void onPostExecute(Bitmap result) {}

    			protected Bitmap doInBackground(String... params) {
    				if(new ParsingComments(jCertifManager).delComment(jCertifManager.getListComments().get(position).getId())){
    					
    					context.runOnUiThread(new Runnable() {
    	 					@Override public void run(){
    	 						try{
	    	 						showWelcomMsg("Suppression du commentaire effectuée"); 						
	    	 						new ParsingComments(jCertifManager).getCommentsByVideo(videoSelected.getId());				
	    	 						list_comment.setAdapter(new CommentsAdapter(context, R.layout.single_news, jCertifManager.getListComments()));
    	 						}catch(Exception e){
    	 							Toast.makeText(context, "Problème de connexion", Toast.LENGTH_LONG).show();
    	 						}
    	 						
    	 						loading_comment.setVisibility(View.GONE);
    	 				        list_comment.setVisibility(View.VISIBLE);
    	 					}
    					});
    										
    				}else
    					showWelcomMsg("Erreur lord de la supprision du commentaire");
    				
    				return null;  
    			}
    		}.execute("");
    	}else{
    		showWelcomMsg("Vous n'êtes pas autorisé à supprimer les commentaires");
    	}
	}
    
    private void setPop_upVisible(){
    	Animation a = AnimationUtils.loadAnimation(getActivity(), R.anim.translate_down);
        a.reset();
        info_photos.clearAnimation();
        info_photos.startAnimation(a);
        info_photos.setVisibility(View.VISIBLE);
    }
    
    private void setPop_upInvisible(){
    	Animation a = AnimationUtils.loadAnimation(getActivity(), R.anim.translate_up);
        a.reset();
        info_photos.clearAnimation();
        info_photos.startAnimation(a);
        info_photos.setVisibility(View.GONE);
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
				 disableIndicDown();
				 break;
			 case 3:
				 disableIndicUp();
				 break;
			 }
			 
		 }
	};
	
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
            	
            	loading_comment.setVisibility(View.VISIBLE);
                list_comment.setVisibility(View.GONE);
				
				new AsyncTask<Void, Void, Void>(){

					@Override
					protected Void doInBackground(Void... arg0) {
						try{
							Comment comment = new Comment(commentaire.getText().toString(), jCertifManager.getCurrentUser(), 0, 0, videoSelected.getId(), 0);
	 						new ParsingComments(jCertifManager).addComment(comment);
						}catch(Exception e){
							Toast.makeText(context, "Problème de connexion", Toast.LENGTH_LONG).show();
						}
 						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						context.runOnUiThread(new Runnable() {
	     					@Override public void run(){
	     						new ParsingComments(jCertifManager).getCommentsByVideo(videoSelected.getId());
	     						list_comment.setAdapter(new CommentsAdapter(context, R.layout.single_news, jCertifManager.getListComments()));
	     						
	     						loading_comment.setVisibility(View.GONE);
	     				        list_comment.setVisibility(View.VISIBLE);
	     					}
						});
					}	
					
				}.execute();
				
            } 
        });
 
        adb.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	dialog.dismiss();
            } 
        });
 
        adb.show();
	
	}

    protected void setVideoDetail() {
    	
    	loading_comment.setVisibility(View.VISIBLE);
        list_comment.setVisibility(View.GONE);
    	
    	new AsyncTask<Void, Void, Void>(){
			@Override
			protected Void doInBackground(Void... arg0) {
				try{
					new ParsingComments(jCertifManager).getCommentsByVideo(videoSelected.getId());	
				}catch(Exception e){
					Toast.makeText(context, "Problème de connexion", Toast.LENGTH_LONG).show();
				}
				
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				context.runOnUiThread(new Runnable() {
 					@Override public void run(){	
						list_comment.setAdapter(new CommentsAdapter(context, R.layout.single_news, jCertifManager.getListComments()));
						
						loading_comment.setVisibility(View.GONE);
		                list_comment.setVisibility(View.VISIBLE);
 					}
				});
				
				isSelected = false;
			}	
			
		}.execute();
    	
    	MediaController mc = new MediaController(context);  
	    mc.setAnchorView(mVideo);
	    mc.setMediaPlayer(mVideo);
	    Uri video = Uri.parse(new Parametres().Base_IMG_URL+videoSelected.getUrl());
	    mVideo.setMediaController(mc);
	    mVideo.setVideoURI(video);
	    mVideo.start();
		
		title.setSelected(true);
		title.setText(videoSelected.getTitle());

		name_poster.setText(videoSelected.getUser().getFirstname()+" "+videoSelected.getUser().getName());
    	date_poster.setText(videoSelected.getCreated());
    	
    	new AsyncTask<String, Long, Bitmap>() {
			protected void onPostExecute(Bitmap result) {
				BitmapDrawable bitmapDrawable = new BitmapDrawable(result);
				img_profil.setBackgroundDrawable(bitmapDrawable);
				this.cancel(true);
			}
			@Override
			protected Bitmap doInBackground(String... params) { 
				return jCertifApplication.ImageOperations(new com.jcertif.reseaujcertif.services.Parametres().getImgULR(videoSelected.getUser().getPhoto_url()));    
			}
		}.execute("");

		new Thread(){
			@Override
			public void run(){
				try{
					sleep(5000);
				}catch(Exception e){}
				
				context.runOnUiThread(new Runnable() {
 					@Override public void run(){
						subMenuVisble = false;
		        		carousel.setVisibility(View.GONE);
		        		indice_up.setBackgroundResource(R.drawable.indice_up_off);
 					}
				});
				
				annimationFinish = true;
				
			}
		}.start();
			
	}

	private void showWelcomMsg(String msg) {
		LayoutInflater inflater = context.getLayoutInflater();
		View layout = inflater.inflate(R.layout.toast_custom_layout, (ViewGroup) context.findViewById(R.id.toast_layout_root));

		TextView text = (TextView) layout.findViewById(R.id.text);
		text.setText(msg);

		Toast toast = new Toast(context.getApplicationContext());
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		toast.show();
	}

	protected void setVideos(final Event event) {
		
		jCertifApplication.setPageInvisible(all_videos);
		jCertifApplication.setPageVisible(loading_body);	
		
		new Thread(){
			@Override
			public void run(){
				try{
					sleep(2000);
				}catch(Exception e){}
				
				context.runOnUiThread(new Runnable() {
 					@Override public void run(){	
						
 						new ParsingVideos(jCertifManager).getVideosByEvent(event.getId());

						if(!jCertifManager.getListVideos().isEmpty()){
							videoSelected = jCertifManager.getListVideos().get(0);
							try{
								new ParsingComments(jCertifManager).getCommentsByVideo(videoSelected.getId());
							}catch(Exception e){
								Toast.makeText(context, "Problème de connexion", Toast.LENGTH_LONG).show();
							}
							
							initialCarosel();
 							
 	 				    	setVideoDetail();
 	 				    	
 	 				    	jCertifManager.setRubrique("videos");
 							
 							jCertifApplication.setPageInvisible(loading_body);
 							jCertifApplication.setPageVisible(detail_photos);
						}else{
 							showWelcomMsg("Aucune vidéo n'est disponible pour cet évenement");
 							 							
 							jCertifApplication.setPageInvisible(loading_body);
 							jCertifApplication.setPageVisible(all_videos);
 						}
						
						
 						isSelected = false;
 					}
				});
				
			}
		}.start();			
		
	}
	
	private void initialCarosel(){
        carousel.setType(Carousel.TYPE_COVERFLOW);
        carousel.setOverScrollBounceEnabled(true);
        carousel.setInfiniteScrollEnabled(false);
        carousel.setItemRearrangeEnabled(true);
        carousel.setAdapter(new GrideAdapter());
    }
 
    public class GrideAdapter extends BaseAdapter {
       
    	public GrideAdapter() {}
 
        public int getCount() {
            return jCertifManager.getListVideos().size();
        }
 
        public Object getItem(int position) {
            return position;
        }
 
        public long getItemId(int position) {
            return position;
        }
 
        public View getView(final int position, View convertView, ViewGroup parent) {
        	LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    		convertView = layoutInflater.inflate(R.layout.single_photo_galery, null);
    		convertView.setLayoutParams(new Gallery.LayoutParams(140, 100));
    		
    		final LinearLayout img 	   	= (LinearLayout) convertView.findViewById(R.id.img);
    		final LinearLayout loading 	= (LinearLayout) convertView.findViewById(R.id.loading);
            
    		new AsyncTask<String, Long, Bitmap>() {
    			protected void onPostExecute(Bitmap result) {
    				if(result != null){
    					BitmapDrawable background = new BitmapDrawable(result);
    					img.setBackgroundDrawable(background);
        				loading.setVisibility(View.GONE);
    				}
    				this.cancel(true);
    			}
    			@Override
    			protected Bitmap doInBackground(String... params) { 
    				Log.i("test",">>"+new Parametres().getImgULR(jCertifManager.getListVideos().get(position).getImg_url()));
    				return jCertifApplication.ImageOperations(new Parametres().getImgULR(jCertifManager.getListVideos().get(position).getImg_url()));    
    			}
    		}.execute("");
            
            return convertView;
        }
 
    } 

}
