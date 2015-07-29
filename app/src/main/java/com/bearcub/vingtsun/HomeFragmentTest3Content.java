package com.bearcub.vingtsun;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Home on 7/22/2015.
 */
public class HomeFragmentTest3Content extends Fragment {
    private static final String KEY_TITLE = "title";
    private static final String KEY_INDICATOR_COLOR = "indicator_color";
    private static final String KEY_DIVIDER_COLOR = "divider_color";

    public static HomeFragmentTest3Content newInstance(CharSequence title, int indicatorColor,
                                                       int dividerColor){
        Bundle bundle = new Bundle();
        bundle.putCharSequence(KEY_TITLE, title);
        bundle.putInt(KEY_INDICATOR_COLOR, indicatorColor);
        bundle.putInt(KEY_DIVIDER_COLOR, dividerColor);

        HomeFragmentTest3Content fragment = new HomeFragmentTest3Content();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pager_item_home_test_3, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();

        if (args != null) {
            TextView title = (TextView) view.findViewById(R.id.item_title_home_3);
            title.setText("Title: " + args.getCharSequence(KEY_TITLE));

            int indicatorColor = args.getInt(KEY_INDICATOR_COLOR);
            TextView indicatorColorView = (TextView) view.findViewById(R.id.item_indicator_color_home_3);
            indicatorColorView.setText("Indicator: #" + Integer.toHexString(indicatorColor));
            indicatorColorView.setTextColor(indicatorColor);

            int dividerColor = args.getInt(KEY_DIVIDER_COLOR);
            TextView dividerColorView = (TextView) view.findViewById(R.id.item_divider_color_home_3);
            dividerColorView.setText("Divider: #" + Integer.toHexString(dividerColor));
            dividerColorView.setTextColor(dividerColor);
        }
    }
}
