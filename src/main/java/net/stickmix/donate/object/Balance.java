package net.stickmix.donate.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import net.villenium.athena.client.annotation.Id;

@ToString
@Getter
@AllArgsConstructor
public class Balance {

    @Id
    private final String user;
    private int money;

    public void changeBalance(double money) {
        this.money += money;
    }

}
