package com.shrikanthravi.chatviewlibrary;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.balysv.materialripple.MaterialRippleLayout;
import com.github.zagum.expandicon.ExpandIconView;
import com.shrikanthravi.chatview.data.Message;
import com.shrikanthravi.chatview.widget.ChatView;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.PicassoEngine;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class ChatViewTestActivity extends AppCompatActivity {


    HorizontalScrollView moreHSV;
    ExpandIconView expandIconView;
    MaterialRippleLayout galleryMRL;
    public static int imagePickerRequestCode = 10;
    public static int SELECT_VIDEO = 11;
    public static int CAMERA_REQUEST = 12;
    public static int SELECT_AUDIO = 13;
    ChatView chatView;
    ImageView sendIcon;
    EditText messageET;
    boolean switchbool = true;
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
        expandIconView.setState(1, false);
        galleryMRL = findViewById(R.id.galleryMRL1);
        mSelected = new ArrayList<>();

        //Send button click listerer
        chatView.setOnClickSendButtonListener(new ChatView.OnClickSendButtonListener() {
            @Override
            public void onSendButtonClick(String body) {
                if (switchbool) {
                    Message message = new Message();
                    message.setBody(body);
                    message.setMessageType(Message.MessageType.RightSimpleImage);
                    message.setTime(getTime());
                    message.setUserName("Groot");
                    message.setUserIcon(Uri.parse("android.resource://com.shrikanthravi.chatviewlibrary/drawable/groot"));
                    chatView.addMessage(message);

                    switchbool = false;
                } else {
                    Message message1 = new Message();
                    message1.setBody(body);
                    message1.setMessageType(Message.MessageType.LeftSimpleMessage);
                    message1.setTime(getTime());
                    message1.setUserName("Hodor");
                    message1.setUserIcon(Uri.parse("android.resource://com.shrikanthravi.chatviewlibrary/drawable/hodor"));
                    chatView.addMessage(message1);

                    switchbool = true;
                }
            }
        });

        //Gallery button click listener
        chatView.setOnClickGalleryButtonListener(new ChatView.OnClickGalleryButtonListener() {
            @Override
            public void onGalleryButtonClick() {
                ChatViewTestActivityPermissionsDispatcher.pickImageFilesWithPermissionCheck(ChatViewTestActivity.this);
            }
        });

        //Video button click listener
        chatView.setOnClickVideoButtonListener(new ChatView.OnClickVideoButtonListener() {
            @Override
            public void onVideoButtonClick() {
                ChatViewTestActivityPermissionsDispatcher.pickVideoFileWithPermissionCheck(ChatViewTestActivity.this);
            }
        });

        //Camera button click listener
        chatView.setOnClickCameraButtonListener(new ChatView.OnClickCameraButtonListener() {
            @Override
            public void onCameraButtonClicked() {
                ChatViewTestActivityPermissionsDispatcher.captureImageWithPermissionCheck(ChatViewTestActivity.this);
            }
        });


        chatView.setOnClickAudioButtonListener(new ChatView.OnClickAudioButtonListener() {
            @Override
            public void onAudioButtonClicked() {
                ChatViewTestActivityPermissionsDispatcher.pickAudioFileWithPermissionCheck(ChatViewTestActivity.this);
            }
        });


    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
    void captureImage() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory(), "MyPhoto.jpg");
        file.delete();
        File file1 = new File(Environment.getExternalStorageDirectory(), "MyPhoto.jpg");

        Uri uri = FileProvider.getUriForFile(ChatViewTestActivity.this, getApplicationContext().getPackageName() + ".provider", file1);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    void pickVideoFile() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        i.setType("video/*");
        startActivityForResult(i, SELECT_VIDEO);
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    void pickImageFiles() {
        Matisse.from(ChatViewTestActivity.this)
                .choose(MimeType.allOf())
                .countable(true)
                .maxSelectable(9)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new PicassoEngine())
                .forResult(imagePickerRequestCode);
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    void pickAudioFile() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("audio/*");
        startActivityForResult(i, SELECT_AUDIO);
    }


    public String getTime() {
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
        for (int i = 0; i < randomLength; i++) {
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        ChatViewTestActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {
            case 10: {

                //Image Selection result
                if (resultCode == RESULT_OK) {
                    mSelected = Matisse.obtainResult(data);

                    if (switchbool) {
                        if (mSelected.size() == 1) {
                            Message message = new Message();
                            message.setBody(messageET.getText().toString().trim());
                            message.setMessageType(Message.MessageType.RightSingleImage);
                            message.setTime(getTime());
                            message.setUserName("Groot");
                            message.setImageList(mSelected);
                            message.setUserIcon(Uri.parse("android.resource://com.shrikanthravi.chatviewlibrary/drawable/groot"));
                            chatView.addMessage(message);
                            switchbool = false;
                        } else {

                            Message message = new Message();
                            message.setBody(messageET.getText().toString().trim());
                            message.setMessageType(Message.MessageType.RightMultipleImages);
                            message.setTime(getTime());
                            message.setUserName("Groot");
                            message.setImageList(mSelected);
                            message.setUserIcon(Uri.parse("android.resource://com.shrikanthravi.chatviewlibrary/drawable/groot"));
                            chatView.addMessage(message);
                            switchbool = false;
                        }
                    } else {

                        if (mSelected.size() == 1) {
                            Message message = new Message();
                            message.setBody(messageET.getText().toString().trim());
                            message.setMessageType(Message.MessageType.LeftSingleImage);
                            message.setTime(getTime());
                            message.setUserName("Hodor");
                            message.setImageList(mSelected);
                            message.setUserIcon(Uri.parse("android.resource://com.shrikanthravi.chatviewlibrary/drawable/hodor"));
                            chatView.addMessage(message);
                            switchbool = true;
                        } else {

                            Message message = new Message();
                            message.setBody(messageET.getText().toString().trim());
                            message.setMessageType(Message.MessageType.LeftMultipleImages);
                            message.setTime(getTime());
                            message.setUserName("Hodor");
                            message.setImageList(mSelected);
                            message.setUserIcon(Uri.parse("android.resource://com.shrikanthravi.chatviewlibrary/drawable/hodor"));
                            chatView.addMessage(message);
                            switchbool = true;
                        }

                    }
                }
                break;
            }
            case 11: {

                //Video Selection Result
                if (resultCode == RESULT_OK) {
                    if (switchbool) {
                        Message message = new Message();
                        message.setMessageType(Message.MessageType.RightVideo);
                        message.setTime(getTime());
                        message.setUserName("Groot");
                        message.setVideoUri(Uri.parse(getVideoPath(ChatViewTestActivity.this, data.getData())));
                        message.setUserIcon(Uri.parse("android.resource://com.shrikanthravi.chatviewlibrary/drawable/groot"));
                        chatView.addMessage(message);
                        switchbool = false;
                    } else {
                        Message message = new Message();

                        message.setMessageType(Message.MessageType.LeftVideo);
                        message.setTime(getTime());
                        message.setUserName("Hodor");
                        message.setVideoUri(Uri.parse(getVideoPath(ChatViewTestActivity.this, data.getData())));
                        message.setUserIcon(Uri.parse("android.resource://com.shrikanthravi.chatviewlibrary/drawable/hodor"));
                        chatView.addMessage(message);
                        switchbool = true;
                    }
                }
                break;
            }
            case 12: {

                //Image Capture result

                if (resultCode == RESULT_OK) {


                    if (switchbool) {
                        Message message = new Message();
                        message.setMessageType(Message.MessageType.RightSingleImage);
                        message.setTime(getTime());
                        message.setUserName("Groot");
                        mSelected.clear();
                        File file = new File(Environment.getExternalStorageDirectory(), "MyPhoto.jpg");
                        //Uri of camera image
                        Uri uri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);
                        mSelected.add(uri);
                        message.setImageList(mSelected);
                        message.setUserIcon(Uri.parse("android.resource://com.shrikanthravi.chatviewlibrary/drawable/groot"));
                        chatView.addMessage(message);
                        switchbool = false;
                    } else {
                        Message message = new Message();

                        message.setMessageType(Message.MessageType.LeftSingleImage);
                        message.setTime(getTime());
                        message.setUserName("Hodor");
                        mSelected.clear();
                        File file = new File(Environment.getExternalStorageDirectory(), "MyPhoto.jpg");
                        //Uri of camera image
                        Uri uri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);
                        mSelected.add(uri);
                        message.setImageList(mSelected);
                        message.setUserIcon(Uri.parse("android.resource://com.shrikanthravi.chatviewlibrary/drawable/hodor"));
                        chatView.addMessage(message);
                        switchbool = true;
                    }
                }
                break;
            }
            case 13: {
                if (resultCode == RESULT_OK) {
                    if (switchbool) {
                        Message message = new Message();
                        message.setMessageType(Message.MessageType.RightAudio);
                        message.setTime(getTime());
                        message.setUserName("Groot");
                        message.setAudioUri(Uri.parse(getPathAudio(data.getData())));
                        message.setUserIcon(Uri.parse("android.resource://com.shrikanthravi.chatviewlibrary/drawable/groot"));
                        chatView.addMessage(message);
                        switchbool = false;
                    } else {
                        Message message = new Message();

                        message.setMessageType(Message.MessageType.LeftAudio);
                        message.setTime(getTime());
                        message.setUserName("Hodor");
                        message.setAudioUri(Uri.parse(getPathAudio(data.getData())));
                        message.setUserIcon(Uri.parse("android.resource://com.shrikanthravi.chatviewlibrary/drawable/hodor"));
                        chatView.addMessage(message);
                        switchbool = true;
                    }
                }
                break;
            }
        }

    }


    //region get media path

    @SuppressLint("NewApi")
    public static String getVideoPath(Context context, Uri uri){
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[]{
                        split[1]
                };
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {


            if (isGooglePhotosUri(uri)) {
                return uri.getLastPathSegment();
            }

            String[] projection = {
                    MediaStore.Images.Media.DATA
            };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver()
                        .query(uri, projection, selection, selectionArgs, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    //endregion

    public String getPathAudio(Uri uri) {
        System.out.println("getpath " + uri.toString());
        String filePath = "";
        String fileId = DocumentsContract.getDocumentId(uri);
        // Split at colon, use second item in the array
        String id = fileId.split(":")[1];
        String[] column = {MediaStore.Audio.Media.DATA};
        String selector = MediaStore.Audio.Media._ID + "=?";
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                column, selector, new String[]{id}, null);
        int columnIndex = cursor.getColumnIndex(column[0]);
        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;

    }


}
