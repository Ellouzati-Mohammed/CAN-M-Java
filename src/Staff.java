/**
 * @author Ellou
 **/
public class Staff extends UtilisateurCAN {
    private static final int NIVEAU_ACCES = 2;
    private String departement;

    public Staff(String nom, String departement) {
        super(nom, "Staff");
        this.departement = departement;
    }

    @Override
    public int getNiveauAcces() {
        return NIVEAU_ACCES;
    }

    public String getDepartement() {
        return departement;
    }
}
