package View;

import controller.RadioInfo;

import javax.imageio.plugins.jpeg.JPEGHuffmanTable;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * Class that hold all JPanels that are created and puts them
 * into JFrame.
 */
public class GraphicalUserInterface {
    private final static int WINDOW_WIDTH = 1600;
    private final static int WINDOW_HEIGHT = 930;
    private final static String CHANNELS = "channels";
    private final static String PROGRAMS = "programs";
    private final static String PROGRAM_DESCRIPTION = "programDescription";
    private final static String PROGRAM_IMAGE = "programImage";
    private HashMap<String, Content> contents;
    private JFrame jFrame;
    private MenuBar menu;
    private ContentSwitcher switcher;

    /**
     * @param programName name of JFrame.
     */
    public GraphicalUserInterface(String programName) {
        setUp(programName);
    }

    /**
     * Creates JFrame
     * @param programName name of JFrame
     */
    public void setUp(String programName){
        switcher = new ContentSwitcher();
        jFrame =  new JFrame(programName);
        jFrame.setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        UIManager.put("ScrollBarUI", "View.MyScrollBarUI");
        createContent();
        addContentToFrame();
        jFrame.setVisible(true);
    }

    /**
     * @param contentName type of content
     * @param objects Array of objects to be put into content.
     */
    public void addContent(String contentName, Object[] objects){
        contents.get(contentName).addContent(objects);
    }

    /**
     * @param contentName type of content
     * @param listenerForAction implemented interface.
     * @param column listener will be set on this given column in table.
     */
    public void setListenerOnContent(String contentName,
                                     ListenerForAction listenerForAction,
                                     int column){
        contents.get(contentName).setActionListener(listenerForAction, column);
    }

    /**
     * Clears content.
     * @param contentName name of content to be cleared.
     */
    public void clearContent(String contentName){
        contents.get(contentName).clearContent();
    }

    /**
     * Creates menuBar for JFrame. Can be only one.
     */
    public void createMenuBar(){
        menu = new MenuBar();
        jFrame.setJMenuBar(menu);
    }

    /**
     * Adds menu to bar
     * @param name name of menu.
     */
    public void addMenuToBar(String name){
        menu.addMenuToBar(name);
    }

    /**
     * @param actionForButton listener
     * @param menuName name of Menu.
     */
    public void setListenerForMenu(ListenerForAction actionForButton,
                                   String menuName){
        menu.setListener(actionForButton, menuName);
    }

    public void displayErrorMessage(String message){
        if(JOptionPane.showConfirmDialog(jFrame, message,
                    "Error Message", JOptionPane.YES_OPTION) == 0)
            System.exit(0);
    }

    /**
     * Creates objects of four classes that implement Content. Those are:
     * - Channels
     * - Programs
     * - ProgramDescription
     * - ProgramImage
     * and puts them into hashMap as value.
     */
    private void createContent(){
        contents = new HashMap<>();
        contents.put(CHANNELS, new Channels());
        contents.put(PROGRAM_IMAGE, new ProgramImage());
        contents.put(PROGRAM_DESCRIPTION, new ProgramDescription());
        contents.put(PROGRAMS, new Programs());
    }

    /**
     * Adds every content into JFrame.
     */
    private void addContentToFrame(){
        JPanel boxPanel = new JPanel();
        boxPanel.setLayout(new GridLayout(1, 3));
        boxPanel.add(contents.get(CHANNELS));
        boxPanel.add(contents.get(PROGRAMS));
        contents.get(PROGRAM_IMAGE).setLayout(new GridLayout(2,1));
        contents.get(PROGRAM_IMAGE).add(contents.get(PROGRAM_DESCRIPTION));
        boxPanel.add(contents.get(PROGRAM_IMAGE));
        switcher.add(loadingPanel());
        switcher.add(boxPanel);

        jFrame.add(switcher);
    }

    private JPanel loadingPanel(){
        JPanel panel = new JPanel();
        panel.setBackground(new Color(249, 249, 249));
        BoxLayout layoutMgr = new BoxLayout(panel, BoxLayout.PAGE_AXIS);
        panel.setLayout(layoutMgr);

        java.net.URL imageURL = getClass().getResource("/Loading.gif");
        ImageIcon imageIcon = new ImageIcon(imageURL);
        JLabel iconLabel = new JLabel();
        iconLabel.setIcon(imageIcon);
        imageIcon.setImageObserver(iconLabel);

        /** TESTING */
        JLabel label = new JLabel("Loading");

        label.setFont(new Font("Serif", Font.PLAIN, 150));

        panel.add(iconLabel);
        panel.add(label);

        JPanel kappa = new JPanel();
        kappa.add(panel);
        kappa.setBackground(new Color(249, 249, 249));

        return kappa;
    }

    public void switchOnContent(){
        switcher.switchCards();
    }
}
