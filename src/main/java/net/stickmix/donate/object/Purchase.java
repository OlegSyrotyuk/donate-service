package net.stickmix.donate.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.stickmix.donate.DonateServiceOuterClass;
import net.villenium.athena.client.annotation.Id;
import net.villenium.athena.client.annotation.Name;

@Getter
@AllArgsConstructor
public class Purchase {

    @Id
    private final String id;
    private final String buyer;
    private final String product;
    private final int price;
    private final long time;
    @Name(name = "server_type")
    private final String serverType;
    @Name(name = "transaction_id")
    private final String transactionId;

    public DonateServiceOuterClass.Purchase toProtobuf() {
        return DonateServiceOuterClass.Purchase
                .newBuilder()
                .setId(id)
                .setBuyer(buyer)
                .setProduct(product)
                .setPrice(price)
                .setTime(time)
                .setServerType(serverType)
                .setTransactionId(transactionId)
                .build();
    }

}
