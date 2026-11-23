package fr.kamisoris.zeldamod;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Bagbomb extends Item {
    private final int maxCapacity;

    public Bagbomb(Properties properties, int maxCapacity) {
        super(properties);
        this.maxCapacity = maxCapacity;
    }

    protected int getBombCount(ItemStack bagStack) {
        return bagStack.getOrDefault(ModDataComponents.BOMB_COUNT.get(), 0);
    }

    protected void setBombCount(ItemStack bagStack, int count) {
        bagStack.set(ModDataComponents.BOMB_COUNT.get(), Math.max(0, Math.min(count, maxCapacity)));
    }

    protected boolean canStoreBomb(ItemStack incoming) {
        return !incoming.isEmpty() && incoming.is(Itemtest.bomb.get());
    }

    protected boolean storeOneBomb(ItemStack bagStack, ItemStack incoming) {
        if (!canStoreBomb(incoming)) {
            return false;
        }
        int current = getBombCount(bagStack);
        if (current >= maxCapacity) {
            return false;
        }
        setBombCount(bagStack, current + 1);
        incoming.shrink(1);
        return true;
    }
    @Override
    public boolean overrideOtherStackedOnMe(
            ItemStack bagStack,
            ItemStack otherStack,
            Slot slot,
            ClickAction action,
            Player player,
            SlotAccess access
    ) {
        if (action == ClickAction.PRIMARY && storeOneBomb(bagStack, otherStack)) {
            return true;
        }
        return super.overrideOtherStackedOnMe(bagStack, otherStack, slot, action, player, access);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack bagStack = player.getItemInHand(hand);
        int count = getBombCount(bagStack);

        if (count <= 0) {
            return InteractionResultHolder.fail(bagStack);
        }

        if (!level.isClientSide) {
            setBombCount(bagStack, count - 1);
        }

        return InteractionResultHolder.sidedSuccess(bagStack, level.isClientSide());
    }
}