
//////////////////////////////////////////////////////////////
/////////////////////|      Day 08      |/////////////////////
//////////////////////////////////////////////////////////////

package submarine.repairing;

import submarine.core.*;
import java.io.*;
import java.util.*;

public class SevenSegment {
    public static void main(String[] args) {
        TimeMeasure timer = new TimeMeasure();
        Logger.print(timer, "Unique numbers in output", uniqueNumbersInOutput(DataTray.getInput(8)));
        Logger.print(timer, "Sum of decoded output", sumOfDecodedDigitsInOutput(DataTray.getInput(8)));
    }

    public static int uniqueNumbersInOutput(File file)
    {
        ArrayList<String> lines = new InputScanner(file).getResult();
        int c = 0;
        for (String line : lines) {
            String[] digits = line.split(" ");
            for (int i = 14; i > 10; i--) {
                switch (digits[i].length())                
                {
                    case 2,3,4,7:
                        c++;
                        break;
                    default: break;
                }
            }
        }
        return c;
    }

    public static int sumOfDecodedDigitsInOutput(File file)
    {
        ArrayList<String> lines = new InputScanner(file).getResult();
        int c = 0;
        for (String line : lines) {
            DigitPattern pattern = new DigitPattern(line);
            String[] digits = line.split(" ");
            char[] outputNumber = new char[] {'x','x','x','x'};
            for (int i = 0; i < 4; i++) {
                outputNumber[Math.abs(3-i)] = pattern.decodeDigit(digits[14-i]);
            }
            c += Integer.parseInt( new String( outputNumber ) );
        }
        return c;
    }
}

class DigitPattern
{
    Character[] code; /*
        000
       1   2
        333
       4   5
        666            */
    public DigitPattern(String readings)
    {
        code = decode(readings);
    }

    public Character decodeDigit(String stringCode)
    {
        switch (stringCode.length()) {
            case 2: return '1';
            case 3: return '7';
            case 4: return '4';
            case 5: return stringCode.indexOf(code[1]) != -1 ? '5' : stringCode.indexOf(code[4]) != -1 ? '2' : '3'; 
            case 6: return stringCode.indexOf(code[2]) == -1 ? '6' : stringCode.indexOf(code[3]) == -1 ? '0' : '9'; 
            case 7: return '8';
            default: throw new IllegalArgumentException();
        }
    }

    private static Character[] decode(String readings)
    {
        Character[] outpcode = new Character[] {'0','0','0','0','0','0','0'};
        String[] nums = new String[10];
        List<String> _fives = new ArrayList<>();
        List<String> _sixes = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            String[] digits = readings.split(" ");
            String digit = digits[i];
            switch (digit.length())
            {
                case 2: nums[1]  = digit;  break;
                case 3: nums[7]  = digit;  break;
                case 4: nums[4]  = digit;  break;
                case 5: _fives.add(digit); break;
                case 6: _sixes.add(digit); break;
                case 7: nums[8]  = digit;  break;
                default: break;
            }
        }

        // 1 -> code0
        int i = 0;
        while (nums[1].indexOf(nums[7].charAt(i)) != -1) i++;
        outpcode[0] = nums[7].charAt(i);

        // 1 -> 6
        i = 0;
        while (((_sixes.get(i).indexOf(nums[1].charAt(0)) != -1) && (_sixes.get(i).indexOf(nums[1].charAt(1)) != -1))) i++;
        nums[6] = _sixes.get(i);
        _sixes.remove(i);

        // 1 -> 3
        i = 0;
        while (_fives.get(i).indexOf(nums[1].charAt(0)) == -1 || _fives.get(i).indexOf(nums[1].charAt(1)) == -1) i++;
        nums[3] = _fives.get(i);
        _fives.remove(i);

        // 3 -> 0,9,code3
        int zerosindex = -1;
        for (int s = 0; s < 2; s++)
        {
            for (int zi = 0; zi < 5 && zerosindex == -1; zi++)
            {
                if (_sixes.get(s).indexOf(nums[3].charAt(zi)) == -1)
                {
                    zerosindex=s;
                    outpcode[3] = nums[3].charAt(zi);
                }
            }
        }
        nums[0] = _sixes.get(zerosindex);
        nums[9] = _sixes.get(Math.abs(zerosindex - 1));

        // 9 -> code4
        i = 0;
        while (nums[9].indexOf(nums[8].charAt(i)) != -1) i++;
        outpcode[4] = nums[8].charAt(i);

        // code4 -> 2,5
        nums[2] = _fives.get(0).indexOf(outpcode[4]) == -1 ? _fives.get(1) : _fives.get(0);
        nums[5] = _fives.get(1).indexOf(outpcode[4]) == -1 ? _fives.get(1) : _fives.get(0);

        // 6 -> code2
        i = 0;
        while (nums[6].indexOf(nums[8].charAt(i)) != -1) i++;
        outpcode[2] = nums[8].charAt(i);

        // 1 -> code5
        outpcode[5] = nums[1].charAt(0) == outpcode[2] ? nums[1].charAt(1) : nums[1].charAt(0);

        // 4 -> code1
        i = 0;
        while (nums[4].charAt(i) == outpcode[2] || nums[4].charAt(i) == outpcode[3] || nums[4].charAt(i) == outpcode[5]) i++;
        outpcode[1] = nums[4].charAt(i);

        // 3 -> code6
        i = 0;
        while (nums[3].charAt(i) == outpcode[0] || nums[3].charAt(i) == outpcode[2] || nums[3].charAt(i) == outpcode[3] || nums[3].charAt(i) == outpcode[5]) i++;
        outpcode[6] = nums[3].charAt(i);

        return outpcode;
    }
}
