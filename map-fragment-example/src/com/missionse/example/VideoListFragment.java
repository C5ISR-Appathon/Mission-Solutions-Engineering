package com.missionse.example;
import android.annotation.TargetApi;
import android.app.ListFragment;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class VideoListFragment extends ListFragment {
	
	SimpleCursorAdapter adapter;
	final Uri mediaSrc = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		Cursor cursor = adapter.getCursor();
		cursor.moveToPosition(position);
		
		String _id = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID));

		Uri playableUri
			= Uri.withAppendedPath(mediaSrc, _id);

		AndroidListMediaActivity mainActivity = (AndroidListMediaActivity)getActivity();
		mainActivity.sendUri();
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
        String[] from = {
        		MediaStore.MediaColumns.TITLE};
        int[] to = {
        		android.R.id.text1};

        Cursor cursor = getActivity().managedQuery(
        		mediaSrc, 
        		null, 
        		null, 
        		null, 
        		MediaStore.Audio.Media.TITLE);
        
        adapter = new SimpleCursorAdapter(getActivity(),
        		android.R.layout.simple_list_item_1, cursor, from, to);
        setListAdapter(adapter);
	}

}
