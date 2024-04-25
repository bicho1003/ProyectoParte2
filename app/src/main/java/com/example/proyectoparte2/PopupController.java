package com.example.proyectoparte2;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;

public class PopupController {
    private Context context;
    private PopupWindow volumePopupWindow;

    public PopupController(Context context) {
        this.context = context;
    }

    public boolean isVolumePopupShowing() {
        return volumePopupWindow != null && volumePopupWindow.isShowing();
    }

    public void hideVolumePopup() {
        if (volumePopupWindow != null && volumePopupWindow.isShowing()) {
            volumePopupWindow.dismiss();
        }
    }

    public void showVolumePopup(View anchorView, AudioController audioController) {
        if (isVolumePopupShowing()) {
            hideVolumePopup();
            return;
        }

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null); // Usa el diseño correcto para el volumen

        // Crear el PopupWindow para el volumen
        volumePopupWindow = new PopupWindow(popupView,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);

        // Mostrar el PopupWindow justo debajo del botón de volumen
        volumePopupWindow.showAsDropDown(anchorView, 0, 100);

        // Configurar el SeekBar para controlar el volumen
        SeekBar volumeSeekBar = popupView.findViewById(R.id.volumeSeekBar);
        volumeSeekBar.setMax(audioController.getMaxVolume());
        volumeSeekBar.setProgress(audioController.getVolume());
        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioController.setVolume(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // No se necesita implementar
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // No se necesita implementar
            }
        });
    }
}
