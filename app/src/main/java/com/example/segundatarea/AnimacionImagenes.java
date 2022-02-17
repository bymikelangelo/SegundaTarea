package com.example.segundatarea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class AnimacionImagenes extends AppCompatActivity {

    ImageView imageDrawable;
    AnimationDrawable animacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animacion_imagenes);

        imageDrawable = findViewById(R.id.imageDrawable);
        imageDrawable.setBackgroundResource(R.drawable.run_animation);
        animacion = (AnimationDrawable) imageDrawable.getBackground();
        animacion.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_opciones, menu);
        menu.removeItem(R.id.opcion_animacion_imag);
        menu.removeItem(R.id.opcion_restablecer);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opcion_animacion_prop:
                Intent intencion = new Intent(this, MainActivity.class);
                startActivity(intencion);
                return true;
            default:
                return onOptionsItemSelected(item);
        }
    }
}