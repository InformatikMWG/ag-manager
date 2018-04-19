import java.util.ArrayList;

public class Project
{    
    int id;
    String name; 
    String description;
    double costs;
    String location;
    String coach;
    String supervisor;
    int maxNrStudents;

    public Project(int pid, int id , String name, String description, double costs, String location, String coach, String supervisor, int maxNrStudents)  {
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

