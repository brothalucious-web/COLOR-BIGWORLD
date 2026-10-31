package dev.bigworld.engine;

import java.util.*;

/** Render-independent color state; renderer integration is required. */
public final class ColorEngine {
    public static final int RESET_TICKS = 600;
    public static final float ORIGINAL_CHANCE = .45f;
    public static final double DEATH_COVERAGE = .85;
    public record ColorDecision(boolean original, float hueDegrees) {}
    public record Coverage(double restored, double total) {
        public double fraction() { return total == 0 ? 0 : restored / total; }
    }
    private final Random random;
    private final Map<String, ColorDecision> colorDecisions = new HashMap<>();
    private final Set<String> restored = new HashSet<>();
    private long ticks;
    private long cycle = -1;
    private boolean diedThisCycle;
    public ColorEngine(long seed) { random = new Random(seed); }
    public void tick() {
        ticks++;
        long next = (ticks - 1) / RESET_TICKS;
        if (next != cycle) {
            cycle = next;
            colorDecisions.clear();
            restored.clear();
            diedThisCycle = false;
        }
    }
    public int cycleTick() { return (int)((ticks - 1) % RESET_TICKS); }
    public long cycle() { return cycle; }
    public ColorDecision colorFor(String kind, String id, float originalHue) {
        String key = kind + ":" + id;
        return colorDecisions.computeIfAbsent(key, ignored -> {
            boolean original = random.nextFloat() < ORIGINAL_CHANCE;
            if (original) return new ColorDecision(true, originalHue);
            float hue = (originalHue + 30f + random.nextFloat() * 300f) % 360f;
            return new ColorDecision(false, hue);
        });
    }
    public void restore(String kind, String id) { restored.add(kind + ":" + id); }
    public boolean isRestored(String kind, String id) { return restored.contains(kind + ":" + id); }
    /** Visible-area samples must come from actual renderer coverage measurements. */
    public Coverage measure(Map<String, Double> visibleAreaByGroup) {
        double shown = 0, total = 0;
        for (var entry : visibleAreaByGroup.entrySet()) {
            double area = entry.getValue();
            if (!Double.isFinite(area) || area < 0) throw new IllegalArgumentException("invalid area");
            total += area;
            if (restored.contains(entry.getKey())) shown += area;
        }
        return new Coverage(shown, total);
    }
    public boolean shouldDie(Map<String, Double> visibleAreaByGroup) {
        if (diedThisCycle) return false;
        if (measure(visibleAreaByGroup).fraction() >= DEATH_COVERAGE) {
            diedThisCycle = true;
            return true;
        }
        return false;
    }
}
