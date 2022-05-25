package com.reptilefury.datacollectionapp

import android.app.Activity
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.reptilefury.datacollectionapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.jar.Manifest
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val outPutFile = Environment.getExternalStorageDirectory().absolutePath + "/recording.3gp"
    var myAudioRecorder: MediaRecorder? = MediaRecorder()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val play = binding.PlayButton
        val stop = binding.StopButton
        val record = binding.RecordButton
        play.isEnabled = false
        stop.isEnabled = false
        record.isEnabled = false

        myAudioRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        myAudioRecorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        myAudioRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        myAudioRecorder?.setOutputFile(outPutFile)
        startRecording()
        stopRecording()
        playAudio()

   /*     if (ActivityCompat.checkSelfPermission(Activity(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(Actvit, new String[]{Manifest.permission.RECORD_AUDIO}, BuildDev.RECORD_AUDIO);

        } else {

            startRecording();

        }*/
    }
    val record = binding.PlayButton
    val stop = binding.StopButton
    var play = binding.PlayButton
    fun startRecording() {
        record.setOnClickListener {
            // CoroutineScope(Dispatchers.Default).launch{
            try {
                myAudioRecorder?.start()
                myAudioRecorder?.prepare()
            } catch (e: Exception) {
                Log.e("Error", e.message.toString())
            }
            record.isEnabled = true
            stop.isEnabled = false
            Toast.makeText(applicationContext, "Started Recording", Toast.LENGTH_SHORT).show()
        }
    }

    fun stopRecording() {
        stop.setOnClickListener {
            myAudioRecorder?.stop()
            myAudioRecorder?.release()
            myAudioRecorder = null
            record.isEnabled = true
            stop.isEnabled = false
            play.isEnabled = true
            Toast.makeText(applicationContext, "Stopped Recording", Toast.LENGTH_SHORT).show()
        }
    }

    fun playAudio() {
        play.setOnClickListener {
            val mediaPlayer = MediaPlayer()
            try {
                //prepare start setDa toat
                mediaPlayer.setDataSource(outPutFile)
                mediaPlayer.prepare()
                mediaPlayer.start()
                Toast.makeText(applicationContext, "Started Playing Audio", Toast.LENGTH_SHORT)
                    .show()
            } catch (e: Exception) {

                Toast.makeText(applicationContext, "Failed to play audio", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}
