package submarine.equipment.diagnostics.powerConsumption;

import submarine.core.DataTray;

public class ConsumptionCalculatorMain {
    public static void main(String[] args) {
        System.out.println(new ConsumptionCalculator(DataTray.getInput(3)).toString());
    }
}
