// UtilisateurCAN.java (classe abstraite)
import java.util.Date;

public abstract class UtilisateurCAN {
    protected int id;
    protected String nom;
    protected String categorie;
    private Badge badge;
    private static int compteurId = 0;

    public UtilisateurCAN(String nom, String categorie) {
        this.id = ++compteurId;
        this.nom = nom;
        this.categorie = categorie;
    }

    public abstract int getNiveauAcces();

    public Badge getBadge() {
        return badge;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }

    @Override
    public String toString() {
        return "UtilisateurCAN [id=" + id + ", nom=" + nom + ", categorie=" + categorie + "]";
    }
}