// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccitemdumper.tickers;

import org.bukkit.plugin.Plugin;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.best241.ccitemdumper.parser.InventorySerializer;
import org.bson.Document;
import pl.best241.ccitemdumper.Config;
import pl.best241.ccitemdumper.data.DataStore;
import pl.best241.ccitemdumper.CcItemDumper;
import org.bukkit.Bukkit;

public class DataFetchTicker
{
    public static void run() {
        Bukkit.getScheduler().runTaskTimer((Plugin)CcItemDumper.getPlugin(), (Runnable)new Runnable() {
            @Override
            public void run() {
                for (final Player player : Bukkit.getOnlinePlayers()) {
                    final Long lastEqFetch = DataStore.lastEqUpdate.get(player.getUniqueId());
                    if (lastEqFetch == null || System.currentTimeMillis() - lastEqFetch >= Config.eqBackupTimeoutsInMillis) {
                        final Long fetchTime = System.currentTimeMillis();
                        final Document doc = new Document("uuid", (Object)player.getUniqueId().toString()).append("time", (Object)fetchTime).append("enderchest", (Object)InventorySerializer.serializeInventory(player.getEnderChest())).append("inventory", (Object)InventorySerializer.serializeInventory((Inventory)player.getInventory())).append("helmet", (Object)InventorySerializer.serializeItemStack(player.getInventory().getHelmet())).append("chestplate", (Object)InventorySerializer.serializeItemStack(player.getInventory().getChestplate())).append("leggings", (Object)InventorySerializer.serializeItemStack(player.getInventory().getLeggings())).append("boots", (Object)InventorySerializer.serializeItemStack(player.getInventory().getBoots()));
                        DataStore.dataToInsert.put(doc, fetchTime);
                        DataStore.lastEqUpdate.put(player.getUniqueId(), fetchTime);
                        System.out.println("Fetching document. Time: " + (System.currentTimeMillis() - fetchTime));
                    }
                }
            }
        }, Config.fetchTimeUnitInTicks, Config.fetchTimeUnitInTicks);
    }
}
