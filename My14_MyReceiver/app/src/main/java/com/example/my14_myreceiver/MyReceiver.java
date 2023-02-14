package com.example.my14_myreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "main:MyReceiver";

    // 데이터의 날짜 형태를 내가 원하는 형태로 변형시키기 위한 변수
    public SimpleDateFormat dateFormat =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    //                   메인 환경설정 파일, 메세지 수신 데이터
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: 호출됨");

        // Bundle : 내부적으로 데이터를 저장할수 있는 객체
        // 메세지 수신데이터인 intent에서 bundle객체에 저장
        Bundle bundle = intent.getExtras();
        SmsMessage[] messages = parseSmsMessage(bundle);

        if(messages != null && messages.length > 0){
            Log.d(TAG, "onReceive: SMS를 수신하였습니다");

            // 보낸사람 전화번호
            String sender = messages[0].getOriginatingAddress();
            Log.d(TAG, "sender: " + sender);

            // 보낸 날짜와 시간
            Date receivedDate = new Date(messages[0].getTimestampMillis());
            Log.d(TAG, "receivedDate: " + receivedDate);

            // 메세지 내용
            String contents = messages[0].getMessageBody();
            Log.d(TAG, "contents: " + contents);

            // 인텐트를 만들어서 데이터를 넣은후 액티비티를 띄운다
            Intent disIntent = new Intent(context, SmsDisActivity.class);
            // 액티비티가 아닌곳에서 인텐트를 만들어 새창을 띄우고자 할때
            // FLAG_ACTIVITY_NEW_TASK를 플래그에 추가해야 한다
            disIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
            // SINGLE_TOP 은 똑같은 화면이 이미 띄워져 있는 상태라면
            // 그 화면을 재사용하겠다
            // 재사용하는 화면에 데이터 갱신을 위해  onNewIntent를
            // 오버라이드 해서 거기서 작업을 한다
                            Intent.FLAG_ACTIVITY_SINGLE_TOP);

            disIntent.putExtra("sender", sender);
            disIntent.putExtra("receivedDate",
                    dateFormat.format(receivedDate));
            disIntent.putExtra("contents", contents);
            // 액티비티가 아닌경우는 context가 없이 새창을 띄울수 없다
            context.startActivity(disIntent);



        }

    }

    // bundle 객체에 있는 데이터중 메세지만 파싱하여 리턴시킴
    private SmsMessage[] parseSmsMessage(Bundle bundle) {
        Object[] objs = (Object[]) bundle.get("pdus");
        SmsMessage[] messages = new SmsMessage[objs.length];

        for(int i=0; i< objs.length; i++){
            // API 버전이 23이상이면
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                String format = bundle.getString("format");
                messages[i] = SmsMessage
                        .createFromPdu((byte[]) objs[i], format);
            // API버전이 23 미만이면
            }else {
                messages[i] = SmsMessage.createFromPdu((byte[]) objs[i]);
            }
        }

        return messages;
    }




}