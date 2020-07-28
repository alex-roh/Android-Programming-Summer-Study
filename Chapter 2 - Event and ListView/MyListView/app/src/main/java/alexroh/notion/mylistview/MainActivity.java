package alexroh.notion.mylistview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.Snapshot;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    SingerAdapter adapter;
    EditText editText;
    EditText editText2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView); // 이제 어댑터를 설정함
        // 어레이 어댑터 등을 사용하면 코드를 간단하게 할 수 있음
        // 리스트뷰 안에는 이미지, 텍스트 등이 커스터마이징되어 들어감
        // 처음부터 리스트뷰 안에 들어가는 아이템의 모양을 커스터마이징할 수 있게 만들도록 하자
        // 따라서 아이템의 뷰를 별도의 부분 화면으로 만듦

        // 어댑터 객체를 만들고 리스트뷰와 연결함
        adapter = new SingerAdapter();
        // 어댑터에 데이터 추가
        adapter.addItem(new SingerItem("카산드라", "000-000-0000", R.drawable.covid));
        adapter.addItem(new SingerItem("실피드", "000-000-0000", R.drawable.cough));
        adapter.addItem(new SingerItem("수메르", "000-000-0000", R.drawable.doctor));
        adapter.addItem(new SingerItem("아키트", "000-000-0000", R.drawable.covid));
        adapter.addItem(new SingerItem("알렉시스", "000-000-0000", R.drawable.cough));
        adapter.addItem(new SingerItem("카밀라", "000-000-0000", R.drawable.doctor));

        // 리스트뷰가 화면에 보일 때 getCount()를 호출하면서 몇개 아이템이 있는지 물어봄봄
        // 그러면 getView를 그 개수만큼 호출함
        listView.setAdapter(adapter);

        // 리스트 뷰는 화면에 보여주는 역할만 처리하지만, 이벤트 처리 리스너를 붙일 수도 있음
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // 아이템이 선택되면 호출되는 메서드
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // position에 해당되는 데이터는 어댑터에서 가져옴
                // 그러나 여기서 어댑터 객체를 참조할 수 없음 -> 어댑터 객체를 전역으로 선언
                SingerItem item = (SingerItem) adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "선택 : " + item.getName(), Toast.LENGTH_LONG).show();
            }
        });

        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();
                String mobile = editText2.getText().toString();
                adapter.addItem(new SingerItem(name, mobile, R.drawable.doctor));
                // 어댑터에 데이터가 추가되었음을 알림 -> 아래 메서드를 호출하면 어댑터에 의해 리스트뷰가 갱신됨
                adapter.notifyDataSetChanged();
                System.out.println("어댑터에 데이터 추가됨");
            }
        });

    }

    // 기존에 정의된 어댑터를 상속한 커스텀 어댑터를 생성
    class SingerAdapter extends BaseAdapter{

        // 데이터를 관리하는 자료구조
        ArrayList<SingerItem> items = new ArrayList<SingerItem>();

        // items에 아이템을 넣는 메서드
        public void addItem(SingerItem item){
            items.add(item);
        }

        // 몇 개의 아이템이 있는지 반환함
        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        // 아이템에 특정한 아이디가 있다면 반환
        @Override
        public long getItemId(int position) {
            return position;
        }

        // 데이터를 관리하는 어댑터에게 각각의 아이템에 대한 뷰도 그려서 보여달라는 메서드
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // 뷰 객체가 많이 만들어지면 메모리가 많이 잡아먹으므로
            // 화면에 보이는 열 몇개만 만들고 재사용함
            SingerItemView view = null;
            if(convertView == null){
                // 아이템 화면에 해당하는 xml 레이아웃을 부분 화면으로 정의함
                view = new SingerItemView(getApplicationContext());
            } else{
                // 캐스팅만 해서 재사용
                view = (SingerItemView) convertView;
            }
            // 패러미터로 넘어오는 position에 해당하는 데이터를 뷰에 넣음
            SingerItem item = items.get(position);
            view.setName(item.getName());
            view.setMobile(item.getMobile());
            view.setImage(item.getResId());
            return view;
        }
    }

}