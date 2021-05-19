package apps.webscare.advocatecasediaryadmin.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import apps.webscare.advocatecasediaryadmin.R;

public class MainActivity extends AppCompatActivity {
    EditText emailET  , passwordET;
    Button btnLogin;
    TextView signUpBtn;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;
    Spinner accountTypeSpinner;
    TextView forgotPasswordTv;

    String uId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        emailET = findViewById(R.id.emailEditTextID);
        passwordET = findViewById(R.id.passwordEditTextID);
        signUpBtn = findViewById(R.id.signUpTextViewId);
        btnLogin = findViewById(R.id.loginBtnID);
        progressBar = findViewById(R.id.progressbarId);
        forgotPasswordTv = findViewById(R.id.forgotPasswordTextViewId);
        forgotPasswordTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toForgotPasswod = new Intent(MainActivity.this, ForgotPassword.class);
                if(TextUtils.isEmpty(emailET.getText().toString())){
                    emailET.setError("Enter Your Email Here !");
                }else{
                    toForgotPasswod.putExtra("email" , emailET.getText().toString());
                    startActivity(toForgotPasswod);
                }

            }
        });
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toSignup = new Intent(MainActivity.this , SignUp.class);
                startActivity(toSignup);
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(emailET.getText().toString())
                        && TextUtils.isEmpty(passwordET.getText().toString())){

                    emailET.setError("Enter an Email");
                    passwordET.setError("Enter Password");
                    progressBar.setVisibility(View.GONE);

                }else {
                    mAuth.signInWithEmailAndPassword(emailET.getText().toString() , passwordET.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            uId = mAuth.getCurrentUser().getUid();
                            mFirestore.collection("Admins").document(uId).addSnapshotListener( new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                                }
                            });

                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(MainActivity.this, "User Logged In Successfully", Toast.LENGTH_SHORT).show();
                            Intent toHome = new Intent(MainActivity.this , Home.class);
                            startActivity(toHome);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(MainActivity.this, "Unable To Login User", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}