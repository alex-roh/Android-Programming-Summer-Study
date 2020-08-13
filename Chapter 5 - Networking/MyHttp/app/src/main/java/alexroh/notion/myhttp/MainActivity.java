package alexroh.notion.myhttp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView textView;

    String urlStr;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlStr = editText.getText().toString();
                RequestThread thread = new RequestThread();
                thread.start();
            }
        });
    }

    // 텍스트뷰에 한 줄씩 추가함
    public void println(final String data){
        handler.post(new Runnable() {
            @Override
            public void run() {
                textView.append(data + "\n");
            }
        });
    }

    class RequestThread extends Thread {
        @Override
        public void run() {
            // url 문자열으로 URL 객체를 생성
            try {
                // 문자열을 모으기 위한 StringBuilder 객체
                StringBuilder builder = new StringBuilder();
                URL url = new URL(urlStr);
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                if(connection != null){
                    // 10초 동안 기다려서 응답이 없으면 끝냄
                    connection.setConnectTimeout(100000);
                    connection.setRequestMethod("GET"); // GET으로 요청
                    // 서버로 입력과 출력을 모두 가능케 함
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    // getResponceCode를 호출하면 서버와 연결됨
                    int resCode = connection.getResponseCode();
                    // 서버의 응답이 OK일 경우에만 화면에 출력함
                    if(resCode == HttpURLConnection.HTTP_OK){
                        // Reader -> 들어오는 데이터를 바이트 배열이 아닌 문자열로 처리함
                        // BufferedReader -> 한 줄씩 문자열을 읽어들일 수 있게 함
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String line = null;
                        while(true){
                            line = reader.readLine();
                            if(line == null) break;
                            builder.append(line + "\n");
                        }
                        reader.close();
                    }
                    connection.disconnect();
                    println(builder.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}