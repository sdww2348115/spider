package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sdww on 2016/4/28.
 */
public class FileUtil {

    private final static String filename = "zhaopin";

    public static void write(String txt) {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(filename, true));
            out.write(txt);
            out.close();
        } catch (Exception e) {
            System.out.print(e.getStackTrace());
        }
    }

    public static List<String> readLines() {
        List<String> result = new LinkedList<String>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            String s;
            while((s = bufferedReader.readLine()) != null) {
                result.add(s);
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.print(e.getStackTrace());
        }
        return result;
    }

    public static void main(String[] args) {
//        FileUtil.write("hello world\ntest");
//        List<String> lines = FileUtil.readLines();
//        for(String line:lines) {
//            System.out.println(line);
//        }
    }
}
