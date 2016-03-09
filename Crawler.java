import java.io.*;
import java.net.FileNameMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Umar on 07-Mar-16.
 */
public class Crawler {

    HashMap<String, List<String>> index;

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Crawler c = new Crawler();
        try {
            c.load("F:\\Movies");
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            String q = s.nextLine();
            List<String> lv = c.index.get(q);

            if (lv != null) {
                for (int o = 0; o < lv.size(); o++)
                    System.out.println(lv.get(o));
            }
            System.out.print("Enter your key word: ");
        }
    }

    public boolean load(String dr) throws IOException {
        File fr2 = new File(dr);

        if (fr2.exists() & fr2.isDirectory()) {                // checking if the file folder exists then search
            System.out.println("Indexing... Please wait");
            index = new HashMap<>();
            // calling finder to search for name passed in argument and the directory
            if (finder(fr2)) {
                System.out.println("Indexing Complete. Enter your key word");
                return true;
            }

        } else
            System.out.println("Directory path is corrupt");
        return false;
    }

    public boolean finder(File cnt) throws IOException {

        File[] names = cnt.listFiles();                // storing all the files in the file array
        File cnt1;
        int flag = 0;
        if (names == null) {                            // if directory is empty return from it
            return false;
        }
        for (int i = 0; i < names.length; i++) {
            cnt1 = names[i];
            if (cnt1.isDirectory()) {
                finder(cnt1);                    // going into inner directory
            }
            if (cnt1.isFile()) {
                // First store file name and splits on spaces and extension
                List<String> lc;
                String s = cnt1.getName();
                String g = cnt1.getAbsolutePath();
                String dot[] = s.split("\\.(?=[^\\.]+$)");
               // System.out.println(s);
             //   System.out.println(dot.length);
                // store extension
                lc = index.get(dot[dot.length - 1]);
                if (lc != null) {
                    lc.add(g);
                } else {
                    List<String> kc = new ArrayList<>();
                    kc.add(g);
                    index.put(dot[dot.length - 1], kc);
                }
                String f[];
                if (dot.length>1)
                 f= dot[0].split("\\s+");
                else
                    f=s.split("\\s+");;
                // store space splits names
                for (int k = 0; k < f.length; k++) {
                    lc = index.get(f[k]);
                    if (lc != null) {
                        lc.add(g);
                    } else {
                        List<String> kc = new ArrayList<>();
                        kc.add(g);
                        index.put(f[k], kc);
                    }
                }

                lc = index.get(s);
                if (lc != null) {
                    lc.add(g);
                } else {
                    List<String> kc = new ArrayList<>();
                    kc.add(g);
                    index.put(s, kc);
                }
                //index.put(getExtension(s),g);

                if (getExtension(s).equalsIgnoreCase("txt")) {        // if name matches delete the file
                    //String g=cnt1.getAbsolutePath();
                    FileReader fr = new FileReader(g);
                    BufferedReader tr = new BufferedReader(fr);
                    String sCurrentLine;
                    while ((sCurrentLine = tr.readLine()) != null) {

                        String p[] = sCurrentLine.split("\\s+");
                        //System.out.println(p.length);
                        for (int k = 0; k < p.length; k++) {
                            lc = index.get(p[k]);
                            if (lc != null) {
                                lc.add(g);
                            } else {
                                List<String> kc = new ArrayList<>();
                                kc.add(g);
                                index.put(p[k], kc);
                            }
                            //index.put(p[k],g);
                        }
                    }
                    // now put file name and extension also
//                    for (int k=0;k<f.length;k++) {
//                        index.put(f[k],g);
//                    }
//                    index.put(s,g);
//                    index.put(getExtension(s),g);

                    flag = 1;
                } else {
                    //  String g=cnt1.getAbsolutePath();
//                    for (int k=0;k<f.length;k++) {
//                        index.put(f[k],g);
//                    }
//                    index.put(s,g);
//                    index.put(getExtension(s),g);
                    flag = 1;
                }

            }
        }
        if (flag == 1)
            return true;
        else
            return false;
    }

    public static String getExtension(String filename) {
        if (filename == null) {
            return null;
        }
        int extensionPos = filename.lastIndexOf('.');
        int lastUnixPos = filename.lastIndexOf('/');
        int lastWindowsPos = filename.lastIndexOf('\\');
        int lastSeparator = Math.max(lastUnixPos, lastWindowsPos);

        int index = lastSeparator > extensionPos ? -1 : extensionPos;
        if (index == -1) {
            return "";
        } else {
            return filename.substring(index + 1);
        }
    }

}
