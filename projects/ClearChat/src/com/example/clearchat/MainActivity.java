package com.example.clearchat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.clearblade.platform.api.ClearBlade;
import com.clearblade.platform.api.ClearBladeException;
import com.clearblade.platform.api.DataCallback;
import com.clearblade.platform.api.InitCallback;
import com.clearblade.platform.api.Item;
import com.clearblade.platform.api.Query;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public final static String SYSTEMKEY = "eac9d0aa0ae0dcd7b1e496f4ddde01";
    public final static String SYSTEMSECRET = "EAC9D0AA0AAAA886B5B4BBAFC6E701";
    
    public final static String USERCOLLECTIONID = "90cad0aa0ac8d8bf89ff8afea432";
    
	public void initCBAPI(View view){
		
		
		InitCallback initCallback = new InitCallback(){
	        @Override
	        public void done(boolean results){
	            //initialization successful
	            login();
	        }
	        @Override
	        public void error(ClearBladeException exception){ 
	            //initialization failed, given a ClearBladeException with the cause
	        }
	    };

	    //Initialize the platform
	    ClearBlade.initialize(SYSTEMKEY, SYSTEMSECRET, initCallback);
		

	}
	
	public void login(){
		final EditText username_text = (EditText) findViewById(R.id.username);
		Query query = new Query();
		query.setCollectionId(USERCOLLECTIONID);
		query.equalTo("username", username_text.getText().toString());
		query.fetch(new DataCallback() {
			
			@Override
			public void done(Item[] items) {
				if (items.length>0){
					//we found the user name, so lets move to the groups activity 
					navigateToGroups();
				}else {
					Item newUser = new Item(USERCOLLECTIONID);
					newUser.set("username", username_text.getText().toString());
					newUser.save(new DataCallback(){
						@Override
						public void done(Item[] items){
							navigateToGroups();
						}
					});
				}
			}

			@Override
			public void error(ClearBladeException exception) {
				super.error(exception);
			}			
		});
	}
	
	
	public void navigateToGroups(){
		Intent intent = new Intent(MainActivity.this, GroupsActivity.class);
		EditText username_text = (EditText) findViewById(R.id.username);
		intent.putExtra("userName", username_text.getText().toString());
		startActivity(intent);
	}

}
