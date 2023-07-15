import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Schedule {

    private ArrayList<String> priorityNames;
    private ArrayList<String> names;
    private ArrayList<String> jobs;

    private HashMap<String,HashMap<String,Integer>> chances;


    public Schedule(ArrayList<String> names, ArrayList<String> jobs){
        this.names = names;
        this.jobs = jobs;
    }

    public Schedule(ArrayList<String> names, ArrayList<String> jobs,HashMap<String,HashMap<String,Integer>> chances){
        this.names = names;
        this.jobs = jobs;
        
        this.chances = chances;
    }

    public Schedule(ArrayList<String> names, ArrayList<String> jobs,ArrayList<String> priorityNames){
        this.names = names;
        this.jobs = jobs;
        this.priorityNames = priorityNames;
    }

    public Schedule(ArrayList<String> names, ArrayList<String> jobs,ArrayList<String> priorityNames, HashMap<String,HashMap<String,Integer>> chances){
        this.names = names;
        this.jobs = jobs;
        this.priorityNames = priorityNames;

        this.chances = chances;
    }

    public static ArrayList<String> nextSundays(int howMany){
        LocalDate today = LocalDate.now();
        LocalDate nextSunday = today;

        ArrayList<String> nextSundaysFormatted = new ArrayList<String>();

        while(nextSunday.getDayOfWeek() != DayOfWeek.SUNDAY){
            nextSunday = nextSunday.plusDays(1);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd");

        if(nextSunday.equals(LocalDate.now())){
            nextSunday = nextSunday.plusDays(7);
        }

        for(int i=0;i<howMany;i++){
            nextSundaysFormatted.add(nextSunday.format(formatter));

            nextSunday = nextSunday.plusDays(7);
        }



        return nextSundaysFormatted;
    }

    public ArrayList<HashMap<String,String>> generate(int numDays,boolean percentEnabled){
        var newSchedule = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<numDays;i++){
            newSchedule.add(new HashMap<String,String>());
        }
        
        // for each day, generate random
        for(int i = 0; i < numDays; i++){
            ArrayList<String> jobsCopy = new ArrayList<String>();
            for(String job : jobs){
                jobsCopy.add(job);
            }

            ArrayList<String> priorityNamesCopy = new ArrayList<String>();
            if(priorityNames != null){
                for(String name : priorityNames){
                    priorityNamesCopy.add(name);
                }
            }

            Iterator<String> priorityIt = priorityNamesCopy.iterator();

            // generate for priority names with or without percentages
            while(priorityIt.hasNext()){
                int jobIdx = (int) (Math.random() * jobsCopy.size());

                String priorityName = priorityIt.next();

                if(percentEnabled){
                    Chance chance = new Chance();

                    var chosen = chance.rollStr(chances.get(priorityName),jobsCopy);

                    newSchedule.get(i).put(chosen,priorityName);

                    jobsCopy.remove(chosen);
                } else {
                    newSchedule.get(i).put(jobsCopy.get(jobIdx),priorityName);
                    jobsCopy.remove(jobIdx);
                }

                if(jobsCopy.size() == 0){
                    break;
                }
                
            }
            
            Iterator<String> namesIt = names.iterator();
            //generate for normal names with or without percentages
            while(namesIt.hasNext()){
                if(jobsCopy.size() == 0){
                    break;
                }


                int jobIdx = (int) (Math.random() * jobsCopy.size());
                String currName = namesIt.next();
                

                if(percentEnabled){
                    Chance chance = new Chance();

                    var chosen = chance.rollStr(chances.get(currName),jobsCopy);

                    newSchedule.get(i).put(chosen,currName);

                    jobsCopy.remove(chosen);
                } else {
                    newSchedule.get(i).put(jobsCopy.get(jobIdx),currName);
                    jobsCopy.remove(jobIdx);
                }
                    
            }

            // System.out.println(newSchedule);
            
            
        }
        return newSchedule;
    }

}
