package com.jcertif.reseaujcertif.interfaces.ihm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;
import com.jcertif.reseaujcertif.R;
import com.jcertif.reseaujcertif.com.GoogleParser;
import com.jcertif.reseaujcertif.interfaces.services.MyLocation;
import com.jcertif.reseaujcertif.interfaces.services.MyLocation.LocationResult;
import com.jcertif.reseaujcertif.interfaces.services.RouteOverlay;
import com.jcertif.reseaujcertif.persistances.Parser;
import com.jcertif.reseaujcertif.persistances.Route;
import com.jcertif.reseaujcertif.transvers.AnimationFactory;
import com.jcertif.reseaujcertif.transvers.AnimationFactory.FlipDirection;
import com.jcertif.reseaujcertif.transvers.Rose;

public class FindCars extends MapActivity implements LocationListener, SensorEventListener {

	private MapController mc = null;
	private MyLocationOverlay myLocation = null;

	private boolean subMenuVisble, isSelected, locationFind, mapActivate;
	private String mode;
	private double lat = 0;
	private double lng = 0;
	private int mCurrentLayoutState, btnSelected, niveau;
	
	private List<Overlay> mapOverlays;
	private Projection projection; 
	
	private GeoPoint starPoint, endPoint;
	private LinearLayout compasse, loading, bar_menu, body1, loading_location;
	private ImageView menu, roll_back, car, direction, walking, retour_page;
	private TextView title1, title3, distance;
	private MapView mapView = null;
	private LocationManager lm = null;
	private ViewFlipper viewFlipper;
	
	public  Route route = null;
	
	private SensorManager sensorManager;
	private Sensor sensor;
	private Rose rose;
	private Parser parser;
	private Route r = null;
	
	private Handler mHandler;
	private Timer titleTimer;
	
	public static double latCar;
	public static double lngCar;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.find_cars);
		
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		
        titleTimer = new Timer();
		mHandler = new Handler();
		
		niveau = 0;
		
		isSelected = false;
		locationFind = false;
		subMenuVisble = false;
		mapActivate = false;
		mode = "driving";
		
		viewFlipper 	 = (ViewFlipper) findViewById(R.id.viewFlipper);
		
		compasse		 = (LinearLayout) findViewById(R.id.compasse);		
		loading			 = (LinearLayout) findViewById(R.id.loading);	
		bar_menu		 = (LinearLayout) findViewById(R.id.bar_menu);
		loading_location = (LinearLayout) findViewById(R.id.loading_location);	
		body1			 = (LinearLayout) findViewById(R.id.body1);
		
		distance		 = (TextView) findViewById(R.id.distance);	
		title1 			 = (TextView) findViewById(R.id.title1);
		title3 		 	 = (TextView) findViewById(R.id.title3);
		
		mapView 		 = (MapView) findViewById(R.id.mapview);
		
		menu			 = (ImageView) findViewById(R.id.menu);
		roll_back		 = (ImageView) findViewById(R.id.roll_back);
		car				 = (ImageView) findViewById(R.id.car);
		direction		 = (ImageView) findViewById(R.id.direction);
		walking			 = (ImageView) findViewById(R.id.walking);
		retour_page	 	 = (ImageView) findViewById(R.id.retour_page);
		
		rose = new Rose(this);
		compasse.addView(rose);

		mapView.setBuiltInZoomControls(true);
		
		lm = (LocationManager) this.getSystemService(LOCATION_SERVICE);
		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0,this);
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);	

		mc = mapView.getController();
		mc.setZoom(15);

		myLocation = new MyLocationOverlay(getApplicationContext(), mapView);
		myLocation.runOnFirstFix(new Runnable() {
		    public void run() {
		    	mc.animateTo(myLocation.getMyLocation());
		    	mc.setZoom(15);
		    }
		});
		
		retour_page.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {	
		        enableReturnPage();	
			}
		});
		
		title1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {				
		        if(!isSelected){
		        	isSelected = true;
		        	enableEnregestrer();
		        }				
			}
		});
		
		title3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {				
		        if(!isSelected){
		        	isSelected = true;
		        	enableMap();
		        }				
			}
		});
		
		roll_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
	        	enableRollBack();
			}
		});
		
		viewFlipper.setOnClickListener(new OnClickListener() { 
        	@Override
        	public void onClick(View v) { 
        		AnimationFactory.flipTransition(viewFlipper, FlipDirection.LEFT_RIGHT);
        	}
        });
		
		car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	if(!mode.equals("driving")){
            		if(!isSelected){
                		isSelected = true;
                		mode = "driving";
                		showWelcomMsg("Le plus court chemin en voiture");
                		loading.setVisibility(View.VISIBLE);
                		try{
                			drowRout();
                		}catch(Exception e){}
                    	subMenuVisble = false;
                	} 
            	}       	
            }
        });
		
		walking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	if(!mode.equals("walking")){
                	if(!isSelected){
                		isSelected = true;
                		mode = "walking";
                		showWelcomMsg("Le plus court chemin à pied");
                		loading.setVisibility(View.VISIBLE);
                		try{
                        	drowRout();
                		}catch(Exception e){}
                    	subMenuVisble = false;
                	}
            	} 
            }
        });
		
		menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {       
            	if(!subMenuVisble){
            		subMenuVisble = true;
            		setSubMenuVisible();
            	}else{
            		subMenuVisble = false;
            		setSubMenuInvisible();
            	}           	
            }
        });

		direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	showWelcomMsg("Rechercher la direction");
            	Intent intent = new Intent(FindCars.this, CameraView.class);
        		startActivity(intent);
            }
        });
		
		readFile();
		
	}

	public void switchLayoutStateTo(int switchTo){
        while(mCurrentLayoutState != switchTo){
        	if(mCurrentLayoutState > switchTo){
        		mCurrentLayoutState--;
        		viewFlipper.setInAnimation(inFromLeftAnimation());
        		viewFlipper.setOutAnimation(outToRightAnimation());
        		viewFlipper.showPrevious();
            
        	} else {
        		mCurrentLayoutState++;
        		viewFlipper.setInAnimation(inFromRightAnimation());
                viewFlipper.setOutAnimation(outToLeftAnimation());
                viewFlipper.showNext();
            }          
               
        };
	}

	protected Animation inFromRightAnimation() {
		 
        Animation inFromRight = new TranslateAnimation(
                        Animation.RELATIVE_TO_PARENT, +1.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromRight.setDuration(500);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        return inFromRight;
	}

	protected Animation outToLeftAnimation() {
        Animation outtoLeft = new TranslateAnimation(
                        Animation.RELATIVE_TO_PARENT, 0.0f,
                        Animation.RELATIVE_TO_PARENT, -1.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoLeft.setDuration(500);
        outtoLeft.setInterpolator(new AccelerateInterpolator());
        return outtoLeft;
	}

	protected Animation inFromLeftAnimation() {
        Animation inFromLeft = new TranslateAnimation(
                        Animation.RELATIVE_TO_PARENT, -1.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromLeft.setDuration(500);
        inFromLeft.setInterpolator(new AccelerateInterpolator());
        return inFromLeft;
	}

	protected Animation outToRightAnimation() {
        Animation outtoRight = new TranslateAnimation(
                        Animation.RELATIVE_TO_PARENT, 0.0f,
                        Animation.RELATIVE_TO_PARENT, +1.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoRight.setDuration(500);
        outtoRight.setInterpolator(new AccelerateInterpolator());
        return outtoRight;
	}
	
	/////////////////////////////////////////////////////////////
	
	private void readFile() {
		String msg = ReadDate("data.txt");
		if(msg.length()>0){
			latCar = Double.parseDouble(msg.substring(0, msg.indexOf("|")));
			msg = msg.substring(msg.indexOf("|")+1, msg.length());
			lngCar = Double.parseDouble(msg.substring(0, msg.indexOf("|")));
			title3.setVisibility(View.VISIBLE);
		}
	}

	protected void enableRollBack() {
		roll_back.setBackgroundResource(R.drawable.return_on);
		btnSelected = 3;
		refreshTimer();
	}

	private void enableEnregestrer(){
		title1.setBackgroundResource(R.drawable.shape);
		title1.setTextColor(Color.rgb(255, 255, 255));
		
		btnSelected = 1;
		refreshTimer();
	}
	
	private void enableMap(){
		title3.setBackgroundResource(R.drawable.shape);
		title3.setTextColor(Color.rgb(255, 255, 255));
		
		btnSelected = 2;
		refreshTimer();
	}
	
	protected void enableReturnPage() {
		retour_page.setBackgroundResource(R.drawable.return_on);
		btnSelected = 4;
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
				 desableEnregistre();
				 break;
				 
			 case 2:
				 desableMap();
				 break;
				 
			 case 3:
				 desableRollBack();
				 break;
				 
			 case 4:
				 desableReturnPage();
				 break;
				 
			 }
			 
		 }
	};
	
	private void desableEnregistre(){

		title1.setBackgroundResource(R.drawable.shape_whate);
		title1.setTextColor(Color.rgb(79, 129, 189));
		
		LocationResult locationResult = new LocationResult(){
		    @Override
		    public void gotLocation(Location location){
		    	latCar = location.getLatitude();
		    	lngCar = location.getLongitude();
		    	locationFind = true;
		    }
		};
		
		MyLocation myLocation = new MyLocation();
		myLocation.getLocation(this, locationResult);
		SaveLocation();	
		title3.setVisibility(View.VISIBLE);
	}
	 
	protected void desableReturnPage() {
		retour_page.setBackgroundResource(R.drawable.return_off);

		Intent intent = new Intent(FindCars.this, Principale.class);
    	startActivity(intent);
   		finish();	
	}

	protected void desableRollBack() {
		roll_back.setBackgroundResource(R.drawable.return_off);
		isSelected = false;
		mapActivate = false;
    	niveau = 0;
		switchLayoutStateTo(0);
	}

	private void SaveLocation() {
		
		loading_location.setVisibility(View.VISIBLE);
		body1.setVisibility(View.GONE);

		new AsyncTask<String, Long, Bitmap>() {

			protected void onPostExecute(Bitmap result) {
				SaveDate(latCar+"|"+lngCar+"|", "data.txt");
				loading_location.setVisibility(View.GONE);
				body1.setVisibility(View.VISIBLE);
				showWelcomMsg("Endroit enregistré");
				isSelected = false;
				this.cancel(true);
			}

			protected Bitmap doInBackground(String... params) {			
				while(!locationFind){}
				return null;  
			}
		}.execute("");
	}
	
	private String ReadDate(String filePath){
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
	
	private void SaveDate(String date, String filePath){
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
	
	private void desableMap(){
		
		title3.setBackgroundResource(R.drawable.shape_whate);
		title3.setTextColor(Color.rgb(79, 129, 189));
		
		isSelected = false;
    	mapActivate = true;
		switchLayoutStateTo(1);
		niveau = 1;
		if(lat!=0)
			Draw();
	}

	/////////////////////////////////////////////////////////////////////
	
	protected void DrewFlyTrajectoir() {
		mapOverlays = mapView.getOverlays();  
		mapOverlays.removeAll(mapOverlays);
		projection = mapView.getProjection();
		mapOverlays.add(new MyOverlay());
			
		MonOverlay object2 = new MonOverlay(getResources().getDrawable(R.drawable.pinred));	
		object2.addPoint(starPoint);	
		mapView.getOverlays().add(object2);
			
		MonOverlay object1 = new MonOverlay(getResources().getDrawable(R.drawable.pin));	
		object1.addPoint(endPoint);	
		mapView.getOverlays().add(object1);

		mc.animateTo(starPoint);
		mc.setCenter(starPoint);
	}

	private void setSubMenuInvisible(){
    	
    	Animation animation = new TranslateAnimation(0, 0, 0, -100);
    	animation.setDuration(1000);
    	animation.setFillAfter(true);
    	bar_menu.startAnimation(animation);
    	bar_menu.setVisibility(View.VISIBLE);
    	
    	Animation rotation = AnimationUtils.loadAnimation(this, R.anim.rotate);
    	menu.startAnimation(rotation);

    }
    
    private void setSubMenuVisible(){
    	Animation animation = new TranslateAnimation(0, 0, -100, 0);
    	animation.setDuration(1000);
    	animation.setFillAfter(true);
    	bar_menu.startAnimation(animation);
    	bar_menu.setVisibility(View.GONE);
    	
    	Animation rotation = AnimationUtils.loadAnimation(this, R.anim.rotate);
    	menu.startAnimation(rotation);
    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==1000){
			finish();
		}
	}
    
    @Override
    protected void onResume() {
		super.onResume();
		sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
		enableGPS(true);
		overridePendingTransition(0,0);
		myLocation.enableMyLocation();
		myLocation.enableCompass();
		
    }

	@Override
	protected void onPause() {
		super.onPause();
		enableGPS(false);
		sensorManager.unregisterListener(this);
		overridePendingTransition(0,0);
		lm.removeUpdates(this);
	}
	
	private void Zoom() {
	    int minLatitude = Integer.MAX_VALUE;
	    int maxLatitude = Integer.MIN_VALUE;
	    int minLongitude = Integer.MAX_VALUE;
	    int maxLongitude = Integer.MIN_VALUE;
	    
	    int lati = starPoint.getLatitudeE6();
        int lon = starPoint.getLongitudeE6();

        maxLatitude = Math.max(lati, maxLatitude);
        minLatitude = Math.min(lati, minLatitude);
        maxLongitude = Math.max(lon, maxLongitude);
        minLongitude = Math.min(lon, minLongitude);

        lati = endPoint.getLatitudeE6();
        lon = endPoint.getLongitudeE6();

        maxLatitude = Math.max(lati, maxLatitude);
        minLatitude = Math.min(lati, minLatitude);
        maxLongitude = Math.max(lon, maxLongitude);
        minLongitude = Math.min(lon, minLongitude);
	   
	    
	    mc.zoomToSpan(Math.abs(maxLatitude - minLatitude), Math.abs(maxLongitude - minLongitude));
	    mc.animateTo(new GeoPoint((maxLatitude + minLatitude) / 2, (maxLongitude + minLongitude) / 2));
	}
	
	public void drowRout(){

	    loading.setVisibility(View.VISIBLE);		 
		
		new AsyncTask<String, Long, Bitmap>() {

			protected void onPostExecute(Bitmap result) {
			
				loading.setVisibility(View.INVISIBLE);	
				
				MonOverlay object2 = new MonOverlay(getResources().getDrawable(R.drawable.pinred));	
				object2.addPoint(starPoint);	
				mapView.getOverlays().add(object2);

				MonOverlay object1 = new MonOverlay(getResources().getDrawable(R.drawable.pin));	
				object1.addPoint(endPoint);	
				mapView.getOverlays().add(object1);

				Zoom();
				isSelected = false;
				
				setSubMenuInvisible();

				this.cancel(true);
			}

			protected Bitmap doInBackground(String... params) {
				
				try{
					route = directions(starPoint,endPoint);

					if(route!=null){
						RouteOverlay routeOverlay;

						if(mode.equals("driving"))
							routeOverlay = new RouteOverlay(route, Color.BLUE);	
						else
							routeOverlay = new RouteOverlay(route, Color.RED);
			
						mapView.getOverlays().clear();
				
	     				mapView.getOverlays().add(routeOverlay);
	     				AnimationSet set2 = new AnimationSet(true);
	     
	     				Animation animation2 = new AlphaAnimation(0.0f, 1.0f);
	     				animation2.setDuration(1000);
	     				set2.addAnimation(animation2);
	   
	     				animation2 = new TranslateAnimation(0, Animation.RELATIVE_TO_SELF,Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,Animation.RELATIVE_TO_SELF, 0.0f);
	     				animation2.setDuration(1500);
	     				set2.addAnimation(animation2);
	     		
					}else{
						Toast.makeText(getApplicationContext(), "Vérifiez votre connexion", Toast.LENGTH_LONG).show();
					}
				}catch(Exception e){ }
				
				return null;  
			}
		}.execute("");
	}
    
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	private Route directions(final GeoPoint start, final GeoPoint dest) {

		String jsonURL = "http://maps.google.com/maps/api/directions/json?";
		final StringBuffer sBuf = new StringBuffer(jsonURL);
		sBuf.append("origin=");
		sBuf.append(start.getLatitudeE6()/1E6);
		sBuf.append(',');
		sBuf.append(start.getLongitudeE6()/1E6);
		sBuf.append("&destination=");
		sBuf.append(dest.getLatitudeE6()/1E6);
		sBuf.append(',');
		sBuf.append(dest.getLongitudeE6()/1E6);
		sBuf.append("&sensor=true&mode="+mode);
		
		parser = new GoogleParser(sBuf.toString());
		r =  parser.parse();
		return r;
	}

	@Override
	public void onLocationChanged(Location location) {
		
		lat = location.getLatitude();
		lng = location.getLongitude();

		if(mapActivate)
			Draw();	
		
	}
	
	private void Draw() {
		starPoint = new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6));
		endPoint = new GeoPoint((int) (latCar * 1E6), (int) (lngCar * 1E6));
		
		mapView.getOverlays().clear();
		
		MonOverlay object2 = new MonOverlay(getResources().getDrawable(R.drawable.pinred));	
		object2.addPoint(starPoint);	
		mapView.getOverlays().add(object2);
		
		MonOverlay object1 = new MonOverlay(getResources().getDrawable(R.drawable.pin));	
		object1.addPoint(endPoint);	
		mapView.getOverlays().add(object1);

		mc.animateTo(starPoint);
		mc.setCenter(starPoint);	
		
		double dis = CalculationByDistance(starPoint,endPoint);
		if(dis<1){
			String m = dis+"";
			if(m.substring(m.indexOf("."), m.length()).length()<4)
				distance.setText(" "+dis+" m ");
			else
				distance.setText(" "+m.substring(0, m.indexOf(".")+4)+" m ");
		}else{
			String txt = dis+"";
			int m = Integer.parseInt(txt.substring(0, txt.indexOf(".")));
			distance.setText(" "+m+" m ");
		}
		
		if(mode.equals("driving")){
			drowRout();
		}else if(mode.equals("fly")){
			mapOverlays = mapView.getOverlays();  
			mapOverlays.removeAll(mapOverlays);
			projection = mapView.getProjection();
			mapOverlays.add(new MyOverlay());
		}else
			drowRout();
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

	public double CalculationByDistance(GeoPoint StartP, GeoPoint EndP) {  
		
		Location locationA = new Location("point A");  

		locationA.setLatitude(StartP.getLatitudeE6() / 1E6);  
		locationA.setLongitude(StartP.getLongitudeE6() / 1E6);  

		Location locationB = new Location("point B");  

		locationB.setLatitude(EndP.getLatitudeE6() / 1E6);  
		locationB.setLongitude(EndP.getLongitudeE6() / 1E6);  

		double distance = locationA.distanceTo(locationB); 
		
		return distance;
	} 
	
	public void onProviderEnabled(String provider) {}

	public void onProviderDisabled(String provider) {}

	public void onStatusChanged(String provider, int status, Bundle extras) {}

	public class MonOverlay extends ItemizedOverlay<OverlayItem>{

		List<GeoPoint> path = new ArrayList<GeoPoint>();

		public MonOverlay(Drawable defaultMarker) {
			super(boundCenterBottom(defaultMarker));
		}

		public void addPoint(GeoPoint point) {
			this.path.add(point);
			populate();			
		}

		@Override
		protected OverlayItem createItem(int i) {

			GeoPoint point = path.get(i);
			return new OverlayItem(point, "Mon Point", "Desp");
		}

		@Override
		public int size() {
			return path.size();
		}
	}
	
	private void enableGPS(boolean enable) {
	    String provider = Settings.Secure.getString(getContentResolver(), 
	        Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

	    if(provider.contains("gps") == enable) {
	        return; // the GPS is already in the requested state
	    }

	    final Intent poke = new Intent();
	    poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
	    poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
	    poke.setData(Uri.parse("3"));
	    sendBroadcast(poke);
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
	    int orientation = (int) event.values[0];
	    rose.setDirection(orientation); 
	}

	class MyOverlay extends Overlay{

	    public MyOverlay(){

	    }   

	    public void draw(Canvas canvas, MapView mapv, boolean shadow){
	        super.draw(canvas, mapv, shadow);

	        Paint   mPaint = new Paint();
	        mPaint.setDither(true);
	        mPaint.setColor(Color.GREEN);
	        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
	        mPaint.setStrokeJoin(Paint.Join.ROUND);
	        mPaint.setStrokeCap(Paint.Cap.ROUND);
	        mPaint.setStrokeWidth(2);

	        Point p1 = new Point();
	        Point p2 = new Point();
	        Path path = new Path();

	        projection.toPixels(starPoint, p1);
	        projection.toPixels(endPoint, p2);

	        path.moveTo(p2.x, p2.y);
	        path.lineTo(p1.x,p1.y);

	        canvas.drawPath(path, mPaint);
	    }
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			if(niveau == 1){
				switchLayoutStateTo(0);
	            niveau = 0;
			}else
				exitApp();
			return true;
		}
		return false;
	}

	private void exitApp(){
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

}
