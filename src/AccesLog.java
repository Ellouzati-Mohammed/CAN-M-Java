/**
 * @author Ellou
 **/
// AccesLog.java
import java.util.Date;

public class AccesLog {
    private Date dateHeure;
    private Badge badge;
    private ZoneSecurisee zone;
    private String resultat; // "AUTORISE" ou "REFUSE"

    public AccesLog(Badge badge, ZoneSecurisee zone, String resultat) {
        this.dateHeure = new Date();
        this.badge = badge;
        this.zone = zone;
        this.resultat = resultat;
    }

    public Date getDateHeure() {
        return dateHeure;
    }

    public Badge getBadge() {
        return badge;
    }

    public ZoneSecurisee getZone() {
        return zone;
    }

    public String getResultat() {
        return resultat;
    }
}