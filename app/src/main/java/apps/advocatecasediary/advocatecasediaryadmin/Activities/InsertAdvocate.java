package apps.advocatecasediary.advocatecasediaryadmin.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import apps.webscare.advocatecasediaryadmin.R;
//import apps.advocatecasediary.advocatecasediaryadmin.R;

public class InsertAdvocate extends AppCompatActivity {

    ImageView profilePictreImageView;
    int PICK_IMAGE = 1;
    EditText nameEt , emailET , cityET , experienceEt , cnicET , educationET;
    Button submitBtn;
    TextView selectProfileImageText;

    FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_advocate);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        nameEt = findViewById(R.id.advocateNameID);
        emailET = findViewById(R.id.emailNameID);
        cityET = findViewById(R.id.cityNameID);
        experienceEt = findViewById(R.id.experienceNameID);
        cnicET = findViewById(R.id.cnicNameID);
        educationET = findViewById(R.id.educationNameID);
        submitBtn = findViewById(R.id.addAdvocateBtnId);
        selectProfileImageText  = findViewById(R.id.selectImageTextViewID);
        profilePictreImageView = findViewById(R.id.profile_image);
        profilePictreImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toPickImage = new Intent();
                toPickImage.setType("image/*");
                toPickImage.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(toPickImage, "Select Picture"), PICK_IMAGE);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameEt.getText().toString().isEmpty()
                || emailET.getText().toString().isEmpty()
                        || cityET.getText().toString().isEmpty()
                        || experienceEt.getText().toString().isEmpty()
                        || cnicET.getText().toString().isEmpty()){
                    nameEt.setError("This Field is Required");
                    emailET.setError("This Field is Required");
                    cityET.setError("This Field is Required");
                    cnicET.setError("This Field is Required");
                    experienceEt.setError("This Field is Required");
                } else  {
                    Map<String , Object> advocateDataMap = new HashMap<>();
                    advocateDataMap.put("name" , nameEt.getText().toString());
                    advocateDataMap.put("email" , emailET.getText().toString());
                    advocateDataMap.put("city" , cityET.getText().toString());
                    advocateDataMap.put("experience" , experienceEt.getText().toString());
                    advocateDataMap.put("cnic" , cnicET.getText().toString());

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            profilePictreImageView.setImageURI(data.getData());
        }
    }
}