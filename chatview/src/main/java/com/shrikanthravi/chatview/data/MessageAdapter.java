package com.shrikanthravi.chatview.data;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.lopei.collageview.CollageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.nostra13.universalimageloader.utils.L;
import com.shrikanthravi.chatview.R;
import com.silencedut.expandablelayout.ExpandableLayout;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shrikanthravi on 16/02/18.
 */


public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Message> messageList;
    private List<Message> filterList;
    Context context;
    MessageFilter filter;
    ImageLoader imageLoader;


    protected boolean showSenderLL=false;
    protected boolean showLeftBubbleIcon=true;
    protected boolean showRightBubbleIcon=true;

    private int leftBubbleLayoutColor = R.color.colorAccent2;
    private int rightBubbleLayoutColor = R.color.colorAccent1;
    private int leftBubbleTextColor = android.R.color.black;
    private int rightBubbleTextColor = android.R.color.white;
    private int chatViewBackgroundColor = android.R.color.white;
    private int timeTextColor = android.R.color.tab_indicator_text;

    @Override
    public int getItemViewType(int position) {
        if(messageList.get(position).getType().equals("LEFT")){
            return 0;
        }
        else{
            if(messageList.get(position).getType().equals("RIGHT")){
                return 1;
            }
            else{
                if(messageList.get(position).getType().equals("LeftImage")){
                    return 2;
                }
                else{
                    if(messageList.get(position).getType().equals("RightImage")){
                        return 3;
                    }
                    else{
                        if(messageList.get(position).getType().equals("LeftImages")){
                            return 4;
                        }
                        else{
                            if(messageList.get(position).getType().equals("RightImages")){
                                return 5;
                            }
                            else{

                                return 6;

                            }

                        }
                    }
                }
            }
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if(viewType==0){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.left_text_layout, parent, false);
            viewHolder = new LeftTextViewHolder(view);
        }
        else{
            if(viewType==1){
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.right_text_layout, parent, false);
                viewHolder = new RightTextViewHolder(view);
            }
            else{
                if(viewType==2){
                    View view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.left_image_layout, parent, false);
                    viewHolder = new LeftImageViewHolder(view);
                }
                else{
                    if(viewType==3){
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.right_image_layout, parent, false);
                        viewHolder = new RightImageViewHolder(view);
                    }
                    else{
                        if(viewType==4){
                            View view = LayoutInflater.from(parent.getContext())
                                    .inflate(R.layout.left_images_layout, parent, false);
                            viewHolder = new LeftImagesViewHolder(view);
                        }
                        else{
                            if(viewType==5) {
                                View view = LayoutInflater.from(parent.getContext())
                                        .inflate(R.layout.right_images_layout, parent, false);
                                viewHolder = new RightImagesViewHolder(view);
                            }
                            else{
                                View view = LayoutInflater.from(parent.getContext())
                                        .inflate(R.layout.left_typing_layout, parent, false);
                                viewHolder = new LeftTypingViewHolder(view);

                            }
                        }
                    }
                }
            }

        }


        return viewHolder;
    }



    protected class LeftTextViewHolder extends RecyclerView.ViewHolder {

        public TextView leftTV,leftTimeTV;
        public ExpandableLayout leftEL,rightEL;
        public ImageView lefttMessageStatusIV;

        public LeftTextViewHolder(View view) {
            super(view);

            leftTV = view.findViewById(R.id.leftTV);
            leftTimeTV = view.findViewById(R.id.leftTimeTV);
            leftEL = view.findViewById(R.id.leftEL);
            rightEL = view.findViewById(R.id.rightEL);
            setBackgroundColor(leftBubbleLayoutColor);
            setTextColor(leftBubbleTextColor);
            setTimeTextColor(timeTextColor);

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
    }
    protected class RightTextViewHolder extends RecyclerView.ViewHolder {

        public TextView rightTV,rightTimeTV;
        public ExpandableLayout leftEL;
        public ImageView rightMessageStatusIV;

        public RightTextViewHolder(View view) {
            super(view);

            rightTV = view.findViewById(R.id.rightTV);
            rightTimeTV = view.findViewById(R.id.rightTimeTV);
            leftEL = view.findViewById(R.id.leftEL);
            setBackgroundColor(rightBubbleLayoutColor);
            setTextColor(rightBubbleTextColor);
            setTimeTextColor(timeTextColor);

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


    }

    protected class LeftImageViewHolder extends RecyclerView.ViewHolder {

        public TextView leftTV,leftTimeTV;
        public ExpandableLayout leftEL;
        public ImageView leftMessageStatusIV;
        public CardView leftIVCV;
        public ImageView leftIV;

        public LeftImageViewHolder(View view) {
            super(view);

            leftTV = view.findViewById(R.id.leftTV);
            leftTimeTV = view.findViewById(R.id.leftTimeTV);
            leftEL = view.findViewById(R.id.leftEL);
            leftIV = view.findViewById(R.id.leftIV);
            leftIVCV = view.findViewById(R.id.leftIVCV);
            setBackgroundColor(leftBubbleLayoutColor);

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
    }

    protected class RightImageViewHolder extends RecyclerView.ViewHolder {

        public TextView rightTV,rightTimeTV;
        public ExpandableLayout rightEL;
        public ImageView rightMessageStatusIV;
        public CardView rightIVCV;
        public ImageView rightIV;

        public RightImageViewHolder(View view) {
            super(view);

            rightTV = view.findViewById(R.id.rightTV);
            rightTimeTV = view.findViewById(R.id.rightTimeTV);
            rightEL = view.findViewById(R.id.rightEL);
            rightIV = view.findViewById(R.id.rightIV);
            rightIVCV = view.findViewById(R.id.rightIVCV);
            setBackgroundColor(rightBubbleLayoutColor);

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
    }

    protected class LeftImagesViewHolder extends RecyclerView.ViewHolder {

        public TextView leftTimeTV;
        public ExpandableLayout rightEL;
        public ImageView leftMessageStatusIV;
        public CollageView leftCollageView;

        public LeftImagesViewHolder(View view) {
            super(view);

            leftTimeTV = view.findViewById(R.id.leftTimeTV);
            rightEL = view.findViewById(R.id.rightEL);
            leftCollageView = view.findViewById(R.id.leftCollageView);

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    int pos = getLayoutPosition();

                    return true;
                }
            });
        }

    }

    protected class RightImagesViewHolder extends RecyclerView.ViewHolder {

        public TextView rightTimeTV;
        public ExpandableLayout rightEL;
        public ImageView rightMessageStatusIV;
        public CollageView rightCollageView,leftCollageView;

        public RightImagesViewHolder(View view) {
            super(view);

            rightTimeTV = view.findViewById(R.id.rightTimeTV);
            rightEL = view.findViewById(R.id.rightEL);
            rightCollageView = view.findViewById(R.id.rightCollageView);
            leftCollageView = view.findViewById(R.id.leftCollageView);

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    int pos = getLayoutPosition();

                    return true;
                }
            });
        }
    }

    protected class LeftTypingViewHolder extends RecyclerView.ViewHolder {


        public LeftTypingViewHolder(View view) {
            super(view);


            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    int pos = getLayoutPosition();

                    return true;
                }
            });
        }
    }


    public MessageAdapter(List<Message> verticalList, Context context,RecyclerView recyclerView) {

        this.messageList = verticalList;
        this.context = context;
        this.filterList = verticalList;
        filter = new MessageFilter(verticalList,this);
        imageLoader = ImageLoader.getInstance();

    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


        Typeface regular = Typeface.createFromAsset(context.getAssets(), "fonts/product_san_regular.ttf");
        Typeface bold = Typeface.createFromAsset(context.getAssets(), "fonts/product_sans_bold.ttf");

        final Message message = messageList.get(position);

        if(holder instanceof LeftTextViewHolder){
            ((LeftTextViewHolder) holder).leftTV.setText(message.getBody());
            ((LeftTextViewHolder) holder).leftTV.setTypeface(regular);
            ((LeftTextViewHolder) holder).leftTimeTV.setText(message.getTime());
        }
        else{
            if(holder instanceof RightTextViewHolder){
                ((RightTextViewHolder) holder).rightTV.setText(message.getBody());
                ((RightTextViewHolder) holder).rightTV.setTypeface(regular);
                ((RightTextViewHolder) holder).rightTimeTV.setText(message.getTime());
            }
            else{
                if(holder instanceof LeftImageViewHolder){
                    final LeftImageViewHolder holder1 =(LeftImageViewHolder) holder;
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
                    holder1.leftTimeTV.setTypeface(regular);
                    holder1.leftIV.setTransitionName("photoTransition");
                    holder1.leftIV.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            /*Intent intent = new Intent(context,ImageFFActivity.class);
                            intent.putExtra("photoURI",message.getImageList().get(0).toString());
                            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, holder1.leftIV,holder1.leftIV.getTransitionName());
                            context.startActivity(intent, optionsCompat.toBundle());*/
                        }
                    });
                }
                else{
                    if(holder instanceof RightImageViewHolder){
                        final RightImageViewHolder holder1 =(RightImageViewHolder) holder;
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
                                /*Intent intent = new Intent(context,ImageFFActivity.class);
                                intent.putExtra("photoURI",message.getImageList().get(0).toString());
                                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, holder1.rightIV,holder1.rightIV.getTransitionName());
                                context.startActivity(intent, optionsCompat.toBundle());*/
                            }
                        });
                        holder1.rightTimeTV.setText(message.getTime());
                        holder1.rightTimeTV.setTypeface(regular);
                    }
                    else{
                        if(holder instanceof LeftImagesViewHolder){
                            final LeftImagesViewHolder holder1 =(LeftImagesViewHolder) holder;
                            List<String> imageList = new ArrayList<>();
                            for(int i=0;i<message.getImageList().size();i++){
                                imageList.add(message.getImageList().get(i).toString());
                            }
                            holder1.leftTimeTV.setText(message.getTime());
                            holder1.leftTimeTV.setTypeface(regular);
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

                                    /*Intent intent = new Intent(context,ImageFFActivity.class);
                                    intent.putExtra("photoURI",message.getImageList().get(i).toString());
                                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, holder1.leftCollageView,holder1.leftCollageView.getTransitionName());
                                    context.startActivity(intent, optionsCompat.toBundle());*/
                                }
                            });
                        }
                        else{

                            if(holder instanceof RightImagesViewHolder) {
                                final RightImagesViewHolder holder1 = (RightImagesViewHolder) holder;
                                List<String> imageList = new ArrayList<>();
                                for (int i = 0; i < message.getImageList().size(); i++) {
                                    imageList.add(message.getImageList().get(i).toString());
                                }
                                holder1.rightTimeTV.setText(message.getTime());
                                holder1.rightTimeTV.setTypeface(regular);
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

                                        /*Intent intent = new Intent(context, ImageFFActivity.class);
                                        intent.putExtra("photoURI", message.getImageList().get(i).toString());
                                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, holder1.rightCollageView, holder1.rightCollageView.getTransitionName());
                                        context.startActivity(intent, optionsCompat.toBundle());*/
                                    }

                                });
                            }
                            else{

                                if(holder instanceof LeftTypingViewHolder){


                                }
                            }

                        }


                    }

                }
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






}
