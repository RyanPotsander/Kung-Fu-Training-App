package com.bearcub.vingtsun;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.melnykov.fab.FloatingActionButton;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Home on 7/16/2015.
 */
public class TrainingFragment extends android.app.Fragment {
    private static final String TAG = "training_log";
    public static final String SHARED_PREFERENCES_FILE_NAME = "training_preferences";
    public static final String KEY_FILE_EXISTS = "file_exists";
    private RecyclerView mRecyclerView;
    TrainingLogAdapter adapter;
    OnTouchItemSelectedListener mCallBack;
    private List<TrainingItem> mItemList;
    boolean fileExists;


    public TrainingFragment(){}

    public static TrainingFragment newInstance(){
        //Bundle args = new Bundle();
        TrainingFragment f = new TrainingFragment();
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fileExists = Boolean.valueOf(readFromSharedPreferences(getActivity(), KEY_FILE_EXISTS, "false"));

        if(!fileExists){
            mItemList = new ArrayList<>();
        }else{
            mItemList = readFromFile(); //TODO
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) { //TODO abstract to methods
        View view = inflater.inflate(R.layout.fragment_training_log, container, false);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_training_log);
        mRecyclerView.addOnItemTouchListener(new TrainingLogTouchListener(getActivity(), mRecyclerView, new TrainingLogClickListener() {
            @Override
            public void onClick(View view, int position) {
                mCallBack.onTouchItemSelected(view, position); //need view? todo
            }

            @Override
            public void onLongClick(View view, int position) {
                deleteTrainingItemDialog(position);
            }
        }));
        adapter = new TrainingLogAdapter(getActivity(), mItemList);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.attachToRecyclerView(mRecyclerView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayTrainingList();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallBack = (OnTouchItemSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnTouchItemSelectedListener");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        writeToFile(); //TODO refactor writeToFile() to be more abstract
    }

    public void displayTrainingList(){
        CharSequence options[] = new CharSequence[] {"Chain Punching", "Shift Punching",
                "Step Punching", "Siu Lim Tao - 1", "Siu Lim Tao - 2"};

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.dialog_training_list, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setTitle("What are we training today?");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String label;
                switch (which) {
                    case 0:
                        label = "Chain Punching";
                        break;
                    case 1:
                        label = "Shift Punching";
                        break;
                    case 2:
                        label = "Step Punching";
                        break;
                    case 3:
                        label = "Siu Lim Tao - 1";
                        break;
                    case 4:
                        label = "Siu Lim Tao - 2";
                        break;
                    default:
                        label = "unknown";
                }
                displayTrainingQuantity(label);
            }
        });
        builder.show();
    }

    public void displayTrainingQuantity(final String label){
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        final View view = inflater.inflate(R.layout.dialog_training_quantity, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setPositiveButton("Next", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText quantity = (EditText) view.findViewById(R.id.edit_dialog_quantity);
                String quantityValue = quantity.getText().toString();

                putTrainingItem(label, getTrainingDay(), getTrainingMonth(), quantityValue);
            }
        });
        builder.show();
    }

    public void deleteTrainingItemDialog(final int index){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteTrainingItem(index);
            }
        });
        builder.show();
    }

    public String getTrainingMonth(){
        String month = Calendar.getInstance().getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US);
        return month;
    }

    public String getTrainingDay(){
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        return Integer.toString(day);
    }

    public void putTrainingItem(String label, String day, String month, String quantity){
        TrainingItem item = new TrainingItem();
        item.label = label;
        item.day = day;
        item.month = month;
        item.quantity = quantity;
        mItemList.add(item);
        //TODO probably need to notify adapter here
    }

    public void deleteTrainingItem(int index){
        mItemList.remove(index);
        adapter.notifyItemRemoved(index);
        Log.d(TAG, "deleteTrainingItem called");
    }

    public void writeToFile(){
        FileOutputStream fos;
        String fileName = "training_data.ser";

        File file = new File(getActivity().getFilesDir(), fileName);

        try {
            fos = getActivity().openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(mItemList);
            Log.d(TAG, "write to file attempted");
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(!fileExists){
            fileExists = true;
            writeToSharedPreferences(getActivity(), KEY_FILE_EXISTS, Boolean.toString(fileExists));
        }
    }

    public ArrayList<TrainingItem> readFromFile(){
        FileInputStream fileInputStream;
        String fileName = "training_data.ser";
        ArrayList<TrainingItem> trainingData = null;

        File file = new File(getActivity().getFilesDir(), fileName);

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            trainingData = (ArrayList<TrainingItem>) ois.readObject();
            Log.d(TAG, "read from file attempted");
            ois.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return trainingData;
    }

    public void writeToSharedPreferences(Context context, String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String readFromSharedPreferences(Context context, String key, String defValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defValue);
    }

    /**
     public static List<TrainingItem> getItemData(){
     List<TrainingItem> list = new ArrayList<>();
     int[] images = {R.drawable.definitions, R.drawable.training_assistant, R.drawable.forms_walkthrough,
     R.drawable.definitions, R.drawable.training_assistant, R.drawable.forms_walkthrough,
     R.drawable.definitions, R.drawable.training_assistant, R.drawable.forms_walkthrough};
     String[] techniques = {"Chain Punching", "Stepping", "Shifting", "Shift Punching", "Step Punching",
     "Faan Sau", "Siu Lim Tao - Section 1", "Siu Lim Tao - Section 2", "Siu Lim Tao - Section 3"};
     String[] date = {"Sep. 12, 1999", "Aug 2, 2001", "July 1, 2014", "Sep. 12, 1999", "Aug 2, 2001", "July 1, 2014",
     "Sep. 12, 1999", "Aug 2, 2001", "July 1, 2014"};
     String[] quantity = {"x200", "x12", "x450", "x200", "x12", "x450", "x200", "x12", "x450"};

     for (int i = 0; i < images.length && i < techniques.length && i < date.length && i < quantity.length; i++){
     TrainingItem item = new TrainingItem();
     item.imageId = images[i];
     item.label = techniques[i];
     item.date = date[i];
     item.quantity = quantity[i];
     list.add(item);
     }
     return list;
     } */

    private class TrainingLogAdapter extends RecyclerView.Adapter<TrainingLogAdapter.MyViewHolder> {
        private static final String TAG = "vtmaa_adapter";
        private final LayoutInflater inflater;
        List<TrainingItem> list = Collections.emptyList();
        //ArrayList<ItemData> list = new ArrayList<>();
        Context context;

        public TrainingLogAdapter(Context context, List<TrainingItem> list){
            inflater= LayoutInflater.from(context);
            this.list = list;
            this.context = context;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = inflater.inflate(R.layout.item_training_log, viewGroup, false);

            MyViewHolder holder = new MyViewHolder(view);

            Log.d(TAG, "onCreateViewHolder called");
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder viewHolder, int position) {
            Log.d(TAG, "onBindVH called");
            TrainingItem currentItem = list.get(position);
            viewHolder.label.setText(currentItem.label);
            viewHolder.day.setText(currentItem.day);
            viewHolder.month.setText(currentItem.month);
            viewHolder.quantity.setText(currentItem.quantity);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView label, day, month, quantity;

            public MyViewHolder(View itemView) {
                super(itemView);
                label = (TextView)itemView.findViewById(R.id.text_view_training1);
                day = (TextView)itemView.findViewById(R.id.text_training_day);
                month = (TextView)itemView.findViewById(R.id.text_training_month);
                quantity = (TextView)itemView.findViewById(R.id.text_view_training3);
            }
        }
    }

    class TrainingLogTouchListener implements RecyclerView.OnItemTouchListener{
        private GestureDetector gestureDetector;
        private TrainingLogClickListener trainingLogClickListener;


        public TrainingLogTouchListener(Context context, final RecyclerView recyclerView, final TrainingLogClickListener trainingLogClickListener){
            this.trainingLogClickListener = trainingLogClickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View view = recyclerView.findChildViewUnder(e.getX(),e.getY());

                    if(view!=null && trainingLogClickListener!=null){
                        trainingLogClickListener.onLongClick(view, recyclerView.getChildAdapterPosition(view));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            View view = recyclerView.findChildViewUnder(motionEvent.getX(),motionEvent.getY());

            if(view!=null && trainingLogClickListener!=null && gestureDetector.onTouchEvent(motionEvent)){
                trainingLogClickListener.onClick(view, recyclerView.getChildAdapterPosition(view));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public static interface TrainingLogClickListener{
        public void onClick(View view, int position);
        public void onLongClick(View view, int position);
    }

    // Container Activity must implement this interface
    public interface OnTouchItemSelectedListener { //todo this needed or can i use HomeFragmentClickListener above?
        public void onTouchItemSelected(View view, int position);
    }
}
