package alexroh.notion.mysmsreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SMSReceiver extends BroadcastReceiver {

    private static final String TAG = "SMSReceiver";
    private static SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd HH:mm");

    // SMS 문자를 받으면 인텐트가 자동으로 onReceive 메서드로 떨어짐 (=> 콜백 메서드)
    // 인텐트에 텔레포니 모듈에 의해 수신받은 SMS 문자가 들어가 있음
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.d(TAG, "onReceive 호출됨");
        // 해시 테이블처럼 데이터를 담고 있는 객체인 번들 객체를 인텐트에서 뽑아냄
        Bundle bundle = intent.getExtras();
        // 번들 객체 안에 있는 SMS 메세지를 뽑아내는 메서드인 parseSmsMessage를 정의
        SmsMessage[] messages = parseSmsMessage(bundle);
        if(messages.length > 0){
            String sender = messages[0].getOriginatingAddress(); // 발신번호
            Log.d(TAG, "sender : " + sender);
            String contents = messages[0].getMessageBody().toString(); // 내용
            Log.d(TAG, "contents : " + contents);
            Date date = new Date(messages[0].getTimestampMillis());
            Log.d(TAG, "received date : " + date);

            sendToActivity(context, sender, contents, date);
        }
    }

    private void sendToActivity(Context context, String sender, String contents, Date date){
        Intent intent = new Intent(context, SMSActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP
                        | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("sender", sender);
        intent.putExtra("contents", contents);
        intent.putExtra("date", format.format(date));
        context.startActivity(intent);
    }

    private SmsMessage[] parseSmsMessage(Bundle bundle){
        // SMS 데이터를 처리하는 국제 표준 프로토콜 SMPP
        // 그 안에 있는 SMS 데이터 이름은 pdus로 되어 있음
        Object[] objs = (Object[]) bundle.get("pdus");
        // SMS는 80바이트 이내로 여러 개를 받을 수 있으므로 배열 선언
        SmsMessage[] messages = new SmsMessage[objs.length];
        // SMS 메세지를 복원하는 반복문
        for(int i = 0; i < objs.length; i++){
            // 마시멜로 버전이거나 마시멜로 버전 이후라면
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                String format = bundle.getString("format");
                messages[i] = SmsMessage.createFromPdu((byte[]) objs[i], format);
            }
            else {
                // pdus로 뽑아낸 데이터를 던진다
                messages[i] = SmsMessage.createFromPdu((byte[]) objs[i]);
            }
        }
        return messages;
    }

}
