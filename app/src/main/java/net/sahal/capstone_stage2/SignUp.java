package net.sahal.capstone_stage2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String email;
    private String pass;
    private boolean isConnected;

    @BindView(R.id.Email_field)
    EditText email_text;
    @BindView(R.id.Password_field)
    EditText password_text;
    @BindView(R.id.Signup_Button)
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("SignUp");

        ButterKnife.bind(this);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = email_text.getText().toString();
                pass = password_text.getText().toString();

                if (email.equals("") || pass.equals("")) {
                    Toast.makeText(SignUp.this, R.string.fill_field, Toast.LENGTH_SHORT).show();

                } else {
                    new NetworkCheck().execute();
                    if (isConnected) {
                        mAuth = FirebaseAuth.getInstance();
                        mAuth.createUserWithEmailAndPassword(email, pass);
                        Toast.makeText(SignUp.this, R.string.Success_SignUp, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(SignUp.this, MainActivity.class);
                        startActivity(i);

                    } else {
                        Toast.makeText(getApplicationContext(), R.string.Internet_Connection, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public class NetworkCheck extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            Toast.makeText(getApplicationContext(), "Loading...", Toast.LENGTH_LONG).show();
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... abc) {
            if (isNetworkConnected()) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            isConnected = result;
            if (!result) {
                Toast.makeText(getApplicationContext(), R.string.Internet_Connection, Toast.LENGTH_LONG).show();
            }
        }
    }
}