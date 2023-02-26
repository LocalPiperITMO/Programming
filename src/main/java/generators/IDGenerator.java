package generators;

import java.util.HashSet;

public class IDGenerator {
    HashSet<Integer> idSet = new HashSet<>();

    public int generateRandomID() {
        boolean isGenerated = false;
        int newId = 0;
        while (!isGenerated) {
            newId = (int) (Math.random() * 100000);
            if (!idSet.contains(newId)) {
                isGenerated = true;
            }
        }
        return newId;
    }
}
