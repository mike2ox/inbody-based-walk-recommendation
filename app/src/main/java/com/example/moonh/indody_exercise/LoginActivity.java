package com.example.moonh.indody_exercise;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by moonh on 2017-05-29.
 * project name :  inbody-based-walk-recommendation
 * brief : 제일 처음 로그인할 때 발생하는 액티비티를 다루는 class
 */

public class LoginActivity extends AppCompatActivity{
    EditText User_email, User_PW;
    SharedPreferences pref; //
    SharedPreferences.Editor editor;        //이부분 json이나 parse부분으로 대체 가능하지 않나?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        User_email = (EditText)findViewById(R.id.Input_Email);
        User_PW = (EditText)findViewById(R.id.Input_Pw);

        String id = User_email.getText().toString();        //edittext로 받은걸 string으로 저장
        String password = User_PW.getText().toString();
        Boolean validation = LoginValidation(id,password);

        if(validation){
            Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_LONG).show();
        }

    }

    private boolean LoginValidation(String ID, String Password){
        if(pref.getString("id","").equals(ID) && pref.getString("pw","").equals(Password)) {
            // login success
            return true;
        } else if (pref.getString("id","").equals(null)){
            // sign in first
            Toast.makeText(LoginActivity.this, "Please Sign in first", Toast.LENGTH_LONG).show();
            return false;
        } else {
            // login failed
            return false;
        }
    }




}

