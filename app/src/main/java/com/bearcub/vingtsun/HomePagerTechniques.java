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
public class HomePagerTechniques extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_pager_techniques, container, false);
    }

    public HomePagerTechniques(){}

    public static HomePagerTechniques newInstance(){
        return new HomePagerTechniques();
    }
}
