package alexroh.notion.myevent;

import androidx.appcompat.app.AppCompatActivity;

import android.gesture.Gesture;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    GestureDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        View view = findViewById(R.id.view);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                float currentX = event.getX();
                float currentY = event.getY();
                // 손가락을 눌렀을 때
                if(action == MotionEvent.ACTION_DOWN){
                    println("손가락 눌렸음 : " + currentX + ", " + currentY);
                }
                // 손가락을 움직일 때
                else if(action == MotionEvent.ACTION_MOVE){
                    println("손가락 움직임 : " + currentX + ", " + currentY);
                }
                // 손가락을 뗐을 때
                else if(action == MotionEvent.ACTION_UP){
                    println("손가락 떼졌음 : " + currentX + ", " + currentY);
                }
                return true; // 이벤트가 정상적으로 처리되었음을 알림
            }
        });

        detector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                println("onDown() 호출됨.");
                return true;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                println("onShowPress() 호출됨.");
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                println("onSingleTapUp() 호출됨.");
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                println("onScroll() 호출됨 : " + distanceX + ", " + distanceY);
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                println("onLongPress() 호출됨.");
            }

            @Override
            // 제스처의 속도를 자동으로 계산해서 패러미터로 넘김
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                println("onFling() 호출됨 : " + velocityX + ", " + velocityY);
                return true;
            }
        });

        View view2 = findViewById(R.id.view2);
        view2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                detector.onTouchEvent(event); // 전달받은 이벤트를 이용하여 계산
                return true;
            }
        });
    }

    @Override
    // 키가 눌리면 실행되는 메서드
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Toast.makeText(this, "시스템 Back 버튼 눌림.", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    public void println(String data){
        textView.append(data + "\n");
    }
}