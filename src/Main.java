import user_interface.UserInterface;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("File Name Changer");
        window.add(new UserInterface());
        window.setMinimumSize(new Dimension(450, 400));

        window.requestFocusInWindow();
        window.setLocationByPlatform(true);
        window.setResizable(true);
        window.setLocationRelativeTo(null);
        window.pack();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }
}
