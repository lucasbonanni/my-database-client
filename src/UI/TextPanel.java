package UI;

import javax.swing.*;

public class TextPanel {
    public static JScrollPane GetTextPanel(){
        JTextArea ta = new JTextArea();

        return new JScrollPane(ta);
    }
}
