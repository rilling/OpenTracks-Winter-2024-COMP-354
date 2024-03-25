/*
 * Copyright 2009 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package de.dennisguse.opentracks.services.announcement;

import android.content.Context; // Base class for interacting with the Android system
import android.media.AudioAttributes; // Encapsulates the audio attributes of an audio stream
import android.media.AudioManager; // Provides access to the system volume controls
import android.media.MediaPlayer; // Provides a way to control playback of audio files/streams
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.speech.tts.TextToSpeech; // Convert text to speech
import android.speech.tts.UtteranceProgressListener; // Used to receive callbacks when the synthesis of an utterance starts/end or at an error
import android.text.Spannable; //Mark up text with style information
import android.text.SpannableStringBuilder;
import android.util.Log; // Provides methods to log messages (debugging purposes)
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull; // Provides helper classes
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List; //Ordered collection of elements
import java.util.Locale; // Represents a locale, which is a specific geographic, political, or cultural region

import de.dennisguse.opentracks.R; // Generated by the Android build system. It contains references to all the resources in the application
import de.dennisguse.opentracks.settings.PreferencesUtils; // A custom class that provides helper methods for working with user preferences


public class TTSManager extends AppCompatActivity {

    public final static int AUDIO_STREAM = TextToSpeech.Engine.DEFAULT_STREAM;
    // TextToSpeech: class part of the Android SDK used to convert tts
    // Engine is an inner class of TextToSpeech
    // DEFAULT_STREAM : static field of the 'Engine' class => specifies default stream of audio

    // Adding log messages related to the TTSManager class
    private static final String TAG = TTSManager.class.getSimpleName();

    //Super important : Context class provides access to system services & application resources
    private final Context context;

    //AudioManager Class  : Manage audio focus on the device (audio playback & recording)
    private final AudioManager audioManager;

    //AudioManager.OnAudioFocusChangeListener : Interface in Android SDK
    // Monitors audio focus change and returns a LOG of the change

    private final AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {

        @Override
        public void onAudioFocusChange(int focusChange) { // Method implementation of interface AudioManager.OnAudioFocusChangeListener

            Log.d(TAG, "Audio focus changed to " + focusChange); // Logging a message to the class

            //Constants that can be returned by the AudioManager Class
            // _LOSS : system lost audio focus and can no longer be used
            // _LOSS_TRANSIENT : Temporary loss of audio focus
            // ''_CAN_DUCK : Lowers the volume of the media player (if another app is playing for example)

            boolean stop = List.of(AudioManager.AUDIOFOCUS_LOSS, AudioManager.AUDIOFOCUS_LOSS_TRANSIENT, AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)
                    .contains(focusChange); // checking the state of 'focusChange' parameter

            if (stop && tts != null && tts.isSpeaking()) {

                // Interrupts the current utterance (whether played or rendered to file) and discards other utterances in the queue.
                // Returns ERROR or SUCCESS.
                tts.stop();
                Log.i(TAG, "Aborting current tts due to focus change " + focusChange);
            }
        }
    };
    // 'UtteranceProgressListener' : interface & defines methods that can be called to monitor the progress of TTS utterance
    private final UtteranceProgressListener utteranceListener = new UtteranceProgressListener() {
        // These methods can be really good for BackEnd to UI implementation
        @Override
        public void onStart(String utteranceId) { //when utterance begins
            int result = audioManager.requestAudioFocus(audioFocusChangeListener, AUDIO_STREAM, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK);
            if (result == AudioManager.AUDIOFOCUS_REQUEST_FAILED) {
                Log.w(TAG, "Failed to request audio focus.");
            }
        }

        @Override
        public void onDone(String utteranceId) { // when utterance has been successfully spoken
            int result = audioManager.abandonAudioFocus(audioFocusChangeListener);
            if (result == AudioManager.AUDIOFOCUS_REQUEST_FAILED) {
                Log.w(TAG, "Failed to relinquish audio focus.");
            }
        }

        @Override
        public void onError(String utteranceId) { // Called if an error occurs during the utterance
            Log.e(TAG, "An error occurred for utteranceId " + utteranceId);
        }
    };

    private TextToSpeech tts; //Conversion MY TTS OBJECT
    // Response from TTS after its initialization
    private int ttsInitStatus = TextToSpeech.ERROR; //Stores initialization status =>
    // Can/will change to .SUCCESS or  .FAILED_SYNTHESIS

    //    A boolean field that indicates whether the text-to-speech engine has been properly initialized
//    and is ready to perform text-to-speech conversion.
//    private boolean ttsReady = false;
    private boolean ttsReady = false;

    private MediaPlayer ttsFallback;
//    MediaPlayer class: used to play a pre-recorded audio file as a fallback mechanism when
//    the TTS engine is unable to perform the TTS conversion.

    TTSManager(Context context) { // Constructor of the TTSManager class
        this.context = context;
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
//        TTSManager constructor initializes audioManager by calling
//        getSystemService(Context.AUDIO_SERVICE) on the Context object, which returns an
//        AudioManager object.
    }

    public void start() { // Called in the VoiceAnnouncementManager Class


        Log.d(TAG, "Start");

        if (tts == null) {

            tts = new TextToSpeech(context, status -> {
                Log.i(TAG, "TextToSpeech initialized with status " + status);

                ttsInitStatus = status;
            });
        }
        if (ttsFallback == null) {
            ttsFallback = MediaPlayer.create(context, R.raw.tts_fallback);

            // initialize a new instance of MediaPlayer & associates it with a sound file resource in the app's raw directory
            //R.raw.tts_fallback : contains reference to the sound file

            if (ttsFallback == null) {
                Log.w(TAG, "MediaPlayer for ttsFallback could not be created.");
            } else {
                ttsFallback.setAudioAttributes(
                        new AudioAttributes.Builder()
                                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                                .build());
                ttsFallback.setLooping(false);
            }
        }
    }

    public void announce(@NonNull Spannable announcement) { // parameter cannot be null
        synchronized (this) { // One announcement at a time

            if (!ttsReady) { // checking if ready to make an announcement
                ttsReady = ttsInitStatus == TextToSpeech.SUCCESS;
                ;
                Log.d(TAG, "TTS initialized successfully.");
                // Update the UI to indicate that TTS is ready.
                if (ttsReady) {
                    onTtsReady();
                }
            }
        }

        if (List.of(AudioManager.MODE_IN_CALL, AudioManager.MODE_IN_COMMUNICATION)
                .contains(audioManager.getMode())) {
            Log.i(TAG, "Announcement is not allowed at this time.");
            return;
        }

        if (!ttsReady) {
            if (ttsFallback == null) {
                Log.w(TAG, "MediaPlayer for ttsFallback was not created.");
            } else {
                Log.i(TAG, "TTS not ready/available, just generating a tone.");
                ttsFallback.seekTo(0);
                ttsFallback.start();
            }
            return;
        }

        if (announcement.length() > 0) { //Where it is gonna speak and start announcement / IMPORTANT
            // We don't care about the utterance id. It is supplied here to force onUtteranceCompleted to be called.
            tts.speak(announcement, TextToSpeech.QUEUE_FLUSH, null, "not used");
        }
    }

    public void stop() {
        if (tts != null) {
            tts.shutdown();
            tts = null;
        }

        if (ttsFallback != null) {
            ttsFallback.release();
            ttsFallback = null;
        }
    }

//    void onTtsReady() {
//        Locale locale = Locale.getDefault(); // Get default geolocation / region etc
//        int languageAvailability = tts.isLanguageAvailable(locale); //Method in the TTS class :
//        // checks is the engine can speak specified language
//        if (languageAvailability == TextToSpeech.LANG_MISSING_DATA || languageAvailability == TextToSpeech.LANG_NOT_SUPPORTED) {
//            Log.w(TAG, "Default locale not available, use English.");
//            locale = Locale.ENGLISH;
//
//
//        }
//        tts.setLanguage(locale);  //set method in android sdk
//        tts.setSpeechRate(PreferencesUtils.getVoiceSpeedRate()); // Set speech rate output based on app preferences (set by user)
//        // track the progress of the TTS utterance,and to perform certain actions
//        // when specific events in the lifecycle of an utterance occur.
//        tts.setOnUtteranceProgressListener(utteranceListener);
//
//
//    }

    //method is where you initialize your activity, set up the user interface,
    // and perform any other necessary setup tasks.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Call onTtsReady for initialization
        onTtsReady();
    }

    void onTtsReady() {
        //        setContentView(R.layout.track_stopped);
        //        ImageView finish = findViewById(R.id.finish_button);


// Set the content view of the activity to the layout defined in 'track_recording.xml'
        setContentView(R.layout.track_recording);
        // Find the FloatingActionButton with the id 'track_recording_fab_action' in the layout
        FloatingActionButton run = findViewById(R.id.track_recording_fab_action);

        if (run != null) {
            // Initialize TextToSpeech instance with the application context
            tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int ttsInitStatus) {
                    // Get the default locale of the device
                    Locale locale = Locale.getDefault();
                    int languageAvailability = tts.isLanguageAvailable(locale);
                    if (languageAvailability == TextToSpeech.LANG_MISSING_DATA || languageAvailability == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.w(TAG, "Default locale not available, use English.");
                        // Set TextToSpeech language to English
                        locale = Locale.ENGLISH;
                        tts.setLanguage(locale);
                        // Set speech rate according to user preferences
                        tts.setSpeechRate(PreferencesUtils.getVoiceSpeedRate());
                        tts.setOnUtteranceProgressListener(utteranceListener);

                        // Set OnClickListener for FloatingActionButton 'run'
                        run.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                //Triggers the speaking FloatingActionButton is clicked
                                String speech = "Hello User!";
                                tts.speak(speech, TextToSpeech.QUEUE_FLUSH, null, null);
                            }
                        });
                    }else {
                        // If language other than English is selected, show a toast notification
                        Toast.makeText(getApplicationContext(), "Please note: Selected language may not be fully supported.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            //Log error if not found
            Log.e(TAG, "FloatingActionButton not found");
        }





    }

}



//Write a method that converts text to speech
//
