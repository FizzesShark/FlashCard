package com.example.xbug2.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CardListActivity extends AppCompatActivity {

    private String CardSetID;
    private DatabaseReference mDatabase;
    private RecyclerView cardListView;
    private ArrayList<String> CardIDs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CardSetID = getIntent().getExtras().getString("CardSetID");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addCardActivity = new Intent(CardListActivity.this, AddCardActivity.class);
                addCardActivity.putExtra("CardSetID", CardSetID);
                startActivity(addCardActivity);
            }
        });

        cardListView = (RecyclerView) findViewById(R.id.card_list);
        cardListView.setHasFixedSize(true);
        cardListView.setLayoutManager(new LinearLayoutManager(this));

        mDatabase = FirebaseDatabase.getInstance().getReference().child("CardSet").child(CardSetID).child("cards");

        CardIDs = generateCardIDs();
    }

    @Override
    protected void onStart(){
        super.onStart();

        FirebaseRecyclerAdapter<Card, CardListActivity.CardViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Card, CardListActivity.CardViewHolder>(
                        Card.class,
                        R.layout.card_row,
                        CardListActivity.CardViewHolder.class,
                        mDatabase
                ) {
                    @Override
                    protected void populateViewHolder(CardListActivity.CardViewHolder viewHolder, Card model, int position) {
                        final String cardKey = getRef(position).getKey().toString();
                        final int currentCardIndex = CardIDs.indexOf(cardKey);

                        viewHolder.setName(model.getName());
                        viewHolder.mView.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View view){
                                Intent singleTaskActivity = new Intent(CardListActivity.this, CardActivity.class);
                                singleTaskActivity.putExtra("CardSetID", CardSetID);
                                singleTaskActivity.putExtra("currentCardIndex", currentCardIndex);
                                singleTaskActivity.putStringArrayListExtra("CardIDs", CardIDs);
                                startActivity(singleTaskActivity);
                            }
                        });
                    }
                };
        cardListView.setAdapter(firebaseRecyclerAdapter);
    }

    public ArrayList<String> generateCardIDs(){
        final ArrayList<String> CardIDs = new ArrayList<>();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot d: dataSnapshot.getChildren()){
                        CardIDs.add(d.getKey());

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return CardIDs;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public CardViewHolder(View iView){
            super(iView);
            mView = iView;
        }

        public void setName(String name){
            TextView taskName = (TextView) mView.findViewById(R.id.CardName);
            taskName.setText(name);
        }
    }

}
