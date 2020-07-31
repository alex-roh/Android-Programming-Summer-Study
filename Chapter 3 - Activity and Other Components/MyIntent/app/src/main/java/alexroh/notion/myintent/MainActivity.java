package alexroh.notion.myintent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // xml 파일과 현재 액티비티를 연결함 : 레이아웃 인플레이션
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 시스템이 화면을 관리하므로 메뉴 화면을 띄우고 싶다면 시스템에게 요청해야 함
                // 시스템이 알아들을 수 있는 포맷으로 요청하는 게 인텐트임
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                // MenuActivity.class -> 클래스의 인스턴스 객체
                // startActivity(intent); // 시스템으로 인텐트(요구사항 명세서)를 전달함
                // 메인 화면으로 돌아올 때 응답을 받아야 하는 경우 startActivityForResult() 메서드 사용
                startActivityForResult(intent, 101);
                // 패러미터로 들어가는 것은 어떤 화면에서 응답받았는지 구분하는 id
            }
        });
    }

    // 다른 액티비티가 보낸 인텐트를 전달받음
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // requestCode : 이 액티비티에서 다른 액티비티를 열었을 때 보낸 id 코드
        if(requestCode == 101){
            String name = data.getStringExtra("name"); // put으로 넣은 데이터를 get으로 뺌(해쉬 테이블과 비슷)
            Toast.makeText(getApplicationContext(), "메뉴화면으로부터 응답 : " + name, Toast.LENGTH_SHORT).show();
        }
    }
}