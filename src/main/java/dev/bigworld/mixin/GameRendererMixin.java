package dev.bigworld.mixin;

import dev.bigworld.ColorBigworldClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Installs once per world after a normal-color introduction.
 * Does not reinstall on every render frame.
 */
@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow private void setPostProcessor(Identifier id) {}
    @Unique private ClientWorld colorbigworld$installedWorld;

    @Inject(method = "render", at = @At("HEAD"))
    private void colorbigworld$activate(CallbackInfo ci) {
        ClientWorld world = MinecraftClient.getInstance().world;
        if (world == null) {
            colorbigworld$installedWorld = null;
            return;
        }
        if (ColorBigworldClient.shouldActivate() && world != colorbigworld$installedWorld) {
            colorbigworld$installedWorld = world;
            setPostProcessor(Identifier.of("colorbigworld", "restoration"));
        }
    }
}
