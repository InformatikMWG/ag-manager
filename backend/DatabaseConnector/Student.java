import java.util.ArrayList;
/**
 * Beschreiben Sie hier die Klasse Student.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Student
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private String firstname;
    private String lastname;
    private String klasse;

    public static Student getStudent(int sid)  {
        return null;
    }
    
    /**
     * Konstruktor für Objekte der Klasse Student
     */
    public Student(String fn, String ln, String k){
        
        firstname = fn;
        lastname = ln;
        klasse = k;
        
    }
    
    public String getFirstName() {
        return firstname;
        
    }
    
    public String getLastName() {
        return lastname;
        
    }
    
    public String getKlasse() {
        return klasse;
        
    }
    
    public void printStudent() {
        System.out.println(firstname + ", " + lastname + ", " + klasse);
        
        
    }
    
    public ArrayList<Project> getProjects() {
        return null;
    }
    
    public ArrayList<Project_Slot> getProject_Slots() {
        return null;
    }

}
