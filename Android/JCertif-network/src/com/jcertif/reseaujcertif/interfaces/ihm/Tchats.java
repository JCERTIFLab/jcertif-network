package com.jcertif.reseaujcertif.interfaces.ihm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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

import com.actionbarsherlock.app.SherlockFragment;
import com.jcertif.reseaujcertif.R;
import com.jcertif.reseaujcertif.com.ParsingUsers;
import com.jcertif.reseaujcertif.dao.DatabaseHandler;
import com.jcertif.reseaujcertif.persistances.Msg;
import com.jcertif.reseaujcertif.persistances.User;
import com.jcertif.reseaujcertif.services.JCertifApplication;
import com.jcertif.reseaujcertif.services.JCertifManager;
import com.jcertif.reseaujcertif.services.Parametres;


@SuppressLint("SimpleDateFormat")
public class Tchats extends SherlockFragment {

    private ListView list, listMessages;
    private LinearLayout mViewGroupe, loading_body, liste_membre, remouve, liste, discution;
    private RelativeLayout img_profil;
    private ImageView retour_liste, send, refresh;
    private TextView membre_name;
    private EditText msg, search_txt;
    private JCertifApplication jCertifApplication;
    private JCertifManager jCertifManager;
    private FragmentActivity context;
    private boolean isSelected;
    private List<Msg> listMsgs = new ArrayList<Msg>();
    private List<Msg> listAllMsgs = new ArrayList<Msg>();
    private User userSelected;
    private DatabaseHandler db;
    private Handler mHandler;
	private Timer titleTimer;
	private int btnSelected;
    private ArrayList<User> listUers, listFinalUsers;
    private View footerView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		mViewGroupe	 	= (LinearLayout) inflater.inflate(R.layout.list_memebre,null);
		
		footerView 		= ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer, null, false);
		
		loading_body 	= (LinearLayout) mViewGroupe.findViewById(R.id.loading_body);
	    liste_membre 	= (LinearLayout) mViewGroupe.findViewById(R.id.liste_membre);
	    remouve 		= (LinearLayout) mViewGroupe.findViewById(R.id.remouve);
	    discution		= (LinearLayout) mViewGroupe.findViewById(R.id.discution);
	    liste			= (LinearLayout) mViewGroupe.findViewById(R.id.liste);
	    
		list 	 		= (ListView) mViewGroupe.findViewById(R.id.list);
		listMessages	= (ListView) mViewGroupe.findViewById(R.id.listMessages);
	    
		img_profil   	= (RelativeLayout) mViewGroupe.findViewById(R.id.img_profil);
	    
		membre_name	 	= (TextView) mViewGroupe.findViewById(R.id.membre_name);
	    
		msg 			= (EditText) footerView.findViewById(R.id.msg);
		search_txt		= (EditText) mViewGroupe.findViewById(R.id.search_txt);
	    
		send			= (ImageView) footerView.findViewById(R.id.send);	    
		refresh			= (ImageView) mViewGroupe.findViewById(R.id.refresh);	 
	    retour_liste	= (ImageView) mViewGroupe.findViewById(R.id.retour_liste);
	    
	    search_txt.setHint("Recherche par prénom...");	    
	    
	    listMessages.addFooterView(footerView, null, false);	
 
	    context = getActivity();

	    remouve.setVisibility(View.INVISIBLE);
	    
	    titleTimer = new Timer();
		mHandler = new Handler();
	    
	    View customView = inflater.inflate(R.layout.title_layout, null);
	    
	    TextView titleTV = (TextView) customView.findViewById(R.id.bar_title);
	    titleTV.setText("Tchat");
	    titleTV.setTextSize(20);
		
		getSherlockActivity().getSupportActionBar().setDisplayShowTitleEnabled(false);
	    getSherlockActivity().getSupportActionBar().setCustomView(customView);
	    getSherlockActivity().getSupportActionBar().setDisplayShowCustomEnabled(true);
	    
	    jCertifManager = JCertifManager.getInstance();
        jCertifApplication =  (JCertifApplication) getActivity().getApplicationContext();
	    
	    db = new DatabaseHandler(context);
	    listAllMsgs = db.getAllMessages(jCertifManager.getCurrentUser().getId());
	    
	    jCertifManager.setRubrique("home");
	    
	    isSelected = false;
        
        membre_name.setSelected(true);
        
		search_txt.addTextChangedListener(new TextWatcher(){
	        public void afterTextChanged(Editable s) {
	        	if(search_txt.getText().toString().length()>0){
	        		remouve.setVisibility(View.VISIBLE);
	        		Search(search_txt.getText().toString());
	        	}else{
	        		listUers = listFinalUsers;
					list.setAdapter(new MembresAdapter(context, R.layout.single_news, listUers));
	        		remouve.setVisibility(View.INVISIBLE);      		        	      	
	        	}
	        }
	        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	        public void onTextChanged(CharSequence s, int start, int before, int count){
	        	
	        }
	    });
        
		new AsyncTask<String, Long, Bitmap>() {

			protected void onPostExecute(Bitmap result) {
				context.runOnUiThread(new Runnable() {
		 			@Override public void run(){
		 				
		 				if(!jCertifManager.getListUsers().isEmpty()){
		 					
		 					list.setAdapter(new MembresAdapter(context, R.layout.single_news, listUers));
		 					
		 					jCertifApplication.setPageInvisible(loading_body);
		 					jCertifApplication.setPageVisible(liste_membre);
		 				}else
		 					Toast.makeText(context, "Aucun membre n'est inscrit", Toast.LENGTH_LONG).show();	
		 			}
				});	 
				
				this.cancel(true);
			}

			protected Bitmap doInBackground(String... params) {
				try{
					if(jCertifManager.getListUsers().size()==0)
						new ParsingUsers(jCertifManager).getUsers();
					
					listUers = new ArrayList<User>();					
					listFinalUsers = new ArrayList<User>();
					
					for(User user : jCertifManager.getListUsers()){
						if(user.getId() != jCertifManager.getCurrentUser().getId())
							listUers.add(user);
					}
					
					listFinalUsers = listUers;
					
				}catch(Exception e){
					Toast.makeText(context, "Problème de connexion", Toast.LENGTH_LONG).show();	
				}
				return null;  
			}
		}.execute("");
		
		remouve.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				search_txt.setText("");
				remouve.setVisibility(View.INVISIBLE);
			}
		});

		list.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
            	if(!isSelected){
            		isSelected = true;

            		if(listUers.get(position).isStatus()){
                		userSelected = listUers.get(position);
                        setInfosUser();
                    	discution.setVisibility(View.VISIBLE);
                    	liste.setVisibility(View.GONE);
                	}else
                		showWelcomMsg(listUers.get(position).getFirstname()+" "+listUers.get(position).getName()+" est occupé");
                	
            		isSelected = false;
            	}
            }
        });
		
		retour_liste.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isSelected)
					enableReturn();

			}
		});
		
		send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isSelected){
					isSelected = true;
					enableSend();
				}
			}
		});
		
		refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isSelected){
					isSelected = true;
					Rafresh();
				 	isSelected = false;
				}
			}
		});

		return mViewGroupe;
    }
    
	protected void enableReturn() {
		retour_liste.setBackgroundResource(R.drawable.return_on);
		isSelected = true;
		btnSelected = 2;
		refreshTimer();
	}

	protected void enableSend() {
		send.setBackgroundResource(R.drawable.send_on);
		btnSelected = 1;
		refreshTimer();
	}
	
	protected void disableSend() {	
		send.setBackgroundResource(R.drawable.send_off);
		sendMsg();
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
				 disableSend();
				 break;	
				 
			 case 2:
				 disableReturn();
				 break;	
			 }
		 }
	};

	@SuppressLint("DefaultLocale")
	protected void Search(String name) {
		listUers = new ArrayList<User>();
		
		for(User user : listFinalUsers){
			if(user.getFirstname().contains(name) || user.getFirstname().contains(name.substring(0, 1).toUpperCase()+name.substring(1, name.length())))
				listUers.add(user);
		}
		
		list.setAdapter(new MembresAdapter(context, R.layout.single_news, listUers));
	}	
	
	protected void disableReturn() {
		retour_liste.setBackgroundResource(R.drawable.return_off);
		listUers = listFinalUsers;
		list.setAdapter(new MembresAdapter(context, R.layout.single_news, listUers));
		discution.setVisibility(View.GONE);
    	liste.setVisibility(View.VISIBLE);
    	search_txt.setText("");
		isSelected = false;
	}

	@SuppressLint("SimpleDateFormat")
	protected void sendMsg() {
		new Thread(){
			@Override
			public void run(){
				context.runOnUiThread(new Runnable() {
 					@Override public void run(){
						if(msg.getText().toString().length()>0){
		
							Msg message = new Msg();
						    message.setDate(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));
							message.setMsg(msg.getText().toString());
							message.setUser(jCertifManager.getCurrentUser().getId()+"");
							message.setId_Sender(jCertifManager.getCurrentUser().getGcm_regid());
							message.setId_Resiver(userSelected.getGcm_regid());
							
							if(new ParsingUsers(jCertifManager).sendMessage(userSelected.getGcm_regid(), jCertifManager.getCurrentUser().getGcm_regid(), msg.getText().toString())){
								listMsgs.add(message);
								listMessages.setAdapter(new MsgAdapter(context, R.layout.single_news, listMsgs));
								listMessages.setSelection(listMsgs.size()-1);
								msg.setText("");
								db.addMessage(message);
							}else{
								showWelcomMsg("Problème lors de la transmission du message");
								msg.setText("");
							}
						}else{
							showWelcomMsg("Vous devez saisir un message");
						}
						isSelected = false;
 					}
				});
			}
		}.start();
	}
	
	private void showWelcomMsg(String msg) {
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View layout = inflater.inflate(R.layout.toast_custom_layout, (ViewGroup) getActivity().findViewById(R.id.toast_layout_root));

		TextView text = (TextView) layout.findViewById(R.id.text);
		text.setText(msg);

		Toast toast = new Toast(getActivity().getApplicationContext());
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		toast.show();
	}

	public class MembresAdapter extends ArrayAdapter<User>{

		public MembresAdapter(Context context, int textViewResourceId, List<User> objects){
			super(context, textViewResourceId, objects);
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent){

			LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(R.layout.single_membre_chat, null);

			TextView name 		= (TextView) convertView.findViewById(R.id.name);
			TextView last_msg 	= (TextView) convertView.findViewById(R.id.last_msg);
			TextView date 		= (TextView) convertView.findViewById(R.id.date);
			ImageView statut    = (ImageView) convertView.findViewById(R.id.statut);
			
			if(!listUers.get(position).isStatus())
				statut.setImageResource(R.drawable.logout);
			
			Msg lastMsg = getLastMsg(listUers.get(position).getGcm_regid());
			last_msg.setText(lastMsg.getMsg());
			date.setText(lastMsg.getDate().substring(lastMsg.getDate().indexOf(" ")+1, lastMsg.getDate().length()));
			
			final RelativeLayout img_profil = (RelativeLayout) convertView.findViewById(R.id.img_profil);

			name.setText(listUers.get(position).getFirstname()+" "+listUers.get(position).getName());			

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
					return jCertifApplication.ImageOperations(new Parametres().getImgULR(listUers.get(position).getPhoto_url()));  
				}
			}.execute("");

			return convertView;
		}

	}
	
	public class MsgAdapter extends ArrayAdapter<Msg>{

		public MsgAdapter(Context context, int textViewResourceId, List<Msg> objects){
			super(context, textViewResourceId, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent){

			LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(R.layout.single_msg, null);
			
			LinearLayout bg = (LinearLayout) convertView.findViewById(R.id.bg); 
			TextView date = (TextView) convertView.findViewById(R.id.date);
			TextView msg  = (TextView) convertView.findViewById(R.id.msg);
			
			msg.setText(listMsgs.get(position).getMsg());
			
			if(jCertifManager.getCurrentUser().getGcm_regid().equals(listMsgs.get(position).getId_Sender()))
				bg.setBackgroundResource(R.drawable.msg_left);
			
			String aujourdhui = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
			if(aujourdhui.equals(listMsgs.get(position).getDate().substring(0, listMsgs.get(position).getDate().indexOf(" "))))
				date.setText(listMsgs.get(position).getDate().substring(listMsgs.get(position).getDate().indexOf(" ")+1, listMsgs.get(position).getDate().length()-3));
			else
				date.setText(listMsgs.get(position).getDate().substring(0,  listMsgs.get(position).getDate().length()-3));

			return convertView;
		}

	}
	
	private void Rafresh(){
		
		listAllMsgs = db.getAllMessages(jCertifManager.getCurrentUser().getId());
		
		listMsgs = new ArrayList<Msg>();
		
    	for(Msg msg : listAllMsgs){
    		if((msg.getId_Resiver().equals(userSelected.getGcm_regid())) || (msg.getId_Sender().equals(userSelected.getGcm_regid())))
    			listMsgs.add(msg);
    	}
    	
    	listMessages.setAdapter(new MsgAdapter(context, R.layout.single_news, listMsgs));
    	listMessages.setSelection(listMsgs.size()-1);

	}
    
    private void setInfosUser(){
    	
    	membre_name.setText(userSelected.getFirstname()+" "+userSelected.getName());
    	
    	Rafresh();

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
				return jCertifApplication.ImageOperations(new com.jcertif.reseaujcertif.services.Parametres().getImgULR(userSelected.getPhoto_url()));    
			}
		}.execute("");
		
    }

	public Msg getLastMsg(String gcm_regid) {
		Msg msg = new Msg();
		
		for(Msg message : listAllMsgs){
			if(message.getId_Resiver().equals(gcm_regid) || message.getId_Sender().equals(gcm_regid))
				msg = message;
		}
		
		return msg;
	}
}
