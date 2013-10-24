package com.clearblade.clearchat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

public class Splash extends Activity {

	private final int SPLASH_DISPLAY_LENGTH = 2000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);
        loadPreferences();
    }
    
	// Method to load username in a preference
	private void loadPreferences() {
		        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		        String retrievedUsername = sharedPreferences.getString("storedName","");
		        
		        if (retrievedUsername.length() > 1) {
		        	
		        	Intent intent = new Intent(Splash.this,Landing.class);
					// Start the Landing activity
					startActivity(intent);
		        	
		        } else {
		            /* New Handler to start the Menu-Activity 
		             * and close this Splash-Screen after some seconds.*/
		            new Handler().postDelayed(new Runnable(){
		                @Override
		                public void run() {
		                    /* Create an Intent that will start the Menu-Activity. */
		                    Intent mainIntent = new Intent(Splash.this,MainActivity.class);
		                    Splash.this.startActivity(mainIntent);
		                    Splash.this.finish();
		                }
		            }, SPLASH_DISPLAY_LENGTH);
		        }
		    }
}