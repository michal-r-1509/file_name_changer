package user_interface;

import javax.swing.*;
import java.awt.*;

class InfoController {

    public void setSuccessText(JTextArea info, String message) {
        info.setForeground(Color.green);
        info.setText(message);
    }

    public void setErrorText(JTextArea info, String message) {
        info.setForeground(Color.red);
        info.setText(message);
    }

    public void reset(JTextArea info){
        info.setForeground(Color.black);
        info.setText("");
    }
}
