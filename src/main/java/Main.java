import java.util.ArrayList;
import java.awt.Font;
import javax.swing.JLabel;

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

        // var gen = new Schedule(arr,jobs);

        // System.out.println(gen.generate(2));

        var window = new Window();
        var title = new JLabel("Time Sheet Generator");
        title.setFont(new Font("Helvetica",Font.PLAIN,30));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.TOP);
        title.setBounds(0,0,900,100);
        window.add(title);
    }
}