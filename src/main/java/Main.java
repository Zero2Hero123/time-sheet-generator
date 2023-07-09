
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.io.File;

public class Main {

    public static void main(String[] args){
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