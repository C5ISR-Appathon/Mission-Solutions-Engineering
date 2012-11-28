
package com.missionse.example;

import java.nio.charset.Charset;
import java.util.List;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MyMapActivity extends MapActivity implements CreateNdefMessageCallback,
OnNdefPushCompleteCallback {

	NfcAdapter mNfcAdapter;
	private static final int MESSAGE_SENT = 1;
	public static final String TAG = "AssetOnDemand";
	private MapView mMapView;
	
	private GeoUpdateHandler locationListener =  new GeoUpdateHandler();
	private MapController mapController = null;

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.my_map_activity);

		// Check for available NFC Adapter
		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		if (mNfcAdapter == null) {
			Log.e(TAG, "NFC not available on device.");
		} else {
			// Register callback to set NDEF message
			mNfcAdapter.setNdefPushMessageCallback(this, this);
			// Register callback to listen for message-sent success
			mNfcAdapter.setOnNdefPushCompleteCallback(this, this);
		}

		mMapView = (MapView) findViewById(R.id.mapview);
		mMapView.setBuiltInZoomControls(true);
		MapController mapController = mMapView.getController();
		mapController.setZoom(7); // Zoom 1 is world view
		mapController.setCenter(new GeoPoint(32240000,-80120000));
		/*
         locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
         locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
		 */
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	/**
	 * Implementation for the CreateNdefMessageCallback interface
	 */
	@Override
	public NdefMessage createNdefMessage(NfcEvent event) {

		String payload = "Two to beam across";
		String mimeType = "application/com.missionse.example";
		byte[] mimeBytes = mimeType.getBytes(Charset.forName("US-ASCII"));
		NdefMessage nfcMessage = new NdefMessage(new NdefRecord[] {
				new NdefRecord(
						NdefRecord.TNF_MIME_MEDIA,
						mimeBytes,
						new byte[0],
						payload.getBytes()),
						NdefRecord.createApplicationRecord("com.missionse.example")
		});
		
		return nfcMessage;
	}

	@Override
	public void onNdefPushComplete(NfcEvent arg0) {

		mHandler.obtainMessage(MESSAGE_SENT).sendToTarget();

		displayAssets();
	}

	private void displayAssets() {

		List<Overlay> mapOverlays = mMapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.helicopter_small);
        MyItemizedOverlay itemizedoverlay = new MyItemizedOverlay(drawable, this, 1);

        GeoPoint point = new GeoPoint(32240000,-80120000);
        OverlayItem overlayitem = new OverlayItem(point, "Track 1", "Show Video");

        itemizedoverlay.addOverlayItem(overlayitem);
        mapOverlays.add(itemizedoverlay);

        Drawable drawable2 = this.getResources().getDrawable(R.drawable.tracked_vehicle_small);
        MyItemizedOverlay itemizedoverlay2 = new MyItemizedOverlay(drawable2, this, 2);

        GeoPoint point2 = new GeoPoint(40240000,-75120000);
        OverlayItem overlayitem2 = new OverlayItem(point2, "Track 2", "Show Video");

        itemizedoverlay2.addOverlayItem(overlayitem2);
        mapOverlays.add(itemizedoverlay2);
	}

	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_SENT:
				Toast.makeText(getApplicationContext(), "Connection Established!", Toast.LENGTH_LONG).show();
				break;
			}
		}
	};

	@Override
	public void onResume() {
		super.onResume();
		// Check to see that the Activity started due to an Android Beam
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
			processIntent(getIntent());
		}
	}

	@Override
	public void onNewIntent(Intent intent) {
		// onResume gets called after this to handle the intent
		setIntent(intent);
	}


	void processIntent(Intent intent) {
		Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
				NfcAdapter.EXTRA_NDEF_MESSAGES);

		NdefMessage msg = (NdefMessage) rawMsgs[0];
	}
	
    public class GeoUpdateHandler implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
        	System.out.println("Received new location: Lat: " + location.getLatitude() 
        						+ " Lon: " + location.getLongitude());
            mapController.animateTo( GeopointFactory(location.getLatitude(), location.getLongitude()));
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }

    private GeoPoint GeopointFactory(double latitude, double longitude) {
        int lat = (int) (latitude * 1E6);
        int lng = (int) (longitude * 1E6);
        return new GeoPoint(lat, lng);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.track_menu, menu);
    }
    
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.video:
                showVideo(info.id);
                return true;
            case R.id.stores:
                showStores(info.id);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    
    public void showStores(long id)
    {
    	System.out.println("showStores:  Value of trackId == " + id);
    }
    public void showVideo(long id)
    {
    	System.out.println("showVideo:  Value of trackId == " + id);    	
    }

}