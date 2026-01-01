/**
 * @author Ellou
 **/

public class Spectateur extends UtilisateurCAN {
    private static final int NIVEAU_ACCES = 1;

    public Spectateur(String nom) {
        super(nom, "Spectateur");
    }

    @Override
    public int getNiveauAcces() {
        return NIVEAU_ACCES;
    }
}