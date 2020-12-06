package subscriber;

public interface Subscriber {
    void send();

    String getQueue();
}
