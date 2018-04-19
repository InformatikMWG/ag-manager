import java.sql.*;
import java.util.*;
import java.io.*;

public class DatabaseConnection
{
    private static DatabaseConnection dbconnection = new DatabaseConnection();
    private String serveradress;
    private String username;
    private String password;
    private String database;
    private Connection connection;

    private String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    private String DB_URL;

    public static DatabaseConnection getDatabaseConnection() {
        return dbconnection;
    }

    private DatabaseConnection()
    {
    }   

    /**
     * connects this program to a database
     */
    public Connection getOpenConnection() {

        try {
            connection = DriverManager.getConnection(DB_URL, username, password);
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * closes the connection to the database
     */
    public void closeConnection() {
        //closes the connection to the database
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * the user configures his settings with this
     */
    public void configureSettings(String serveradress, String username, String password, String database) {

        this.serveradress = serveradress;
        this.username = username;
        this.password = password;
        this.database = database;
        DB_URL = "jdbc:mariadb://" + serveradress + "/" + database;

    }
    /**
     * executes a file with commands for an SQL file
     */
    public void executeSQLFile(String path) { 
        String s = new String();
        StringBuffer sb = new StringBuffer();

        try
        {
            FileReader fr = new FileReader(new File(path));
            BufferedReader br = new BufferedReader(fr);

            while((s = br.readLine()) != null)
            {
                sb.append(s);
            }
            br.close();

            String[] instructions = sb.toString().split(";");

            connection = DriverManager.getConnection(DB_URL, username, password);
            Statement statement = connection.createStatement();

            for(int i = 0; i < instructions.length; i++)
            {
                String instruction = instructions[i];
                instruction.trim();
                // leere Anweisungen ueberspringen               
                if(!instruction.equals(""))
                {
                    statement.execute(instruction);
                }
            }

            connection.close();
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
    /**
     * executes a single SQL command
     */
    public ResultSet executeSQLCommand(String sqlCommand) { 

        try {
            connection = DriverManager.getConnection(DB_URL, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlCommand);            
            connection.close();
            return resultSet;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
