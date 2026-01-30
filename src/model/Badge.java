package model;
/**
 * @author Ellou
 **/
// Badge.java
import java.util.Date;

import Interface.Accreditable;

public class Badge implements Accreditable {
    private int numeroBadge;
    private UtilisateurCAN utilisateur;
    private Date dateDebut;
    private Date dateFin;
    private String statut; // VALIDE, INVALIDE, BLOQUE
    private int tentativesFail;

    public Badge(UtilisateurCAN utilisateur, Date dateDebut, Date dateFin) {
        this.utilisateur = utilisateur;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.statut = "VALIDE";
        this.tentativesFail = 0;
        // Génération d'un numéro de badge unique basé sur le hash
        this.numeroBadge = (utilisateur.toString() + dateDebut.toString()).hashCode();
    }

    @Override
    public void invaliderBadge() {
        this.statut = "INVALIDE";
    }

    @Override
    public boolean estBadgeValide() {
        Date now = new Date();
        return statut.equals("VALIDE") &&
                now.after(dateDebut) &&
                now.before(dateFin);
    }

    public void bloquer() {
        this.statut = "BLOQUE";
    }

    public void debloquer() {
        this.statut = "VALIDE";
        this.tentativesFail = 0;
    }

    public int getNumeroBadge() {
        return numeroBadge;
    }

    public UtilisateurCAN getUtilisateur() {
        return utilisateur;
    }

    public String getStatut() {
        return statut;
    }

    public int getTentativesFail() {
        return tentativesFail;
    }

    public void incrementerTentativesFail() {
        this.tentativesFail++;
    }
}
