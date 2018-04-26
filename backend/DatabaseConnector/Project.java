
import java.sql.*;
import java.util.*;
import java.io.*;

public class Project {
    private int id;
    private String name;
    private String description;
    private String costs;
    private String location;
    private String coach;
    private String supervisor;
    private int maxNrStudents;

    public Project(int id, String name, String description, String costs, String location, String coach, String supervisor, int maxNrStudents) {
        this.id = id;
        this.name = name; 
        this.description = description;
        this.costs = costs;
        this.location = location;
        this.coach = coach;
        this.supervisor = supervisor;
        this.maxNrStudents = maxNrStudents;
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

    public ArrayList<Project_Slot> getProject_Slots() {
          DatabaseConnection db = DatabaseConnection.getDatabaseConnection();
        ArrayList<Project_Slot> project_slots = new ArrayList<>();
       

        String sqlCommand1 = "SELECT * FROM Project_slots WHERE pid = '" + id + "';";
        ResultSet resultSet1 = db.executeSQLCommand(sqlCommand1);

        try {
            while(resultSet1.next()) {
                String pid = resultSet1.getString("pid");
                String date = resultSet1.getString("date");
                String time_start  = resultSet1.getString("time_start " );
                String time_end = resultSet1.getString("time_end" );
                Project_Slot ps = new Project_Slot(Integer.parseInt(pid),date,time_start,time_end);
                project_slots.add(ps);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

       
        return project_slots;
    }

    /**
     * print nicely formattet entry list, e.g.
     * 
     * ################################################################################
     * Project: Testproject  
     * Date:    08.07.2018
     * Room:    E251
     * Teacher: Mr. X
     * ################################################################################
     * Participants
     * 
     * nr, classname, name, first name
     * 
     * 
     * Participants ordered by classname, last name and first name 
     */
    public void printEntryList() {
        String delimiterSymbol = "################################################################################";
        System.out.println(delimiterSymbol);
    }
    
    
}

