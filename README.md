# Client Commands Server Bypass

Bypasses the server opt-in requirement introduced in clientcommands 2.15, allowing restricted commands to work on servers that don't have clientcommands installed.

## How it works

clientcommands 2.15 added a central check — `ClientCommandsServer.requirePrivileges()` — called by every restricted command (`/cfindblock`, `/careastats`, `/csignsearch`, `/cfind`, `/cglow entities`, `/cgetdata entity`, `/cghostblock`). Normally it throws unless the server registers the `clientcommands:opt_in` plugin channel or the player is opped.

This mod uses a Mixin to overwrite that method with a no-op, so the check always passes. No server-side installation is needed — just install it on the client alongside clientcommands.

## Usage

1. Install [Fabric Loader](https://fabricmc.net/use/).
2. Place both **clientcommands** and **this mod** in your `mods` folder.
3. Launch the game — restricted commands now work on any server.

No configuration, no commands, no server-side changes.

## Requirements

- Minecraft 26.2
- Fabric Loader ≥0.19.3
- clientcommands ≥2.15

## Build from source

```bash
./gradlew build
```

Output: `build/libs/clientcommands-server-bypass-1.0.jar`
