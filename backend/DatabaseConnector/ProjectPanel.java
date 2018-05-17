import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import javax.swing.text.*;
import javax.imageio.*;
import java.util.ArrayList;

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
    private ArrayList<JTextField> fields;

    private JButton editButton;
    private JButton cancelButton;
    private JButton saveButton;

    public ProjectPanel(Project project) {
        this.project = project;

        FlowLayout flowLayout = new FlowLayout();
        setLayout(flowLayout);

        setupFields();
        setFieldText();
        setupButtons();

        add(pidField);
        add(nameField);
        add(descriptionField);
        add(costsField);
        add(locationField);
        add(coachField);
        add(supervisorField);
        add(maxNrStudentsField);
        add(editButton);
        add(cancelButton);
        add(saveButton);
    }

    public ProjectPanel(int pid)
    {
        this(new Project(pid));
    }

    private void setupButtons() {
        editButton = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource("resources/edit.png"));
            img = img.getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            editButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        editButton.addActionListener(new EditButtonActionListener());

        cancelButton = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource("resources/cancel.png"));
            img = img.getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            cancelButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        cancelButton.addActionListener(new CancelButtonActionListener());
        cancelButton.setEnabled(false);

        saveButton = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource("resources/save.png"));
            img = img.getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            saveButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        saveButton.addActionListener(new SaveButtonActionListener());
        saveButton.setEnabled(false);
    }

    private void setFieldText() {
        pidField.setText(project.getId()+"");
        nameField.setText(project.getName());
        descriptionField.setText(project.getDescription()); 
        costsField.setText(project.getCosts());
        locationField.setText(project.getLocation());
        coachField.setText(project.getCoach()); 
        supervisorField.setText(project.getSupervisor()); 
        maxNrStudentsField.setText(project.getMaxNrStudents() + "");   
    }

    private void updateDatabase() {
        String a = nameField.getText();
        String v = nameField.getText();
        project.setName(nameField.getText());

        project.setDescription(descriptionField.getText()); 
        project.setCosts(costsField.getText());
        project.setLocation(locationField.getText());
        project.setCoach(coachField.getText()); 
        project.setSupervisor(supervisorField.getText()); 
        project.setMaxNrStudents(Integer.parseInt(maxNrStudentsField.getText()));   
        project.updateDatabase();
    }

    private void setupFields() {
        fields = new ArrayList<JTextField>();

        pidField = new JTextField();        
        pidField.setPreferredSize(new Dimension(20, 24));
        pidField.setEditable(false);

        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(250, 24));
        fields.add(nameField);

        descriptionField = new JTextField();
        descriptionField.setPreferredSize(new Dimension(400, 24));
        fields.add(descriptionField);

        costsField = new JTextField();
        costsField.setPreferredSize(new Dimension(50, 24));
        fields.add(costsField);

        locationField = new JTextField();      
        locationField.setPreferredSize(new Dimension(80, 24));
        fields.add(locationField);

        coachField = new JTextField();       
        coachField.setPreferredSize(new Dimension(80, 24));
        fields.add(coachField);

        supervisorField = new JTextField();      
        supervisorField.setPreferredSize(new Dimension(80, 24));
        fields.add(supervisorField);

        for(JTextField f : fields) {
            f.setEditable(false);
        }

        NumberFormat longFormat = NumberFormat.getIntegerInstance();
        NumberFormatter numberFormatter = new NumberFormatter(longFormat);
        numberFormatter.setValueClass(Long.class); //optional, ensures you will always get a long value
        numberFormatter.setAllowsInvalid(false); //this is the key!!
        numberFormatter.setMinimum(0l); //Optional        

        maxNrStudentsField = new JFormattedTextField(numberFormatter);
        maxNrStudentsField.setEditable(false);
        maxNrStudentsField.setPreferredSize(new Dimension(50, 24));

    }

    private class EditButtonActionListener implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {

            for(JTextField f : fields) {
                f.setEditable(true);
            }
            maxNrStudentsField.setEditable(true);

            editButton.setEnabled(false);
            cancelButton.setEnabled(true);
            saveButton.setEnabled(true);          

        }
    }

    private class CancelButtonActionListener implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {

            for(JTextField f : fields) {
                f.setEditable(false);
            }
            maxNrStudentsField.setEditable(false);
            setFieldText();

            editButton.setEnabled(true);
            cancelButton.setEnabled(false);
            saveButton.setEnabled(false);          
        }
    }

    private class SaveButtonActionListener implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {

            for(JTextField f : fields) {
                f.setEditable(false);
            }
            maxNrStudentsField.setEditable(false);

            updateDatabase();

            editButton.setEnabled(true);
            cancelButton.setEnabled(false);
            saveButton.setEnabled(false);          
        }
    }
}
