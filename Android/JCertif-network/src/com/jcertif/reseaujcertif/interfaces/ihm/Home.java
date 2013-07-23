package com.jcertif.reseaujcertif.interfaces.ihm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import com.jcertif.reseaujcertif.R;
import com.jcertif.reseaujcertif.interfaces.services.BaseActivity;
import com.jcertif.reseaujcertif.interfaces.services.MenuFragment;
import com.jcertif.reseaujcertif.services.JCertifManager;
import com.slidingmenu.lib.SlidingMenu;

public class Home extends BaseActivity {
	
	private Fragment mContent;
	
	public Home() {
		super();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
		
		if (mContent == null){
			switch (JCertifManager.getInstance().getMenu_Selecteed()){
			case 1:
				mContent = new NewsFragement();
				break;
			case 2:
				mContent = new Events();
				break;
			case 3:
				mContent = new Forums();
				break;
			case 4:
				mContent = new Tchats();
				break;
			case 5:
				mContent = new NewsFragement();
				break;
			case 6:
				mContent = new Photos();
				break;
			case 7:
				mContent = new Videos();
				break;				
			}
			
		}
		
		setContentView(R.layout.content_frame);
		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, mContent).commit();

		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new MenuFragment()).commit();

		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);

	}

	public void switchContent(Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
		getSlidingMenu().showAbove();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		JCertifManager jCertifManager = JCertifManager.getInstance();
		
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			
			if(jCertifManager.getRubrique().equals("home")){
				
				exit();
				
			}else if(jCertifManager.getRubrique().equals("news")){
				
				switchContent(new NewsFragement());
				jCertifManager.setRubrique("home");
				
			}else if(jCertifManager.getRubrique().equals("events")){
				
				switchContent(new Events());
				jCertifManager.setRubrique("home");
				 
			}else if(jCertifManager.getRubrique().equals("forums")){
				
				switchContent(new Forums());
				jCertifManager.setRubrique("home");
				
			}else if(jCertifManager.getRubrique().equals("photos")){
				
				switchContent(new Photos());
				jCertifManager.setRubrique("home");

			}else if(jCertifManager.getRubrique().equals("videos")){
				
				switchContent(new Videos());
				jCertifManager.setRubrique("home");

			}else
				exit();
			
			return true;
		}
		return false;
	}

	private void exit(){
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==1000){
			finish();
		}
	}

}
