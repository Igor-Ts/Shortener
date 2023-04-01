package main.strategy;

import java.util.HashMap;

public class OurHashBiMapStorageStrategy implements StorageStrategy {

    private HashMap<Long, String> k2v = new HashMap<>();
    private HashMap<String, Long> v2k = new HashMap<>();

    @Override
    public boolean containsKey(Long key) {
        return k2v.containsKey(key);
    }

    @Override
    public boolean containsValue(String value) {
        return v2k.containsValue(value);
    }

    @Override
    public void put(Long key, String value) {
        k2v.put(key,value);
        v2k.put(value,key);
    }

    // return value from v2k
    @Override
    public Long getKey(String value) {
        return v2k.get(value);
    }

    // return value from k2v
    @Override
    public String getValue(Long key) {
        return k2v.get(key);
    }
}
