package dev.bigworld;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.world.ClientWorld;

/**
 * Starts with vanilla world colors for 600 active client ticks.
 * This timer is independent of the world day clock.
 */
public final class ColorBigworldClient implements ClientModInitializer {
    private static ClientWorld previousWorld;
    private static int activeTicks;

    public static boolean shouldActivate() {
        return previousWorld != null && activeTicks >= 600;
    }

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.world != previousWorld) {
                previousWorld = client.world;
                activeTicks = 0;
            } else if (client.world != null && !client.isPaused() && activeTicks < 600) {
                activeTicks++;
            }
        });
    }
}
