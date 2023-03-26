package main;

import main.strategy.StorageStrategy;

public class Shortener {

    private Long lastId = 0L;                               //last id value which was used to add string in storage
    private StorageStrategy storageStrategy;                //current storage strategy

    public Shortener(StorageStrategy storageStrategy) {
        this.storageStrategy = storageStrategy;
    }

    public synchronized long getId(String string) {         //gives string id
        if (storageStrategy.containsValue(string)) {        // check storage
            return storageStrategy.getKey(string);
        } else {                                            //add new pair in storage
            lastId ++;
            storageStrategy.put(lastId,string);
            return lastId;
        }
    }

    public synchronized String getString(Long id) {         //gives string or null
        return storageStrategy.getValue(id);
    }


}
