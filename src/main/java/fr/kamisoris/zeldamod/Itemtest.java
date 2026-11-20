package fr.kamisoris.zeldamod;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Itemtest
{
    public static final DeferredRegister<net.minecraft.world.item.Item> small_bomb_bagitem = DeferredRegister.create(ForgeRegistries.ITEMS,"zeldamod");
    public static final RegistryObject<Item> small_bomb_bag = small_bomb_bagitem.register("bomb_bag", () -> new Item( new Bagbomb.Properties()));
}
