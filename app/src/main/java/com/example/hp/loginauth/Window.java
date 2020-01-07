package com.example.hp.loginauth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Window extends AppCompatActivity {
    TextView nameview;
    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth=FirebaseAuth.getInstance();
        setContentView(R.layout.activity_window);
        nameview=(TextView) findViewById(R.id.nameview);
        user=auth.getCurrentUser();
      //  nameview.setText((CharSequence) user.getEmail());
        ref=FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
      //  ref=database.getReference("Users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

              String username = dataSnapshot.child("name").getValue().toString();
                    Toast.makeText(Window.this,"welcome"+username,Toast.LENGTH_LONG).show();
                   nameview.setText((CharSequence)username);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                  Toast.makeText(Window.this,"Error",Toast.LENGTH_LONG).show();
            }
        });


    }
}
