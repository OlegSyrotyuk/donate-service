package net.stickmix.donate;

import net.stickmix.donate.object.Balance;
import net.stickmix.donate.object.Purchase;
import net.stickmix.donate.object.Transaction;
import net.villenium.athena.client.IAthenaStorage;

public class Storages {

    public static IAthenaStorage<Balance> BALANCES = DonateBootstrap.getStorageManager().create("balances", Balance.class);
    public static IAthenaStorage<Purchase> PURCHASES = DonateBootstrap.getStorageManager().create("purchases", Purchase.class);
    public static IAthenaStorage<Transaction> TRANSACTIONS = DonateBootstrap.getStorageManager().create("transactions", Transaction.class);

}
