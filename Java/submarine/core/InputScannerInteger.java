package submarine.core;

import java.io.*;
import java.util.*;

public class InputScannerInteger {
    private ArrayList<Integer> result = new ArrayList<Integer>();

    public InputScannerInteger(File input){
        try (Scanner in = new Scanner((new FileReader(input)))) {
            while (in.hasNext()) {
                result.add(Integer.parseInt(in.nextLine()));
            }
        }
        catch (Exception e)
        {
            System.err.println(e);
        }
    }

    public ArrayList<Integer> getResult() { return result; }
}