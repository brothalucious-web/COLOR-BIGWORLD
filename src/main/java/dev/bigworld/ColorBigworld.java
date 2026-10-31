package dev.bigworld;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import java.util.UUID;
import java.util.Map;
import java.util.HashMap;

/** Experimental challenge scheduler. 600 ticks per cycle. */
public final class ColorBigworld implements ModInitializer {
    private final Map<UUID, Integer> elapsed = new HashMap<>();
    @Override public void onInitialize() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                if (player.isSpectator() || player.isCreative()) {
                    elapsed.remove(player.getUuid());
                    continue;
                }
                int ticks = elapsed.getOrDefault(player.getUuid(), 0) + 1;
                // A complete 30-second cycle resets the danger clock.
                if (ticks >= 600) ticks = 0;
                // This timer-based trigger is an approximation, NOT a measurement
                // of the rendered image's actual colored-pixel coverage.
                if (ticks == 510) {
                    player.kill(player.getServerWorld());
                    ticks = 0;
                }
                elapsed.put(player.getUuid(), ticks);
            }
            elapsed.keySet().removeIf(uuid -> server.getPlayerManager().getPlayer(uuid) == null);
        });
    }
}
