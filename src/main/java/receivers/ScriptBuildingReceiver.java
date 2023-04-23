package receivers;

import datatype.Vehicle;
import exceptions.LessOrEqualToZeroException;
import exceptions.NoArgumentException;
import exceptions.ScriptBuildingException;
import generators.IDGenerator;

import java.util.List;

/**
 * Building receiver<br>
 * Builds objects using script lines
 */
public class ScriptBuildingReceiver {
    private final IDGenerator idGenerator;
    private final TextReceiver textReceiver;

    /**
     * Gets IDGenerator for building purposes and text receiver in order to output an error if one occurs
     *
     * @param idGenerator  generates random ID
     * @param textReceiver outputs errors
     */
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

    /**
     * Gets list of arguments, then builds vehicle using these arguments<br>
     * If building goes wrong, throws error and terminates building process
     *
     * @param vehicleProperties list of arguments for building
     * @return built element
     * @throws ScriptBuildingException if an error occurred while building
     */
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
