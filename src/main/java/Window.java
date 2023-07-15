import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Window extends JFrame implements ActionListener {


    private JPanel mainBody = new JPanel();
    private ArrayList<JPanel> sheets = new ArrayList<JPanel>();


    private JButton generateBtn;

    private JButton printBtn;

    private JPanel namesContainer;
    private JTextField nameInput;
    private JButton addNameBtn;

    private JPanel jobsContainer;
    private JTextField jobInput;
    private JButton addJobBtn;

    private JPanel taskPercentageContainer;
    private JPanel taskInnerContainer;
    private JComboBox<String> nameSelect;
    JButton togglePercentagesBtn;
    private String currSelected = "None";

    private ArrayList<String> priorityNames = new ArrayList<String>();
    private ArrayList<String> names = new ArrayList<String>();
    private ArrayList<String> jobs = new ArrayList<String>();

    private final Color bgColor = new Color(38, 43, 51);
    private final Color fgColor = new Color(70, 78, 92);

    private int numDays = 5;
    private boolean percentagesEnabled = false;

    private ArrayList<HashMap<String,String>> currSchedule;

    private HashMap<String,HashMap<String,Integer>> chancesOfEachJob = new HashMap<String,HashMap<String,Integer>>();
    
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
                        writer.write("\n");

                        for(var jobMap : chancesOfEachJob.values()){
                            for(int percentage : jobMap.values()){
                                writer.write(percentage+",");
                            }
                        }

                        writer.close();
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                } else if(System.getProperty("os.name").startsWith("Mac")){
                    try {
                        
                        BufferedWriter writer = new BufferedWriter(new FileWriter("names_jobs_data.txt"));

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

        printBtn = new JButton();
        printBtn.setText("Print");
        printBtn.addActionListener(this);
        printBtn.setFocusable(false);
        printBtn.setBackground(new Color(88, 98, 115));
        printBtn.setEnabled(false);
        printBtn.setForeground(Color.WHITE);

        printBtn.addActionListener((e) -> {
            Printer printer = new Printer(sheets);

            printer.printSchedule();
        });

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

        JSlider daysSlider = new JSlider(5,20,5);
        daysSlider.setOrientation(SwingConstants.VERTICAL);
        daysSlider.setPreferredSize(new Dimension(100, 230));
        daysSlider.setBackground(fgColor);
        daysSlider.setForeground(Color.WHITE);

        daysSlider.setPaintTrack(true);
        daysSlider.setMajorTickSpacing(5);
        daysSlider.setPaintLabels(true);


        JPanel sliderContainer = new JPanel();
        sliderContainer.setPreferredSize(new Dimension(100, 300));
        sliderContainer.setBackground(fgColor);
        var daysLabel = new JLabel("<html>Number of<br/> Sundays <br>5</html>");
        daysLabel.setForeground(Color.WHITE);
        daysSlider.setToolTipText("How many days to Generate");
        daysSlider.addChangeListener((e) -> {
            numDays = daysSlider.getValue();
            
            daysLabel.setText("<html>Number of<br/> Sundays <br>"+numDays+"</html>");
        });
        sliderContainer.add(daysLabel);
        sliderContainer.add(daysSlider);

        nameSelect = new JComboBox<String>();
        nameSelect.setPreferredSize(new Dimension(225, 30));
        nameSelect.setBackground(fgColor);
        nameSelect.setForeground(Color.WHITE);
        nameSelect.setFocusable(false);
        nameSelect.addItem("None");
        nameSelect.addActionListener(this);

        
        togglePercentagesBtn = new JButton("Off");
        togglePercentagesBtn.setPreferredSize(new Dimension(60, 30));
        togglePercentagesBtn.setBackground(Color.DARK_GRAY);
        togglePercentagesBtn.setForeground(Color.WHITE);
        togglePercentagesBtn.setFocusable(false);
        togglePercentagesBtn.addActionListener(e -> {
            percentagesEnabled = !percentagesEnabled;

            if(percentagesEnabled){
                togglePercentagesBtn.setBackground(new Color(56, 138, 78));
                togglePercentagesBtn.setText("On");

                nameSelect.setSelectedIndex(1);
            } else {
                togglePercentagesBtn.setBackground(Color.DARK_GRAY);
                togglePercentagesBtn.setText("Off");
                nameSelect.setSelectedItem("None");
                
            }
        });

        taskInnerContainer = new JPanel();
        taskInnerContainer.setPreferredSize(new Dimension(300, 250));
        taskInnerContainer.setBackground(fgColor);
        var taskInnerContainerScroll = new JScrollPane(taskInnerContainer);
        taskInnerContainerScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        taskPercentageContainer = new JPanel();
        taskPercentageContainer.setPreferredSize(new Dimension(300, 300));
        taskPercentageContainer.setBackground(fgColor);
        var percentageLabel = new JLabel("Job Assignment Percentage");
        percentageLabel.setForeground(Color.WHITE);
        taskPercentageContainer.add(percentageLabel);
        taskPercentageContainer.add(nameSelect);
        taskPercentageContainer.add(togglePercentagesBtn);
        taskPercentageContainer.add(taskInnerContainerScroll);


        JPanel settingsBody = new JPanel(); // config for time sheet generation
        settingsBody.setBackground(bgColor);
        settingsBody.setPreferredSize(new Dimension(this.getWidth(), 300));
        settingsBody.add(sliderContainer);
        settingsBody.add(namesContainer);
        settingsBody.add(jobsContainer);
        settingsBody.add(taskPercentageContainer);


        var lowerHeader = new JPanel(); // Jpanel in SOUTH of header to achieve centered generate-button
        lowerHeader.setBackground(bgColor);
        lowerHeader.add(generateBtn);
        lowerHeader.add(printBtn);

        JPanel header = new JPanel(); // top-header to hold title and settingsBody
        header.setLayout(new BorderLayout());
        header.add(title,BorderLayout.NORTH);
        header.add(settingsBody,BorderLayout.CENTER);
        header.add(lowerHeader,BorderLayout.SOUTH);

        sheets.add(createSheetGrid());

        //container of generated schedule 
        mainBody.add(sheets.get(0));
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
            }

        } else if(System.getProperty("os.name").startsWith("Mac")){
            File file = new File("names_jobs_data.txt");
            if(file.exists()){
                try{
                    loadData(new BufferedReader(new FileReader("names_jobs_data.txt")));
                } catch (FileNotFoundException e){
                    System.out.println("ERROR: Save Data Not Found");
                }
            }
        }

        this.setVisible(true);
    }

    
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == generateBtn){
            if(names.size() == 0 || jobs.size() == 0){
                return;
            }

            printBtn.setEnabled(true);

            Iterator<JPanel> it = sheets.iterator();
            while(it.hasNext()){
                JPanel curr = it.next();
                mainBody.remove(curr);
                it.remove();
            }
            
                
            int pagesNeeded = (int) (Math.ceil((double) numDays/5));

            for(int i=0;i<pagesNeeded;i++){
                createSheetGrid();
            }

            ArrayList<String> nextSundayDates = Schedule.nextSundays(numDays);
            

            System.out.println("Generating...");

            Schedule newSchedule;

            if(priorityNames.size() > 0){
                newSchedule = (percentagesEnabled) ? new Schedule(names, jobs,priorityNames,chancesOfEachJob) : new Schedule(names, jobs,priorityNames);
            } else {
                newSchedule = (percentagesEnabled) ? new Schedule(names, jobs,chancesOfEachJob) : new Schedule(names, jobs);
            }

            
            
            currSchedule = newSchedule.generate(numDays,percentagesEnabled);

           
            
            int dayIndex = 0;
            int dayCounter = 0;
            int sheetIdx = 0;

            for(HashMap<String,String> day : currSchedule){
                var firstColumn = new JPanel();
                firstColumn.setBorder(new LineBorder(Color.BLACK,1));
                for(String key : day.keySet()){
                    String jobString = String.format("%s - %s",day.get(key),key);
                    JLabel line = new JLabel(jobString);
                    line.setFont(new Font("Helvetica",Font.PLAIN,15));
                    line.setPreferredSize(new Dimension(200,15));

                    firstColumn.add(line);

                }
                
                sheets.get(sheetIdx).add(firstColumn);

                var secondColumn = new JPanel();
                JLabel date = new JLabel(nextSundayDates.get(dayIndex));
                date.setFont(new Font("Helvetica",Font.PLAIN,15));
                date.setPreferredSize(new Dimension(200,15));
                secondColumn.add(date);

                secondColumn.setBorder(new LineBorder(Color.BLACK,1));

                sheets.get(sheetIdx).add(secondColumn);

                var thirdColumn = new JPanel();
                thirdColumn.setBorder(new LineBorder(Color.BLACK,1));
                JLabel line = new JLabel("[Supervising Here]");
                line.setFont(new Font("Helvetica",Font.PLAIN,15));
                line.setPreferredSize(new Dimension(200,15));
                thirdColumn.add(line);
                sheets.get(sheetIdx).add(thirdColumn);


                dayIndex++;
                dayCounter++;

                if(dayCounter >= 5){
                    sheetIdx++;

                    dayCounter = 0;
                }
            }

            this.invalidate();
            this.validate();
            this.repaint();
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
        } else if(e.getSource() == nameSelect){
            String previousState = currSelected;

            currSelected = (String) nameSelect.getSelectedItem();

            if(previousState == "None" && currSelected != "None"){
                percentagesEnabled = true;
                togglePercentagesBtn.setBackground(new Color(56, 138, 78));
                togglePercentagesBtn.setText("On");
            }

            if(currSelected == "None"){

                for(int i = 0; i < taskInnerContainer.getComponents().length;i++){
                    Component comp = taskInnerContainer.getComponents()[i];

                    comp.setEnabled(false);
                }
                return;
            } else {
                for(int i = 0; i < taskInnerContainer.getComponents().length;i++){
                    Component comp = taskInnerContainer.getComponents()[i];

                    comp.setEnabled(true);
                }
            }

            
            
            
            ArrayList<JSlider> sliders = new ArrayList<JSlider>();
            for(int i = 0; i < taskInnerContainer.getComponents().length;i++){
                Component comp = taskInnerContainer.getComponents()[i];

                if(comp instanceof JSlider){
                    var slider = (JSlider) comp;
                    
                    sliders.add(slider);
                }
            }

            for(int i=0;i<sliders.size();i++){
                String jobName = jobs.get(i);
                    
                sliders.get(i).setValue(chancesOfEachJob.get(currSelected).get(jobName));
            }
        }

        
    }

    private void loadData(BufferedReader reader){
        try{
            String namesRaw = reader.readLine();
            String jobsRaw = reader.readLine();


            // job Assingment Percentage
            // String[] percentagesRaw = reader.readLine().split(",");
            // ArrayList<String> jobPercentages = (ArrayList<String>) Arrays.asList(percentagesRaw);
            // ArrayList<Integer> jobPercentagesAsInt = new ArrayList<>();
            // jobPercentages.forEach(p -> {
            //     jobPercentagesAsInt.add(Integer.parseInt(p));
            // });

            // ArrayList<ArrayList<Integer>> percentagesAsIntProper = new ArrayList<>();
            // for(int i=0;i<jobPercentagesAsInt.size();i++){
            //     percentagesAsIntProper.add(new ArrayList<Integer>());
            // }

            // Iterator<Integer> it = jobPercentagesAsInt.iterator();
            // int index = 0;
            // int counter = 0;
            // while(it.hasNext()){
            //     percentagesAsIntProper.get(index).add(it.next());

            //     if(counter == 3){
            //         index++;
            //         counter = 0;
            //     }

            // }
            // Job Assignment Percentage^^^^

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



        for(String name : names){
            chancesOfEachJob.get(name).put(jobName,100);
        }
            
        // add a label and a remove-button for the new job added
        JLabel newJob = new JLabel(jobName);
        newJob.setPreferredSize(new Dimension(200, 30));
        newJob.setHorizontalAlignment(JLabel.CENTER);
        newJob.setForeground(Color.WHITE);

        JButton removeBtn = new JButton("X");
        removeBtn.setPreferredSize(new Dimension(50, 30));
        removeBtn.setBackground(new Color(189, 58, 58));
        removeBtn.setForeground(Color.WHITE);

        // Job Assingment Percentage
        JLabel percent = new JLabel(String.format("%s: 100%%",jobName));
        percent.setForeground(Color.WHITE);
        JSlider chanceOfJobSlider = new JSlider(0,100,100);
        chanceOfJobSlider.setBackground(fgColor);
        chanceOfJobSlider.setPreferredSize(new Dimension(270, 30));
        
        chanceOfJobSlider.addChangeListener(e -> {
            int newChance = chanceOfJobSlider.getValue();

            percent.setText(String.format("%s: %d%%",jobName,newChance));

            chancesOfEachJob.get(currSelected).put(jobName,newChance);

            
        });

        if(currSelected == "None"){
            percent.setEnabled(false);
            chanceOfJobSlider.setEnabled(false);
        }

        taskInnerContainer.add(percent);
        taskInnerContainer.add(chanceOfJobSlider);



        String currJob = jobName;
        removeBtn.addActionListener((ActionEvent event) -> {
            jobs.remove(currJob);
            
            
            jobsContainer.remove(newJob);
            jobsContainer.remove(removeBtn);
            
            for(String name : names){
                chancesOfEachJob.get(name).remove(currJob);
            }

            taskInnerContainer.remove(percent);
            taskInnerContainer.remove(chanceOfJobSlider);

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
        nameSelect.addItem(memberName);

        chancesOfEachJob.put(memberName,new HashMap<String,Integer>());

        for(String job : jobs){
            chancesOfEachJob.get(memberName).put(job,100);
        }

        
            
        // add a label and a remove-button for the new job added
        JLabel newName = new JLabel(memberName);
        newName.setPreferredSize(new Dimension(150, 30));
        newName.setHorizontalAlignment(JLabel.CENTER);
        newName.setForeground(Color.WHITE);

        JButton priorityBtn = new JButton("*");
        priorityBtn.setPreferredSize(new Dimension(50, 30));
        priorityBtn.setBackground(Color.DARK_GRAY);
        priorityBtn.setForeground(Color.WHITE);
        priorityBtn.setFocusable(false);
        priorityBtn.setToolTipText(String.format("<html>Should <b>%s</b> be included everyday?</html>",memberName));

        JButton removeBtn = new JButton("X");
        removeBtn.setPreferredSize(new Dimension(50, 30));
        removeBtn.setBackground(new Color(189, 58, 58));
        removeBtn.setForeground(Color.WHITE);

        
        String currName = memberName; // for action listener since nameInput.getText gets reset
        removeBtn.addActionListener((ActionEvent event) -> {
            
            priorityNames.remove(currName);
            names.remove(currName);
            chancesOfEachJob.remove(currName);
            
            
            namesContainer.remove(newName);
            namesContainer.remove(priorityBtn);
            namesContainer.remove(removeBtn);

            nameSelect.removeItem(currName);
            

            this.invalidate();
            this.validate();
            this.repaint();
        });

        priorityBtn.addActionListener(e -> {
            if(priorityNames.contains(currName)){
                priorityNames.remove(currName);
                priorityBtn.setBackground(Color.DARK_GRAY);
            } else {
                priorityNames.add(currName);
                priorityBtn.setBackground(new Color(36, 117, 163));
            }

            System.out.printf("Priority names %s\n",priorityNames);
        });

        namesContainer.add(newName);
        namesContainer.add(priorityBtn);
        namesContainer.add(removeBtn);


        this.invalidate();
        this.validate();
        this.repaint();
    }

    private JPanel createSheetGrid() {
        JPanel sheetGrid = new JPanel();
        sheetGrid.setLayout(new GridLayout(5, 3));
        sheetGrid.setPreferredSize(new Dimension(816, 1000));
        sheetGrid.setBackground(Color.white);
        sheetGrid.setBorder(new EmptyBorder(96,96,96,96));

        sheets.add(sheetGrid);
        mainBody.add(sheetGrid);

        return sheetGrid;
    }

}
