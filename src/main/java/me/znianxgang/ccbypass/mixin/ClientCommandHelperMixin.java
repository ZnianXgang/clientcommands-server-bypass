package me.znianxgang.ccbypass.mixin;

import net.earthcomputer.clientcommands.command.ClientCommandHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Makes {@code ClientCommandHelper.requirePrivileges()} a no-op.
 *
 * <p>Client Commands 2.16.4 moved the server opt-in check out of
 * {@code ClientCommandsServer} into {@code ClientCommandHelper} (keeping the same
 * name and signature), as part of reworking server permissions for server-side
 * {@code /cfinditem}. That is why the old {@code ClientCommandsServer}-based
 * mixin could no longer apply. This mixin targets the method in its current
 * location.</p>
 *
 * <p>Every restricted command ({@code /cfindblock}, {@code /careastats},
 * {@code /cfind}, {@code /cglow entities}, {@code /cgetdata entity},
 * {@code /cghostblock}) funnels through this single method, so cancelling it at
 * {@code HEAD} is enough to make them usable regardless of whether the server
 * has Client Commands installed or has registered the {@code clientcommands:opt_in}
 * channel.</p>
 *
 * <p>{@code require = 0} keeps this injector optional: on Client Commands
 * 2.15&ndash;2.16.3 the method still lives in {@code ClientCommandsServer}, and
 * {@link ClientCommandsServerMixin} handles that case instead. Whichever target
 * exists at runtime wins, so a single build covers both branches.</p>
 */
@Mixin(ClientCommandHelper.class)
public class ClientCommandHelperMixin {

	@Inject(method = "requirePrivileges", at = @At("HEAD"), cancellable = true, require = 0)
	private static void clientcommandsServerBypass$skipOptInCheck(CallbackInfo ci) {
		ci.cancel();
	}
}
