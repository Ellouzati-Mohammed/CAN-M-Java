/**
 * @author Ellou
 **/
// ControleAccess.java
import java.util.Date;

public class ControleAccess {

    public boolean verifierAcces(Badge badge, ZoneSecurisee zone) {
        if (badge == null) {
            throw new BadgeInvalideException("Badge non fourni");
        }

        if (!badge.estBadgeValide()) {
            return false;
        }

        UtilisateurCAN utilisateur = badge.getUtilisateur();
        if (utilisateur == null) {
            return false;
        }

        return utilisateur.getNiveauAcces() >= zone.getNiveauRequis();
    }

    public void autoriser(ZoneSecurisee zone) {
        // Méthode pour autoriser l'accès à une zone (pourrait déclencher l'ouverture d'une porte)
        System.out.println("Accès autorisé à la zone: " + zone.getNomZone());
    }

    public void refuser(ZoneSecurisee zone) {
        // Méthode pour refuser l'accès à une zone
        System.out.println("Accès refusé à la zone: " + zone.getNomZone());
    }

    public AccesLog enregistrerTentative(Badge badge, ZoneSecurisee zone, boolean accesAutorise) {
        String resultat = accesAutorise ? "AUTORISE" : "REFUSE";
        return new AccesLog(badge, zone, resultat);
    }
}