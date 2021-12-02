package submarine.equipment.propeller;

import java.io.File;

public class PathCalculationMain {
    public static void main(String[] args) {
        File input = new File("data/Day 02/input.txt");
        PathCalculation path = new PathCalculation(input);
        path.evaulateSimple();
        System.out.println("Simple calculations: ");
        System.out.println("Horizontal: "+ path.getHorizontal() + ", Depth: " + path.getDepth());
        System.out.println("Horizontal * depth = " + path.getHorizontal() * path.getDepth());
        path.resetPosition();
        path.evaulateAdvanced();
        System.out.println("Advanced calculations: ");
        System.out.println("Horizontal: "+ path.getHorizontal() + ", Depth: " + path.getDepth());
        System.out.println("Horizontal * depth = " + path.getHorizontal() * path.getDepth());
    }
}
