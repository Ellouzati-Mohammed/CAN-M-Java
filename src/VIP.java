/**
 * @author Ellou
 **/
public class VIP extends UtilisateurCAN {
    private static final int NIVEAU_ACCES = 3;
    private String organisation;

    public VIP(String nom, String organisation) {
        super(nom, "VIP");
        this.organisation = organisation;
    }

    @Override
    public int getNiveauAcces() {
        return NIVEAU_ACCES;
    }

    public String getOrganisation() {
        return organisation;
    }
}
