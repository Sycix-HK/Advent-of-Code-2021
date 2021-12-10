
//////////////////////////////////////////////////////////////
/////////////////////|      Day 10      |/////////////////////
//////////////////////////////////////////////////////////////

package submarine.repairing;

import submarine.core.*;
import java.io.*;
import java.util.*;

public class NavigationSyntax {
    public static void main(String[] args) {
        System.out.println("Syntax corruption score: " + syntaxErrorScore(DataTray.getInput(10),ScoreType.CORRUPT));
        System.out.println("Syntax completion score: " + syntaxErrorScore(DataTray.getInput(10),ScoreType.INCOMPLETE));
    }

    public enum ScoreType {
        CORRUPT, INCOMPLETE
    }

    public static Long syntaxErrorScore(File file, ScoreType scoreType)
    {
        int score = 0;
        ArrayList<Long> lineScores = new ArrayList<>(); 
        for (String line : new InputScannerString(file).getResult())
        {
            Stack<Character> stack = new Stack<>();
            String openings = "([{<";
            String closings = ")]}>";
            int isClosing=0;
            int i = 0;
            while (i != line.length() && 
                (openings.indexOf(line.charAt(i)) != -1 || 
                closings.indexOf(line.charAt(i)) == openings.indexOf(stack.pop()) && ++isClosing == 1
            ))
            {
                if (isClosing == 1) isClosing = 0;
                else stack.push(line.charAt(i));
                i++;
            }
            if (i != line.length() && scoreType == ScoreType.CORRUPT){
                switch (line.charAt(i)) {
                    case ')': score += 3; break;
                    case ']': score += 57; break;
                    case '}': score += 1197; break;
                    case '>': score += 25137; break;
                    default: throw new IllegalArgumentException("This syntax is VERY wrong. The hell this?: " + line.charAt(i));
                }
            }
            else if (i == line.length() && scoreType == ScoreType.INCOMPLETE)
            {
                Long lineScore = 0l;
                while (!stack.isEmpty())
                {
                    lineScore *= 5;
                    lineScore += 1 + openings.indexOf(stack.pop());
                }
                lineScores.add(lineScore);
            }
        }

        Collections.sort(lineScores);

        return scoreType == ScoreType.CORRUPT ? 
            score :
            lineScores.get(lineScores.size()/2);
    }
}
