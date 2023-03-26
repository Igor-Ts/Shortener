package main.strategy;

import java.util.HashMap;
import java.util.Map;

public class HashMapStorageStrategy implements StorageStrategy {

    private HashMap<Long, String> data;                                 //storage

    @Override
    public boolean containsKey(final Long key) {
        return data.containsKey(key);
    }

    @Override
    public boolean containsValue(final String value) {
        return data.containsValue(value);
    }

    @Override
    public void put(final Long key,final String value) {
        data.put(key,value);
    }

    @Override
    public Long getKey(final String value) {
        for (Map.Entry<Long, String> map: data.entrySet()) {
            if (value.equals(map.getValue())){
                return map.getKey();
            }
        }
        return null;
    }

    @Override
    public String getValue(final Long key) {
        return data.get(key);
    }
}
