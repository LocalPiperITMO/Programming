package receivers;

import collection.CollectionStorage;
import commands.Command;
import datatype.Vehicle;
import exceptions.EmptyDatasetException;
import user.Invoker;

import java.time.LocalDate;
import java.util.Map;

/**
 * Receiver class
 * Stores realization for displaying commands (such as 'help', 'show' and 'info')
 */
public class DisplayingCommandReceiver {
    /**
     * Stores Vehicle vector
     */
    private final CollectionStorage storage;
    /**
     * Stores Invoker with the commandHashMap (used by 'help' command)
     */
    private final Invoker invoker;

    public DisplayingCommandReceiver(CollectionStorage storage, Invoker invoker) {
        this.storage = storage;
        this.invoker = invoker;
    }
    /**
     * 'help' command realization
     *
     * @return command execution report (sent to TextReceiver)
     */
    public String help() {
        Map<String, Command> commandMap = invoker.getCommandHashMap();
        StringBuilder report = new StringBuilder("""
                The conventions are as follows:
                No argument required : after inputting command name one DOES NOT need to input an argument.
                Required argument - argumentName(argumentType) : after inputting command name press SPACE BAR and then print the value of an argument of the required type
                User builds an element : after inputting command name and pressing ENTER the user is welcomed by VehicleBuilder
                                
                The command names and their descriptions are as follows:
                """);
        for (Map.Entry<String, Command> set : commandMap.entrySet()) {
            report.append(set.getKey()).append(" : ").append(set.getValue().showInfo()).append("\n");
        }
        return String.valueOf(report);
    }
    /**
     * 'info' command realization
     *
     * @return command execution report (sent to TextReceiver)
     */
    public String info() {
        return "Dataset type: " + storage.getDataSet().getClass().getSimpleName() + "\nDataset creation date: " + LocalDate.now()
                + "\nDataset number of elements: " + storage.getDataSet().size();
    }
    /**
     * 'show' command realization
     *
     * @return command execution report (sent to TextReceiver)
     */
    public String show() {
        try {
            if (storage.getDataSet().size() == 0) {
                throw new EmptyDatasetException();
            }
            StringBuilder report = new StringBuilder("ID Name CreationDate X Y EnginePower FuelConsumption Type FuelType\n");
            for (Vehicle vehicle : storage.getDataSet()) {
                report.append(vehicle.toString()).append("\n");
            }
            return String.valueOf(report);
        } catch (EmptyDatasetException noData) {
            return "Dataset is empty: nothing to show";
        }
    }
}