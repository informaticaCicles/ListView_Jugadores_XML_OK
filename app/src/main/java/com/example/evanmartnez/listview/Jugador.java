package com.example.evanmartnez.listview;

public class Jugador {

    private String nombre;
    private String posicion;
    private int idFoto;
    private String url;

    public Jugador(String aNombre, String aPosicion, int aIdFoto, String aUrl){
        nombre = aNombre;
        posicion = aPosicion;
        idFoto = aIdFoto;
        url = aUrl;
    }

    public String getNombre(){
        return nombre;
    }

    public String getPosicion(){
        return posicion;
    }

    public int getIdFoto(){
        return idFoto;
    }

    public String getUrl(){
        return url;
    }
}
