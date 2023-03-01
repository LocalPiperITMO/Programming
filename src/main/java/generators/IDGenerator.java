package generators;

import java.util.HashSet;

public class IDGenerator {
    private final HashSet<Integer> idSet = new HashSet<>();

    public int generateRandomID() {
        boolean isGenerated = false;
        int newId = 0;
        while (!isGenerated) {
            newId = (int) (Math.random() * 100000);
            if (!idSet.contains(newId)) {
                idSet.add(newId);
                isGenerated = true;
            }
        }
        return newId;
    }
}
