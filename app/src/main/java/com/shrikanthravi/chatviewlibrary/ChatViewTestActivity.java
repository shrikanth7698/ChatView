package com.shrikanthravi.chatviewlibrary;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.shrikanthravi.chatview.widget.ChatView;
import com.shrikanthravi.chatview.data.Message;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.PicassoEngine;

import java.text.SimpleDateFormat;
import java.util.Random;

public class ChatViewTestActivity extends AppCompatActivity {

    ChatView chatView;
    ImageView sendIcon;
    EditText messageET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_view_test);

        chatView = findViewById(R.id.chatView);

        messageET = findViewById(R.id.messageET1);
        messageET.requestFocus();

        System.out.println("send clicked");

        sendIcon  = findViewById(R.id.sendIcon);

        sendIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message = new Message();
                message.setBody(messageET.getText().toString());
                message.setType(Message.RightSimpleMessage);
                message.setTime(getTime());


                chatView.addMessage(message);
                final Handler handler1 = new Handler();
                messageET.setText("");

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.setBody(getRandomText());
                        message.setType(Message.LeftSimpleMessage);
                        message.setTime(getTime());
                        chatView.addMessage(message);
                    }
                },2000);
            }
        });
            //new Message("RIGHT",messageET.getText().toString().trim(),getTime())


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
    });
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == imagePickerRequestCode && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);

            if(mSelected.size()==1) {
                messageList.add(0,new com.shrikanthravi.chatviewlibrary.Message("RightImage", "", getTime(), mSelected));
                messageAdapter.notifyItemInserted(0);
                chatRV.smoothScrollToPosition(0);
                dbHandler.insertMessage(new com.shrikanthravi.chatviewlibrary.Message("RightImage", "", getTime(), mSelected));
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        messageList.add(0,new com.shrikanthravi.chatviewlibrary.Message("LeftImage", "", getTime(), mSelected));
                        messageAdapter.notifyItemInserted(0);
                        chatRV.smoothScrollToPosition(0);
                        dbHandler.insertMessage(new com.shrikanthravi.chatviewlibrary.Message("LeftImage", "", getTime(), mSelected));
                    }
                },3000);
            }
            else{
                messageList.add(0,new com.shrikanthravi.chatviewlibrary.Message("RightImages", "", getTime(), mSelected));
                messageAdapter.notifyItemInserted(0);
                chatRV.smoothScrollToPosition(0);
                dbHandler.insertMessage(new com.shrikanthravi.chatviewlibrary.Message("RightImages", "", getTime(), mSelected));
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        messageList.add(0,new com.shrikanthravi.chatviewlibrary.Message("LeftImages", "", getTime(), mSelected));
                        messageAdapter.notifyItemInserted(0);
                        chatRV.smoothScrollToPosition(0);
                        dbHandler.insertMessage(new com.shrikanthravi.chatviewlibrary.Message("LeftImages", "", getTime(), mSelected));
                    }
                },3000);
            }

        }
    }
    */
}
