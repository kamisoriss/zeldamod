package fr.kamisoris.zeldamod;

import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import javax.swing.text.html.parser.Entity;

import static fr.kamisoris.zeldamod.Itemtest.bomb;
import static fr.kamisoris.zeldamod.Itemtest.small_bomb_bag;

@Mod("zeldamod")
public class Zeldamod
{
    public static final String MOD_ID = "zeldamod";
    public Zeldamod(FMLJavaModLoadingContext context) {
        var modBus = context.getModEventBus();
        Itemtest.small_bomb_bagitem.register(modBus);
        Itemtest.bomb_item.register(modBus);
        ModDataComponents.register(modBus);
        Zeldamodentity.bombentity.register(modBus);
        regristcreattivetab.register(modBus);
        // À brancher sur le modBus dans le constructeur de Zeldamod :
        modBus.addListener(this::registerEntityRenderers);
    }
    private void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(Zeldamodentity.bomb.get(), Bombrenderer::new);    }
    public static final DeferredRegister<CreativeModeTab> regristcreattivetab = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);
    public static final RegistryObject<CreativeModeTab> zeldamodtab = regristcreattivetab.register("zeldamod", () -> CreativeModeTab.builder()
            // Set name of tab to display
            .title(Component.translatable("item_group." + MOD_ID + ".example"))
            // Set icon of creative tab
            .icon(() -> new ItemStack(small_bomb_bag.get()))
            // Add default items to tab
            .displayItems((params, output) -> {
                output.accept(small_bomb_bag.get());
                output.accept(bomb.get());
            })
            .build()
    );
    @SubscribeEvent
    public void buildContents(BuildCreativeModeTabContentsEvent event) {
        // Add to ingredients tab
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(small_bomb_bag);
            event.accept(bomb);
        }
    }
}
