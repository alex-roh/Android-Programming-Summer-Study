package alexroh.notion.myparcelable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent passedIntent = getIntent();
        processIntent(passedIntent);

    }

    private void processIntent(Intent intent){
        if(intent != null){
            // ArrayList는 Serializable 인터페이스를 구현하고 있으므로 아래 메서드로 뺄 수 있음
            ArrayList<String> names = (ArrayList<String>) intent.getSerializableExtra("names");
            if(names != null){
            }
        }
    }

}