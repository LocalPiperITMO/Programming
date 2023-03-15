package commands;

import datatype.Vehicle;
import exceptions.InvalidArgumentsWhileVehicleBuildingViaScriptException;

import java.io.IOException;

public class RemoveGreaterElementsCommand implements Command {
    private final Receiver receiver;
    private final Invoker invoker;

    public RemoveGreaterElementsCommand(Receiver receiver, Invoker invoker) {
        this.receiver = receiver;
        this.invoker = invoker;
    }

    public String showInfo() {
        return "No argument required. User builds an element. Removes all the elements from the dataset greater than the given one";
    }

    public void execute(String arg, boolean isCalledByScript) throws IOException, InvalidArgumentsWhileVehicleBuildingViaScriptException {
        Vehicle vehicle;
        if (isCalledByScript) {
            vehicle = buildVehicleViaScript(invoker.getListOfArgumentsForBuildingViaScript());
        } else {
            vehicle = buildVehicleViaUserInput((new Vehicle()));
        }
        receiver.dataSet().removeIf(vehicleToCompare -> vehicleToCompare.getSum() > vehicle.getSum());
        System.out.println("Done");
    }
}