import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import java.util.ArrayList;

import javax.swing.JPanel;

public class Printer implements Printable {

    private ArrayList<JPanel> sheets;

    public Printer(ArrayList<JPanel> panels){
        this.sheets = panels;
    }
    
    public int print(Graphics graphics,PageFormat pageFormat, int pageIndex){

        if(pageIndex > sheets.size()-1){
            return Printable.NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) graphics;

        g2d.scale(0.75,0.75);

        
        sheets.get(pageIndex).printAll(graphics);
        

        return Printable.PAGE_EXISTS;
    }

    public void printSchedule(){
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);

        job.setJobName("Media Team Schedule");

        if(job.printDialog()){
            try {
                job.print();
            } catch (PrinterException e){
                e.printStackTrace();
            }
        }
    }
}
