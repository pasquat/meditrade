package com.example.tutorial;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.transition.Fade;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.adrianotelesc.expandablesearchbar.ExpandableSearchBar;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link marketplace#newInstance} factory method to
 * create an instance of this fragment.
 */
public class marketplace extends Fragment  {

    listAdapter adapter;
    ListView listView;
    FirebaseDatabase database;
    String tmpStr="";
    String searchTxt="";
    TextView nothing;
    TextView title,location;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;




    public marketplace() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment marketplace.
     */
    // TODO: Rename and change types and number of parameters
    public static marketplace newInstance(String param1, String param2) {
        marketplace fragment = new marketplace();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(getContext());
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
        View view = inflater.inflate(R.layout.fragment_marketplace, container, false);
        Fresco.initialize(getContext());
        database = FirebaseDatabase.getInstance();
        listView = view.findViewById(R.id.listview);
        SearchView searchbar = view.findViewById(R.id.searchbar);
        ImageButton searchbtn = view.findViewById(R.id.imageButton2);
        TextView meditrade = view.findViewById(R.id.textView10);
        nothing = view.findViewById(R.id.textView18);
        nothing.setVisibility(View.GONE);
        searchbar.setVisibility(View.GONE);


        if (isConnected()) {

        }
        else {
            Snackbar.make(getActivity().findViewById(android.R.id.content),"No connection",Snackbar.LENGTH_SHORT).show();
        }

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchbar.setIconified(false);
                searchbtn.setVisibility(View.GONE);
                meditrade.setVisibility(View.GONE);
                searchbar.setVisibility(View.VISIBLE);
            }
        });

        searchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String newText) {
                //Log.e("onQueryTextChange", "called");
                searchTxt = searchbar.getQuery().toString();
                database_run(tmpStr);
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                searchTxt = searchbar.getQuery().toString();
                database_run(tmpStr);
                return false;
            }

        });

        searchbar.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchbtn.setVisibility(View.VISIBLE);
                meditrade.setVisibility(View.VISIBLE);
                searchbar.setVisibility(View.GONE);
                return false;
            }
        });
        listView.setAdapter(adapter);
        getLocation();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a_parent, View a_view, int a_position, long a_id) {
                Intent intent=new Intent(getContext(),itemSpecifics.class);
                intent.putExtra("key",adapter.items.get(a_position).key);
                startActivity(intent);
            }
        });
        return view;
    }

    class listAdapter extends BaseAdapter {
        //데이터가 들어가있지 않고, 어떻게 담을지만 정의해뒀다.
        ArrayList<listitem> items = new ArrayList<listitem>();
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "Lato-Black.ttf");

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(listitem item){
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
            listitemview singerItemView = null;
            listitem item = items.get(position);

            // 코드를 재사용할 수 있도록
            if(convertView == null) {
                singerItemView = new listitemview(getActivity().getApplicationContext());
            } else {
                singerItemView = (listitemview) convertView;
            }
            singerItemView.setEmpty();
            singerItemView.setName(item.getName());
            singerItemView.setMobile(item.getMobile());
            singerItemView.setImage(item.getResId());


            return singerItemView;
        }
        public void clear(){
            items.clear();
        }
    }
    public void database_run(String tmpStr)
    {
        adapter.clear();
        database.getReference("post").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    String postId = messageData.child("country").getValue().toString();
                    String desc = messageData.child("title").getValue().toString();
                    Log.d("database_run",messageData.toString());
                    Log.d("database_run_2",postId+"/"+tmpStr);
                    if(postId.equals(tmpStr)){
                        if(searchTxt.equals("")) {
                            String key = messageData.getKey();
                            String imgId = messageData.child("img").getValue().toString();
                            adapter.addItem(new listitem(desc, postId, imgId, key));
                        }
                        else{
                            if(desc.contains(searchTxt)){
                                String key = messageData.getKey();
                                String imgId = messageData.child("img").getValue().toString();
                                adapter.addItem(new listitem(desc, postId, imgId, key));
                            }
                        }
                    }
                }
                if(adapter.isEmpty()){
                    nothing.setVisibility(View.VISIBLE);
                }
                else {
                    nothing.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void getLocation(){
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database.getReference("info").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    Log.d("conform",messageData.toString()+"/"+id);
                    if(messageData.child("uid").getValue().toString().equals(id)){
                        tmpStr=messageData.child("country").getValue().toString();
                        database_run(tmpStr);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error2",databaseError.toString());
                //Toast.makeText(getContext(), databaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private Boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
}