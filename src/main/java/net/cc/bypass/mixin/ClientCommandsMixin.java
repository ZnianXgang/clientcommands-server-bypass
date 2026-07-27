package net.cc.bypass.mixin;

import net.earthcomputer.clientcommands.ClientCommands;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Prevents {@link ClientCommands#sendCommandExecutionToServer(String)} from sending
 * command content to the server via the {@code clientcommands:command_execution} custom payload.
 *
 * <p>On servers that register the {@code command_execution} channel, clientcommands
 * would otherwise transmit the full command string of every executed clientcommands command.
 * This mixin ensures the payload is never sent, blocking all three dispatch paths
 * (chat input via {@code ChatScreenMixin}, macro via {@code ParsedEntry}, and macro via
 * {@code LazyEntry}) since they all converge on this single method.</p>
 */
@Mixin(ClientCommands.class)
public class ClientCommandsMixin {

	@Inject(method = "sendCommandExecutionToServer", at = @At("HEAD"), cancellable = true)
	private static void onSendCommandExecutionToServer(String command, CallbackInfo ci) {
		ci.cancel();
	}
}
