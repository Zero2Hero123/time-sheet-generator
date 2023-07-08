import java.util.ArrayList;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

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

        try {
            UIManager.setLookAndFeel(UIManager.getInstalledLookAndFeels()[0].getClassName());
        } catch (ClassNotFoundException e){
            System.out.println("not found");
        } catch(InstantiationException e){
            System.out.println("cant be inst");
        } catch (IllegalAccessException e){
            System.out.println("cant be accessed");
        } catch (UnsupportedLookAndFeelException e){
            System.out.println("unsupported");
        }

        // LookAndFeelInfo selected = null;
        // for (LookAndFeelInfo lafInfo : UIManager.getInstalledLookAndFeels()) {
        //     System.out.println(lafInfo.getName());
        //     if (lafInfo.getName().equals(UIManager.getLookAndFeel().getName())) {
        //         selected = lafInfo;
        //     }
        // }
        // System.out.print("Selected UIManager: ");
        // System.out.println(selected.getName());

        new Window();

        
    }
}