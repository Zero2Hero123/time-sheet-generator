import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Chance {

    private LinkedList<Integer> percentageList = new LinkedList<Integer>();

    private LinkedList<String> percentageListStr = new LinkedList<String>();

    public Chance(){

    }
    public Chance(int percentage){

        for(int i = 0; i < percentage; i++){
            percentageList.add(1);
        }
        for(int i = 0; i < 100 - percentage; i++){
            percentageList.add(0);
        }
    }

    public String rollStr(HashMap<String,Integer> percentages,ArrayList<String> jobsInclude){

        
        for(String k : percentages.keySet()){
            if(jobsInclude.contains(k)){
                for(int i=0;i<percentages.get(k);i++){
                    percentageListStr.add(k);
                }
            }
        }

        if(percentageListStr.size() == 0){
            return "None";
        }

        int randIdx = (int) (Math.random() * percentageListStr.size());

        return percentageListStr.get(randIdx);
    }

    public boolean roll(){
        int randIdx = (int) (Math.random() * 100);

        if(percentageList.get(randIdx) == 1){
            return true;
        } else {
            return false;
        }
    }



}
