package ua.behindthatteam.mixin.client;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ua.behindthatteam.OffhandBlockerClient;

@Mixin(ClientPlayerInteractionManager.class)
public class OffhandBlockerMixin {

	@Inject(method = "interactBlock", at = @At("HEAD"), cancellable = true)
	private void onInteractBlock(ClientPlayerEntity player, Hand hand, BlockHitResult hitResult,
								 CallbackInfoReturnable<ActionResult> cir) {
		if (hand == Hand.OFF_HAND && OffhandBlockerClient.isOffhandBlocked) {
			cir.setReturnValue(ActionResult.PASS);
		}
	}

	@Inject(method = "interactItem", at = @At("HEAD"), cancellable = true)
	private void onInteractItem(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		if (hand == Hand.OFF_HAND && OffhandBlockerClient.isOffhandBlocked) {
			cir.setReturnValue(ActionResult.PASS);
		}
	}
}