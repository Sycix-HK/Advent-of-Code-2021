package submarine.equipment.sonar;

import submarine.core.DataTray;

public class DepthSweeperMain {
    public static void main(String[] args) {
        System.out.println("Simple scan: "+ DepthSweeperSimple.Sweep(DataTray.getInput(1)));
        System.out.println("Denoised scan: "+ DepthSweeperAdvanced.Sweep(DataTray.getInput(1)));
    }
}
