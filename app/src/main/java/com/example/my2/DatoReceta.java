package com.example.my2;

import android.net.Uri;

public class DatoReceta {

        //Variables con el mismo nombre de la base de datos
        private byte[] imgp;
        private String nombrep;
        private String preparar;
        private  int id;
        private String ingre,categoria;

        public DatoReceta(){

        }

    public DatoReceta (/*byte[] imgp,*/ String nombrep, String preparar, /*String ingre,*/String categoria){

        this.imgp =imgp;
        this.nombrep = nombrep;
        this.preparar = preparar;
        this.ingre = ingre;
        this.categoria = categoria;
    }

        public byte[] getImgp() {
            return imgp;
        }

        public void setImgp(byte[] imgp) {
            this.imgp = imgp;
        }

        public int getId(){
        return id;
    }

        public void setId(int id) {
        this.id = id;
    }

        public String getNombrep() {
            return nombrep;
        }

        public void setNombrep(String nombrep) {
            this.nombrep = nombrep;
        }

        public String getPreparar() { return preparar; }

        public void setPreparar(String preparar) { this.preparar = preparar; }

        public String getIngre() { return ingre; }

        public void setIngre(String ingre) { this.ingre = ingre; }

        public String getCategoria() { return categoria; }

        public void setCategoria(String categoria) { this.categoria = categoria; }








}
