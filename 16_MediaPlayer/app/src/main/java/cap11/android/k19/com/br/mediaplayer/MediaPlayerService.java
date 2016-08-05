package cap11.android.k19.com.br.mediaplayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by Wagner on 04/08/2016.
 */
public class MediaPlayerService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener{
    private MediaPlayer mMediaPlayer;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Iniciando o Serviço", Toast.LENGTH_SHORT).show();

        try {
            mMediaPlayer = new MediaPlayer();
            Uri path = Uri.parse("android.resource://br.com.k19.android.cap11/" + R.raw.sample);
            mMediaPlayer.setDataSource(getApplicationContext(), path);
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.prepareAsync();
        }catch (IOException e) {
            // recurso não encontrado.
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Terminando o Serviço", Toast.LENGTH_SHORT).show();
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mMediaPlayer.start();
    }

    public boolean onError(MediaPlayer mediaPlayer, int arg1, int arg2) {
        //Tratamento de erros.
        return false;
    }
}
