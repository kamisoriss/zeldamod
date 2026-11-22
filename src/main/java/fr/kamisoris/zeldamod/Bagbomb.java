package fr.kamisoris.zeldamod;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class Bagbomb extends Item {
    public Bagbomb(Properties properties) {
        super(properties);
    }
    public void bagbombuse() {

    }
    protected boolean stockerbomb(ItemStack bagStack, ItemStack bombStack, int maxCapacity) {
        if (bombStack.isEmpty() || !bombStack.is(Blocktest.bomb.get().asItem())) {
            return false;
        }

        int current = bagStack.getOrDefault(ModDataComponents.BOMB_COUNT.get(), 0);
        if (current >= maxCapacity) {
            return false;
        }

        bagStack.set(ModDataComponents.BOMB_COUNT.get(), current + 1);
        bombStack.shrink(1);
        return true;
    }
}
