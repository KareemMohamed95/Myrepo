package com.example.karee.androidproject;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgetActivity extends AppCompatActivity {

    private EditText Emailaddress;
    private Button NextFP;
    private User US;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        Emailaddress = (EditText)findViewById(R.id.EAField);
        US = new User(getApplicationContext());
        NextFP = (Button) findViewById(R.id.FPF1);
        NextFP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = Emailaddress.getText().toString();
                String Pass = US.CheckUserEmail(Email);
                if (Pass.equals("")) {
                    Toast.makeText(getApplicationContext(), "Invalid Email address", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(ForgetActivity.this, ForgetActivity2.class);
                    intent.putExtra("Email", Email);
                    startActivityForResult(intent, 11);
                }
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==11)
        {
            if(resultCode==RESULT_OK)
            {
                Intent intent = new Intent(ForgetActivity.this,MainActivity.class);
                setResult(RESULT_OK,intent);
                finish();
            }
        }
    }
}
