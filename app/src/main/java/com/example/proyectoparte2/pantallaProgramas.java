package com.example.proyectoparte2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

//A2
import android.media.MediaPlayer;
import android.widget.Toast;


//A2

public class pantallaProgramas extends Activity {
    Button volumenButton, ruidoButton, programaButton, pitchButton;
    Button programaA0Button, programaA1Button,programaA2Button;
    Button ruidoBlancoSuaveButton,tonoModuladoButton;
    Button arribaButton,abajoButton,regresarButton;

    AudioManager audioManager;
    boolean isProgramButtonsVisible,isRuidoButtonsVisible,isPitchButtonsVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantallaprogramas);

        volumenButton = findViewById(R.id.volumenButton);
        ruidoButton = findViewById(R.id.ruidoButton);
        programaButton = findViewById(R.id.programaButton);
        pitchButton = findViewById(R.id.pitchButton);

        programaA0Button = findViewById(R.id.programaA0Button);
        programaA1Button = findViewById(R.id.programaA1Button);
        programaA2Button = findViewById(R.id.programaA2Button);
        //programaA2Button = findViewById(R.id.programaA2Button);
        //A2
        programaA2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showControlButtons();
            }
        });
        //A2

        ruidoBlancoSuaveButton = findViewById(R.id.ruidoBlancoSuaveButton);
        tonoModuladoButton = findViewById(R.id.tonoModuladoButton);

        arribaButton = findViewById(R.id.arribaButton);
        abajoButton = findViewById(R.id.abajoButton);
        regresarButton = findViewById(R.id.regresarButton);

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        volumenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ruidoButton.getVisibility() == View.GONE) {
                    showAllButtons();
                } else {
                    hideAllButtons();
                    regresarButton.setVisibility(View.VISIBLE);
                    showVolumeControl();
                }
            }
        });

        ruidoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRuidoButtonsVisible) {
                    hideRuidoButtons();
                    showAllButtons();
                    isRuidoButtonsVisible = false;
                } else {
                    hideAllButtons();
                    showRuidoButtons();
                    isRuidoButtonsVisible = true;
                }
            }
        });

        programaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isProgramButtonsVisible) {
                    hideProgramButtons();
                    showAllButtons();
                    isProgramButtonsVisible = false;
                } else {
                    hideAllButtons();
                    showProgramButtons();
                    isProgramButtonsVisible = true;
                }
            }
        });

        pitchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPitchButtonsVisible) {
                    hidePitchButtons();
                    showAllButtons();
                    isPitchButtonsVisible = false;
                } else {
                    hideAllButtons();
                    showPitchButtons();
                    isPitchButtonsVisible = true;
                }
            }
        });

        regresarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAllButtons();
                hidePitchButtons();
                hideProgramButtons();
                hideRuidoButtons();
                showAllButtons();
            }
        });
    }

    private void showAllButtons() {
        volumenButton.setVisibility(View.VISIBLE);
        ruidoButton.setVisibility(View.VISIBLE);
        programaButton.setVisibility(View.VISIBLE);
        pitchButton.setVisibility(View.VISIBLE);
    }
    private void hideAllButtons() {
        volumenButton.setVisibility(View.GONE);
        ruidoButton.setVisibility(View.GONE);
        programaButton.setVisibility(View.GONE);
        pitchButton.setVisibility(View.GONE);
    }
    private void showProgramButtons() {
        programaA0Button.setVisibility(View.VISIBLE);
        programaA1Button.setVisibility(View.VISIBLE);
        programaA2Button.setVisibility(View.VISIBLE);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, R.id.programaA2Button);
        regresarButton.setLayoutParams(params);
        regresarButton.setVisibility(View.VISIBLE);
    }
    //A2
    private void showControlButtons() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.control_buttons, null);
        builder.setView(dialogView);

        final Button startButton = dialogView.findViewById(R.id.startButton);
        final Button pauseButton = dialogView.findViewById(R.id.pauseButton);
        final Button resumeButton = dialogView.findViewById(R.id.resumeButton);

        // Inicializar MediaPlayer
        mediaPlayer = MediaPlayer.create(this, R.raw.musica); // Asegúrate de que 'song.mp3' está en res/raw
        mediaPlayer.setLooping(false); // No repetir la canción automáticamente

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                }
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
            }
        });

        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                }
            }
        });

        builder.setCancelable(true);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //A2


    private void hideProgramButtons() {
        programaA0Button.setVisibility(View.GONE);
        programaA1Button.setVisibility(View.GONE);
        programaA2Button.setVisibility(View.GONE);
        regresarButton.setVisibility(View.GONE);
    }
    private void showRuidoButtons() {
        ruidoBlancoSuaveButton.setVisibility(View.VISIBLE);
        tonoModuladoButton.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, R.id.tonoModuladoButton);
        regresarButton.setLayoutParams(params);
        regresarButton.setVisibility(View.VISIBLE);
    }

    private void hideRuidoButtons() {
        ruidoBlancoSuaveButton.setVisibility(View.GONE);
        tonoModuladoButton.setVisibility(View.GONE);
    }

    private void showPitchButtons() {
        arribaButton.setVisibility(View.VISIBLE);
        abajoButton.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, R.id.abajoButton);
        regresarButton.setLayoutParams(params);
        regresarButton.setVisibility(View.VISIBLE);
    }

    private void hidePitchButtons() {
        arribaButton.setVisibility(View.GONE);
        abajoButton.setVisibility(View.GONE);
    }

    private void showVolumeControl() {
        // Crear un PopupWindow
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);

        // Configurar el contenido del PopupWindow
        SeekBar seekBar = popupView.findViewById(R.id.volumeSeekBar);
        seekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        seekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Crear el PopupWindow
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

        // Evitar que el PopupWindow se cierre cuando se toca fuera de él
        popupWindow.setOutsideTouchable(false);

        // Mostrar el PopupWindow
        popupWindow.showAsDropDown(volumenButton, 0, 0);
    }

    private void hideOtherButtons(View view) {
        if (view.getId() != volumenButton.getId()) {
            volumenButton.setVisibility(View.GONE);
        }
        if (view.getId() != ruidoButton.getId()) {
            ruidoButton.setVisibility(View.GONE);
        }
        if (view.getId() != programaButton.getId()) {
            programaButton.setVisibility(View.GONE);
        }
        if (view.getId() != pitchButton.getId()) {
            pitchButton.setVisibility(View.GONE);
        }
    }
    private void showPopupWindow(View view, String options) {
        // Crear un PopupWindow
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);

        // Configurar el contenido del PopupWindow
        TextView textView = popupView.findViewById(R.id.popupTextView);
        textView.setText(options);

        // Crear el PopupWindow
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

        // Mostrar el PopupWindow
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }
    private void showPopupWindow(View view, String[] options) {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Elige una opción")
            .setItems(options, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Realizar la acción correspondiente a la opción seleccionada
                    // Por ejemplo, puedes mostrar un Toast con la opción seleccionada
                    Toast.makeText(getApplicationContext(), options[which], Toast.LENGTH_SHORT).show();

                    // Mostrar todos los botones
                    showAllButtons();
                }
            });
    builder.create().show();
}

    private MediaPlayer mediaPlayer;
}