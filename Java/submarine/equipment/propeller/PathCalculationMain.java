package submarine.equipment.propeller;

import submarine.core.DataTray;

public class PathCalculationMain {
    public static void main(String[] args) {
        PathCalculation path = new PathCalculation(DataTray.getInput(2));
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
