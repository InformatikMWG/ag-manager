import javax.swing.*;
import java.awt.*;

public class ProjectsGUI extends JFrame
{
    public ProjectsGUI() {
        initUI();
    }

    private void initUI() {
        setTitle("Projektliste");
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BoxLayout boxLayout = new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS);

        setLayout(boxLayout);

        for(Project p : Organizer.getAllProjects()) {
            add(new ProjectPanel(p));
            pack();
            setVisible(true);
        }

    }
}
