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
    private BluetoothConnector bluetoothConnector;
    private Thread blinkThread;
    private int startTime;
    private int endTime = 15 * 60; // 15 minutos en segundos
    private String currentProgram;
    private volatile boolean isPaused = false;

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
        bluetoothConnector = new BluetoothConnector();
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
                bluetoothConnector.connect();
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
                audioController.changeAudioResource(R.raw.a0); // Cambiar el recurso de audio
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
                audioController.changeAudioResource(R.raw.a1);
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
                audioController.changeAudioResource(R.raw.a2);
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
                    blinkThread.interrupt();
                }
                startTime = 0;
                blinkThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (startTime < endTime && !Thread.currentThread().isInterrupted()) {
                            synchronized (blinkThread) {
                                while (isPaused) {
                                    try {
                                        blinkThread.wait();
                                    } catch (InterruptedException e) {
                                        Thread.currentThread().interrupt();
                                    }
                                }
                            }
                            // Aquí puedes implementar la secuencia de parpadeo específica para el programa actualmente seleccionado
                            switch (currentProgram) {
                                case "A0":
                                    // Fase 1: de 18hz a 7hz en 5 minutos
                                    float startFrequency1 = 18f;
                                    float endFrequency1 = 7f;
                                    float duration1 = 5 * 60; // 5 minutos en segundos
                                    float deltaFrequency1 = (endFrequency1 - startFrequency1) / duration1;
                                    for (int i = 0; i < duration1; i++) {
                                        float currentFrequency = startFrequency1 + deltaFrequency1 * i;
                                        bluetoothConnector.sendData(String.valueOf(currentFrequency) + "\n"); // Enviar la frecuencia actual a Arduino con un carácter de nueva línea
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            Thread.currentThread().interrupt(); // Restablecer el estado interrumpido
                                        }
                                    }
                                    // Fase 2: mantener en 7hz durante 7 minutos
                                    float frequency2 = 7f;
                                    float duration2 = 7 * 60; // 7 minutos en segundos
                                    for (int i = 0; i < duration2; i++) {
                                        bluetoothConnector.sendData(String.valueOf(frequency2) + "\n"); // Enviar la frecuencia actual a Arduino con un carácter de nueva línea
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            Thread.currentThread().interrupt(); // Restablecer el estado interrumpido
                                        }
                                    }

                                    // Fase 3: de 7hz a 40hz en 3 minutos
                                    float startFrequency3 = 7f;
                                    float endFrequency3 = 40f;
                                    float duration3 = 3 * 60; // 3 minutos en segundos
                                    float deltaFrequency3 = (endFrequency3 - startFrequency3) / duration3;
                                    for (int i = 0; i < duration3; i++) {
                                        float currentFrequency = startFrequency3 + deltaFrequency3 * i;
                                        bluetoothConnector.sendData(String.valueOf(currentFrequency) + "\n"); // Enviar la frecuencia actual a Arduino con un carácter de nueva línea
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            Thread.currentThread().interrupt(); // Restablecer el estado interrumpido
                                        }
                                    }
                                    break;
                                case "A1":
                                    // Implementar la secuencia de parpadeo para el programa A1
                                    break;
                                case "A2":
                                    // Implementar la secuencia de parpadeo para el programa A2
                                    break;
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                            startTime++;
                        }
                    }
                });
                blinkThread.start();
            }
        });

        programButtonController.setOnClickListener(R.id.pauseButton, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pausar la reproducción del audio
                audioController.pause();

                // Pausar la secuencia de parpadeo
                    isPaused = true;
                    bluetoothConnector.sendData("0\n");
            }
        });
        programButtonController.setOnClickListener(R.id.resumeButton, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reanudar la reproducción del audio
                audioController.play();
                // Reanudar la secuencia de parpadeo
                synchronized (blinkThread) {
                    isPaused = false;
                    blinkThread.notify();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        audioController.release();
    }
}
