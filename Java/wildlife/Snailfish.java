
//////////////////////////////////////////////////////////////
/////////////////////|      Day 18      |/////////////////////
//////////////////////////////////////////////////////////////

package wildlife;

import submarine.core.*;
import java.io.*;
import java.util.*;

public class Snailfish {
    public static void main(String[] args) {
        TimeMeasure timer = new TimeMeasure();
        Homework hw = new Homework(DataTray.getInput(18));
        Logger.print(timer, "Magnitude of the homework", getFinalMagnitude(hw));
        Logger.print(timer, "Largest possible addition", findLargestAddition(hw));
    }

    public static long getFinalMagnitude(Homework hw) {
        Tuple main = hw.read(0);
        for (int i = 1; i < hw.getSize(); i++) {
            main = Tuple.add(main,hw.read(i));
        }
        return main.getMagnitude();
    }

    public static long findLargestAddition(Homework hw)
    {
        long maxval = 0;
        for (int i = 0; i < hw.getSize(); i++)
        {
            for (int j = 0; j < hw.getSize(); j++)
            {
                if (i == j) break;
                long val = Tuple.add(hw.read(i),hw.read(j)).getMagnitude();
                if (val > maxval)
                {
                    maxval = val;
                }
            }
        }
        return maxval;
    }
}

class Homework
{
    private List<String> originalNumbers;

    public int getSize()
    {
        return originalNumbers.size();
    }

    public Tuple read(int i)
    {
        return readNumber(originalNumbers.get(i));
    }
    
    public Homework(File file) {
        originalNumbers = new InputScanner(file).getResult();
    }

    private static Tuple readNumber(String line)
    {
        Tuple prev = null;
        Deque<TupleInitializer> stack = new ArrayDeque<>();
        TupleInitializer outer = new TupleInitializer();
        stack.add(outer);
        for (int i = 1; i < line.length()-1; i++)
        {
            switch (line.charAt(i))
            {
                case '[':
                    stack.addLast(new TupleInitializer());
                    break;
                case ',':
                    stack.peekLast().shiftCursor();
                    break;
                case ']':
                    TupleInitializer tp = stack.pollLast();
                    stack.peekLast().put(tp.close());
                    break;
                default: // number
                    Tuple simple = new Tuple(Character.getNumericValue(line.charAt(i)));
                    stack.peekLast().put(simple);
                    if (prev != null)
                    {
                        TupleInitializer.link(prev, simple);
                    }
                    prev = simple;
                    break;
            }
        }
        return outer.close();
    }
}

class TupleInitializer
{
    private Tuple main = new Tuple();
    private int cursor = 0;

    public void shiftCursor()
    {
        cursor++;
    }

    public void put(int value)
    {
        main.values[cursor] = new Tuple(value);
    }

    public void put(Tuple duovalue)
    {
        main.values[cursor] = duovalue;
    }

    public Tuple close()
    {
        if (cursor != 1) throw new IllegalStateException("Wrong tuple size: " + cursor);
        return main;
    }

    public static void link(Tuple left, Tuple right)
    {
        if (left != null)
        {
            left.neighbors[1] = right;
        }
        if (right != null)
        {
            right.neighbors[0] = left;
        }
    }
}

class Tuple
{
    Tuple[] neighbors = new Tuple[] {null, null};
    Tuple[] values;
    boolean duo;
    int intvalue;

    public Tuple()
    {
        values = new Tuple[2];
        duo = true;
    }

    public Tuple(int value)
    {
        this.intvalue = value;
        duo = false;
    }

    public Tuple(Tuple left, Tuple right)
    {
        values = new Tuple[] {left, right};
        duo = true;
    }

    public static Tuple add(Tuple left, Tuple right)
    {
        Tuple t = new Tuple(left, right);
        TupleInitializer.link(Tuple.rightMostOf(left), Tuple.leftMostOf(right));
        process(t);
        return t;
    }

    public static Tuple rightMostOf(Tuple target)
    {
        return (target.duo) ? rightMostOf(target.values[1]) : target;
    }
    public static Tuple leftMostOf(Tuple target)
    {
        return (target.duo) ? leftMostOf(target.values[0]) : target;
    }

    public static void process(Tuple target)
    {
        Tuple exp = findExplosion(0, target);
        if (exp != null)
        {
            explode(exp);
            process(target);
            return;
        }
        Tuple spl = findSplit(target);
        if (spl != null)
        {
            split(spl);
            process(target);
        }
    }

    public static Tuple findExplosion(int depth, Tuple node)
    {
        if (!node.duo) return null;
        if (depth == 3)
        {
            if (node.values[0].duo)
            {
                return node.values[0];
            }
            if (node.values[1].duo)
            {
                return node.values[1];
            }
            return null;
        }
        Tuple left = findExplosion(depth+1, node.values[0]);
        if (left != null)
        {
            return left;
        }
        return findExplosion(depth+1, node.values[1]);
    }

    public static Tuple findSplit(Tuple node)
    {
        if (!node.duo) return node.intvalue > 9 ? node : null;
        Tuple left = findSplit(node.values[0]);
        if (left != null)
        {
            return left;
        }
        return findSplit(node.values[1]);
    }

    public static void explode(Tuple t)
    {

        Tuple[] neighb = new Tuple[] {t.values[0].neighbors[0],t.values[1].neighbors[1]};

        if (neighb[0] != null)
        {
            neighb[0].intvalue += t.values[0].intvalue;
        }
        if (neighb[1] != null)
        {
            neighb[1].intvalue += t.values[1].intvalue;
        }
        t.values = null;
        t.duo = false;
        t.intvalue = 0;
        TupleInitializer.link(neighb[0],t);
        TupleInitializer.link(t,neighb[1]);
    }

    public static void split(Tuple t)
    {
        t.duo = true;
        t.values = t.intvalue % 2 == 0 ? 
            new Tuple[]{new Tuple(t.intvalue/2),        new Tuple(t.intvalue/2)} : 
            new Tuple[]{new Tuple((t.intvalue-1)/2),    new Tuple(((t.intvalue-1)/2)+1)
        };
        TupleInitializer.link(t.neighbors[0],t.values[0]);
        TupleInitializer.link(t.values[0],t.values[1]);
        TupleInitializer.link(t.values[1],t.neighbors[1]);
        t.neighbors = new Tuple[]{null,null};
    }

    public long getMagnitude()
    {
        return duo ? (values[0].getMagnitude()*3) + (values[1].getMagnitude()*2) : intvalue ;
    }
}
