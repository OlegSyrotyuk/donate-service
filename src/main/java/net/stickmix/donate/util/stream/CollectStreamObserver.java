package net.stickmix.donate.util.stream;

import io.grpc.stub.StreamObserver;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Data
public class CollectStreamObserver<V> implements StreamObserver<V> {

    private final List<V> collection = new ArrayList<>();

    private final Consumer<Collection<V>> handler;
    private final BiConsumer<Collection<V>, Throwable> errorHandler;

    @Override
    public void onNext(V value) {
        collection.add(value);
    }

    @Override
    public void onError(Throwable t) {
        errorHandler.accept(collection, t);
    }

    @Override
    public void onCompleted() {
        handler.accept(collection);
    }

}