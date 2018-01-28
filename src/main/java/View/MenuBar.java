package View;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.util.HashMap;

/**
 * Creates JMenuBar.
 */
class MenuBar extends JMenuBar {

    private HashMap<String, JMenu> menus;

    MenuBar(){
        menus = new HashMap<>();
    }

    /**
     * @param name name of menu.
     */
    void addMenuToBar(String name){
        JMenu menu = new JMenu(name);
        menus.put(name, menu);
        this.add(menu);
    }

    /**
     * @param actionForButton listener.
     * @param menuName name of menu.
     */
    void setListener(ListenerForAction actionForButton, String menuName){
        menus.get(menuName).addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent menuEvent) {
                actionForButton.action(menuEvent.getSource().toString());

            }

            @Override
            public void menuDeselected(MenuEvent menuEvent) {

            }

            @Override
            public void menuCanceled(MenuEvent menuEvent) {

            }
        });
    }
}
