import java.util.ArrayList;
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
    private String firstname;
    private String lastname;
    private String classname;
    private String sid;

    /**
     * Konstruktor für Objekte der Klasse Student
     */
    public Student(String fn, String ln, String c, String sid){

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
    public String getFirstName() {
        return firstname;

    }

    public String getLastName() {
        return lastname;

    }

    public String getClassName() {
        return classname;

    }
    
    public String getSid() {
        return sid;

    }

    public void printStudent() {
        System.out.println(firstname + ", " + lastname + ", " + classname);

    }
    public ArrayList<Project> getProjects() {
        return null;
    }

    public ArrayList<Project_Slot> getProject_Slots() {
        return null;
    }

}
