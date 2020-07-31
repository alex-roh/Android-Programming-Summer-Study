package alexroh.notion.mycallintent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String receiver = editText.getText().toString();
                // 암시적 인텐트
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+receiver));
                startActivity(intent);

                Intent intent2 = new Intent();
                // 다른 액티비티의 패키지 이름과 다른 액티비티의 클래스 이름
                // 이 방법의 장점은 MenuActivity.class로 객체를 지정하지 않고 문자열로 지정할 수 있다는 것
                // 즉, 액티비티가 아직 만들어지지 않은 시점에서 혹은 남들이 만든 액티비티를 부를 수 있음
                ComponentName name = new ComponentName("alexroh.notion.mycallintent", "alexroh.notion.mycallintent.MenuActivity");
                intent.setComponent(name);
                startActivity(intent2);
            }
        });
    }
}