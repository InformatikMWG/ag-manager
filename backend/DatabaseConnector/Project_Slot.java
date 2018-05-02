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
    public void addProjectSlotToDatabase(Project_Slot ps) {

    }

}
