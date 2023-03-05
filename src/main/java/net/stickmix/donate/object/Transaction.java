package net.stickmix.donate.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.stickmix.donate.DonateServiceOuterClass;
import net.villenium.athena.client.annotation.Id;

@Getter
@AllArgsConstructor
public class Transaction {

    @Id
    private final String id;
    private final long time;
    private final String sender;
    private final String receiver;
    private final int amount;

    public DonateServiceOuterClass.Transaction toProtobuf() {
        return DonateServiceOuterClass.Transaction
                .newBuilder()
                .setId(id)
                .setTime(time)
                .setSender(sender)
                .setReceiver(receiver)
                .setAmount(amount)
                .build();
    }

}
