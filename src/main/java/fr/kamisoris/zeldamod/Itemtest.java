package fr.kamisoris.zeldamod;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Itemtest
{
    public static final DeferredRegister<Item> small_bomb_bagitem = DeferredRegister.create(ForgeRegistries.ITEMS,"zeldamod");
    public static final RegistryObject<Item> small_bomb_bag = small_bomb_bagitem.register("bomb_bag", () -> new Item( new Bagbomb.Properties()));
    public static final DeferredRegister<Item> bomb_item = DeferredRegister.create(ForgeRegistries.ITEMS,"zeldamod");
    public static final RegistryObject<BlockItem> bomb = bomb_item.register("bomb", () -> new BlockItem(Blocktest.bomb.get(), new Item.Properties()));
}
