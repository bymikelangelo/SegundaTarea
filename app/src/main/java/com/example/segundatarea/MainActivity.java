package com.example.segundatarea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BaseInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageView imageMario;
    int interpolacion = 0;
    int movimiento = 0;
    float valor = 1000f;
    ListView listAtrib;
    EditText editValor;
    ArrayAdapter arrayMovimientos, arrayInterpol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editValor = findViewById(R.id.editValor);
        editValor.setVisibility(View.INVISIBLE);

        listAtrib = findViewById(R.id.listAtrib);
        listAtrib.setVisibility(View.INVISIBLE);

        imageMario = findViewById(R.id.imageMario);
        registerForContextMenu(imageMario);
        imageMario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animacion = cambiarMovimiento();
                animacion.setDuration(2000);
                animacion.setInterpolator(new LinearInterpolator());
                animacion.start();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_opciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.anim_prop:

            case R.id.anim_imag:
                return true;
            default:
                return onOptionsItemSelected(item);
        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_propiedades, menu);
        MenuItem botonMov = menu.getItem(1);
        MenuItem botonInter = menu.getItem(0);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tipo_mov:
                if (arrayMovimientos == null) {
                    arrayMovimientos = new ArrayAdapter(this, android.R.layout.simple_list_item_1,
                            getResources().getStringArray(R.array.movimientos));
                }
                crossfade(editValor);
                crossfade(listAtrib);
                listAtrib.setAdapter(arrayMovimientos);
                break;
            case R.id.tipo_interpol:
                if (arrayInterpol == null) {
                    arrayInterpol = new ArrayAdapter(this, android.R.layout.simple_list_item_1,
                            getResources().getStringArray(R.array.interpoladores));
                }
                editValor.setVisibility(View.INVISIBLE);
                crossfade(listAtrib);
                listAtrib.setAdapter(arrayInterpol);
        }
        return super.onContextItemSelected(item);
    }


    private void crossfade(View vista) {
        int duracionAnimacion = 500;

        vista.setAlpha(0f);
        vista.setVisibility(View.VISIBLE);
        vista.animate().alpha(1f).setDuration(duracionAnimacion).setListener(null);
    }


    public BaseInterpolator cambiarInterpolacion() {
        return new AccelerateDecelerateInterpolator();
    }

    public ObjectAnimator cambiarMovimiento() {
        switch (movimiento) {
            case 0:
                return ObjectAnimator.ofFloat(imageMario, View.X, valor);
            case 1:
                return ObjectAnimator.ofFloat(imageMario, View.Y, valor);
            case 2:
                return ObjectAnimator.ofFloat(imageMario, View.ALPHA, valor);
            case 3:
                return ObjectAnimator.ofFloat(imageMario, View.ROTATION, valor);
            default:
                return ObjectAnimator.ofFloat(imageMario, View.X, valor);
        }
    }
}