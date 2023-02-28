package converters;

import datatype.Vehicle;
import generators.ObjectGenerator;
import validators.ConversionValidator;

import java.util.List;
import java.util.Vector;

public class StringListToObjectVectorConverter {
    List<String[]> text;
    Vector<Vehicle> dataSet;
    ConversionValidator validator;
    public static ObjectGenerator generator = new ObjectGenerator();

    public StringListToObjectVectorConverter(List<String[]> text) {
        this.dataSet = new Vector<>();
        this.text = text;
        this.validator = new ConversionValidator();


    }

    public Vector<Vehicle> convertStringListToObjectVector() {
        int corruptedLines = 0;
        int lineCounter = 0;
        generator.consecutiveInputMode(false);
        for (String[] line : this.text) {
            ++lineCounter;
            Vehicle vehicle = generator.createObjectByData(line, lineCounter);
            if (vehicle == null) {
                ++corruptedLines;
            } else {
                dataSet.add(vehicle);
            }
        }
        System.out.println("Dataset is ready for use. Number of corrupted lines: " + corruptedLines);
        return dataSet;
    }
}
