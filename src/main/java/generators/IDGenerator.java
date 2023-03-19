package generators;

import java.util.HashSet;

/**
 * Generator class
 * Used in element building
 */
public class IDGenerator {
    private final HashSet<Integer> idSet = new HashSet<>();

    /**
     * Used to generate unique ID. Remembers all the IDs
     *
     * @return ID
     */
    public int generateRandomID() {
        boolean isGenerated = false;
        int newId = 0;
        while (!isGenerated) {
            newId = (int) (Math.random() * 1000000);
            if (!idSet.contains(newId)) {
                idSet.add(newId);
                isGenerated = true;
            }
        }
        return newId;
    }
}
