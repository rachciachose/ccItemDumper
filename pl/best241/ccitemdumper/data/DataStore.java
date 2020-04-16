// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccitemdumper.data;

import org.bson.Document;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class DataStore
{
    public static ConcurrentHashMap<UUID, Long> lastEqUpdate;
    public static ConcurrentHashMap<Document, Long> dataToInsert;
    
    static {
        DataStore.lastEqUpdate = new ConcurrentHashMap<UUID, Long>();
        DataStore.dataToInsert = new ConcurrentHashMap<Document, Long>();
    }
}
