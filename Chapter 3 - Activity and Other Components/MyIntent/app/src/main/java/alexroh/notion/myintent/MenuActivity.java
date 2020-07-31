package alexroh.notion.myintent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // finish(); // 현재 화면을 없애고 액티비티 스택에 쌓인 이전 화면으로 돌아감
                             // 인텐트를 넣어서 이전 화면으로 데이터를 넘길 수도 있음
                Intent intent = new Intent();
                // 시스템은 Extra 데이터를 해석하지 않고 그대로 다른 액티비티로 넘김
                intent.putExtra("name", "Jin");
                // 이 응답이 정상적인 응답인지 아닌지를 가리키는 상수와 함께 인텐트를 전달
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}