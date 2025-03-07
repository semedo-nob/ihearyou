package com.example.hearyou;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;




        public class MainActivity extends AppCompatActivity {
            private static final String TAG = "MainActivity";

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                Log.d(TAG, "MainActivity created");
                EdgeToEdge.enable(this);
                setContentView(R.layout.activity_main);

                ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                    v.setPadding(insets.getSystemWindowInsetLeft(), insets.getSystemWindowInsetTop(),
                            insets.getSystemWindowInsetRight(), insets.getSystemWindowInsetBottom());
                    return insets;
                });

                // Start SpeechRecognition activity after delay
                new Handler().postDelayed(() -> {
                    Intent intent = new Intent(MainActivity.this, SpeechRecognition.class);
                    startActivity(intent);
                    finish();
                }, 2000); // 2 seconds delay
            }
        }


