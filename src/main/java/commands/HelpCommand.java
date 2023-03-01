package commands;

import pattern.Command;

public class HelpCommand implements Command {
    public void execute(String arg) {
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
}
