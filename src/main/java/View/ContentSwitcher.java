package View;

import javax.swing.*;
import java.awt.*;

public class ContentSwitcher extends JPanel{

    private CardLayout cardLayout;

    public ContentSwitcher(){
        cardLayout = new CardLayout();
        setLayout(cardLayout);
    }

    public void switchCards(){
        cardLayout.next(this);
    }
}
