package com.example.karee.androidproject;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private String email;
    private String pass;
    private EditText EmailField;
    private EditText PasswordField;
    private CheckBox Rem;
    private Button enter;
    private RememberMe RM;
    private User US;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EmailField = (EditText)findViewById(R.id.LoginE);
        PasswordField = (EditText)findViewById(R.id.LoginP);
        enter = (Button)findViewById(R.id.LoginPP);
        Rem = (CheckBox)findViewById(R.id.Rem);

        US = new User(getApplicationContext());
        RM = new RememberMe(getApplicationContext());
        Cursor cursor = RM.RetRow();
        if((cursor.getString(0)).equals("1"))
        {
            EmailField.setText(cursor.getString(1));
            PasswordField.setText(cursor.getString(2));
            Rem.setChecked(true);
        }
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = EmailField.getText().toString();
                pass = PasswordField.getText().toString();
                String CorrectPass = US.CheckUserEmail(email);
                if(CorrectPass.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Invalid email address",Toast.LENGTH_LONG).show();
                }
                else
                {
                    if(!(pass.equals(CorrectPass)))Toast.makeText(getApplicationContext(),"Invalid password",Toast.LENGTH_LONG).show();
                    else
                    {
                        if(Rem.isChecked())
                        {
                          RM.DeleteRow();
                          RM.AddRow(1,email,pass);
                        }
                        else
                        {
                            RM.DeleteRow();
                            RM.AddRow(0,"","");
                        }
                        Intent intent = new Intent(LoginActivity.this,AppActivity.class);
                        intent.putExtra("email",email);
                        startActivity(intent);
                    }
                }
            }
        });
    }
}
