package com.jcertif.reseaujcertif.transvers;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CustomCameraView extends SurfaceView implements SurfaceHolder.Callback {	
	private Camera 	camera;
	private SurfaceHolder 	holder;
	private Parameters 	parameters;
	
	public CustomCameraView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
			
		holder = getHolder();
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	    holder.addCallback(this);
			
	    //désactiver le vérouillage de l’écran
	    setKeepScreenOn(true);
	}
	
	public CustomCameraView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,	int height) {
			
		try {
			parameters = camera.getParameters();
			parameters.setPreviewSize(800, 480);
			camera.setParameters(parameters);
			camera.setPreviewDisplay(holder);
			camera.startPreview();
		} catch (Exception e) {
				
		}
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder _holder) {
		camera = Camera.open();
		try {
		   camera.setPreviewDisplay(_holder);
		} catch (Exception e) {
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		camera.stopPreview();
		camera.release();		
	}
}
