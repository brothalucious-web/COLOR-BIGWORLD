# COLOR-BIGWORLD — Fabric 1.21.2

## Repair status
This is an **experimental prototype, not the fully requested mod**.

Fixed:
- GameRenderer no longer reinstalls the post-processing effect on **every single rendered frame**.
- Removed the timer-based player kill that falsely claimed to implement 85% color restoration.
- Retained instant grayscale-to-color shader prototype and 45% original/55% random RGB-key selection.

Not fixed / not implemented:
- The first 30 seconds must stay fully colored.
- Real, independent 30-second grayscale resets; the shader still uses the Minecraft day clock.
- Block/material semantic color groups (e.g. every grass or tree of matching original colors).
- Exact 85% of visual-world color coverage measured by the renderer and triggering death.
- Synchronizing restoration across multiplayer clients or covering HUD/UI.
- In-game verification. A successful GitHub Actions build verifies **compilation only**.

Run the automated build under **Actions** in this repository and inspect the log before installing the jar. Requires Java 21, Fabric Loader and Fabric API for Minecraft 1.21.2. GitHub Actions uploads a jar artifact on success.

Minecraft 1.21.2 post-processing technical specification:
https://www.minecraft.net/en-us/article/minecraft-java-edition-1-21-2
