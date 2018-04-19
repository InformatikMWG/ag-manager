import java.sql.*;
import java.util.*;
import java.io.*;

/**
 * Beschreiben Sie hier die Klasse Student.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Student
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private String sid;
    private String firstname;
    private String lastname;
    private String classname;

    /**
     * Konstruktor für Objekte der Klasse Student
     */
    public Student(String fn, String ln, String c, String sid) {
        firstname = fn;
        lastname = ln;
        classname = c;
        this.sid = sid;
    }

    public static Student getStudent(String sid) {
        String firstname = null;
        String lastname = null;
        String classname = null;
        DatabaseConnection db;
        db = DatabaseConnection.getDatabaseConnection();
        String sqlCommand = "SELECT * FROM Students WHERE id = '" + sid + "';";
        ResultSet resultSet = db.executeSQLCommand(sqlCommand);

        try {
            while(resultSet.next()) {
                firstname = resultSet.getString("first_name");
                lastname  = resultSet.getString("last_name" );
                classname = resultSet.getString("classname" );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return new Student(firstname, lastname, classname, sid);
    }

    public String getSid() {
        return sid;
    }

    public String getFirstName() {
        return firstname;
    }

    public String getLastName() {
        return lastname;
    }

    public String getClassName() {
        return classname;
    }

    public void printStudent() {
        System.out.println(sid + "," + firstname + ", " + lastname + ", " + classname);
    }

    public ArrayList<Project> getProjects() {
        String sqlCommand = "SELECT * FROM Projects WHERE id = (SELECT pid FROM Student_in_Project WHERE sid = '" + sid + "' ) ;";
        DatabaseConnection db = DatabaseConnection.getDatabaseConnection();
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

                Project p = new Project(id, name, description, costs, location, coach, supervisor, Integer.parseInt(maxNrStudents));

                projects.add(p);
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
