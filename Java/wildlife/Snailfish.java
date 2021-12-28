
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
        Homework hw = new Homework(DataTray.getTest(18));
        Logger.print(timer, "Magnitude of the homework", getMagnitude(hw));
    }

    public static long getMagnitude(Homework hw) {
        Tuple t = Tuple.add(hw.numbers.get(0),hw.numbers.get(1));
        return 5L;
    }
}

class Homework
{
    public List<Tuple> numbers;
    
    public Homework(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            numbers = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null)
            {
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
                            stack.peekLast().put(Character.getNumericValue(line.charAt(i)));
                            break;
                            
                    }
                }
                numbers.add(outer.close());
            }
        }
        catch (Exception e) {Logger.error(e);}
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
}

class Tuple
{
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
        process(t);
        return t;
    }

    public static void process(Tuple target)
    {
        Tuple exp = findExplosion(0, target);
        if (exp != null)
        {
            explode(exp);
            process(target);
        }
        else if (false)
        {
            //split();
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

    public static void explode(Tuple t)
    {
        System.out.println(t);
    }

    public static void split(Tuple t)
    {

    }
}
