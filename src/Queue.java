public class Queue<T> {
    private QueueRecord head;
    private QueueRecord tail;

    public Queue() {
        head = null;
        tail = null;
    }

    public void enqueue(T obj) {
        QueueRecord newRecord = new QueueRecord(obj);
        if (tail == null) {
            head = newRecord;
            tail = newRecord;
        }
        else {
            tail.next = newRecord;
            tail = newRecord;
        }
    }

    public T dequeue() {
        if (head == null) return null;
        T payload = head.payload;
        head = head.next;
        if (head == null) tail = null;
        return payload;
    }

    private class QueueRecord {
        public T payload;
        public QueueRecord next;

        public QueueRecord(T payload) {
            this.payload = payload;
            this.next = null;
        }
    }

}