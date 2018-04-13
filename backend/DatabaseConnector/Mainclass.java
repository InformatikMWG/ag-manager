
import java.sql.*;
import java.util.*;
import java.io.*;

public class Mainclass
{
    private DatabaseConnection db;
    public Mainclass(){
        db = DatabaseConnection.getDatabaseConnection();

        try {
            // Die Datei test.properties enthaelt die sensiblen Zugangsdaten
            File file = new File("test.properties");
            FileInputStream fileInput = new FileInputStream(file);
            Properties properties = new Properties();
            properties.load(fileInput);
            fileInput.close();

            String ip = properties.getProperty("IP");
            String user = properties.getProperty("USER");   
            String password = properties.getProperty("PASSWORD");   
            String database = properties.getProperty("DATABASE");   

            db.configureSettings(ip,user, password, database);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printProjectList(String pid){

    }
    //pid is project id
    //sid is student id
    public void showAllStudentsInProject(int pid) {
        String sqlCommand = "SELECT * FROM Projects WHERE id = '" + pid + "';";
        ResultSet resultSet = db.executeSQLCommand(sqlCommand);

        Student[] project;
        int length = 0;
        try {
            while(resultSet.next()) {

                length = resultSet.getInt("maxNrStudents");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        project = new Student[length];

        String sqlCommand1 = "SELECT * FROM Student_in_Project WHERE pid = '" + pid + "';";
        ResultSet resultSet1 = db.executeSQLCommand(sqlCommand1);
        ArrayList<String> sids = new ArrayList<>();

        try {
            while(resultSet1.next()) {
                String sid = resultSet1.getString("sid");
                sids.add(sid);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // for (String s: sids) {
        //  System.out.println(s);
        //}

        String sqlCommand2 = "SELECT * FROM Students;";
        ResultSet resultSet2 = db.executeSQLCommand(sqlCommand2);
        try{
            while(resultSet2.next()) {
                String sid = resultSet2.getString("id");
                //                System.out.println(sid);
                String first_name = resultSet2.getString("first_name");
                String last_name = resultSet2.getString("last_name");
                String classname = resultSet2.getString("classname");
                for (String s: sids) {
                    int i = 0;
                    if (s.equals(sid)){

                        project[i] = new Student(first_name, last_name, classname );
                        project[i].printStudent();
                        i++;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

}
