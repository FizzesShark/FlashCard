package com.example.xbug2.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;

public class AddCardSetActivity extends AppCompatActivity {

    private Spinner ColourPicker;
    private DatabaseReference mDatabase;
    private String curColor;
    private EditText setName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card_set);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("CardSet");

        ColourPicker = (Spinner) findViewById(R.id.ColourPicker);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.color_values, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ColourPicker.setAdapter(adapter);
        ColourPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                curColor = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView){
            }
        });

        setName = (EditText) findViewById(R.id.CardSetName);
    }

    public void save(View view){
        String name = setName.getText().toString();

        if (setName == null || setName.length() == 0){
            Toast.makeText(this, "You did not enter a set name", Toast.LENGTH_SHORT).show();
            return;
        }else if (setName.length() > 30){
            Toast.makeText(this, "Name must be less than 30 characters", Toast.LENGTH_SHORT).show();;
            return;
        }
        DatabaseReference newCardSet = mDatabase.push();
        newCardSet.child("name").setValue(name);
        newCardSet.child("color").setValue(curColor);

        Intent cardSetListActivity = new Intent(AddCardSetActivity.this, CardSetListActivity.class);
        startActivity(cardSetListActivity);
    }

    public void cancel(View view){
        Intent cardSetListActivity = new Intent(AddCardSetActivity.this, CardSetListActivity.class);
        startActivity(cardSetListActivity);
    }
}
