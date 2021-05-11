package com.domru.smarthome;

import androidx.fragment.app.Fragment;

import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import android.os.Bundle;

public class MainFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.main_fragment, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}