import java.sql.*;
import java.util.*;
import java.io.*;

public class Organizer
{
    /**
     * returns a list of all projects
     */
    public static ArrayList<Project> getAllProjects() {  

        DatabaseConnection db = DatabaseConnection.getDatabaseConnection();
        ArrayList<Project> projects = new ArrayList<>();

        String    sqlCommand = "SELECT * FROM Projects";
        ResultSet resultSet  = db.executeSQLCommand(sqlCommand);

        try {
            while(resultSet.next()) {
                int    id            = resultSet.getInt("id"            );
                int    maxNrStudents = resultSet.getInt("maxNrStudents" );
                String name          = resultSet.getString("name"       );
                String description   = resultSet.getString("description");
                String costs         = resultSet.getString("costs"      );
                String location      = resultSet.getString("location"   );
                String coach         = resultSet.getString("coach"      );
                String supervisor    = resultSet.getString("supervisor" );

                projects.add(new Project(id, name, description, costs, location, coach, supervisor, maxNrStudents));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return projects;
    }

    /**
     * returns a list of all students
     */
    public static ArrayList<Student> getAllStudents() {  
        DatabaseConnection db = DatabaseConnection.getDatabaseConnection();
        ArrayList<Student> students = new ArrayList<>();

        String sqlCommand = "SELECT * FROM Students";
        ResultSet resultSet = db.executeSQLCommand(sqlCommand);

        try {
            while(resultSet.next()) {
                String id          = resultSet.getString("id"        );
                String first_name  = resultSet.getString("first_name");
                String last_name   = resultSet.getString("last_name" );
                String password    = resultSet.getString("password"  );
                String classname   = resultSet.getString("classname" );

                students.add(new Student(id, first_name, last_name, password, classname));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }

    /**
     * returns a list of students which are not enrolled at a specified date
     */
    public static ArrayList<Student> getAllStudentsWithoutRegistration(String date) {
        DatabaseConnection db = DatabaseConnection.getDatabaseConnection();
        ArrayList<Student> students = getAllStudents();

        String    sqlCommand = "" +
            "SELECT * " +
            "FROM Student_in_Project,Project_slots " +
            "WHERE Project_slots.pid = Student_in_Project.pid " +
            "AND Project_slots.date = '" + date + "'";
        ResultSet resultSet  = db.executeSQLCommand(sqlCommand);
        try {
            while(resultSet.next()) {
                System.out.println(resultSet.getString("sid"));
                for(Student s: students) 
                    if(s.getId() == resultSet.getString("sid")) 
                        students.remove(s);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return students;
    }

    /**
     * print out all entry lists
     * 
     */

    public static ArrayList<Project> printAllEntryLists() { 

        ArrayList<Project> allProjects = getAllProjects();
        for(Project p: allProjects) p.printEntryList();
        return null; 
    }

}
