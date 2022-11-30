package com.example.my2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class FragmentInicio extends Fragment {

    // Variables Globales
    private RecyclerView mRvInicio;
    private ProgressBar mProgInicio;
    private String mUser, key;
    private FloatingActionButton mBtnFlo;
    private ImageView mImgPizza, mImgCH, mImgHam;
    private String mCategoria;
    private FirebaseRecyclerAdapter<DatoReceta,DatosViewHolder> firebaseRecyclerAdapter;
    private FirebaseRecyclerAdapter<DatoReceta, PeopleVH> mPeopleRVAdapter;

    private DatabaseReference mReferenciaLista,mRefenciaUsuario;
    private FirebaseAuth mAuth;

    private LinearLayoutManager mLayout;





    public FragmentInicio() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Creamos una vista del layout que usaremos
        View view = inflater.inflate(R.layout.fragment_home, container, false);



        mRefenciaUsuario = FirebaseDatabase.getInstance().getReference().child("usuarios");
        mAuth=FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser().getUid().trim();

        // Enlace de variables
        mRvInicio =(RecyclerView)view.findViewById(R.id.lista);
        mBtnFlo = (FloatingActionButton)view.findViewById(R.id.btnflo);
        mImgCH = (ImageView)view.findViewById(R.id.imgchi);
        mImgHam = (ImageView)view.findViewById(R.id.imghami);
        mImgPizza = (ImageView)view.findViewById(R.id.imgpizzai);
        mRvInicio.setHasFixedSize(true);

        mProgInicio = (ProgressBar)view.findViewById(R.id.progress);
        mLayout =new GridLayoutManager(getActivity(),2);

        mRvInicio.setLayoutManager(mLayout);

        mBtnFlo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //startActivity(new Intent(getContext(), ActivityReceta.class));

            }
        });


        Drawable originalDrawable = getResources().getDrawable(R.drawable.pizza);
        Drawable originalDrawable3 = getResources().getDrawable(R.drawable.comidachina);
        Drawable originalDrawable2 = getResources().getDrawable(R.drawable.hamburgusa);

        Bitmap originalBitmap = ((BitmapDrawable) originalDrawable).getBitmap();
        Bitmap originalBitmap2 = ((BitmapDrawable) originalDrawable2).getBitmap();
        Bitmap originalBitmap3 = ((BitmapDrawable) originalDrawable3).getBitmap();

        //creamos el drawable redondeado
        RoundedBitmapDrawable roundedDrawable =
                RoundedBitmapDrawableFactory.create(getResources(), originalBitmap);

        //asignamos el CornerRadius
        roundedDrawable.setCornerRadius(originalBitmap.getHeight());

        //creamos el drawable redondeado
        RoundedBitmapDrawable roundedDrawable2 =
                RoundedBitmapDrawableFactory.create(getResources(), originalBitmap2);

        //asignamos el CornerRadius
        roundedDrawable2.setCornerRadius(originalBitmap3.getHeight());

        //creamos el drawable redondeado
        RoundedBitmapDrawable roundedDrawable3 =
                RoundedBitmapDrawableFactory.create(getResources(), originalBitmap3);

        //asignamos el CornerRadius
        roundedDrawable3.setCornerRadius(originalBitmap3.getHeight());




        mImgPizza.setImageDrawable(roundedDrawable);
        mImgHam.setImageDrawable(roundedDrawable2);
        mImgCH.setImageDrawable(roundedDrawable3);




        mImgPizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategoria = "pizza";
                mRvInicio.setVisibility(View.VISIBLE);
                mProgInicio.setVisibility(View.VISIBLE);
                mRefenciaUsuario.child(mUser).child("categoria").setValue(mCategoria);
            }
        });
        mImgCH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategoria = "comidac";
                mRvInicio.setVisibility(View.VISIBLE);
                mProgInicio.setVisibility(View.VISIBLE);
                mRefenciaUsuario.child(mUser).child("categoria").setValue(mCategoria);
            }
        });
        mImgHam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategoria = "hamburgesa";
                mRvInicio.setVisibility(View.VISIBLE);
                mProgInicio.setVisibility(View.VISIBLE);
                mRefenciaUsuario.child(mUser).child("categoria").setValue(mCategoria);
            }
        });


        mRefenciaUsuario.child(mUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null){

                    if (dataSnapshot.child("tipousu").exists()&& dataSnapshot.child("tipousu").getValue().toString().equals("Administrador")){

                        mBtnFlo.setVisibility(View.VISIBLE);

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        return view;
    }



    @Override
    public void onStart(){
        super.onStart();


        mRefenciaUsuario.child(mUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot!=null){

                    if (dataSnapshot.child("categoria").exists() && dataSnapshot.child("categoria").getValue().toString().trim()!=null){

                        if (mCategoria!=null){
                            mReferenciaLista = FirebaseDatabase.getInstance().getReference().child("productos").child(mCategoria);

                            Query personsQuery = mReferenciaLista.orderByKey();

                            FirebaseRecyclerOptions<DatoReceta> options = new FirebaseRecyclerOptions.Builder<DatoReceta>().setQuery(personsQuery, DatoReceta.class).build();

                            mPeopleRVAdapter = new FirebaseRecyclerAdapter<DatoReceta, FragmentInicio.PeopleVH>(options) {
                                @Override
                                protected void onBindViewHolder(PeopleVH holder, final int position, DatoReceta model) {



                                    if (holder != null && model!=null){



                                        mProgInicio.setVisibility(View.GONE);
                                        //holder.setImgp(getActivity(),model.getImgp());
                                        //holder.setPreciop(model.getPreciop());

                                        holder.setNombrep(model.getNombrep());

                                        holder.mView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                key = getRef(position).getKey();

                                                Intent intent = new Intent(getContext(),ActivityDetalles.class);
                                                intent.putExtra("ID",key);
                                                startActivity(intent);

                                            }
                                        });


                                    }




                                }


                                @Override
                                public FragmentInicio.PeopleVH onCreateViewHolder(ViewGroup parent, int viewType) {

                                    View view = LayoutInflater.from(parent.getContext())
                                            .inflate(R.layout.cartaprincipal, parent, false);

                                    return new PeopleVH(view);
                                }
                            };
                            mPeopleRVAdapter.startListening();
                            mRvInicio.setAdapter(mPeopleRVAdapter);
                        }
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });










    }


    private static class DatosViewHolder extends RecyclerView.ViewHolder {

        View mView;


        public DatosViewHolder(View itemView) {

            super(itemView);
            mView=itemView;
        }




    }


    private class PeopleVH extends RecyclerView.ViewHolder {

        View mView;
        PeopleVH(View view) {
            super(view);

            mView = view;
        }

        void setImgp(Context context, String imgp){

            if (imgp!=null){
                ImageView imagenP= (ImageView)mView.findViewById(R.id.imgproducto);
                Glide.with(context).load(imgp).into(imagenP);

            }

        }


        void setNombrep(String nombrep){
            if (nombrep!=null){
                TextView viewnombre=(TextView)mView.findViewById(R.id.txtnompro);
                viewnombre.setText(nombrep);
            }

        }

        void setPreciop(String preciop){

            if (preciop!=null){
                TextView viewprecio=(TextView)mView.findViewById(R.id.txtpreper);
                viewprecio.setText(preciop);

            }


        }




    }





    @Override
    public void onStop() {
        super.onStop();
        mPeopleRVAdapter.stopListening();
    }

}

