package com.example.xbug2.flashcard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private ArrayAdapter aAdapter;
    private String[] buttons = {"My Flash Cards", "Recently Learned", "My Favourite"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
    }

    public void myCardSetClicked(View view){
        Intent startActivity = new Intent(MainActivity.this, CardSetListActivity.class);
        startActivity(startActivity);
    }
}
