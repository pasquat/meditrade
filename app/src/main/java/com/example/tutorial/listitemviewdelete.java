package com.example.tutorial;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class listitemviewdelete extends LinearLayout {

    //어디서든 사용할 수 있게하려면
    TextView textView, textView2;

    public listitemviewdelete(Context context) {
        super(context);
        init(context);//인플레이션해서 붙여주는 역
    }

    public listitemviewdelete(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    // 지금 만든 객체(xml 레이아웃)를 인플레이션화(메모리 객체화)해서 붙여줌
    // LayoutInflater를 써서 시스템 서비스를 참조할 수 있음
    // 단말이 켜졌을 때 기본적으로 백그라운드에서 실행시키는 것을 시스템 서비스라고 함
    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.listview_itemdelete, this, true);
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "Lato-Black.ttf");
        Typeface smallfont = Typeface.createFromAsset(getContext().getAssets(),"Lato-Bold.ttf");
        textView = findViewById(R.id.textViewdtitle);
        textView2 = findViewById(R.id.textViewdlocation);

        textView.setTypeface(font);
        textView2.setTypeface(smallfont);
    }

    public void setName(String name) {
        textView.setText(name);
    }

    public void setMobile(String mobile) {
        textView2.setText(mobile);
    }
}
