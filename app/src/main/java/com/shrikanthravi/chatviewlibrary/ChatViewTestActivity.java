package com.shrikanthravi.chatviewlibrary;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.shrikanthravi.chatview.widget.ChatView;
import com.shrikanthravi.chatview.data.Message;

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
}
