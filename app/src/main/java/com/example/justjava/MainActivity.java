package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void increment(View view) {
        if (quantity ==100){
            Toast.makeText(this,"You cannot have more than 100 coffees",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity +1;
        display(quantity);
    }
    public void decrement(View view) {
        if (quantity ==1){
            Toast.makeText(this,"You cannot have less than 1 coffees",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity -1;
        display(quantity);
    }
    public void submitOrder(View view) {
        EditText nameField = (EditText)findViewById(R.id.name_field);
        String name = nameField.getText().toString();
        CheckBox whippedCreamCheckedBox = (CheckBox)findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckedBox.isChecked();

        CheckBox chocolateCheckedBox = (CheckBox)findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = whippedCreamCheckedBox.isChecked();

        int price = calculatePrice (hasWhippedCream, hasChocolate);
        String priceMessage= createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this

        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for "+ name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate){
        int basePrice =5;
        if (addWhippedCream) {
            basePrice = basePrice+1;
        }
        if (addChocolate) {
            basePrice = basePrice+2;
        }
        return quantity*basePrice;
    }
    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate){
        String priceMessage ="Name: "+ name;
        priceMessage += "\nAdd whipped cream? " +addWhippedCream;
        priceMessage += "\nAdd chocolate? " +addChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $ "+price;
        priceMessage += "\nThank you!";
        return priceMessage;
    }

    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}