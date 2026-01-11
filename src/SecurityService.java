/**
 * @author Ellou
 **/
import java.util.*;
import java.util.stream.Collectors;

public class SecurityService {
    private List<UtilisateurCAN> utilisateurs;
    private List<Badge> badges;
    private List<ZoneSecurisee> zones;
    private List<AccesLog> logs;
    private Map<Integer, Badge> badgeMap;
    private ControleAccess controleAcces;

    public SecurityService() {
        this.utilisateurs = new ArrayList<>();
        this.badges = new ArrayList<>();
        this.zones = new ArrayList<>();
        this.logs = new ArrayList<>();
        this.badgeMap = new HashMap<>();
        this.controleAcces = new ControleAccess();
    }

    public void ajouterUtilisateur(UtilisateurCAN utilisateur) {
        utilisateurs.add(utilisateur);
    }

    public void ajouterZone(ZoneSecurisee zone) {
        zones.add(zone);
    }

    public Badge genererBadge(int utilisateurId, Date dateDebut, Date dateFin) {
        UtilisateurCAN utilisateur = utilisateurs.stream()
                .filter(u -> u.getId() == utilisateurId)
                .findFirst()
                .orElseThrow(() -> new DonneeInvalideException("Utilisateur non trouvé avec ID: " + utilisateurId));

        Badge badge = new Badge(utilisateur, dateDebut, dateFin);
        badges.add(badge);
        badgeMap.put(badge.getNumeroBadge(), badge);
        utilisateur.setBadge(badge);

        return badge;
    }

    public boolean verifierAcces(int numeroBadge, int idZone) {
        Badge badge = badgeMap.get(numeroBadge);
        if (badge == null) {
            throw new BadgeInvalideException("Badge non trouvé: " + numeroBadge);
        }

        ZoneSecurisee zone = zones.stream()
                .filter(z -> z.getIdZone() == idZone)
                .findFirst()
                .orElseThrow(() -> new ZoneInterditeException("Zone non trouvée: " + idZone));

        boolean accesAutorise = false;
        try {
            accesAutorise = controleAcces.verifierAccess(badge, zone);

            if (accesAutorise) {
                controleAcces.autoriser(zone);
                // badge.incrementerTentativesFail(); // Réinitialiser les tentatives échouées
            } else {
                controleAcces.refuser(zone);
                badge.incrementerTentativesFail();

                // Bloquer le badge après 3 tentatives échouées
                if (badge.getTentativesFail() >= 3) {
                    badge.bloquer();
                    throw new AccesRefuseException("Badge bloqué après 3 tentatives échouées");
                }

                throw new AccesRefuseException("Accès refusé à la zone: " + zone.getNomZone());
            }
        } finally {
            // Enregistrer la tentative dans les logs
            AccesLog log = controleAcces.enregistrerTentative(badge, zone, accesAutorise);
            logs.add(log);
        }

        return accesAutorise;
    }

    public void bloquerBadge(int numeroBadge) {
        Badge badge = badgeMap.get(numeroBadge);
        if (badge == null) {
            throw new BadgeInvalideException("Badge non trouvé: " + numeroBadge);
        }
        badge.bloquer();
    }

    public void debloquerBadge(int numeroBadge) {
        Badge badge = badgeMap.get(numeroBadge);
        if (badge == null) {
            throw new BadgeInvalideException("Badge non trouvé: " + numeroBadge);
        }
        badge.debloquer();
    }

    public void afficherHistorique() {
        System.out.println("=== Historique des Accès ===");
        for (AccesLog log : logs) {
            System.out.printf("[%s] Badge %d - Zone: %s - Résultat: %s%n",
                    log.getDateHeure(),
                    log.getBadge().getNumeroBadge(),
                    log.getZone().getNomZone(),
                    log.getResultat());
        }
    }

    public double genererRapportTauxRefus() {
        if (logs.isEmpty()) {
            return 0.0;
        }

        long refusCount = logs.stream()
                .filter(log -> log.getResultat().equals("REFUSE"))
                .count();

        return (double) refusCount / logs.size() * 100;
    }

    public List<ZoneSecurisee> genererRapportZonesSensibles() {
        // Retourne les zones avec le plus grand nombre de refus
        Map<ZoneSecurisee, Long> refusParZone = logs.stream()
                .filter(log -> log.getResultat().equals("REFUSE"))
                .collect(Collectors.groupingBy(AccesLog::getZone, Collectors.counting()));

        return refusParZone.entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public List<Badge> genererRapportBadgesBloques() {
        return badges.stream()
                .filter(badge -> badge.getStatut().equals("BLOQUE"))
                .collect(Collectors.toList());
    }

    public Badge rechercherParNumeroBadge(int numeroBadge) {
        Badge badge = badgeMap.get(numeroBadge);
        if (badge == null) {
            throw new BadgeInvalideException("Badge non trouvé: " + numeroBadge);
        }
        return badge;
    }

    public List<AccesLog> trierLogsParDate() {
        return logs.stream()
                .sorted((log1, log2) -> log1.getDateHeure().compareTo(log2.getDateHeure()))
                .collect(Collectors.toList());
    }

    // Getters pour les listes (utiles pour les tests)
    public List<UtilisateurCAN> getUtilisateurs() { return utilisateurs; }
    public List<Badge> getBadges() { return badges; }
    public List<ZoneSecurisee> getZones() { return zones; }
    public List<AccesLog> getLogs() { return logs; }
    public ControleAccess getControleAcces() { return controleAcces; }
}
