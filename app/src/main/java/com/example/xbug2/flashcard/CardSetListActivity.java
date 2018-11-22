package com.example.xbug2.flashcard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CardSetListActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private RecyclerView cardSetListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_set_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addCardSet = new Intent(CardSetListActivity.this, AddCardSetActivity.class);
                startActivity(addCardSet);
            }
        });

        cardSetListView = (RecyclerView) findViewById(R.id.card_set_list);
        cardSetListView.setHasFixedSize(true);
        cardSetListView.setLayoutManager(new LinearLayoutManager(this));

        mDatabase = FirebaseDatabase.getInstance().getReference().child("CardSet");
    }

    @Override
    protected void onStart(){
        super.onStart();

        FirebaseRecyclerAdapter<CardSet, CardSetViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<CardSet, CardSetViewHolder>(
                        CardSet.class,
                        R.layout.card_set_row,
                        CardSetViewHolder.class,
                        mDatabase
                ) {
                    @Override
                    protected void populateViewHolder(CardSetViewHolder viewHolder, CardSet model, int position) {
                        final String cardSetKey = getRef(position).getKey().toString();

                        viewHolder.setName(model.getName());
                        viewHolder.setColor(model.getColor());
                        viewHolder.mView.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View view){
                                Intent singleTaskActivity = new Intent(CardSetListActivity.this, CardListActivity.class);
                                singleTaskActivity.putExtra("CardSetID", cardSetKey);
                                startActivity(singleTaskActivity);
                            }
                        });
                    }
                };
        cardSetListView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class CardSetViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public CardSetViewHolder(View iView){
            super(iView);
            mView = iView;
        }

        public void setName(String name){
            String firstLetter;
            if (name == null || name.isEmpty()){
                firstLetter="?";
            }else{
                firstLetter = name.substring(0, 1);
            }
            TextView cardLetter = (TextView) mView.findViewById(R.id.cardLetter);
            cardLetter.setText(firstLetter);
            if (name != null && name.length() > 20)
                name = name.substring(0, 20) + "...";
            TextView taskName = (TextView) mView.findViewById(R.id.CardSetName);
            taskName.setText(name);
        }

        public void setColor(String  color){
            CardView cardView = (CardView) mView.findViewById(R.id.cardDisk);
            if ("Blue".equals(color)){
                cardView.setCardBackgroundColor(Color.parseColor("#546de5"));
            }else if ("Green".equals(color)){
                cardView.setCardBackgroundColor(Color.parseColor("#05c46b"));
            }else if ("Yellow".equals(color)){
                cardView.setCardBackgroundColor(Color.parseColor("#ffff0f"));
            }else if ("Red".equals(color)){
                cardView.setCardBackgroundColor(Color.parseColor("#ff0000"));
            }
        }
    }
}
