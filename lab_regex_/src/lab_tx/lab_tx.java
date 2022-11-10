package lab_tx;
import java.io.*;
import java.util.Scanner;
import java.lang.String;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileFilter;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Vector;
public class lab_tx {
    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader("in.txt");
        Scanner sc = new Scanner(fr);
        String text=sc.nextLine();
        fr.close();
        FileWriter fw=new FileWriter("out.txt");
        fw.write(text);
        fw.write('\n');
        fw.write("all numbers");
        fw.write('\n');
        String[] words = text.split("[\\s\\,\\;\\:\\!]");
     /*   Pattern pattern = Pattern.compile("((\\+||\\-)?\\d*?[_\\d]*?(\\.?\\d*)?(e[\\+\\-][0-9])?([fl])?)");
        for (String word : words) {
           Matcher matcher = pattern.matcher(word);
            while (matcher.find()) {
                System.out.println(matcher.group(1));
            }
        }*/
        double i;
        double sum=0;
        int x;
        for (String word : words) {
           // System.out.println(word);
            if(word!="") {
                if (word.matches("((\\+||\\-)?\\d*?[_\\d]*?(\\.?\\d*)?([Ee]\\d*)?([Ee][\\+\\-][0-9])?([FfLl])?)") == true)
                {
                    //  System.out.pri
                    //  ntln(word);
                    fw.write(word);
                    fw.write('\n');
                    if (word.matches("(\\d*l)")) {
                        i = Double.parseDouble(word.replace("l", ""));
                    }
                    else if(word.matches("(\\d*L)")){
                        i = Double.parseDouble(word.replace("L", ""));
                    }
                        else {
                        i = Double.parseDouble(word.replace("_", ""));
                    }
                    sum=sum+i;
                }
                else if(word.matches("0[bB][01]+")==true){
                    fw.write(word);
                    fw.write('\n');
                    x = (int)Long.parseLong(word.substring(2,word.length()), 2);
                    sum=sum+x;
                }
                else if(word.matches("0[xX][0-9A-Fa-f]+")==true){
                    fw.write(word);
                    fw.write('\n');
                    x = (int)Long.parseLong(word.substring(2,word.length()), 16);
                    sum=sum+x;
                }
                else if(word.matches("0[0-7]")==true){
                    fw.write(word);
                    fw.write('\n');
                    x = (int)Long.parseLong(word, 8);
                    sum=sum+x;
                }

            }
        }
        //System.out.println("sum of all numbers: "+sum);
        fw.write("sum of all numbers: "+sum);
      fw.close();
    }

}
