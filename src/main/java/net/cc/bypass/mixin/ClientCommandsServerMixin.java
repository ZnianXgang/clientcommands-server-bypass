package net.cc.bypass.mixin;

import net.earthcomputer.clientcommands.server.ClientCommandsServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * Makes {@link ClientCommandsServer#requirePrivileges()} a no-op,
 * bypassing the server opt-in requirement added in Client Commands 2.15.
 *
 * <p>All restricted commands (cfindblock, careastats, csignsearch, cfind,
 * cglow entities, cgetdata entity, cghostblock) call this single method.
 * By preventing it from throwing, every command becomes usable without
 * the server having Client Commands installed.</p>
 */
@Mixin(ClientCommandsServer.class)
public class ClientCommandsServerMixin {

	/**
	 * @reason This is the only change needed to bypass <em>all</em> restricted
	 * commands. An {@code @Inject(cancellable = true)} at HEAD would also work,
	 * but {@code @Overwrite} makes the intent explicit: we want zero side effects
	 * from this method, not merely early-return.
	 */
	@Overwrite
	public static void requirePrivileges() {
		// no-op — allow all commands regardless of server opt-in status
	}
}
