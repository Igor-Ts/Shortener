package main.tests;

import main.Helper;
import main.Shortener;
import main.strategy.HashBiMapStorageStrategy;
import main.strategy.HashMapStorageStrategy;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class SpeedTest {

    @Test
    public void testHashMapStorage(){
        Shortener hashMapStorageStrategy = new Shortener(new HashMapStorageStrategy());
        Shortener hashBiMapStorageStrategy = new Shortener(new HashBiMapStorageStrategy());
        Set<String> origStrings = new HashSet<>();
        for (int i = 0; i < 10000; i++) {
            origStrings.add(Helper.generateRandomString());
        }
        Set<Long> id = new HashSet<>();

        long timeHashMap = getTimeForGettingIds(hashMapStorageStrategy,origStrings, id);
        long timeBiHashMap = getTimeForGettingIds(hashBiMapStorageStrategy,origStrings, id);

        Assert.assertTrue(timeHashMap > timeBiHashMap);

        Set<String> strings = new HashSet<>();

        long timeHashMapString = getTimeForGettingStrings(hashMapStorageStrategy,id, strings);
        long timeBiHashMapString = getTimeForGettingStrings(hashBiMapStorageStrategy,id, strings);
        Assert.assertEquals(timeHashMapString, timeBiHashMapString, 30f);
    }

    public long getTimeForGettingIds(Shortener shortener, Set<String> strings, Set<Long> ids) {
        Long startIds = System.currentTimeMillis();                                             // time start
        for (String string : strings) {
            ids.add(shortener.getId(string));
        }
        Long endIds = System.currentTimeMillis();                                               // time end
        return endIds - startIds;
    }

    public long getTimeForGettingStrings(Shortener shortener, Set<Long> ids, Set<String> strings) {
        Long startString = System.currentTimeMillis();
        for (Long id: ids) {
            strings.add(shortener.getString(id));
        }
        Long endString = System.currentTimeMillis();
        return endString - startString;
    }
}
