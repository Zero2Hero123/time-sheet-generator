import java.util.ArrayList;
import java.util.HashMap;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Schedule {

    private ArrayList<String> names;
    private ArrayList<String> jobs;

    public Schedule(ArrayList<String> names, ArrayList<String> jobs){
        this.names = names;
        this.jobs = jobs;
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

    public ArrayList<HashMap<String,String>> generate(int numDays){
        var newSchedule = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<numDays;i++){
            newSchedule.add(new HashMap<String,String>());
        }
        
        // for each day, generate random
        for(int i = 0; i < numDays; i++){

            ArrayList<String> namesCopy = new ArrayList<String>();
            for(String name : names){
                namesCopy.add(name);
            }
            
            for(String job : jobs){

                if(namesCopy.size() == 0){
                    newSchedule.get(i).put(job,"None");
                    break;
                }
                
                int nameIdx = (int) (Math.random() * namesCopy.size());

                newSchedule.get(i).put(job,namesCopy.get(nameIdx));
                namesCopy.remove(nameIdx);
                
            }
            
            
        }

        return newSchedule;
    }

}
