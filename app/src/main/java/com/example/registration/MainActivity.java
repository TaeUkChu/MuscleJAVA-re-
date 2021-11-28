package com.example.registration;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    public static String userID;
    private AlertDialog dialog; // 알림창

    ImageView imageView;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    TextView TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userID = getIntent().getStringExtra("userID");

        final Button EntranceButton = (Button) findViewById(R.id.EntranceButton);
        final Button RefactButton = (Button) findViewById(R.id.RefactButton);
        final Button LogoutButton = (Button) findViewById(R.id.logoutbutton);

        imageView = (ImageView) findViewById(R.id.imageView);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        imageView4 = (ImageView) findViewById(R.id.imageView4);
        imageView5 = (ImageView) findViewById(R.id.imageView5);

        TextView = (TextView) findViewById(R.id.numbertext);

        //입장하기 버튼 - 누르면 userID를 서버에 보내고 서버는 boolean값을 바꿔줌.
        EntranceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //결과 출력
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {   //해당 결과 받아옴

                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {  //입장이 성공했을 때
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                dialog = builder.setMessage("입장에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();  //다이얼로그 실행

                                //Intent로 메인액티비티로 넘겨줌
                                Intent mainIntent = new Intent(MainActivity.this, MainActivity2.class);
                                MainActivity.this.startActivity(mainIntent);
                                finish();
                            }
                        } catch (Exception e)  //예외처리
                        {
                            e.printStackTrace();
                        }
                    }


                };
                // EntranceRequest 클래스의 queue 형태로 DB에 전달
                EntranceRequest EntranceRequest = new EntranceRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);  //큐에 담음
                queue.add(EntranceRequest);
            }
        });
        //새로 고침 버튼을 이용한 데이터 요청 + 가져오기
        RefactButton.setOnClickListener(new View.OnClickListener() {
            /*
            public void onClick(View v) {
                String url = "http://musclejava.cafe24.com/EntranceSum.php" + userID;
                StringRequest sumRequest = new StringRequest(Request.Method.GET, url, response -> {
                    try
                    {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i<jsonArray.length(); i++)
                        {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String sum = jsonObject.getString("sum");
                            TextView.setText(sum + "명");
                        }
                    }
                    catch( JSONException e)
                    {
                        e.printStackTrace();
                    }
                }, error -> Toast.makeText(MainActivity.this, "실패",Toast.LENGTH_SHORT).show());

                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                requestQueue.add(sumRequest);
            }*/

            public void onClick(View v) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {   //해당 결과 받아옴

                            JSONObject jsonResponse = new JSONObject(response);
                            int sum = jsonResponse.getInt("sum");
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {  //입장이 성공했을 때
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                dialog = builder.setMessage("입장에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();  //다이얼로그 실행

                                String temp;
                                temp = Integer.toString(sum);

                                TextView.setText(temp);
                                TextView.setTextColor(Color.parseColor("#ffcc0000"));
                                finish();
                            }
                            else{
                                Toast.makeText(MainActivity.this,"입장에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e)  //예외처리
                        {
                            e.printStackTrace();
                        }
                    }
                };
                // EntranceRequest 클래스의 queue 형태로 DB에 전달
                EntranceSumRequest EntranceSumRequest = new EntranceSumRequest(responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);  //큐에 담음
                queue.add(EntranceSumRequest);
            }
        });



        //로그 아웃 버튼
        LogoutButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                Intent Logoutintent = new Intent(MainActivity.this, LoginActivity.class);
                MainActivity.this.startActivity(Logoutintent);
                finish();
             }
        });

        //백그라운드 쓰레드를 이용한 데이터 파싱

       new BackgroundTask().execute();
    }
    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;

        protected void onPreExecute(){
            target = "http://musclejava.cafe24.com/EntranceSum.php";
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine()) != null){
                    stringBuilder.append(temp+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
            @Override
            public void onProgressUpdate(Void... values) {
            super.onProgressUpdate();
            }

        @Override
        public void onPostExecute(String result){
            try{
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("response");

            int count = 0;
            String sum;

         while(count < jsonArray.length())
       {
            JSONObject object = jsonArray.getJSONObject(count);
            sum = object.getString("sum");
            int percent = (10 - Integer.parseInt(sum))*10;
            int temp = percent/20;
            switch (temp) {
               case 0:
                   imageView.setVisibility(View.VISIBLE);
                   imageView2.setVisibility(View.INVISIBLE);
                   imageView3.setVisibility(View.INVISIBLE);
                   imageView4.setVisibility(View.INVISIBLE);
                   imageView5.setVisibility(View.INVISIBLE);
                   TextView.setTextColor(Color.parseColor("#ffcc0000")); //빨강
                   break;
               case 1:
                   imageView.setVisibility(View.INVISIBLE);
                   imageView2.setVisibility(View.VISIBLE);
                   imageView3.setVisibility(View.INVISIBLE);
                   imageView4.setVisibility(View.INVISIBLE);
                   imageView5.setVisibility(View.INVISIBLE);
                   TextView.setTextColor(Color.parseColor("#ffff8800")); //주황
                   break;
               case 2:
                   imageView.setVisibility(View.INVISIBLE);
                   imageView2.setVisibility(View.INVISIBLE);
                   imageView3.setVisibility(View.VISIBLE);
                   imageView4.setVisibility(View.INVISIBLE);
                   imageView5.setVisibility(View.INVISIBLE);
                   TextView.setTextColor(Color.parseColor("#ffffbb33")); //노랑
                   break;
               case 3:
                   imageView.setVisibility(View.INVISIBLE);
                   imageView2.setVisibility(View.INVISIBLE);
                   imageView3.setVisibility(View.INVISIBLE);
                   imageView4.setVisibility(View.VISIBLE);
                   imageView5.setVisibility(View.INVISIBLE);
                   TextView.setTextColor(Color.parseColor("#ff99cc00")); //연두
                   break;
               case 4:
                   imageView.setVisibility(View.INVISIBLE);
                   imageView2.setVisibility(View.INVISIBLE);
                   imageView3.setVisibility(View.INVISIBLE);
                   imageView4.setVisibility(View.INVISIBLE);
                   imageView5.setVisibility(View.VISIBLE);
                   TextView.setTextColor(Color.parseColor("#ff669900")); //초록
                   break;
           }
            TextView.setText("["+ sum + "명 / 10명] \n" +"최대 수용:"+ percent +"%");

            /* 오류뜸 (이유모름)
            Congestion congestion = new Congestion(TextView, imageView, imageView2, imageView3, imageView4, imageView5, percent);
            congestion.show();*/
           count++;
       }

            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}