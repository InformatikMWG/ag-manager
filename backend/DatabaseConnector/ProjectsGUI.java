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

        add(new ProjectPanel(1));
        pack();
        setVisible(true);
        

    }
}
