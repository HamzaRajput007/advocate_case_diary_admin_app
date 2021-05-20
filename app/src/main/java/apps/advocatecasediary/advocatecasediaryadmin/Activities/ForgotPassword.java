package apps.advocatecasediary.advocatecasediaryadmin.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import apps.webscare.advocatecasediaryadmin.R;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    EditText forgotPasswordET ;
    Button sendEmailBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        forgotPasswordET = findViewById(R.id.forgoatPasswordEditTextId);
        sendEmailBtn = findViewById(R.id.sendEmailBtnId);
        forgotPasswordET.setText(getIntent().getStringExtra("email"));
        sendEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(forgotPasswordET.getText().toString())){
                    forgotPasswordET.setError("This is a required Field");
                }else {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(forgotPasswordET.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(ForgotPassword.this, "We have sent a mail at " +getIntent().getStringExtra("email"), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}