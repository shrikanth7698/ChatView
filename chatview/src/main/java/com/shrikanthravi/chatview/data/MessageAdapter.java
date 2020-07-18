package com.shrikanthravi.chatview.data;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.lopei.collageview.CollageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.ohoussein.playpause.PlayPauseView;
import com.shrikanthravi.chatview.R;
import com.shrikanthravi.chatview.activities.ImageFFActivity;
import com.shrikanthravi.chatview.activities.VideoFFActivity;
import com.shrikanthravi.chatview.utils.FontChanger;

import com.silencedut.expandablelayout.ExpandableLayout;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


import static android.content.ContentValues.TAG;

/**
 * Created by shrikanthravi on 16/02/18.
 */


public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Message> messageList;
    private List<Message> filterList;
    Context context;
    MessageFilter filter;
    ImageLoader imageLoader;
    Typeface typeface;

    public static MediaPlayer mediaPlayer;

    String playingposition;
    //onCompletionListener method
    MediaPlayer.OnCompletionListener mCompletionListener;
    protected boolean showLeftBubbleIcon=true;
    protected boolean showRightBubbleIcon=true;
    protected boolean showSenderName=true;

    private int leftBubbleLayoutColor = R.color.colorAccent2;
    private int rightBubbleLayoutColor = R.color.colorAccent1;
    private int leftBubbleTextColor = android.R.color.black;
    private int rightBubbleTextColor = android.R.color.white;
    private int timeTextColor = android.R.color.tab_indicator_text;
    private int senderNameTextColor = android.R.color.tab_indicator_text;
    private float textSize = 20;

    public MessageAdapter(List<Message> verticalList, Context context,RecyclerView recyclerView) {

        this.messageList = verticalList;
        this.context = context;
        this.filterList = verticalList;
        filter = new MessageFilter(verticalList,this);
        imageLoader = ImageLoader.getInstance();
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/product_san_regular.ttf");
        mCompletionListener = new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
                mediaPlayer = null;
            }
        };

    }

    @Override
    public int getItemViewType(int position) {


        int type=0;
        switch (messageList.get(position).getMessageType()) {
            case LeftSimpleMessage: {
                type = 1;
                break;
            }
            case RightSimpleImage: {
                type = 2;
                break;
            }
            case LeftSingleImage: {
                type = 3;
                break;
            }
            case RightSingleImage: {
                type = 4;
                break;
            }
            case LeftMultipleImages: {
                type = 5;
                break;
            }
            case RightMultipleImages: {
                type = 6;
                break;
            }
            case LeftVideo: {
                type = 7;
                break;
            }
            case RightVideo: {
                type = 8;
                break;
            }
            case LeftAudio: {
                type = 9;
                break;
            }
            case RightAudio: {
                type = 10;
                break;
            }
        }
        if(type==0){
            throw new RuntimeException("Set Message Type ( Message Type is Null )");
        }
        else {
            return type;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;


        if(viewType==1){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.left_text_layout, parent, false);
            viewHolder = new LeftTextViewHolder(view);
        }
        else{
            if(viewType==2){
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.right_text_layout, parent, false);
                viewHolder = new RightTextViewHolder(view);
            }
            else{
                if(viewType==3){
                    View view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.left_image_layout, parent, false);
                    viewHolder = new LeftImageViewHolder(view);
                }
                else{
                    if(viewType==4){
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.right_image_layout, parent, false);
                        viewHolder = new RightImageViewHolder(view);
                    }
                    else{
                        if(viewType==5){
                            View view = LayoutInflater.from(parent.getContext())
                                    .inflate(R.layout.left_images_layout, parent, false);
                            viewHolder = new LeftImagesViewHolder(view);
                        }
                        else{
                            if(viewType==6) {
                                View view = LayoutInflater.from(parent.getContext())
                                        .inflate(R.layout.right_images_layout, parent, false);
                                viewHolder = new RightImagesViewHolder(view);
                            }
                            else{
                                if(viewType==20) {
                                    View view = LayoutInflater.from(parent.getContext())
                                            .inflate(R.layout.left_typing_layout, parent, false);
                                    viewHolder = new LeftTypingViewHolder(view);
                                }
                                else{
                                    if(viewType==7) {
                                        View view = LayoutInflater.from(parent.getContext())
                                                .inflate(R.layout.left_video_layout, parent, false);

                                        viewHolder = new LeftVideoViewHolder(view);

                                    }
                                    else{
                                        if(viewType==8) {

                                            View view = LayoutInflater.from(parent.getContext())
                                                    .inflate(R.layout.right_video_layout, parent, false);
                                            viewHolder = new RightVideoViewHolder(view);
                                        }
                                        else {
                                            if(viewType==9){
                                                View view = LayoutInflater.from(parent.getContext())
                                                        .inflate(R.layout.left_audio_layout, parent, false);
                                                viewHolder = new LeftAudioViewHolder(view);
                                            }
                                            else{
                                                View view = LayoutInflater.from(parent.getContext())
                                                        .inflate(R.layout.right_audio_layout, parent, false);
                                                viewHolder = new RightAudioViewHolder(view);
                                            }

                                        }



                                    }
                                }

                            }
                        }
                    }
                }
            }

        }


        if(viewHolder==null){
            throw new RuntimeException("View Holder is null");
        }
        return viewHolder;
    }



    protected class LeftTextViewHolder extends RecyclerView.ViewHolder {

        public TextView leftTV,leftTimeTV,senderNameTV;
        public ExpandableLayout leftEL;
        public ImageView lefttMessageStatusIV,leftBubbleIconIV;
        public CardView leftBubbleIconCV;

        public LeftTextViewHolder(View view) {
            super(view);

            leftTV = view.findViewById(R.id.leftTV);
            leftTimeTV = view.findViewById(R.id.leftTimeTV);
            leftEL = view.findViewById(R.id.leftEL);
            senderNameTV = view.findViewById(R.id.senderNameTV);
            leftBubbleIconIV = view.findViewById(R.id.leftBubbleIconIV);
            leftBubbleIconCV = view.findViewById(R.id.leftBubbleIconCV);
            setBackgroundColor(leftBubbleLayoutColor);
            setTextColor(leftBubbleTextColor);
            setTimeTextColor(timeTextColor);
            setSenderNameTextColor(senderNameTextColor);
            showSenderName(showSenderName);
            showLeftBubbleIcon(showLeftBubbleIcon);
            setTextSize(textSize);

            FontChanger fontChanger = new FontChanger(typeface);
            fontChanger.replaceFonts((ViewGroup)view);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    int pos = getLayoutPosition();

                    return true;
                }
            });
        }

        public void setBackgroundColor(int color){
            Drawable backgroundDrawable = DrawableCompat.wrap(leftTV.getBackground()).mutate();
            DrawableCompat.setTint(backgroundDrawable,color);
        }

        public void setTextColor(int color){
            leftTV.setTextColor(color);
        }

        public void setTimeTextColor(int color){
            leftTimeTV.setTextColor(color);
        }

        public void setSenderNameTextColor(int color){
            senderNameTV.setTextColor(color);
        }

        public void showSenderName(boolean b){
            if(b){
                senderNameTV.setVisibility(View.VISIBLE);
            }
            else{
                senderNameTV.setVisibility(View.GONE);
            }
        }

        public void showLeftBubbleIcon(boolean b){
            if(b){
                leftBubbleIconCV.setVisibility(View.VISIBLE);
            }
            else{
                leftBubbleIconCV.setVisibility(View.GONE);
            }
        }

        public void setTextSize(float size){
            leftTV.setTextSize(size);
        }


    }
    protected class RightTextViewHolder extends RecyclerView.ViewHolder {

        public TextView rightTV,rightTimeTV,senderNameTV;
        public ImageView rightMessageStatusIV,rightBubbleIconIV;
        public ExpandableLayout rightEL;
        public CardView rightBubbleIconCV;

        public RightTextViewHolder(View view) {
            super(view);

            rightTV = view.findViewById(R.id.rightTV);
            rightTimeTV = view.findViewById(R.id.rightTimeTV);
            rightEL = view.findViewById(R.id.rightEL);
            senderNameTV = view.findViewById(R.id.senderNameTV);
            rightBubbleIconCV = view.findViewById(R.id.rightBubbleIconCV);
            rightBubbleIconIV = view.findViewById(R.id.rightBubbleIconIV);
            setBackgroundColor(rightBubbleLayoutColor);
            setTextColor(rightBubbleTextColor);
            setTimeTextColor(timeTextColor);
            setSenderNameTextColor(senderNameTextColor);
            showSenderName(showSenderName);
            showRightBubbleIcon(showRightBubbleIcon);
            setTextSize(textSize);
            FontChanger fontChanger = new FontChanger(typeface);
            fontChanger.replaceFonts((ViewGroup)view);

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    int pos = getLayoutPosition();

                    return true;
                }
            });
        }

        public void setBackgroundColor(int color){
            Drawable backgroundDrawable = DrawableCompat.wrap(rightTV.getBackground()).mutate();
            DrawableCompat.setTint(backgroundDrawable,color);
        }

        public void setTextColor(int color){
            rightTV.setTextColor(color);
        }

        public void setTimeTextColor(int color){
            rightTimeTV.setTextColor(color);
        }

        public void setSenderNameTextColor(int color){
            senderNameTV.setTextColor(color);
        }
        public void showSenderName(boolean b){
            if(b){
                senderNameTV.setVisibility(View.VISIBLE);
            }
            else{
                senderNameTV.setVisibility(View.GONE);
            }
        }

        public void showRightBubbleIcon(boolean b){
            if(b){
                rightBubbleIconCV.setVisibility(View.VISIBLE);
            }
            else{
                rightBubbleIconCV.setVisibility(View.GONE);
            }
        }

        public void setTextSize(float size){
            rightTV.setTextSize(size);
        }
    }

    protected class LeftImageViewHolder extends RecyclerView.ViewHolder {

        public TextView leftTimeTV,senderNameTV;
        public ExpandableLayout leftEL;
        public ImageView lefttMessageStatusIV,leftBubbleIconIV;
        public CardView leftBubbleIconCV;
        public CardView leftIVCV;
        public ImageView leftIV;

        public LeftImageViewHolder(View view) {
            super(view);


            leftTimeTV = view.findViewById(R.id.leftTimeTV);
            leftEL = view.findViewById(R.id.leftEL);
            leftIV = view.findViewById(R.id.leftIV);
            leftIVCV = view.findViewById(R.id.leftIVCV);
            senderNameTV = view.findViewById(R.id.senderNameTV);
            leftBubbleIconIV = view.findViewById(R.id.leftBubbleIconIV);
            leftBubbleIconCV = view.findViewById(R.id.leftBubbleIconCV);

            setBackgroundColor(leftBubbleLayoutColor);
            setSenderNameTextColor(senderNameTextColor);
            showSenderName(showSenderName);
            showLeftBubbleIcon(showLeftBubbleIcon);
            FontChanger fontChanger = new FontChanger(typeface);
            fontChanger.replaceFonts((ViewGroup)view);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    int pos = getLayoutPosition();

                    return true;
                }
            });
        }

        public void setBackgroundColor(int color){
            Drawable backgroundDrawable = DrawableCompat.wrap(leftIV.getBackground()).mutate();
            DrawableCompat.setTint(backgroundDrawable,color);
        }

        public void setSenderNameTextColor(int color){
            senderNameTV.setTextColor(color);
        }

        public void showSenderName(boolean b){
            if(b){
                senderNameTV.setVisibility(View.VISIBLE);
            }
            else{
                senderNameTV.setVisibility(View.GONE);
            }
        }

        public void showLeftBubbleIcon(boolean b){
            if(b){
                leftBubbleIconCV.setVisibility(View.VISIBLE);
            }
            else{
                leftBubbleIconCV.setVisibility(View.GONE);
            }
        }
    }

    protected class RightImageViewHolder extends RecyclerView.ViewHolder {

        public TextView rightTV,rightTimeTV,senderNameTV;
        public ExpandableLayout rightEL;
        public ImageView rightMessageStatusIV,rightBubbleIconIV;
        public CardView rightBubbleIconCV;
        public CardView rightIVCV;
        public ImageView rightIV;

        public RightImageViewHolder(View view) {
            super(view);


            rightTimeTV = view.findViewById(R.id.rightTimeTV);
            rightEL = view.findViewById(R.id.rightEL);
            rightIV = view.findViewById(R.id.rightIV);
            rightIVCV = view.findViewById(R.id.rightIVCV);
            senderNameTV = view.findViewById(R.id.senderNameTV);
            rightBubbleIconCV = view.findViewById(R.id.rightBubbleIconCV);
            rightBubbleIconIV = view.findViewById(R.id.rightBubbleIconIV);
            FontChanger fontChanger = new FontChanger(typeface);
            fontChanger.replaceFonts((ViewGroup)view);
            setBackgroundColor(rightBubbleLayoutColor);
            setSenderNameTextColor(senderNameTextColor);
            showSenderName(showSenderName);
            showRightBubbleIcon(showRightBubbleIcon);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    int pos = getLayoutPosition();

                    return true;
                }
            });
        }

        public void setBackgroundColor(int color){
            Drawable backgroundDrawable = DrawableCompat.wrap(rightIV.getBackground()).mutate();
            DrawableCompat.setTint(backgroundDrawable,color);
        }

        public void setSenderNameTextColor(int color){
            senderNameTV.setTextColor(color);
        }
        public void showSenderName(boolean b){
            if(b){
                senderNameTV.setVisibility(View.VISIBLE);
            }
            else{
                senderNameTV.setVisibility(View.GONE);
            }
        }
        public void showRightBubbleIcon(boolean b){
            if(b){
                rightBubbleIconCV.setVisibility(View.VISIBLE);
            }
            else{
                rightBubbleIconCV.setVisibility(View.GONE);
            }
        }
    }

    protected class LeftImagesViewHolder extends RecyclerView.ViewHolder {

        public TextView leftTimeTV,senderNameTV;
        public ExpandableLayout leftEL;
        public ImageView lefttMessageStatusIV,leftBubbleIconIV;
        public CardView leftBubbleIconCV;
        public CollageView leftCollageView;

        public LeftImagesViewHolder(View view) {
            super(view);

            leftTimeTV = view.findViewById(R.id.leftTimeTV);
            leftEL = view.findViewById(R.id.leftEL);
            leftCollageView = view.findViewById(R.id.leftCollageView);
            senderNameTV = view.findViewById(R.id.senderNameTV);
            leftBubbleIconIV = view.findViewById(R.id.leftBubbleIconIV);
            leftBubbleIconCV = view.findViewById(R.id.leftBubbleIconCV);
            setSenderNameTextColor(senderNameTextColor);
            showSenderName(showSenderName);
            showLeftBubbleIcon(showLeftBubbleIcon);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    int pos = getLayoutPosition();

                    return true;
                }
            });
        }

        public void setSenderNameTextColor(int color){
            senderNameTV.setTextColor(color);
        }

        public void showSenderName(boolean b){
            if(b){
                senderNameTV.setVisibility(View.VISIBLE);
            }
            else{
                senderNameTV.setVisibility(View.GONE);
            }
        }

        public void showLeftBubbleIcon(boolean b){
            if(b){
                leftBubbleIconCV.setVisibility(View.VISIBLE);
            }
            else{
                leftBubbleIconCV.setVisibility(View.GONE);
            }
        }
    }

    protected class RightImagesViewHolder extends RecyclerView.ViewHolder {

        public TextView rightTimeTV,senderNameTV;
        public ExpandableLayout rightEL;
        public ImageView rightMessageStatusIV,rightBubbleIconIV;
        public CardView rightBubbleIconCV;
        public CollageView rightCollageView,leftCollageView;

        public RightImagesViewHolder(View view) {
            super(view);

            rightTimeTV = view.findViewById(R.id.rightTimeTV);
            rightEL = view.findViewById(R.id.rightEL);
            rightCollageView = view.findViewById(R.id.rightCollageView);
            leftCollageView = view.findViewById(R.id.leftCollageView);
            senderNameTV = view.findViewById(R.id.senderNameTV);
            rightBubbleIconCV = view.findViewById(R.id.rightBubbleIconCV);
            rightBubbleIconIV = view.findViewById(R.id.rightBubbleIconIV);
            setSenderNameTextColor(senderNameTextColor);
            showSenderName(showSenderName);
            showRightBubbleIcon(showRightBubbleIcon);
            FontChanger fontChanger = new FontChanger(typeface);
            fontChanger.replaceFonts((ViewGroup)view);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    int pos = getLayoutPosition();

                    return true;
                }
            });
        }

        public void setSenderNameTextColor(int color){
            senderNameTV.setTextColor(color);
        }

        public void showSenderName(boolean b){
            if(b){
                senderNameTV.setVisibility(View.VISIBLE);
            }
            else{
                senderNameTV.setVisibility(View.GONE);
            }
        }
        public void showRightBubbleIcon(boolean b){
            if(b){
                rightBubbleIconCV.setVisibility(View.VISIBLE);
            }
            else{
                rightBubbleIconCV.setVisibility(View.GONE);
            }
        }
    }

    protected class LeftVideoViewHolder extends RecyclerView.ViewHolder {

        public TextView leftTimeTV,senderNameTV;
        public ExpandableLayout leftEL;
        public ImageView lefttMessageStatusIV,leftBubbleIconIV;
        public CardView leftBubbleIconCV;
        public CardView leftIVCV;
        public ImageView leftIV;
        public LinearLayout videoLL;

        public LeftVideoViewHolder(View view) {
            super(view);


            leftIVCV = view.findViewById(R.id.leftIVCV);
            leftTimeTV = view.findViewById(R.id.leftTimeTV);
            leftEL = view.findViewById(R.id.leftEL);
            senderNameTV = view.findViewById(R.id.senderNameTV);
            leftBubbleIconIV = view.findViewById(R.id.leftBubbleIconIV);
            leftBubbleIconCV = view.findViewById(R.id.leftBubbleIconCV);
            videoLL = view.findViewById(R.id.videoLL);

            setBackgroundColor(leftBubbleLayoutColor);
            setSenderNameTextColor(senderNameTextColor);
            showSenderName(showSenderName);
            showLeftBubbleIcon(showLeftBubbleIcon);
            FontChanger fontChanger = new FontChanger(typeface);
            fontChanger.replaceFonts((ViewGroup)view);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    int pos = getLayoutPosition();

                    return true;
                }
            });
        }

        public void setBackgroundColor(int color){
            leftIVCV.setCardBackgroundColor(color);
        }



        public void setSenderNameTextColor(int color){
            senderNameTV.setTextColor(color);
        }

        public void showSenderName(boolean b){
            if(b){
                senderNameTV.setVisibility(View.VISIBLE);
            }
            else{
                senderNameTV.setVisibility(View.GONE);
            }
        }

        public void showLeftBubbleIcon(boolean b){
            if(b){
                leftBubbleIconCV.setVisibility(View.VISIBLE);
            }
            else{
                leftBubbleIconCV.setVisibility(View.GONE);
            }
        }
    }

    protected class RightVideoViewHolder extends RecyclerView.ViewHolder {

        public TextView rightTimeTV,senderNameTV;
        public ExpandableLayout rightEL;
        public ImageView rightMessageStatusIV,rightBubbleIconIV;
        public CardView rightBubbleIconCV,rightIVCV;
        public LinearLayout videoLL;

        public RightVideoViewHolder(View view) {
            super(view);

            rightTimeTV = view.findViewById(R.id.rightTimeTV);
            rightEL = view.findViewById(R.id.rightEL);
            rightIVCV = view.findViewById(R.id.rightIVCV);
            senderNameTV = view.findViewById(R.id.senderNameTV);
            rightBubbleIconCV = view.findViewById(R.id.rightBubbleIconCV);
            rightBubbleIconIV = view.findViewById(R.id.rightBubbleIconIV);
            videoLL = view.findViewById(R.id.videoLL);

            setBackgroundColor(rightBubbleLayoutColor);
            setSenderNameTextColor(senderNameTextColor);
            showSenderName(showSenderName);
            showRightBubbleIcon(showRightBubbleIcon);
            FontChanger fontChanger = new FontChanger(typeface);
            fontChanger.replaceFonts((ViewGroup)view);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    int pos = getLayoutPosition();

                    return true;
                }
            });
        }
        public void setBackgroundColor(int color){
            rightIVCV.setCardBackgroundColor(color);
        }

        public void setSenderNameTextColor(int color){
            senderNameTV.setTextColor(color);
        }

        public void showSenderName(boolean b){
            if(b){
                senderNameTV.setVisibility(View.VISIBLE);
            }
            else{
                senderNameTV.setVisibility(View.GONE);
            }
        }
        public void showRightBubbleIcon(boolean b){
            if(b){
                rightBubbleIconCV.setVisibility(View.VISIBLE);
            }
            else{
                rightBubbleIconCV.setVisibility(View.GONE);
            }
        }
    }

    protected class LeftAudioViewHolder extends RecyclerView.ViewHolder {

        public TextView leftTimeTV,senderNameTV;
        public ExpandableLayout leftEL;
        public ImageView leftBubbleIconIV;
        public CardView leftBubbleIconCV;
        public SeekBar audioSeekbar;
        public PlayPauseView playPauseView;
        public Message message;
        public android.os.Handler handler;

        public LeftAudioViewHolder(View view) {
            super(view);

            audioSeekbar = view.findViewById(R.id.audioSeekbar);
            playPauseView = view.findViewById(R.id.play_pause_view);
            leftTimeTV = view.findViewById(R.id.leftTimeTV);
            leftEL = view.findViewById(R.id.leftEL);

            senderNameTV = view.findViewById(R.id.senderNameTV);
            leftBubbleIconIV = view.findViewById(R.id.leftBubbleIconIV);
            leftBubbleIconCV = view.findViewById(R.id.leftBubbleIconCV);
            setBackgroundColor(leftBubbleLayoutColor);
            setSeekBarLineColor(leftBubbleTextColor);
            setSeekBarThumbColor(rightBubbleLayoutColor);
            setTimeTextColor(timeTextColor);
            setSenderNameTextColor(senderNameTextColor);
            showSenderName(showSenderName);
            showLeftBubbleIcon(showLeftBubbleIcon);
            handler=new android.os.Handler();
            audioSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    if(playingposition==message.getAudioUri().toString()) {
                        mediaPlayer.seekTo(seekBar.getProgress());
                    }
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    if(playingposition==message.getAudioUri().toString()) {
                        mediaPlayer.seekTo(seekBar.getProgress());
                    }
                }
            });
            ((Activity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (message != null) {
                        if (playingposition == message.getAudioUri().toString()) {
                            if (mediaPlayer != null) {

                                if (mediaPlayer.isPlaying()) {

                                    audioSeekbar.setProgress(mediaPlayer.getCurrentPosition());
                                    if (playPauseView.isPlay()) {
                                        playPauseView.change(false);
                                    }
                                } else {
                                    playPauseView.change(true);
                                }
                            } else {
                                playPauseView.change(true);
                            }

                        } else {

                            audioSeekbar.setProgress(0);
                            playPauseView.change(true);
                            playPauseView.change(true);
                        }
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            playPauseView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {

                        if(playingposition==message.getAudioUri().toString()) {
                            mediaPlayer.stop();
                            mediaPlayer.reset();
                            mediaPlayer.release();
                            mediaPlayer = null;
                            playPauseView.change(true);
                        }

                        else {

                            mediaPlayer.stop();
                            mediaPlayer.reset();
                            mediaPlayer.release();
                            mediaPlayer = null;
                            mediaPlayer = MediaPlayer.create(v.getContext(), message.getAudioUri());
                            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mediaPlayer) {

                                    mediaPlayer.start();
                                    playingposition=message.getAudioUri().toString();
                                    audioSeekbar.setMax(mediaPlayer.getDuration());
                                    playPauseView.change(false);
                                }
                            });

                        }
                    }
                    else{

                        mediaPlayer = MediaPlayer.create(v.getContext(), message.getAudioUri());
                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mediaPlayer) {

                                mediaPlayer.start();
                                playingposition=message.getAudioUri().toString();
                                audioSeekbar.setMax(mediaPlayer.getDuration());
                                playPauseView.change(false);
                            }
                        });
                    }



                }
            });

            FontChanger fontChanger = new FontChanger(typeface);
            fontChanger.replaceFonts((ViewGroup)view);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    int pos = getLayoutPosition();

                    return true;
                }
            });
        }

        public Message getMessage() {
            return message;
        }

        public void setMessage(final Message message) {
            this.message = message;

        }

        public void setBackgroundColor(int color){
            Drawable backgroundDrawable = DrawableCompat.wrap(audioSeekbar.getBackground()).mutate();
            DrawableCompat.setTint(backgroundDrawable,color);
        }

        public void setSeekBarLineColor(int color){
            audioSeekbar.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.MULTIPLY);

        }

        public void setSeekBarThumbColor(int color){
            Drawable backgroundDrawable1 = DrawableCompat.wrap(audioSeekbar.getThumb()).mutate();
            DrawableCompat.setTint(backgroundDrawable1,color);
        }


        public void setTimeTextColor(int color){
            leftTimeTV.setTextColor(color);
        }

        public void setSenderNameTextColor(int color){
            senderNameTV.setTextColor(color);
        }

        public void showSenderName(boolean b){
            if(b){
                senderNameTV.setVisibility(View.VISIBLE);
            }
            else{
                senderNameTV.setVisibility(View.GONE);
            }
        }

        public void showLeftBubbleIcon(boolean b){
            if(b){
                leftBubbleIconCV.setVisibility(View.VISIBLE);
            }
            else{
                leftBubbleIconCV.setVisibility(View.GONE);
            }
        }


    }

    protected class RightAudioViewHolder extends RecyclerView.ViewHolder {

        public TextView rightTimeTV,senderNameTV;
        public ImageView rightMessageStatusIV,rightBubbleIconIV;
        public ExpandableLayout rightEL;
        public CardView rightBubbleIconCV;
        public Message message;
        public SeekBar audioSeekbar;
        public PlayPauseView playPauseView;
        public android.os.Handler handler;

        public RightAudioViewHolder(View view) {
            super(view);


            audioSeekbar = view.findViewById(R.id.audioSeekbar);
            playPauseView = view.findViewById(R.id.play_pause_view);
            rightTimeTV = view.findViewById(R.id.rightTimeTV);
            rightEL = view.findViewById(R.id.rightEL);
            senderNameTV = view.findViewById(R.id.senderNameTV);
            rightBubbleIconCV = view.findViewById(R.id.rightBubbleIconCV);
            rightBubbleIconIV = view.findViewById(R.id.rightBubbleIconIV);
            setBackgroundColor(rightBubbleLayoutColor);
            setSeekBarLineColor(rightBubbleTextColor);
            setSeekBarThumbColor(leftBubbleLayoutColor);
            setTimeTextColor(timeTextColor);
            setSenderNameTextColor(senderNameTextColor);
            showSenderName(showSenderName);
            showRightBubbleIcon(showRightBubbleIcon);
            handler=new android.os.Handler();
            audioSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    if(mediaPlayer!=null){
                        if(playingposition==message.getAudioUri().toString()) {
                            mediaPlayer.seekTo(seekBar.getProgress());
                        }
                    }
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    if(mediaPlayer!=null){
                        if(playingposition==message.getAudioUri().toString()) {
                            mediaPlayer.seekTo(seekBar.getProgress());
                        }
                    }
                }
            });
            ((Activity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (message != null) {
                        if (playingposition == message.getAudioUri().toString()) {
                            if (mediaPlayer != null) {
                                if (mediaPlayer.isPlaying()) {

                                    audioSeekbar.setProgress(mediaPlayer.getCurrentPosition());
                                    if (playPauseView.isPlay()) {
                                        playPauseView.change(false);
                                    }
                                } else {
                                    playPauseView.change(true);
                                }
                            } else {
                                playPauseView.change(true);
                            }

                        } else {

                            audioSeekbar.setProgress(0);
                            playPauseView.change(true);
                            playPauseView.change(true);
                        }
                    }
                    handler.postDelayed(this, 1000);

                }
            });

            playPauseView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {

                        if(playingposition==message.getAudioUri().toString()) {
                            mediaPlayer.stop();
                            mediaPlayer.reset();
                            mediaPlayer.release();
                            mediaPlayer = null;
                            playPauseView.change(true);
                        }
                        else {

                            mediaPlayer.stop();
                            mediaPlayer.reset();
                            mediaPlayer.release();
                            mediaPlayer = null;
                            mediaPlayer = MediaPlayer.create(v.getContext(), message.getAudioUri());
                            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mediaPlayer) {

                                    mediaPlayer.start();
                                    playingposition=message.getAudioUri().toString();
                                    audioSeekbar.setMax(mediaPlayer.getDuration());
                                    playPauseView.change(false);
                                }
                            });

                        }
                    }
                    else{

                        mediaPlayer = MediaPlayer.create(v.getContext(), message.getAudioUri());
                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mediaPlayer) {

                                mediaPlayer.start();
                                playingposition=message.getAudioUri().toString();
                                audioSeekbar.setMax(mediaPlayer.getDuration());
                                playPauseView.change(false);
                            }
                        });
                    }





                }
            });
            FontChanger fontChanger = new FontChanger(typeface);
            fontChanger.replaceFonts((ViewGroup)view);

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    int pos = getLayoutPosition();

                    return true;
                }
            });
        }

        public Message getMessage() {
            return message;
        }

        public void setMessage(final Message message) {
            this.message = message;

        }

        public void setBackgroundColor(int color){
            Drawable backgroundDrawable = DrawableCompat.wrap(audioSeekbar.getBackground()).mutate();
            DrawableCompat.setTint(backgroundDrawable,color);
        }

        public void setSeekBarLineColor(int color){
            audioSeekbar.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.MULTIPLY);

        }
        public void setSeekBarThumbColor(int color){
            Drawable backgroundDrawable1 = DrawableCompat.wrap(audioSeekbar.getThumb()).mutate();
            DrawableCompat.setTint(backgroundDrawable1,color);
        }

        public void setTimeTextColor(int color){
            rightTimeTV.setTextColor(color);
        }

        public void setSenderNameTextColor(int color){
            senderNameTV.setTextColor(color);
        }
        public void showSenderName(boolean b){
            if(b){
                senderNameTV.setVisibility(View.VISIBLE);
            }
            else{
                senderNameTV.setVisibility(View.GONE);
            }
        }

        public void showRightBubbleIcon(boolean b){
            if(b){
                rightBubbleIconCV.setVisibility(View.VISIBLE);
            }
            else{
                rightBubbleIconCV.setVisibility(View.GONE);
            }
        }

    }




    protected class LeftTypingViewHolder extends RecyclerView.ViewHolder {


        public LeftTypingViewHolder(View view) {
            super(view);


            FontChanger fontChanger = new FontChanger(typeface);
            fontChanger.replaceFonts((ViewGroup)view);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    int pos = getLayoutPosition();

                    return true;
                }
            });
        }
    }








    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {



        final Message message = messageList.get(position);
        messageList.get(position).setIndexPosition(position);


        if(holder instanceof LeftTextViewHolder){
            final LeftTextViewHolder holder1 =(LeftTextViewHolder) holder;
            holder1.leftTV.setText(message.getBody());
            holder1.leftTimeTV.setText(message.getTime());

            if(message.getUserIcon()!=null) {
                Picasso.with(context).load(message.getUserIcon()).into(holder1.leftBubbleIconIV);
            }
            holder1.senderNameTV.setText(message.getUserName());
        }
        else{
            if(holder instanceof RightTextViewHolder){
                final RightTextViewHolder holder1 =(RightTextViewHolder) holder;
                holder1.rightTV.setText(message.getBody());
                holder1.rightTimeTV.setText(message.getTime());
                if(message.getUserIcon()!=null) {
                    Picasso.with(context).load(message.getUserIcon()).into(holder1.rightBubbleIconIV);
                }
                holder1.senderNameTV.setText(message.getUserName());
            }
            else{
                if(holder instanceof LeftImageViewHolder){
                    final LeftImageViewHolder holder1 =(LeftImageViewHolder) holder;

                    if(message.getUserIcon()!=null) {
                        Picasso.with(context).load(message.getUserIcon()).into(holder1.leftBubbleIconIV);
                    }
                    holder1.senderNameTV.setText(message.getUserName());
                    if (message.getImageList().get(0) != null && !message.getImageList().get(0).equals("")) {
                        final File image = DiskCacheUtils.findInCache(message.getImageList().get(0).toString(), imageLoader.getDiskCache());
                        if (image!= null && image.exists()) {
                            Picasso.with(context).load(image).into(holder1.leftIV);
                        } else {
                            imageLoader.loadImage(message.getImageList().get(0).toString(), new ImageLoadingListener() {
                                @Override
                                public void onLoadingStarted(String s, View view) {
                                    holder1.leftIV.setImageBitmap(null);
                                }

                                @Override
                                public void onLoadingFailed(String s, View view, FailReason failReason) {

                                }

                                @Override
                                public void onLoadingComplete(String s, View view, final Bitmap bitmap) {
                                    Picasso.with(context).load(s).into(holder1.leftIV);

                                }

                                @Override
                                public void onLoadingCancelled(String s, View view) {

                                }
                            });
                        }
                    }else {
                        holder1.leftIV.setImageBitmap(null);
                    }

                    holder1.leftTimeTV.setText(message.getTime());

                    holder1.leftIV.setTransitionName("photoTransition");
                    holder1.leftIV.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context,ImageFFActivity.class);
                            intent.putExtra("photoURI",message.getImageList().get(0).toString());
                            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, holder1.leftIV,holder1.leftIV.getTransitionName());
                            context.startActivity(intent, optionsCompat.toBundle());
                        }
                    });
                }
                else{
                    if(holder instanceof RightImageViewHolder){
                        final RightImageViewHolder holder1 =(RightImageViewHolder) holder;

                        if(message.getUserIcon()!=null) {
                            Picasso.with(context).load(message.getUserIcon()).into(holder1.rightBubbleIconIV);
                        }
                        holder1.senderNameTV.setText(message.getUserName());

                        if (message.getImageList().get(0) != null && !message.getImageList().get(0).equals("")) {
                            final File image = DiskCacheUtils.findInCache(message.getImageList().get(0).toString(), imageLoader.getDiskCache());
                            if (image!= null && image.exists()) {
                                Picasso.with(context).load(image).into(holder1.rightIV);
                            } else {
                                imageLoader.loadImage(message.getImageList().get(0).toString(), new ImageLoadingListener() {
                                    @Override
                                    public void onLoadingStarted(String s, View view) {
                                        holder1.rightIV.setImageBitmap(null);
                                    }

                                    @Override
                                    public void onLoadingFailed(String s, View view, FailReason failReason) {

                                    }

                                    @Override
                                    public void onLoadingComplete(String s, View view, final Bitmap bitmap) {
                                        Picasso.with(context).load(s).into(holder1.rightIV);

                                    }

                                    @Override
                                    public void onLoadingCancelled(String s, View view) {

                                    }
                                });
                            }
                        }else {
                            holder1.rightIV.setImageBitmap(null);
                        }
                        holder1.rightIV.setTransitionName("photoTransition");
                        holder1.rightIV.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context,ImageFFActivity.class);
                                intent.putExtra("photoURI",message.getImageList().get(0).toString());
                                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, holder1.rightIV,holder1.rightIV.getTransitionName());
                                context.startActivity(intent, optionsCompat.toBundle());
                            }
                        });
                        holder1.rightTimeTV.setText(message.getTime());

                    }
                    else{
                        if(holder instanceof LeftImagesViewHolder){
                            final LeftImagesViewHolder holder1 =(LeftImagesViewHolder) holder;

                            if(message.getUserIcon()!=null) {
                                Picasso.with(context).load(message.getUserIcon()).into(holder1.leftBubbleIconIV);
                            }
                            holder1.senderNameTV.setText(message.getUserName());

                            List<String> imageList = new ArrayList<>();
                            for(int i=0;i<message.getImageList().size();i++){
                                imageList.add(message.getImageList().get(i).toString());
                            }
                            holder1.leftTimeTV.setText(message.getTime());

                            holder1.leftCollageView
                                    .photoMargin(8)
                                    .photoPadding(0)
                                    .backgroundColor(leftBubbleLayoutColor)
                                    .useFirstAsHeader(false) // makes first photo fit device widtdh and use full line
                                    .defaultPhotosForLine(2) // sets default photos number for line of photos (can be changed by program at runtime)
                                    .useCards(true)// adds cardview backgrounds to all photos
                                    .loadPhotos(imageList);

                            holder1.leftCollageView.setTransitionName("photoTransition");
                            holder1.leftCollageView.setOnPhotoClickListener(new CollageView.OnPhotoClickListener() {
                                @Override
                                public void onPhotoClick(int i) {

                                    Intent intent = new Intent(context,ImageFFActivity.class);
                                    intent.putExtra("photoURI",message.getImageList().get(i).toString());
                                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, holder1.leftCollageView,holder1.leftCollageView.getTransitionName());
                                    context.startActivity(intent, optionsCompat.toBundle());
                                }
                            });
                        }
                        else{

                            if(holder instanceof RightImagesViewHolder) {
                                final RightImagesViewHolder holder1 = (RightImagesViewHolder) holder;

                                if(message.getUserIcon()!=null) {
                                    Picasso.with(context).load(message.getUserIcon()).into(holder1.rightBubbleIconIV);
                                }
                                holder1.senderNameTV.setText(message.getUserName());
                                List<String> imageList = new ArrayList<>();
                                for (int i = 0; i < message.getImageList().size(); i++) {
                                    imageList.add(message.getImageList().get(i).toString());
                                }
                                holder1.rightTimeTV.setText(message.getTime());
                                holder1.rightCollageView
                                        .photoMargin(8)
                                        .photoPadding(0)
                                        .backgroundColor(rightBubbleLayoutColor)
                                        .useFirstAsHeader(false) // makes first photo fit device widtdh and use full line
                                        .defaultPhotosForLine(2) // sets default photos number for line of photos (can be changed by program at runtime)
                                        .useCards(true)// adds cardview backgrounds to all photos
                                        .loadPhotos(imageList);

                                holder1.rightCollageView.setTransitionName("photoTransition");
                                holder1.rightCollageView.setOnPhotoClickListener(new CollageView.OnPhotoClickListener() {
                                    @Override
                                    public void onPhotoClick(int i) {

                                        Intent intent = new Intent(context, ImageFFActivity.class);
                                        intent.putExtra("photoURI", message.getImageList().get(i).toString());
                                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, holder1.rightCollageView, holder1.rightCollageView.getTransitionName());
                                        context.startActivity(intent, optionsCompat.toBundle());
                                    }

                                });
                            }
                            else{

                                if(holder instanceof LeftTypingViewHolder){

                                }
                                else{
                                    if(holder instanceof LeftVideoViewHolder){
                                        final LeftVideoViewHolder holder1 =(LeftVideoViewHolder) holder;
                                        final VideoPlayer videoPlayer = new VideoPlayer(context);
                                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                                        videoPlayer.setLayoutParams(params);
                                        videoPlayer.setScaleType(VideoPlayer.ScaleType.CENTER_CROP);
                                        //((LeftVideoViewHolder) holder).videoLL.getLayoutParams().height = getScreenWidth(context) * 9 /16;
                                        //holder1.videoLL.removeAllViews();
                                        holder1.videoLL.addView(videoPlayer);
                                        videoPlayer.loadVideo(message.getVideoUri().toString(),message);
                                        if(message.getUserIcon()!=null) {
                                            Picasso.with(context).load(message.getUserIcon()).into(holder1.leftBubbleIconIV);
                                        }

                                        videoPlayer.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                if(mediaPlayer!=null && mediaPlayer.isPlaying()){
                                                    mediaPlayer.pause();
                                                }
                                                videoPlayer.setTransitionName("videoFF");
                                                Intent intent = new Intent(context, VideoFFActivity.class);
                                                intent.putExtra("videoURI", message.getVideoUri().toString());
                                                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, videoPlayer, videoPlayer.getTransitionName());
                                                context.startActivity(intent, optionsCompat.toBundle());
                                            }
                                        });
                                        holder1.senderNameTV.setText(message.getUserName());

                                        holder1.leftTimeTV.setText(message.getTime());

                                    }
                                    else{
                                        if (holder instanceof  RightVideoViewHolder) {
                                            final RightVideoViewHolder holder1 = (RightVideoViewHolder) holder;
                                            final VideoPlayer videoPlayer = new VideoPlayer(context);
                                            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                                            videoPlayer.setScaleType(VideoPlayer.ScaleType.CENTER_CROP);
                                            videoPlayer.setLayoutParams(params);
                                            //((RightVideoViewHolder) holder).videoLL.getLayoutParams().height = getScreenWidth(context) * 9 /16;
                                            //holder1.videoLL.removeAllViews();
                                            holder1.videoLL.addView(videoPlayer);
                                            videoPlayer.loadVideo(message.getVideoUri().toString(), message);
                                            //adjustAspectRatio(videoPlayer,videoPlayer.getMp().getVideoWidth(),videoPlayer.getMp().getVideoHeight());

                                            if (message.getUserIcon() != null) {
                                                Picasso.with(context).load(message.getUserIcon()).into(holder1.rightBubbleIconIV);
                                            }

                                            videoPlayer.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    if(mediaPlayer!=null && mediaPlayer.isPlaying()){
                                                        mediaPlayer.pause();
                                                    }
                                                    videoPlayer.setTransitionName("videoFF");
                                                    Intent intent = new Intent(context, VideoFFActivity.class);
                                                    intent.putExtra("videoURI", message.getVideoUri().toString());
                                                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, videoPlayer, videoPlayer.getTransitionName());
                                                    context.startActivity(intent, optionsCompat.toBundle());
                                                }
                                            });
                                            holder1.senderNameTV.setText(message.getUserName());

                                            holder1.rightTimeTV.setText(message.getTime());
                                        }
                                        else{
                                            if(holder instanceof LeftAudioViewHolder){
                                                final LeftAudioViewHolder holder1 =(LeftAudioViewHolder) holder;

                                                holder1.leftTimeTV.setText(message.getTime());

                                                if(message.getUserIcon()!=null) {
                                                    Picasso.with(context).load(message.getUserIcon()).into(holder1.leftBubbleIconIV);
                                                }
                                                holder1.senderNameTV.setText(message.getUserName());

                                                holder1.setMessage(message);

                                            }
                                            else{
                                                final RightAudioViewHolder holder1 =(RightAudioViewHolder) holder;

                                                holder1.rightTimeTV.setText(message.getTime());
                                                if(message.getUserIcon()!=null) {
                                                    Picasso.with(context).load(message.getUserIcon()).into(holder1.rightBubbleIconIV);
                                                }


                                                holder1.senderNameTV.setText(message.getUserName());

                                                holder1.setMessage(message);


                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        Log.d(TAG, "onViewRecycledCalled");
        if(holder instanceof LeftVideoViewHolder){
            ((LeftVideoViewHolder) holder).videoLL.removeAllViews();
        }
        else{
            if(holder instanceof RightVideoViewHolder){
                ((RightVideoViewHolder) holder).videoLL.removeAllViews();
            }
        }

    }


    @Override
    public int getItemCount() {
        return filterList.size();
    }

    // set adapter filtered list
    public void setList(List<Message> list) {
        this.filterList = list;
    }

    //call when you want to filter
    public void filterList(String text) {
        filter.filter(text);
    }

    public String getTime(){
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd MMM yyyy HH:mm");
        String time = mdformat.format(calendar.getTime());
        return time;
    }


    public void showLeftBubbleIcon(boolean b){
        this.showLeftBubbleIcon=b;
    }

    public void showRightBubbleIcon(boolean b){
        this.showRightBubbleIcon = b;
    }

    public void setLeftBubbleLayoutColor(int color){
        this.leftBubbleLayoutColor = color;
    }

    public void setRightBubbleLayoutColor(int color){
        this.rightBubbleLayoutColor = color;


    }

    public void setLeftBubbleTextColor(int color){
        this.leftBubbleTextColor = color;
    }

    public void setRightBubbleTextColor(int color){
        this.rightBubbleTextColor = color;
    }

    public void setTimeTextColor(int color){
        this.timeTextColor = color;
    }

    public void setTypeface(Typeface typeface){
        this.typeface = typeface;
    }

    public void showSenderName(boolean b){
        this.showSenderName = b;
    }

    public void setSenderNameTextColor(int color){
        this.senderNameTextColor = color;
    }

    public void setTextSize(float size){
        this.textSize = size;
    }

    public static int getScreenWidth(Context c) {
        int screenWidth = 0; // this is part of the class not the method
        if (screenWidth == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
        }

        return screenWidth;
    }

    private void adjustAspectRatio(VideoPlayer m_TextureView,int videoWidth, int videoHeight) {
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

    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }


}
