package submarine.core;

import java.io.*;
import java.util.*;

public class InputScanner<T> {
    private List<T> result;
    
    @SuppressWarnings("unchecked")
    public InputScanner(File input){
        try (Scanner in = new Scanner((new FileReader(input)))) {
            while (in.hasNext()) {result.add((T)in.nextLine());}
        }
        catch (Exception e){}
    }
    public List<T> getResult() { return result; }
}
