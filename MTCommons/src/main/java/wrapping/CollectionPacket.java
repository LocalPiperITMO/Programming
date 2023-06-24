package wrapping;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class CollectionPacket<T extends Serializable> implements Serializable {
    public enum PacketIdentifier {
        COMMAND_INFO,
        COLLECTION,
        FUEL_TYPE_COLLECTION
    }

    private final PacketIdentifier packetIdentifier;
    private final LinkedHashMap<Integer, T> collection = new LinkedHashMap<>();

    public CollectionPacket(PacketIdentifier packetIdentifier, LinkedHashMap<Integer, ? extends T> collection) {
        this.packetIdentifier = packetIdentifier;
        this.collection.putAll(collection);
    }

    public LinkedHashMap<Integer, T> getCollection() {
        return collection;
    }

    public PacketIdentifier getPacketIdentifier() {
        return packetIdentifier;
    }
}
