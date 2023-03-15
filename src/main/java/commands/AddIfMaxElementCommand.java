package commands;

import datatype.Vehicle;
import exceptions.InvalidArgumentsWhileVehicleBuildingViaScriptException;

import java.io.IOException;
import java.util.Collections;

public class AddIfMaxElementCommand implements Command {
    private final Receiver receiver;
    private final Invoker invoker;

    /**
     * "add_if_max" command
     * Creates a new element, asking user for arguments in the process.
     * Created element then compared with the greatest element in the collection (compared using sums of integer arguments, excluding ID)
     * If the created element is greater than the greatest element, it is added to the collection.
     *
     * @param receiver used for storing the collection
     * @param invoker  used for storing arguments for building (used only via "execute_script" command)
     */
    public AddIfMaxElementCommand(Receiver receiver, Invoker invoker) {
        this.receiver = receiver;
        this.invoker = invoker;
    }

    /**
     * Used for showing information.
     *
     * @return information about the command
     */
    public String showInfo() {
        return "No argument required. User builds an element. If the element is greater that the greatest element in the dataset, it is added to dataset";
    }

    /**
     * Executes command
     *
     * @param arg              command argument
     * @param isCalledByScript checks if command called from script
     * @throws IOException                                            if unexpected error occurs
     * @throws InvalidArgumentsWhileVehicleBuildingViaScriptException if invalid arguments given for building vehicle via script
     */
    public void execute(String arg, boolean isCalledByScript) throws IOException, InvalidArgumentsWhileVehicleBuildingViaScriptException {
        Vehicle vehicle;
        if (isCalledByScript) {
            vehicle = buildVehicleViaScript(invoker.getListOfArgumentsForBuildingViaScript());
        } else {
            vehicle = buildVehicleViaUserInput((new Vehicle()));
        }
        vehicle.setId(receiver.idGenerator().generateRandomID());
        int index = 0;
        do {
            if (receiver.dataSet().get(index).getSum() > receiver.dataSet().get(index + 1).getSum()) {
                Collections.swap(receiver.dataSet(), index, index + 1);
                index = 0;
            } else {
                ++index;
            }
        } while (index != receiver.dataSet().size() - 1);
        if (vehicle.compareTo(receiver.dataSet().lastElement()) > 0) {
            receiver.dataSet().add(vehicle);
            System.out.println("New element added successfully");
        } else {
            System.out.println("New element has not been added: element with ID " + receiver.dataSet().lastElement().getId() + " is greater");
        }
    }
}