package me.znianxgang.ccbypass.mixin;

import net.earthcomputer.clientcommands.server.ClientCommandsServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Legacy target for the server opt-in check: Client Commands 2.15 through
 * 2.16.3 keep {@code requirePrivileges()} in {@link ClientCommandsServer}.
 *
 * <p>As of 2.16.4 the method moved to
 * {@code ClientCommandHelper} and this class no longer contains it. The
 * injector is therefore optional ({@code require = 0}) instead of an
 * {@code @Overwrite}, so the same jar keeps working on both branches instead of
 * hard-crashing with a mixin apply failure when the method is gone.</p>
 *
 * <p>See {@link ClientCommandHelperMixin} for the current (2.16.4+) target.</p>
 */
@Mixin(ClientCommandsServer.class)
public class ClientCommandsServerMixin {

	@Inject(method = "requirePrivileges", at = @At("HEAD"), cancellable = true, require = 0)
	private static void clientcommandsServerBypass$skipOptInCheck(CallbackInfo ci) {
		ci.cancel();
	}
}
