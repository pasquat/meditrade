package com.example.tutorial;

        import android.content.Context;
        import android.graphics.Typeface;
        import android.net.Uri;
        import android.util.AttributeSet;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;

        import com.bumptech.glide.Glide;
        import com.facebook.drawee.view.SimpleDraweeView;
        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.firebase.storage.FirebaseStorage;
        import com.google.firebase.storage.StorageReference;

public class listitemview extends LinearLayout {

    //어디서든 사용할 수 있게하려면
    TextView textView, textView2;
    ImageView imageView;
    SimpleDraweeView draweeView;

    public listitemview(Context context) {
        super(context);
        init(context);//인플레이션해서 붙여주는 역
    }

    public listitemview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    // 지금 만든 객체(xml 레이아웃)를 인플레이션화(메모리 객체화)해서 붙여줌
    // LayoutInflater를 써서 시스템 서비스를 참조할 수 있음
    // 단말이 켜졌을 때 기본적으로 백그라운드에서 실행시키는 것을 시스템 서비스라고 함
    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.listview_item,this, true);
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "Lato-Black.ttf");
        Typeface smallfont = Typeface.createFromAsset(getContext().getAssets(), "Lato-Bold.ttf");
        textView = findViewById(R.id.textView11);
        textView2 = findViewById(R.id.textView12);
        imageView = findViewById(R.id.imageView5);
        draweeView = (SimpleDraweeView) findViewById(R.id.imageView5);

        textView.setTypeface(font);
        textView2.setTypeface(smallfont);
    }

    public void setName(String name){
        textView.setText(name);
    }
    public void setMobile(String mobile){
        textView2.setText(mobile);
    }
    public void setEmpty() {draweeView.setImageResource(0);}
    public void setImage(String resId) {
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://meditrade-e4574.appspot.com");
        StorageReference storageRef = storage.getReference();
        storageRef.child("images/" + resId).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //이미지 로드 성공시
                draweeView.setImageURI(uri);
                //Glide.with(getContext()).load(uri).centerCrop().into(imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                //이미지 로드 실패시
            }
        });

    }
}