package databasemanagement.observertools;

public abstract class AbstractDatabase<T> {

    public abstract boolean insertElement(T element);

    public abstract boolean updateElement(T element, int index);

    public abstract boolean deleteElement(T element, int index);
}
