package com.example.karee.androidproject;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgetActivity3 extends AppCompatActivity {

    private EditText NewPass,ConfirmPass;
    private String Email;
    private User US;
    private Button SubmitB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget3);
        Email = getIntent().getStringExtra("Email");
        US = new User(getApplicationContext());
        NewPass = (EditText)findViewById(R.id.PFField4);
        ConfirmPass = (EditText)findViewById(R.id.PFField5);

        SubmitB = (Button)findViewById(R.id.FPF3);
        SubmitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NewP = NewPass.getText().toString();
                String ConfirmP = ConfirmPass.getText().toString();
                if(!(NewP.equals(ConfirmP)))
                {
                    Toast.makeText(getApplicationContext(), "Passwords are different", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(NewP.equals(""))
                    {
                        Toast.makeText(getApplicationContext(),"Password can't be empty",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        US.UpdatePassword(Email,NewP);
                        Intent intent = new Intent(ForgetActivity3.this,ForgetActivity2.class);
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                }
            }
        });
    }
}
