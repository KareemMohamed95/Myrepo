package com.example.karee.androidproject;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private Button CalenderBtn;
    private Button NextBtn;
    private EditText Username;
    private EditText EmailAddress;
    private EditText Password;
    private TextView Date;
    private User OS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        CalenderBtn = (Button)findViewById(R.id.CalBtn);
        NextBtn = (Button)findViewById(R.id.NextBtn);

        Username = (EditText)findViewById(R.id.Username);
        Password = (EditText)findViewById(R.id.Password);
        EmailAddress = (EditText)findViewById(R.id.Emailaddress);
        Date = (TextView)findViewById(R.id.DateView);

        OS = new User(getApplicationContext());
        CalenderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,CalenderActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        NextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Name = Username.getText().toString();
                String Pass = Password.getText().toString();
                String Email = EmailAddress.getText().toString();
                String BirthDate = Date.getText().toString();
                if(Name.equals("")||Pass.equals("")||Email.equals("")||BirthDate.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Some fields are blank", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(!(OS.CheckUserEmail(Email).equals("")))
                    {
                        Toast.makeText(getApplicationContext(), "email already exists", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Intent intent = new Intent(RegisterActivity.this,QuestionActivity.class);
                        startActivityForResult(intent,3);
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
        {
            if(resultCode==RESULT_OK)
            {
                String Birth = data.getStringExtra("date");
                Date.setText(Birth);
            }
        }
        if(requestCode==3)
        {
            if(resultCode==RESULT_OK)
            {
                String Question = data.getStringExtra("question");
                String Answer = data.getStringExtra("answer");
                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                intent.putExtra("user",Username.getText().toString());
                intent.putExtra("pass",Password.getText().toString());
                intent.putExtra("birth",Date.getText().toString());
                intent.putExtra("email",EmailAddress.getText().toString());
                intent.putExtra("question",Question);
                intent.putExtra("answer",Answer);
                setResult(RESULT_OK,intent);
                finish();
            }
        }
    }
}
