/**
 * @author Ellou
 **/
public interface Securisable {
    void autoriser(ZoneSecurisee zone);
    void refuser(ZoneSecurisee zone);
    boolean verifierAccess(Badge badge, ZoneSecurisee zone);
}
