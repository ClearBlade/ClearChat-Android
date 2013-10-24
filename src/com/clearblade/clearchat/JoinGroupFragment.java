package com.clearblade.clearchat;

import com.clearblade.platform.api.ClearBladeException;
import com.clearblade.platform.api.DataCallback;
import com.clearblade.platform.api.Item;
import com.clearblade.platform.api.Query;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
 
public class JoinGroupFragment extends Fragment{
	ProgressWheel pw_two;
 
    public static Fragment newInstance(Context context) {
        MainFragment f = new MainFragment();
        return f;  
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.fragment_join_group, container, false);
    	pw_two = (ProgressWheel) view.findViewById(R.id.progressBarTwo);
  		pw_two.setVisibility(4);
  		pw_two.setVisibility(View.GONE);
    	return view;
    }
    


}