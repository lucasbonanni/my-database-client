package presentacion;


import javax.swing.*;
import java.awt.*;

public class QueryEditorPane extends JScrollPane {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4252492595829105679L;
	private final JEditorPane textArea;

    public QueryEditorPane(){
        this.textArea = new JEditorPane();
    }

    public void build(){

        setLayout(new ScrollPaneLayout.UIResource());
        setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_AS_NEEDED);
        setViewport(createViewport());
        setVerticalScrollBar(createVerticalScrollBar());
        setHorizontalScrollBar(createHorizontalScrollBar());
        if (textArea != null) {
            setViewportView(textArea);
        }
        //setUIProperty("opaque",true);
        updateUI();

        if (!this.getComponentOrientation().isLeftToRight()) {
            viewport.setViewPosition(new Point(Integer.MAX_VALUE, 0));
        }
    }

    public String getText(){
        return this.textArea.getText();
    }

    public void clearText(){
        this.textArea.setText("");
    }
}
