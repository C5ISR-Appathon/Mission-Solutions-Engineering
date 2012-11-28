package com.missionse.example;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.MenuInflater;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;


public class MyItemizedOverlay extends ItemizedOverlay {
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private MyMapActivity mActivity = null;
	private int mTrackId = 0;
	
	public MyItemizedOverlay(Drawable defaultMarker, MyMapActivity activity, int track_id) {
		  super(boundCenterBottom(defaultMarker));
		  mActivity = activity;
		  mTrackId = track_id;
		// TODO Auto-generated constructor stub
	}

	@Override
	public int size() {
	  return mOverlays.size();
	}

	public void addOverlayItem(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}
	
	@Override
	protected OverlayItem createItem(int i) {
	  return mOverlays.get(i);
	}
	@Override
	protected boolean onTap(int index) {
	  OverlayItem item = mOverlays.get(index);
	  final int trackId = mTrackId;
	  AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity);
	  dialog.setTitle(item.getTitle());
	  dialog.setMessage(item.getSnippet());
	  dialog.setPositiveButton("Video", new DialogInterface.OnClickListener() {

	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	            // TODO Auto-generated method stub
	        	mActivity.showVideo(trackId);
	        }
	    });

	  dialog.setNeutralButton("Stores", new DialogInterface.OnClickListener() {

	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	                // TODO Auto-generated method stub
	            	mActivity.showStores(trackId);
	            }
	        });

	  
	  dialog.show();
	  return true;
	}

}
