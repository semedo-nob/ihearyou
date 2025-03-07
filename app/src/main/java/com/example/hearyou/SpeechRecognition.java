package com.example.hearyou;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.RecognitionListener;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.airbnb.lottie.LottieAnimationView;
import java.util.ArrayList;
import java.util.Locale;

public class SpeechRecognition extends AppCompatActivity {
    private static final String TAG = "SpeechRecognition";
    private static final int PERMISSION_REQUEST_CODE = 1;
    private TextToSpeech textToSpeech;
    private SpeechRecognizer speechRecognizer;
    private LottieAnimationView talkingAnimation;
    private Button listenButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "SpeechRecognition activity created");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_speech_recognition);

        talkingAnimation = findViewById(R.id.talkingAnimation);
        listenButton = findViewById(R.id.listenButton);

        if (talkingAnimation == null || listenButton == null) {
            Log.e(TAG, "Animation or button not found");
            Toast.makeText(this, "Error: UI elements not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Initialize Text-to-Speech
        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.setLanguage(Locale.US);
            }
        });

        // Check and request permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSION_REQUEST_CODE);
        }

        // Initialize speech recognizer
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onResults(Bundle results) {
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null && !matches.isEmpty()) {
                    String spokenText = matches.get(0);
                    repeatText(spokenText);
                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onSegmentResults(@NonNull Bundle segmentResults) {
                RecognitionListener.super.onSegmentResults(segmentResults);
            }

            @Override
            public void onEndOfSegmentedSession() {
                RecognitionListener.super.onEndOfSegmentedSession();
            }

            @Override
            public void onLanguageDetection(@NonNull Bundle results) {
                RecognitionListener.super.onLanguageDetection(results);
            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }

            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override public void onError(int error) {
                Toast.makeText(SpeechRecognition.this, "Try again!", Toast.LENGTH_SHORT).show();
            }

            // Other override methods can be left empty
        });

        // Listen button click event
        listenButton.setOnClickListener(view -> startListening());
    }

    private void startListening() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        speechRecognizer.startListening(intent);
    }

    private void repeatText(String text) {
        talkingAnimation.playAnimation();  // Start default animation

        // Detect words and show emotions
        if (text.toLowerCase().contains("happy") || text.toLowerCase().contains("fun")) {
            talkingAnimation.setAnimation(R.raw.happy_animation);
        } else if (text.toLowerCase().contains("sad") || text.toLowerCase().contains("cry")) {
            talkingAnimation.setAnimation(R.raw.sad_animation);
        } else if (text.toLowerCase().contains("wow") || text.toLowerCase().contains("amazing")) {
            talkingAnimation.setAnimation(R.raw.surprised_animation);
        } else {
            talkingAnimation.setAnimation(R.raw.talking_animation);  // Default talking animation
        }

        talkingAnimation.playAnimation(); // Start animation

        // Modify voice randomly
        float pitch = (float) (0.5 + Math.random() * 1.5);
        float speed = (float) (0.7 + Math.random() * 1.3);

        textToSpeech.setPitch(pitch);
        textToSpeech.setSpeechRate(speed);

        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);

        textToSpeech.setOnUtteranceCompletedListener(utteranceId ->
                talkingAnimation.cancelAnimation()
        );
    }



    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        if (speechRecognizer != null) {
            speechRecognizer.destroy();
        }
        super.onDestroy();
    }
}
