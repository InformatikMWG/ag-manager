import java.sql.*;
import java.util.*;
import java.io.*;

/**
 * Beschreiben Sie hier die Klasse Student.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Student implements Comparable<Student>
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private String id;
    private String first_name;
    private String last_name;
    private String password;
    private String classname;

    /**
     * Konstruktor für Objekte der Klasse Student
     */
    public Student( String id, String first_name, String last_name, String password, String classname) {
        this.id        = id;
        this.first_name = first_name;
        this.last_name  = last_name;
        this.password  = password;
        this.classname = classname;
    }

    public static Student getStudent(String id) {
        String first_name = null;
        String last_name  = null;
        String password   = null;
        String classname  = null;

        DatabaseConnection db;
        db = DatabaseConnection.getDatabaseConnection();
        String sqlCommand = "SELECT * FROM Students WHERE id = '" + id + "';";
        ResultSet resultSet = db.executeSQLCommand(sqlCommand);

        try {
            while(resultSet.next()) {
                first_name = resultSet.getString("first_name");
                last_name  = resultSet.getString("last_name" );
                password   = resultSet.getString("password"  );
                classname  = resultSet.getString("classname" );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Student(id, first_name, last_name, password, classname);
    }

    public String getSid() {               
        return id;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public String getClassName() {
        return classname;
    }   

    public void setClassName(String classname) {
        this.classname = classname;
    }

    public void printStudent() {
        System.out.println(id + "," + first_name + ", " + last_name + ", " + classname);
    }

    public String getRelevantInformation() {
        return (classname + ", " + last_name + ", " + first_name);
    }

    public ArrayList<Project> getAvailableProjects() {
        String sqlCommand = "SELECT * FROM Projects WHERE id =(SELECT pid FROM Filters WHERE (gid = (SELECT gid FROM Student_in_Group WHERE sid="+ id +") AND (NOT isBlacklist OR isBlacklist is NULL)) AND (NOT gid = (SELECT gid FROM Student_in_Group WHERE sid="+id+") AND isBlacklist));";
        DatabaseConnection db = DatabaseConnection.getDatabaseConnection();
        ResultSet resultSet = db.executeSQLCommand(sqlCommand);
        ArrayList<Project> projects = new ArrayList<Project>();

        try {
            while(resultSet.next()) {
                int    id            = resultSet.getInt("id"              );
                String name          = resultSet.getString("name"         );
                String description   = resultSet.getString("description"  );
                String costs         = resultSet.getString("costs"        );
                String location      = resultSet.getString("location"     );
                String coach         = resultSet.getString("coach"        );
                String supervisor    = resultSet.getString("supervisor"   );
                String maxNrStudents = resultSet.getString("maxNrStudents");

                Project p = new Project(id, name, description, costs, location, coach, supervisor, Integer.parseInt(maxNrStudents));

                projects.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return projects;
    }

    public ArrayList<Project> getProjects() {
        String sqlCommand = "SELECT * FROM Projects WHERE id = (SELECT pid FROM Student_in_Project WHERE sid = '" + id + "' ) ;";
        DatabaseConnection db = DatabaseConnection.getDatabaseConnection();
        ResultSet resultSet = db.executeSQLCommand(sqlCommand);
        ArrayList<Project> projects = new ArrayList<Project>();

        try {
            while(resultSet.next()) {
                int    id            = resultSet.getInt("id"              );
                String name          = resultSet.getString("name"         );
                String description   = resultSet.getString("description"  );
                String costs         = resultSet.getString("costs"        );
                String location      = resultSet.getString("location"     );
                String coach         = resultSet.getString("coach"        );
                String supervisor    = resultSet.getString("supervisor"   );
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

    /**
     * compares two students
     * order criteria: classname, last name, firstname
     */
    @Override
    public int compareTo(Student s) {
        int temp = classname.compareToIgnoreCase(s.getClassName());
        if(temp != 0)return temp;
        temp = last_name.compareToIgnoreCase(s.getLastName());
        if(temp != 0)return temp;
        temp = first_name.compareToIgnoreCase(s.getFirstName());
        return temp;
    }

    public void updateStudentInDatabase() {
        //       DatabaseConnection db = new DatabaseConnection();
        //        db.executeSQLCommand("UPDATE Student SET  name = "+ name + ", description = " + description + ", costs = " + costs ", location = " + location + ", coach = " + coach + ",  supervisor = " + supervisor + ", maxNrStudents = "+ maxNrStudents + " Where ID = " + id)" 

    }

}
