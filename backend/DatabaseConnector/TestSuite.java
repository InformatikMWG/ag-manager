import java.sql.*;
import java.util.*;
import java.io.*;

public class TestSuite
{
    private static DatabaseConnection db = DatabaseConnection.getDatabaseConnection();

    public static void resetDatabase() {
        db.executeSQLFile("resetDatabase.sql");         
    } 

    public static void insertMockupDataStudents() {
        String s = new String();

        try
        {
            FileReader fr = new FileReader(new File("mockupData/students.csv"));
            BufferedReader br = new BufferedReader(fr);

            String insertInto = "INSERT INTO Students (id, first_name, last_name, password) VALUES (?, ?, ?, ?)";
            Connection connection = db.getOpenConnection();
            PreparedStatement statement = connection.prepareStatement(insertInto);
            while((s = br.readLine()) != null)
            {
                s.trim();
                String[] values = s.split(",");
                statement.setInt(1, Integer.parseInt(values[0]));
                statement.setString(2, values[1]);
                statement.setString(3, values[2]);
                statement.setString(4, values[3]);
                statement.addBatch();
            }

            statement.executeBatch();
            db.closeConnection();
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }  
    }

    public static void insertMockupDataProjects()
    {
        String s = new String();
        try
        {
            FileReader fr = new FileReader(new File("mockupData/Projects.csv"));
            BufferedReader br = new BufferedReader(fr);

            String insertInto = "INSERT INTO Projects (name, description, costs, location, coach, supervisor, maxNrStudents) VALUES (?,?,?,?,?,?,?)";
            Connection connection = db.getOpenConnection();
            PreparedStatement statement = connection.prepareStatement(insertInto);
            while ((s = br.readLine()) != null)
            {
                s.trim();
                String[] values = s.split(",");
                statement.setString(1, values[0]);
                statement.setString(2, values[1]);
                statement.setString(3, values[2]);
                statement.setString(4, values[3]);
                statement.setString(5, values[4]);
                statement.setString(6, values[5]);
                statement.setString(7, values[6]);
                statement.addBatch();
            }
            statement.executeBatch();
            db.closeConnection();
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }

    public static void showAllStudents() {
        String sqlCommand = "SELECT * FROM Students;";
        ResultSet resultSet = db.executeSQLCommand(sqlCommand);
        try {
            while(resultSet.next()) {
                String id = resultSet.getString("id");
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                String password = resultSet.getString("password");
                System.out.println(id + "," + first_name + "," + last_name + "," + password);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showAllProjects() {
        String sqlCommand = "SELECT * FROM Projects;";
        ResultSet resultSet = db.executeSQLCommand(sqlCommand);
        try {
            while(resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String costs = resultSet.getString("costs");
                String location = resultSet.getString("location");
                String coach = resultSet.getString("coach");
                String supervisor = resultSet.getString("supervisor");
                String maxNrStudents = resultSet.getString("maxNrStudents");
                System.out.println(id + "," + name + "," + description + "," + costs + "," + location + "," + coach + "," + supervisor + "," + maxNrStudents);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
