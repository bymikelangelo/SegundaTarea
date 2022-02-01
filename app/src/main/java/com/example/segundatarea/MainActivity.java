package com.example.segundatarea;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    float valorIni = 0f;
    float valorFin = 2f;
    int duracion = 1000;

    ImageView imageMario;
    ListView listAtrib;
    EditText editValorIni, editValorFin;
    ArrayAdapter arrayMovimientos, arrayInterpol;
    ObjectAnimator animacion;
    BaseInterpolator interpolador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editValorIni = findViewById(R.id.editValorIni);
        editValorIni.setVisibility(View.INVISIBLE);
        editValorFin = findViewById(R.id.editValorFin);
        editValorFin.setVisibility(View.INVISIBLE);

        listAtrib = findViewById(R.id.listAtrib);
        listAtrib.setVisibility(View.INVISIBLE);

        imageMario = findViewById(R.id.imageMario);
        registerForContextMenu(imageMario);
        imageMario.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View v) {
                if (animacion == null) {
                    cambiarMovimiento(-1);
                }
                if (interpolador == null) {
                    cambiarInterpolador(-1);
                }
                animacion.setDuration(duracion);
                animacion.setInterpolator(interpolador);
                animacion.start();
                Snackbar cancelar = Snackbar.make(imageMario, "Cancelar animación", Snackbar.LENGTH_SHORT);
                cancelar.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_opciones, menu);
        menu.removeItem(R.id.animacion_prop);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.animacion_imag:
                Intent intencion = new Intent(this, AnimacionImagenes.class);
                startActivity(intencion);
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
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tipo_mov:
                if (arrayMovimientos == null) {
                    arrayMovimientos = new ArrayAdapter(this, android.R.layout.simple_list_item_1,
                            getResources().getStringArray(R.array.movimientos));
                }
                crossfade(editValorIni);
                crossfade(listAtrib);
                listAtrib.setAdapter(arrayMovimientos);
                listAtrib.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        cambiarMovimiento(position);
                    }
                });
                break;
            case R.id.tipo_interpol:
                if (arrayInterpol == null) {
                    arrayInterpol = new ArrayAdapter(this, android.R.layout.simple_list_item_1,
                            getResources().getStringArray(R.array.interpoladores));
                }
                editValorIni.setVisibility(View.INVISIBLE);
                crossfade(listAtrib);
                listAtrib.setAdapter(arrayInterpol);
                listAtrib.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        cambiarInterpolador(position);
                    }
                });
        }
        return super.onContextItemSelected(item);
    }


    private void crossfade(View vista) {
        int duracionAnimacion = 500;

        vista.setAlpha(0f);
        vista.setVisibility(View.VISIBLE);
        vista.animate().alpha(1f).setDuration(duracionAnimacion).setListener(null);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public void cambiarInterpolador(int position) {
        switch (position) {
            case 0:
                interpolador = new AccelerateDecelerateInterpolator();
            case 1:
                interpolador = new AccelerateInterpolator();
                break;
            case 2:
                interpolador = new BounceInterpolator();
                break;
            case 3:
                interpolador = new OvershootInterpolator();
                break;
            default:
                interpolador = new LinearInterpolator();
        }
    }

    public void cambiarMovimiento(int position) {
        switch (position) {
            case 0:
                animacion = ObjectAnimator.ofFloat(imageMario, View.ALPHA, valorIni, valorFin);
                break;
            case 1:
                animacion = ObjectAnimator.ofFloat(imageMario, View.ROTATION, valorIni, valorFin);
                break;
            case 2:
                animacion=  ObjectAnimator.ofFloat(imageMario, View.ROTATION_X, valorIni, valorFin);
                break;
            case 3:
                animacion = ObjectAnimator.ofFloat(imageMario, View.ROTATION_Y, valorIni, valorFin);
                break;
            case 4:
                animacion = ObjectAnimator.ofFloat(imageMario, View.SCALE_X, valorIni, valorFin);
                break;
            case 5:
                animacion = ObjectAnimator.ofFloat(imageMario, View.SCALE_Y, valorIni, valorFin);
                break;
            default:
                animacion = ObjectAnimator.ofFloat(imageMario, View.X, valorIni);
        }
    }
}