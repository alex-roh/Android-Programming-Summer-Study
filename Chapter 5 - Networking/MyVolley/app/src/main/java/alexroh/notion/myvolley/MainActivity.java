package alexroh.notion.myvolley;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView textView;
    // Volley 라이브러리 내부에서 핸들러로 처리되기 때문에 핸들러 객체를 만들 필요가 없음

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlStr = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=430156241533f1d058c603178cc3ca0e&targetDt=20120101";
                request(urlStr);
            }
        });

        // RequestQueue를 만듦
        if(AppHelper.requestQueue == null) {
            AppHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
    }

    public void request(String urlStr){
        StringRequest request = new StringRequest(
                Request.Method.GET,
                urlStr,
                new Response.Listener<String>() {
                    @Override
                    // 응답을 문자열로 받아서 패러미터로 받음
                    public void onResponse(String response) {
                        println("응답:\n" + response);
                        processResponce(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        println("에러:\n");
                    }
                }){
            // Request 객체 안의 메서드를 재정의
            @Override
            // 요청 패러미터를 추가함
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };

        // Volley는 내부에서 Request들을 캐싱함
        // 한번 보내고 받은 결과가 있으면 그걸 다시 보여줄 수 있기 때문에
        // 받은 결과를 매번 보기 위해서는 setShouldCache를 false로 처리해야 함
        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);
        println("요청을 보냈음.\n");
        // 응답을 받으면 Listner에 정의한 메서드가 호출될 것임
    }

    public void processResponce(String responce){
        Gson gson = new Gson();
        // Gson의 리턴 타입은 MovieClass
        MovieList movieList = gson.fromJson(responce, MovieList.class);
        if(movieList != null){
            int countMovie = movieList.boxOfficeResult.dailyBoxOfficeList.size();
            println("박스오피스 타입 : " + movieList.boxOfficeResult.boxofficeType);
            println("응답받은 영화 개수 : " + countMovie);
        }

    }

    public void println(String data){
        textView.append(data + "\n");
    }

}