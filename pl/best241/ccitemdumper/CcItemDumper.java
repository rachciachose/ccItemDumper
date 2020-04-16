// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccitemdumper;

import java.util.Iterator;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.UUID;
import com.mongodb.MongoClient;
import pl.best241.ccitemdumper.listeners.PubSubListener;
import pl.best241.ccitemdumper.listeners.PlayerQuitKickListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;
import pl.best241.ccitemdumper.listeners.PlayerJoinListener;
import pl.best241.ccitemdumper.tickers.DataInsertTicker;
import pl.best241.ccitemdumper.tickers.DataFetchTicker;
import org.bukkit.plugin.java.JavaPlugin;

public class CcItemDumper extends JavaPlugin
{
    private static CcItemDumper plugin;
    
    public void onEnable() {
        CcItemDumper.plugin = this;
        DataFetchTicker.run();
        DataInsertTicker.run();
        this.getServer().getPluginManager().registerEvents((Listener)new PlayerJoinListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new PlayerQuitKickListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new PubSubListener(), (Plugin)this);
    }
    
    public void onDisable() {
        DataInsertTicker.insert();
    }
    
    public static CcItemDumper getPlugin() {
        return CcItemDumper.plugin;
    }
    
    public static void main(final String[] args) {
        try (final MongoClient mongoClient = new MongoClient("localhost", 27017)) {
            final MongoDatabase database = mongoClient.getDatabase("craftcore");
            final MongoCollection<Document> collection = (MongoCollection<Document>)database.getCollection("ccItemDeposit:playerInventories");
            System.out.println("database " + database);
            System.out.println("Collection " + collection);
            final Document doc = new Document("uuid", (Object)UUID.randomUUID().toString()).append("lastSave", (Object)System.currentTimeMillis());
            collection.insertOne((Object)doc);
            final FindIterable<Document> find = (FindIterable<Document>)collection.find();
            for (final Document foundDoc : find) {
                System.out.println("Found " + foundDoc.getLong((Object)"lastSave"));
            }
        }
    }
}
