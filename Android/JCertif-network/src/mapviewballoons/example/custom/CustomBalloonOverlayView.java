package mapviewballoons.example.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.maps.OverlayItem;
import com.jcertif.reseaujcertif.R;
import com.jcertif.reseaujcertif.services.JCertifApplication;
import com.readystatesoftware.mapviewballoons.BalloonOverlayView;

public class CustomBalloonOverlayView<Item extends OverlayItem> extends BalloonOverlayView<CustomOverlayItem> {

	private TextView title;
	private TextView snippet;
	private RelativeLayout img_profil;
	private JCertifApplication jCertifApplication;
	
	public CustomBalloonOverlayView(Context context, int balloonBottomOffset) {
		super(context, balloonBottomOffset);
		jCertifApplication =  (JCertifApplication) context.getApplicationContext();
	}
	
	@Override
	protected void setupView(Context context, final ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.balloon_overlay_example2, parent);
		
		title 		= (TextView) v.findViewById(R.id.balloon_item_title);
		snippet 	= (TextView) v.findViewById(R.id.balloon_item_snippet);
		img_profil 	= (RelativeLayout) v.findViewById(R.id.img_profil);
	}

	@Override
	protected void setBalloonData(final CustomOverlayItem item, ViewGroup parent) {
		title.setText(item.getTitle());
		snippet.setText(item.getSnippet());	
		
		new AsyncTask<String, Long, Bitmap>() {
			protected void onPostExecute(Bitmap result) {			
				if(result != null){
					BitmapDrawable bitmapDrawable = new BitmapDrawable(result);
					img_profil.setBackgroundDrawable(bitmapDrawable);
				}
				this.cancel(true);
			}
			@Override
			protected Bitmap doInBackground(String... params) { 
				return jCertifApplication.ImageOperations(item.mImageURL);    
			}
		}.execute("");
	}	
}
