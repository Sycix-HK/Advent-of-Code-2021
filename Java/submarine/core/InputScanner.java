package submarine.core;

import java.io.*;
import java.util.*;

public class InputScanner {
    private ArrayList<String> result = new ArrayList<>();

    public InputScanner(File input){
        try (Scanner in = new Scanner((new FileReader(input)))) {
            while (in.hasNext()) {
                result.add(in.nextLine());
            }
        }
        catch (Exception e)
        {
            System.err.println(e);
        }
    }

    public ArrayList<String> getResult() { return result; }
}