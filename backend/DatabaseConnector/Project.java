
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

    public Project(int pid) {
        this.id = pid;
        DatabaseConnection db = DatabaseConnection.getDatabaseConnection();

        String sqlCommand1 = "SELECT * FROM Projects WHERE id = " + pid + ";";
//        String sqlCommand1 = "SELECT * FROM Projects;";
        ResultSet resultSet1 = db.executeSQLCommand(sqlCommand1);

        try {
            while(resultSet1.next()) {
                this.id = pid;
                name = resultSet1.getString("name");
                description = resultSet1.getString("description");
                costs = resultSet1.getString("costs");
                location = resultSet1.getString("location");
                coach = resultSet1.getString("coach");
                supervisor = resultSet1.getString("supervisor");
                maxNrStudents = resultSet1.getInt("maxNrStudents");
              
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        

    }

    public static void printAllStudentsInProject(int pid) {
        ArrayList<Student> students = getStudentsInProject(pid);
        for (Student s: students) s.printStudent();
    }

    public static ArrayList<Student> getStudentsInProject(int pid) {
        DatabaseConnection db = DatabaseConnection.getDatabaseConnection();
        ArrayList<Student> project = new ArrayList<>();
        ArrayList<String>  sids    = new ArrayList<>();

        String    sqlCommand = "SELECT * FROM Student_in_Project WHERE pid = '" + pid + "';";
        ResultSet resultSet = db.executeSQLCommand(sqlCommand);

        try {
            while(resultSet.next()) {
                String sid = resultSet.getString("sid");
                sids.add(sid);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        sqlCommand = "SELECT * FROM Students;";
        resultSet  = db.executeSQLCommand(sqlCommand);

        try{
            while(resultSet.next()) {
                String sid        = resultSet.getString("id"        );
                String first_name = resultSet.getString("first_name");
                String last_name  = resultSet.getString("last_name" );
                String password   = resultSet.getString("password"  );
                String classname  = resultSet.getString("classname" );
                for (String s: sids) if (s.equals(sid)) project.add(new Student(sid, first_name, last_name, password, classname));

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
