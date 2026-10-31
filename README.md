# COLOR-BIGWORLD — COLOR RESTORATION V2 EXPERIMENT

**Test branch:** `experimental/color-restoration-v2`. Your main branch and proven magenta diagnostic are untouched.

## Proven by user
The magenta diagnostic shader visibly turned the world purple in-game, confirming the pipeline can render a custom fragment shader.

## What this experimental build tries
- Restores **groups of similar original RGB colors at once**, not random pixels. Each group consistently chooses original color (45% probability) or a randomly chosen replacement (55% probability).
- Instant transitions with no fading.
- Before each selected group restores, pixels in that group appear grayscale.
- Both original and random restorations count visually.

## Important remaining work
- GameTime is not an independent 30-second clock; changing world time affects progression.
- No first-30-seconds normal-world startup.
- A source RGB grouping is not a block/material grouping.
- Restoration is currently by color-group thresholds, not precise percentage of screen/world colored area.
- **No 85% visual-area death trigger.** It was intentionally disabled rather than falsely killing players at a timer threshold.
- No in-game verification on this branch; this is a visually testable experimental shader.

Build with GitHub Actions; use Minecraft 1.21.2, Fabric Loader, Fabric API and only one COLOR-BIGWORLD jar.
