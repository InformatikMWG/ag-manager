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

    public ProjectPanel(Project project) {

        FlowLayout flowLayout = new FlowLayout();
        setLayout(flowLayout);

        pidField = new JTextField();
        pidField.setText(project.getId()+"");
        pidField.setEditable(false);
        pidField.setPreferredSize(new Dimension(20, 24));

        nameField = new JTextField();
        nameField.setText(project.getName());
        nameField.setEditable(true);
        nameField.setPreferredSize(new Dimension(250, 24));

        descriptionField = new JTextField();
        descriptionField.setText(project.getDescription());        
        descriptionField.setEditable(true);
        descriptionField.setPreferredSize(new Dimension(400, 24));

        costsField = new JTextField();
        costsField.setText(project.getCosts());        
        costsField.setEditable(true);
        costsField.setPreferredSize(new Dimension(50, 24));

        locationField = new JTextField();
        locationField.setText(project.getLocation());        
        locationField.setEditable(true);
        locationField.setPreferredSize(new Dimension(80, 24));

        coachField = new JTextField();
        coachField.setText(project.getCoach());        
        coachField.setEditable(true);
        coachField.setPreferredSize(new Dimension(80, 24));

        supervisorField = new JTextField();
        supervisorField.setText(project.getSupervisor());        
        supervisorField.setEditable(true);
        supervisorField.setPreferredSize(new Dimension(80, 24));
        
        NumberFormat longFormat = NumberFormat.getIntegerInstance();
        NumberFormatter numberFormatter = new NumberFormatter(longFormat);
        numberFormatter.setValueClass(Long.class); //optional, ensures you will always get a long value
        numberFormatter.setAllowsInvalid(false); //this is the key!!
        numberFormatter.setMinimum(0l); //Optional        

        maxNrStudentsField = new JFormattedTextField(numberFormatter);

        maxNrStudentsField.setText(project.getMaxNrStudents() + "");        
        maxNrStudentsField.setEditable(true);
        maxNrStudentsField.setPreferredSize(new Dimension(50, 24));

        add(pidField);
        add(nameField);
        add(descriptionField);
        add(costsField);
        add(locationField);
        add(coachField);
        add(supervisorField);
        add(maxNrStudentsField);

    }

    public ProjectPanel(int pid)
    {
        this(new Project(pid));
    }

}
