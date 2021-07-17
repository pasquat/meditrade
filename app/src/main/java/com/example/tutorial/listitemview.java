package com.example.tutorial;

        import android.content.Context;
        import android.util.AttributeSet;
        import android.view.LayoutInflater;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;
        import androidx.annotation.Nullable;

public class listitemview extends LinearLayout {

    //어디서든 사용할 수 있게하려면
    TextView textView, textView2;
    ImageView imageView;

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

        textView = findViewById(R.id.textView11);
        textView2 = findViewById(R.id.textView12);
        imageView = findViewById(R.id.imageView5);
    }

    public void setName(String name){
        textView.setText(name);
    }
    public void setMobile(String mobile){
        textView2.setText(mobile);
    }
    public void setImage(int resId){
        imageView.setImageResource(resId);
    }
}