package com.example.karee.androidproject;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InformationActivity extends AppCompatActivity {

    private TextView UserView;
    private TextView EmailView;
    private TextView DateView;
    private User OS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        UserView = (TextView)findViewById(R.id.userv);
        EmailView = (TextView)findViewById(R.id.emailv);
        DateView = (TextView)findViewById(R.id.birthv);

        OS = new User(getApplicationContext());
        String email = getIntent().getStringExtra("email");
        Cursor cursor = OS.showUser(email);
        UserView.setText(cursor.getString(1));
        EmailView.setText(email);
        DateView.setText(cursor.getString(3));
    }
}
