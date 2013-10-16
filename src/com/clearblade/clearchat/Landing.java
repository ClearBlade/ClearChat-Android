package com.clearblade.clearchat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
 
public class Landing extends FragmentActivity {
	
    final String[] data ={"Home", "My Groups","Search","Create New Group"};
    final String[] fragments ={
    		"com.clearblade.clearchat.MainFragment",
    		"com.clearblade.clearchat.MyGroupsFragment",
            "com.clearblade.clearchat.SearchFragment",
            "com.clearblade.clearchat.CreateGroupFragment"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_landing);
 
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
 
}