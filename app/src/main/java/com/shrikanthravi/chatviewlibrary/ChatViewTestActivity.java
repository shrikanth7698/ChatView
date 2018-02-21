package com.shrikanthravi.chatviewlibrary;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.github.zagum.expandicon.ExpandIconView;
import com.shrikanthravi.chatview.widget.ChatView;
import com.shrikanthravi.chatview.data.Message;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.PicassoEngine;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChatViewTestActivity extends AppCompatActivity {


    HorizontalScrollView moreHSV;
    ExpandIconView expandIconView;
    MaterialRippleLayout galleryMRL;
    int imagePickerRequestCode=10;
    ChatView chatView;
    ImageView sendIcon;
    EditText messageET;
    boolean switchbool=true;
    boolean more = false;
    List<Uri> mSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_view_test);

        chatView = findViewById(R.id.chatView);

        messageET = findViewById(R.id.messageET1);
        messageET.requestFocus();

        //Initialization start
        moreHSV = findViewById(R.id.moreLL1);
        expandIconView = findViewById(R.id.expandIconView1);
        expandIconView.setState(1,false);
        galleryMRL = findViewById(R.id.galleryMRL1);
        mSelected  = new ArrayList<>();

        System.out.println("send clicked");

        sendIcon  = findViewById(R.id.sendIcon);

        sendIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(switchbool) {
                    Message message = new Message();
                    message.setBody(messageET.getText().toString().trim());
                    message.setType(Message.RightSimpleMessage);
                    message.setTime(getTime());
                    message.setUserName("Groot");
                    message.setUserIcon(Uri.parse("android.resource://com.shrikanthravi.chatviewlibrary/drawable/groot"));
                    chatView.addMessage(message);
                    messageET.setText("");
                    switchbool=false;
                }
                else{
                    Message message1 = new Message();
                    message1.setBody(messageET.getText().toString().trim());
                    message1.setType(Message.LeftSimpleMessage);
                    message1.setTime(getTime());
                    message1.setUserName("Hodor");
                    message1.setUserIcon(Uri.parse("android.resource://com.shrikanthravi.chatviewlibrary/drawable/hodor"));
                    chatView.addMessage(message1);
                    messageET.setText("");
                    switchbool=true;
                }




            }
        });

        expandIconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(more){
                    expandIconView.setState(1,true);
                    moreHSV.setVisibility(View.GONE);
                    more=false;
                }
                else{
                    expandIconView.setState(0,true);
                    moreHSV.setVisibility(View.VISIBLE);
                    more=true;
                }
            }
        });

        galleryMRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Matisse.from(ChatViewTestActivity.this)
                        .choose(MimeType.allOf())
                        .countable(true)
                        .maxSelectable(9)
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new PicassoEngine())
                        .forResult(imagePickerRequestCode);
            }
        });


    }

    public String getTime(){
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd MMM yyyy HH:mm");
        String time = mdformat.format(calendar.getTime());
        return time;
    }



    public static String getRandomText() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(30);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    /*

    galleryMRL.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Matisse.from(ChatActivity.this)
                    .choose(MimeType.allOf())
                    .countable(true)
                    .maxSelectable(9)
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.85f)
                    .imageEngine(new PicassoEngine())
                    .forResult(imagePickerRequestCode);
        }
    });*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == imagePickerRequestCode && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);

                if(switchbool) {
                    if (mSelected.size() == 1) {
                        Message message = new Message();
                        message.setBody(messageET.getText().toString().trim());
                        message.setType(Message.RightSingleImage);
                        message.setTime(getTime());
                        message.setUserName("Groot");
                        message.setImageList(mSelected);
                        message.setUserIcon(Uri.parse("android.resource://com.shrikanthravi.chatviewlibrary/drawable/groot"));
                        chatView.addMessage(message);
                        switchbool=false;
                    } else {

                        Message message = new Message();
                        message.setBody(messageET.getText().toString().trim());
                        message.setType(Message.RightMultipleImages);
                        message.setTime(getTime());
                        message.setUserName("Groot");
                        message.setImageList(mSelected);
                        message.setUserIcon(Uri.parse("android.resource://com.shrikanthravi.chatviewlibrary/drawable/groot"));
                        chatView.addMessage(message);
                        switchbool=false;
                    }
                }
                else{

                    if (mSelected.size() == 1) {
                        Message message = new Message();
                        message.setBody(messageET.getText().toString().trim());
                        message.setType(Message.LeftSingleImage);
                        message.setTime(getTime());
                        message.setUserName("Hodor");
                        message.setImageList(mSelected);
                        message.setUserIcon(Uri.parse("android.resource://com.shrikanthravi.chatviewlibrary/drawable/hodor"));
                        chatView.addMessage(message);
                        switchbool=true;
                    } else {

                        Message message = new Message();
                        message.setBody(messageET.getText().toString().trim());
                        message.setType(Message.LeftMultipleImages);
                        message.setTime(getTime());
                        message.setUserName("Hodor");
                        message.setImageList(mSelected);
                        message.setUserIcon(Uri.parse("android.resource://com.shrikanthravi.chatviewlibrary/drawable/hodor"));
                        chatView.addMessage(message);
                        switchbool=true;
                    }

                }
            }

        }


}
