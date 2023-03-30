package main.strategy;

import main.ExceptionHandler;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileBucket {

    private Path path;                                                              //path to file

    public FileBucket() {
        try {
            path = Files.createTempFile("FileBucket_", ".tmp");         //create temp file
            Files.deleteIfExists(path);
            Files.createFile(path);
            path.toFile().deleteOnExit();
        } catch (Exception e) {
            ExceptionHandler.log(e);
        }



    }
    //return path file size
    public long getFileSize () {
        long size = 0;
        try {
            size = Files.size(path);
        } catch (Exception e) {
            ExceptionHandler.log(e);
        }
        return size;
    }

    // entry serialization
    public void putEntry(Entry entry) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(path)) ;
            outputStream.writeObject(entry);
        } catch (Exception e){
            ExceptionHandler.log(e);
        }

    }

    //get Entry from file
    public Entry getEntry() {
        if (getFileSize() == 0) {
            return null;
        }
        Entry entry = null;
        try(ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(path))) {
            entry = (Entry) inputStream.readObject();
        } catch (Exception e) {
            ExceptionHandler.log(e);
        }
        return entry;
    }

    public void remove() {
        try {
            Files.delete(path);
        } catch (Exception e) {
            ExceptionHandler.log(e);
        }

    }
}
