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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcertif.reseaujcertif.R;
import com.jcertif.reseaujcertif.persistances.Comment;
import com.jcertif.reseaujcertif.services.JCertifApplication;
import com.jcertif.reseaujcertif.services.JCertifManager;
import com.jcertif.reseaujcertif.services.Parametres;

public class CommentsAdapter extends ArrayAdapter<Comment>{
	
	private JCertifManager jCertifManager;
	private JCertifApplication jCertifApplication;

	public CommentsAdapter(Context context, int textViewResourceId, List<Comment> objects){
		super(context, textViewResourceId, objects);
		
		jCertifManager = JCertifManager.getInstance();
        jCertifApplication =  (JCertifApplication) context.getApplicationContext();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){

		LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = layoutInflater.inflate(R.layout.single_comment, null);

		TextView name = (TextView) convertView.findViewById(R.id.name);
		TextView comment = (TextView) convertView.findViewById(R.id.comment);
		TextView date = (TextView) convertView.findViewById(R.id.date);
		
		final RelativeLayout img_profil = (RelativeLayout) convertView.findViewById(R.id.img_profil);
		final String url = new Parametres().getImgULR(jCertifManager.getListComments().get(position).getUser().getPhoto_url());
		
		name.setText(jCertifManager.getListComments().get(position).getUser().getFirstname() + " "+jCertifManager.getListComments().get(position).getUser().getName());
		date.setText(jCertifManager.getListComments().get(position).getCreated());
		comment.setText(jCertifManager.getListComments().get(position).getContent());

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
				return jCertifApplication.ImageOperations(url);  
			}
		}.execute("");

		return convertView;
	}
}