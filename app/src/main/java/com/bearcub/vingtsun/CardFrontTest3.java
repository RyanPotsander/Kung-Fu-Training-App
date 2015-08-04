package com.bearcub.vingtsun;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Home on 7/20/2015.
 */
public class CardFrontTest3 extends Fragment implements FormsFragment.OnRecyclerItemSelectedListener {
    ImageView mFocusImageView;

    public CardFrontTest3(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_front_card_3, container, false);

        mFocusImageView = (ImageView)view.findViewById(R.id.image_view_card_test_3);

        setFocusImage(R.drawable.slt1);

        return view;
    }

    @Override
    public void setFocusImage(int id) {
        mFocusImageView.setImageResource(id);
    }
}

