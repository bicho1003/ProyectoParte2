package com.example.proyectoparte2;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

public class AudioController {
    private AudioManager audioManager;
    private MediaPlayer mediaPlayer;
    private Context context; // Agregar una variable de instancia para el context


    public AudioController(Context context, int audioResource) {
        this.context = context;
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        mediaPlayer = MediaPlayer.create(context, audioResource);

    }

    public void setVolume(int volume) {
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
    }

    public int getMaxVolume() {
        return audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }

    public int getVolume() {
        return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    public void play() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    public void pause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void release() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void stop() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }
    public void changeAudioResource(int resourceId) {
        // Liberar el recurso de audio actual
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

        // Crear un nuevo MediaPlayer con el nuevo recurso
        mediaPlayer = MediaPlayer.create(context, resourceId);
    }


}
