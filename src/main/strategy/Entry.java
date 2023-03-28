package main.strategy;

import java.io.Serializable;
import java.util.Objects;

public class Entry implements Serializable {
    Long key;
    String value;
    Entry next;
    int hash;

    public Entry(int hash, Long key, String value, Entry next) {
        this.hash = hash;
        this.key = key;
        this.value = value;
        this.next = next;
    }

    public Long getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public int hashCode() {                                                             //Generates a hash code for a sequence of input values.
        return Objects.hash(key, value);
    }

    @Override
    public boolean equals(Object o) {                                                   // comparing
        if (this == o) {                                                                // check object
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {                             // check null value and comparing classes
            return false;
        }
        Entry entry = (Entry) o;
        return (Objects.equals(key,entry.key)) &&                                       // check key and values
                Objects.equals(value,entry.value);
    }

    @Override
    public String toString() {
        return key + " = " + value;
    }
}
