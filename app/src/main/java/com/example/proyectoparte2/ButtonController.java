package com.example.proyectoparte2;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;

public class ButtonController {
    private Activity activity;
    private Map<Integer, Button> buttonMap = new HashMap<>();
   public int getVisibility() {
        // Devuelve la visibilidad del primer botón
        if (!buttonMap.isEmpty()) {
            return buttonMap.values().iterator().next().getVisibility();
        }
        return View.GONE; // Devuelve GONE por defecto si no hay botones
}

    public ButtonController(Activity activity, int... buttonIds) {
        this.activity = activity;
        for (int buttonId : buttonIds) {
            buttonMap.put(buttonId, activity.findViewById(buttonId));
        }
    }

    public void setVisibility(int visibility) {
        for (Button button : buttonMap.values()) {
            button.setVisibility(visibility);
        }
    }

    public void setOnClickListener(int buttonId, View.OnClickListener listener) {
        if (buttonMap.containsKey(buttonId)) {
            buttonMap.get(buttonId).setOnClickListener(listener);
        }
    }

    public void setVisibleButtons(int... buttonIds) {
        setVisibility(View.GONE); // Ocultar todos primero
        for (int buttonId : buttonIds) {
            if (buttonMap.containsKey(buttonId)) {
                buttonMap.get(buttonId).setVisibility(View.VISIBLE);
            }
        }
    }
    public void setInvisibleButtons(int... buttonIds) {
        for (int buttonId : buttonIds) {
            if (buttonMap.containsKey(buttonId)) {
                buttonMap.get(buttonId).setVisibility(View.INVISIBLE);
            }
        }
    }
}
