package net.stickmix.donate.util.stream;

import io.grpc.stub.StreamObserver;
import lombok.Data;

import java.util.function.Consumer;

@Data
public class EachStreamObserver<V> implements StreamObserver<V> {

    private final Consumer<V> eachHandler;
    private final Consumer<Throwable> errorHandler;

    @Override
    public void onNext(V value) {
        eachHandler.accept(value);
    }

    @Override
    public void onError(Throwable t) {
        if (errorHandler != null) errorHandler.accept(t);
    }

    @Override
    public void onCompleted() {

    }

}