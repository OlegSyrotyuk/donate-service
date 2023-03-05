package net.stickmix.donate.manager;

import lombok.Getter;
import net.stickmix.donate.Storages;
import net.stickmix.donate.object.Balance;
import net.stickmix.donate.object.Purchase;
import net.stickmix.donate.object.Transaction;
import net.villenium.athena.client.ObjectPool;

import java.util.List;
import java.util.Random;

public class DonateManagerImpl implements DonateManager {

    @Getter
    private final ObjectPool<Balance> balancePool;

    public DonateManagerImpl() {
        balancePool = Storages.BALANCES.newObjectPool();
        balancePool.setDefaultObject(new Balance(null, 0));
        balancePool.setAutoSaveTime(1);
    }

    @Override
    public Transaction transaction(String sender, String receiver, int delta) {
        Balance senderBalance = getBalancePool().get(sender);
        Balance receiverBalance = getBalancePool().get(receiver);
        Transaction transaction = new Transaction(generateIdentity(), System.currentTimeMillis(), sender, receiver, delta);
        senderBalance.changeBalance(-delta);
        getBalancePool().save(sender, false);
        receiverBalance.changeBalance(delta);
        getBalancePool().save(receiver, false);
        Storages.TRANSACTIONS.upsert(transaction.getId(), transaction);
        return transaction;
    }

    @Override
    public Purchase buyProduct(String buyer, String product, int price, String serverType) {
        Transaction transaction = transaction(buyer, "StickMix", price);
        Purchase purchase = new Purchase(generateIdentity(), buyer, product, price, System.currentTimeMillis(), serverType, transaction.getId());
        Storages.PURCHASES.upsert(purchase.getId(), purchase);
        return purchase;
    }

    @Override
    public List<Transaction> getTransactions(String user, boolean sender) {
        return sender ? Storages.TRANSACTIONS
                .find()
                .whereEquals("sender", user)
                .findAll() : Storages.TRANSACTIONS
                .find()
                .whereEquals("receiver", user)
                .findAll();
    }

    @Override
    public List<Purchase> getUserPurchases(String user) {
        return Storages.PURCHASES
                .find()
                .whereEquals("buyer", user)
                .findAll();
    }

    @Override
    public List<Purchase> getPurchasesByServerType(String serverType) {
        return Storages.PURCHASES
                .find()
                .whereEquals("server_type", serverType)
                .findAll();
    }


    private String generateIdentity() {
        Random random = new Random();
        char[] numbers = new char[10];
        for (char c = '0'; c <= '9'; ++c) {
            numbers[c - '0'] = c;
        }
        StringBuilder sb = new StringBuilder();
        sb.append('#');
        for (int i = 0; i < 16; ++i) {
            sb.append(numbers[random.nextInt(numbers.length)]);
        }
        return sb.toString().toUpperCase();
    }
}
