package com.example.laicooper.recordactivity;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnErrorListener;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Button btn_RecordStart, btn_RecordStop,btn_RecordPlay;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mp;
    private boolean isRecording;
    private File file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_RecordStart=(Button)findViewById(R.id.but_startRecord);
        btn_RecordStop=(Button)findViewById(R.id.but_stop);
        btn_RecordPlay=(Button)findViewById(R.id.but_play);
        btn_RecordStop.setEnabled(false);
    }



    public void start(View view){

        try{
            file=new File("/sdcard/mediarecorder.amr");
            if(file.exists()) {
                file.delete();
            }

            mediaRecorder=new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
           mediaRecorder.setOutputFile(file.getAbsolutePath());
            mediaRecorder.setOnErrorListener(new OnErrorListener() {

                @Override

                public void onError(MediaRecorder mr, int what, int extra) {
                    // 发生错误，停止录制
                    mediaRecorder.stop();
                    mediaRecorder.release();
                    mediaRecorder = null;
                    isRecording = false;
                    btn_RecordStart.setEnabled(true);
                    btn_RecordStop.setEnabled(false);
                    Toast.makeText(MainActivity.this, "录音发生错误", Toast.LENGTH_SHORT).show();

                }

            });
            mediaRecorder.prepare();
            mediaRecorder.start();

            isRecording=true;
            btn_RecordStart.setEnabled(false);
            btn_RecordStop.setEnabled(true);
            Toast.makeText(MainActivity.this, "开始录音", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void stop(View view){
        if (isRecording) {
         // 如果正在录制，停止并释放资源
          mediaRecorder.stop();
          mediaRecorder.release();
          mediaRecorder = null;
          isRecording=false;
          btn_RecordStart.setEnabled(true);
          btn_RecordStop.setEnabled(false);
          Toast.makeText(MainActivity.this, "停止录音，并保存文件", Toast.LENGTH_SHORT).show();

        }

    }
    public void play(View view){

        mp=new MediaPlayer();
        if(!isRecording){
            try{
                mp.setDataSource(file.getAbsolutePath());
                mp.prepare();
                mp.start();
                Toast.makeText(this,"play record sound",Toast.LENGTH_SHORT);
            }catch (IOException e){
                Toast.makeText(this,"Couldn't play media",Toast.LENGTH_SHORT);
            }
        }
        else {
            Toast.makeText(this,"it is recording",Toast.LENGTH_SHORT);
        }
    }
}
