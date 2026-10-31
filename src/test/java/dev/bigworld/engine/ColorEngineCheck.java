package dev.bigworld.engine;
import java.util.Map;
public final class ColorEngineCheck {
    public static void main(String[] args) {
        var engine = new ColorEngine(42);
        engine.tick();
        var grass = engine.colorFor("block", "minecraft:grass_block", 120);
        if (!grass.equals(engine.colorFor("block", "minecraft:grass_block", 120))) throw new AssertionError("same-type consistency");
        if (!engine.colorFor("sky", "overworld", 210).equals(engine.colorFor("sky", "overworld", 210))) throw new AssertionError("sky");
        engine.restore("block", "minecraft:grass_block");
        var areas = Map.of("block:minecraft:grass_block", 90., "block:minecraft:stone", 10.);
        if (engine.measure(areas).fraction() != .9) throw new AssertionError("area weighting");
        if (!engine.shouldDie(areas) || engine.shouldDie(areas)) throw new AssertionError("one death per cycle");
        for (int i = 1; i < 600; i++) engine.tick();
        if (engine.cycle() != 0) throw new AssertionError("cycle too soon");
        engine.tick();
        if (engine.cycle() != 1 || engine.isRestored("block", "minecraft:grass_block")) throw new AssertionError("reset");
        System.out.println("PASS: consistent groups, area-weighted death threshold, 600-tick reset");
    }
}
