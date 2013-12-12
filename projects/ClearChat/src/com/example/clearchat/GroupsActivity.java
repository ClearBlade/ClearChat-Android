package com.example.clearchat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.clearblade.platform.api.ClearBladeException;
import com.clearblade.platform.api.Collection;
import com.clearblade.platform.api.DataCallback;
import com.clearblade.platform.api.Item;

public class GroupsActivity extends Activity {

	public static String GROUPCOLLECTIONID = "98cad0aa0ae8f3e4f888bcdeb29701";
	SimpleAdapter simpleAdpt;
	// The data to show
	List<Map<String, String>> groupList = new ArrayList<Map<String, String>>();
	String userName = "";
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_groups);
		
		Intent intent = getIntent();
		userName = intent.getStringExtra("userName");
		TextView welcomeText = (TextView) findViewById(R.id.groupwelcome);
		welcomeText.setText("Welcome: "+userName);
		
		//pull the full list of groups in the ClearBlade platform
		initList();

		// We get the ListView component from the layout
		ListView lv = (ListView) findViewById(R.id.listView);

		simpleAdpt = new SimpleAdapter(this, groupList,
				android.R.layout.simple_list_item_1, new String[] { "group" },
				new int[] { android.R.id.text1 });

		lv.setAdapter(simpleAdpt);
		// React to user clicks on item
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

		     public void onItemClick(AdapterView<?> parentAdapter, View view, int position,
		                             long id) {

		         // We know the View is a TextView so we can cast it
		         TextView clickedView = (TextView) view;
		         
		         Intent intent = new Intent(GroupsActivity.this, ChatActivity.class);
		         intent.putExtra("groupName", clickedView.getText().toString());
		         intent.putExtra("userName", userName);
		         startActivity(intent);
		     }
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.groups, menu);
		return true;
	}

	private void initList() {
		Collection collection = new Collection(GROUPCOLLECTIONID);
		collection.fetchAll(new DataCallback(){

			@Override
			public void done(Item[] response) {
				for (int i = 0; i < response.length; i++) {
					HashMap<String, String> group = new HashMap<String, String>();
					group.put("group", response[i].getString("groupname"));
					groupList.add(group);
				}
				simpleAdpt.notifyDataSetChanged();
			}
		});
	}
	
	public void createGroup(View view){
		final EditText groupNameText = (EditText) findViewById(R.id.groupname);
		Item item = new Item(GROUPCOLLECTIONID);
		item.set("groupname",groupNameText.getText().toString());
		item.save(new DataCallback(){

			@Override
			public void done(Item[] response) {
				 Intent intent = new Intent(GroupsActivity.this, ChatActivity.class);
		         intent.putExtra("groupName", groupNameText.getText().toString());
		         startActivity(intent);
			}

			@Override
			public void error(ClearBladeException exception) {
				super.error(exception);
			}
			
		});

	}

}
