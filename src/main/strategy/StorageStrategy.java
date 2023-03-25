package main.strategy;

public interface StorageStrategy {
    boolean containsKey(Long key);          //return true if key exist in storage
    boolean containsValue(String value);    //return true if value exist in storage
    void put(Long key, String value);       //put new pair key-value
    Long getKey(String value);              //return key
    String getValue(Long key);              //return value
}
