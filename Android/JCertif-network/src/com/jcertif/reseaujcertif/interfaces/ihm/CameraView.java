package com.jcertif.reseaujcertif.interfaces.ihm;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;

import com.google.android.maps.GeoPoint;
import com.jcertif.reseaujcertif.R;

public class CameraView extends Activity {
	
	private ImageView target;
	
	private int mScreenWidth, mScreenHeight;
		
	private GeoPoint[] 		locs;

	private Context 		ctx;
	
	private Location myLocation;
	
	private SensorManager 		sensorMngr;
	private SensorEventListener	sensorLstr;	

	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_layout);
        
        ctx=this;
        
        mScreenWidth=getScreenWidth();
        mScreenHeight=getScreenHeight();
               
        target = (ImageView) findViewById(R.id.target);
        
        locs=new GeoPoint[3];

       	locs[0]= new GeoPoint((int) (FindCars.latCar * 1E6), (int) (FindCars.lngCar * 1E6));	
       	
       	myLocation=getMyLocation(this);
       	
       	
       	showWelcomMsg("Tourner autour de vous jusqu'à ce que l'indicateur de la direction à prendre s'affiche au centre de l'écran");
     }
	
	private void showWelcomMsg(String msg) {
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.toast_custom_layout, (ViewGroup) findViewById(R.id.toast_layout_root));

		TextView text = (TextView) layout.findViewById(R.id.text);
		text.setText(msg);

		Toast toast = new Toast(this);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		toast.show();
	}
	
	//Récupérer la position courante de l’utilisateur
	
	public final static Location getMyLocation(Context _context) {
		Location location = null;
		try {
			LocationManager locationManager = (LocationManager) _context.getSystemService(Context.LOCATION_SERVICE);
			List<String> providers = locationManager.getProviders(true);
			for (int i = providers.size() - 1; i >= 0; i--){
				location = locationManager.getLastKnownLocation(providers.get(i));
			}
		} catch (Exception e) { }
		return location;
	}
	
	//Récupérer les dimensions de l’écran
	
	public int getScreenHeight() {
		DisplayMetrics metrics = new DisplayMetrics();
		((Activity) this).getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.heightPixels;
	}
    
	public int getScreenWidth() {
		DisplayMetrics metrics = new DisplayMetrics();
		((Activity) this).getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.widthPixels;
	}
	
	//récupérer l’orientation du téléphone
	
	@Override
	protected void onStart() {    	
	    super.onStart();
	    sensorMngr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	    sensorLstr = createListener();
	    sensorMngr.registerListener(sensorLstr, sensorMngr.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_UI);
	}
	
	@Override
	protected void onStop() {    	
	    super.onStop();
	    sensorMngr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	    sensorMngr.unregisterListener(sensorLstr, sensorMngr.getDefaultSensor(Sensor.TYPE_ORIENTATION));    	
	}
	
	public final static float getSpotAngle(Context _context, GeoPoint p, Location _me) {
        Location location = new Location("LOCATION_SERVICE");

        try {
            location.setLatitude(p.getLatitudeE6());
            location.setLongitude(p.getLatitudeE6());
        } catch (Exception e) {
        }
        return _me.bearingTo(location);
    }

    public final static double angleDirection(double spotAngle, double direction) {
        double angle;
        if (spotAngle > 0) {
            if (direction < spotAngle - 180)
                angle = 360 - spotAngle + direction;
            else
                angle = direction - spotAngle;
        } else {
            if (direction > spotAngle + 180)
                angle = direction - spotAngle - 360;
            else
                angle = direction - spotAngle;
        }
        return (angle);
    }
    
    public final static void moveSpot(Context c, ImageView tv, GeoPoint p, float azimut, Location me, int screenWidth, float roll, int screenHeight, float pitch){
        
    	int angle = (int) (angleDirection(getSpotAngle(c,p,me), azimut));
        LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT); 
        int marginTop;
        
        if(pitch<screenHeight/2)
        	marginTop = (int)((roll-90)/90*screenHeight);
        else
        	marginTop = -(int)((roll-90)/90*screenHeight);
        
        lp.setMargins(-angle*screenWidth/90,marginTop, 0, 0);
        lp.gravity=Gravity.CENTER;
        tv.setLayoutParams(lp);
    }
    
    public SensorEventListener createListener(){
    	return new SensorEventListener() {
    		public void onAccuracyChanged(Sensor _sensor, int _accuracy) {

    		}
    		public void onSensorChanged(SensorEvent evt) {
    			if (evt.sensor.getType() == Sensor.TYPE_ORIENTATION) {
    				final float vals[] = evt.values;    				
    				final float azimut = vals[0] - 270;
    				final float roll   = vals[2];
    				final float pitch  = Math.abs(vals[1]);
    				moveSpot(ctx,target,locs[0],azimut,myLocation, mScreenWidth, roll, mScreenHeight, pitch);
    			}
    		}
    	};
    }
    
} 