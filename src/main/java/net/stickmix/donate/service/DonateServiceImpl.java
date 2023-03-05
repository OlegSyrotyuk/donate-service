package net.stickmix.donate.service;

import com.google.protobuf.DoubleValue;
import com.google.protobuf.Empty;
import com.google.protobuf.Int32Value;
import com.google.protobuf.StringValue;
import io.grpc.stub.StreamObserver;
import net.stickmix.donate.DonateBootstrap;
import net.stickmix.donate.DonateServiceGrpc;
import net.stickmix.donate.DonateServiceOuterClass;
import net.stickmix.donate.manager.DonateManager;
import net.stickmix.donate.object.Purchase;
import net.stickmix.donate.object.Transaction;
import net.stickmix.donate.util.stream.Streams;

import java.util.List;
import java.util.stream.Collectors;

public class DonateServiceImpl extends DonateServiceGrpc.DonateServiceImplBase {

    private final DonateManager manager = DonateBootstrap.getDonateManager();

    @Override
    public void getBalance(StringValue request, StreamObserver<Int32Value> responseObserver) {
        responseObserver.onNext(Int32Value.newBuilder()
                .setValue(manager.getBalancePool().get(request.getValue()).getMoney())
                .build());
        responseObserver.onCompleted();
//        Streams.write(responseObserver,
//                DoubleValue.newBuilder()
//                        .setValue(manager.getBalancePool().get(request.getValue()).getMoney())
//                        .build());
    }

    @Override
    public void transaction(DonateServiceOuterClass.Transaction request,
                            StreamObserver<Empty> responseObserver) {
        manager.transaction(request.getSender(), request.getReceiver(), request.getAmount());
        Streams.write(responseObserver, Empty.newBuilder().build());
    }

    @Override
    public void buyProduct(DonateServiceOuterClass.Purchase request,
                           StreamObserver<DonateServiceOuterClass.Purchase> responseObserver) {
        Purchase purchase = manager.buyProduct(request.getBuyer(),
                request.getProduct(),
                request.getPrice(),
                request.getServerType());
        Streams.write(responseObserver, purchase.toProtobuf());
    }

    @Override
    public void getTransactions(DonateServiceOuterClass.GetTransactionsRequest request,
                                StreamObserver<DonateServiceOuterClass.Transaction> responseObserver) {
        List<Transaction> transactions = manager.getTransactions(request.getUser(), request.getSender());
        Streams.write(responseObserver, transactions
                .stream()
                .map(Transaction::toProtobuf)
                .collect(Collectors.toList()));
    }

    @Override
    public void getUserPurchases(StringValue request,
                                 StreamObserver<DonateServiceOuterClass.Purchase> responseObserver) {
        List<Purchase> purchases = manager.getUserPurchases(request.getValue());
        Streams.write(responseObserver, purchases
                .stream()
                .map(Purchase::toProtobuf)
                .collect(Collectors.toList()));
    }

    @Override
    public void getPurchasesByServerType(StringValue request,
                                         StreamObserver<DonateServiceOuterClass.Purchase> responseObserver) {
        List<Purchase> purchases = manager.getPurchasesByServerType(request.getValue());
        Streams.write(responseObserver, purchases
                .stream()
                .map(Purchase::toProtobuf)
                .collect(Collectors.toList()));
    }
}
