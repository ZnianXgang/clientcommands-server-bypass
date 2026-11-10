# Client Commands Server Bypass

Bypasses the server opt-in requirement introduced in Client Commands 2.15 and intercepts the `clientcommands:command_execution` custom payload, allowing unrestricted use of all clientcommands features on any server.

## How it works

Client Commands 2.15 added a central check — `requirePrivileges()` — called by every restricted command (`/careastats`, `/cfind`, `/cfindblock`, `/cglow entities`, `/cgetdata entity`, `/cghostblock`). Normally it throws unless the server registers the `clientcommands:opt_in` plugin channel or the player is opped.

Client Commands **2.16.4 moved that method** from `ClientCommandsServer` into `ClientCommandHelper` (same name and signature), while reworking the server-permission system for server-side `/cfinditem`. The previous version of this mod used `@Overwrite` on `ClientCommandsServer.requirePrivileges()`, so once the method disappeared the mixin could not apply and the game failed to start. This mod now handles both locations:

1. **Bypass opt-in check** — `ClientCommandHelperMixin` cancels `ClientCommandHelper.requirePrivileges()` at `HEAD` (Client Commands 2.16.4+), and `ClientCommandsServerMixin` does the same for `ClientCommandsServer.requirePrivileges()` (Client Commands 2.15 – 2.16.3). Both injectors are marked `require = 0`, so whichever location exists at runtime is patched and the other is skipped instead of crashing.

2. **Block command execution payload** — injects into `sendCommandExecutionToServer()` to cancel outgoing `clientcommands:command_execution` packets, preventing the server from receiving the content of commands you execute, even if the server has registered the `command_execution` channel.

No server-side installation is needed — just install it on the client alongside Client Commands.

## Usage

1. Install [Fabric Loader](https://fabricmc.net/use/).
2. Place both **Client Commands** and **this mod** in your `mods` folder.
3. Launch the game — restricted commands now work on any server.

No configuration, no commands, no server-side changes.

If Client Commands ever moves the check again, the game still starts; the log will contain a `Bypass INACTIVE` warning naming the mod version instead of a mixin crash.

## Requirements

- Minecraft ≥26.2 (this build is compiled and tested against 26.3)
- Client Commands ≥2.15 (built and tested against 2.16.4)
- Fabric Loader: this mod declares no loader requirement of its own — use whatever your Client Commands version needs. Client Commands 2.16.4 bundles libraries that refuse to load below Loader 0.19.5, so 0.19.5+ is required in practice.

## Build from source

```bash
./gradlew build
```

Output: `build/libs/clientcommands-server-bypass-1.2.jar`
