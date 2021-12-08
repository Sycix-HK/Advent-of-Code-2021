
//////////////////////////////////////////////////////////////
/////////////////////|      Day 08      |/////////////////////
//////////////////////////////////////////////////////////////

package submarine.emergency;

import submarine.core.*;
import java.io.*;
import java.util.*;

public class SevenSegment {
    public static void main(String[] args) {
        System.out.println("Unique numbers in output: " + uniqueNumbersInOutput(DataTray.getInput(8)));
    }

    public static int uniqueNumbersInOutput(File file)
    {
        ArrayList<String> lines = new InputScannerString(file).getResult();
        int c = 0;
        for (String line : lines) {
            String[] digits = line.split(" ");
            for (int i = 14; i > 10; i--) {
                switch (digits[i].length())                {
                    case 2:
                    case 3:
                    case 4:
                    case 7:
                        c++;
                    default: break;
                }
            }
        }
        return c;
    }
}
