# Scenes:

## Behaviour

Controls

- Space - Changes Implementation used

The orange cube is the target.

The plane allows for wrapping arround the field.

The blue NPCs will chase and the red NPCs will flee.

Implementation I

- All Kinematic based movement behaviours

Implementation II

- All Steering based movement behaviours



## CTF

The NPCs are by default wandering arround to randomly picked location

(The rest isnt implemented for my submission)

A random NPC is picked to capture to flag and goes straight to it. If contact the flag, will make a bee line to the homeland to win the game.

An enemy will pursue and try to contact the NPC to tag and freeze him. Once tagged, an ally is picked to unfreeze them by contact and the code repicks a capturer.
