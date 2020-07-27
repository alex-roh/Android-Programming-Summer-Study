package alexroh.notion.mybutton;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatButton;

class BitmapButton extends AppCompatButton {

    // 1. 자바 소스 코드에서 버튼을 만들 때 사용하는 생성자
    public BitmapButton(Context context) {
        super(context);
        init(context);
    }

    // 2. XML 레이아웃 파일에서 버튼을 만들 때 사용하는 생성자자
   public BitmapButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    // 배경 이미지를 설정하는 메서드
    private void init(Context context){
        setBackgroundResource(R.drawable.title_bitmap_button_normal);
        // setTextSize(); // 소스 코드에서는 픽셀 단위로만 지정할 수 있음
                          // 단, 패러미터에서 unit을 지정하면 dp도 가능
                          // 텍스트뿐만 아니라 다른 것도 크기를 dp 단위로 설정하고 싶으면
                          // res -> values -> dimens.xml을 생성하여 dp 단위로 설정
                          // 그 정보를 소스 코드에서 읽어와서 설정하면 됨

        // getResources : res 폴더 밑에 있는 것을 가리킴
        // getDimension : dimens.xml의 정보를 가져옴
        float textSize = getResources().getDimension(R.dimen.text_size);
        setTextSize(textSize);
        setTextColor(Color.WHITE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch(action){
            case MotionEvent.ACTION_DOWN:
                setBackgroundResource(R.drawable.title_bitmap_button_clicked);
                break;
            case MotionEvent.ACTION_UP:
                setBackgroundResource(R.drawable.title_bitmap_button_normal);
                break;
        }
        // 이미지가 바뀌면 다시 그려야 하므로 다시 그리는 메서드인 invalidate() 호출
        invalidate();
        return true;
    }
}
