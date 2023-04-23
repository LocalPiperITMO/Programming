package generators;

import java.util.HashSet;

/**
 * Generator class<br>
 * Used in element building
 */
public class IDGenerator {

    private final HashSet<Integer> idSet = new HashSet<>();

    /**
     * @return set of unique IDs
     */
    public HashSet<Integer> getIdSet() {
        return idSet;
    }

    /**
     * Used to generate unique ID
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
