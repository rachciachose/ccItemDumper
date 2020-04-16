// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.ccitemdumper;

import java.util.concurrent.TimeUnit;

public class Config
{
    public static final String lastUpdateTimePubSubChannelName = "ccItemDeposit.lastUpdateTime";
    public static final long fetchTimeUnitInTicks;
    public static final long insertTimeUnitInTicks;
    public static final long eqBackupTimeoutsInMillis;
    
    static {
        fetchTimeUnitInTicks = TimeUnit.SECONDS.toSeconds(30L) * 20L;
        insertTimeUnitInTicks = TimeUnit.MINUTES.toSeconds(1L) * 20L;
        eqBackupTimeoutsInMillis = TimeUnit.MINUTES.toMillis(2L);
    }
}
