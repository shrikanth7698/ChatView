package com.shrikanthravi.chatview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.shrikanthravi.chatview.R;

/**
 * Created by shrikanthravi on 20/02/18.
 */

public class ChatView extends RelativeLayout {

    protected Context mContext;
    protected LayoutInflater mLayoutInflater;

    protected RelativeLayout mLayoutRoot;
    protected RecyclerView chatRV;
    protected LinearLayout sendLL;

    public ChatView(Context context, AttributeSet attrs) {
        super(context, attrs);


        init(context);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ChatView,
                0, 0);
        setAttributes(a);

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




    }

    protected void setAttributes(TypedArray attrs){
        //set Attributes from xml
    }
}
