package apps.webscare.advocatecasediaryadmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import apps.advocatecasediary.advocatecasediaryadmin.Activities.MainActivity;
import apps.advocatecasediary.advocatecasediaryadmin.R;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ConstraintLayout parentLayout = findViewById(R.id.splashParentLayout);
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        parentLayout.setSystemUiVisibility(uiOptions);

        ImageView img = (ImageView)findViewById(R.id.splashScreenCenterImageViewId);
//        ImageView lowerImage = (ImageView)findViewById(R.id.imageViewBottomSplashId);
        Animation aniFade = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
//        lowerImage.startAnimation(aniFade);
        img.startAnimation(aniFade);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent toCountries = new Intent(Splash.this , MainActivity.class);
                startActivity(toCountries);
                finish();
            }
        },3000);
    }
}