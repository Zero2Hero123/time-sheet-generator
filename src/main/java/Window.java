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
import javax.swing.JTextField;

import java.util.ArrayList;

public class Window extends JFrame implements ActionListener {

    private JButton generateBtn;

    private JTextField nameInput;
    private JButton addNameBtn;

    private JTextField jobInput;
    private JButton addJobBtn;

    private ArrayList<String> names = new ArrayList<String>();
    private ArrayList<String> jobs = new ArrayList<String>();
    
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

        nameInput = new JTextField();
        nameInput.setPreferredSize(new Dimension(100,20));
        addNameBtn = new JButton("Add");
        addNameBtn.addActionListener(this);
        addNameBtn.setPreferredSize(new Dimension(70,20));

        jobInput = new JTextField();
        jobInput.setPreferredSize(new Dimension(100,20));
        addJobBtn = new JButton("Add");
        addJobBtn.addActionListener(this);
        addJobBtn.setPreferredSize(new Dimension(70,20));

        var namesContainer = new JPanel();
        namesContainer.setPreferredSize(new Dimension(300, 300));
        namesContainer.add(new JLabel("Names"));
        namesContainer.add(nameInput);
        namesContainer.add(addNameBtn);

        var jobsContainer = new JPanel();
        jobsContainer.setPreferredSize(new Dimension(300, 300));
        jobsContainer.add(new JLabel("Jobs"));
        jobsContainer.add(jobInput);
        jobsContainer.add(addJobBtn);


        JPanel settingsBody = new JPanel(); // config for time sheet generation
        settingsBody.setBackground(Color.lightGray);
        settingsBody.setPreferredSize(new Dimension(this.getWidth(), 300));
        settingsBody.add(namesContainer);
        settingsBody.add(jobsContainer);

        JPanel sheetGrid = new JPanel();
        sheetGrid.setLayout(new GridLayout(5, 3));
        sheetGrid.setPreferredSize(new Dimension(this.getWidth() / 3, 900));
        sheetGrid.setBackground(Color.CYAN);


        var lowerHeader = new JPanel(); // Jpanel in SOUTH of header to achieve centered generate-button
        lowerHeader.add(generateBtn);

        JPanel header = new JPanel(); // top-header to hold title and settingsBody
        header.setLayout(new BorderLayout());
        header.add(title,BorderLayout.NORTH);
        header.add(settingsBody,BorderLayout.CENTER);
        header.add(lowerHeader,BorderLayout.SOUTH);

        JPanel mainBody = new JPanel(); //container of generated schedule 
        mainBody.add(sheetGrid);
        mainBody.setBackground(Color.BLUE);

        


        this.add(header,BorderLayout.NORTH);
        this.add(mainBody,BorderLayout.CENTER);

        this.setVisible(true);
    }

    
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == generateBtn){
            System.out.println("Generating...");
        } else if(e.getSource() == addNameBtn){
            names.add(nameInput.getText());

            System.out.println(names);
        } else if(e.getSource() == addJobBtn){
            jobs.add(jobInput.getText());

            System.out.println(jobs);
        }


    }

}
