package com.clearblade.clearchat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.clearblade.platform.api.AbstractMessageCallback;
import com.clearblade.platform.api.ClearBladeException;
import com.clearblade.platform.api.DataCallback;
import com.clearblade.platform.api.Item;
import com.clearblade.platform.api.Message;
import com.clearblade.platform.api.MessageCallback;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
 
public class Landing extends FragmentActivity implements MessageCallback {
	private static final String TAG = "MALCOLM LOG"; 
	
	ProgressWheel pw_two;
	EditText groupNameText;
	Message message;
	//ListView list;
	
    final String[] data ={"Home", "My Groups","Search","Create New Group"};
    final String[] fragments ={
    		"com.clearblade.clearchat.MainFragment",
    		"com.clearblade.clearchat.MyGroupsFragment",
            "com.clearblade.clearchat.SearchFragment",
            "com.clearblade.clearchat.CreateGroupFragment",
            "com.clearblade.clearchat.ChatFragment",
            "com.clearblade.clearchat.JoinGroupFragment",
            "com.clearblade.clearchat.FindGroupFragment",
            "com.clearblade.clearchat.FindUsersFragment"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_landing);
         
         //list = (ListView) findViewById(R.id.listViewFromDB);
         
         if (message== null){
 			message = new Message(this);
 		}
 	
         ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActionBar().getThemedContext(), android.R.layout.simple_list_item_1, data);
 
         final DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
   
         loadPref();
         
         final ListView navList = (ListView) findViewById(R.id.drawer);
         navList.setAdapter(adapter);
         navList.setOnItemClickListener(new OnItemClickListener(){
                 @Override
                 public void onItemClick(AdapterView<?> parent, View view, final int pos,long id){
                         drawer.setDrawerListener( new DrawerLayout.SimpleDrawerListener(){
                                 @Override
                                 public void onDrawerClosed(View drawerView){
                                         super.onDrawerClosed(drawerView);
                                         FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
                                         tx.replace(R.id.main, Fragment.instantiate(Landing.this, fragments[pos]));                                      
                                         tx.commit();
                                 }
                         });
                         drawer.closeDrawer(navList);
                 }
         });
         FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
         tx.replace(R.id.main,Fragment.instantiate(Landing.this, fragments[0]));
         tx.commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.landing, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

     /*
      * Because it's onlt ONE option in the menu.
      * In order to make it simple, We always start SetPreferenceActivity
      * without checking.
      */
     
     Intent intent = new Intent();
           intent.setClass(Landing.this, SetPreferenceActivity.class);
           startActivityForResult(intent, 0); 
     
           return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
     // TODO Auto-generated method stub
     //super.onActivityResult(requestCode, resultCode, data);
     
     /*
      * To make it simple, always re-load Preference setting.
      */
     
     loadPref();
    }
    private void loadPref(){
    	  SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    	  
    	  boolean my_checkbox_preference = mySharedPreferences.getBoolean("checkbox_preference", false);
    	  //prefCheckBox.setChecked(my_checkbox_preference);

    	

    	 }
    
    public void joinGroup(View view) {
	//go to MyGroups Fragment
		FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
    tx.replace(R.id.main,Fragment.instantiate(Landing.this, fragments[4]));
    tx.commit();
    }
    
    public void findGroup(View view) {
    	//go to MyGroups Fragment
    		FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.main,Fragment.instantiate(Landing.this, fragments[5]));
        tx.commit();
        }
    
    public void findUsers(View view) {
    	//go to MyGroups Fragment
    		FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.main,Fragment.instantiate(Landing.this, fragments[6]));
        tx.commit();
        }
    
    public void chat(View view) {
    	//go to MyGroups Fragment
    	final View currentView = view;
    		FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.main,Fragment.instantiate(Landing.this, fragments[4]));
        tx.commit();
        
        message.subscribe("test",new AbstractMessageCallback() {
 		    @Override
 		    public void done(String topic, String message) {
 		      Landing.this.done(topic, message, currentView);
 		    }
 		  });	
        
        }
    
    
    
    public void CopyDB(InputStream inputStream, OutputStream outputStream) throws IOException {
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = inputStream.read(buffer)) > 0) {
    		outputStream.write(buffer, 0, length);
    	}
    	inputStream.close();
    	outputStream.close();
    	
    }
    
	public void publishClick(View view){
		
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String retrievedUsername = sharedPreferences.getString("storedName","");

		EditText publishText = (EditText) findViewById(R.id.publishEditText);

		//TODO get time, format it, package it with the message
		
		message.publish("test", retrievedUsername + ": " + publishText.getText().toString());
	}
	
	public void done(String topic, String message, View view) {
		
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String retrievedUsername = sharedPreferences.getString("storedName","");
 
		TextView resultText = (TextView) findViewById(R.id.messageTextView);
		if(message.toLowerCase().contains(retrievedUsername.toLowerCase())){
			//resultText.setBackgroundResource(android.R.color.holo_blue_bright);
			//resultText.setTypeface(null,Typeface.BOLD);
			resultText.append("\n" + message);
			
		} else {
			final MediaPlayer mp1=MediaPlayer.create(getBaseContext(), R.raw.pop);  
	        mp1.start();
		//resultText.setBackgroundResource(android.R.color.holo_green_light);
	        //resultText.setTypeface(null,Typeface.NORMAL);
	        resultText.append("\n" + message);	
		}
	}
	@Override
	public void error(ClearBladeException exception) {
		// TODO Auto-generated method stub
		
	}
 
	public void createGroup(View view) {
		pw_two = (ProgressWheel) findViewById(R.id.progressBarTwo);
  		pw_two.setVisibility(4);
  		pw_two.setVisibility(View.GONE);
		
		EditText groupNameText = (EditText) findViewById(R.id.groupNameEditText);
       			String mGroupname = groupNameText.getText().toString();

       			// Boolean variable to cancel creating group
       			boolean cancel = false;

       			// Reset the textbox focus
       			View focusView = null;
       			// Reset error
       			groupNameText.setError(null);

       			if (mGroupname.length() < 4) {
       				groupNameText.setError(getString(R.string.error_invalid_username));
       				focusView = groupNameText;
       				cancel = true;
       			} else if (TextUtils.isEmpty(mGroupname)) {
       				groupNameText.setError(getString(R.string.error_field_required));
       				focusView = groupNameText;
       				cancel = true;
       			} else if (cancel) {
       				// There was an error; don't attempt create group and focus the group name
       				// form field with an error.
       				focusView.requestFocus();
       			// All checks passes, add the item to the Groups collection
       			} else {
       				
       				// Start spinner
       				pw_two.setVisibility(View.VISIBLE);
       				pw_two.spin();
       				
       				try {
       					String destPath = "/data/data/" + getPackageName() + "/databases/GroupsDB";
       					File f = new File(destPath);
       					if(!f.exists()) {
       						CopyDB( getBaseContext().getAssets().open("GroupsDB"),
       						new FileOutputStream(destPath));
       					}
       				}catch (FileNotFoundException e) {
       					e.printStackTrace();
       				}catch (IOException e) {
       					e.printStackTrace();
       				}

       				DBAdapter db = new DBAdapter(this);
       				
       				//try hard coding the record here, if unable to insertRecord for any reason it will return -1
       				db.open();
       				long id = db.insertRecord(mGroupname);
       				Log.i(TAG, "Inserting record...");
       				Log.i(TAG, "mGroupName = " + mGroupname);
       				Log.i(TAG, "long id = " + id);
       				populateListViewFromDB(db);
       				db.close();
       				
       				
       				// Add group name to the Groups Collection
       				Item item = new Item("525bf8e48ab3a3212a06bd83");
       				item.set("groupName", mGroupname);
       				item.save(new DataCallback() {
       					@Override
       					public void done(Item[] response) {      						
       						// Stop the spinner
       						pw_two.setVisibility(View.GONE);
       						pw_two.stopSpinning();

       						//go to MyGroups Fragment
       						FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
       			         tx.replace(R.id.main,Fragment.instantiate(Landing.this, fragments[1]));
       			         tx.commit();
       					}

       					// If there is an error communicating w/  ClearBlade Platform, hide the spinner set error text
       					public void error(ClearBladeException exception) {
       						//TODO: handle error
       						pw_two.setVisibility(View.GONE);
       						pw_two.stopSpinning();
       						
       					}
       				});
       			}	
	}
	
	@Override
	public void done(String topic, String message) {
		// TODO Auto-generated method stub
		
	}
	
	private void populateListViewFromDB(DBAdapter db) {
		Cursor cursor = db.getAllRecords();	
		
		//Allow activity to manage lifetime of the cursor
		startManagingCursor(cursor);
		
		//setup mappings from cursor to view fields
		String[] fromFieldNames = new String[]
				{DBAdapter.KEY_GROUP_NAME};
		
		int[] toViewIDs = new int[]
				{R.id.item_group_name};
		
		
		//create adapter to map columns of the database to elements in the UI
		SimpleCursorAdapter myCursorAdapter = 
			new SimpleCursorAdapter(
				this, 
				R.layout.item_layout, 
				cursor, 
				fromFieldNames, 
				toViewIDs
				);
		
		//set adapter for the list view
		ListView myList = (ListView) findViewById(R.id.listViewFromDB);
		myList.setAdapter(myCursorAdapter);
	} 
	
}




















