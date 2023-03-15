package commands;

import datatype.Vehicle;
import exceptions.InvalidArgumentsWhileVehicleBuildingViaScriptException;

import java.io.IOException;

public class AddElementCommand implements Command {
    private final Receiver receiver;
    private final Invoker invoker;

    public AddElementCommand(Receiver receiver, Invoker invoker) {
        this.receiver = receiver;
        this.invoker = invoker;
    }

    public String showInfo() {
        return "No argument required. User builds an element. Adds element to dataset";
    }

    public void execute(String arg, boolean isCalledByScript) throws IOException, InvalidArgumentsWhileVehicleBuildingViaScriptException {
        Vehicle vehicle;
        if (isCalledByScript) {
            vehicle = buildVehicleViaScript(invoker.getListOfArgumentsForBuildingViaScript());
        } else {
            vehicle = buildVehicleViaUserInput((new Vehicle()));
        }
        vehicle.setId(receiver.idGenerator().generateRandomID());
        receiver.dataSet().add(vehicle);
        System.out.println("Vehicle added successfully!");
    }
}
