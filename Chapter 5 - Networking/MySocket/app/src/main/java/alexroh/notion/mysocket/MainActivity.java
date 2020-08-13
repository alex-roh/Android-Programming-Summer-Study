package alexroh.notion.mysocket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClientThread thread = new ClientThread();
                thread.start();
            }
        });
    }

    class ClientThread extends Thread{
        @Override
        public void run(){
            String host = "localhost"; // 자기 자신을 가리킴
            int port = 5001;
            // 서버로 접속
            try {
                Socket socket = new Socket(host, port);
                // 서버로 데이터를 내보내기 위한 통로를 만듦
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeObject("안녕!");
                outputStream.flush();
                Log.d("ClientThread", "서버로 보냄.");

                // 서버에서 데이터를 받아오기 위한 통로를 만듦
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                final Object input = inputStream.readObject();
                Log.d("ClientThread", "받은 데이터 : " + input);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("받은 데이터 : " + input);
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

}