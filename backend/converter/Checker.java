
/**
 * Beschreiben Sie hier die Klasse Checker.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Checker
{
    String speicher = new String();
    public Checker(){}
    
    public boolean add(String a){
        if(speicher.contains(a)){
            return true;
        }
        speicher+=",";
        speicher+=a;
        return false;
    }
}
