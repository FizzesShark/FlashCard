package com.example.xbug2.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddCardActivity extends AppCompatActivity {

    private String CardSetID;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CardSetID = getIntent().getExtras().getString("CardSetID");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("CardSet").child(CardSetID).child("cards");
    }

    public void save(View view){
        EditText cardName = (EditText) findViewById(R.id.CardName);
        String name = cardName.getText().toString();

        EditText description = (EditText) findViewById(R.id.Description);
        String descript = description.getText().toString();

        DatabaseReference newCard = mDatabase.push();
        newCard.child("name").setValue(name);
        newCard.child("description").setValue(descript);
        newCard.child("status").setValue("Unknown");

        Intent cardListActivity = new Intent(AddCardActivity.this, CardListActivity.class);
        cardListActivity.putExtra("CardSetID", CardSetID);
        startActivity(cardListActivity);
    }
}
