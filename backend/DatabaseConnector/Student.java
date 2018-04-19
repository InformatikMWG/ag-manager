import java.util.ArrayList;
import java.sql.*;
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
    private String sid;
    private DatabaseConnection db;
    public static Student getStudent(int sid)  {
        return null;
    }

    /**
     * Konstruktor für Objekte der Klasse Student
     */
    public Student(String fn, String ln, String k){
        db = DatabaseConnection.getDatabaseConnection();
       
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
        String sqlCommand = "SELECT * FROM Projects WHERE id = (SELECT pid FROM Student_in_Project WHERE sid = " + sid + " ) ;";
        ResultSet resultSet = db.executeSQLCommand(sqlCommand);
        ArrayList<Project> projects = new ArrayList<Project>();

        try {
            while(resultSet.next()) {
                int id = Integer.parseInt(resultSet.getString("id"));
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String costs = resultSet.getString("costs");
                String location = resultSet.getString("location");
                String coach = resultSet.getString("coach");
                String supervisor = resultSet.getString("supervisor");
                String maxNrStudents = resultSet.getString("maxNrStudents");

                projects.add( new Project(id, name, description, costs, location, coach, supervisor, maxNrStudents));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return projects;
    }

    public ArrayList<Project_Slot> getProject_Slots() {
        return null;
    }

}
