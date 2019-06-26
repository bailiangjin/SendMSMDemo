package com.bailiangjin.smsdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 发送短信示例Demo
 *
 * @author liangjin.bai
 * @date 19-6-25
 */
public class MainActivity extends AppCompatActivity {


    private EditText et_phone_number;
    private EditText et_sc_address;
    private EditText et_destination_port;
    private EditText et_msg;
    private TextView tv_binary;
    private Button btn_send_normal_msg;
    private Button btn_send_binary_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_phone_number = findViewById(R.id.et_phone_number);
        et_sc_address = findViewById(R.id.et_sc_address);
        et_destination_port = findViewById(R.id.et_destination_port);
        et_msg = findViewById(R.id.et_msg);
        tv_binary = findViewById(R.id.tv_binary);
        btn_send_normal_msg = findViewById(R.id.btn_send_normal_msg);
        btn_send_binary_msg = findViewById(R.id.btn_send_binary_msg);

        et_msg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tv_binary.setText(strToBinaryStr(s.toString()));
            }
        });

        btn_send_normal_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNormalMsg();

            }
        });
        btn_send_binary_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBinaryMsg();
            }
        });


    }


    private String strToBinaryStr(String str) {
        char[] chars = str.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            sb.append(Integer.toBinaryString(chars[i]) + " ");
        }
        return sb.toString();
    }

    public void sendNormalMsg() {
        // 1.获取电话号码和短信内容
        String phoneNum = et_phone_number.getText().toString().trim();
        String smsContent = et_msg.getText().toString().trim();
        String scAddressStr = et_sc_address.getText().toString().trim();
        String scAddress = TextUtils.isEmpty(scAddressStr) ? null : scAddressStr;
        if (paramInvalid(phoneNum)) {
            return;
        }
        // 2.获取发送短信的api
        SmsManager smsManager = SmsManager.getDefault();
        // 3.发送短信
        smsManager.sendMultipartTextMessage(phoneNum, scAddress, smsManager.divideMessage(smsContent), null, null);
        Toast.makeText(MainActivity.this, "已发送", Toast.LENGTH_SHORT).show();
    }

    public void sendBinaryMsg() {
        // 1.获取电话号码和短信内容
        String phoneNum = et_phone_number.getText().toString().trim();
        String smsContent = et_msg.getText().toString().trim();
        String scAddressStr = et_sc_address.getText().toString().trim();
        String destinationPortStr = et_destination_port.getText().toString().trim();
        if (paramInvalid(phoneNum)) {
            return;
        }

        String scAddress = TextUtils.isEmpty(scAddressStr) ? null : scAddressStr;
        short destinationPort = TextUtils.isEmpty(destinationPortStr) ? 16001 : Short.valueOf(destinationPortStr);

        // 2.获取发送短信的api
        SmsManager smsManager = SmsManager.getDefault();
        // 3.发送短信
        smsManager.sendDataMessage(phoneNum, scAddress, destinationPort, smsContent.getBytes(), null, null);
        Toast.makeText(MainActivity.this, "已发送", Toast.LENGTH_SHORT).show();
    }

    /**
     * 参数校验
     * @param phoneNum
     * @return
     */
    private boolean paramInvalid(String phoneNum) {
        if(TextUtils.isEmpty(phoneNum)){
            Toast.makeText(MainActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

}
