package com.shrikanthravi.chatviewlibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.ActivityOptionsCompat;
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
import com.silencedut.expandablelayout.ExpandableLayout;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shrikanthravi on 16/02/18.
 */


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private List<Message> messageList;
    private List<Message> filterList;
    Context context;
    MessageFilter filter;
    ImageLoader imageLoader;
    // The minimum amount of items to have below your current scroll position before loading more.
    private int visibleThreshold = 10;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView leftTV,rightTV,leftTimeTV,rightTimeTV;
        public ExpandableLayout leftEL,rightEL;
        public ImageView rightMessageStatusIV;
        public CardView leftIVCV,rightIVCV;
        public ImageView leftIV,rightIV;
        public HorizontalScrollView quickContainerHSV;
        public LinearLayout quickListLL;
        public CollageView rightCollageView,leftCollageView;

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
            quickContainerHSV = view.findViewById(R.id.quickContainerHSV);
            quickListLL = view.findViewById(R.id.quickListLL);
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

    public MessageAdapter(List<Message> verticalList, Context context,RecyclerView recyclerView) {

        this.messageList = verticalList;
        this.context = context;
        this.filterList = verticalList;
        filter = new MessageFilter(verticalList,this);
        imageLoader = ImageLoader.getInstance();
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        // End has been reached
                        // Do something
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });
        }

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
                holder.quickContainerHSV.setVisibility(View.GONE);
                break;
            }
            case "RIGHT":{
                holder.rightCollageView.setVisibility(View.GONE);
                holder.quickContainerHSV.setVisibility(View.GONE);
                holder.leftEL.setVisibility(View.GONE);
                holder.rightIVCV.setVisibility(View.GONE);
                holder.rightEL.setVisibility(View.VISIBLE);
                holder.rightTV.setVisibility(View.VISIBLE);
                holder.rightTV.setText(message.getBody());
                holder.rightTimeTV.setText(message.getTime());
                break;
            }
            case "LeftImage":{
                holder.quickContainerHSV.setVisibility(View.GONE);
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
                holder.rightCollageView.setVisibility(View.GONE);
                holder.quickContainerHSV.setVisibility(View.GONE);
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

                        Intent intent = new Intent(context,ImageFFActivity.class);
                        intent.putExtra("photoURI",message.getImageList().get(0).toString());
                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, holder.rightIV,holder.rightIV.getTransitionName());
                        context.startActivity(intent, optionsCompat.toBundle());
                    }
                });
                break;

            }
            case "quick": {

                holder.quickContainerHSV.setVisibility(View.VISIBLE);
                holder.rightEL.setVisibility(View.GONE);
                holder.leftEL.setVisibility(View.GONE);
                for (int i = 0; i < message.getQuickList().size(); i++) {

                    View child1, space;
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    child1 = inflater.inflate(R.layout.quick_reply_layout, null);
                    MaterialRippleLayout quickMRL = child1.findViewById(R.id.quickMRL);
                    TextView text1 = child1.findViewById(R.id.quickTV);
                    text1.setTypeface(regular);
                    text1.setText(message.getQuickList().get(i));
                    text1.setTag(i);
                    View.OnClickListener listener = new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            int i = (Integer) view.getTag();
                            messageList.remove(position);
                            notifyItemRemoved(position);
                            messageList.add(new Message("RIGHT",message.getQuickList().get(i).trim().toString(),getTime()));
                            notifyItemInserted(messageList.size()-1);

                        }
                    };
                    text1.setOnClickListener(listener);

                    holder.quickListLL.addView(child1);

                }
                break;
            }

            case "RightImages":{

                holder.quickContainerHSV.setVisibility(View.GONE);
                holder.rightTV.setVisibility(View.GONE);
                holder.leftEL.setVisibility(View.GONE);
                holder.rightTV.setVisibility(View.GONE);
                holder.rightIV.setVisibility(View.GONE);
                holder.rightEL.setVisibility(View.VISIBLE);
                holder.rightCollageView.setVisibility(View.VISIBLE);
                List<String> imageList = new ArrayList<>();
                for(int i=0;i<message.getImageList().size();i++){
                    imageList.add(message.getImageList().get(i).toString());
                }
                holder.rightTimeTV.setText(message.getTime());
                holder.rightCollageView
                        .photoMargin(8)
                        .photoPadding(0)
                        .backgroundColor(context.getResources().getColor(R.color.colorAccent1))
                        .useFirstAsHeader(false) // makes first photo fit device widtdh and use full line
                        .defaultPhotosForLine(2) // sets default photos number for line of photos (can be changed by program at runtime)
                        .useCards(true)// adds cardview backgrounds to all photos
                        .loadPhotos(imageList);

                holder.rightCollageView.setTransitionName("photoTransition");
                holder.rightCollageView.setOnPhotoClickListener(new CollageView.OnPhotoClickListener() {
                    @Override
                    public void onPhotoClick(int i) {

                        Intent intent = new Intent(context,ImageFFActivity.class);
                        intent.putExtra("photoURI",message.getImageList().get(i).toString());
                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, holder.rightCollageView,holder.rightCollageView.getTransitionName());
                        context.startActivity(intent, optionsCompat.toBundle());
                    }
                });
                break;
            }

            case "LeftImages":{

                holder.quickContainerHSV.setVisibility(View.GONE);
                holder.rightTV.setVisibility(View.GONE);
                holder.rightTV.setVisibility(View.GONE);
                holder.rightIV.setVisibility(View.GONE);
                holder.rightEL.setVisibility(View.GONE);
                holder.leftEL.setVisibility(View.VISIBLE);
                holder.leftCollageView.setVisibility(View.VISIBLE);
                List<String> imageList = new ArrayList<>();
                for(int i=0;i<message.getImageList().size();i++){
                    imageList.add(message.getImageList().get(i).toString());
                }
                holder.leftTimeTV.setText(message.getTime());
                holder.leftCollageView
                        .photoMargin(8)
                        .photoPadding(0)
                        .backgroundColor(context.getResources().getColor(R.color.colorAccent1))
                        .useFirstAsHeader(false) // makes first photo fit device widtdh and use full line
                        .defaultPhotosForLine(2) // sets default photos number for line of photos (can be changed by program at runtime)
                        .useCards(true)// adds cardview backgrounds to all photos
                        .loadPhotos(imageList);

                holder.leftCollageView.setTransitionName("photoTransition");
                holder.leftCollageView.setOnPhotoClickListener(new CollageView.OnPhotoClickListener() {
                    @Override
                    public void onPhotoClick(int i) {

                        Intent intent = new Intent(context,ImageFFActivity.class);
                        intent.putExtra("photoURI",message.getImageList().get(i).toString());
                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, holder.leftCollageView,holder.leftCollageView.getTransitionName());
                        context.startActivity(intent, optionsCompat.toBundle());
                    }
                });
                break;
            }




        }


    }
    public void setLoaded() {
        loading = false;
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

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }


}
