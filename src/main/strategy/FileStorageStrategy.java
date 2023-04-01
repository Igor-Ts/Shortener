package main.strategy;

import java.util.Objects;
import java.util.stream.IntStream;

public class FileStorageStrategy implements StorageStrategy{

    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final long DEFAULT_BUCKET_SIZE_LIMIT = 10000;
    private FileBucket[] table = new FileBucket[DEFAULT_INITIAL_CAPACITY];
    private int size;
    private long bucketSizeLimit = DEFAULT_BUCKET_SIZE_LIMIT;
    private long maxBucketSize;

    public FileStorageStrategy() {
        for (int i = 0; i < table.length; i++) {
            table[i] = new FileBucket();
        }
    }
    public int hash(int h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    public int indexFor(int hash, int length) {
        return hash & (length - 1);
    }

    public Entry getEntry(Long key) {
        int hash = (key == null) ? 0 : hash(key.hashCode());
        for (Entry e = table[indexFor(hash, table.length)].getEntry();
             e != null;
             e = e.next) {
            Long eKey = e.key;
            if (e.hash == hash && eKey.equals(key))
                return e;
        }
        return null;
    }

    public void resize(int newCapacity) {
       // FileBucket[] newTable = new FileBucket[newCapacity];
        FileBucket[] newTable = IntStream
                .range(0, newCapacity)
                .mapToObj(i -> new FileBucket())
                .toArray(FileBucket[]::new);
        transfer(newTable);
        table = newTable;
        maxBucketSize = 0;
        for (FileBucket bucket :table) {
            final long currentBucketSize = bucket.getFileSize();
            maxBucketSize = Math.max(currentBucketSize, maxBucketSize);
        }
    }

    public void transfer(FileBucket[] newTable) {
        for (FileBucket bucket: table) {
            Entry e = bucket.getEntry();
            while (e != null) {
                Entry next = e.next;
                int i = indexFor(e.hash, newTable.length);
                e.next = newTable[i].getEntry();
                newTable[i].putEntry(e);
                e = next;
            }
            bucket.remove();
        }
    }

    public void addEntry(int hash, Long key, String value, int bucketIndex) {
        Entry e = table[bucketIndex].getEntry();
        table[bucketIndex].putEntry(new Entry(hash, key, value, e));
        size++;
        long currentBucketSize = table[bucketIndex].getFileSize();
        maxBucketSize = Math.max(currentBucketSize, maxBucketSize);
        if (maxBucketSize > bucketSizeLimit) resize(2 * table.length);

    }

    public void createEntry(int hash, Long key, String value, int bucketIndex) {
        table[bucketIndex].putEntry( new Entry (hash, key, value, null));
        size++;
        final long currentBucketSize = table[bucketIndex].getFileSize();
        maxBucketSize = Math.max(currentBucketSize, maxBucketSize);
    }


    @Override
    public boolean containsKey(Long key) {
        return getEntry(key) != null;
    }

    @Override
    public boolean containsValue(String value) {
        for (FileBucket bucket : table){
            for (Entry e = bucket.getEntry() ; e != null ; e = e.next){
                if (value.equals(e.value))
                    return true;
            }
        }
        return false;
    }

    @Override
    public void put(Long key, String value) {
        int hash = (key == null) ? 0 : hash(key.hashCode());
        int i = indexFor(hash, table.length);

        if (table[i].getEntry() != null) {
            for (Entry e = table[i].getEntry(); e != null; e = e.next) {
                if (e.hash == hash &&
                        (Objects.equals(e.key, key))) {
                    e.value = value;
                    return;
                }
            }
            addEntry(hash, key, value, i);
        } else {
            createEntry(hash, key, value, i);
        }
    }

    @Override
    public Long getKey(String value) {
        for (FileBucket bucket: table) {
            for (Entry e = bucket.getEntry(); e != null; e = e.next) {
                if (Objects.equals(e.value, value))
                    return e.key;
            }
        }
        return null;
    }

    @Override
    public String getValue(Long key) {
        Entry e = getEntry(key);
        return e == null ? null: e.value;
    }
}
