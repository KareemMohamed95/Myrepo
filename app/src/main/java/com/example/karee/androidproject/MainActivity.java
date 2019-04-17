package com.example.karee.androidproject;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private  Button RegisterBtn;
    private  Button LoginBtn;
    private Button ForgetPassword;
    private Button BtnMap;
    private User OS;
    private UserCart UC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Intent intent = new Intent(MainActivity.this,Test.class);
        //startActivity(intent);
        RegisterBtn = (Button)findViewById(R.id.RegisterBtn);
        LoginBtn = (Button)findViewById(R.id.LoginBtn);
        ForgetPassword = (Button)findViewById(R.id.ForgetB);
        BtnMap = (Button)findViewById(R.id.BtnMap);
        OS = new User(this);
        UC = new UserCart(this);
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivityForResult(intent, 2);
            }
        });
        ForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ForgetActivity.class);
                startActivityForResult(intent,13);
            }
        });
        BtnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MapsActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2)
        {
            if(resultCode==RESULT_OK)
            {
                String user = data.getStringExtra("user");
                String email = data.getStringExtra("email");
                String pass = data.getStringExtra("pass");
                String birth = data.getStringExtra("birth");
                String question = data.getStringExtra("question");
                String answer = data.getStringExtra("answer");
                OS.addUser(email,user,pass,birth,question,answer);
                UC.addUser(email);
                Toast.makeText(getApplicationContext(),"Registration successful",Toast.LENGTH_LONG).show();;
            }
        }
        if(requestCode==13)
        {
            if(resultCode==RESULT_OK)
            {
                Toast.makeText(getApplicationContext(),"Password Updated Successfully",Toast.LENGTH_LONG).show();
            }
        }
    }
}
