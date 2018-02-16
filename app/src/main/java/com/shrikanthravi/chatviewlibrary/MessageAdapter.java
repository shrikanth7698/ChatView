package com.shrikanthravi.chatviewlibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.silencedut.expandablelayout.ExpandableLayout;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * Created by shrikanthravi on 16/02/18.
 */


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    private List<Message> messageList;
    private List<Message> filterList;
    Context context;
    MessageFilter filter;
    ImageLoader imageLoader;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView leftTV,rightTV,leftTimeTV,rightTimeTV;
        public ExpandableLayout leftEL,rightEL;
        public ImageView rightMessageStatusIV;
        public CardView leftIVCV,rightIVCV;
        public ImageView leftIV,rightIV;

        public MyViewHolder(View view) {
            super(view);

            leftTV = view.findViewById(R.id.leftTV);
            rightTV = view.findViewById(R.id.rightTV);
            leftTimeTV = view.findViewById(R.id.leftTimeTV);
            rightTimeTV = view.findViewById(R.id.rightTimeTV);
            leftEL = view.findViewById(R.id.leftEL);
            rightEL = view.findViewById(R.id.rightEL);
            leftIV = view.findViewById(R.id.leftIV);
            rightIV = view.findViewById(R.id.rightIV);
            leftIVCV = view.findViewById(R.id.leftIVCV);
            rightIVCV = view.findViewById(R.id.rightIVCV);

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    int pos = getLayoutPosition();

                    return true;
                }
            });
        }
    }

    public MessageAdapter(List<Message> verticalList, Context context) {

        this.messageList = verticalList;
        this.context = context;
        this.filterList = verticalList;
        filter = new MessageFilter(verticalList,this);
        imageLoader = ImageLoader.getInstance();

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        Typeface regular = Typeface.createFromAsset(context.getAssets(), "fonts/product_san_regular.ttf");
        Typeface bold = Typeface.createFromAsset(context.getAssets(), "fonts/product_sans_bold.ttf");

        final Message message = messageList.get(position);

        holder.leftTV.setTypeface(regular);
        holder.rightTV.setTypeface(regular);
        holder.leftTimeTV.setTypeface(regular);
        holder.rightTimeTV.setTypeface(regular);

        switch (filterList.get(position).getType()){
            case "LEFT":{
                holder.rightEL.setVisibility(View.GONE);
                holder.leftIVCV.setVisibility(View.GONE);
                holder.leftEL.setVisibility(View.VISIBLE);
                holder.leftTV.setVisibility(View.VISIBLE);
                holder.leftTV.setText(message.getBody());
                holder.leftTimeTV.setText(message.getTime());
                break;
            }
            case "RIGHT":{
                holder.leftEL.setVisibility(View.GONE);
                holder.rightIVCV.setVisibility(View.GONE);
                holder.rightEL.setVisibility(View.VISIBLE);
                holder.rightTV.setVisibility(View.VISIBLE);
                holder.rightTV.setText(message.getBody());
                holder.rightTimeTV.setText(message.getTime());
                break;
            }
            case "LeftImage":{
                holder.rightEL.setVisibility(View.GONE);
                holder.leftTV.setVisibility(View.GONE);
                holder.leftEL.setVisibility(View.VISIBLE);
                holder.leftIV.setVisibility(View.VISIBLE);
                holder.leftIVCV.setVisibility(View.VISIBLE);
                if (message.getImageList().get(0) != null && !message.getImageList().get(0).equals("")) {
                    final File image = DiskCacheUtils.findInCache(message.getImageList().get(0).toString(), imageLoader.getDiskCache());
                    if (image!= null && image.exists()) {
                        Picasso.with(context).load(image).into(holder.leftIV);
                    } else {
                        imageLoader.loadImage(message.getImageList().get(0).toString(), new ImageLoadingListener() {
                            @Override
                            public void onLoadingStarted(String s, View view) {
                                holder.leftIV.setImageBitmap(null);
                            }

                            @Override
                            public void onLoadingFailed(String s, View view, FailReason failReason) {

                            }

                            @Override
                            public void onLoadingComplete(String s, View view, final Bitmap bitmap) {
                                Picasso.with(context).load(s).into(holder.leftIV);

                            }

                            @Override
                            public void onLoadingCancelled(String s, View view) {

                            }
                        });
                    }
                }else {
                    holder.rightIV.setImageBitmap(null);
                }
                holder.leftTimeTV.setText(message.getTime());
                break;
            }
            case "RightImage":{
                holder.leftEL.setVisibility(View.GONE);
                holder.rightTV.setVisibility(View.GONE);
                holder.rightEL.setVisibility(View.VISIBLE);
                holder.rightEL.setExpand(true);
                holder.rightIV.setVisibility(View.VISIBLE);
                holder.rightIVCV.setVisibility(View.VISIBLE);

                //holder.rightIV.setImageBitmap(null);

                if (message.getImageList().get(0) != null && !message.getImageList().get(0).equals("")) {
                    final File image = DiskCacheUtils.findInCache(message.getImageList().get(0).toString(), imageLoader.getDiskCache());
                    if (image!= null && image.exists()) {
                        Picasso.with(context).load(image).into(holder.rightIV);
                    } else {
                        imageLoader.loadImage(message.getImageList().get(0).toString(), new ImageLoadingListener() {
                            @Override
                            public void onLoadingStarted(String s, View view) {
                                holder.rightIV.setImageBitmap(null);
                            }

                            @Override
                            public void onLoadingFailed(String s, View view, FailReason failReason) {

                            }

                            @Override
                            public void onLoadingComplete(String s, View view, final Bitmap bitmap) {
                                Picasso.with(context).load(s).into(holder.rightIV);

                            }

                            @Override
                            public void onLoadingCancelled(String s, View view) {

                            }
                        });
                    }
                }else {
                    holder.rightIV.setImageBitmap(null);
                }
                /*Picasso.with(context).load(message.getImageList().get(0).toString()).into(holder.rightIV);*/
                holder.rightTimeTV.setText(message.getTime());
                holder.rightIV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder.rightIV.setTransitionName("photoTransition");


                    }
                });
                break;
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



}