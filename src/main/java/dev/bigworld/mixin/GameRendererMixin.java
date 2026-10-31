package dev.bigworld.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow private void setPostProcessor(Identifier id) {}
    @Inject(method = "render", at = @At("HEAD"))
    private void colorbigworld$activate(CallbackInfo ci) {
        if (MinecraftClient.getInstance().world != null) {
            setPostProcessor(Identifier.of("colorbigworld", "restoration"));
        }
    }
}
