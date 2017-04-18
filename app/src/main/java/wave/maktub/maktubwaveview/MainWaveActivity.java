package wave.maktub.maktubwaveview;

import android.content.res.Resources;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ProgressBar;

import java.io.IOException;

import wave.maktub.maktubwave.MaktubView;

public class MainWaveActivity extends FragmentActivity {
    private Visualizer mVisualizer;
    private MediaPlayer mMediaPlayer;
    private MaktubView mWave;
    private ProgressBar mPrgLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_wave);
        mWave = (MaktubView) findViewById(R.id.wave);
        mPrgLoading = (ProgressBar) findViewById(R.id.progressBar);
        if (mMediaPlayer == null)
            mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            String link = "http://www117.zippyshare.com/music/yM1PHPZ2/0/[NhacDJ.vn] - Nonstop - Viet Mix - Khi Co Don Em Nghi Den Ai - HPBD Cong Cha Dia - DJ KienZ Mix [NhacDJ.vn].mp3".replace(" " , "%20");
            mMediaPlayer.setDataSource(link);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mPrgLoading.setVisibility(View.GONE);
                mMediaPlayer.start();
            }
        });

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


    private void setAmpForWave(int values) {
        mWave.setMetter(values);
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
}
