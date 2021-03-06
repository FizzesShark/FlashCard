package com.example.xbug2.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ResultActivity extends AppCompatActivity {

    private String CardSetID;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CardSetID = getIntent().getExtras().getString("CardSetID");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("CardSet").child(CardSetID).child("cards");

        TextView knownPercentageText = findViewById(R.id.knownPercentage);

        calculateKnownPercentage(knownPercentageText);
    }

    private void returnHome(View view){
        Intent homeActivity = new Intent(ResultActivity.this, MainActivity.class);
        startActivity(homeActivity);
    }

    private void calculateKnownPercentage(final TextView knownPercentageText) {

        final int[] knownCount = {0};
        final int[] total = {0};
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for(DataSnapshot d : dataSnapshot.getChildren()) {
                        HashMap value = (HashMap)(d.getValue());
                        String status = (String)(value.get("status"));

                        if("Known".equals(status))
                        {
                            knownCount[0]++;
                        }

                        total[0]++;
                    }
                    if(total[0] == 0)
                    {
                        knownPercentageText.setText("100%");
                        return;
                    }

                    if(knownCount[0] == total[0])
                    {
                        knownPercentageText.setText("100%");
                        return;
                    }

                    long percentage = Math.round(knownCount[0] * 100.0 / total[0]);
                    if(percentage == 100)
                    {
                        // due to round up
                        percentage = 99;
                    }

                    knownPercentageText.setText((int)percentage+"%");
                    return;
                }

            }//onDataChange

            @Override
            public void onCancelled(DatabaseError error) {

            }//onCancelled
        });
    }
}
