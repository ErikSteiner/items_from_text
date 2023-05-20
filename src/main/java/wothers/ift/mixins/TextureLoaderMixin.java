package wothers.ift.mixins;

import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceImpl;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import wothers.ift.ItemRegistry;
import java.io.FileInputStream;
import java.io.IOException;

@Mixin(SpriteAtlasTexture.class)
public class TextureLoaderMixin {
    @Redirect(method = "method_18160", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ResourceManager;getResource(Lnet/minecraft/util/Identifier;)Lnet/minecraft/resource/Resource;"))
    private Resource loadExternalResource(ResourceManager resourceManager, Identifier id) throws IOException {
        if (ItemRegistry.Texture.INSTANCE.getMap().containsKey(id.toString())) {
            FileInputStream fileInputStream = new FileInputStream(ItemRegistry.Texture.INSTANCE.getMap().get(id.toString()));
            return new ResourceImpl(null, id, fileInputStream, null);
        }
        return resourceManager.getResource(id);
    }

    @Redirect(method = "loadSprite", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ResourceManager;getResource(Lnet/minecraft/util/Identifier;)Lnet/minecraft/resource/Resource;"))
    private Resource loadExternalResource2(ResourceManager resourceManager, Identifier id) throws IOException {
        if (ItemRegistry.Texture.INSTANCE.getMap().containsKey(id.toString())) {
            FileInputStream fileInputStream = new FileInputStream(ItemRegistry.Texture.INSTANCE.getMap().get(id.toString()));
            return new ResourceImpl(null, id, fileInputStream, null);
        }
        return resourceManager.getResource(id);
    }
}
