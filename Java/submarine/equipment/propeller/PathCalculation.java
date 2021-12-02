package submarine.equipment.propeller;

import java.io.File;
import java.util.*;

enum Direction {
    forward,
    up,down
}

public class PathCalculation {
    private int depth = 0;
    private int horizontal = 0;
    private int aim = 0;
    private ArrayList<Instruction> instructions = new ArrayList<>();

    public int getDepth() {return depth; }
    public int getHorizontal() {return horizontal; }

    public PathCalculation(File file) {
        ArrayList<String> data = new submarine.core.InputScannerString(file).getResult();

        for (String line : data) {
            String[] splitline = line.trim().split(" ");
            instructions.add(new Instruction(
                Direction.valueOf(splitline[0]),
                Integer.parseInt(splitline[1])
            ));
        }
    }

    public void evaulateSimple()
    {
        for (Instruction ins : instructions) {
            switch (ins.dir)
            {
                case forward:
                    horizontal += ins.units;
                    break;
                case up:
                    depth -= ins.units;
                    break;
                case down:
                    depth += ins.units;
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
    }

    public void evaulateAdvanced()
    {
        for (Instruction ins : instructions) {
            switch (ins.dir)
            {
                case forward:
                    horizontal += ins.units;
                    depth += aim*ins.units;
                    break;
                case up:
                    aim -= ins.units;
                    break;
                case down:
                    aim += ins.units;
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
    }

    public void resetPosition()
    {
        depth = 0;
        horizontal = 0;
        aim = 0;
    }
}


class Instruction {
    public Direction dir;
    public int units;

    public Instruction(Direction dir, int units) {
        this.dir = dir;
        this.units = units;
    }
}