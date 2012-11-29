package com.missionse.example;


import com.missionse.example.R.layout;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AndroidListMediaActivity extends Activity {

	PlayerFragment myPlayerFragment;
	VideoListFragment myVideoListFragment;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.main);
        FragmentManager fragmentManager = getFragmentManager();
        myPlayerFragment = (PlayerFragment)fragmentManager.findFragmentById(R.id.playerfragment);
        myVideoListFragment = (VideoListFragment)fragmentManager.findFragmentById(R.id.videolistfragment);
    }

    public void onResume(Bundle savedInstanceState) {
        sendUri();
    }

    public void sendUri() {
    	myPlayerFragment.setTargetUri(Uri.parse("android.resource://" + getPackageName() + "/" 
				+ R.raw.test2));
    }

}