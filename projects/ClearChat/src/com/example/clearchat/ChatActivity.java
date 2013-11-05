package com.example.clearchat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.clearblade.platform.api.AbstractMessageCallback;
import com.clearblade.platform.api.ClearBladeException;
import com.clearblade.platform.api.Message;

public class ChatActivity extends Activity {

	Message message;
	String groupName;
	String userName;
	boolean subscribed = false;
	
	// Array adapter for the conversation thread
    private ArrayAdapter<String> chatArrayAdapter;
    private ListView chatView;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		Intent intent = getIntent();
		groupName = intent.getStringExtra("groupName");
		userName = intent.getStringExtra("userName");
		
		if (message== null){
			message = new Message(this);
		}
		subscribed=false;
		
		chatArrayAdapter = new ArrayAdapter<String>(this, R.layout.message);
        chatView = (ListView) findViewById(R.id.in);
        chatView.setAdapter(chatArrayAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chat, menu);
		return true;
	}
	
	public void sendChat(View view){
		if (!subscribed){
			message.subscribe(groupName, new AbstractMessageCallback(){

				@Override
				public void done(String topic, String messageString) {
					chatArrayAdapter.add(messageString);
				}

			});
			subscribed = true;
		}
		EditText chatText = (EditText) findViewById(R.id.edittextchat);
		String chatMessage = userName+": "+chatText.getText().toString();
		message.publish(groupName,chatMessage);
		chatText.setText("");
	}

}
