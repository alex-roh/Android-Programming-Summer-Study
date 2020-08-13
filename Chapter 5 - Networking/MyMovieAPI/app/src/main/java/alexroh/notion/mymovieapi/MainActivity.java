package alexroh.notion.mymovieapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import alexroh.notion.mymovieapi.data.MovieInfo;
import alexroh.notion.mymovieapi.data.MovieList;
import alexroh.notion.mymovieapi.data.ResponceInfo;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestMovieList();
            }
        });

        if(AppHelper.requestQueue == null){
            AppHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

    }

    public void requestMovieList(){
        String url = "http://" + AppHelper.host + ":" + AppHelper.port + "/movie/readMovieList";
        url += "?" + "type=1";

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        println("응답 받음 -> " + response);
                        processResponce(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        println("에러 발생 -> " + error.getMessage());
                    }
                }
        ); // post 방식에서는 중괄호를 열고 getParams를 재정의해야 하지만 여기선 필요없음

        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);
        println("영화목록 요청 보냄");
    }

    public void processResponce(String responce){
        Gson gson = new Gson();
        ResponceInfo info = gson.fromJson(responce, ResponceInfo.class);
        // 응답에서 코드가 200으로 넘어오면 정상적으로 데이터를 넘겨받은 것임
        if(info.code == 200){
            // MovieList 역시 Gson으로 파싱할 수 있음
            MovieList movieList = gson.fromJson(responce, MovieList.class);
            println("영화 갯수 : " + movieList.result.size());
            for(int i = 0; i < movieList.result.size(); i++){
                MovieInfo movieInfo = movieList.result.get(i);
                println("영화 #" + i + " -> " + movieInfo.id + ", " + movieInfo.title + ", " + movieInfo.grade);
                // 이제 영화 상세정보를 Volley로 받아올 수 있음
                // MovieInfo 객체에 속성을 더 추가해서 만들어 줄 수도 있음
            }
        }
    }

    public void println(String data){
        textView.append(data + "\n");
    }
}