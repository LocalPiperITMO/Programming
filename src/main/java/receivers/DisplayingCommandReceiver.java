package receivers;

import collection.CollectionStorage;
import commands.Command;
import user.Invoker;
import datatype.Vehicle;
import exceptions.EmptyDatasetException;

import java.time.LocalDate;
import java.util.Map;

public class DisplayingCommandReceiver {
    private final CollectionStorage storage;
    private final Invoker invoker;

    public DisplayingCommandReceiver(CollectionStorage storage, Invoker invoker) {
        this.storage = storage;
        this.invoker = invoker;
    }

    public void help() {
        Map<String, Command> commandMap = invoker.getCommandHashMap();
        System.out.println("""
                The conventions are as follows:
                No argument required : after inputting command name one DOES NOT need to input an argument.
                Required argument - argumentName(argumentType) : after inputting command name press SPACE BAR and then print the value of an argument of the required type
                User builds an element : after inputting command name and pressing ENTER the user is welcomed by VehicleBuilder
                                
                The command names and their descriptions are as follows:""");
        for (Map.Entry<String, Command> set : commandMap.entrySet()) {
            System.out.println(set.getKey() + " : " + set.getValue().showInfo());
        }
    }

    public void info() {
        System.out.println("Dataset type: " + storage.getDataSet().getClass().getSimpleName() + "\nDataset creation date: " + LocalDate.now()
                + "\nDataset number of elements: " + storage.getDataSet().size());
    }

    public void show() {
        try {
            if (storage.getDataSet().size() == 0) {
                throw new EmptyDatasetException();
            }
            System.out.println("ID Name CreationDate X Y EnginePower FuelConsumption Type FuelType");
            for (Vehicle vehicle : storage.getDataSet()) {
                System.out.println(vehicle.toString());
            }
        } catch (EmptyDatasetException noData) {
            System.out.println("Dataset is empty: nothing to show");
        }
    }

}