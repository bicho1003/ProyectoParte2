package com.example.proyectoparte2;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;

public class pantallaProgramas extends Activity {
    private ButtonController mainButtonController;
    private ButtonController programButtonController;
    private ButtonController noiseButtonController;
    private ButtonController pitchButtonController;
    private AudioController audioController;
    private PopupController popupController;
    BluetoothConnector bluetoothConnector = BluetoothConnector.getInstance();
    private Thread blinkThread;
    private int startTime;
    private String currentProgram;
    private volatile boolean isPaused = false;
    private volatile boolean isRuido = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantallaprogramas);
        mainButtonController = new ButtonController(this, R.id.volumenButton, R.id.ruidoButton, R.id.programaButton, R.id.pitchButton);
        programButtonController = new ButtonController(this, R.id.programaA0Button, R.id.programaA1Button, R.id.programaA2Button, R.id.startButton, R.id.pauseButton, R.id.resumeButton);
        noiseButtonController = new ButtonController(this, R.id.ruidoBlancoSuaveButton, R.id.tonoModuladoButton);
        pitchButtonController = new ButtonController(this, R.id.arribaButton, R.id.abajoButton);
        audioController = new AudioController(this, R.raw.a0);  // Cambiar el recurso según sea necesario
        popupController = new PopupController(this);
        configureButtonActions();
    }
    private void configureButtonActions() {
        // Variable para controlar si los botones están ocultos o no
        final boolean[] areButtonsHidden = {false, false, false, false, false};
        mainButtonController.setOnClickListener(R.id.volumenButton, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!areButtonsHidden[0]) {
                    // Ocultar todos los botones excepto el botón de volumen
                    mainButtonController.setInvisibleButtons(R.id.ruidoButton, R.id.programaButton, R.id.pitchButton);
                    mainButtonController.setVisibleButtons(R.id.volumenButton);

                    // Mostrar la barra de volumen
                    popupController.showVolumePopup(v, audioController);

                    areButtonsHidden[0] = true;
                } else {
                    // Mostrar todos los botones
                    mainButtonController.setVisibility(View.VISIBLE);

                    // Ocultar la barra de volumen
                    popupController.hideVolumePopup();

                    areButtonsHidden[0] = false;
                }
            }
        });

        mainButtonController.setOnClickListener(R.id.ruidoButton, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!areButtonsHidden[1]) {
                    // Hacer invisibles todos los botones excepto los botones de ruido/tono blanco
                    mainButtonController.setInvisibleButtons(R.id.volumenButton, R.id.programaButton, R.id.pitchButton);
                    // Mostrar solo los botones de ruido/tono blanco
                    noiseButtonController.setVisibleButtons(R.id.ruidoBlancoSuaveButton, R.id.tonoModuladoButton);
                    areButtonsHidden[1] = true;
                } else {
                    // Mostrar todos los botones
                    mainButtonController.setVisibility(View.VISIBLE);
                    // Hacer invisibles los botones de ruido/tono blanco
                    noiseButtonController.setInvisibleButtons(R.id.ruidoBlancoSuaveButton, R.id.tonoModuladoButton);
                    areButtonsHidden[1] = false;
                }
            }
        });
        noiseButtonController.setOnClickListener(R.id.ruidoBlancoSuaveButton, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cambiar el recurso de audio a ruido blanco suave
                isRuido = true;
            }
        });
        noiseButtonController.setOnClickListener(R.id.tonoModuladoButton, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cambiar el recurso de audio a un tono modulado
                isRuido = false;
            }
        });
        mainButtonController.setOnClickListener(R.id.programaButton, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!areButtonsHidden[2]) {
                    // Hacer invisibles todos los botones excepto los botones de programa
                    mainButtonController.setInvisibleButtons(R.id.volumenButton, R.id.ruidoButton, R.id.pitchButton);
                    // Mostrar solo los botones de programa
                    programButtonController.setVisibleButtons(R.id.programaA0Button, R.id.programaA1Button, R.id.programaA2Button);
                    areButtonsHidden[2] = true;
                } else {
                    // Mostrar todos los botones
                    mainButtonController.setVisibility(View.VISIBLE);
                    // Hacer invisibles los botones de programa
                    programButtonController.setInvisibleButtons(R.id.programaA0Button, R.id.programaA1Button, R.id.programaA2Button);
                    areButtonsHidden[2] = false;
                }
            }
        });
        mainButtonController.setOnClickListener(R.id.pitchButton, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!areButtonsHidden[3]) {
                    // Hacer invisibles todos los botones excepto los botones de pitch
                    mainButtonController.setInvisibleButtons(R.id.volumenButton, R.id.ruidoButton, R.id.programaButton);
                    // Mostrar solo los botones de pitch
                    pitchButtonController.setVisibleButtons(R.id.arribaButton, R.id.abajoButton);
                    areButtonsHidden[3] = true;
                } else {
                    // Mostrar todos los botones
                    mainButtonController.setVisibility(View.VISIBLE);
                    // Hacer invisibles los botones de pitch
                    pitchButtonController.setInvisibleButtons(
                            R.id.arribaButton,
                            R.id.abajoButton
                    );
                    areButtonsHidden[3] = false;
                }
            }
        });
        pitchButtonController.setOnClickListener(R.id.arribaButton, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        pitchButtonController.setOnClickListener(R.id.abajoButton, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        programButtonController.setOnClickListener(R.id.programaA0Button, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!areButtonsHidden[4]) {
                    programButtonController.setVisibleButtons(R.id.startButton, R.id.pauseButton, R.id.resumeButton, R.id.programaA0Button);
                    programButtonController.setInvisibleButtons(R.id.programaA1Button, R.id.programaA2Button);
                    mainButtonController.setInvisibleButtons(R.id.programaButton); // Ocultar el botón de programa
                    areButtonsHidden[4] = true;
                } else {
                    programButtonController.setInvisibleButtons(R.id.startButton, R.id.pauseButton, R.id.resumeButton);
                    programButtonController.setVisibleButtons(R.id.programaA0Button,R.id.programaA1Button,R.id.programaA2Button); // Mostrar el botón programaA0Button
                    mainButtonController.setVisibleButtons(R.id.programaButton); // Mostrar el botón de programa
                    areButtonsHidden[4] = false;
                }
                if (!isRuido) {
                    audioController.changeAudioResource(R.raw.a0); // Cambiar el recurso de audio
                }else {
                    audioController.changeAudioResource(R.raw.ruidoblanco);
                }
                currentProgram = "A0";
            }
        });
        programButtonController.setOnClickListener(R.id.programaA1Button, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!areButtonsHidden[4]) {
                    programButtonController.setVisibleButtons(R.id.startButton, R.id.pauseButton, R.id.resumeButton, R.id.programaA1Button);
                    programButtonController.setInvisibleButtons(R.id.programaA0Button, R.id.programaA2Button);
                    mainButtonController.setInvisibleButtons(R.id.programaButton); // Ocultar el botón de programa
                    areButtonsHidden[4] = true;
                } else {
                    programButtonController.setInvisibleButtons(R.id.startButton, R.id.pauseButton, R.id.resumeButton);
                    programButtonController.setVisibleButtons(R.id.programaA0Button,R.id.programaA1Button,R.id.programaA2Button); // Mostrar el botón programaA0Button
                    mainButtonController.setVisibleButtons(R.id.programaButton); // Mostrar el botón de programa
                    areButtonsHidden[4] = false;
                }
                if (!isRuido) {
                    audioController.changeAudioResource(R.raw.a1); // Cambiar el recurso de audio
                }else{
                    audioController.changeAudioResource(R.raw.ruidoblanco); // Cambiar el recurso de audio
                }
                currentProgram = "A1";
            }
        });
        programButtonController.setOnClickListener(R.id.programaA2Button, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!areButtonsHidden[4]) {
                    programButtonController.setVisibleButtons(R.id.startButton, R.id.pauseButton, R.id.resumeButton, R.id.programaA2Button);
                    programButtonController.setInvisibleButtons(R.id.programaA0Button, R.id.programaA1Button);
                    mainButtonController.setInvisibleButtons(R.id.programaButton); // Ocultar el botón de programa
                    areButtonsHidden[4] = true;
                } else {
                    programButtonController.setInvisibleButtons(R.id.startButton, R.id.pauseButton, R.id.resumeButton);
                    programButtonController.setVisibleButtons(R.id.programaA0Button,R.id.programaA1Button,R.id.programaA2Button); // Mostrar el botón programaA0Button
                    mainButtonController.setVisibleButtons(R.id.programaButton); // Mostrar el botón de programa
                    areButtonsHidden[4] = false;
                }
                if (!isRuido) {
                    audioController.changeAudioResource(R.raw.a1); // Cambiar el recurso de audio
                }else {
                    audioController.changeAudioResource(R.raw.ruidoblanco); // Cambiar el recurso de audio
                }
                currentProgram = "A2";
            }
        });
        programButtonController.setOnClickListener(R.id.startButton, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la reproducción del audio
                audioController.play();

                // Iniciar la secuencia de parpadeo
                if (blinkThread != null && blinkThread.isAlive()) {
                    blinkThread.interrupt(); // Interrumpe el hilo si ya está ejecutándose
                }
                startTime = 0;

                Thread blinkThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // Ejecutar la secuencia de parpadeo
                            switch (currentProgram) {
                                case "A0":
                                    blinkSequence(18f, 7f, 5 * 60,"32"); // de 18hz a 7hz en 5 minutos
                                    blinkSequence(7f, 7f, 7 * 60,"32"); // mantener en 7hz durante 7 minutos
                                    blinkSequence(7f, 40f, 3 * 60,"32"); // de 7hz a 40hz en 3 minutos
                                    break;
                                case "A1":
                                    blinkSequence(17f, 16f, 1 * 60,"1202"); // de 18hz a 7hz en 5 minutos
                                    blinkSequence(16f, 15f, 1 * 60,"32");
                                    blinkSequence(15f, 14f, 1 * 60,"32");
                                    blinkSequence(14f, 12f, 1 * 60,"3222");
                                    blinkSequence(12f, 11f, 1 * 60,"1202");
                                    blinkSequence(11f, 5f, 2 * 60,"1202");
                                    blinkSequence(5f, 5f, 7 * 60,"1202");
                                    blinkSequence(5f, 20f, 2 * 60,"1202");
                                    break;
                                case "A2":
                                    blinkSequence(8f, 30f, 3 * 60,"1202");
                                    blinkSequence(30f, 6f, 3 * 60,"3222");
                                    blinkSequence(6f, 20f, 3 * 60,"32");
                                    blinkSequence(20f, 4f, 3 * 60,"1202");
                                    blinkSequence(4f, 18f, 3 * 60,"3222");
                                    blinkSequence(18f, 6f, 3 * 60,"32");
                                    blinkSequence(6f, 24f, 3 * 60,"1202");
                                    blinkSequence(24f, 10f, 3 * 60,"3222");
                                    blinkSequence(10f, 20f, 3 * 60,"32");
                                    blinkSequence(20f, 6f, 2 * 60,"1202");
                                    blinkSequence(6f, 30f, 1 * 60,"32");
                                    break;
                            }
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt(); // Restablecer el estado interrumpido
                        }
                    }
                });
                Thread amplitudeThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // Ejecutar la secuencia de amplitud
                            switch (currentProgram) {
                                case "A0":
                                    amplitudeSequence(50, 100, 3 * 60); // de 50 a 100 en 3 minutos
                                    amplitudeSequence(100, 80, 2 * 60); // de 100 a 80 en 2 minutos
                                    amplitudeSequence(80, 50, 7 * 60); // de 80 a 50 en 7 minutos
                                    amplitudeSequence(50, 100, 2 * 60); // de 50 a 100 en 2 minutos
                                    amplitudeSequence(100, 100, 1 * 60); // mantener en 100 durante 1 minuto
                                    amplitudeSequence(100, 0, 1 * 60); // de 100 a 0 en 1 minuto
                                    break;
                                case "A1":
                                    
                                    break;
                                case "A2":
                                    break;
                            }
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt(); // Restablecer el estado interrumpido
                        }
                    }
                });
                blinkThread.start();
                amplitudeThread.start();
            }
        });

        programButtonController.setOnClickListener(R.id.pauseButton, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pausar la secuencia de parpadeo
                isPaused = true;
                //enviar 0 a arduino para detener la vibración
                sendFrequency(0);
                // Pausar la reproducción del audio
                audioController.pause();
            }
        });
        programButtonController.setOnClickListener(R.id.resumeButton, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reanudar la secuencia de parpadeo
                isPaused = false;
                // Reanudar la reproducción del audio
                    audioController.play();
            }
        });
    }
    private void sendBluetoothCommand(String command) {
        bluetoothConnector.sendData(command + "\n");
    }

    private void sendFrequency(float frequency) {
        sendBluetoothCommand("F" + String.valueOf(frequency));
    }

    private void sendSequence(String sequence) {
        sendBluetoothCommand("S" + sequence);
    }

    private void sendAmplitude(int amplitude) {
        sendBluetoothCommand("A" + String.valueOf(amplitude));
    }
    private void blinkSequence(float startFrequency, float endFrequency, int duration,String sequence) throws InterruptedException {
    float deltaFrequency = (endFrequency - startFrequency) / duration;
    for (int i = 0; i < duration; i++) {
        if (Thread.currentThread().isInterrupted()) {
            return; // Salir de la función si el hilo es interrumpido
        }
        while (isPaused) { // Si isPaused es verdadero, hacer que el hilo se duerma
            Thread.sleep(100);// revisa cada 100ms si isPaused es falso
        }
        float currentFrequency = startFrequency + deltaFrequency * i;
        sendSequence(sequence);
        sendFrequency(currentFrequency);
        Thread.sleep(1000);
    }
}

    private void amplitudeSequence(int startAmplitude, int endAmplitude, int duration) throws InterruptedException {
        float deltaAmplitude = (float)(endAmplitude - startAmplitude) / duration;
        for (int i = 0; i < duration; i++) {
            if (Thread.currentThread().isInterrupted()) {
                return; // Salir de la función si el hilo es interrumpido
            }
            int currentAmplitude = Math.round(startAmplitude + deltaAmplitude * i);
            sendAmplitude(currentAmplitude);
            Thread.sleep(1000);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (blinkThread != null && blinkThread.isAlive()) {
            blinkThread.interrupt(); // Interrumpe el hilo al destruir la actividad
        }
        audioController.release();
    }
}
