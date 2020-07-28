package alexroh.notion.mylistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

class SingerItemView extends LinearLayout {

    TextView textView;
    TextView textView2;
    ImageView imageView;

    public SingerItemView(Context context) {
        super(context);
        init(context);
    }

    public SingerItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    // 인플레이션을 수행
    private void init(Context context){
        // 실행 중인 시스템 서비스 중에서 레이아웃 인플레이터 서비스를 가져옴
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 리니어 레이아웃을 상속받았으므로 this로 참조하여 붙임
        inflater.inflate(R.layout.singer_item, this, true);
        // 인플레이션 후에는 이미지뷰와 텍스트뷰를 참조할 수 있음
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        imageView = findViewById(R.id.imageView);
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
