import java.sql.*;
import java.util.*;
import java.io.*;

public class Test
{
    private DatabaseConnection db;

    public Test()
    {
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

    public void showAllStudents() {
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

    public void resetDatabase() {
        db.executeSQLFile("resetDatabase.sql");         
    }

    public void insertMockupDataSingle() {
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
                System.out.println(statement.toString());
                statement.execute();
            }

            db.closeConnection();
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }  
    }

    public void insertMockupData() {
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

}
