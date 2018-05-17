public class Project_Slot
{
    private int pid;
    private String date;

    private String time_start;
    private String time_end;

    public Project_Slot(int pid, String date, String time_start, String time_end){
        this.pid = pid;
        this.date = date;

        this.time_start = time_start;
        this.time_end = time_end;                  
    } 

    /**
     * add a given slot to the database
     */
    public void addProjectSlotToDatabase() {
        DatabaseConnection db = DatabaseConnection.getDatabaseConnection();
        db.executeSQLCommand("INSERT INTO Project_slots (pid, date, time_start, time_end)  VALUES ("+ pid + ",'" + date + "','" + time_start + "','" + time_end + "')" );
    }

    /**
     * remove a given slot from the database
     */
    public void removeProjectSlotFromDatabase() {

    }

    public int getPid(){return pid;}

    public void setPid(int newpid){pid = newpid;}

    public String getDate(){return date;}

    public void setDate(String newDate){date = newDate;}

    public String getTime_start(){return time_start;}

    public void setTime_start(String newtime_start){time_start = newtime_start;}

    public String getTime_end(){return time_end;}

    public void setTime_end(String newtime_end){time_start = newtime_end;}
}

