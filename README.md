# COLOR-BIGWORLD — Material color-state engine (incomplete)

This branch adds a Java 21 **render-independent state engine** and a verified standalone test. It does NOT yet make gameplay behave as requested.

Implemented in the engine:
- Separate registry keys for block types, sky, and water (e.g., `block:minecraft:grass_block`, `sky:overworld`, `fluid:minecraft:water`).
- 45% original or 55% randomized hue for each group, stable during a cycle.
- Different random hue at least 30 degrees away from the original.
- State reset after every 600 engine ticks.
- Area-weighted 85% threshold based on **provided actual visible area** rather than group count.
- A death signal that fires at most once per cycle.

**Still required before playable:** Hook the engine to Fabric client ticks, render all block materials as a solid uniform color, handle sky/water/other entities separately, measure actual visible screen coverage from the renderer, invoke gameplay death safely through server logic, add normal-color startup, synchronize the visual cycle, and test in-game.

Important: no GPU sampling or block-ID renderer integration has been built yet. Existing post-effect in this branch remains the earlier experimental shader. This is not a finished mod.

Standalone test (Java 21):
```
javac -d tmp src/main/java/dev/bigworld/engine/ColorEngine.java src/test/java/dev/bigworld/engine/ColorEngineCheck.java
java -cp tmp dev.bigworld.engine.ColorEngineCheck
```
