package packets;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class CollectionPacket<T extends Serializable> implements Serializable {
    public enum PacketIdentifier {
        COMMAND_INFO,
        COLLECTION,
        FUEL_TYPE_COLLECTION
    }

    private final PacketIdentifier packetIdentifier;
    private final List<T> collection;

    public CollectionPacket(PacketIdentifier packetIdentifier, Collection<? extends T> collection) {
        this.packetIdentifier = packetIdentifier;
        this.collection = List.copyOf(collection);
    }

    public List<T> getCollection() {
        return collection;
    }

    public PacketIdentifier getPacketIdentifier() {
        return packetIdentifier;
    }
}
