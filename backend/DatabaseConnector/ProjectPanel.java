import javax.swing.*;
import java.awt.*;
import java.text.*;
import javax.swing.text.*;

public class ProjectPanel extends JPanel
{

    private Project project;
    private JTextField pidField;
    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField costsField;
    private JTextField locationField;
    private JTextField coachField;
    private JTextField supervisorField;
    private JFormattedTextField maxNrStudentsField;

    public ProjectPanel(int pid)
    {
        project = new Project(pid);

        FlowLayout flowLayout = new FlowLayout();
        setLayout(flowLayout);

        pidField = new JTextField();
        pidField.setText(project.getId()+"");
        pidField.setEditable(false);

        nameField = new JTextField();
        nameField.setText(project.getName());
        nameField.setEditable(true);

        descriptionField = new JTextField();
        descriptionField.setText(project.getDescription());        
        descriptionField.setEditable(true);

        costsField = new JTextField();
        costsField.setText(project.getCosts());        
        costsField.setEditable(true);

        locationField = new JTextField();
        locationField.setText(project.getLocation());        
        locationField.setEditable(true);

        coachField = new JTextField();
        coachField.setText(project.getCoach());        
        coachField.setEditable(true);

        supervisorField = new JTextField();
        supervisorField.setText(project.getSupervisor());        
        supervisorField.setEditable(true);

        NumberFormat longFormat = NumberFormat.getIntegerInstance();
        NumberFormatter numberFormatter = new NumberFormatter(longFormat);
        numberFormatter.setValueClass(Long.class); //optional, ensures you will always get a long value
        numberFormatter.setAllowsInvalid(false); //this is the key!!
        numberFormatter.setMinimum(0l); //Optional

        maxNrStudentsField = new JFormattedTextField(numberFormatter);

        maxNrStudentsField.setText(project.getMaxNrStudents() + "");        
        maxNrStudentsField.setEditable(true);

        add(pidField);
        add(nameField);
        add(descriptionField);
        add(costsField);
        add(locationField);
        add(coachField);
        add(supervisorField);
        add(maxNrStudentsField);
        
    }
}
