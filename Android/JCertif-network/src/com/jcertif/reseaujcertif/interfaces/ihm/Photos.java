package com.jcertif.reseaujcertif.interfaces.ihm;

import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.digitalaria.gama.carousel.Carousel;
import com.jcertif.reseaujcertif.R;
import com.jcertif.reseaujcertif.com.ParsingComments;
import com.jcertif.reseaujcertif.com.ParsingEvents;
import com.jcertif.reseaujcertif.com.ParsingPhotos;
import com.jcertif.reseaujcertif.interfaces.services.CommentsAdapter;
import com.jcertif.reseaujcertif.interfaces.services.EventsAdapter;
import com.jcertif.reseaujcertif.persistances.Comment;
import com.jcertif.reseaujcertif.persistances.Event;
import com.jcertif.reseaujcertif.persistances.Photo;
import com.jcertif.reseaujcertif.services.JCertifApplication;
import com.jcertif.reseaujcertif.services.JCertifManager;
import com.jcertif.reseaujcertif.services.Parametres;

public class Photos extends SherlockFragment {

	private LinearLayout mViewGroupe, mSwitcher, all_photos, loading_body, loading_img, fermer, info, add_comment, indice_up;
	private RelativeLayout info_photos, detail_photos, img_profil;
	private FragmentActivity context;
	private JCertifApplication jCertifApplication;
	private JCertifManager jCertifManager;
	private ListView list, list_comment;
	private TextView title, name_poster, date_poster, txt_add_comment;
	private Photo photoSelected;
//	private Button share;
//	private CheckBox mFacebookBtn;
	private View header = null;
	private boolean isSelected, subMenuVisble, annimationFinish;
	private int btnSelected;
	private Handler mHandler;
	private Timer titleTimer;
	private Carousel carousel;
//	private ProgressDialog mProgress;
//	private Facebook mFacebook;
	private Event eventSelected;
	
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
    	mViewGroupe	  	= (LinearLayout) inflater.inflate(R.layout.photo_layout,null);

    	all_photos	  	= (LinearLayout) mViewGroupe.findViewById(R.id.all_photos);   	
    	loading_body  	= (LinearLayout) mViewGroupe.findViewById(R.id.loading_body);
    	loading_img   	= (LinearLayout) mViewGroupe.findViewById(R.id.loading_img);
    	fermer 	  	  	= (LinearLayout) mViewGroupe.findViewById(R.id.fermer);
    	indice_up		= (LinearLayout) mViewGroupe.findViewById(R.id.indice_up);
    	
    	detail_photos 	= (RelativeLayout) mViewGroupe.findViewById(R.id.detail_photos);
    	info_photos   	= (RelativeLayout) mViewGroupe.findViewById(R.id.info_photos);
    	img_profil    	= (RelativeLayout) mViewGroupe.findViewById(R.id.img_profil);

        carousel 		= (Carousel) mViewGroupe.findViewById(R.id.carousel);
    	
    	list 		  	= (ListView) mViewGroupe.findViewById(R.id.list);
    	list_comment  	= (ListView) mViewGroupe.findViewById(R.id.list_comment);
    	
    	name_poster   	= (TextView) mViewGroupe.findViewById(R.id.name_poster);
    	date_poster   	= (TextView) mViewGroupe.findViewById(R.id.date_poster);
	 
		jCertifManager = JCertifManager.getInstance();
        jCertifApplication =  (JCertifApplication) getActivity().getApplicationContext();
		
		context = getActivity();
		
		titleTimer = new Timer();
		mHandler = new Handler();
		
		isSelected = false;
		annimationFinish = false;
		subMenuVisble = true;
		
		jCertifManager.setRubrique("home");
		
//		mProgress	= new ProgressDialog(context);	
//		mFacebook 	= new Facebook(jCertifApplication.APP_ID);
//		SessionStore.restore(mFacebook, context);
		
	    View customView = inflater.inflate(R.layout.title_layout, null);
	    
	    TextView titleTV = (TextView) customView.findViewById(R.id.bar_title);
	    titleTV.setText("Photos");
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
				}catch(Exception e){}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				context.runOnUiThread(new Runnable() {
 					@Override public void run(){
 						list.setAdapter(new EventsAdapter(context, R.layout.single_news, jCertifManager.getListEvents()));
 						jCertifApplication.setPageInvisible(loading_body);
 						jCertifApplication.setPageVisible(all_photos);
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
				if(!isSelected){
					isSelected = true;
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
					eventSelected = jCertifManager.getListEvents().get(position);
					setPhotos(eventSelected);	
				}			
			}
		});

		carousel.setOnItemClickListener(new OnItemClickListener() {    	 
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id) {
				if(!isSelected){
					isSelected = true;
					photoSelected = jCertifManager.getListPhotos().get(position);	
					setPhotosDetail();	
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
    	
		new AsyncTask<String, Long, Bitmap>() {
	
			protected void onPostExecute(Bitmap result) {}
	
			protected Bitmap doInBackground(String... params) {
				
				context.runOnUiThread(new Runnable() {
 					@Override public void run(){
 						if(new ParsingComments(jCertifManager).delComment(jCertifManager.getListComments().get(position-1).getId())){
 							
 							try{
 		 						showWelcomMsg("Suppression du commentaire effectuée"); 						
 		 						new ParsingComments(jCertifManager).getCommentsByPhoto(photoSelected.getId());				
 		 						list_comment.setAdapter(new CommentsAdapter(context, R.layout.single_news, jCertifManager.getListComments()));
 							}catch(Exception e){
 								Toast.makeText(context, "Problème de connexion", Toast.LENGTH_LONG).show();
 							}
 												
 						}else
 							showWelcomMsg("Erreur lors de la suppression du commentaire");
 					}
				});
				
				return null;  
			}
		}.execute("");
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
    
    private View getHeader(){
		
		View headerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.header_detail_photo, null, false);

		add_comment 	= (LinearLayout) headerView.findViewById(R.id.add_comment);
		mSwitcher 	  	= (LinearLayout) headerView.findViewById(R.id.switcher);
    	info 	  	  	= (LinearLayout) headerView.findViewById(R.id.info);
    	loading_img   	= (LinearLayout) headerView.findViewById(R.id.loading_img);

    	title		  	= (TextView) headerView.findViewById(R.id.title_new);
    	txt_add_comment	= (TextView) headerView.findViewById(R.id.txt_add_comment);

//		share 			= (Button) headerView.findViewById(R.id.share);
//		
//		mFacebookBtn	= (CheckBox) headerView.findViewById(R.id.cb_facebook);
		
		title.setSelected(true);
		title.setText(photoSelected.getTitle());
		
		loading_img.setVisibility(View.VISIBLE);
		mSwitcher.setVisibility(View.GONE);
		
//		if (mFacebook.isSessionValid()) {
//			mFacebookBtn.setChecked(true);
//				
//			String name = SessionStore.getName(context);
//			name		= (name.equals("")) ? "Unknown" : name;
//				
//			mFacebookBtn.setText("  connecté  (" + name + ")");
//		}	    
		
    	
    	new AsyncTask<Void, Void, Void>(){
			@Override
			protected Void doInBackground(Void... arg0) {
				context.runOnUiThread(new Runnable() {
 					@Override public void run(){
 						BitmapDrawable bitmapDrawable = new BitmapDrawable(jCertifApplication.ImageOperations(new Parametres().getImgULR(photoSelected.getUrl())));
 						mSwitcher.setBackgroundDrawable(bitmapDrawable);
 					}
				});
				
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				context.runOnUiThread(new Runnable() {
 					@Override public void run(){							
 					    loading_img.setVisibility(View.GONE);
 						mSwitcher.setVisibility(View.VISIBLE);
 					}
				});	 
			}
    	}.execute();

		name_poster.setText(photoSelected.getUser().getFirstname()+" "+photoSelected.getUser().getName());
    	date_poster.setText(photoSelected.getCreated());
    	
    	new AsyncTask<String, Long, Bitmap>() {
			protected void onPostExecute(Bitmap result) {
				BitmapDrawable bitmapDrawable = new BitmapDrawable(result);
				img_profil.setBackgroundDrawable(bitmapDrawable);
				this.cancel(true);
			}
			@Override
			protected Bitmap doInBackground(String... params) { 
				return jCertifApplication.ImageOperations(new com.jcertif.reseaujcertif.services.Parametres().getImgULR(photoSelected.getUser().getPhoto_url()));    
			}
		}.execute("");
		
//		share.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if(!isSelected){
//		share.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if(!isSelected){
//					if(mFacebookBtn.isChecked()){
//						isSelected = true;
//		                ShowFacebookDialogue();
//						isSelected = false;
//					}else
//						showWelcomMsg("Vous devez connecter à votre profil facebook");
//				}
//			}
//		});
//
//		mFacebookBtn.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if(!isSelected){
//					isSelected = true;
//					onFacebookClick();
//					isSelected = false;
//				}
//			}
//		});			if(mFacebookBtn.isChecked()){
//						isSelected = true;
//		                ShowFacebookDialogue();
//						isSelected = false;
//					}else
//						showWelcomMsg("Vous devez connecter à votre profil facebook");
//				}
//			}
//		});
//
//		mFacebookBtn.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if(!isSelected){
//					isSelected = true;
//					onFacebookClick();
//					isSelected = false;
//				}
//			}
//		});
		
		mSwitcher.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isSelected){
					isSelected = true;
					ShowImage();
					isSelected = false;
				}
			}
		});
		
		
		info.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isSelected){
					isSelected = true;
					setPop_upVisible();
					isSelected = false;
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

		return headerView;
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
        
        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
				
				new AsyncTask<Void, Void, Void>(){

					@Override
					protected Void doInBackground(Void... arg0) {
						Comment comment = new Comment(commentaire.getText().toString(), jCertifManager.getCurrentUser(), 0, photoSelected.getId(), 0, 0);
 						if(new ParsingComments(jCertifManager).addComment(comment))
 							new ParsingComments(jCertifManager).getCommentsByPhoto(photoSelected.getId());
 						else
 							showWelcomMsg("Problème l'ors de l'ajout du commentaire");
 						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						context.runOnUiThread(new Runnable() {
	     					@Override public void run(){
	     						list_comment.setAdapter(new CommentsAdapter(context, R.layout.single_news, jCertifManager.getListComments()));
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
        

		isSelected = false;        
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

    protected void setPhotosDetail() {

		new AsyncTask<Void, Void, Void>(){
			@Override
			protected Void doInBackground(Void... arg0) {
				try{
					new ParsingComments(jCertifManager).getCommentsByPhoto(photoSelected.getId());	
				}catch(Exception e){}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				context.runOnUiThread(new Runnable() {
 					@Override public void run(){		
 						if(header != null)
 							list_comment.removeHeaderView(header);
 						
 						header = getHeader();
 						
 						list_comment.addHeaderView(header);
						list_comment.setAdapter(new CommentsAdapter(context, R.layout.single_news, jCertifManager.getListComments()));

						isSelected = false;
 					}
				});
			}	
			
		}.execute();
		
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
    
	protected void setPhotos(final Event event) {	
		
		jCertifApplication.setPageInvisible(all_photos);
		jCertifApplication.setPageVisible(loading_body);	
		
		new Thread(){
			@Override
			public void run(){
				try{
					sleep(2000);
				}catch(Exception e){}
				
				context.runOnUiThread(new Runnable() {
 					@Override public void run(){	
						
 						new ParsingPhotos(jCertifManager).getPhotosByEvent(event.getId());

						if(!jCertifManager.getListPhotos().isEmpty()){
							photoSelected = jCertifManager.getListPhotos().get(0);
							new ParsingComments(jCertifManager).getCommentsByPhoto(photoSelected.getId());	
							
							initialCarosel();
 							
 							setPhotosDetail();
 							
 							jCertifManager.setRubrique("photos");
 							
 							jCertifApplication.setPageInvisible(loading_body);
 							jCertifApplication.setPageVisible(detail_photos);
						}else{
 							showWelcomMsg("Aucune photo n'est disponible pour cet évènement");
 							jCertifApplication.setPageInvisible(loading_body);
 							jCertifApplication.setPageVisible(all_photos);
 						}
 						
 						isSelected = false;
 					}
				});
				
			}
		}.start();			
		
	}
 
    public class ImageAdapter extends BaseAdapter {
        public ImageAdapter() {}
 
        public int getCount() {
            return jCertifManager.getListPhotos().size();
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
    				return jCertifApplication.ImageOperations(new Parametres().getImgULR(jCertifManager.getListPhotos().get(position).getUrl()));    
    			}
    		}.execute("");
            
            return convertView;
        }
 
    } 
    
    private void initialCarosel(){
        carousel.setType(Carousel.TYPE_COVERFLOW);
        carousel.setOverScrollBounceEnabled(true);
        carousel.setInfiniteScrollEnabled(false);
        carousel.setItemRearrangeEnabled(true);
        carousel.setAdapter(new ImageAdapter());
    }

	private void ShowImage(){
		
		LayoutInflater factory = LayoutInflater.from(context);
		
        final View alertDialogView = factory.inflate(R.layout.image_dialog, null);
        
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        
        dialog.setView(alertDialogView);
        
		final LinearLayout img 	   	= (LinearLayout) alertDialogView.findViewById(R.id.img);
		final LinearLayout loading 	= (LinearLayout) alertDialogView.findViewById(R.id.loading);
        
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
				return jCertifApplication.ImageOperations(new Parametres().getImgULR(photoSelected.getUrl()));    
			}
		}.execute("");
        
		dialog.show();
	}
	
//	private void onFacebookClick() {
//		if (mFacebook.isSessionValid()) {
//			final AlertDialog.Builder builder = new AlertDialog.Builder(context);
//			
//			builder.setMessage("Delete current Facebook connection?")
//			       .setCancelable(false)
//			       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//			           public void onClick(DialogInterface dialog, int id) {
//			        	   fbLogout();
//			           }
//			       })
//			       .setNegativeButton("No", new DialogInterface.OnClickListener() {
//			           public void onClick(DialogInterface dialog, int id) {
//			                dialog.cancel();
//			                
//			                mFacebookBtn.setChecked(true);
//			           }
//			       });
//			
//			final AlertDialog alert = builder.create();
//			
//			alert.show();
//		} else {
//			mFacebookBtn.setChecked(false);
//			
//			mFacebook.authorize(context, jCertifApplication.PERMISSIONS, -1, new FbLoginDialogListener());
//		}
//	}
//    
//    private final class FbLoginDialogListener implements DialogListener {
//        public void onComplete(Bundle values) {
//            SessionStore.save(mFacebook, context);
//           
//            mFacebookBtn.setText("  non connecté");
//            mFacebookBtn.setChecked(true);
//			 
//            getFbName();
//        }
//
//        public void onFacebookError(FacebookError error) {
//           Toast.makeText(context, "Facebook connection failed", Toast.LENGTH_SHORT).show();
//           
//           mFacebookBtn.setChecked(false);
//        }
//        
//        public void onError(DialogError error) {
//        	Toast.makeText(context, "Facebook connection failed", Toast.LENGTH_SHORT).show(); 
//        	
//        	mFacebookBtn.setChecked(false);
//        }
//
//        public void onCancel() {
//        	mFacebookBtn.setChecked(false);
//        }
//    }
//    
//	private void getFbName() {
//		mProgress.setMessage("Finalizing ...");
//		mProgress.show();
//		
//		new Thread() {
//			@Override
//			public void run() {
//		        String name = "";
//		        int what = 1;
//		        
//		        try {
//		        	String me = mFacebook.request("me");
//		        	
//		        	JSONObject jsonObj = (JSONObject) new JSONTokener(me).nextValue();
//		        	name = jsonObj.getString("name");
//		        	what = 0;
//		        } catch (Exception ex) {
//		        	ex.printStackTrace();
//		        }
//		        
//		        mFbHandler.sendMessage(mFbHandler.obtainMessage(what, name));
//			}
//		}.start();
//	}
//	
//	private void fbLogout() {
//		mProgress.setMessage("Disconnecting from Facebook");
//		mProgress.show();
//			
//		new Thread() {
//			@Override
//			public void run() {
//				SessionStore.clear(context);
//		        	   
//				int what = 1;
//					
//		        try {
//		        	mFacebook.logout(context);
//		        		 
//		        	what = 0;
//		        } catch (Exception ex) {
//		        	ex.printStackTrace();
//		        }
//		        	
//		        mHandler.sendMessage(mHandler.obtainMessage(what));
//			}
//		}.start();
//	}
//	
//	private Handler mFbHandler = new Handler() {
//		@Override
//		public void handleMessage(Message msg) {
//			mProgress.dismiss();
//			
//			if (msg.what == 0) {
//				String username = (String) msg.obj;
//		        username = (username.equals("")) ? "No Name" : username;
//		            
//		        SessionStore.saveName(username, context);
//		        
//		        mFacebookBtn.setText("  Facebook (" + username + ")");
//		         
//		        Toast.makeText(context, "Connected to Facebook as " + username, Toast.LENGTH_SHORT).show();
//			} else {
//				Toast.makeText(context, "Connected to Facebook", Toast.LENGTH_SHORT).show();
//			}
//		}
//	};
//	
//	private void ShowFacebookDialogue(){
//		LayoutInflater factory = LayoutInflater.from(getActivity());
//        View alertDialogView = factory.inflate(R.layout.post, null);
//        
//        final AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
//        
//        adb.setView(alertDialogView);
//        
//        final EditText reviewEdit = (EditText) alertDialogView.findViewById(R.id.revieew);
//        
//        adb.setPositiveButton("Publier", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//            	String review = reviewEdit.getText().toString();				
//				postToFacebook(review);		
//				dialog.dismiss();
//            } 
//        });
// 
//        adb.show();
//	}
//	
//	private void postToFacebook(String review) {	
//		mProgress.setMessage("En cours de publication ...");
//		mProgress.show();
//		
//		AsyncFacebookRunner mAsyncFbRunner = new AsyncFacebookRunner(mFacebook);
//		
//		Bundle params = new Bundle();
//    		 
//		params.putString("message", review);
//		params.putString("name", photoSelected.getTitle());
//		params.putString("caption", "JCertif");
//		params.putString("description", new Parametres().getImgULR(photoSelected.getUrl()));
//		
//		mAsyncFbRunner.request("me/feed", params, "POST", new WallPostListener());
//	}
//
//	private final class WallPostListener extends BaseRequestListener {
//        public void onComplete(final String response) {
//        	mRunOnUi.post(new Runnable() {
//        		@Override
//        		public void run() {
//        			mProgress.cancel();
//        			
//        			Toast.makeText(context, "Publié sur votre mure", Toast.LENGTH_SHORT).show();
//        		}
//        	});
//        }
//    }
//	
//	private Handler mRunOnUi = new Handler();
}
