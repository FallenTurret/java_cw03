package hse.cw03.linkedhashmap;

public class List<K, V> {

    private class ListElement {
        private K key;
        private V value;
        private ListElement next;
        private LinkedHashMap.ListElement ref;
        ListElement(K keyOfElement, V valueOfElement, LinkedHashMap.ListElement refOfElement) {
            key = keyOfElement;
            value = valueOfElement;
            ref = refOfElement;
            next = null;
        }
    }

    private ListElement head;

    public List() {
        head = null;
    }

    public void addElement(K key, V value, LinkedHashMap.ListElement ref) {
        var newHead = new ListElement(key, value, ref);
        newHead.next = head;
        head = newHead;
    }

    public LinkedHashMap.ListElement getValue(Object key) {
        ListElement curElement = head;
        while (curElement != null) {
            if (key.equals(curElement.key)) {
                return curElement.ref;
            }
            curElement = curElement.next;
        }
        return null;
    }

    public LinkedHashMap.ListElement removeKey(Object key) {
        if (head == null) {
            return null;
        }
        if (key.equals(head.key)) {
            var tmp = head.ref;
            head = head.next;
            return tmp;
        }
        ListElement curElement = head;
        while (curElement.next != null) {
            if (key.equals(curElement.next.key)) {
                var tmp = curElement.next.ref;
                curElement.next = curElement.next.next;
                return tmp;
            }
            curElement = curElement.next;
        }
        return null;
    }
}