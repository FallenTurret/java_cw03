package hse.cw03.linkedhashmap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedHashMapTest {

    static LinkedHashMap<Integer, String> map;

    @BeforeEach
    void setUp() {
        map = new LinkedHashMap<>();
    }

    @Test
    void entrySet() {
        map.putIfAbsent(1, "a");
        map.putIfAbsent(3, "c");
        map.putIfAbsent(4, "d");
        map.putIfAbsent(2, "b");
        map.remove(4, "d");
        var set = map.entrySet();
        var iterator = set.iterator();
        assertTrue(iterator.hasNext());
        assertEquals("a", iterator.next().getValue());
        assertTrue(iterator.hasNext());
        assertEquals("c", iterator.next().getValue());
        assertTrue(iterator.hasNext());
        assertEquals("b", iterator.next().getValue());
    }

    @Test
    void shouldCorrectlyProcessMapModifications() {
        assertNull(map.putIfAbsent(1, "a"));
        assertEquals("a", map.getOrDefault(1, null));
        assertEquals("a", map.replace(1, "b"));
        assertEquals("b", map.getOrDefault(1, null));
        assertEquals(1, map.entrySet().size());
        assertFalse(map.remove(1, "a"));
        assertTrue(map.remove(1, "b"));
        assertEquals(0, map.entrySet().size());
    }
}