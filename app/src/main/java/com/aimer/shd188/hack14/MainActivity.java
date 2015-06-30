package com.aimer.shd188.hack14;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;


public class MainActivity extends Activity {

    private View mPortraitPosition;
    private LinearLayout mPortraitContent;
    private VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        mVideoView.post(new Runnable() {
            @Override
            public void run() {
                initVideoView();
            }
        });
    }

    private void init() {
        mPortraitContent = (LinearLayout) findViewById(R.id.main_portrait_content);
        mPortraitPosition = (View) findViewById(R.id.main_portrait_position);
        mVideoView = (VideoView) findViewById(R.id.main_videoView);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        setVideoViewPosition();
        super.onConfigurationChanged(newConfig);
    }

    private void setVideoViewPosition() {
        if (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            mPortraitContent.setVisibility(View.VISIBLE);
            int[] locationArray = new int[2];
            //videoView的位置
            mPortraitPosition.getLocationOnScreen(locationArray);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(mPortraitPosition.getWidth(), mPortraitPosition.getHeight());
            params.leftMargin = locationArray[0];
            params.topMargin = locationArray[1];

            mVideoView.setLayoutParams(params);
        } else {
            mPortraitContent.setVisibility(View.GONE);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            mVideoView.setLayoutParams(params);
        }
    }

    private void initVideoView() {
        mVideoView.setMediaController(new MediaController(this));
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bigbuck);
        mVideoView.setVideoURI(uri);

        setVideoViewPosition();
        mVideoView.start();
    }
}
