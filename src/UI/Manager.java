package UI;

public class Manager {
    private MainFrame mainFrame;

    public Manager() {
        this.mainFrame = new MainFrame();
    }

    public void build(){
        this.mainFrame.build();
    }

    public void showMainFrame()
    {
        this.mainFrame.showFrame();
    }
}
