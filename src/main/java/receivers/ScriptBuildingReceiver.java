package receivers;

import datatype.Vehicle;
import exceptions.LessOrEqualToZeroException;
import exceptions.NoArgumentException;
import exceptions.ScriptBuildingException;
import generators.IDGenerator;

import java.util.List;

public class ScriptBuildingReceiver {
    private final IDGenerator idGenerator;
    private final TextReceiver textReceiver;

    public ScriptBuildingReceiver(IDGenerator idGenerator, TextReceiver textReceiver) {
        this.idGenerator = idGenerator;
        this.textReceiver = textReceiver;
    }

    private void throwError(String errorMessage) {
        textReceiver.print(errorMessage);
    }

    private Vehicle build(List<String> vehicleProperties) throws NoArgumentException, LessOrEqualToZeroException {
        Vehicle currentVehicle = new Vehicle();
        currentVehicle.setName(vehicleProperties.get(0))
                .getCoordinates()
                .setX(vehicleProperties.get(1))
                .setY(vehicleProperties.get(2));
        currentVehicle.setEnginePower(vehicleProperties.get(3))
                .setFuelConsumption(vehicleProperties.get(4))
                .setType(vehicleProperties.get(5))
                .setFuelType(vehicleProperties.get(6));
        currentVehicle.setId(idGenerator.generateRandomID());
        return currentVehicle;
    }

    public Vehicle buildOrThrowError(List<String> vehicleProperties) throws ScriptBuildingException {
        try {
            return build(vehicleProperties);
        } catch (NoArgumentException noArgumentException) {
            throwError("An argument is missing");
        } catch (LessOrEqualToZeroException lessOrEqualToZeroException) {
            throwError("An argument does not match the given condition");
        }
        throw new ScriptBuildingException();
    }
}
