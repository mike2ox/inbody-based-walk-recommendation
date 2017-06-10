package com.example.moonh.indody_exercise;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.app.Activity;
import android.view.Menu;

import java.io.*;
import java.net.*;


/**
 * Created by moonh on 2017-05-29.
 * project name :  inbody-based-walk-recommendation
 * brief : 회원가입할 때 발생하는 액티비티를 다루는 class
 */

public class JoinActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
    }


        //안드로이드에서 서버로 데이터를 전송하는 클래스
    public class registDB extends AsyncTask<Void, Integer, Void> {

           String user_email, user_name,user_password,user_age,user_gender,user_height,user_weight;
           String data = "";


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

}
