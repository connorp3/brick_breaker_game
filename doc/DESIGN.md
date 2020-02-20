# Game Design Final
### Names

Connor Penny - cgp19

John Taylor - jmt86

## Team Roles and Responsibilities

 * Team Member #1 - Connor Penny
 
 I implemented a portion of the features and functions of the Breakout game, including the 
 Ball class, the PowerUp class, the Block superclass, the CollidableObjects abstract class. I
 also worked on GamePlay class a lot, including a made a major effort to refactor it which resulted
 in a smaller update method, and fewer collision handling methods.

 * Team Member #2


## Design goals cgp19

The goal of this project was to implement a functional, traditional Breakout game that took in key inputs 
from the user and performed game functions based on those inputs. It was designed so that each major 
game element in the Breakout game had its own class (Paddle, Block, Ball, PowerUp, StatusDisplay) which
were then kept as instance variable objects in a main GamePlay class. By doing this, we hoped to keep
the attributes and functions of these game elements as "shy" as possible when they were manipulated in
GamePlay. 

We tried to reduce dependencies of the GamePlay classes on lower level classes, and the biggest concern for
this was the interaction between game elements in the game. An abstraction was necessary that would
make these collisions easily implemented in GamePlay, and this was the goal of CollidableObject.

Additionally, we made efforts to extract methods where possible to reduce duplicate code and to name
variables so that their intentions were clear.


#### What Features are Easy to Add cgp19

We tried to adhere to the Open-Closed design principle through the implementation of the Block superclass
and the CollidableObject abstract class. These would make the addition of new game elements (different types
of blocks, extra balls, etc) easier to add because they could extend CollidableObject and their collision
functionality could be called in a for loop with many other CollidableObject objects.

Specifically, the Block superclass makes it quite simple to create new blocks, as you simply have to call 
the superclass's constructor, set a color, set a hit limit for the brick, and then implement the 
collision method to give the brick a function upon being collided with. Other methods for the block could
also be implemented that are not related to a collision (perhaps movement).


## High-level Design cp19

This program's main class is the GamePlay class. This class holds the game elements of the BreakOut game
as instance variables. Each of these game elements is its own class which handles key inputs and object 
updates specific to that game element within its own class. For example, the Ball object handles the
updating of its own position, its collision with the sides of the screen, resetting when it passes the
bottom position, changing speed, and the cheat keys that affect its behavior.

We attempted to make GamePlay handle only the functions of the game that weren't completely specific to a
single game element. For example creates the scene for the game, displays an initial title screen, reads in
block configurations for levels, switches between the levels, and checks for collisions between any of the
CollidableObject objects

#### Core Classes

######GamePlay cgp19
This class facilitates the BreakOut game by creating a Scene with an animation Timeline. New instance
variables are constructed of each game element and are added to the Scenes root. This class also holds
the current level as an instance variable because it is an important attribute of the game

We wanted the main purpose of GamePlay to be handling only the functions of the game that weren't completely 
specific to a single game element. For example it creates the scene for the game, displays an initial 
title screen, reads in block configurations for levels, switches between the levels, and checks for 
collisions between any of the CollidableObject objects. It also is responsible for handling key inputs
that switch between levels, add and remove game elements from the scene, and pause/play/start the game. 
Any changes to specific game elements in GamePlay should be done using general method calls from various 
game element classes and perhaps some logic. Information for the game relevant to the method for a game
element class is then passed to the class as a parameter (for example, elapsedTime is passed to Ball
from GamePlay in the update method to change the movement of the ball correctly).

######PowerUp cgp19
This class represents a power-up in the breakout game. Its main purpose upon being generated after elimination
of a PowerUpBlock object is to move downward in the scene toward the paddle. If it collides with the Paddle,
its collision method is called, and a random Power-Up is applied to some feature of the game. These power-ups
are:

- Increase the score by 50
- Increase the width of the paddle
- Slow the ball down

In order to be able to make those changes in the game, a PowerUp object is constructed with the Paddle,
the Ball, and the StatusDisplay of the game passed as parameters. This allows GamePlay to "tell" rather
than "ask" the PowerUp to perform the necessary powerup functionality.

######CollidableObject cgp19

This abstract class is extended by any game element class that collides with another object (every game
element). Its purpose is to simplify the checking of collisions between game elements in GamePlay.
The static methods intersectTop and intersectSide take CollidableObject objects as parameters and checks
whether they are intersecting on top or on the side. Thus, regardless of the specific kind, any two game
elements can be checked for a collision with this one method call. Additionally, every class that extends
CollidableObject can implement its own collision method, so one call to collision in any for loop can
apply to any CollidableObject object in the GamePlay class.


######Block TODO: explain this class's purpose and interactions with other classes


######Paddle TODO: explain this class's purpose and interactions with other classes


######Ball TODO: explain this class's purpose and interactions with other classes


######StatusDisplay TODO: explain this class's purpose and interactions with other classes



## Assumptions that Affect the Design TODO: complete this section

what assumptions or decisions were made to simplify your project's design?

#### Features Affected by Assumptions TODO: complete this section

how did these assumptions affect the addition of new features?

## New Features HowTo - TODO: Moving blocks, Substantial addition (two-player), Thanos key

