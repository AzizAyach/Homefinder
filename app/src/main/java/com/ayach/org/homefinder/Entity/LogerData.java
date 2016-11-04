package com.ayach.org.homefinder.Entity;

/**
 * Created by aziz on 01/12/2015.
 */
public class LogerData {


    private String pays;
    private String city;
    private int nbchambre;
    private int nbtoil;
    private int nblit;
    private int Pricemin;
    private int Pricemax;
public LogerData(String pays, String city,int nbchambre,int nbtoil ,int nblit,int Pricemin,int Pricemax){

    this.setPays(pays);
    this.setCity(city);
    this.setNbchambre(nbchambre);
    this.setNbtoil(nbtoil);
    this.setNblit(nblit);
    this.setPricemin(Pricemin);
    this.setPricemax(Pricemax);

}
    public LogerData(){}


    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getNbchambre() {
        return nbchambre;
    }

    public void setNbchambre(int nbchambre) {
        this.nbchambre = nbchambre;
    }

    public int getNbtoil() {
        return nbtoil;
    }

    public void setNbtoil(int nbtoil) {
        this.nbtoil = nbtoil;
    }

    public int getNblit() {
        return nblit;
    }

    public void setNblit(int nblit) {
        this.nblit = nblit;
    }


    public int getPricemin() {
        return Pricemin;
    }

    public void setPricemin(int pricemin) {
        Pricemin = pricemin;
    }

    public int getPricemax() {
        return Pricemax;
    }

    public void setPricemax(int pricemax) {
        Pricemax = pricemax;
    }
}
