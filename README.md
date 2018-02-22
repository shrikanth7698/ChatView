# ChatView for Android

<br/>
<img src="https://github.com/shrikanth7698/ChatView/blob/master/chatview_library_icon_1.png" width="100"/>
<br/>
This is an Android library which can be used to add chat functionality to your android application with just a few lines of code.
<br/>
This library is still in beta but will be improved over time.
<img src="https://raw.githubusercontent.com/shrikanth7698/ChatView/master/Explanation%20Screenshot%202.png" width="350"/>
### Video Support
<img src="https://github.com/shrikanth7698/ChatView/blob/master/chatview%20library%20video.gif" width="300"/>
### Version
v0.1.0
<br/>
### Installation
Add this to your root build.gradle at the end of repositories:
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
Then add dependency
```
dependencies {
	compile 'com.github.shrikanth7698:ChatView:v0.1.0'
}
```
## Usage

Drop the ChatView in your XML layout as is shown below:
```
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
```
xmlns:app="http://schemas.android.com/apk/res-auto"
```
And then in your Activity or Fragment
```
ChatView chatView = (ChatView) findViewById(R.id.chatview);
```
Sample code
```
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
```
//Message.LeftSimpleMessage
	message.setType(Message.LeftSimpleMessage);
	message.setBody(body); //string
	
//Message.RightSimpleMessage
	message.setType(Message.RightSimpleMessage);
	message.setBody(body); //string
	
//Message.LeftSingleImage
	List<Uri> mSelected  = new ArrayList<>();
	mselected.add(Uri.parse("Your image"))
	message.setType(Message.LeftSingleImage);
	message.setImageList(mSelected);
	
//Message.RightSingleImage
	List<Uri> mSelected  = new ArrayList<>();
	mselected.add(Uri.parse("Your image"));
	message.setType(Message.RightSingleImage);
	message.setImageList(mSelected);	
	
//Message.LeftMultipleImages (can hold upto 10 images in a single row as a collage view)
	List<Uri> mSelected  = new ArrayList<>();
	mselected.add(Uri.parse("Your image"));
	mselected.add(Uri.parse("Your image"));
	mselected.add(Uri.parse("Your image"));	
	message.setType(Message.LeftMultipleImages);
	message.setImageList(mSelected);	
	
//Message.RightMultipleImages (can hold upto 10 images in a single row as a collage view)
	List<Uri> mSelected  = new ArrayList<>();
	mselected.add(Uri.parse("Your image"));
	mselected.add(Uri.parse("Your image"));
	mselected.add(Uri.parse("Your image"));	
	message.setType(Message.RightMultipleImages);
	message.setImageList(mSelected);		
```
### Customization
![Library Explanation](https://raw.githubusercontent.com/shrikanth7698/ChatView/master/Explanation%20Screenshot%202.png)

Note: You can customize the chatview in both xml and java way.
```
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
*  Easily customizable.

Check the sample app https://github.com/shrikanth7698/ChatView/tree/master/app

## Upcoming features
* Video Support
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
