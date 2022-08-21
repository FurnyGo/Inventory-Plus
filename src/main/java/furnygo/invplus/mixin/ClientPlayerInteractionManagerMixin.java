package furnygo.invplus.mixin;

import furnygo.invplus.mixininterface.IClientPlayerInteractionManager;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//======================================================
//
//     Original code by 19MisterX98
//
//======================================================
@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin implements IClientPlayerInteractionManager {
    @Shadow
    protected abstract void syncSelectedSlot();

    @Shadow
    public abstract void clickSlot(int syncId, int slotId, int button, SlotActionType actionType, PlayerEntity player);

    @Inject(method = "clickSlot", at = @At("HEAD"), cancellable = true)
    public void onClickArmorSlot(int syncId, int slotId, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci) {

        ScreenHandler screenHandler = player.currentScreenHandler;

        if (screenHandler instanceof PlayerScreenHandler) {
            if (slotId >= 5 && slotId <= 8) {
                int armorSlot = (8 - slotId) + 36;
                if (actionType == SlotActionType.PICKUP && !screenHandler.getCursorStack().isEmpty()) {
                    clickSlot(syncId, 17, armorSlot, SlotActionType.SWAP, player); //armor slot <-> inv slot
                    clickSlot(syncId, 17, button, SlotActionType.PICKUP, player); //inv slot <-> cursor slot
                    clickSlot(syncId, 17, armorSlot, SlotActionType.SWAP, player); //armor slot <-> inv slot
                    ci.cancel();
                } else if (actionType == SlotActionType.SWAP) {
                    clickSlot(syncId, 36 + button, armorSlot, SlotActionType.SWAP, player); //invert swap
                    ci.cancel();
                }
            }
        }
    }
    @Override
    public void syncSelected() {
        syncSelectedSlot();
    }
}
