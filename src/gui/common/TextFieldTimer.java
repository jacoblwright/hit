package gui.common;

import javax.swing.Timer;
import java.awt.event.*;

public class TextFieldTimer {

    private final int DELAY = 300;
    
    private Timer timer;
    
    public TextFieldTimer(ActionListener listener) {
        
        timer = new Timer(DELAY, listener);
        timer.setRepeats(false);
        
    }
    
    public void start() {
        
        timer.start();
        
    }
    
}
