package com.example.registration;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity2 extends AppCompatActivity {

    public static String userID;
    public static String userperiod;
    private AlertDialog dialog; // 알림창

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        userID = getIntent().getStringExtra("userID");
        userperiod = getIntent().getStringExtra("userPeriod");
        final Button EntranceButton = (Button) findViewById(R.id.EntranceButton);
        final Button ExitButton = (Button) findViewById(R.id.ExitButton);
        final Button LogoutButton = (Button) findViewById(R.id.logoutbutton);

        //입장하기 버튼 - 누르면 userID를 서버에 보내고 서버는 boolean값을 바꿔줌.
        EntranceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EntranceButton.setEnabled(false);
                ExitButton.setEnabled(true);
                //결과 출력
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {   //해당 결과 받아옴

                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {  //입장이 성공했을 때

                                Toast.makeText(MainActivity2.this,"입장 완료",Toast.LENGTH_SHORT).show();
                                //congestion.condition(Integer.parseInt(TextView.getText().toString())+1);
                                /*AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                dialog = builder.setMessage("입장에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();  //다이얼로그 실행
                                //Intent로 메인액티비티로 넘겨줌
                                Intent mainIntent = new Intent(MainActivity.this, MainActivity2.class);
                                MainActivity.this.startActivity(mainIntent);*/
                                //finish();
                            }
                            else{
                                Toast.makeText(MainActivity2.this,"입장 실패",Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e)  //예외처리
                        {
                            e.printStackTrace();
                        }
                    }
                };
                // EntranceRequest 클래스의 queue 형태로 DB에 전달
                EntranceRequest EntranceRequest = new EntranceRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity2.this);  //큐에 담음
                queue.add(EntranceRequest);
            }
        });

        //퇴장하기 버튼 (입장과 반대)
        ExitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EntranceButton.setEnabled(true);
                ExitButton.setEnabled(false);
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {   //해당 결과 받아옴
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {  //퇴장이 성공했을 때
                                //congestion.condition(Integer.parseInt(TextView.getText().toString())-1);
                                /*AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                dialog = builder.setMessage("퇴장에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();  //다이얼로그 실행

                                //Intent로 메인액티비티로 넘겨줌
                                Intent mainIntent = new Intent(MainActivity.this, MainActivity2.class);
                                MainActivity.this.startActivity(mainIntent);*/
                                //finish();
                            }
                            else{
                                Toast.makeText(MainActivity2.this,"실패",Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e)  //예외처리
                        {
                            e.printStackTrace();
                        }
                    }
                };
                // ExitRequest 클래스의 queue 형태로 DB에 전달
                ExitRequest ExitRequest = new ExitRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity2.this);  //큐에 담음
                queue.add(ExitRequest);
            }
        });

        LogoutButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Toast.makeText(MainActivity2.this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                Intent Logoutintent = new Intent(MainActivity2.this, LoginActivity.class);
                MainActivity2.this.startActivity(Logoutintent);
                finish();
            }
        });
    }

}