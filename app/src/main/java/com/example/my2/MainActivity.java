package com.example.my2;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my2.adapter.ListaIngreAdapter;
import com.example.my2.adapter.RecetaAdapter;
import com.example.my2.ui.dashboard.DashboardFragment;
import com.example.my2.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private static ArrayList<DatoReceta> arrayList;
    public MyOpenHelper dbHandler;
    private EditText mEditnombre, mEditIngrediente, mEditDes;
    private String mUser, mNombre, mIngre, mUrl, mCategoria, mDes;
    private Button mBtnCargar;
    private static final int GALLERY_REGUEST = 1;
    private Uri mUrlPerfil;
    private TextView mTxtSeleccion;
    private ImageView mImgPro, mImgPizza, mImgCH, mImgHam;
    private RecetaAdapter adapter;
    private static RecyclerView mRvInicio1;
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
    private MyDatabase dbHandler2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


        dbHandler = new MyOpenHelper(this, "RECETADB.sqlite", null, 1);
        dbHandler.queryData("CREATE TABLE IF NOT EXISTS RECETA (Id INTEGER PRIMARY KEY AUTOINCREMENT, nombre VARCHAR, preparar VARCHAR, ingrediente VARCHAR, imagen BLOB, categoria VARCHAR)");
        mImgPro = (ImageView)findViewById(R.id.imgprop);
        mRvInicio1 =(RecyclerView)findViewById(R.id.listaIngre);
        dbHandler2 = new MyDatabase(getBaseContext());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mImgPro = (ImageView)findViewById(R.id.imgprop);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REGUEST && resultCode == RESULT_OK) {

            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                mImgPro.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
    }



    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }


    public void insertarImagen(){

        Intent intentimg = new Intent(Intent.ACTION_GET_CONTENT);
        intentimg.setType("image/*");
        startActivityForResult(intentimg, GALLERY_REGUEST);
    }


    public void categoriaP(View v){
        mCategoria = "pizza";
        insertarImagen();
    }

    public void publicar(View v){
        mEditnombre = (EditText) findViewById(R.id.txtnompp);
        mEditIngrediente = findViewById(R.id.txtingredientes);
        mEditDes = (EditText)findViewById(R.id.txtdespp);
        mNombre = mEditnombre.getText().toString();
        mDes = mEditDes.getText().toString();
        mIngre = mEditIngrediente.getText().toString();


        DatoReceta receta = new DatoReceta(/*imageViewToByte(mImgPro),*/mNombre, mDes,/*mIngre,*/"hamburguesa");
        dbHandler.addReceta(receta);

        mEditnombre.setText("");
        mEditDes.setText("");
        mEditIngrediente.setText("");

        Toast.makeText(getApplicationContext(), "Carga Completa!", Toast.LENGTH_LONG).show();
    }

    public void insertarIngre(View v)
    {
        mEditnombre = (EditText) findViewById(R.id.txtnompp);
        mEditIngrediente = findViewById(R.id.txtingredientes);
        mEditDes = (EditText)findViewById(R.id.txtdespp);
        mNombre = mEditnombre.getText().toString();
        mDes = mEditDes.getText().toString();
        mIngre = mEditIngrediente.getText().toString();


        DatoReceta receta = new DatoReceta(/*imageViewToByte(mImgPro),*/mNombre, mDes,/*mIngre,*/"hamburguesa");
        SQLiteDatabase db = dbHandler2.getWritableDatabase();
        dbHandler2.addReceta(db,receta);
    }



    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_buscador,menu);
        MenuItem item = menu.findItem(R.id.buscador);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {

                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                HomeFragment home = new HomeFragment();
                adapter = home.adapter(arrayList,mRvInicio1);
                adapter.setOnTapListener(new OnTapListener() {
                    @Override
                    public void OnTapView(int position) {
                        // Toast.makeText(getContext(),"Click to"+position, Toast.LENGTH_SHORT).show();
                      int  posicionReceta = arrayList.get(position).getId();

                        cargarDetalle(posicionReceta);

                    }
                });

                return true;
            }
        });


        return  true;
    }

    public void cargarDetalle(int position)
    {
        Intent intent = new Intent(getBaseContext(),ActivityDetalles.class);
        intent.putExtra("Id", position);
        startActivity(intent);
    }

    public static void  arrayList(ArrayList<DatoReceta> lista, RecyclerView mRvInicio){
        arrayList = lista;
        mRvInicio1 = mRvInicio;

    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String nuevoTexto) {
        try {


            HomeFragment home = new HomeFragment();

            filtro(arrayList,nuevoTexto,mRvInicio1);



        }catch (Exception e){
                e.printStackTrace();
        }
        return false;
    }

    //Mostrar los resultados de la busqueda
    public void filtro(ArrayList<DatoReceta> arrayList1, String texto, RecyclerView mRvInicio){


        try {
            final ArrayList<DatoReceta>listaFiltrado = new ArrayList<>();
            //texto que se utiliza para filtrar y se separa la palabra
            texto = texto.toLowerCase();

            //Para recorrer la lista
            for(DatoReceta recetas: arrayList1){

                String recetas2 = recetas.getNombrep().toLowerCase();

                //Se hace una comparación
                if(recetas2.contains(texto)){

                    listaFiltrado.add(recetas);
                }

            }
            HomeFragment home = new HomeFragment();
            adapter = home.adapter(listaFiltrado,mRvInicio);

            adapter.setOnTapListener(new OnTapListener() {
                @Override
                public void OnTapView(int position) {
                    // Toast.makeText(getContext(),"Click to"+position, Toast.LENGTH_SHORT).show();
                   int posicionReceta = listaFiltrado.get(position).getId();
                    cargarDetalle(posicionReceta);
                }
            });



        }catch (Exception e){
            e.printStackTrace();
        }


    }

}
