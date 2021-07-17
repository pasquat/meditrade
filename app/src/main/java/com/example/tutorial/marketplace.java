package com.example.tutorial;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link marketplace#newInstance} factory method to
 * create an instance of this fragment.
 */
public class marketplace extends Fragment  {

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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_marketplace, container, false);

        ListView listView = view.findViewById(R.id.listview);
        listAdapter adapter = new listAdapter();
        adapter.addItem(new listitem("30pc Face Masks", "010-1000-1000",R.drawable.masks));
        adapter.addItem(new listitem("whiteFish", "010-1000-1001",R.drawable.masks));
        adapter.addItem(new listitem("whiteGroup", "010-1000-1002",R.drawable.masks));
        adapter.addItem(new listitem("blackGroup", "010-1000-1003",R.drawable.masks));
        adapter.addItem(new listitem("blackFish", "010-1000-1000",R.drawable.masks));
        adapter.addItem(new listitem("whiteFish", "010-1000-1001",R.drawable.masks));
        listView.setAdapter(adapter);

        return view;
    }

    class listAdapter extends BaseAdapter {
        //데이터가 들어가있지 않고, 어떻게 담을지만 정의해뒀다.
        ArrayList<listitem> items = new ArrayList<listitem>();

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
            // 코드를 재사용할 수 있도록
            if(convertView == null) {
                singerItemView = new listitemview(getActivity().getApplicationContext());
            } else {
                singerItemView = (listitemview) convertView;
            }
            listitem item = items.get(position);
            singerItemView.setName(item.getName());
            singerItemView.setMobile(item.getMobile());
            singerItemView.setImage(item.getResId());
            return singerItemView;
        }
    }
}