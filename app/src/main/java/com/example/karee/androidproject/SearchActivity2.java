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

import java.util.ArrayList;

public class SearchActivity2 extends AppCompatActivity {

    private ListView List;
    private ArrayAdapter<String> ListArr;
    private int PriceMin,PriceMax,QuantityMin,QuantityMax,ItemQuantity;
    private String email,NameSearch,LaptopC,MobileC,PrinterC,ItemName;
    private Product PR;
    private UserCart US;
    private ArrayList<String> SearchResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search2);
        List = (ListView)findViewById(R.id.ListViewSearch);
        ListArr = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        List.setAdapter(ListArr);
        registerForContextMenu(List);
        PR = new Product(getApplicationContext());
        US = new UserCart(getApplicationContext());

        email = getIntent().getStringExtra("email");
        NameSearch = getIntent().getStringExtra("NameSearch");
        LaptopC = getIntent().getStringExtra("LaptopC");
        MobileC = getIntent().getStringExtra("MobileC");
        PrinterC = getIntent().getStringExtra("PrinterC");
        PriceMin = Integer.parseInt(getIntent().getStringExtra("PriceMin"));
        PriceMax = Integer.parseInt(getIntent().getStringExtra("PriceMax"));
        QuantityMin = Integer.parseInt(getIntent().getStringExtra("QuantityMin"));
        QuantityMax = Integer.parseInt(getIntent().getStringExtra("QuantityMax"));

        SearchResult = PR.SearchProducts(NameSearch,new String[]{MobileC,LaptopC,PrinterC},new int[]{PriceMin,PriceMax,QuantityMin,QuantityMax});
        SetData();

        List.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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
        String ProductType = (PR.GetProductINFO(2,"",ItemName,"")).getString(2);
        Intent intent = new Intent(SearchActivity2.this,AddProduct.class);
        intent.putExtra("ItemName",ItemName);
        intent.putExtra("category",ProductType);
        startActivityForResult(intent,40);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==40)
        {
            if(resultCode==RESULT_OK)
            {
                String ItemId = data.getStringExtra("ItemId");
                String ItemQuantity = data.getStringExtra("TotalQuantity");
                int DeletedQuantity = Integer.parseInt(ItemQuantity);
                US.UpdateCart(0,email,ItemId+ItemQuantity);
                PR.UpdateProduct(ItemId,DeletedQuantity);
                SetData();
                Toast.makeText(getApplicationContext(), "Products added successfully", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void SetData()
    {
        ListArr.clear();
        int Size = SearchResult.size();
        for(int i = 0;i < Size;i++)
        {
            String ItemName = SearchResult.get(i);
            String ItemQuantity;
            String ItemPrice;
            String Newdata = "";
            Cursor cursor = PR.GetProductINFO(2,"",ItemName,"");
            ItemQuantity = cursor.getString(1);
            ItemPrice = cursor.getString(3);
            Newdata += ItemName;
            Newdata += "          ";
            Newdata += ItemPrice;
            Newdata += "          ";
            Newdata += ItemQuantity;
            ListArr.add(Newdata);
        }
    }

}
