package com.clearblade.clearchat;

import com.clearblade.platform.api.ClearBladeException;
import com.clearblade.platform.api.DataCallback;
import com.clearblade.platform.api.Item;
import com.clearblade.platform.api.Query;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
 
public class CreateGroupFragment extends Fragment{
	Button btn_create;
 
    public static Fragment newInstance(Context context) {
        MainFragment f = new MainFragment();
 
        return f;
        
        
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_create_new_group, null);
        return root;
  
        btn_create = (Button) findViewById(R.id.createGroupButton);
    	btn_create.setOnClickListener(new OnClickListener() {

    		@Override
    		public void onClick(View view) {
    			Toast.makeText(getActivity(), "msg msg", Toast.LENGTH_SHORT).show();
    		}
    	});
    }

}