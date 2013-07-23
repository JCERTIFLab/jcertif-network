package com.jcertif.reseaujcertif.services;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class ListItimizedOverlay extends ItemizedOverlay<OverlayItem>{

	private ArrayList<OverlayItem> arrayListOverlayItem = new ArrayList<OverlayItem>();
	private Context context;

	public ListItimizedOverlay(Drawable defaultMarker, Context pContext){
		super(boundCenterBottom(defaultMarker));
		this.context = pContext;
	}
	
	@Override
	protected boolean onTap(int index){
		OverlayItem item = arrayListOverlayItem.get(index);
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle(item.getTitle());
		dialog.setMessage(item.getSnippet());
		dialog.show();
		return true;
	}

	@Override
	protected OverlayItem createItem(int i){
		return arrayListOverlayItem.get(i);
	}

	@Override
	public int size(){
		return arrayListOverlayItem.size();
	}

	public void addOverlayItem(OverlayItem overlay){
		arrayListOverlayItem.add(overlay);
		populate();
	}

}