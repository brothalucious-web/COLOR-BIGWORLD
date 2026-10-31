package dev.bigworld;

import net.fabricmc.api.ModInitializer;

/**
 * Do not kill players using an elapsed-time guess.
 * The 85% rule requires a verified color-coverage signal from the renderer.
 * Until the renderer reports such a signal, there is NO automatic death.
 */
public final class ColorBigworld implements ModInitializer {
    @Override public void onInitialize() {
    }
}
