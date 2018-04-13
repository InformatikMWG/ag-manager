import java.sql.*;
import java.util.*;
import java.io.*;

public class TestProjekte
{
    private DatabaseConnection db;
    public TestProjekte()
    {
        db = DatabaseConnection.getDatabaseConnection();

        try {
            // Die Datei test.properties enthaelt die sensiblen Zugangsdaten
            File file = new File("test.properties");
            FileInputStream fileInput = new FileInputStream(file);
            Properties properties = new Properties();
            properties.load(fileInput);
            fileInput.close();

            String ip = properties.getProperty("IP");           String user = properties.getProperty("USER");   
            String password = properties.getProperty("PASSWORD");   
            String database = properties.getProperty("DATABASE");   

            db.configureSettings(ip,user, password, database);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void resetDatabase() {
        db.executeSQLFile("resetDatabase.sql");         
    }

    public void insertMockupData()
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

    public void showAllProjects() {
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
