package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    Button Login, SignUp;
    EditText email,passsword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Login = findViewById(R.id.login);
        SignUp = findViewById(R.id.SignUp);
        email = findViewById(R.id.emailLogin);
        passsword = findViewById(R.id.passwordLogin);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1 = email.getText().toString().trim();
                String s2 = passsword.getText().toString().trim();
                String s3 = passsword.getText().toString().trim();
                String s4 = email.getText().toString().trim();
                   startActivity(new Intent(getApplicationContext(),sendSms.class));


//                if(s1 == s4 && s2 == s3){
//                   startActivity(new Intent(getApplicationContext(),sendSms.class));
//                }else {
//                    email.setText("");
//                    passsword.setText("");
//                    Toast.makeText(Login.this, "wrong Password or Email", Toast.LENGTH_SHORT).show();
//                    email.requestFocus();
//
//                }


            }
        });

    }
}
