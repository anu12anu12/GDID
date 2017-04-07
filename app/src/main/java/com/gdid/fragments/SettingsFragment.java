package com.gdid.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gdid.material_management.R;

/**
 * Created by anupamsi on 1/4/2017.
 */
public class SettingsFragment extends Fragment {
    public SettingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settings, container, false);
        return rootView;
    }

}
