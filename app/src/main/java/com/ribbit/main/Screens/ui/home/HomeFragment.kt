package com.ribbit.main.Screens.ui.home

import android.app.Application
import android.content.Context
import android.media.MediaRecorder
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ribbit.main.R
import com.ribbit.main.dbConnections.GetUserData
import java.io.File
import java.util.*

class HomeFragment : Fragment() {

    var gud = GetUserData()
    var userEmail = ""
    var recording = false
    var outputPath = File("")
    var recorder = MediaRecorder()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println(view.context.filesDir)

        var btn = view.findViewById<Button>(R.id.recordButton)
        //Button click event

        btn.setOnClickListener {
            println(recording)
            try {

                //if recorder is stopped
                if (recording == false) {
                    createFile()
                    println("out" + outputPath)
                    recorder.setAudioSource(MediaRecorder.AudioSource.MIC)
                    recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                    recorder.setOutputFile(outputPath.toString())
                    try{
                        recorder.prepare()
                    }catch(e:Exception){
                        e.printStackTrace()
                    }

                    recorder.start()
                    Toast.makeText(view.context,"Recording Started!", Toast.LENGTH_SHORT).show()
                    recording = true

                    //if recorder is running
                }else {
                    recorder.stop()
                    Toast.makeText(view.context,"Recording Stopped!", Toast.LENGTH_SHORT).show()
                    recorder.reset()
                    recording = false
                }
            }catch(e:Exception){
                e.printStackTrace()
                println("failed")
            }
        }
    }

    fun createFile() {
        println(HomeFragment().activity)
        var directory = view?.context?.filesDir
        var path = "/documents/recordings/"
        var recordingFolder = File(directory, path)

        //Create folder if it doesnt exit
        if (!recordingFolder.exists()) {
            recordingFolder.mkdirs()
        }

        //create file for recording
        var dateTime = Calendar.getInstance().timeInMillis
        outputPath = File(directory, path + dateTime + ".mp4")
        try {
            outputPath.createNewFile()
            println("File Created")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}