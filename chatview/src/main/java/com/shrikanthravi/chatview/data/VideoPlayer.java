package com.shrikanthravi.chatview.data;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;

import java.io.IOException;

import static android.content.ContentValues.TAG;

/**
 * Created by shrikanthravi on 22/02/18.
 */
public class VideoPlayer extends TextureView implements TextureView.SurfaceTextureListener {

    private static String TAG = "VideoPlayer";

    public enum ScaleType {
        CENTER_CROP, TOP, BOTTOM
    }


    /**This flag determines that if current VideoPlayer object is first item of the list if it is first item of list*/
    boolean isFirstListItem;

    boolean isLoaded;
    boolean isMpPrepared;

    private float mVideoHeight;
    private float mVideoWidth;

    IVideoPreparedListener iVideoPreparedListener;

    Message video;
    String url;
    MediaPlayer mp;
    Surface surface;
    SurfaceTexture s;
    private ScaleType mScaleType;

    public interface IVideoPreparedListener {

        public void onVideoPrepared(Message video);
    }

    public VideoPlayer(Context context) {
        super(context);
    }

    public VideoPlayer(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public void loadVideo(String localPath, Message video) {

        this.url = localPath;
        this.video = video;
        isLoaded = true;

        if (this.isAvailable()) {
            prepareVideo(getSurfaceTexture());
        }

        setSurfaceTextureListener(this);
    }

    @Override
    public void onSurfaceTextureAvailable(final SurfaceTexture surface, int width, int height) {
        isMpPrepared = false;
        prepareVideo(surface);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {

        if(mp!=null)
        {
            mp.stop();
            mp.reset();
            mp.release();
            mp = null;
        }

        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
    }

    public void setScaleType(ScaleType scaleType) {
        mScaleType = scaleType;
    }

    private void updateTextureViewSize() {
        float viewWidth = getWidth();
        float viewHeight = getHeight();

        float scaleX = 1.0f;
        float scaleY = 1.0f;

        if (mVideoWidth > viewWidth && mVideoHeight > viewHeight) {
            scaleX = mVideoWidth / viewWidth;
            scaleY = mVideoHeight / viewHeight;
        } else if (mVideoWidth < viewWidth && mVideoHeight < viewHeight) {
            scaleY = viewWidth / mVideoWidth;
            scaleX = viewHeight / mVideoHeight;
        } else if (viewWidth > mVideoWidth) {
            scaleY = (viewWidth / mVideoWidth) / (viewHeight / mVideoHeight);
        } else if (viewHeight > mVideoHeight) {
            scaleX = (viewHeight / mVideoHeight) / (viewWidth / mVideoWidth);
        }

        // Calculate pivot points, in our case crop from center
        int pivotPointX;
        int pivotPointY;

        switch (mScaleType) {
            case TOP:
                pivotPointX = 0;
                pivotPointY = 0;
                break;
            case BOTTOM:
                pivotPointX = (int) (viewWidth);
                pivotPointY = (int) (viewHeight);
                break;
            case CENTER_CROP:
                pivotPointX = (int) (viewWidth / 2);
                pivotPointY = (int) (viewHeight / 2);
                break;
            default:
                pivotPointX = (int) (viewWidth / 2);
                pivotPointY = (int) (viewHeight / 2);
                break;
        }

        Matrix matrix = new Matrix();
        matrix.setScale(scaleX, scaleY, pivotPointX, pivotPointY);

        setTransform(matrix);
    }


    public void prepareVideo(SurfaceTexture t)
    {

        this.surface = new Surface(t);
        mp = new MediaPlayer();
        mp.setSurface(this.surface);

        try {
            mp.setDataSource(url);
            mp.prepareAsync();

            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mp) {
                    isMpPrepared = true;
                    mp.setLooping(true);
                    mp.setVolume(0,0);
                    mp.setOnVideoSizeChangedListener(
                            new MediaPlayer.OnVideoSizeChangedListener() {
                                @Override
                                public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                                    mVideoWidth = width;
                                    mVideoHeight = height;
                                    updateTextureViewSize();
                                }

                });
                    //startPlay();
                    changePlayState();
                    //iVideoPreparedListener.onVideoPrepared(video);
                    //adjustAspectRatio(VideoPlayer.this,mp.getVideoWidth(),mp.getVideoHeight());
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

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
    }

    public boolean startPlay()
    {
        if(mp!=null)
            if(!mp.isPlaying())
            {
                mp.start();
                return true;
            }

        return false;
    }

    public void pausePlay()
    {
        if(mp!=null)
            mp.pause();
    }

    public void stopPlay()
    {
        if(mp!=null)
            mp.stop();
    }

    public void changePlayState()
    {
        if(mp!=null)
        {
            if(mp.isPlaying())
                mp.pause();
            else
                mp.start();
        }

    }

    public void setOnVideoPreparedListener(IVideoPreparedListener iVideoPreparedListener) {
        this.iVideoPreparedListener = iVideoPreparedListener;
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
        Log.v(TAG, "video=" + videoWidth + "x" + videoHeight +
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

}
