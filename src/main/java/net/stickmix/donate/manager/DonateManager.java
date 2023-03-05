package net.stickmix.donate.manager;

import net.stickmix.donate.object.Balance;
import net.stickmix.donate.object.Purchase;
import net.stickmix.donate.object.Transaction;
import net.villenium.athena.client.ObjectPool;

import java.util.List;

public interface DonateManager {

    /**
     * Получить пул для работы с балансом пользователя.
     * @return пул объектов.
     */
    ObjectPool<Balance> getBalancePool();

    /**
     * Создать и записать новую транзакцию.
     * @param sender отправитель средств.
     * @param receiver получатель средств.
     * @param delta количество денег.
     * @return объект транзакции.
     */
    Transaction transaction(String sender, String receiver, int delta);

    /**
     * Покупка продукта.
     * @param buyer покупатель.
     * @param product название продукта.
     * @param price цена продукта.
     * @param serverType тип сервера на котором приобретается покупка.
     * @return объект покупки.
     */
    Purchase buyProduct(String buyer, String product, int price, String serverType);

    /**
     * Получить все транзакции пользователя.
     * @param user имя пользователя.
     * @param sender является ли пользователь отправителем.
     *               Если нет, то искать будем транзакции, где пользователь получатель.
     * @return список транзакций.
     */
    List<Transaction> getTransactions(String user, boolean sender);

    /**
     * Получить все покупки пользователя.
     * @param user имя пользователя.
     * @return список покупок.
     */
    List<Purchase> getUserPurchases(String user);

    /**
     * Получить все покупки совершенные на определенном типе сервера.
     * @param serverType тип сервера.
     * @return список покупок.
     */
    List<Purchase> getPurchasesByServerType(String serverType);

}
