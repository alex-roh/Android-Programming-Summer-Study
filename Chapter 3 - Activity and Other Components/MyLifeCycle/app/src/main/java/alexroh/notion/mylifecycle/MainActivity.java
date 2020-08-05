package alexroh.notion.mylifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    // 수명주기 메서드 중 하나
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, "OnCreate() 호출됨", Toast.LENGTH_LONG).show();

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "OnStart() 호출됨", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "OnStop() 호출됨", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "OnDestroy() 호출됨", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "OnPause() 호출됨", Toast.LENGTH_LONG).show();
        // 혹은 preference 매니저로 참조
        // 패러미터는 이름과 모드가 들어가는데, 이름의 경우 저장할 때와 복구할 때 같은 이름이 사용됨
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit(); // Editor (쉐어드 프리퍼런스의 이너 클래스)
        editor.putString("name", "NNAAMMEE");
        editor.commit(); // 값을 저장
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "OnResume() 호출됨", Toast.LENGTH_LONG).show();
        // 쉐어드 프리퍼런스 객체를 참조
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        if(pref != null){
            String name = pref.getString("name", ""); // name이 없는 경우 빈 값을 돌려받음
            Toast.makeText(this, "복구된 이름 : " + name, Toast.LENGTH_LONG).show();
        }
    }
}