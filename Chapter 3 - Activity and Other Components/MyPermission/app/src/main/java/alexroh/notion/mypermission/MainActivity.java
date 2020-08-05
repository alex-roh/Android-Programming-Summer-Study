package alexroh.notion.mypermission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        // 권한이 주어져 있는 경우
        if(permissionCheck == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "SMS 수신 권한 주어져 있음", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "SMS 수신 권한 없음", Toast.LENGTH_LONG).show();
            // 권한에 대한 설명이 필요한 경우
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS)){
                Toast.makeText(this, "SMS 권한 설명 필요함", Toast.LENGTH_LONG).show();
            } else {
                // 시스템에게 권한을 부여해 달라고 요청함 -> 시스템은 대화 상자를 띄움
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, 101);
            }
        }
    }

    // 권한이 부여될 때 실행되는 콜백 메서드
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       switch(requestCode){
           case 1:
               if(grantResults.length > 0){
                   if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                       Toast.makeText(this, "SMS 수신 권한을 사용자가 승인함", Toast.LENGTH_LONG).show();
                   }
                   else if(grantResults[0] == PackageManager.PERMISSION_DENIED) {
                       Toast.makeText(this, "SMS 수신 권한을 사용자가 승인하지 않음", Toast.LENGTH_LONG).show();
                   }
               } else {
                   Toast.makeText(this, "SMS 수신 권한을 부여받지 못함", Toast.LENGTH_LONG).show();
               }

               break;

       }
    }
}