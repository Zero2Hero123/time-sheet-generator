import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Window extends JFrame implements ActionListener {

    private JButton generateBtn;
    
    public Window(){
        this.setSize(900,700);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setTitle("Time Sheet Generator");


        var title = new JLabel("Time Sheet Generator");
        title.setFont(new Font("Comic Sans",Font.PLAIN,30));
        title.setHorizontalAlignment(JLabel.CENTER);

        generateBtn = new JButton();
        generateBtn.setText("Generate");
        generateBtn.addActionListener(this);
        generateBtn.setFocusable(false);

        JPanel settingsBody = new JPanel(); // config for time sheet generation
        settingsBody.setBackground(Color.lightGray);
        settingsBody.setPreferredSize(new Dimension(this.getWidth(), 300));
        settingsBody.add(generateBtn);

        JPanel sheetGrid = new JPanel();
        sheetGrid.setLayout(new GridLayout(5, 3));
        sheetGrid.setPreferredSize(new Dimension(this.getWidth() / 3, 900));
        sheetGrid.setBackground(Color.CYAN);

        JPanel header = new JPanel(); // top-header to hold title and settingsBody
        header.setLayout(new BorderLayout());
        header.add(title,BorderLayout.NORTH);
        header.add(settingsBody,BorderLayout.CENTER);

        JPanel mainBody = new JPanel();
        mainBody.add(sheetGrid);
        mainBody.setBackground(Color.BLUE);


        this.add(header,BorderLayout.NORTH);
        this.add(mainBody,BorderLayout.CENTER);

        this.setVisible(true);
    }

    
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == generateBtn){
            System.out.println("Generating...");
        }
    }

}
