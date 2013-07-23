package com.jcertif.reseaujcertif.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class JCertifApplication extends Application{
	
	public String SENDER_ID = "853948940057";
	
	public String[] PERMISSIONS = new String[] {"publish_stream", "read_stream", "offline_access"};
	
	public String APP_ID = "135798803260295";
	
	public String months[] = new String[] {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};
	
	public String[] menuPrincipale = { "Actualités", "Evènement", "Forum", "Tchat", "Rechercher un endroit", "Galerie photos", "Galerie vidéos", "Réseau JCertif"};
	
	public boolean isConnected = false;
	
	public final int cacheSize = 4 * 1024 * 1024;
	
	public LruCache<String, Bitmap> mMemoryCache;
	
	public String DISPLAY_MESSAGE_ACTION = "com.jcertif.reseaujcertif.DISPLAY_MESSAGE";
	
	public String ReadDate(String filePath){
		String msg = "";
		try {
            FileInputStream mInput = openFileInput(filePath);
            byte[] data = new byte[128];
            mInput.read(data);
            mInput.close();
            
            String display = new String(data);
            msg = display.trim();
        } catch (FileNotFoundException e) {
        	
        } catch (IOException e) {
            
        }
		
		return msg;
	}
	
	public void SaveDate(String date, String filePath){
		try {
            FileOutputStream mOutput = openFileOutput(filePath, Activity.MODE_PRIVATE);
            mOutput.write(date.getBytes());
            mOutput.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public Bitmap ImageOperations(String url) {
		Bitmap bm = null;
		bm = getBitmapFromMemCache(url);
		if(bm!=null){
			 return bm;
		}
		
		else{
			try {
				InputStream in = new URL(url).openStream();
				try{
					bm = BitmapFactory.decodeStream(in);
					addBitmapToMemoryCache(url, bm);
				}catch(Exception e){
					Log.e("CanManager", "error downloading url: "+e.toString());
				}
				in.close();
			} catch (Exception e) {
				Log.e("CanManager", "url: "+url);
			}
		}
		return bm;
	}
	
	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		if (getBitmapFromMemCache(key) == null) {
			mMemoryCache.put(key, bitmap);
		}
	}

	public Bitmap getBitmapFromMemCache(String key) {
		if (mMemoryCache.size() == 0)
			return null;
		else
			return (Bitmap) mMemoryCache.get(key);
	}
	
	public LruCache<String, Bitmap> getmMemoryCache() {
		return mMemoryCache;
	}

	public void setmMemoryCache(LruCache<String, Bitmap> mMemoryCache) {
		this.mMemoryCache = mMemoryCache;
	}
	
	public void setPageVisible(LinearLayout page){
    	Animation animation = new TranslateAnimation(0, 0, 1200, 0);
    	animation.setDuration(2000);
    	animation.setFillAfter(true);
    	page.startAnimation(animation);
    	page.setVisibility(View.VISIBLE);
    }
    
    public void setPageInvisible(LinearLayout page){
    	Animation animation = new TranslateAnimation(0, 0, 0, 1200);
    	animation.setDuration(2000);
    	animation.setFillAfter(true);
    	page.startAnimation(animation);
    	page.setVisibility(View.GONE);
    }
    
    public void setPageVisible(RelativeLayout page){
    	Animation animation = new TranslateAnimation(0, 0, 1200, 0);
    	animation.setDuration(2000);
    	animation.setFillAfter(true);
    	page.startAnimation(animation);
    	page.setVisibility(View.VISIBLE);
    }
    
    public void setPageInvisible(RelativeLayout page){
    	Animation animation = new TranslateAnimation(0, 0, 0, 1200);
    	animation.setDuration(2000);
    	animation.setFillAfter(true);
    	page.startAnimation(animation);
    	page.setVisibility(View.GONE);
    }

}
