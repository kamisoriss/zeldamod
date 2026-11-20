package fr.kamisoris.zeldamod;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class Bagbomb extends Item {
    public Bagbomb(Properties properties) {
        super(properties);
    }
    public void bagbombuse() {

    }
    protected void stockerbomb()
    {

    }
    protected void stockbomb()
    {
        int stock = 0;
        if (Blocktest.bomb.get().asItem())
        {
            stock++;
        }
    }
}
