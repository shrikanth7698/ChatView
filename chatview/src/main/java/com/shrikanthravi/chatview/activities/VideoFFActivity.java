package com.shrikanthravi.chatview.activities;

import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;

import com.shrikanthravi.chatview.R;


import java.io.IOException;

public class VideoFFActivity extends AppCompatActivity {

    TextureView textureView;
    static MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_ff);

        textureView = findViewById(R.id.textureView);
        mediaPlayer = new MediaPlayer();

        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
                prepareVideo(surfaceTexture);
                adjustAspectRatio(textureView,mediaPlayer.getVideoWidth(),mediaPlayer.getVideoHeight());
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {
                adjustAspectRatio(textureView,mediaPlayer.getVideoWidth(),mediaPlayer.getVideoHeight());
            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
                adjustAspectRatio(textureView,mediaPlayer.getVideoWidth(),mediaPlayer.getVideoHeight());
            }
        });
    }

    public void prepareVideo(SurfaceTexture t)
    {

        mediaPlayer.setSurface(new Surface(t));

        try {
            mediaPlayer.setDataSource(getIntent().getStringExtra("videoURI"));
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mp) {

                    mp.setLooping(true);
                    mediaPlayer.start();

                    //adjustAspectRatio(textureView,mp.getVideoWidth(),mp.getVideoHeight());
                    //iVideoPreparedListener.onVideoPrepared(video);
                }


            });
        } catch (IllegalArgumentException e1) {
            e1.printStackTrace();
        } catch (SecurityException e1) {
            e1.printStackTrace();
        } catch (IllegalStateException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        try {

        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

    }

    private void adjustAspectRatio(TextureView m_TextureView,int videoWidth, int videoHeight) {
        int viewWidth = m_TextureView.getWidth();
        int viewHeight = m_TextureView.getHeight();
        double aspectRatio = (double) videoHeight / videoWidth;

        int newWidth, newHeight;
        if (viewHeight > (int) (viewWidth * aspectRatio)) {
            // limited by narrow width; restrict height
            newWidth = viewWidth;
            newHeight = (int) (viewWidth * aspectRatio);
        } else {
            // limited by short height; restrict width
            newWidth = (int) (viewHeight / aspectRatio);
            newHeight = viewHeight;
        }
        int xoff = (viewWidth - newWidth) / 2;
        int yoff = (viewHeight - newHeight) / 2;
        Log.v("Video Player", "video=" + videoWidth + "x" + videoHeight +
                " view=" + viewWidth + "x" + viewHeight +
                " newView=" + newWidth + "x" + newHeight +
                " off=" + xoff + "," + yoff);

        Matrix txform = new Matrix();
        m_TextureView.getTransform(txform);
        txform.setScale((float) newWidth / viewWidth, (float) newHeight / viewHeight);
        //txform.postRotate(10);          // just for fun
        txform.postTranslate(xoff, yoff);
        m_TextureView.setTransform(txform);
    }

    @Override
    public void onBackPressed() {
        //To support reverse transitions when user clicks the device back button
        supportFinishAfterTransition();
        mediaPlayer.reset();
    }

    @Override
    protected void onStop() {
        super.onStop();

        mediaPlayer.reset();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mediaPlayer.reset();
        finish();

        mediaPlayer.reset();
    }
}
