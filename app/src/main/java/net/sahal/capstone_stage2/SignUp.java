package net.sahal.capstone_stage2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String email;
    private String pass;

    @BindView(R.id.Email_field)
    private EditText email_text;
    @BindView(R.id.Password_field)
    private EditText password_text;
    @BindView(R.id.Signup_Button)
    private Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("SignUp");

        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = email_text.getText().toString();
                pass = password_text.getText().toString();

                if (email.equals("") || pass.equals("")) {
                    Toast.makeText(SignUp.this, R.string.fill_field, Toast.LENGTH_SHORT).show();

                } else {
                    mAuth.createUserWithEmailAndPassword(email, pass);
                    Toast.makeText(SignUp.this, R.string.Success_SignUp, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(SignUp.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });
    }
}