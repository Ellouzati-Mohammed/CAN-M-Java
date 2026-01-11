import java.util.Calendar;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        SecurityService service = new SecurityService();

        // Création des zones
        ZoneSecurisee entree = new ZoneSecurisee("Entrée", 1);
        ZoneSecurisee backstage = new ZoneSecurisee("Backstage", 2);
        ZoneSecurisee zoneVIP = new ZoneSecurisee("Zone VIP", 3);
        service.ajouterZone(entree);
        service.ajouterZone(backstage);
        service.ajouterZone(zoneVIP);

        // Création des utilisateurs
        Spectateur spectateur = new Spectateur("Alice");
        Staff staff = new Staff("Bob", "Technique");
        VIP vip = new VIP("Charlie", "Sponsor");
        service.ajouterUtilisateur(spectateur);
        service.ajouterUtilisateur(staff);
        service.ajouterUtilisateur(vip);

        // Dates de validité des badges (valide maintenant)
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, -1);
        Date dateDebut = cal.getTime();
        cal.add(Calendar.DAY_OF_MONTH, 7);
        Date dateFin = cal.getTime();

        // Génération des badges
        Badge badgeSpectateur = service.genererBadge(spectateur.getId(), dateDebut, dateFin);
        Badge badgeStaff = service.genererBadge(staff.getId(), dateDebut, dateFin);
        Badge badgeVIP = service.genererBadge(vip.getId(), dateDebut, dateFin);

        System.out.println("--- Scénario de test : vérification des accès ---");

        // Cas autorisé
        try {
            System.out.println("Spectateur -> Entrée (attendu: autorisé): " +
                    service.verifierAcces(badgeSpectateur.getNumeroBadge(), entree.getIdZone()));
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }

        // Cas refusé (spectateur -> backstage)
        for (int i = 1; i <= 4; i++) {
            try {
                System.out.println("Tentative " + i + ": Spectateur -> Backstage: " +
                        service.verifierAcces(badgeSpectateur.getNumeroBadge(), backstage.getIdZone()));
            } catch (Exception e) {
                System.out.println("Tentative " + i + " exception: " + e.getMessage());
            }
        }

        // Cas autorisé (staff -> backstage)
        try {
            System.out.println("Staff -> Backstage (attendu: autorisé): " +
                    service.verifierAcces(badgeStaff.getNumeroBadge(), backstage.getIdZone()));
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }

        // Cas VIP -> Zone VIP
        try {
            System.out.println("VIP -> Zone VIP (attendu: autorisé): " +
                    service.verifierAcces(badgeVIP.getNumeroBadge(), zoneVIP.getIdZone()));
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }

        // Afficher historique
        System.out.println();
        service.afficherHistorique();

        // Rapports
        System.out.printf("Taux de refus: %.2f%%\n", service.genererRapportTauxRefus());
        System.out.println("Badges bloqués: " + service.genererRapportBadgesBloques().size());
    }
}
