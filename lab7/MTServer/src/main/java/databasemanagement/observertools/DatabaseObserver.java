package databasemanagement.observertools;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public record DatabaseObserver<T>(ConcurrentHashMap<Integer, T> map) implements Consumer<DatabaseEvent<T>> {

    @Override
    public void accept(DatabaseEvent<T> event) {
        switch (event.type()) {
            case ADD -> map.put(event.index(), event.element());
            case UPDATE -> map.replace(event.index(), event.element());
            case DELETE -> map.remove(event.index());
        }
    }

}
