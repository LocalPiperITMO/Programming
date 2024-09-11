package databasemanagement.observertools;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class DatabaseObservable<T> {
    private final CopyOnWriteArrayList<Consumer<DatabaseEvent<T>>> observers = new CopyOnWriteArrayList<>();

    public CopyOnWriteArrayList<Consumer<DatabaseEvent<T>>> getObservers() {
        return observers;
    }

    public void attach(Consumer<DatabaseEvent<T>> observer) {
        observers.add(observer);
    }

    public void detach(Consumer<DatabaseEvent<T>> observer) {
        observers.remove(observer);
    }

    public void notifyObservers(DatabaseEvent<T> event) {
        for (Consumer<DatabaseEvent<T>> observer : observers) {
            observer.accept(event);
        }
    }
}
