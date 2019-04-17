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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShoppingActivity extends AppCompatActivity {

    private String email,Cart,ProductName,ProductID,ProductQuantity,ProductView;
    private ListView ShopCart;
    private ArrayAdapter<String> SCArr;
    private  int CurrentQuantity,CurrentPrice,UnitPrice,PriceViewI;
    private UserCart UC;
    private Product PR;
    private Button Confirm;
    private TextView PriceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        email = getIntent().getStringExtra("email");
        ShopCart = (ListView)findViewById(R.id.SCLIST);
        SCArr = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        ShopCart.setAdapter(SCArr);
        UC = new UserCart(getApplicationContext());
        PR = new Product(getApplicationContext());
        Confirm = (Button)findViewById(R.id.Confirm1);
        PriceView = (TextView)findViewById(R.id.PriceViewT);
        Cursor cursor = UC.RetUser(email);
        Cart = UpdateCart(cursor.getString(1));
        UC.UpdateCart(1,email,Cart);
        SetListData();
        registerForContextMenu(ShopCart);
        ShopCart.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ProductView = ((TextView)view).getText().toString();
                ProductName = "";
                CurrentPrice = 0;
                CurrentQuantity = 0;
                int i = 0;
                while (true)
                {
                    if(ProductView.charAt(i)==' '&&ProductView.charAt(i+1)==' ')break;
                    ProductName+=ProductView.charAt(i);
                    i++;
                }
                i+=10;
                while(ProductView.charAt(i)!=' ')
                {
                    CurrentQuantity = (CurrentQuantity*10)+(Character.getNumericValue(ProductView.charAt(i)));
                    i++;
                }
                i+=10;
                for(int j = i;j < ProductView.length();j++)CurrentPrice = (CurrentPrice*10)+(Character.getNumericValue(ProductView.charAt(j)));
                UnitPrice = (CurrentPrice/CurrentQuantity);
                Cursor cursor = PR.GetProductINFO(2,"",ProductName,"");
                ProductID = cursor.getString(0);
                ProductQuantity = cursor.getString(1);
                return false;
            }
        });
        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((UC.RetUser(email)).getString(1)).equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Shop cart is empty",Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent(ShoppingActivity.this, AddressActivity.class);
                    startActivityForResult(intent, 50);
                }
            }
        });
    }

    //for updating cart
    public String UpdateCart(String Cart)
    {
        String NewCart = "";
        if(Cart.length()>0) {
            for (int i = 0; i < Cart.length(); i += 3) {
                if (NewCart.length() > 0) {
                    int BOOLE = 0;
                    for (int j = 0; j < NewCart.length(); j += 3) {
                        if (Cart.substring(i, i + 2).equals(NewCart.substring(j, j + 2))) BOOLE = 1;
                    }
                    if (BOOLE == 1) continue;
                }
                NewCart += Cart.substring(i, i + 2);
                int Quantity = Character.getNumericValue(Cart.charAt(i + 2));
                for (int j = i + 3; j < Cart.length(); j+=3) {
                    if (Cart.substring(j, j + 2).equals(Cart.substring(i, i + 2)))
                        Quantity += Character.getNumericValue(Cart.charAt(j + 2));
                }
                NewCart += (char)(Quantity+'0');
            }
        }
        return NewCart;
    }

    //for viewing cart
    public void SetListData()
    {
        PriceViewI = 0;
        for(int j = 0;j < Cart.length();j+=3)
        {
            String Info = "";
            String ItemId = Cart.substring(j,j+2);
            Cursor cursor2 = PR.GetProductINFO(1,ItemId,"","");
            Info += cursor2.getString(0)+"          "+Cart.charAt(j+2)+"          "
                    +Integer.toString((Integer.parseInt(cursor2.getString(1)))*(Character.getNumericValue(Cart.charAt(j+2))));
            PriceViewI += (Integer.parseInt(cursor2.getString(1)))*(Character.getNumericValue(Cart.charAt(j+2)));
            SCArr.add(Info);
        }
        PriceView.setText(Integer.toString(PriceViewI));
    }

    //for removing item from cart
    public void RemoveFromCart()
    {
        String NewCart = "";
        for(int i = 0;i < Cart.length();i+=3)
        {
            if(Cart.substring(i,i+2).equals(ProductID))
            {
                continue;
            }
            NewCart += Cart.charAt(i);
            NewCart+=Cart.charAt(i+1);
            NewCart+=Cart.charAt(i+2);
        }
        Cart = NewCart;
        UserCart UC = new UserCart(getApplicationContext());
        UC.UpdateCart(1,email,Cart);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.cartmenu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.EQuantity2:
                Intent intent = new Intent(ShoppingActivity.this,EditQuantity.class);
                intent.putExtra("CurrentQuantity",Integer.toString(CurrentQuantity));
                intent.putExtra("AvailableQuantity",ProductQuantity);
                intent.putExtra("UnitPrice",Integer.toString(UnitPrice));
                startActivityForResult(intent,9);
                return true;
            case R.id.RQuantity2:
                RemoveFromCart();
                SCArr.remove(ProductView);
                SCArr.notifyDataSetChanged();
                PR.UpdateProduct(ProductID,-CurrentQuantity);
                PriceViewI -= CurrentPrice;
                PriceView.setText(Integer.toString(PriceViewI));
                return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==9)
        {
            if(resultCode==RESULT_OK)
            {
                int UserQuantity = Integer.parseInt(data.getStringExtra("CurrentQuantity"));
                int ProductDeletedQuantity = Integer.parseInt(ProductQuantity)-Integer.parseInt(data.getStringExtra("AvailableQuantity"));
                PR.UpdateProduct(ProductID,ProductDeletedQuantity);
                SCArr.remove(ProductView);
                SCArr.notifyDataSetChanged();
                PriceViewI -= CurrentPrice;
                PriceViewI += UserQuantity*UnitPrice;
                PriceView.setText(Integer.toString(PriceViewI));
                if(UserQuantity==0)RemoveFromCart();
                else
                {
                    String NewCart = "";
                    for(int i = 0;i < Cart.length();i+=3)
                    {
                        if(Cart.substring(i,i+2).equals(ProductID))
                        {
                            NewCart += Cart.charAt(i);
                            NewCart += Cart.charAt(i+1);
                            NewCart+=((char)(UserQuantity+'0'));
                            String Info = ProductName+"          "+((char)(UserQuantity+'0'))+"          " +data.getStringExtra("TotalUserPrice");
                            SCArr.add(Info);
                            continue;
                        }
                        NewCart+=Cart.charAt(i);
                        NewCart+=Cart.charAt(i+1);
                        NewCart+=Cart.charAt(i+2);
                    }
                    Cart = NewCart;
                    UC.UpdateCart(1,email,Cart);
                }
            }
        }
        if(requestCode==50)
        {
            if(resultCode==RESULT_OK)
            {
                Intent intent = new Intent(ShoppingActivity.this,AppActivity.class);
                setResult(RESULT_OK,intent);
                finish();
            }
        }
    }
}
