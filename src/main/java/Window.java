import javax.swing.JFrame;
import java.awt.Color;

public class Window extends JFrame {
    
    public Window(){
        this.setSize(900,700);
        this.setVisible(true);
        this.setLayout(null);

        this.getContentPane().setBackground(new Color(29, 65, 117));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
