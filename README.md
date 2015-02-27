# CMPU-203
# game1 manual by Elijah Lucey
## A field of play where the blocks move.
Ideally, this will provide for more freedom than, for example, the grid 2048 uses. I'd like to just have a small protagonist block made out of a few ASCII characters that moves around the 2D field of play, along with similarly-sized enemy blocks.
## A set of "live" blocks that are player controlled. (This "set" may contain one block.)
As stated above, the "live", player controlled block should be constructd out of some ASCII characters. I don't want it to be big like 2048 blocks, but rather just a few characters, but I'm willing to be flexible.
## A set of "dead" blocks that are no longer player controlled.
The "dead" blocks are the enemies that have been extinguished by the player character.
## A scoring system.
The scoring system is simple--1 point for each enemy defeated, and enemies are defeated simply by reaching them with the player character.
## A win or fail state.
This is where I cannot commit to anything quite yet--I am considering adding stationary blocks that shoot missiles at the player character, and the game is over once the player gets hit by X missiles. In this case, there will be no win state--the game continues until the player is hit by enough missiles. However, if this proves to be too complicated to implement, there will instead be no fail state but a win state reached by defeating all the enemies in a round.
## A control mechanism.
The player character will be controlled only with the arrow keys, moving up, down, left, or right.

##General description
I will describe this game in its most ambitious form for now, and edit this later if I end up having to simplify things. You, the player, control a small block that can move anywhere on the field of play in 2D directions using the arrow keys. There are also similarly-sized enemy characters moving around the field of play randomly. These enemies have "set up" a few blocks around the field of play that shoot small missiles at the player. If the missile collides with the player, damage is taken by the player until health reaches zero. If the missile collides with an enemy, the enemy vanishes and the player gains a point. Enemies can also be defeated by making contact with the player character. Thus, the game at its core consists of chasing enemies while avoiding missiles.
