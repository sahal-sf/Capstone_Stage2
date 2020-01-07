package net.sahal.capstone_stage2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {

    EditText Email, Password;
    Button SignUp;
    String email, pass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Email = findViewById(R.id.Email_field);
        Password = findViewById(R.id.Password_field);
        SignUp = findViewById(R.id.Signup_Button);


        email = Email.getText().toString();
        pass = Password.getText().toString();


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (email.equals("") || pass.equals("")) {

                } else {

                    mAuth.createUserWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(SignUp.this, "createUserWithEmail:success", Toast.LENGTH_SHORT).show();
                                        FirebaseUser user = mAuth.getCurrentUser();


                                    } else {
                                        // If sign in fails, display a message to the user.

                                        Toast.makeText(SignUp.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }
            }
        });

    }
}
