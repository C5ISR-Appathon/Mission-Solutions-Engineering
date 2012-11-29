package com.missionse.example;

import java.io.IOException;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.media.AudioManager;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class PlayerFragment extends Fragment 
	implements SurfaceHolder.Callback {

	Uri targetUri = null;
	
	MediaPlayer mediaPlayer;
	SurfaceView surfaceView;
	SurfaceHolder surfaceHolder;
	boolean pausing = false;
	
	//TextView mediaUri;
//	Button buttonPlayVideo, buttonPauseVideo;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.playerlayout, container, false);
//		mediaUri = (TextView)view.findViewById(R.id.mediauri);
//		buttonPlayVideo = (Button)view.findViewById(R.id.playvideoplayer);
//		buttonPauseVideo = (Button)view.findViewById(R.id.pausevideoplayer);
		surfaceView = (SurfaceView)view.findViewById(R.id.surfaceview);
		
		getActivity().getWindow().setFormat(PixelFormat.UNKNOWN);
		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(this);
		//surfaceHolder.setFixedSize(350, 300);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mediaPlayer = new MediaPlayer();
	

//		buttonPlayVideo.setOnClickListener(new Button.OnClickListener(){
//
//			@Override
//			public void onClick(View arg0) {
//				
//				if(targetUri != null){
//
//					pausing = false;
//
//					 if(mediaPlayer.isPlaying()){
//						 mediaPlayer.reset();	 
//					 }
//
//					 mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//					 mediaPlayer.setDisplay(surfaceHolder);
//
//					 try {
//						 mediaPlayer.setDataSource(getActivity().getApplicationContext(), targetUri);
//						 mediaPlayer.prepare();							 
//					 } catch (IllegalArgumentException e) {
//						 e.printStackTrace();
//						 Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
//					 } catch (IllegalStateException e) {
//						 e.printStackTrace();
//						 Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
//					 } catch (IOException e) {
//						 e.printStackTrace();
//						 Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
//					 }
//					 
//					 /*
//					  *  Handle aspect ratio
//					  */
//					 int surfaceView_Width = surfaceView.getWidth();
//					 int surfaceView_Height = surfaceView.getHeight();
//
//					 float video_Width = mediaPlayer.getVideoWidth();
//					 float video_Height = mediaPlayer.getVideoHeight();
//
//					 float ratio_width = surfaceView_Width/video_Width;
//					 float ratio_height = surfaceView_Height/video_Height;
//					 float aspectratio = video_Width/video_Height;
//
//					 LayoutParams layoutParams = surfaceView.getLayoutParams();
//					 
//					 if (ratio_width > ratio_height){
//						 layoutParams.width = (int) (surfaceView_Height * aspectratio);
//						 layoutParams.height = surfaceView_Height;
//					 }else{
//						 layoutParams.width = surfaceView_Width;
//						 layoutParams.height = (int) (surfaceView_Width / aspectratio);
//					 }
//
//					 surfaceView.setLayoutParams(layoutParams);
//					 /*
//					  * 
//					  */
//					 
//					 mediaPlayer.start();
//				}
//				
//			}});
//		
//		buttonPauseVideo.setOnClickListener(new Button.OnClickListener(){
//
//			@Override
//			public void onClick(View arg0) {
//				if(targetUri != null){
//					if(pausing){
//						pausing = false;
//						mediaPlayer.start();	
//					}
//					else{
//						pausing = true;
//						mediaPlayer.pause();	
//					}
//				}
//			}});
//		
		return view;
	}
	
	public void play() {
	
	if(targetUri != null){

		pausing = false;

		 if(mediaPlayer.isPlaying()){
			 mediaPlayer.reset();	 
		 }

		 mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		 mediaPlayer.setDisplay(surfaceHolder);

		 try {
			 mediaPlayer.setDataSource(getActivity().getApplicationContext(), targetUri);
			 mediaPlayer.prepare();							 
		 } catch (IllegalArgumentException e) {
			 e.printStackTrace();
			 Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
		 } catch (IllegalStateException e) {
			 e.printStackTrace();
			 Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
		 } catch (IOException e) {
			 e.printStackTrace();
			 Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
		 }
		 
		 /*
		  *  Handle aspect ratio
		  */
		 int surfaceView_Width = surfaceView.getWidth();
		 int surfaceView_Height = surfaceView.getHeight();

		 float video_Width = mediaPlayer.getVideoWidth();
		 float video_Height = mediaPlayer.getVideoHeight();

		 float ratio_width = surfaceView_Width/video_Width;
		 float ratio_height = surfaceView_Height/video_Height;
		 float aspectratio = video_Width/video_Height;

		 LayoutParams layoutParams = surfaceView.getLayoutParams();
		 
		 if (ratio_width > ratio_height){
			 layoutParams.width = (int) (surfaceView_Height * aspectratio);
			 layoutParams.height = surfaceView_Height;
		 }else{
			 layoutParams.width = surfaceView_Width;
			 layoutParams.height = (int) (surfaceView_Width / aspectratio);
		 }

		 surfaceView.setLayoutParams(layoutParams);
		 /*
		  * 
		  */
		 
		 mediaPlayer.start();
	}
	}
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mediaPlayer.release();
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void setTargetUri(Uri u){
		targetUri = u;
		play();
		//mediaUri.setText(u.toString());
	}

}
