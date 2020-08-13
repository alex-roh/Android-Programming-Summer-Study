package alexroh.notion.myasynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 일정 시간 후에 진행할 수 있도록 함
                /*
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ProgressThread thread = new ProgressThread();
                        thread.start();
                    }
                }, 5000); // 5초 후부터 프로그래스바가 진행됨
                 */
                ProgressTask task = new ProgressTask();
                task.execute("start");
            }
        });
    }

    class ProgressTask extends AsyncTask<String, Integer, Integer>{

        boolean running = false;
        int value = 0;

        @Override
        // AsyncTask를 실행하면 자동으로 실행되는 콜백 메서드 (첫번째 패러미터 -> String)
        protected Integer doInBackground(String... strings) { // 가변 길이 패러미터
            // 스레드 안에서 동작하는 코드를 이 안에 넣음
            while(true){
                if(value > 100){
                    break;
                }
                value++;
                // onProgressUpdate 메서드를 실행하기 위해 publishProgress를 호출함
                publishProgress(value);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return value; // onPostExecute의 패러미터로 들어가는 반환값
        }

        @Override
        // 중간중간 UI를 업데이트하는 메서드 (두번째 패러미터 -> Integer)
        protected void onProgressUpdate(Integer... values) {
            // doInBackground 메서드 안에서 publishProgress를 호출하면 이 메서드가 호출됨
            progressBar.setProgress(values[0].intValue());
            super.onProgressUpdate(values);
        }

        @Override
        // doInbackground가 반환하는 값을 패러미터로 전달받음 (세번째 패러미터 -> Integer)
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            Toast.makeText(getApplicationContext(), "완료됨", Toast.LENGTH_LONG).show();
        }

    }

    class ProgressThread extends Thread{
        int value = 0;
        public void run(){
            while(true){
                if(value > 100){
                    break;
                }
                value++;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setProgress(value);
                    }
                });

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}