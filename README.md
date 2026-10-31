# COLOR-BIGWORLD — DIAGNOSTIC BRANCH ONLY

**This is not the finished gameplay mod.** The existing `main` branch has not been changed.

## Why this branch exists
Prior releases compiled, but the user reports that no intended color-changing behavior appeared. This version isolates rendering from gameplay rules.

- The fragment shader is deliberately an obvious **magenta grayscale**.
- The shader should be visible immediately on joining a world.
- It does **not** attempt restoration, death, group consistency, 45/55 distribution, or a 30-second reset.
- It retains the existing once-per-world GameRenderer mixin.
- HUD elements are not affected because Minecraft applies post-effects before the HUD.

## Test
1. Download the latest successful build artifact from GitHub Actions for this branch.
2. Use Minecraft Java **1.21.2**, Fabric Loader, and Fabric API.
3. Remove other COLOR-BIGWORLD jar versions from the mods folder.
4. Enter a world. If the world image does **not** turn bright magenta-tinted grayscale, the post-processing pipeline isn't applied or fails to load.
5. If not visible, provide `logs/latest.log` and any resource-reload error. If visible, the pipeline works and the color state logic can be rebuilt against this verified path.

A GitHub Actions build checks compilation and packaging only. **No in-game validation has been performed.**
