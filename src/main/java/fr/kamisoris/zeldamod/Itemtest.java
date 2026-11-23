package fr.kamisoris.zeldamod;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Itemtest {
    public static final DeferredRegister<Item> small_bomb_bagitem =
            DeferredRegister.create(ForgeRegistries.ITEMS, Zeldamod.MOD_ID);

    public static final RegistryObject<Item> small_bomb_bag = small_bomb_bagitem.register("bomb_bag",
            () -> new Bagbomb(new Item.Properties().stacksTo(1), 10));

    public static final DeferredRegister<Item> bomb_item =
            DeferredRegister.create(ForgeRegistries.ITEMS, Zeldamod.MOD_ID);

    public static final RegistryObject<Item> bomb = bomb_item.register("bomb",
            () -> new Item(new Item.Properties()) {
                @Override
                public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
                    ItemStack stack = player.getItemInHand(hand);

                    if (!level.isClientSide) {
                        boolean isMoving = player.isSprinting()
                                || player.walkDist != player.walkDistO
                                || player.getDeltaMovement().horizontalDistanceSqr() > 1.0E-5D;

                        BombEntity bombEntity = new BombEntity(Zeldamodentity.bomb.get(), player, level);

                        if (isMoving) {
                            bombEntity.setPos(player.getX(), player.getEyeY() - 0.15D, player.getZ());
                            bombEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), -3.0F, 1.55F, 0.8F);
                        } else {
                            HitResult hitResult = player.pick(6.0D, 0.0F, false);
                            Vec3 pos = hitResult.getLocation();
                            bombEntity.setPos(pos.x, pos.y + 0.05D, pos.z);
                            bombEntity.setDeltaMovement(Vec3.ZERO);
                        }

                        level.addFreshEntity(bombEntity);

                        if (!player.getAbilities().instabuild) {
                            stack.shrink(1);
                        }
                    }

                    return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
                }
            });
}