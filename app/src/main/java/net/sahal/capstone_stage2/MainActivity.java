package net.sahal.capstone_stage2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String email;
    private String pass;

    @BindView(R.id.Email_field)
    EditText email_text;
    @BindView(R.id.Password_field)
    EditText password_text;
    @BindView(R.id.Signin_Button)
    Button signIn;
    @BindView(R.id.Signup_Button)
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.app_name);

        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = email_text.getText().toString();
                pass = password_text.getText().toString();

                if (email.equals("") || pass.equals("")) {
                    Toast.makeText(MainActivity.this, R.string.fill_field, Toast.LENGTH_SHORT).show();

                } else {
                    mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent i = new Intent(MainActivity.this, ExerciseList.class);
                                startActivity(i);

                            } else {
                                Toast.makeText(MainActivity.this, R.string.failed_SignIn, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SignUp.class);
                startActivity(i);
            }
        });
    }
}