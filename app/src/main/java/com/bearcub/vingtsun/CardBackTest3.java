package com.bearcub.vingtsun;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Home on 7/20/2015.
 */
public class CardBackTest3 extends Fragment{
    TextView cardContentView;

    public CardBackTest3(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.test_back_card_3, container, false);
        cardContentView = (TextView)view.findViewById(R.id.text_view_card_back_test_3);

        setCardContent();

        return view;
    }

    private void setCardContent(){
        String content = "Details about the selected technique will go here. Maybe other buttons, options, etc.";
        cardContentView.setText(content);
    }
}
