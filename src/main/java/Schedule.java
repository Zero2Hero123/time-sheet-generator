import java.util.ArrayList;
import java.util.HashMap;


public class Schedule {

    private ArrayList<String> names;
    private ArrayList<String> jobs;

    public Schedule(ArrayList<String> names, ArrayList<String> jobs){
        this.names = names;
        this.jobs = jobs;
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
