package submarine.equipment.depthSweeper;

import java.io.*;

public class DepthSweeperMain {
    public static void main(String[] args) {
        File input = new File(args[0]);

        System.out.println("Simple scan: "+ simple.Sweep(input));
        System.out.println("Denoised scan: "+ advanced.Sweep(input));
    }
}
