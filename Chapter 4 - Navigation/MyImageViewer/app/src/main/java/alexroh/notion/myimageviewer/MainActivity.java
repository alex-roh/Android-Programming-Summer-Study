package alexroh.notion.myimageviewer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    ListFragment fragment1;
    ViewerFragment fragment2;
    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* 자바에서 동적으로 프래그먼트를 추가하는 코드
        fragment1 = new ListFragment();
        fragment2 = new ViewerFragment();
         */
        // XML 레이아웃에서 프래그먼트를 추가한 경우 id를 이용해 찾음
        manager = getSupportFragmentManager();
        fragment1 = (ListFragment) manager.findFragmentById(R.id.listFragment);
        fragment2 = (ViewerFragment) manager.findFragmentById(R.id.viewerFragment);
    }

    // 프래그먼트가 호출할 액티비티의 메서드
    public void onImageChange(int index){
        fragment2.setImage(index);
    }

}