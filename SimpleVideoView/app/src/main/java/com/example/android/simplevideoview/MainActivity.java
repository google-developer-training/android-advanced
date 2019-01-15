/*
 * Copyright 2018, Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.simplevideoview;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    // Use this string for the first part of the practical (load local media
    // from the raw directory).
    // private static final String VIDEO_SAMPLE = "tacoma_narrows";

    // Use this string for part 2 (load media from the internet).
    //private static final String VIDEO_SAMPLE =
    //        "https://developers.google.com/training/images/tacoma_narrows.mp4";

    // Use this string for part 3 (load media from sdcard).
    private static final String VIDEO_SAMPLE =
            "file://" + Environment.getExternalStorageDirectory().getPath()+"/Movies/01.mp4";

    private static final String VIDEO_SAMPLE2 =
            "file://" + Environment.getExternalStorageDirectory().getPath()+"/Movies/02.mp4";

    private VideoView mVideoView;
    private TextView mBufferingTextView;

    // Current playback position (in milliseconds).
    private int mCurrentPosition = 0;

    // Tag for the instance state bundle.
    private static final String PLAYBACK_TIME = "play_time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVideoView = findViewById(R.id.videoview);
        mBufferingTextView = findViewById(R.id.buffering_textview);

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
        }

        // Set up the media controller widget and attach it to the video view.
//        MediaController controller = new MediaController(this);
//        controller.setMediaPlayer(mVideoView);
//        mVideoView.setMediaController(controller);
    }


    @Override
    protected void onStart() {
        super.onStart();

        // Load the media each time onStart() is called.
        initializePlayer(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideSystemUi();
    }

    private void hideSystemUi(){
        mVideoView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );
    }

    @Override
    protected void onPause() {
        super.onPause();

        // In Android versions less than N (7.0, API 24), onPause() is the
        // end of the visual lifecycle of the app.  Pausing the video here
        // prevents the sound from continuing to play even after the app
        // disappears.
        //
        // This is not a problem for more recent versions of Android because
        // onStop() is now the end of the visual lifecycle, and that is where
        // most of the app teardown should take place.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mVideoView.pause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Media playback takes a lot of resources, so everything should be
        // stopped and released at this time.
        releasePlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mVideoView.suspend();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current playback position (in milliseconds) to the
        // instance state bundle.
        outState.putInt(PLAYBACK_TIME, mVideoView.getCurrentPosition());
        mCurrentPosition = mVideoView.getCurrentPosition();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 0:
                if(grantResults.length > 0 && grantResults[0] ==PackageManager.PERMISSION_GRANTED){
                    initializePlayer(false);
                }else{
                    Toast.makeText(this, "denied", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    private boolean mu = false;
    private void initializePlayer(boolean swith) {
        // Show the "Buffering..." message while the video loads.
        mBufferingTextView.setVisibility(VideoView.VISIBLE);

        // Buffer and decode the video sample.

        Uri videoUri = null;
        if(mu) {
            videoUri = getMedia(VIDEO_SAMPLE);
        }else {
            videoUri = getMedia(VIDEO_SAMPLE2);
        }
        mu = !mu;
        Log.d("zhanghao", "initializePlayer: " + videoUri);

        if (Util.maybeRequestReadExternalStoragePermission(this, videoUri)) {
            // The player will be reinitialized if the permission is granted.
            Log.d("zhanghao", "initializePlayer: " );
            return;
        }

        mVideoView.setVideoURI(videoUri);

        // Listener for onPrepared() event (runs after the media is prepared).
        mVideoView.setOnPreparedListener(
                new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {

                // Hide buffering message.
                mBufferingTextView.setVisibility(VideoView.INVISIBLE);

                // Restore saved position, if available.
                if (mCurrentPosition > 0) {
                    mVideoView.seekTo(mCurrentPosition);
                } else {
                    // Skipping to 1 shows the first frame of the video.
                    mVideoView.seekTo(1);
                }

                // Start playing!
                mVideoView.start();
            }
        });

        // Listener for onCompletion() event (runs after media has finished
        // playing).
        mVideoView.setOnCompletionListener(
                new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Toast.makeText(MainActivity.this,
                        R.string.toast_message,
                        Toast.LENGTH_SHORT).show();

                // Return the video position to the start.
                mVideoView.seekTo(0);
            }
        });

        mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {

                return true;
            }
        });
    }


    // Release all media-related resources. In a more complicated app this
    // might involve unregistering listeners or releasing audio focus.
    private void releasePlayer() {
        mVideoView.stopPlayback();
    }

    // Get a Uri for the media sample regardless of whether that sample is
    // embedded in the app resources or available on the internet.
    private Uri getMedia(String mediaName) {
        if (URLUtil.isValidUrl(mediaName)) {
            // Media name is an external URL.
            return Uri.parse(mediaName);
        } else {
            // Media name is a raw resource embedded in the app.
            return Uri.parse("android.resource://" + getPackageName() +
                    "/raw/" + mediaName);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_VOLUME_UP:
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        switch (keyCode){
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (mVideoView.isPlaying()){
                    mVideoView.stopPlayback();
                }
                initializePlayer(true);
                return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}
