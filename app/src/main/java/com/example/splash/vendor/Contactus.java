package com.example.splash.vendor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.splash.R;

public class Contactus extends AppCompatActivity {

    TextView termsandcondition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);
        termsandcondition=findViewById(R.id.termsandcondition);
        termsandcondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://splash-0.flycricket.io/privacy.html?fbclid=IwAR2cNOxMvzNbbmzhOI220dX5wJz-JDQQyYxnTAOrrzZTjbhV2gR5OdNSVu0";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                // Note the Chooser below. If no applications match,
                // Android displays a system message.So here there is no need for try-catch.
                startActivity(Intent.createChooser(intent, "Browse with"));
            }
        });
    }
}