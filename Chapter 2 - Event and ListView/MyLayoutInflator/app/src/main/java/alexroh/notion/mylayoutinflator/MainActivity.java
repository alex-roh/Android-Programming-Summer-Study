package alexroh.notion.mylayoutinflator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    FrameLayout container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // 인플레이션

        container = (FrameLayout) findViewById(R.id.container);

        Button button = (Button) findViewById(R.id.button); // 인플레이션되어 메모리에 올라간 위치를 가져옴

        // 버튼을 클릭하면 sub1.xml에 대한 인플레이션 과정을 거쳐서
        // 우리가 공간을 확보해둔 container에 붙임
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // getSystemService -> 액티비티에서 사용할 수 있는 메서드
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                // sub1.xml을 container에 붙이고, true -> 바로 붙여주시오
                inflater.inflate(R.layout.sub1, container, true);
                // 인플레이션을 하고 나면 그 안에 들어가 있는 뷰를 id를 이용해 찾을 수 있음
            }
        });
    }
}