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

    private JPanel namesContainer;
    private JTextField nameInput;
    private JButton addNameBtn;

    private JPanel jobsContainer;
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
        title.setFont(new Font("Helvetica",Font.BOLD,30));
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

        namesContainer = new JPanel();
        namesContainer.setPreferredSize(new Dimension(300, 300));
        namesContainer.add(new JLabel("Names"));
        namesContainer.add(nameInput);
        namesContainer.add(addNameBtn);

        jobsContainer = new JPanel();
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

            
            // add a label and a remove-button for the new job added
            JLabel newName = new JLabel(nameInput.getText());
            newName.setPreferredSize(new Dimension(200, 30));
            newName.setHorizontalAlignment(JLabel.CENTER);

            JButton removeBtn = new JButton("X");
            removeBtn.setPreferredSize(new Dimension(50, 30));
            String currName = nameInput.getText(); // for action listener since nameInput.getText gets reset
            removeBtn.addActionListener((ActionEvent event) -> {
                
                names.remove(currName);
                
                
                namesContainer.remove(newName);
                namesContainer.remove(removeBtn);

                this.invalidate();
                this.validate();
                this.repaint();
            });

            namesContainer.add(newName);
            namesContainer.add(removeBtn);

            this.invalidate();
            this.validate();
            this.repaint();
            
            
            
            nameInput.setText("");
            System.out.println(names);
        } else if(e.getSource() == addJobBtn){
            jobs.add(jobInput.getText());
            
            // add a label and a remove-button for the new job added
            JLabel newJob = new JLabel(jobInput.getText());
            newJob.setPreferredSize(new Dimension(200, 30));
            newJob.setHorizontalAlignment(JLabel.CENTER);

            JButton removeBtn = new JButton("X");
            removeBtn.setPreferredSize(new Dimension(50, 30));
            String currJob = jobInput.getText(); // for action listener since jobInput.getText gets reset
            removeBtn.addActionListener((ActionEvent event) -> {
                jobs.remove(currJob);
                
                
                jobsContainer.remove(newJob);
                jobsContainer.remove(removeBtn);

                this.invalidate();
                this.validate();
                this.repaint();
            });

            jobsContainer.add(newJob);
            jobsContainer.add(removeBtn);
            
            jobInput.setText("");

            this.invalidate();
            this.validate();
            this.repaint();
            System.out.println(jobs);
        }


    }

}
