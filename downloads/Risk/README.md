# CS2010 Group Project submission

*Create a Board Game*

*We decided to make Risk, using Java*

## How to play

**Game:**
At the start of the game you will be presented with a map with a bunch of different territories.
Each territory has territories that are adjacent to it. If a territory is touching generally they are adjacent, but
there are exceptions to this. 

**Territory:**
Each player can own a territory, and each territory has an army, and the strengh of that army is 
determind by the number and power of the units that are in it.

**Setup phase:**
*setup phase is only played once at the beggining of the game is there to set up the board for the rest of the match*
At the beggining of a game each player can place 1 unit on the board at a time from a pool of units they
are given at the start (based on the number of players). Each player has a turn to do so, and this is done 
unit all territories on the board have been taken by a player. At the start of this phase you cannot place 
a unit on another players or your own territroy if there are availbe territories with no owner on the board. 

Once all territories are taken you can then place units on territories you own. There is no restriction to 
where or how many units you place. Do this until all players have no units left to place. This will mark the
end of the setup phase. 

**the next three phases are in order. Each player plays out these three phases on their turn. A player must play the reinforce phase, but can skip the *attack* and *fortify* phases**

**reinfoce phase**
A player, at the start of their turn, will receive units that they can place on their owned territories. To move to the next 
phase, all units must be placed. If a unit is placed by accident or the player changes there mind, it can be removed from that 
territory and placed elsewhere.

**Attack phase**
In the attack phase a player is given the oppotunity to fight against other players. One territory (owned) can attack another 
territory (not owned). The attacker will use a maximuim 3 units, from a given territory, in a fight and cannot fight if that territory
has only 1 unit (there must always be 1 unit in a territory). This means that if the attacker choose to fight with a territory that has 
only 3 units in it, then only 2 can be commited to the fight (this reduces the odd of winning that fight).

The defending territory can only use up to 2 units to defend, but will use all units. For example, if a defending territory has 
only 1 units availbe, then that 1 unit can be used to fight. 

In a 1unit v 1unit the defender has better odd of winning, meaning the attacker can only have heigher odds if they play more units than
the defender, hence the name [*Risk*](https://en.wikipedia.org/wiki/Risk_(game)).

A player can attack any territory (not owned) any number of times during this phase on their turn. The is no limit. If an attacker defeats all
units in an enemy territory, then the attacker wins it. They must move all units used to fight the last battle over. From the attacking territory
More units can be moved over if desired, however, 1 must remain behind.

**Fortify phase**
*not implemented*
In this phase will be able to move your units to any adjacent territory.





## Controls:

**Left-Mouse Button**
during the game you will always be able to use the right mouse button to select a territory. 
This will highlight it yellow. Enemy territories next to it will be highlighted red and friendly territories 
will be highlighted green.

**Setup Phase**
during this phase if you select a territory *(above)* and end turn a unit will be placed there

**Placement Phase**
in this phase select a territory and press either *num key 1* to add units to that territory or 
*num key 2* to remove from that territory. **Note:** You can only add as many units as you have been given, and 
you can also only remove what you have placed this turn in this phase

**Attack Phase**
using the selection controls select a friendly territory you want to use to attack and the using *right mouse button* 
select the territory you want to fight. This will instantly initiate the fight. Make sure it is what you want. If you win 
the minimum amount of units will be moved over.

**Reinforcement Phase**
*to be implemented*

Maybe you will be able to drag and drop units...

### Built using [libdgx](https://libgdx.badlogicgames.com/)











