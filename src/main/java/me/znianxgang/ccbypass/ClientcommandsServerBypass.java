package me.znianxgang.ccbypass;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientcommandsServerBypass implements ClientModInitializer {
	private static final Logger LOGGER = LoggerFactory.getLogger("clientcommands-server-bypass");

	/**
	 * Classes that have held Client Commands' {@code requirePrivileges()} method.
	 * This mirrors the two mixin targets (see {@link me.znianxgang.ccbypass.mixin.ClientCommandHelperMixin}
	 * and {@link me.znianxgang.ccbypass.mixin.ClientCommandsServerMixin}) and is only
	 * used to produce a useful log line if Client Commands moves the check again.
	 */
	private static final String[] PRIVILEGE_GATE_CLASSES = {
		// Client Commands 2.16.4+
		"net.earthcomputer.clientcommands.command.ClientCommandHelper",
		// Client Commands 2.15 - 2.16.3
		"net.earthcomputer.clientcommands.server.ClientCommandsServer",
	};

	@Override
	public void onInitializeClient() {
		String gateClass = findPrivilegeGate();
		if (gateClass != null) {
			LOGGER.info("Bypass active - Client Commands' server opt-in check (requirePrivileges() in {}) is neutralised", gateClass);
		} else {
			LOGGER.warn("Bypass INACTIVE - could not find requirePrivileges() in any known Client Commands class. "
				+ "Client Commands has probably moved or renamed its permission check again; "
				+ "restricted commands will ask for server installation until this mod is updated.");
		}
	}

	/**
	 * @return the class currently declaring {@code requirePrivileges()}, or {@code null}
	 *         if Client Commands has moved it somewhere this mod does not know about
	 */
	private static String findPrivilegeGate() {
		ClassLoader loader = ClientcommandsServerBypass.class.getClassLoader();
		for (String className : PRIVILEGE_GATE_CLASSES) {
			try {
				// initialize = false: we only need the method table, not the static initialisers
				Class.forName(className, false, loader).getDeclaredMethod("requirePrivileges");
				return className;
			} catch (ClassNotFoundException | NoSuchMethodException ignored) {
				// try the next known location
			}
		}
		return null;
	}
}
