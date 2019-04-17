package com.example.karee.androidproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class EditQuantity extends AppCompatActivity {

    private Button confirm;
    private SeekBar seek;
    private int AvailableQuantity,CurrentQuantity,TotalQuantity,UnitPrice,CurrentPrice;
    private String AvailableQuantityS , CurrentQuantityS , UnitPriceS;
    TextView AQ,CQ,PP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_quantity);
        confirm = (Button)findViewById(R.id.confirmqq);
        seek = (SeekBar)findViewById(R.id.seekbarq);

        AvailableQuantityS = getIntent().getStringExtra("AvailableQuantity");
        CurrentQuantityS = getIntent().getStringExtra("CurrentQuantity");
        UnitPriceS = getIntent().getStringExtra("UnitPrice");
        UnitPrice = Integer.parseInt(UnitPriceS);
        AvailableQuantity = Integer.parseInt(AvailableQuantityS);
        CurrentQuantity = Integer.parseInt(CurrentQuantityS);
        TotalQuantity = AvailableQuantity + CurrentQuantity;
        CurrentPrice = UnitPrice*CurrentQuantity;

        AQ = (TextView)findViewById(R.id.aqq);
        CQ = (TextView)findViewById(R.id.cqq);
        PP = (TextView)findViewById(R.id.pQqQ);

        AQ.setText(AvailableQuantityS);
        CQ.setText(CurrentQuantityS);
        PP.setText(Integer.toString(CurrentPrice));

        seek.setProgress(CurrentQuantity);
        seek.setMax(TotalQuantity);

        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                CurrentQuantity = progress;
                AvailableQuantity = TotalQuantity-CurrentQuantity;
                CurrentPrice = UnitPrice*CurrentQuantity;

                AQ.setText(Integer.toString(AvailableQuantity));
                CQ.setText(Integer.toString(CurrentQuantity));
                PP.setText(Integer.toString(CurrentPrice));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditQuantity.this,ShoppingActivity.class);
                intent.putExtra("CurrentQuantity",Integer.toString(CurrentQuantity));
                intent.putExtra("AvailableQuantity",Integer.toString(AvailableQuantity));
                intent.putExtra("TotalUserPrice",Integer.toString(CurrentPrice));
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
