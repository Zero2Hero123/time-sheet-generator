import java.util.ArrayList;

public class Main {

    public static void main(String[] args){

        var arr = new ArrayList<String>();
        arr.add("Hero");
        arr.add("Joshua");
        arr.add("Ruth");
        arr.add("Oserame");

        var jobs = new ArrayList<String>();
        jobs.add("Audio Mixer");
        jobs.add("Camera");
        jobs.add("Video Switcher");
        jobs.add("Propresenter");
        jobs.add("Vmix");

        var gen = new Schedule(arr,jobs);

        System.out.println(gen.generate(2));
    }
}