package com.example.ruslan.draganddroprectapplication;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyView view = (MyView)findViewById(R.id.my_view);
        // заполняем квадратиками
        view.addRect(new Rect(50, 350, 150, 450));
        view.addRect(new Rect(100, 20, 200, 120));
        view.addRect(new Rect(150, 200, 250, 300));
        view.addRect(new Rect(300, 200, 400, 300));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }
}
