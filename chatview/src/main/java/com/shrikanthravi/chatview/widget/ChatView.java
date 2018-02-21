package com.shrikanthravi.chatview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.balysv.materialripple.MaterialRippleLayout;
import com.github.zagum.expandicon.ExpandIconView;
import com.shrikanthravi.chatview.R;
import com.shrikanthravi.chatview.data.Message;
import com.shrikanthravi.chatview.data.MessageAdapter;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.ScaleInBottomAnimator;

/**
 * Created by shrikanthravi on 20/02/18.
 */

public class ChatView extends RelativeLayout {

    protected Context mContext;
    protected LayoutInflater mLayoutInflater;

    protected RelativeLayout mLayoutRoot;
    protected RecyclerView chatRV;
    protected LinearLayout sendLL;
    protected MaterialRippleLayout sendMRL;
    protected ExpandIconView expandIconView;
    protected List<Message> messageList;
    protected MessageAdapter messageAdapter;
    protected boolean showSenderLL=false;
    protected boolean showLeftBubbleIcon=true;
    protected boolean showRightBubbleIcon=true;

    private int leftBubbleLayoutColor = R.color.colorAccent2;
    private int rightBubbleLayoutColor = R.color.colorAccent1;
    private int leftBubbleTextColor = android.R.color.black;
    private int rightBubbleTextColor = android.R.color.white;
    private int chatViewBackgroundColor = android.R.color.white;
    private int timeTextColor = android.R.color.tab_indicator_text;

    public ChatView(Context context, AttributeSet attrs) {
        super(context, attrs);


        init(context);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ChatView,
                0, 0);
        setAttributes(a);
        a.recycle();

    }


    protected void init(Context context){

        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);

        //load rootview from xml
        View rootView = mLayoutInflater.inflate(R.layout.widget_chatview, this, true);

        //initialize UI
        mLayoutRoot = rootView.findViewById(R.id.rootRL);
        chatRV = rootView.findViewById(R.id.chatRV);
        sendLL = rootView.findViewById(R.id.sendLL);
        sendMRL = rootView.findViewById(R.id.sendMRL);
        expandIconView = rootView.findViewById(R.id.expandIconView);
        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList,context,chatRV);
        WrapContentLinearLayoutManager layoutManager = new WrapContentLinearLayoutManager(context,LinearLayoutManager.VERTICAL,true);
        layoutManager.setStackFromEnd(true);
        chatRV.setLayoutManager(layoutManager);
        chatRV.setItemAnimator(new ScaleInBottomAnimator(new OvershootInterpolator(1f)));
        chatRV.setAdapter(messageAdapter);



    }

    protected void setAttributes(TypedArray attrs){
        //set Attributes from xml
        showSenderLayout(attrs.getBoolean(R.styleable.ChatView_showSenderLayout,showSenderLL));
        showLeftBubbleIcon(attrs.getBoolean(R.styleable.ChatView_showLeftBubbleIcon,showLeftBubbleIcon));
        showRightBubbleIcon(attrs.getBoolean(R.styleable.ChatView_showRightBubbleIcon,showRightBubbleIcon));
        setLeftBubbleLayoutColor(attrs.getColor(R.styleable.ChatView_leftBubbleLayoutColor,mContext.getResources().getColor(leftBubbleLayoutColor)));
        setRightBubbleLayoutColor(attrs.getColor(R.styleable.ChatView_rightBubbleLayoutColor,mContext.getResources().getColor(rightBubbleLayoutColor)));
        setLeftBubbleTextColor(attrs.getColor(R.styleable.ChatView_leftBubbleTextColor,mContext.getResources().getColor(leftBubbleTextColor)));
        setRightBubbleTextColor(attrs.getColor(R.styleable.ChatView_rightBubbleTextColor,mContext.getResources().getColor(rightBubbleTextColor)));
        setChatViewBackgroundColor(attrs.getColor(R.styleable.ChatView_chatViewBackgroundColor,mContext.getResources().getColor(chatViewBackgroundColor)));
        setTimeTextColor(attrs.getColor(R.styleable.ChatView_timeTextColor,mContext.getResources().getColor(timeTextColor)));

    }



    public void addMessage(Message message){


        messageList.add(0,message);
        messageAdapter.notifyItemInserted(0);
        chatRV.smoothScrollToPosition(0);
        mLayoutRoot.invalidate();
    }

    public void removeMessage(Message message){
        messageList.remove(message);
        messageAdapter.notifyDataSetChanged();

    }

    public void showSenderLayout(boolean b){
        if(showSenderLL){
            sendLL.setVisibility(VISIBLE);
        }
        else{
            sendLL.setVisibility(GONE);
        }
    }

    public void showLeftBubbleIcon(boolean b){

    }

    public void showRightBubbleIcon(boolean b){

    }

    public void setLeftBubbleLayoutColor(int color){
        messageAdapter.setLeftBubbleLayoutColor(color);
    }

    public void setRightBubbleLayoutColor(int color){
        messageAdapter.setRightBubbleLayoutColor(color);
    }

    public void setLeftBubbleTextColor(int color){
        messageAdapter.setLeftBubbleTextColor(color);
    }

    public void setRightBubbleTextColor(int color){
        messageAdapter.setRightBubbleTextColor(color);
    }

    public void setChatViewBackgroundColor(int color){

    }

    public void setTimeTextColor(int color){
        messageAdapter.setTimeTextColor(color);
    }


    private class WrapContentLinearLayoutManager extends LinearLayoutManager {
        public WrapContentLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException e) {
                Log.e("probe", "meet a IOOBE in RecyclerView");
            }
        }
    }
}
