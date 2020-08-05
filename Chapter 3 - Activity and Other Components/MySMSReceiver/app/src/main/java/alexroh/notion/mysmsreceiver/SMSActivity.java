package alexroh.notion.mysmsreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SMSActivity extends AppCompatActivity {

    EditText editText;
    EditText editText2;
    EditText editText3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_m_s);

        Toast.makeText(getApplicationContext(), "onCreate!", Toast.LENGTH_LONG).show();

        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onNewIntent(Intent intent) {
        Toast.makeText(getApplicationContext(), "onNewIntent!", Toast.LENGTH_LONG).show();
        processCommand(intent);
        super.onNewIntent(intent);
    }

    private void processCommand(Intent intent){
        if(intent != null){
            String sender = intent.getStringExtra("sender");
            String contents = intent.getStringExtra("contents");
            String date = intent.getStringExtra("date");

            editText.setText(sender);
            editText2.setText(contents);
            editText3.setText(date);

            Toast.makeText(getApplicationContext(), "processCommand!", Toast.LENGTH_LONG).show();
        }
        else Toast.makeText(getApplicationContext(), "Intent null!", Toast.LENGTH_LONG).show();
    }
}