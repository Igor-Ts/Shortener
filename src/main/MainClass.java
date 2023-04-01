package main;

import main.strategy.FileStorageStrategy;
import main.strategy.HashMapStorageStrategy;
import main.strategy.OurHashMapStorageStrategy;
import main.strategy.StorageStrategy;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

public class MainClass {

    public static void main(String[] args) {
        testStrategy(new HashMapStorageStrategy(),10000);
        testStrategy(new OurHashMapStorageStrategy(),10000);
        testStrategy(new FileStorageStrategy(),100L);
    }

    public static Set<Long> getIds(Shortener shortener, Set<String> strings) {      //return id set for given string set
        Iterator<String> iterator = strings.iterator();
        Set <Long> ids = new HashSet<>();
        while (iterator.hasNext()){
            ids.add(shortener.getId(iterator.next()));
        }
        return ids;
    }

    public static Set<String> getStrings(Shortener shortener, Set<Long> keys) {     //return string set for given id set
        return keys.stream()
                .map(shortener::getString)
                .collect(Collectors.toSet());
    }

    // test case
    public static void testStrategy(StorageStrategy strategy, long elementsNumber) {
        Helper.printMessage(strategy.getClass().getSimpleName());                               // strategy name
        Set <String> stringHashSet = new HashSet<>();                                           // randomly generated string set
        for (int i = 0; i < elementsNumber; i++) {                                              // string generator
            stringHashSet.add(Helper.generateRandomString());
        }
        Shortener shortener = new Shortener(strategy);                                          // create new strategy

        Long startIds = System.currentTimeMillis();                                             // time start
        Set <Long> id = getIds(shortener, stringHashSet);                                       // id set
        Long endIds = System.currentTimeMillis();                                               // time end
        Long timeDuratonIds = endIds - startIds;                                                // method time duration
        Helper.printMessage(timeDuratonIds + " millis ids method");

        Long startString = System.currentTimeMillis();
        Set <String> string = getStrings(shortener, id);                                         // string set given by previous id's set
        Long endString = System.currentTimeMillis();
        Long timeDuratonString = endString - startString;
        Helper.printMessage(timeDuratonString + " millis strings method");

        Helper.printMessage(stringHashSet.equals(string) ? "Test done" : "Test failed");        // test result (strings some or not)
    }
}
