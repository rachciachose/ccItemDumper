// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccitemdumper.listeners;

import org.bukkit.event.EventHandler;
import pl.best241.ccitemdumper.data.DataStore;
import java.util.UUID;
import pl.best241.rdbplugin.events.PubSubRecieveMessageEvent;
import org.bukkit.event.Listener;

public class PubSubListener implements Listener
{
    @EventHandler
    public static void onMessage(final PubSubRecieveMessageEvent event) {
        if (event.getChannel().equals("ccItemDeposit.lastUpdateTime")) {
            final String[] parsedData = event.getMessage().split(":");
            final UUID uuid = UUID.fromString(parsedData[0]);
            final Long lastUpdateTime = Long.valueOf(parsedData[1]);
            DataStore.lastEqUpdate.put(uuid, lastUpdateTime);
        }
    }
}
