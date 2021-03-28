package apps.webscare.advocatecasediaryadmin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import apps.webscare.advocatecasediaryadmin.R;

public class Home extends AppCompatActivity {

    Button insertAdvocate, updateSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        insertAdvocate = findViewById(R.id.insertAdvocateBtnID);
        updateSchedule = findViewById(R.id.updateSchedule);

        insertAdvocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toInsertAdvocate = new Intent(Home.this , InsertAdvocate.class);
                startActivity(toInsertAdvocate);
            }
        });

        updateSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}