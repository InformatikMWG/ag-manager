import javax.swing.*;
import java.awt.*;

public class ProjectPanel extends JPanel
{

    private Project project;
    private JTextField pidField;
    private JTextField nameField;

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
        
        add(pidField);
        add(nameField);
    }
}
