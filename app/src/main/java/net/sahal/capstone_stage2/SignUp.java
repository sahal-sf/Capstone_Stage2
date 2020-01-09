package net.sahal.capstone_stage2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
        setTitle("SignUp");

        Email = findViewById(R.id.Email_field);
        Password = findViewById(R.id.Password_field);
        SignUp = findViewById(R.id.Signup_Button);

        mAuth = FirebaseAuth.getInstance();
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = Email.getText().toString();
                pass = Password.getText().toString();

                if (email.equals("") || pass.equals("")) {
                    Toast.makeText(SignUp.this, "Please enter EMAIL and PASSWORD.", Toast.LENGTH_SHORT).show();

                } else {
                    mAuth.createUserWithEmailAndPassword(email, pass);
                    Toast.makeText(SignUp.this, "Successfully registered, Please Login.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(SignUp.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });

    }
}
