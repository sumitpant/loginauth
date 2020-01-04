package com.example.hp.loginauth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Sign_Up extends AppCompatActivity {
       EditText name,uid,upass;
       Button Signup;
       private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);
        name=findViewById(R.id.name);
        uid=findViewById(R.id.uid);
        upass=findViewById(R.id.upass);
        Signup=findViewById(R.id.Signup);
        auth=FirebaseAuth.getInstance();
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.createUserWithEmailAndPassword(uid.getText().toString(),upass.getText().toString()).addOnCompleteListener(Sign_Up.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {// startActivity(new Intent(Sign_Up.this,Window.class));
                            auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Users user=new Users(name.getText().toString());
                                        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(Sign_Up.this,"successful",Toast.LENGTH_LONG).show();
                                            }
                                        });
                                           Toast.makeText(Sign_Up.this,"Successful",Toast.LENGTH_LONG).show();
                                           name.setText("");
                                           uid.setText("");
                                           upass.setText("");
                                    }
                                    else{
                                        Toast.makeText(Sign_Up.this,"User already Exist ",Toast.LENGTH_LONG).show();
                                    }

                                }
                            });


                        }
                        else{
                            Toast.makeText(Sign_Up.this,"User already Exist ",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}
