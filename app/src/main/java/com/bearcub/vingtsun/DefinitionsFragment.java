package com.bearcub.vingtsun;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bearcub.recyclerviewlibrary.BaseRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Home on 7/16/2015.
 */
public class DefinitionsFragment extends BaseRecyclerView {
    private static final String TAG = DefinitionsFragment.class.getSimpleName();
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_definitions, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.definitions_recycler_view);
        recyclerView.setAdapter(new DefinitionsAdapter(getActivity(), getDefItems()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new ExtTouchListener(getActivity(), recyclerView, new ExtClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getActivity(), "clicked position is " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getActivity(), "long clicked position is "+ position, Toast.LENGTH_SHORT).show();
            }
        }));

        return view;
    }

    public static DefinitionsFragment newInstance(){
        return new DefinitionsFragment();
    }

    public List<DefinitionsItem> getDefItems(){
        List<DefinitionsItem> list = new ArrayList<>();
        String[] names = {"first", "second", "third", "fourth", "fifth", "sixth", "seventh"};

        for (int i = 0; i < names.length; i++){
            DefinitionsItem item = new DefinitionsItem();
            item.name = names[i];
            list.add(item);
        }
        return list;
    }

    private class DefinitionsItem {
        String name;
    }


    private class DefinitionsAdapter extends RecyclerView.Adapter<DefinitionsAdapter.MyViewHolder> {
        private static final String TAG = "definitions_adapter";
        private final LayoutInflater inflater;
        List<DefinitionsItem> list = Collections.emptyList();
        Context context;

        public DefinitionsAdapter(Context context, List<DefinitionsItem> list){
            inflater= LayoutInflater.from(context);
            this.list = list;
            this.context = context;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = inflater.inflate(R.layout.item_definitions, viewGroup, false);

            MyViewHolder holder = new MyViewHolder(view);

            Log.d(TAG, "onCreateViewHolder called");
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder viewHolder, int position) {
            Log.d(TAG, "onBindVH called");
            DefinitionsItem currentItem = list.get(position);
            viewHolder.name.setText(currentItem.name);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView name;
            //ImageView image;

            public MyViewHolder(View itemView) {
                super(itemView);
                name = (TextView)itemView.findViewById(R.id.text_view_definitions);
            }
        }
    }
}
