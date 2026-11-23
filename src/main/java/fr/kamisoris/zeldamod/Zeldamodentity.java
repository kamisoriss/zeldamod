package fr.kamisoris.zeldamod;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Zeldamodentity {
    public static final DeferredRegister<EntityType<?>> bombentity =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Zeldamod.MOD_ID);

    public static final RegistryObject<EntityType<BombEntity>> bomb =
            bombentity.register("bomb", () -> EntityType.Builder.<BombEntity>of(BombEntity::new, MobCategory.MISC)
                    .sized(0.4F, 0.4F)
                    .clientTrackingRange(8)
                    .updateInterval(1)
                    .build("bomb"));
}