# ChatView for Android

### Repo no longer being maintained


<img src="https://raw.githubusercontent.com/shrikanth7698/ChatView/master/chatview_library_icon_1.png" width="100" >
<br/>
This is an Android library which can be used to add chat functionality to your android application with just a few lines of code.
<br/>
This library is still in beta but will be improved over time.
<br/>


<table>
  <tr>
    <td>
       <img src="https://drive.google.com/uc?id=1NDELjyr1T0Y-JMfFmEFpQvZfT6rRXIX-" width="300" >
    </td>
    <td>
      <img src="https://drive.google.com/uc?id=1w1rgZd5oHaDX_FKRfvCb1ddS_LMzJuHK" width="300">
    </td>    
  </tr>
  <tr>
    <td>
      <img src="https://drive.google.com/uc?id=1XzzM3QJjz2IqZ5mrsLKB15q_lelnx9oW" width="300">
    </td>
    <td>
      <img src="https://raw.githubusercontent.com/shrikanth7698/ChatView/master/Explanation%20Screenshot%202.png" width="300">
    </td>

  </tr>
</table>

### Version
[![](https://jitpack.io/v/shrikanth7698/ChatView.svg)](https://jitpack.io/#shrikanth7698/ChatView)


### Installation
Add this to your root build.gradle at the end of repositories:
```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
Then add dependency
```gradle
dependencies {
	compile 'com.github.shrikanth7698:ChatView:v0.1.2'
}
```
### Usage

Drop the ChatView in your XML layout as is shown below:
```xml
    <com.shrikanthravi.chatview.widget.ChatView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:showSenderLayout="false" 
        app:leftBubbleLayoutColor="#ff7b7b"
        app:rightBubbleLayoutColor="@color/colorAccent2"
        app:leftBubbleTextColor="@android:color/white"
        app:rightBubbleTextColor="@android:color/black"
        android:layout_above="@+id/sendLL"
        app:showSenderName="true"
        android:id="@+id/chatView">

    </com.shrikanthravi.chatview.widget.ChatView>

```
don't forget to add this attribute to your root layout.
```xml
xmlns:app="http://schemas.android.com/apk/res-auto"
```
And then in your Activity or Fragment
```java
ChatView chatView = (ChatView) findViewById(R.id.chatview);
```
Sample code
```java
//sample code to add message to right
Message message = new Message();
                    message.setBody(messageET.getText().toString().trim()); //message body
                    message.setType(Message.RightSimpleMessage); //message type
                    message.setTime(getTime()); //message time (String)
                    message.setUserName("Groot"); //sender name
		    //sender icon
                    message.setUserIcon(Uri.parse("android.resource://com.shrikanthravi.chatviewlibrary/drawable/groot")); 
                    chatView.addMessage(message);
		    
//sample code to add message to left
Message message1 = new Message();
                    message1.setBody(messageET.getText().toString().trim());
                    message1.setType(Message.LeftSimpleMessage);
                    message1.setTime(getTime());
                    message1.setUserName("Hodor");
                    message1.setUserIcon(Uri.parse("android.resource://com.shrikanthravi.chatviewlibrary/drawable/hodor"));
		    chatView.addMessage(message);

//to clear messages
chatview.clearMessages();

//to remove message
chatview.remove(position) //or  chatview.remove(message)

		    
```
Message types
```java
//Message.LeftSimpleMessage
	Message message = new Message();
	message.setType(Message.LeftSimpleMessage);
	message.setBody(body); //string
	message.setTime(getTime());
	chatView.addMessage(message);
	
//Message.RightSimpleMessage
	Message message = new Message();
	message.setType(Message.RightSimpleMessage);
	message.setBody(body); //string
	message.setTime(getTime());
	chatView.addMessage(message);
	
//Message.LeftSingleImage  (when clicked it will go to fullscreen mode)
	Message message = new Message();
	List<Uri> mSelected  = new ArrayList<>();
	mselected.add(Uri.parse("Your image"))
	message.setTime(getTime());
	message.setType(Message.LeftSingleImage);
	message.setImageList(mSelected);
	chatView.addMessage(message);	
	
// Message.RightSingleImage (when clicked it will go to fullscreen mode)
	Message message = new Message();
	List<Uri> mSelected  = new ArrayList<>();
	mselected.add(Uri.parse("Your image"));
	message.setTime(getTime());
	message.setType(Message.RightSingleImage);
	message.setImageList(mSelected);	
	chatView.addMessage(message);
	
// Message.LeftMultipleImages (can hold upto 10 images in a single row as a collage view)
	Message message = new Message();
	List<Uri> mSelected  = new ArrayList<>();
	mselected.add(Uri.parse("Your image"));
	mselected.add(Uri.parse("Your image"));
	mselected.add(Uri.parse("Your image"));	
	message.setTime(getTime());
	message.setType(Message.LeftMultipleImages);
	message.setImageList(mSelected);
	chatView.addMessage(message);
	
// Message.RightMultipleImages (can hold upto 10 images in a single row as a collage view)
	Message message = new Message();
	List<Uri> mSelected  = new ArrayList<>();
	mselected.add(Uri.parse("Your image"));
	mselected.add(Uri.parse("Your image"));
	mselected.add(Uri.parse("Your image"));	
	message.setTime(getTime());
	message.setType(Message.RightMultipleImages);
	message.setImageList(mSelected);
	chatView.addMessage(message);
	
// Message.LeftVideo (when clicked it will go to fullscreen mode)	
	Message message = new Message();
	message.setType(Message.LeftVideo);
        message.setTime(getTime());
	message.setUserName("Hodor");
	message.setVideoUri(Uri.parse(getPath(data.getData())));
	message.setUserIcon(Uri.parse("android.resource://com.shrikanthravi.chatviewlibrary/drawable/hodor"));
	hatView.addMessage(message);

// Message.RightVideo (when clicked it will go to fullscreen mode)
	Message message = new Message();
	message.setType(Message.RightVideo);
	message.setTime(getTime());
	message.setUserName("Groot");
	message.setVideoUri(Uri.parse(getPath(data.getData())));
	message.setUserIcon(Uri.parse("android.resource://com.shrikanthravi.chatviewlibrary/drawable/groot"));
	chatView.addMessage(message);	
```
### Sender Layout
<img src="https://raw.githubusercontent.com/shrikanth7698/ChatView/master/Sender_Layout.png" width="400" >

You should have a accent color in your colors.xml otherwise view will crash.


You can hide or show sender layout

```xml

app:showSenderLayout="true or false"

```


This is still in beta

Sender Layout has 4 buttons for now.

* Send Button

* Camera Button

* Gallery Button

* Video Button

and a Text box

You can change the color of buttons by changing the accent color you use in your app.

Note: You should use your own logic of picking images,videos and capturing images.

In this sample I used an external library to pick images from gallery.

```java
	//Send button click listerer
        chatView.setOnClickSendButtonListener(new ChatView.OnClickSendButtonListener() {
            @Override
            public void onSendButtonClick(String body) {
                if(switchbool) {
                    Message message = new Message();
                    message.setBody(body);
                    message.setType(Message.RightSimpleMessage);
                    message.setTime(getTime());
                    message.setUserName("Groot");
                    message.setUserIcon(Uri.parse("android.resource://com.shrikanthravi.chatviewlibrary/drawable/groot"));
                    chatView.addMessage(message);

                    switchbool=false;
                }
                else{
                    Message message1 = new Message();
                    message1.setBody(body);
                    message1.setType(Message.LeftSimpleMessage);
                    message1.setTime(getTime());
                    message1.setUserName("Hodor");
                    message1.setUserIcon(Uri.parse("android.resource://com.shrikanthravi.chatviewlibrary/drawable/hodor"));
                    chatView.addMessage(message1);

                    switchbool=true;
                }
            }
        });

        //Gallery button click listener
        chatView.setOnClickGalleryButtonListener(new ChatView.OnClickGalleryButtonListener() {
            @Override
            public void onGalleryButtonClick() {
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

        //Video button click listener
        chatView.setOnClickVideoButtonListener(new ChatView.OnClickVideoButtonListener() {
            @Override
            public void onVideoButtonClick() {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                i.setType("video/*");
                startActivityForResult(i, SELECT_VIDEO);
            }
        });

        //Camera button click listener
        chatView.setOnClickCameraButtonListener(new ChatView.OnClickCameraButtonListener() {
            @Override
            public void onCameraButtonClicked() {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                File file = new File(Environment.getExternalStorageDirectory(), "MyPhoto.jpg");
                file.delete();
                File file1 = new File(Environment.getExternalStorageDirectory(), "MyPhoto.jpg");

                Uri uri = FileProvider.getUriForFile(ChatViewTestActivity.this, getApplicationContext().getPackageName() + ".provider", file1);
                cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
	
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Image Selection result
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
            else {

            //Video Selection result
            if (requestCode == SELECT_VIDEO && resultCode == RESULT_OK) {


                if (switchbool) {
                    Message message = new Message();
                    message.setType(Message.RightVideo);
                    message.setTime(getTime());
                    message.setUserName("Groot");
                    message.setVideoUri(Uri.parse(getPath(data.getData())));
                    message.setUserIcon(Uri.parse("android.resource://com.shrikanthravi.chatviewlibrary/drawable/groot"));
                    chatView.addMessage(message);
                    switchbool = false;
                } else {
                    Message message = new Message();

                    message.setType(Message.LeftVideo);
                    message.setTime(getTime());
                    message.setUserName("Hodor");
                    message.setVideoUri(Uri.parse(getPath(data.getData())));
                    message.setUserIcon(Uri.parse("android.resource://com.shrikanthravi.chatviewlibrary/drawable/hodor"));
                    chatView.addMessage(message);
                    switchbool = true;
                }
            }
            else{

                //Image Capture result
                if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {


                    if (switchbool) {
                        Message message = new Message();
                        message.setType(Message.RightSingleImage);
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

                        message.setType(Message.LeftSingleImage);
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

            }


            }


        }
```
### Customization
<img src="https://raw.githubusercontent.com/shrikanth7698/ChatView/master/Explanation%20Screenshot%202.png" width="300">

Note: You can customize the chatview in both xml and java way.
```xml
//xml 

<com.shrikanthravi.chatview.widget.ChatView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
	app:showLeftBubbleIcon="true or false" //  (4)
	app:showRightBubbleIcon="true or false" //  (2)
        app:showSenderLayout="false" // still in beta (set the value to false)
        app:leftBubbleLayoutColor="#ff7b7b"  //   (5)
        app:rightBubbleLayoutColor="@color/colorAccent2" //   (3)
        app:leftBubbleTextColor="@android:color/white" // (5)
        app:rightBubbleTextColor="@android:color/black" //  (3)
        android:layout_above="@+id/sendLL"
	app:textSize="text size"
	app:chatViewBackground="your color"  //   (10)
	app:timeTextColor="your color" 	//  (11)
        app:showSenderName="true" //  (1)
        android:id="@+id/chatView">
	
```

```java
//java


//Message.LeftSingleImage      (7)
	List<Uri> mSelected  = new ArrayList<>();
	mselected.add(Uri.parse("Your image"))
	message.setType(Message.LeftSingleImage);
	message.setImageList(mSelected);
	
//Message.RightSingleImage    (6)
	List<Uri> mSelected  = new ArrayList<>();
	mselected.add(Uri.parse("Your image"));
	message.setType(Message.RightSingleImage);
	message.setImageList(mSelected);	
	
//Message.LeftMultipleImages (can hold upto 10 images in a single row as a collage view)   (9)
	List<Uri> mSelected  = new ArrayList<>();
	mselected.add(Uri.parse("Your image"));
	mselected.add(Uri.parse("Your image"));
	mselected.add(Uri.parse("Your image"));	
	message.setType(Message.LeftMultipleImages);
	message.setImageList(mSelected);	
	
//Message.RightMultipleImages (can hold upto 10 images in a single row as a collage view)   (8)
	List<Uri> mSelected  = new ArrayList<>();
	mselected.add(Uri.parse("Your image"));
	mselected.add(Uri.parse("Your image"));
	mselected.add(Uri.parse("Your image"));	
	message.setType(Message.RightMultipleImages);
	message.setImageList(mSelected);		


```
### Features
*  Supports Group mode (Like whatsapp group).
*  Supports multiple images in a single row.
*  Video preview
*  Easily customizable.

Check the sample app https://github.com/shrikanth7698/ChatView/tree/master/app

## Upcoming features
* Video Support (Completed)
* Location Support
* Contact support
## Contributions are welcome

### License
```
MIT License

Copyright (c) 2018 Shrikanth Ravi

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
