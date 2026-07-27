# Client Commands Server Bypass

Bypasses the server opt-in requirement introduced in Client Commands 2.15 and intercepts the `clientcommands:command_execution` custom payload, allowing unrestricted use of all clientcommands features on any server.

## How it works

Client Commands 2.15 added a central check — `ClientCommandsServer.requirePrivileges()` — called by every restricted command (`/cfindblock`, `/careastats`, `/csignsearch`, `/cfind`, `/cglow entities`, `/cgetdata entity`, `/cghostblock`). Normally it throws unless the server registers the `clientcommands:opt_in` plugin channel or the player is opped.

This mod uses two Mixins to restore full control:

1. **Bypass opt-in check** — overwrites `requirePrivileges()` with a no-op, so the check always passes regardless of server opt-in status.

2. **Block command execution payload** — injects into `sendCommandExecutionToServer()` to cancel outgoing `clientcommands:command_execution` packets, preventing the server from receiving the content of commands you execute, even if the server has registered the `command_execution` channel.

No server-side installation is needed — just install it on the client alongside Client Commands.

## Usage

1. Install [Fabric Loader](https://fabricmc.net/use/).
2. Place both **Client Commands** and **this mod** in your `mods` folder.
3. Launch the game — restricted commands now work on any server.

No configuration, no commands, no server-side changes.

## Requirements

- Minecraft 26.2
- Fabric Loader ≥0.19.3
- Client Commands ≥2.15

## Build from source

```bash
./gradlew build
```

Output: `build/libs/clientcommands-server-bypass-1.1.jar`
