package com.example.androidwork7_22;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 11;
    private static final int MY_PERMISSIONS_REQUEST_SMS_PHONE = 12;
    Button callbtn;
    Button smsbtn;
    EditText numbertxt;
    EditText messagetxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        callbtn = findViewById(R.id.callbtn);
        smsbtn = findViewById(R.id.smsbtn);
        numbertxt = findViewById(R.id.numbertxt);
        messagetxt = findViewById(R.id.messagetxt);
        callbtn.setOnClickListener(this::onClick);
        smsbtn.setOnClickListener(this::onClick);
    }
    private void callByNumber() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
        } else {
            Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + numbertxt.getText().toString()));
            startActivity(dialIntent);
        }
    }
    private void sendSms() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SMS_PHONE);
        } else {
            SmsManager smgr = SmsManager.getDefault();
            if (numbertxt.getText().toString().equals("") || messagetxt.getText().toString().equals("")) {
                Toast.makeText(this, "Поле номер или сообщеные пустое", Toast.LENGTH_SHORT).show();
            } else {
                smgr.sendTextMessage(numbertxt.getText().toString(), null, messagetxt.getText().toString(), null, null);
                Toast.makeText(this, "Сообщение отправлено", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callByNumber();
                } else {
                    Toast.makeText(this, "Нет разрешение", Toast.LENGTH_SHORT).show();
                }
                break;

            case MY_PERMISSIONS_REQUEST_SMS_PHONE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendSms();
                } else {
                    Toast.makeText(this, "Нет разрешения", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.callbtn:
                callByNumber();
                break;
            case R.id.smsbtn:
                sendSms();
                break;
        }
    }
}