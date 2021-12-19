package com.example.tutorial;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class chkInternet {
    Context context;
    public chkInternet(Context context){
        this.context=context;
    }
    public Boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
}
