# Create: Stressizers — source project

Target: Minecraft 1.21.1 / NeoForge 21.1.x / Create 6.0.10 / Java 21.

This ZIP contains the **mod source project**, not a ready-to-install JAR. Build it first, then install the generated JAR together with the matching NeoForge and Create versions.

## How the Stressizers work

The Stressizers are **shaft-driven pass-through kinetic components**. They do not generate rotation themselves, and they do not use redstone.

- Place a Stressizer in a Create rotational network, aligned with the shaft axis.
- When rotational power reaches it, it contributes stress capacity based on its tier and the network's actual RPM.
- When the network stops, it contributes zero stress capacity.
- Its kinetic connection transfers rotation through the block along its facing axis.

## Four tiers and starting balance

The capacities below are the expected values at 16 RPM. Capacity scales with the actual RPM passing through the block.

| Block | Capacity at 16 RPM |
|---|---:|
| Andesite Stressizer | 256 SU |
| Brass Stressizer | 1,024 SU |
| Diamond Stressizer | 4,096 SU |
| Netherite Stressizer | 16,384 SU |

The per-RPM values can be tuned in `StressizerContent.java`.

## Build

1. Install a Java 21 JDK.
2. Open this folder in IntelliJ IDEA as a Gradle project.
3. Let Gradle sync and download NeoForge/Create dependencies.
4. Run the Gradle task `build`.
5. The output JAR should appear in `build/libs/`.
6. Put that JAR in your Minecraft `mods` folder alongside matching NeoForge and Create versions.

This source project does not include a Gradle wrapper. Use an installed Gradle version compatible with the NeoForge ModDev plugin, or generate a wrapper from IntelliJ's Gradle tools.

## First-pass notes

- The block models currently use Create casing and vanilla block textures as placeholders; dedicated textures/models can be added later.
- The stress-capacity integration is implemented against Create's 1.21.1 kinetic network behavior. It still needs a local Gradle compile and in-game test against the exact installed Create/NeoForge build before being treated as release-ready.
