package com.bearcub.vingtsun;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Home on 7/31/2015.
 */
public class SocialFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_social, container, false);
    }

    public SocialFragment(){} //required empty constructor

    public static SocialFragment newInstance(){
        return new SocialFragment();
    }
}
