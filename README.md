# COLOR-BIGWORLD — Fabric 1.21.2 (experimental)

## Current implementation
- Fully monochrome at the beginning of every **30-second / 600-tick** shader cycle.
- Instant, discrete changes; **no fades**.
- Restored color groups share a quantized source-RGB key throughout a frame.
- Color-group choice is **45% original / 55% recolored**; both count as colored.
- Pixel-level restoration is probabilistic across the **visible image**, not a percentage of correctly restored groups.
- The post-effect is applied to the world image, **before Minecraft draws HUD/UI**.

## IMPORTANT limitations / not yet production-ready
**The 85% death condition is NOT implemented to the exact requested specification.** The server currently uses a **510-tick / 85%-of-cycle timer**, not GPU measured pixel coverage. The shader uses probabilistic visible screen-space restoration, so 85% is only expected approximately. This is **not an exact measure of the whole Minecraft world**. The entire persistent world's visual-color coverage cannot be calculated merely by observing the frame. The game server and shader are also not perfectly synchronized (daylight cycle, pause, time commands). **This is an experimental prototype.**

The shader runs as a Minecraft 1.21.2 post-effect. It does not tint the HUD, and the grouping is based on sampled source colors rather than block or biome types. Some post-effect mods may conflict. In-game and build testing have NOT been completed.

## Build
Requires Java 21. Install Gradle 8.10+ or add a Gradle wrapper, then run `gradle build`. The jar should appear in `build/libs`. Install Fabric Loader 1.21.2 and place the jar in `mods`.

Sources: https://www.minecraft.net/en-us/article/minecraft-java-edition-1-21-2 and https://fabricmc.net/2024/10/14/1212.html
