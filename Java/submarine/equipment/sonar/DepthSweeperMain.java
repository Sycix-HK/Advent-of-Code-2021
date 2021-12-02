package submarine.equipment.radar;

import java.io.*;

public class DepthSweeperMain {
    public static void main(String[] args) {
        File input = new File("data/Day 01/input.txt");

        System.out.println("Simple scan: "+ DepthSweeperSimple.Sweep(input));
        System.out.println("Denoised scan: "+ DepthSweeperAdvanced.Sweep(input));
    }
}
