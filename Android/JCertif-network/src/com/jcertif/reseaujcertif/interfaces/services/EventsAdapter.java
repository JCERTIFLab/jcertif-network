package com.jcertif.reseaujcertif.interfaces.services;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcertif.reseaujcertif.R;
import com.jcertif.reseaujcertif.persistances.Event;
import com.jcertif.reseaujcertif.services.JCertifApplication;
import com.jcertif.reseaujcertif.services.JCertifManager;
import com.jcertif.reseaujcertif.services.Parametres;

public class EventsAdapter extends ArrayAdapter<Event>{
	
	private JCertifApplication jCertifApplication;
	private JCertifManager jCertifManager;
	
	public EventsAdapter(Context context, int textViewResourceId, List<Event> objects){
		super(context, textViewResourceId, objects);
		
		jCertifManager = JCertifManager.getInstance();
        jCertifApplication =  (JCertifApplication) context.getApplicationContext();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){

		LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = layoutInflater.inflate(R.layout.single_photo, null);

		TextView title 		 		= (TextView) convertView.findViewById(R.id.title_new);
		final LinearLayout loading 	= (LinearLayout) convertView.findViewById(R.id.loading);
		final LinearLayout img 	 	= (LinearLayout) convertView.findViewById(R.id.img);
		
		final String url = new Parametres().getImgULR(jCertifManager.getListEvents().get(position).getImg_url());
		
		title.setText(jCertifManager.getListEvents().get(position).getTitle());

		new AsyncTask<String, Long, Bitmap>() {

			protected void onPostExecute(Bitmap result) {
				if (result != null) {
					BitmapDrawable background = new BitmapDrawable(result);
					img.setBackgroundDrawable(background);
					img.setVisibility(View.VISIBLE);
					loading.setVisibility(View.GONE);
				} 

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