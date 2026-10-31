package dev.bigworld.mixin;

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

/** Only installs the effect when a new client world is loaded. */
@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow private void setPostProcessor(Identifier id) {}
    @Unique private ClientWorld colorbigworld$previousWorld;

    @Inject(method = "render", at = @At("HEAD"))
    private void colorbigworld$activate(CallbackInfo ci) {
        ClientWorld current = MinecraftClient.getInstance().world;
        if (current != colorbigworld$previousWorld) {
            colorbigworld$previousWorld = current;
            if (current != null) {
                setPostProcessor(Identifier.of("colorbigworld", "restoration"));
            }
        }
    }
}
