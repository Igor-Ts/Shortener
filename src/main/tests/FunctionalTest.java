package main.tests;

import main.Shortener;
import main.strategy.*;
import org.junit.Assert;
import org.junit.Test;

public class FunctionalTest {

    public void testStorage(Shortener shortener) {
        String text1 = "From Estonia with love";
        String text2 = "I'm god";
        String text3 = "From Estonia with love";

        Long id1 = shortener.getId(text1);
        Long id2 = shortener.getId(text2);
        Long id3 = shortener.getId(text3);

        Assert.assertNotEquals(text1, text2);
        Assert.assertNotEquals(text3, text2);

        Assert.assertEquals(text1, text3);

        String getText1 = shortener.getString(id1);
        String getText2 = shortener.getString(id2);
        String getText3 = shortener.getString(id3);

        Assert.assertEquals(text1, getText1);
        Assert.assertEquals(text2, getText2);
        Assert.assertEquals(text3, getText3);


    }

    @Test
    public void testHashMapStorageStrategy() {
        testStorage(new Shortener(new HashMapStorageStrategy()));
    }

    @Test
    public void testOurHashMapStorageStrategy() {
        testStorage(new Shortener(new OurHashMapStorageStrategy()));
    }

    @Test
    public void testFileStorageStrategy() {
        testStorage(new Shortener(new FileStorageStrategy()));
    }

    @Test
    public void testHashBiMapStorageStrategy() {
        testStorage(new Shortener(new HashBiMapStorageStrategy()));
    }

    @Test
    public void testDualHashBidiMapStorageStrategy() {
        testStorage(new Shortener(new DualHashBidiMapStorageStrategy()));
    }

    @Test
    public void testOurHashBiMapStorageStrategy() {
        testStorage(new Shortener(new OurHashBiMapStorageStrategy()));
    }
}
