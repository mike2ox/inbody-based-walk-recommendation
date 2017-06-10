package com.example.moonh.indody_exercise;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.*;
import java.net.*;


/**
 * Created by moonh on 2017-05-29.
 * project name :  inbody-based-walk-recommendation
 * brief : 회원가입할 때 발생하는 액티비티를 다루는 class
 */

public class JoinActivity extends AppCompatActivity{
    EditText User_email,User_name, User_PW,User_age,User_gender,User_height,User_weight;
    String user_email, user_name,user_password,user_age,user_gender,user_height,user_weight;
    //유저의 이메일, 이름, 비밀번호, 나이, 성별, 키, 몸무게 입력받도록 해줌
    String data = "";

    SharedPreferences pref; //
    SharedPreferences.Editor editor;        //이부분 json이나 parse부분으로 대체 가능하지 않나?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        User_email = (EditText)findViewById(R.id.Input_Email);
        User_PW = (EditText)findViewById(R.id.Input_Pw);
        ////////////////////////////////////////레이아웃 받아서 이부분 수정해야함
        User_name = (EditText)findViewById(R.id.Input_Pw);
        User_age = (EditText)findViewById(R.id.Input_Pw);
        User_gender = (EditText)findViewById(R.id.Input_Pw);
        User_height = (EditText)findViewById(R.id.Input_Pw);
        User_weight = (EditText)findViewById(R.id.Input_Pw);


        user_email = User_email.getText().toString();        //edittext로 받은걸 string으로 저장
        user_password = User_PW.getText().toString();
        user_name = User_name.getText().toString();
        user_age = User_age.getText().toString();
        user_gender = User_gender.getText().toString();
        user_height = User_height.getText().toString();
        user_weight = User_weight.getText().toString();



    }
    /* onClick에서 정의한 이름과 똑같은 이름으로 생성 */
    //로그인 버튼이 수행될때 적용
    public void bt_Complete(View view){                 //레이아웃에서   android:onClick="bt_Login" 부분에서 ""를 함수이름으로 넣어줘야함
        JoinActivity.registDB rDB = new JoinActivity.registDB();
        rDB.execute();

        Toast.makeText(JoinActivity.this, "가입성공 성공", Toast.LENGTH_LONG).show();
        //Intent i = new Intent(getApplicationContext(), MainActivity.class);
        //startActivity(i);
        //////////////////이부분에서 회원가입이 완료됬을때 로그인 페이지로 다시 돌아가게 하려면 onresume과 onpause를 선언
        finish();
    }

        //안드로이드에서 서버로 데이터를 전송하는 클래스
    public class registDB extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... unused) {

/* 인풋 파라메터값 생성 */
            String param = "User_email=" +user_email+
                            "&User_name=" +user_name+
                    "&User_password=" +user_password+
                    "&User_age=" +user_age+
                    "&User_gender=" +user_gender+
                    "&User_height=" +user_height+
                    "&User_weight=" +user_weight+"";
            try {
            /* 서버연결 */
                URL url = new URL(
                        "http://http://172.16.203.130:3306/Walking_join.php");      //DB가 있는 웹서버 주소
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.connect();

/* 안드로이드 -> 서버 파라메터값 전달 */
                OutputStream outs = conn.getOutputStream();
                outs.write(param.getBytes("UTF-8"));
                outs.flush();
                outs.close();

/* 서버 -> 안드로이드 파라메터값 전달 */
                InputStream is = null;
                BufferedReader in = null;


                is = conn.getInputStream();
                in = new BufferedReader(new InputStreamReader(is), 8 * 1024);
                String line = null;
                StringBuffer buff = new StringBuffer();
                while ( ( line = in.readLine() ) != null ){
                    buff.append(line + "\n");
                }
                data = buff.toString().trim();

                //서버에서 응답
                Log.e("RECV DATA",data);

                if(data.equals("0")){
                    Log.e("RESULT","성공적으로 처리");
                }
                else{
                    Log.e("RESULT","에러발생~ Errcode ="+data);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

    }


    ///////////////6월 10일 17:50에 추가가
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
