package receivers;

import collection.CollectionStorage;
import commands.Command;
import datatype.Vehicle;
import exceptions.EmptyDatasetException;
import user.Invoker;

import java.time.LocalDate;
import java.util.Map;

/**
 * Receiver class<br>
 * Stores realization for displaying commands (such as 'help', 'show' and 'info')
 */
public class DisplayingCommandReceiver {

    private final CollectionStorage storage;

    private final Invoker invoker;

    /**
     * Gets collection and invoker
     * @param storage contains the collection
     * @param invoker contains command map
     */
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
                No argument required                           : after inputting command name one DOES NOT need to input an argument.
                Required argument - argumentName(argumentType) : after inputting command name press SPACE BAR and then print the value of an argument of the required type.
                                
                The command names and their descriptions are as follows:
                """);
        for (Map.Entry<String, Command> set : commandMap.entrySet()) {
            report.append(String.format("%-31s" + " : " + "%s\n", set.getKey(), set.getValue().showInfo()));
        }
        return String.valueOf(report);
    }

    /**
     * 'info' command realization
     *
     * @return command execution report (sent to TextReceiver)
     */
    public String info() {
        return String.format("%-26s : " + storage.getDataSet().getClass().getSimpleName() +
                "\n%-26s : " + LocalDate.now() +
                "\n%-26s : " + storage.getDataSet().size(), "Dataset type", "Dataset creation date", "Dataset number of elements");
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
            StringBuilder report = new StringBuilder(String.format("%7s " + "%-20s " + "%12s " + "%15s " + "%11s " + "%15s " + "%15s " + "%-10s " + "%-10s\n",
                    "ID",
                    "Name",
                    "CreationDate",
                    "X",
                    "Y",
                    "EnginePower",
                    "FuelConsumption",
                    "Type",
                    "FuelType"));
            for (Vehicle vehicle : storage.getDataSet()) {
                report.append(vehicle.toString()).append("\n");
            }
            return String.valueOf(report);
        } catch (EmptyDatasetException noData) {
            return "Dataset is empty: nothing to show";
        }
    }
}