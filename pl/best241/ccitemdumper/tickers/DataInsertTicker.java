// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccitemdumper.tickers;

import java.util.Iterator;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import pl.best241.ccitemdumper.data.DataStore;
import com.mongodb.MongoClient;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.plugin.Plugin;
import pl.best241.ccitemdumper.Config;
import pl.best241.ccitemdumper.CcItemDumper;
import org.bukkit.Bukkit;

public class DataInsertTicker
{
    public static void run() {
        Bukkit.getScheduler().runTaskTimerAsynchronously((Plugin)CcItemDumper.getPlugin(), (Runnable)new Runnable() {
            @Override
            public void run() {
                DataInsertTicker.insert();
            }
        }, Config.insertTimeUnitInTicks, Config.insertTimeUnitInTicks);
    }
    
    public static void insert() {
        try {
            ClassLoader.getSystemClassLoader().loadClass("com.mongodb.client.MongoDatabase");
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(DataInsertTicker.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (final MongoClient mongoClient = new MongoClient("localhost", 27017)) {
            final MongoDatabase database = mongoClient.getDatabase("craftcore");
            final MongoCollection<Document> collection = (MongoCollection<Document>)database.getCollection("ccItemDeposit:playerInventories");
            for (final Document documentToInsert : DataStore.dataToInsert.keySet()) {
                final Long fetchTime = DataStore.dataToInsert.remove(documentToInsert);
                collection.insertOne((Object)documentToInsert);
                System.out.println("Inserting document. Time: " + (System.currentTimeMillis() - fetchTime));
            }
        }
        catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}
