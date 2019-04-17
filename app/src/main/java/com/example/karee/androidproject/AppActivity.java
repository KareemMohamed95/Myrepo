package com.example.karee.androidproject;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AppActivity extends AppCompatActivity {

    private String email;
    private Button PersonalInformation;
    private Button BrowseCategories;
    private Button ViewShoppingCart;
    private Button SearchAction;
    private UserCart US;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        email = getIntent().getStringExtra("email");
        US = new UserCart(getApplicationContext());
        BrowseCategories = (Button)findViewById(R.id.BrowseP);
        PersonalInformation = (Button)findViewById(R.id.PIB);
        ViewShoppingCart = (Button)findViewById(R.id.ViewCart3);
        SearchAction = (Button)findViewById(R.id.SearchSB);
        PersonalInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppActivity.this,InformationActivity.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });
        BrowseCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppActivity.this,CategoriesActivity.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });
        ViewShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppActivity.this,ShoppingActivity.class);
                intent.putExtra("email",email);
                startActivityForResult(intent,51);
            }
        });
        SearchAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppActivity.this,SearchActivity.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==51)
        {
            if(resultCode==RESULT_OK)
            {
                Toast.makeText(getApplicationContext(),"Order placement successful",Toast.LENGTH_LONG).show();
                US.UpdateCart(1,email,"");
            }
        }
    }
}
