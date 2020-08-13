package alexroh.notion.mythread;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    ValueHandler handler = new ValueHandler();
    Handler handler2 = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // BackgroundThread thread = new BackgroundThread();
                // thread.start();
                new Thread(new Runnable() {
                    boolean running  = false;
                    int value = 0;
                    @Override
                    public void run() {
                        running = true;
                        // running == false일 시 중단됨
                        while(running){
                            value++;
                            handler2.post(new Runnable() {
                                @Override
                                public void run() {
                                    // 핸들러(handler2) 내부에서 실행되는 코드
                                    textView.setText("현재 값 : " + value);
                                }
                            });
                            try {
                                Thread.sleep(1000); // 1초동안 쉼
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        });
        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 메인 스레드 안에서 UI에 접근하므로 문제가 발생하지 않음
            }
        });

    }

    class BackgroundThread extends Thread {
        int value = 0;
        boolean running = false;
        // 스레드 객체를 만들어서 start()를 호출하면 run() 메서드가 자동으로 실행됨
        public void run(){
            running = true;
            // running == false일 시 중단됨
            while(running){
                value++;
                /*
                스레드에서 UI에 접근하면 문제가 발생함 -> 핸들러를 사용해야 함
                textView.setText("현재 값 : " + value);
                */
                // 핸들러의 메세지 객체를 참조함
                Message message = handler.obtainMessage();
                // 메세지에 넣을 번들 객체를 만듦
                Bundle bundle = new Bundle();
                bundle.putInt("value", value);
                message.setData(bundle); // set으로 Bundle 객체를 넣음
                // 핸들러 쪽으로 처리 요청을 보냄
                handler.sendMessage(message);
                try {
                    Thread.sleep(1000); // 1초동안 쉼
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 핸들러는 메인 스레드에서 동작함
    class ValueHandler extends Handler {
        @Override
        // 핸들러로 들어온 메세지를 전달받음
        public void handleMessage(@NonNull Message msg) {
            // UI에 직접 접근 가능
            Bundle bundle = msg.getData();
            int value = bundle.getInt("value");
            textView.setText("현재 값 : " + value);
            super.handleMessage(msg);
        }
    }

}