package server;

import subscriber.Subscriber;
import subscriber.SubscriberTopic;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Класс является центральным хранилищем объектов.
 * Publisher при подключении добавлюят в QUEUE_MESSAGE новых ObjectJson
 * Subscriber читают из QUEUE_MESSAGE нужный ObjectJson.
 * Список активных Subscriber хранятся в SUBSCRIBERS.
 */

public class EventMessage {
    private static final ConcurrentHashMap<String, BlockingQueue<ObjectJson>> QUEUE_MESSAGE =
            new ConcurrentHashMap<>();
    private static final CopyOnWriteArrayList<Subscriber> SUBSCRIBERS =
            new CopyOnWriteArrayList<>();

    public void addMessage(String[] requestArray) {
        ObjectJson objectJson = new ObjectJson(requestArray[2]);
        if (!add(objectJson)) {
            update(objectJson);
        }
    }

    private boolean add(ObjectJson objectJson) {
        BlockingQueue<ObjectJson> queue = new LinkedBlockingDeque<>();
        queue.offer(objectJson);
        return QUEUE_MESSAGE.putIfAbsent(objectJson.getQueue(), queue) == null;
    }

    private void update(ObjectJson objectJson) {
        QUEUE_MESSAGE.computeIfPresent(objectJson.getQueue(), (k, v) -> {
            v.offer(objectJson);
            return v;
        });
    }

    public ObjectJson getMessage(String getQueue) {
        return QUEUE_MESSAGE.get(getQueue).poll();
    }

    public void subscribe(Subscriber newSubscriber) {
        SUBSCRIBERS.add(newSubscriber);
    }

    public void unsubscribe(Subscriber oldSubscriber) {
        SUBSCRIBERS.remove(oldSubscriber);
    }

    public void notification() {
        if (!SUBSCRIBERS.isEmpty()) {
            for (Map.Entry<String, BlockingQueue<ObjectJson>> entry : QUEUE_MESSAGE.entrySet()) {
                ObjectJson objectJson = null;
                for (Subscriber sub : SUBSCRIBERS) {
                    if (entry.getKey().equals(sub.getQueue())) {
                        objectJson = objectJson == null ? getMessage(sub.getQueue()) : objectJson;
                        SubscriberTopic subscriberTopic = (SubscriberTopic) sub;
                        subscriberTopic.getQueueObjectJson(objectJson);
                    }
                }
            }
        }
    }
}
