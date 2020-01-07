package net.sahal.capstone_stage2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText Email,Password;
    String email, pass;
    Button SignIn,SignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Email = findViewById(R.id.Email_field);
        Password = findViewById(R.id.Password_field);
        SignIn = findViewById(R.id.Signin_Button);
        SignUp = findViewById(R.id.Signup_Button);





        mAuth = FirebaseAuth.getInstance();

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = Email.getText().toString();
                pass = Password.getText().toString();

                if (email.equals("") || pass.equals("")) {

                } else {
                    mAuth.signInWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(getApplicationContext(), "signInWithEmail:success", Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(MainActivity.this, ExerciseList.class);
                                        startActivity(i);
                                        FirebaseUser user = mAuth.getCurrentUser();

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(getApplicationContext(), "signInWithEmail:failure", Toast.LENGTH_LONG).show();
                                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SignUp.class);
                startActivity(i);
                }

        });
            }
    }