import java.io.*;
public class convert
{
    public static void con(String path){
        try{
            FileReader reading = new FileReader(path);
            BufferedReader read = new BufferedReader(reading);
            PrintWriter writeout = new PrintWriter("output.csv","UTF-8");
            Checker check = new Checker();
            while(true){
                String line= read.readLine();
                String[] zeile = line.split(",");
                String outputline = new String();
                outputline += zeile[0].charAt(0);
                outputline += zeile[1].charAt(0);
                int randomPIN = (int)(Math.random()*9000)+1000;
                outputline += randomPIN;
                while(check.add(outputline)){
                    outputline = new String();
                    outputline += zeile[0].charAt(0);
                    outputline += zeile[1].charAt(0);
                    randomPIN = (int)(Math.random()*9000)+1000;
                    outputline += randomPIN;
                }
                line+=",";
                line+=outputline;
                writeout.println(line);
            }
        }
        catch(Exception e){}
    }
}
