package databasemanagement.observertools;

public record DatabaseEvent<T>(DatabaseEventType type, T element, int index) {
}
