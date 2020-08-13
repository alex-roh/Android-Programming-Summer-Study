package alexroh.notion.myserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerService extends Service {
    public ServerService() {
    }

    @Override
    public void onCreate() {
        // 서비스가 만들어졌을 경우 실행되는 콜백 메서드
        // startService를 메인 액티비티에서 호출하면 스레드가 만들어지면서 서버가 대기함
        super.onCreate();
        ServerThread thread = new ServerThread();
        thread.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    class ServerThread extends Thread{
        @Override
        public void run() {
            int port = 5001; // 클라이언트가 접속해야 하는 포트
            try {
                ServerSocket server = new ServerSocket(port);
                Log.d("ServerThread", "서버가 실행됨.");
                while(true){
                    // accept 메서드를 호출하면 대기 상태로 진입
                    // 클라이언트가 접속하면 소켓 객체를 반환함
                    Socket socket = server.accept();
                    // 들어온 데이터를 받음
                    ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                    Object input = inputStream.readObject();
                    Log.d("ServerThread", "input : " + input);
                    // 데이터를 내보냄
                    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                    outputStream.writeObject(input + " from server.");
                    outputStream.flush(); // 버퍼를 비움
                    Log.d("ServerThread", "output 보냄.");
                    // 연결을 끊음
                    socket.close(); // 네트워킹은 시스템 리소스를 많이 사용하므로 바로바로 끊어주는 것이 좋음
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
