package com.example.karee.androidproject;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewProducts extends AppCompatActivity {

    private String ProductType,ItemName,Email;
    private ListView ProductInfo;
    private ArrayAdapter<String> ProductArr;
    private UserCart US;
    private Product PR;
    private int ItemQuantity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_products);
        ProductType = getIntent().getStringExtra("product");
        Email = getIntent().getStringExtra("email");

        ProductInfo = (ListView)findViewById(R.id.ProductList1);
        ProductArr = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        ProductInfo.setAdapter(ProductArr);
        US = new UserCart(getApplicationContext());
        PR = new Product(getApplicationContext());
        SetData();
        registerForContextMenu(ProductInfo);
        ProductInfo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String data = ((TextView)view).getText().toString();
                ItemName = "";
                int i = 0;
                while(true)
                {
                    if(data.charAt(i)==' '&&data.charAt(i+1)==' ')break;
                    ItemName += data.charAt(i);
                    i++;
                }
                i+=10;
                while(true)
                {
                    if(data.charAt(i)==' '&&data.charAt(i+1)==' ')break;
                    i++;
                }
                i+=10;
                ItemQuantity = Character.getNumericValue(data.charAt(i));
                return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflator = new MenuInflater(this);
        inflator.inflate(R.menu.produtaction, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(ItemQuantity==0)
        {
            Toast.makeText(getApplicationContext(),"Out of stock",Toast.LENGTH_LONG).show();
            return true;
        }
        Intent intent = new Intent(ViewProducts.this,AddProduct.class);
        intent.putExtra("ItemName",ItemName);
        intent.putExtra("category",ProductType);
        startActivityForResult(intent,8);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==8)
        {
            if(resultCode==RESULT_OK)
            {
                String ItemId = data.getStringExtra("ItemId");
                String ItemQuantity = data.getStringExtra("TotalQuantity");
                int DeletedQuantity = Integer.parseInt(ItemQuantity);
                US.UpdateCart(0,Email,ItemId+ItemQuantity);
                PR.UpdateProduct(ItemId,DeletedQuantity);
                SetData();
                Toast.makeText(getApplicationContext(), "Products added successfully", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void SetData()
    {
        ProductArr.clear();
        PR = new Product(getApplicationContext());
        Cursor cursor = PR.GetProductINFO(0,"","",ProductType);
        while(!cursor.isAfterLast()) {
            String data = cursor.getString(1)+"          "+cursor.getString(2)+"          "+cursor.getString(3);
            ProductArr.add(data);
            cursor.moveToNext();
        }
    }
}
