package com.example.tutorial;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.app.ProgressDialog.show;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link myposts#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class myposts extends Fragment {

    listAdapter adapter;
    ListView listviewdelete;
    FirebaseDatabase database;
    String tmpStr ="";


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment myposts.
     */
    // TODO: Rename and change types and number of parameters
    public static myposts newInstance(String param1, String param2) {
        myposts fragment = new myposts();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public myposts() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
            database = FirebaseDatabase.getInstance();
            adapter = new listAdapter();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_myposts,container,false);
        database = FirebaseDatabase.getInstance();
        TextView title = (TextView) view.findViewById(R.id.textView11);
        listviewdelete = (ListView) view.findViewById(R.id.listviewdelete);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        database_run();
        adapter = new listAdapter();
        listviewdelete.setAdapter(adapter);

        chkInternet chk=new chkInternet(getContext());
        if(chk.isConnected()){

        }
        else{
            Snackbar.make(getActivity().findViewById(android.R.id.content),"No connection", Snackbar.LENGTH_SHORT).show();
        }

        listviewdelete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a_parent, View a_view, int a_position, long a_id) {
                builder.setMessage("You cannot undo this") .setTitle("Do you want to delete this item?");
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("AlertDialogExample");
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                database.getReference("post").child(adapter.items.get(a_position).key).removeValue();
                                adapter.clear();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                alert.hide();
                                break;
                        }
                    }
                };
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

            }
        });

        return view;
    }

    class listAdapter extends BaseAdapter {
        //데이터가 들어가있지 않고, 어떻게 담을지만 정의해뒀다.
        ArrayList<listitemdelete> items = new ArrayList<listitemdelete>();
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "Lato-Black.ttf");

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(listitemdelete item){
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        // 어댑터가 데이터를 관리하고 뷰도 만듬
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            listitemviewdelete singerItemView = null;
            listitemdelete item = items.get(position);

            // 코드를 재사용할 수 있도록
            if(convertView == null) {
                singerItemView = new listitemviewdelete(getActivity().getApplicationContext());
            } else {
                singerItemView = (listitemviewdelete) convertView;
            }

            singerItemView.setName(item.getName());
            singerItemView.setMobile(item.getMobile());


            return singerItemView;
        }
        public void clear(){
            items.clear();
        }
    }
    public void database_run(){
        String strEmail=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        adapter.clear();
        database.getReference("post").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    String postEmail = messageData.child("postId").getValue().toString();
                    if (strEmail.equals(postEmail)){
                        String postId = messageData.child("country").getValue().toString();
                        String desc = messageData.child("title").getValue().toString();
                        Log.d("database_runDE",messageData.toString());
                        Log.d("database_run_2DE",postId+"/"+tmpStr);
                        String key = messageData.getKey();
                        String imgId = messageData.child("img").getValue().toString();
                        adapter.addItem(new listitemdelete(desc, postId, imgId, key));
                    }
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}