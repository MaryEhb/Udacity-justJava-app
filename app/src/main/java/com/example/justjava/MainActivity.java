package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */

    int quantity = 1;


    public void submitOrder(View view) {

        CheckBox creamCheckElement = (CheckBox) findViewById(R.id.check_cream);
        boolean whippedCreamCheck = creamCheckElement.isChecked();

        CheckBox chocolateCheckElement = (CheckBox) findViewById(R.id.check_chocolate);
        boolean chocolateCheck = chocolateCheckElement.isChecked();

        EditText customerNameElement = (EditText) findViewById(R.id.customer);
        String name = customerNameElement.getText().toString();

        String priceMessage = createOrderSummary(name, quantity, calcPrice(whippedCreamCheck, chocolateCheck), whippedCreamCheck, chocolateCheck);

//        displayMessage(priceMessage);

//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse("geo:47.6, -122.3"));
//        if(intent.resolveActivity(getPackageManager()) != null){
//            startActivity(intent);
//        }

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Java order from " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private String displayPrice(int number) {

        return NumberFormat.getCurrencyInstance().format(number);
    }


    public void increment(View view) {
        if (quantity < 100)
            quantity++;
        display(quantity);
    }

    public void decrement(View view) {
        if (quantity > 1)
            quantity--;
        display(quantity);
    }

    public void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    private int calcPrice(boolean cream, boolean choco) {
        int pricePerCup = 5;
        if (cream)
            pricePerCup += 1;
        if (choco)
            pricePerCup += 2;

        return quantity * pricePerCup;
    }

    private String createOrderSummary(String name, int quantity, int price, boolean whippedCreamCheck, boolean choco) {
        return getString(R.string.name) + ": " + name + "\n"
                + getString(R.string.add_cream) + whippedCreamCheck + "\n"
                + getString(R.string.add_choco) + choco + "\n"
                + getString(R.string.total_count) + quantity + "\n"
                + getString(R.string.total_order) + displayPrice(price)
                + "\n" + getString(R.string.thank_you);
    }

}