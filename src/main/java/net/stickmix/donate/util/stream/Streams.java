package net.stickmix.donate.util.stream;

import io.grpc.stub.StreamObserver;
import lombok.experimental.UtilityClass;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@UtilityClass
public class Streams {

    public <V> void write(StreamObserver<V> observer, Collection<V> collection, boolean close) {
        collection.forEach(observer::onNext);
        if (close) observer.onCompleted();
    }

    public <V> void write(StreamObserver<V> observer, Collection<V> collection) {
        write(observer, collection, true);
    }

    public <V> void write(StreamObserver<V> observer, V value, boolean close) {
        observer.onNext(value);
        if (close) observer.onCompleted();
    }

    public <V> void write(StreamObserver<V> observer, V value) {
        write(observer, value, true);
    }

    public <V, C extends Collection<V>> C read(Iterator<V> iterator, C collection) {
        iterator.forEachRemaining(collection::add);
        return collection;
    }

    public <V> List<V> readList(Iterator<V> iterator) {
        return read(iterator, new ArrayList<>());
    }

    public <V> Set<V> readSet(Iterator<V> iterator) {
        return read(iterator, new HashSet<>());
    }

    public <V> StreamObserver<V> forEach(Consumer<V> eachHandler, Consumer<Throwable> errorHandler) {
        return new EachStreamObserver<>(eachHandler, errorHandler);
    }

    public <V> StreamObserver<V> forEach(Consumer<V> eachHandler) {
        return forEach(eachHandler, null);
    }

    public <V> StreamObserver<V> collect(Consumer<Collection<V>> eachHandler, BiConsumer<Collection<V>, Throwable> errorHandler) {
        return new CollectStreamObserver<>(eachHandler, errorHandler);
    }

    public <V> StreamObserver<V> collect(Consumer<Collection<V>> handler) {
        return collect(handler, null);
    }

}
