# COLOR-BIGWORLD (Fabric 1.21.2) — experimental branch

This branch deliberately preserves the last confirmed compiling version on `main`.

## Change in this branch
- World loads with ordinary Minecraft colors for **600 active client ticks** (~30 seconds at 20 TPS).
- Only after that initial period does it install the existing restoration post-effect.
- Rendering does not reinstall the post-effect every frame.
- A fresh world starts a fresh 600-tick introduction.

## Not yet implemented correctly
- Every 30-second grayscale reset using an independent client cycle.
- Reliable material-level world color groups (grass, foliage, etc.).
- Accurate measurement of restored visual coverage and death at 85%.
- The shader's own color cycle still uses GameTime; it is a prototype.
- In-game testing. CI passing only proves the mod compiles.

The threshold must never be approximated by '85% elapsed time' or '85% groups correct'.
