package furnygo.invplus.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//======================================================
//
//     Original code by 19MisterX98
//
//======================================================
@Mixin(targets = "net/minecraft/screen/PlayerScreenHandler$1")
public class PlayerArmorSlotMixin {

    @Inject(method = "getMaxItemCount", at = @At("HEAD"), cancellable = true)
    private void onGetMaxItemCount(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(64);
    }

    @Inject(method = "canInsert", at = @At("HEAD"), cancellable = true)
    private void onCanInsert(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }

    @Inject(method = "canTakeItems", at = @At("HEAD"), cancellable = true)
    private void onCanTakeItems(PlayerEntity playerEntity, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
}
