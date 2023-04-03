package com.example.localetextstarter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    EditText inputPrice;
    TextView resultPrice, country;
    Button countPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHelp();
            }
        });

        Date myDate = new Date();
        long expirationDate = myDate.getTime() +
                TimeUnit.DAYS.toMillis(5);
        myDate.setTime(expirationDate);

        String formatDate = DateFormat.getDateInstance().format(myDate);
        TextView expiredTextView = findViewById(R.id.date);
        expiredTextView.setText(formatDate);

        inputPrice = findViewById(R.id.inputHarga);
        resultPrice = findViewById(R.id.textTotalHarga);
        countPrice = findViewById(R.id.btnTotal);
        country = findViewById(R.id.country);

        //call method
        convertPrice();

    }

    /**
     * Shows the Help screen.
     */
    private void showHelp() {
        // Create the intent.
        Intent helpIntent = new Intent(this, HelpActivity.class);
        // Start the HelpActivity.
        startActivity(helpIntent);
    }

    /**
     * Creates the options menu and returns true.
     *
     * @param menu       Options menu
     * @return boolean   True after creating options menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Handles options menu item clicks.
     *
     * @param item      Menu item
     * @return boolean  True if menu item is selected.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle options menu item clicks here.
        int id = item.getItemId();
        if (id == R.id.action_help) {
            Intent intent = new Intent(this, HelpActivity.class);
            startActivity(intent);
            return true;
        }
//        menambah menu bahasa untuk menggantu bahasa yang digunakan
        else if (id == R.id.action_language){
            Intent languageIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(languageIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void convertPrice(){

        //get Resource Locale
        Locale currentLocale = getResources().getConfiguration().locale;

        //get country
        TextView country = findViewById(R.id.country);
        country.setText(currentLocale.getCountry());
        String lang = country.getText().toString();

        //Conditions when the button is clicked
        countPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double priceItem = Double.parseDouble(inputPrice.getText().toString());
                Double price100Item = priceItem * 100;
                //Convert Currency
                if (lang.equals("US")){
                    Double price = price100Item / 14968.65;
                    resultPrice.setText(NumberFormat.getCurrencyInstance(currentLocale).format(price));
                } else if (lang.equals("SA")) {
                    Double price = price100Item / 3989.17;
                    resultPrice.setText(NumberFormat.getCurrencyInstance(currentLocale).format(price));
                }else {
                    resultPrice.setText(NumberFormat.getCurrencyInstance(currentLocale).format(price100Item));
                }
            }
        });

    }
}