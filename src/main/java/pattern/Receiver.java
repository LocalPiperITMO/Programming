package pattern;

import converters.ObjectVectorToStringListConverter;
import datatype.Vehicle;
import exceptions.EmptyDatasetException;
import exceptions.NoPreviousSortingException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import static app.App.creationDate;
import static converters.CSVtoStringListConverter.varAddress;
import static converters.StringListToObjectVectorConverter.generator;

public class Receiver {
    Vector<Vehicle> dataSet;
    public static String sortingParameter;

    public Receiver(Vector<Vehicle> dataSet) {
        this.dataSet = dataSet;
    }

    public void show() {
        try {
            if (dataSet.size() == 0) {
                throw new EmptyDatasetException();
            }
            System.out.println("ID Name CreationDate X Y EnginePower FuelConsumption Type FuelType");
            for (Vehicle vehicle : dataSet) {
                System.out.println(vehicle.toString());
            }
        } catch (EmptyDatasetException noData) {
            System.out.println("Dataset is empty: nothing to show");
        }
    }

    public void info() {
        System.out.println("Dataset type: " + dataSet.getClass().getName() + "\nDataset creation date: " + creationDate
                + "\nDataset number of elements: " + this.dataSet.size());
    }

    public void help() {
        System.out.println("""
                Here is the list of supported commands:
                help : displays this message
                info : displays the information about the dataset (type of dataset, creation date, number of elements)
                show : displays every element of the dataset
                add : adds element to dataset
                update id : updates the element with the given ID
                remove_by_id id : removes an element with the given ID
                clear : clears the dataset
                save : save the dataset to file
                exit : leaves the program
                add_if_max : if the element is greater that the greatest element in the dataset, it is added to dataset
                remove_greater : removes all the elements from the dataset greater than the given one
                reorder : displays every element of the dataset in reverse order of the current sorting
                filter_by_fuel_consumption fuelConsumption : displays elements with the given fuelConsumption
                print_ascending : displays every element sorted by ID
                print_field_ascending_fuel_type : displays only ID and FuelType of every element, sorted by FuelType
                """);
    }

    public void printAscending() {
        try {
            if (dataSet.size() == 0) {
                throw new EmptyDatasetException();
            }
            sortingParameter = "ID";
            Collections.sort(dataSet);
            show();
        } catch (EmptyDatasetException noData) {
            System.out.println("Dataset is empty: nothing to sort");
        }
    }

    public void printFieldAscendingFuelType() {
        try {
            if (dataSet.size() == 0) {
                throw new EmptyDatasetException();
            }
            sortingParameter = "FuelType";
            Collections.sort(dataSet);
            System.out.println("ID FuelType");
            for (Vehicle vehicle : dataSet) {
                System.out.println(vehicle.getId() + " " + vehicle.getFuelType());
            }
        } catch (EmptyDatasetException noData) {
            System.out.println("Dataset is empty: nothing to sort");
        }
    }

    public void reorder() {
        try {
            if (dataSet.size() == 0) {
                throw new EmptyDatasetException();
            }
            if (sortingParameter == null) {
                throw new NoPreviousSortingException();
            }
            Collections.reverse(dataSet);
            show();

        } catch (NoPreviousSortingException noSort) {
            System.out.println("No previous sorting detected. Switching to default mod: sorting by ID");
            sortingParameter = "ID";
            reorder();
        } catch (EmptyDatasetException noData) {
            System.out.println("Dataset is empty: nothing to sort");
        }
    }

    public void clear() {
        dataSet.removeAllElements();
        System.out.println("Collection has been emptied");
    }

    public void save() throws IOException {
        ObjectVectorToStringListConverter converter = new ObjectVectorToStringListConverter(dataSet);
        List<String> strings = converter.convertObjectVectorToStringList();
        new FileOutputStream(varAddress).close();
        try (FileOutputStream writer = new FileOutputStream(varAddress, true)) {
            for (String stringLine : strings) {
                byte[] buffer = stringLine.getBytes();
                writer.write(buffer, 0, buffer.length);
                writer.write(10);
            }
            System.out.println("File saved successfully.");
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }

    public void removeByID(int id) {
        boolean isFound = false;
        for (Vehicle vehicle : dataSet) {
            isFound = (vehicle.getId() == id);
            if (isFound) {
                dataSet.remove(vehicle);
                System.out.println("Object deleted successfully");
                break;
            }
        }
        if (!isFound) {
            System.out.println("There is no object by this ID.");
        }
    }

    public void filterByFuelConsumption(long fuelConsumption) {
        Vector<Vehicle> filteredDataSet = new Vector<>();
        for (Vehicle vehicle : dataSet) {
            if (fuelConsumption == vehicle.getFuelConsumption()) {
                filteredDataSet.add(vehicle);
            }
        }
        if (filteredDataSet.size() > 0) {
            System.out.println("ID Name CreationDate X Y EnginePower FuelConsumption Type FuelType");
            for (Vehicle vehicle : filteredDataSet) {
                System.out.println(vehicle.toString());
            }
        }
    }

    public void add() throws IOException {
        generator.consecutiveInputMode(true);
        dataSet.add(generator.createOrUpdateObjectByUserInput(null));
        System.out.println("Object added successfully");
    }

    public void update(int id) throws IOException {
        boolean isFound = false;
        for (Vehicle vehicle : dataSet) {
            if (id == vehicle.getId()) {
                isFound = true;
                generator.consecutiveInputMode(true);
                generator.createOrUpdateObjectByUserInput(vehicle);
                System.out.println("Object by ID " + id + " updated successfully");
                break;
            }

        }
        if (!isFound) {
            System.out.println("Object with the given ID does not exist.");
        }
    }

    public void addIfMaxElement() throws IOException {
        generator.consecutiveInputMode(true);
        Vehicle elementToCompare = generator.createOrUpdateObjectByUserInput(null);
        sortingParameter = "Numeric";
        Collections.sort(dataSet);
        if (elementToCompare.compareTo(dataSet.lastElement()) > 0) {
            dataSet.add(elementToCompare);
            System.out.println("New element added successfully");
        } else {
            System.out.println("New element has not been added: element with ID " + dataSet.lastElement().getId() + " is greater");
        }
    }

    public void removeGreaterElements() throws IOException {
        generator.consecutiveInputMode(true);
        Vehicle elementToCompare = generator.createOrUpdateObjectByUserInput(null);
        sortingParameter = "Numeric";
        dataSet.removeIf(vehicle -> elementToCompare.compareTo(vehicle) < 0);
        System.out.println("Done");
    }
}
