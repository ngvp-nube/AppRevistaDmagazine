package com.dmagazine.mydmagazinechile.modelo;

public class ListRevisita {
    String nombreR, linkR;

    public ListRevisita(String nombreR, String link){
        this.nombreR=nombreR;
        this.linkR=link;
    }

    public String getNombreR() {
        return nombreR;
    }

    public void setNombreR(String nombreR) {
        this.nombreR = nombreR;
    }

    public String getLink() {
        return linkR;
    }

    public void setLink(String link) {
        this.linkR = link;
    }
}
