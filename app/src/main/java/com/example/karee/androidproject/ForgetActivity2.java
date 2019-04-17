package com.example.karee.androidproject;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ForgetActivity2 extends AppCompatActivity {

    private TextView QuestionView;
    private EditText AnswerView;
    private Button Next;
    private String Email,DBQuestion,CorrectAnswer;
    private User US;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget2);
        Email = getIntent().getStringExtra("Email");
        QuestionView = (TextView)findViewById(R.id.QView2);
        AnswerView = (EditText)findViewById(R.id.AnswerView2);
        US = new User(getApplicationContext());
        Cursor cursor = US.showUser(Email);
        DBQuestion = cursor.getString(4);
        CorrectAnswer = cursor.getString(5);
        QuestionView.setText(DBQuestion);

        Next = (Button)findViewById(R.id.FPF2);
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UserAnswer = AnswerView.getText().toString();
                if(!(UserAnswer.equals(CorrectAnswer)))
                {
                    Toast.makeText(getApplicationContext(), "Invalid answer", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent = new Intent(ForgetActivity2.this,ForgetActivity3.class);
                    intent.putExtra("Email",Email);
                    startActivityForResult(intent,12);
                }
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==12)
        {
            if(resultCode==RESULT_OK)
            {
                Intent intent = new Intent(ForgetActivity2.this,ForgetActivity.class);
                setResult(RESULT_OK,intent);
                finish();
            }
        }
    }
}
