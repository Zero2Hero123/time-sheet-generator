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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

import java.util.ArrayList;
import java.util.HashMap;

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

    private final Color bgColor = new Color(38, 43, 51);
    private final Color fgColor = new Color(70, 78, 92);

    private ArrayList<HashMap<String,String>> currSchedule;
    
    public Window(){

        this.addWindowListener(new WindowAdapter() {
            
            @Override
            public void windowClosing(WindowEvent event){


                // Save names and jobs to file system
                if(System.getProperty("os.name").startsWith("Windows")){

                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\ProgramData\\names_jobs_data.txt"));

                        for(String name : names){
                            writer.write(name+",");
                        }
                        writer.write("\n");

                        for(String job : jobs){
                            writer.write(job+",");
                        }

                        writer.close();
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
                
            }
        });
        


        this.setSize(900,700);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setTitle("Time Sheet Generator");

        ImageIcon logo = new ImageIcon("church_logo.png");
        this.setIconImage(logo.getImage());



        var title = new JLabel("Time Sheet Generator");
        title.setFont(new Font("Helvetica",Font.BOLD,30));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setForeground(Color.WHITE);
        title.setBackground(bgColor);
        title.setOpaque(true);

        generateBtn = new JButton();
        generateBtn.setText("Generate");
        generateBtn.addActionListener(this);
        generateBtn.setFocusable(false);
        generateBtn.setBackground(new Color(88, 98, 115));
        generateBtn.setForeground(Color.WHITE);

        nameInput = new JTextField(){
            @Override public void setBorder(Border border) {
                // No border
            }
        };
        nameInput.setPreferredSize(new Dimension(100,20));
        nameInput.setBackground(new Color(22, 25, 31));
        nameInput.setForeground(Color.WHITE);
        nameInput.setCaretColor(Color.WHITE);

        addNameBtn = new JButton("Add");
        addNameBtn.addActionListener(this);
        addNameBtn.setPreferredSize(new Dimension(70,20));
        addNameBtn.setBackground(new Color(88, 98, 115));
        addNameBtn.setForeground(Color.WHITE);

        jobInput = new JTextField(){
            @Override public void setBorder(Border border) {
                // No border
            }
        };
        jobInput.setPreferredSize(new Dimension(100,20));
        jobInput.setBackground(new Color(22, 25, 31));
        jobInput.setForeground(Color.WHITE);
        jobInput.setCaretColor(Color.WHITE);

        addJobBtn = new JButton("Add");
        addJobBtn.addActionListener(this);
        addJobBtn.setPreferredSize(new Dimension(70,20));
        addJobBtn.setBackground(new Color(88, 98, 115));
        addJobBtn.setForeground(Color.WHITE);


        namesContainer = new JPanel();
        namesContainer.setPreferredSize(new Dimension(300, 300));
        namesContainer.setBackground(fgColor);
        var namesLabel = new JLabel("Names");
        namesLabel.setForeground(Color.WHITE);
        namesContainer.add(namesLabel);
        namesContainer.add(nameInput);
        namesContainer.add(addNameBtn);

        jobsContainer = new JPanel();
        jobsContainer.setPreferredSize(new Dimension(300, 300));
        jobsContainer.setBackground(fgColor);
        var jobsLabel = new JLabel("Jobs");
        jobsLabel.setForeground(Color.WHITE);
        jobsContainer.add(jobsLabel);
        jobsContainer.add(jobInput);
        jobsContainer.add(addJobBtn);


        JPanel settingsBody = new JPanel(); // config for time sheet generation
        settingsBody.setBackground(bgColor);
        settingsBody.setPreferredSize(new Dimension(this.getWidth(), 300));
        settingsBody.add(namesContainer);
        settingsBody.add(jobsContainer);

        JPanel sheetGrid = new JPanel();
        sheetGrid.setLayout(new GridLayout(5, 3));
        sheetGrid.setPreferredSize(new Dimension(816, 1000));
        sheetGrid.setBackground(Color.white);


        var lowerHeader = new JPanel(); // Jpanel in SOUTH of header to achieve centered generate-button
        lowerHeader.setBackground(bgColor);
        lowerHeader.add(generateBtn);

        JPanel header = new JPanel(); // top-header to hold title and settingsBody
        header.setLayout(new BorderLayout());
        header.add(title,BorderLayout.NORTH);
        header.add(settingsBody,BorderLayout.CENTER);
        header.add(lowerHeader,BorderLayout.SOUTH);

        JPanel mainBody = new JPanel(); //container of generated schedule 
        mainBody.add(sheetGrid);
        mainBody.setBackground(bgColor);

        
        var mainBodyWithScroll = new JScrollPane(mainBody); // adds scrollbar to mainBody
        mainBodyWithScroll.setBorder(null);

        this.add(header,BorderLayout.NORTH);
        this.add(mainBodyWithScroll,BorderLayout.CENTER);

        if(System.getProperty("os.name").startsWith("Windows")){
            File file = new File("C:\\ProgramData\\names_jobs_data.txt");
            if(file.exists()){
                try{
                    loadData(new BufferedReader(new FileReader("C:\\ProgramData\\names_jobs_data.txt")));
                } catch (FileNotFoundException e){
                    System.out.println("ERROR: Save Data Not Found");
                }
            } else {
                System.out.println(false);
            }

        }

        this.setVisible(true);
    }

    
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == generateBtn){
            
            System.out.println("Generating...");

            Schedule newSchedule = new Schedule(names, jobs);
            
            currSchedule = newSchedule.generate(5);

            System.out.println(currSchedule);


        } else if(e.getSource() == addNameBtn){
            
            addName(nameInput.getText());
            
            nameInput.setText("");
            nameInput.grabFocus();
            System.out.println(names);
        } else if(e.getSource() == addJobBtn){
            addJob(jobInput.getText());
            
            jobInput.grabFocus();
            jobInput.setText("");

            System.out.println(jobs);
        }

        
    }

    private void loadData(BufferedReader reader){
        try{
            String namesRaw = reader.readLine();
            String jobsRaw = reader.readLine();

            if(namesRaw == null || jobsRaw == null){
                System.out.println("Save-Data File is empty");
                
                return;
            }

            for(String name : namesRaw.split(",")){
                addName(name);
            }

            for(String job : jobsRaw.split(",")){
                addJob(job);
            }


            reader.close();

        } catch (IOException e){
            System.out.println(e);
        }


    }

    private void addJob(String jobName){
        jobs.add(jobName);
            
        // add a label and a remove-button for the new job added
        JLabel newJob = new JLabel(jobName);
        newJob.setPreferredSize(new Dimension(200, 30));
        newJob.setHorizontalAlignment(JLabel.CENTER);
        newJob.setForeground(Color.WHITE);

        JButton removeBtn = new JButton("X");
        removeBtn.setPreferredSize(new Dimension(50, 30));
        removeBtn.setBackground(new Color(189, 58, 58));
        removeBtn.setForeground(Color.WHITE);

        String currJob = jobName;
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

        this.invalidate();
        this.validate();
        this.repaint();
    }

    private void addName(String memberName){
        names.add(memberName);

            
        // add a label and a remove-button for the new job added
        JLabel newName = new JLabel(memberName);
        newName.setPreferredSize(new Dimension(200, 30));
        newName.setHorizontalAlignment(JLabel.CENTER);
        newName.setForeground(Color.WHITE);

        JButton removeBtn = new JButton("X");
        removeBtn.setPreferredSize(new Dimension(50, 30));
        removeBtn.setBackground(new Color(189, 58, 58));
        removeBtn.setForeground(Color.WHITE);
        
        String currName = memberName; // for action listener since nameInput.getText gets reset
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
    }

}
