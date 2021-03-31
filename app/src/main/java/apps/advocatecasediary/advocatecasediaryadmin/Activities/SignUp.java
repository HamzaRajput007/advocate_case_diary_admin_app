package apps.advocatecasediary.advocatecasediaryadmin.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import apps.advocatecasediary.advocatecasediaryadmin.R;

public class SignUp extends AppCompatActivity {

    EditText emailET, passwordET, phoneNumberET, fullNameET , idCardET;
    Button btnSignUp;
    TextView loginText;
    FirebaseAuth mAuth;
    FirebaseFirestore mFireStore;
    ProgressBar progressBar;

    String phone, name;
    String uID , idCardNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        mFireStore = FirebaseFirestore.getInstance();
        emailET = findViewById(R.id.emailEditTextID);
        passwordET = findViewById(R.id.passwordEditTextID);
        loginText = findViewById(R.id.signUpTextViewId);
        btnSignUp = findViewById(R.id.loginBtnID);
        phoneNumberET = findViewById(R.id.phoneNumberEditTextID);
        fullNameET = findViewById(R.id.nameEditTextID);
        progressBar = findViewById(R.id.progressbarId);
        idCardET = findViewById(R.id.idCardEditTextID);

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toLogin = new Intent(SignUp.this, MainActivity.class);
                startActivity(toLogin);
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(emailET.getText().toString())
                        && TextUtils.isEmpty(passwordET.getText().toString())
                        && TextUtils.isEmpty(phoneNumberET.getText().toString())
                        && TextUtils.isEmpty(fullNameET.getText().toString())
                        && TextUtils.isEmpty(idCardET.getText().toString())) {
                    emailET.setError("Please Enter Some Email Address");
                    passwordET.setError("Please Enter Some Password");
                    fullNameET.setError("Please Enter Your Name");
                    phoneNumberET.setError("Please Enter Your Phone Number ");
                    idCardET.setError("Please Enter Your CNIC #");
                    Toast.makeText(SignUp.this, "Invalid Input", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                } else {
                    name = fullNameET.getText().toString();
                    phone = phoneNumberET.getText().toString();
                    idCardNumber = idCardET.getText().toString();
                    mAuth.createUserWithEmailAndPassword(emailET.getText().toString(), passwordET.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignUp.this, "Registered user successfully!", Toast.LENGTH_SHORT).show();
                                emailET.setText("");
                                passwordET.setText("");
                                phoneNumberET.setText("");
                                fullNameET.setText("");
                                progressBar.setVisibility(View.GONE);
                                uID = mAuth.getCurrentUser().   getUid();
                                Map<String, Object> userDetailsMap = new HashMap<>();
                                userDetailsMap.put("name", name);
                                userDetailsMap.put("phone", phone);
                                userDetailsMap.put("CNIC" , idCardNumber);
                                userDetailsMap.put("type", "Admin");
                                mFireStore.collection("Admins").document(uID).set(userDetailsMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(SignUp.this, "Loaded Users Data in FireStore", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(SignUp.this, "Failed FireStore : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignUp.this, "Failed to Register User : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    });

                }
            }
        });
    }
}