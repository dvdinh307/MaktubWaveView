package wave.maktub.maktubwaveview;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ProgressBar;

import wave.maktub.maktubwave.MaktubView;

public class MainWaveActivity extends FragmentActivity {
    private Visualizer mVisualizer;
    private MediaPlayer mMediaPlayer;
    private MaktubView mWave;
    private ProgressBar mPrgLoading;
    private final int MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_wave);
        mWave = (MaktubView) findViewById(R.id.wave);
        mPrgLoading = (ProgressBar) findViewById(R.id.progressBar);
        if (mMediaPlayer == null)
            mMediaPlayer = MediaPlayer.create(MainWaveActivity.this , R.raw.vogia);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mPrgLoading.setVisibility(View.GONE);
                mMediaPlayer.start();
            }
        });
        // Android >= 6.0
        if (ContextCompat.checkSelfPermission(MainWaveActivity.this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainWaveActivity.this,
                    Manifest.permission.READ_CONTACTS)) {
                initVisualizer();
            } else {
                ActivityCompat.requestPermissions(MainWaveActivity.this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        MY_PERMISSIONS_REQUEST_RECORD_AUDIO);
            }
        }
    }

    private void initVisualizer() {
        int audioSessionID = mMediaPlayer.getAudioSessionId();
        mVisualizer = new Visualizer(audioSessionID);
        mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
        mVisualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener() {
            public void onWaveFormDataCapture(Visualizer visualizer, byte[] bytes,
                                              int samplingRate) {
                // Send bytes (waveform) to your custom UI view to show it.
                for (int j = 0; j < bytes.length - 1; j++) {
                    if (bytes[j] < 0) {
                        setAmpForWave(0);
                    } else if (bytes[j] > 0 && bytes[j] < 25) {
                        setAmpForWave(getScreenWidth() > 1000 ? 1 : (int) 0.5);
                    } else if (bytes[j] >= 25 && bytes[j] < 50) {
                        setAmpForWave(getScreenWidth() > 1000 ? 2 : (int) 1.0);
                    } else if (bytes[j] >= 50 && bytes[j] < 75) {
                        setAmpForWave(getScreenWidth() > 1000 ? 3 : (int) 2.0);
                    } else if (bytes[j] >= 75 && bytes[j] < 100) {
                        setAmpForWave(getScreenWidth() > 1000 ? 4 : (int) 2.8);
                    } else if (bytes[j] > 100) {
                        setAmpForWave(getScreenWidth() > 1000 ? 5 : (int) 3.2);
                    }
                }
            }

            public void onFftDataCapture(Visualizer visualizer, byte[] bytes, int samplingRate) {
                for (int j = 0; j < bytes.length; j++) {
//                    Log.e("Vjzzz2","samplingRate :" + bytes[j]);
                }
            }
        }, Visualizer.getMaxCaptureRate() / 2, true, true);
        mVisualizer.setEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_RECORD_AUDIO: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    initVisualizer();
                } else {

                }
                return;
            }
        }
    }


    private void setAmpForWave(int values) {
        mWave.setMetter(values);
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
}
