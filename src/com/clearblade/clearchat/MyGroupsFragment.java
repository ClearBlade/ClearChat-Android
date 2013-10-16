package com.clearblade.clearchat;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
 
public class MyGroupsFragment extends Fragment {
 
    public static Fragment newInstance(Context context) {
        MainFragment f = new MainFragment();
 
        return f;
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_my_groups, null);
        return root;
    }
 
}