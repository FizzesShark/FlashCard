package com.example.xbug2.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CardActivity extends AppCompatActivity {

    private String CardSetID;
    private DatabaseReference mDatabase;
    private TextView frontText;
    private TextView backText;
    private ArrayList<String> CardIDs;
    private int currentCardIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CardSetID = getIntent().getExtras().getString("CardSetID");

        CardIDs = getIntent().getExtras().getStringArrayList("CardIDs");

        if (currentCardIndex == -1){
            currentCardIndex = getIntent().getExtras().getInt("currentCardIndex");
        }

        String CardID = CardIDs.get(currentCardIndex);

        frontText = findViewById(R.id.cardFrontText);
        backText = findViewById(R.id.cardBackText);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("CardSet").child(CardSetID).child("cards");
        mDatabase.child(CardID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = (String) dataSnapshot.child("name").getValue();
                String definition = (String) dataSnapshot.child("description").getValue();
                frontText.setText(name);
                backText.setText(definition);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void unknown(View view){
        updateCard("Unknown");
    }

    public void known(View view){ updateCard("Known");}

    public void updateCard(String status){
        String CardID = CardIDs.get(currentCardIndex);
        mDatabase.child(CardID).child("status").setValue(status);
        if (currentCardIndex == CardIDs.size() - 1){
            Intent resultActivity = new Intent(CardActivity.this, ResultActivity.class);
            resultActivity.putExtra("CardSetID", CardSetID);
            startActivity(resultActivity);
        }else{
            currentCardIndex++;

            Intent cardActivity = new Intent(CardActivity.this, CardActivity.class);
            cardActivity.putStringArrayListExtra("CardIDs", CardIDs);
            cardActivity.putExtra("currentCardIndex", currentCardIndex);
            cardActivity.putExtra("CardSetID", CardSetID);
            startActivity(cardActivity);
        }
    }
}
