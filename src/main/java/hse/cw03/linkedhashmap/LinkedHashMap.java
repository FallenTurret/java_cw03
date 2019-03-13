package hse.cw03.linkedhashmap;

import java.util.*;

public class LinkedHashMap<K, V> extends AbstractMap<K, V> implements Map<K, V> {

    public class ListElement {
        public ListElement next = null;
        public ListElement prev = null;
        public MyEntry<K, V> data;
        ListElement(K key, V value) {
            data = new MyEntry(key, value);
        }
    }

    private ListElement head = null;
    private ListElement tail = null;
    private int size = 0;
    private final int START_MOD = (int)1e3;
    private int mod = START_MOD;
    private List<K, V>[] buckets;

    private class MapSet extends AbstractSet implements Set {

        @Override
        public Iterator iterator() {
            MapIterator iterator = new MapIterator();
            iterator.head = head;
            return iterator;
        }

        @Override
        public int size() {
            return size;
        }
    }

    private class MapIterator implements Iterator<MyEntry<K, V>> {

        private ListElement head;

        @Override
        public boolean hasNext() {
            return (head != null);
        }

        @Override
        public MyEntry<K, V> next() {
            var tmp = head.data;
            head = head.next;
            return tmp;
        }
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        var set = new MapSet();
        return set;
    }

    public LinkedHashMap() {
        buckets = new List[mod];
        for (int i = 0; i < mod; i++) {
            buckets[i] = new List();
        }
    }

    private int getIndex(Object key) {
        return ((key.hashCode() % mod) + mod) % mod;
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        var hash = getIndex(key);
        var tmp = buckets[hash].getValue(key);
        if (tmp != null) {
            return (V) tmp.data.getValue();
        } else {
            return defaultValue;
        }
    }

    @Override
    public V putIfAbsent(K key, V value) {
        var hash = getIndex(key);
        var curValue = buckets[hash].getValue(key);
        if (curValue == null) {
            if (tail == null) {
                head = new ListElement(key, value);
                tail = head;
            } else {
                tail.next = new ListElement(key, value);
                tail.next.prev = tail;
                tail = tail.next;
            }
            buckets[hash].addElement(key, value, tail);
            size++;
            return null;
        }
        return (V) curValue.data.getValue();
    }

    @Override
    public boolean remove(Object key, Object value) {
        var hash = getIndex(key);
        var curValue = buckets[hash].getValue(key);
        if (curValue.data.getValue() == value) {
            if (curValue.prev != null) {
                curValue.prev.next = curValue.next;
            } else {
                head = curValue.next;
            }
            if (curValue.next != null) {
                curValue.next.prev = curValue.prev;
            } else {
                tail = curValue.prev;
            }
            buckets[hash].removeKey(key);
            size--;
            return true;
        }
        return false;
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        var hash = getIndex(key);
        var curValue = buckets[hash].getValue(key);
        if (curValue.data.getValue() == oldValue) {
            curValue.data.setValue(newValue);
            buckets[hash].removeKey(key);
            buckets[hash].addElement(key, newValue, curValue);
            return true;
        }
        return false;
    }

    @Override
    public V replace(K key, V value) {
        var hash = getIndex(key);
        var curValue = buckets[hash].getValue(key);
        if (curValue != null) {
            var oldValue = curValue.data.getValue();
            curValue.data.setValue(value);
            buckets[hash].removeKey(key);
            buckets[hash].addElement(key, value, curValue);
            return (V) oldValue;
        }
        return null;
    }
}