package model;
/**
 * @author Ellou
 **/
public class ZoneSecurisee {
    private int idZone;
    private String nomZone;
    private int niveauRequis;
    private static int compteurZone = 0;

    public ZoneSecurisee(String nomZone, int niveauRequis) {
        this.idZone = ++compteurZone;
        this.nomZone = nomZone;
        this.niveauRequis = niveauRequis;
    }

    public int getIdZone() {
        return idZone;
    }

    public String getNomZone() {
        return nomZone;
    }

    public int getNiveauRequis() {
        return niveauRequis;
    }
}
