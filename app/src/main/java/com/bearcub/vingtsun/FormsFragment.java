package com.bearcub.vingtsun;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bearcub.recyclerviewlibrary.BaseRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Home on 7/20/2015.
 */
public class FormsFragment extends BaseRecyclerView implements View.OnClickListener{
    private static final String TAG =FormsFragment.class.getSimpleName();
    private static final String INFO_BUTTON_TAG = "info_button";
    private static final String TRAIN_BUTTON_TAG = "train_button";
    private static final String VIDEO_BUTTON_TAG = "video_button";
    private static final String EXPAND_BUTTON_TAG = "expand_button";
    private static final String FRONT_CARD_FRAGMENT_TAG = "front_card";
    private static final String BACK_CARD_FRAGMENT_TAG = "back_card";
    private RecyclerView mRecyclerView;
    private FormsFragmentTest3Adapter mAdapter;
    private CardFrontTest3 mFrontCardFragment;
    private boolean mShowingBack;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forms, container, false);

        setUpRecyclerView((RecyclerView) view.findViewById(R.id.recycler_horizontal_cards));

        setUpButtons(view);

        setCardFragmentFront();

        //setFocusCard(0);

        return view;
    }

    public static FormsFragment newInstance(){
        return new FormsFragment();
    }

    public FormsFragment(){
        //required empty constructor
    }

    private int getRecyclerViewOrientation(){
        int mLinearLayoutOrientation;
        int orientation = getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            mLinearLayoutOrientation = LinearLayoutManager.VERTICAL;
        }else{
            mLinearLayoutOrientation = LinearLayoutManager.HORIZONTAL;
        }
        return mLinearLayoutOrientation;
    }

    private void setUpRecyclerView(RecyclerView view){
        mRecyclerView = view;
        mAdapter = new FormsFragmentTest3Adapter(getActivity(), getItems());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(getRecyclerViewOrientation());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addOnItemTouchListener(new BaseRecyclerView.ExtTouchListener(getActivity(), mRecyclerView, new BaseRecyclerView.ExtClickListener() {
            @Override
            public void onClick(View view, int position) {
                setFocusCard(position);
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getActivity(), "long clicked position is " + position, Toast.LENGTH_SHORT).show();
            }
        }));
    }

    private void setUpButtons(View view){
        ImageButton mInfoButton, mTrainButton, mVideoButton, mExpandButton;

        mInfoButton = (ImageButton)view.findViewById(R.id.button_forms_card_info_3);
        mInfoButton.setOnClickListener(this);
        mInfoButton.setTag(INFO_BUTTON_TAG);

        mTrainButton = (ImageButton)view.findViewById(R.id.button_forms_card_train_3);
        mTrainButton.setOnClickListener(this);
        mTrainButton.setTag(TRAIN_BUTTON_TAG);

        mVideoButton = (ImageButton)view.findViewById(R.id.button_forms_card_video_3);
        mVideoButton.setOnClickListener(this);
        mVideoButton.setTag(VIDEO_BUTTON_TAG);

        mExpandButton = (ImageButton)view.findViewById(R.id.button_forms_card_expand_3);
        mExpandButton.setOnClickListener(this);
        mExpandButton.setTag(EXPAND_BUTTON_TAG);
    }

    private void setCardFragmentFront(){
        if(mFrontCardFragment==null){
            mFrontCardFragment = new CardFrontTest3();
        }

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.image_forms_container, mFrontCardFragment, FRONT_CARD_FRAGMENT_TAG);

        transaction.commit();
        mShowingBack = false;
    }

    private void flipCard() {
        if (mShowingBack) {
            getChildFragmentManager().popBackStack();
            mShowingBack = false;
            return;
        }

        mShowingBack = true;

        getChildFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in, R.animator.card_flip_left_out)
                .replace(R.id.image_forms_container, new CardBackTest3())
                .addToBackStack(null)
                .commit();
    }

    /**
     public void setCardFragment(CardFrontFragment fragment){
     FragmentManager fm = getChildFragmentManager();
     mFrontFragment = fragment;
     fm.beginTransaction().add(R.id.container_card_fragment, fragment).commit();
     } */

    private void setFocusCard(int position){
        mFrontCardFragment.setFocusImage(mAdapter.list.get(position).imageId);
    }


    private List<FormsFragmentTestItem> getItems(){
        List<FormsFragmentTestItem> list = new ArrayList<>();
        String[] strings = {"slide 1", "slide 2", "slide 3", "slide 4", "slide 5", "slide 6"};
        int[] images = {R.drawable.slt1, R.drawable.slt2, R.drawable.slt3, R.drawable.slt4, R.drawable.slt5, R.drawable.slt6};

        for (int i = 0; i < strings.length && i < images.length; i++){
            FormsFragmentTestItem item = new FormsFragmentTestItem();
            item.label = strings[i];
            item.imageId = images[i];
            list.add(item);
        }
        return list;
    }

    //onclick for card buttons
    @Override
    public void onClick(View v) {
        switch(v.getTag().toString()){
            case INFO_BUTTON_TAG:
                flipCard();
                Toast.makeText(getActivity(), "clicked " + INFO_BUTTON_TAG, Toast.LENGTH_SHORT).show();
                break;
            case TRAIN_BUTTON_TAG:
                Toast.makeText(getActivity(), "clicked " + TRAIN_BUTTON_TAG, Toast.LENGTH_SHORT).show();
                break;
            case VIDEO_BUTTON_TAG:
                Toast.makeText(getActivity(), "clicked " + VIDEO_BUTTON_TAG, Toast.LENGTH_SHORT).show();
                break;
            case EXPAND_BUTTON_TAG:
                Toast.makeText(getActivity(), "clicked " + EXPAND_BUTTON_TAG, Toast.LENGTH_SHORT).show();
        }
    }

    private class FormsFragmentTest3Adapter extends RecyclerView.Adapter<FormsFragmentTest3Adapter.MyViewHolder> {
        private final String TAG = FormsFragmentTest3Adapter.class.getSimpleName();
        private final LayoutInflater inflater;
        List<FormsFragmentTestItem> list = Collections.emptyList();
        Context context;

        public FormsFragmentTest3Adapter(Context context, List<FormsFragmentTestItem> list) {
            inflater = LayoutInflater.from(context);
            this.list = list;
            this.context = context;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = inflater.inflate(R.layout.item_forms_test, viewGroup, false);

            MyViewHolder holder = new MyViewHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder viewHolder, int position) {
            FormsFragmentTestItem currentItem = list.get(position);
            //viewHolder.label.setText(currentItem.label);
            viewHolder.imageView.setImageResource(currentItem.imageId);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            //TextView label;

            public MyViewHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.image_forms_horizontal_2);
                //label = (TextView)itemView.findViewById(R.id.text_forms_horizontal);
            }
        }
    }

    public interface OnRecyclerItemSelectedListener{
        void setFocusImage(int id);
    }
}
