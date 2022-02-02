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
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BaseInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    float valorIni = 0f;
    float valorFin = 2f;
    int tiempo = 1000;

    int interpolPosicion = -1;
    int movPosicion = -1;

    ImageView imageMario;
    ListView listAtrib;
    EditText editValorIni, editValorFin, editTiempo;
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
        editTiempo = findViewById(R.id.editTiempo);
        editTiempo.setVisibility(View.INVISIBLE);

        arrayMovimientos = new ArrayAdapter(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.movimientos));
        listAtrib = findViewById(R.id.listAtrib);
        listAtrib.setAdapter(arrayMovimientos);
        listAtrib.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cambiarAnimacionPred(position);
            }
        });

        imageMario = findViewById(R.id.imageMario);
        registerForContextMenu(imageMario);
        imageMario.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View v) {
                empezarAnimacion();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_opciones, menu);
        menu.removeItem(R.id.opcion_animacion_prop);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opcion_animacion_imag:
                Intent intencion = new Intent(this, AnimacionImagenes.class);
                startActivity(intencion);
                return true;
            case R.id.opcion_restablecer:
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
            case R.id.opcion_interpol:
                editValorIni.setVisibility(View.INVISIBLE);
                editTiempo.setVisibility(View.INVISIBLE);
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
                        interpolPosicion = position;
                    }
                });
                break;
            case R.id.opcion_anim_predet:
                editValorIni.setVisibility(View.INVISIBLE);
                editValorFin.setVisibility(View.INVISIBLE);
                editTiempo.setVisibility(View.INVISIBLE);
                crossfade(listAtrib);
                listAtrib.setAdapter(arrayMovimientos);
                listAtrib.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        cambiarAnimacionPred(position);
                    }
                });
                break;
            case R.id.opcion_anim_pers:
                crossfade(editTiempo);
                crossfade(editValorIni);
                crossfade(editValorIni);
                crossfade(editValorFin);
                crossfade(listAtrib);
                listAtrib.setAdapter(arrayMovimientos);
                listAtrib.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        movPosicion = position;
                    }
                });
                break;

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
                break;
            case 1:
                interpolador = new AccelerateInterpolator();
                break;
            case 2:
                interpolador = new AnticipateInterpolator();
                break;
            case 3:
                interpolador = new AnticipateOvershootInterpolator();
                break;
            case 4:
                interpolador = new BounceInterpolator();
                break;
            case 5:
                interpolador = new LinearInterpolator();
                break;
            case 6:
                interpolador = new CycleInterpolator(10);
                break;
            case 7:
                interpolador = new DecelerateInterpolator();
                break;
            case 8:
                interpolador = new OvershootInterpolator();
                break;
            default:
                interpolador = new AccelerateInterpolator();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public void cambiarAnimacionPred(int position) {
        switch (position) {
            case 0:
                animacion = ObjectAnimator.ofFloat(imageMario, View.ALPHA, 0, 1);
                break;
            case 1:
                animacion = ObjectAnimator.ofFloat(imageMario, View.ROTATION, 0, 360);
                break;
            case 2:
                animacion=  ObjectAnimator.ofFloat(imageMario, View.ROTATION_X, 0, 360);
                break;
            case 3:
                animacion = ObjectAnimator.ofFloat(imageMario, View.ROTATION_Y, 0, 360);
                break;
            case 4:
                animacion = ObjectAnimator.ofFloat(imageMario, View.SCALE_X, 0, 1);
                break;
            case 5:
                animacion = ObjectAnimator.ofFloat(imageMario, View.SCALE_Y, 0, 1);
                break;
            case 6:
                animacion = ObjectAnimator.ofFloat(imageMario, View.X, 0, 1000);
                break;
            case 7:
                animacion = ObjectAnimator.ofFloat(imageMario, View.Y, 0, 1000);
                break;
            default:
                animacion = ObjectAnimator.ofFloat(imageMario, View.X, 0, 1000);
        }
        cambiarInterpolador(interpolPosicion);
        animacion.setInterpolator(interpolador);
        animacion.setDuration(2500);
        animacion.start();
    }

    public void cambiarAnimacion(int position) {
        switch (position) {
            case 0:
                animacion = ObjectAnimator.ofFloat(imageMario, View.ALPHA, valorIni);
                break;
            case 1:
                animacion = ObjectAnimator.ofFloat(imageMario, View.ROTATION, valorIni);
                break;
            case 2:
                animacion=  ObjectAnimator.ofFloat(imageMario, View.ROTATION_X, valorIni);
                break;
            case 3:
                animacion = ObjectAnimator.ofFloat(imageMario, View.ROTATION_Y, valorIni);
                break;
            case 4:
                animacion = ObjectAnimator.ofFloat(imageMario, View.SCALE_X, valorIni);
                break;
            case 5:
                animacion = ObjectAnimator.ofFloat(imageMario, View.SCALE_Y, valorIni);
                break;
            case 6:
                animacion = ObjectAnimator.ofFloat(imageMario, View.X, valorIni);
                break;
            case 7:
                animacion = ObjectAnimator.ofFloat(imageMario, View.Y, valorIni);
                break;
            default:
                animacion = ObjectAnimator.ofFloat(imageMario, View.X, valorIni);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public void empezarAnimacion() {
        try {
            if (String.valueOf(editTiempo.getText()).equals(""))
                tiempo = 1000;
            else
                tiempo = Integer.parseInt(String.valueOf(editValorIni.getText()));

            if (String.valueOf(editValorIni.getText()).equals(""))
                valorIni = 0;
            else
                valorIni = Float.parseFloat(String.valueOf(editValorIni.getText()));

            if (String.valueOf(editValorFin.getText()).equals(""))
                valorFin = 0;
            else {
                valorFin = Float.parseFloat(String.valueOf(editValorFin.getText()));
            }

        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        cambiarAnimacion(movPosicion);
        cambiarInterpolador(interpolPosicion);
        animacion.setDuration(tiempo);
        animacion.setInterpolator(interpolador);
        animacion.start();
        Snackbar cancelar = Snackbar.make(imageMario, "Cancelar animación", Snackbar.LENGTH_SHORT);
        cancelar.show();
    }
}