package com.clearblade.clearchat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.clearblade.platform.api.ClearBlade;
import com.clearblade.platform.api.ClearBladeException;
import com.clearblade.platform.api.DataCallback;
import com.clearblade.platform.api.Item;
import com.clearblade.platform.api.Query;


public class MainActivity extends Activity {
	public final static String APIKEY = "525542228ab3a3212a06bd81";
	public final static String APISECRET = "MNDDJ0POOIC98VTS9ZQZQ5JBQB0FKI";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ClearBlade.initialize(APIKEY, APISECRET);
		ClearBlade.setUri("https://platform.clearblade.com");
		ClearBlade.setAllowUntrusted(true);
	}

	public void initializePlatform(View view){
		Intent intent = new Intent(this, CreateItemActivity.class);
		
		intent.putExtra(APIKEY, APIKEY);
		intent.putExtra(APISECRET, APISECRET);
		startActivity(intent);
		
	}
	/*
	public void login(View view){
		
		EditText usernameText= (EditText) findViewById (R.id.et_username);
		EditText passwordText= (EditText) findViewById (R.id.et_password);
		
		Query query = new Query("525542308ab3a3212a06bd82");
        query.equalTo("username", usernameText.getText().toString());
        query.equalTo("password", passwordText.getText().toString());
        query.fetch(new DataCallback(){

			@Override
			public void done(Item[] response) {
				Intent intent = new Intent(MainActivity.this, Landing.class);
				startActivity(intent);
				
			}
			
			@Override
			public void error(ClearBladeException exception) {
				Toast.makeText(getApplicationContext(), "user not found", Toast.LENGTH_SHORT).show();
			}
        	
        	
        });
	} */

}
