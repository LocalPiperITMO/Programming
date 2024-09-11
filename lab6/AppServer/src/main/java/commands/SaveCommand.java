package commands;

import collection_handling.CollectionSaver;

import java.io.Serializable;

public class SaveCommand implements Command{
    private final String fileName;
    public SaveCommand(String fileName){
        this.fileName = fileName;
    }

    @Override
    public Serializable execute() {
        CollectionSaver collectionSaver = new CollectionSaver();
        return collectionSaver.save(fileName);
    }
}
