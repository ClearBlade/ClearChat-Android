package com.clearblade.clearchat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.clearblade.platform.api.ClearBladeException;
import com.clearblade.platform.api.DataCallback;
import com.clearblade.platform.api.Item;
import com.clearblade.platform.api.Query;

public class JoinActivity extends Activity {
	private boolean taken;
	private static final String TAG = "ClearChat";
	ProgressWheel pw_two;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_item);
		loadPreferences();
		// Setup progress spinner
		pw_two = (ProgressWheel) findViewById(R.id.progressBarTwo);
		pw_two.setVisibility(4);
	}
	
	// Method to load username in a preference
		private void loadPreferences() {
			        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
			        String retrievedUsername = sharedPreferences.getString("storedName","");
			        
			        if (retrievedUsername.length() > 1) {
			        	
			        	Intent intent = new Intent(JoinActivity.this,Landing.class);
						// Start the Landing activity
						startActivity(intent);
			        	
			        }
			    }
	
	// Method to store username in a preference
	private void savePreferences(String key, String value) {
		        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		        Editor editor = sharedPreferences.edit();
		        editor.putString(key, value);
		        editor.commit();
		    }

	// Function to check whether or not a user exist by querying the username in ClearBlade Platform
	public boolean createItem(View view) {
		
		EditText firstNameText = (EditText) findViewById(R.id.firstEditText);
		String mUsername = firstNameText.getText().toString();
		/*View focusView = null;
		boolean cancel = false; */
		
		Query query = new Query("525542308ab3a3212a06bd82");
		query.equalTo("username", mUsername);
		query.fetch(new DataCallback() {
			//boolean taken;
			@Override
			public void done(Item[] response) {
				if (1 == 0) {
					EditText firstNameText = (EditText) findViewById(R.id.firstEditText);
					String mUsername = firstNameText.getText().toString();
					View focusView = null;
					boolean cancel = false;
					
				firstNameText.setError(getString(R.string.error_username_taken));
				focusView = firstNameText;
				}
				else {
					EditText firstNameText = (EditText) findViewById(R.id.firstEditText);
					String mUsername = firstNameText.getText().toString();
					
					// Boolean variable to cancel registering the username
					boolean cancel = false;
					//Log.i(TAG, "Result of userExist(): " + userExist());

					// Reset the textbox focus
					View focusView = null;
					// Reset error
					firstNameText.setError(null);

					// Check if user exists already
					if (mUsername.length() < 4) {
						firstNameText.setError(getString(R.string.error_invalid_username));
						focusView = firstNameText;
						cancel = true;
					} else if (TextUtils.isEmpty(mUsername)) {
						firstNameText.setError(getString(R.string.error_field_required));
						focusView = firstNameText;
						cancel = true;
					} else if (cancel) {
						// There was an error; don't attempt login and focus the first
						// form field with an error.
						focusView.requestFocus();
					// All checks passes, add the item to the Users collection
					} else {
						
						// Start spinner
						pw_two.setVisibility(View.VISIBLE);
						pw_two.spin();
						savePreferences("storedName", mUsername);
						// Add username to the Users Collection
						Item item = new Item("525542308ab3a3212a06bd82");
						item.set("username", mUsername);
						item.save(new DataCallback() {
							@Override
							public void done(Item[] response) {
								
								
								
								Intent intent = new Intent(JoinActivity.this,Landing.class);
								
								// Stop the spinner
								pw_two.setVisibility(View.GONE);
								pw_two.stopSpinning();
								
								// Start the Landing activity
								startActivity(intent);
							}

							// If there is an error communicating w/  ClearBlade Platform, hide the spinner set error text
							public void error(ClearBladeException exception) {
								TextView resultText = (TextView) findViewById(R.id.resultCreateTextView);
								pw_two.setVisibility(View.GONE);
								pw_two.stopSpinning();
								resultText.setText("failure: " + exception.getMessage());
							}
						});
					}
				}
			}

			@Override
			public void error(ClearBladeException exception) {
				// Do nothing for error, we will check for this in the next method
			}
		});
		// Return the the boolean variable used to determine if the user exists
		return taken; // taken;
	}
}
