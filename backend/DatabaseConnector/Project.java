
import java.sql.*;
import java.util.*;
import java.io.*;

public class Project {
    
    int id;
    String name; 
    String description;
    double costs;
    String location;
    String coach;
    String supervisor;
    int maxNrStudents;
    
    public Project() {

    }

    public static void printAllStudentsInProject(int pid) {
        ArrayList<Student> students = getStudentsInProject(pid);
        for (Student s: students) s.printStudent();
    }

    public static ArrayList<Student> getStudentsInProject(int pid) {
        DatabaseConnection db = DatabaseConnection.getDatabaseConnection();
        ArrayList<Student> project = new ArrayList<>();
        ArrayList<String>  sids    = new ArrayList<>();

        String sqlCommand1 = "SELECT * FROM Student_in_Project WHERE pid = '" + pid + "';";
        ResultSet resultSet1 = db.executeSQLCommand(sqlCommand1);

        try {
            while(resultSet1.next()) {
                String sid = resultSet1.getString("sid");
                sids.add(sid);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String    sqlCommand2 = "SELECT * FROM Students;";
        ResultSet resultSet2  = db.executeSQLCommand(sqlCommand2);

        try{
            while(resultSet2.next()) {
                String sid = resultSet2.getString("id");
                String firstname = resultSet2.getString("first_name");
                String lastname  = resultSet2.getString("last_name" );
                String classname = resultSet2.getString("classname" );
                for (String s: sids) if (s.equals(sid)) project.add(new Student(firstname, lastname, classname, sid));

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return project;
    }

    public Project(int pid, int id , String name, String description, double costs, String location, String coach, String supervisor, int maxNrStudents) {
        id = this.id;
        name = this.name; 
        description = this.description;
        costs = this.costs;
        location = this.location;
        coach = this.coach;
        supervisor = this.supervisor;
        maxNrStudents = this.maxNrStudents;
    }


    public ArrayList<Project_Slot> getProject_Slots() {

        return null;

    }
}

