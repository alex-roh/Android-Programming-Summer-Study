package alexroh.notion.myservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

    private static final String TAG = "MyService";

    public MyService() {
    }


    // 서비스는 한번 실행되면 계속 실행되므로 startService를 여러번 실행해도 onCreate는 한번만 실행됨
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() 호출됨.");
    }

    // 인텐트로 전달된 데이터를 확인할 때 사용하는 메서드 : mainActivity에서 보낸 인텐트를 처리함
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand() 호출됨.");

        if(intent == null){
            return Service.START_STICKY; // 서비스가 종료되더라도 다시 자동으로 실행해달라고 하는 옵션
        }
        else {
            String command = intent.getStringExtra("command");
            String name = intent.getStringExtra("name");
            Log.d(TAG, "전달받은 데이터 : " + command + ", " + name);
            // 5초동안 쉼
            try {
                Thread.sleep(500);
            } catch (Exception e) {}
            // 액티비티로 인텐트를 전달
            Intent showIntent = new Intent(getApplicationContext(), MainActivity.class);
            showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            showIntent.putExtra("command", "show");
            showIntent.putExtra("name", "mike");
            startActivity(showIntent);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    // 일반적으로는 onBind를 많이 사용하지 않음
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
