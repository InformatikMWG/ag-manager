import java.io.*;

public class convert {
    public static void con(String path){
        try {
            FileReader reading = new FileReader(path);
            BufferedReader read = new BufferedReader(reading);
            PrintWriter writeout = new PrintWriter("output.csv","UTF-8");
            Checker check = new Checker();
            while(true){
                String line= read.readLine();
                String[] zeile = line.split("\t");// \t represents a tabstopp
                String outputline = new String();
                outputline += zeile[0].charAt(0);

                String[] nachnamensbestandteile= zeile[1].split(" ");
                int nrBestandteile = nachnamensbestandteile.length;
                outputline += nachnamensbestandteile[nrBestandteile - 1].charAt(0);

                int randomPIN = (int)(Math.random()*9000)+1000;
                outputline += randomPIN;
                
                while(check.add(outputline)){
                    outputline = new String();
                    outputline += zeile[0].charAt(0);
                    outputline += zeile[1].charAt(0);
                    randomPIN = (int)(Math.random()*9000)+1000;
                    outputline += randomPIN;
                }
                line+="\t";
                line+=outputline;
                writeout.println(line);
                writeout.flush();
            }
        }
        catch(Exception e){}
    }
    
    public static void main(String[] args) {
        con(args[0]);
    }

}
