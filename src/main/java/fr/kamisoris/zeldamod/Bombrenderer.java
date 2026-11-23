package fr.kamisoris.zeldamod;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomModelData;

/**
 * Clignotement "Zelda" : au lieu de peindre un aplat par-dessus, on change le MODÈLE de la bombe
 * (texture rouge/orange avec les mêmes ombres et reflets) grâce à custom_model_data.
 *   custom_model_data = 1 -> models/item/bomb_orange.json
 *   custom_model_data = 2 -> models/item/bomb_red.json
 */
public class Bombrenderer extends EntityRenderer<BombEntity> {

    public Bombrenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(BombEntity entity, float entityYaw, float partialTicks,
                       PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.0D, 0.25D, 0.0D);
        poseStack.mulPose(Axis.YP.rotationDegrees(-entityYaw));

        int fuseRemaining = Math.max(0, entity.getFuseTicks());
        float progress = 1.0F - (fuseRemaining / 40.0F);

        // gonflement (comme avant)
        float speed = fuseRemaining <= 15 ? 1.15F : 0.45F;
        float wave = 0.5F + 0.5F * (float) Math.sin((entity.tickCount + partialTicks) * speed);
        float swell = 1.0F + (progress * 0.25F) + (wave * 0.14F);
        poseStack.scale(swell, swell, swell);

        // rythme du clignotement : de plus en plus rapide
        int interval = fuseRemaining > 25 ? 6 : fuseRemaining > 12 ? 4 : 2;
        boolean flashOn = (fuseRemaining / interval) % 2 == 0;

        ItemStack stack = new ItemStack(Itemtest.bomb.get());
        if (flashOn) {
            // 1 = orange au début, 2 = rouge dans les 20 derniers ticks
            stack.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(fuseRemaining <= 20 ? 2 : 1));
        }

        // quand elle clignote, la bombe "brille" (pleine lumière)
        int light = flashOn ? LightTexture.FULL_BRIGHT : packedLight;

        Minecraft.getInstance().getItemRenderer().renderStatic(
                stack, ItemDisplayContext.GROUND, light, OverlayTexture.NO_OVERLAY,
                poseStack, bufferSource, entity.level(), entity.getId());

        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(BombEntity entity) {
        return InventoryMenu.BLOCK_ATLAS;
    }
}