# CMPU-203
# game1 manual by Elijah Lucey
## A field of play where the blocks move.
The field of play is an 80x25 grid (1 square unit being the size of an ASCII character).
## A set of "live" blocks that are player controlled. (This "set" may contain one block.)
The player controlled character is the Hero, represented on screen by "GOOD GUY", and punctuation corresponding to its health. An exclamation point is 3 hp (full), a period is 2 hp, and no punctuation is 1 hp. There are also enemy characters represented on screen as "BAD GUY". There are also missiles (represented as "HATE") that move quickly in a horizontal direction from either the right or left side of the screen. They will destroy any item that comes in contact with them, including other missiles.
## A set of "dead" blocks that are no longer player controlled.
When an enemy is defeated (either by the Hero or a missile), it disappears off the screen and the score (visible on the bottom left corner of the screen) increases by 1.
## A scoring system.
The scoring system is simple: 1 point for each enemy defeated, and enemies are defeated simply by reaching them with the player character. Enemies are also defeated by missiles, and the player is awarded a point for these kills as well.
## A win or fail state.
There is no win state, only a fail state that is triggered when the Hero reaches (or falls below) 0 hp by being hit by 3 different missiles.
## A control mechanism.
The player character will be controlled only with the arrow keys, moving up, down, left, or right.

##General description
You, the player, control a small block that can move anywhere on the field of play in 2D directions using the arrow keys. There are also similarly-sized enemy characters moving around the field of play randomly. High speed missiles shoot out from both the left and right sides of the screen. If the missile collides with the player, damage is taken by the player until health reaches zero (starting at 3). If the missile collides with an enemy, the enemy vanishes and the player gains a point. Enemies can also be defeated by making contact with the player character. Thus, the gameplay consists of chasing enemies while avoiding missiles.
