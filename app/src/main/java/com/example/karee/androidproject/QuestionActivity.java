package com.example.karee.androidproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class QuestionActivity extends AppCompatActivity {

    private Button SubmitBtn;
    private Spinner Questions;
    private EditText Answer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        SubmitBtn = (Button)findViewById(R.id.SubmitBtn);
        Questions = (Spinner)findViewById(R.id.Qspinner);
        Answer = (EditText)findViewById(R.id.AnswerText);

        SubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = Questions.getSelectedItem().toString();
                String answer = Answer.getText().toString();
                if(answer.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Answer can't be blank",Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent(QuestionActivity.this, RegisterActivity.class);
                    intent.putExtra("question", question);
                    intent.putExtra("answer", answer);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}
