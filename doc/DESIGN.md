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

 * Team Member #2 - John Taylor
 
 I implemented a portion of the features of the Breakout game, including the Paddle class, the Block subclasses,
 the StatusDisplay class, and I helped with bug fixes within the other classes as they came. I helped work on the 
 refactoring efforts within the GamePlay class, and I helped when it came to the visual aspect of our game.

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

## High-level Design cgp19

This program's main class is the GamePlay class. This class holds the game elements of the BreakOut game
as instance variables. Each of these game elements is its own class which handles key inputs and object 
updates specific to that game element within its own class. For example, the Ball object handles the
updating of its own position, its collision with the sides of the screen, resetting when it passes the
bottom position, changing speed, and the cheat keys that affect its behavior.

We attempted to make GamePlay handle only the functions of the game that weren't completely specific to a
single game element. For example creates the scene for the game, displays an initial title screen, reads in
block configurations for levels, switches between the levels, and checks for collisions between any of the
CollidableObject objects

## High-Level Design jmt86

The Main class of this project just starts the game, and relies completely on the GamePlay class,
the one where the majority of our efforts were focused initially. This is the class that creates the stage,
scenes, and initializes these scenes to make the game playable. The GamePlay class reads in a block configuration from 
a file in the data folder and creates a scene (level) based on this configuration. The GamePlay class also handles most 
of the logic of the game, like switching between levels, handling cheat keys, and updating the current time step 
based on object collisions.

From there, the Block, Paddle, and Ball classes are the next most prominent. These classes each handle their own input
and update themselves according to different aspects of the game (mainly upon being hit). Overall, we tried to 
keep everything held only within the classes it pertained to and did not try to have repeated code or very long
methods.

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


######Block jmt86

This class represents a Block within the game. It is the object that the player tries to destroy and it 
extends the CollidableObject class. It takes on the shape of a Rectangle with many attributes to
initialize its size, color, position, and hit count. This is a superclass of the EasyBlock, MediumBlock, 
and HardBlock. The Block class contains methods to return its hit count, its position, and its size. There
is an eliminateBlock method to adjust the block based on hits (change color or eliminate the block if hit 
limit is reached). There is also a method to override the collision method within the CollidableObjects
abstract class, which just calls the eliminateBlock method. 

The subclasses EasyBlock, MediumBlock, HardBlock, and PowerUpBlock all extend the block class, meaning they 
also get the attributes from the CollidableObject abstract class. The only real difference in these subclasses
is the color, which is different to initialize every block, and the hit limit, which increases as the difficulty
of the block increases. The PowerUp block is different though because once its hit limit is reached, it drops
a power up that must be caught by the paddle in order to take effect. This PowerUp block also has a very different
color to make it stand out in the game.


######Paddle jmt86

The Paddle class also extends the CollidableObjects abstract class since we want it to be able to interact with
the ball and the power ups. This class also uses the shape of a rectangle, and the constructor of this class creates
this rectangle based on the final variables to determine the initial size and position of the paddle. This class also 
contains two boolean variables to determine if the paddle should move left or right, both of which are
initialized to false.

This class contains methods to return the size and position of the paddle which are used to 
determine if the paddle is touching a wall in the rWallReached and lWallReached methods. As long
as the walls are not reached, the paddle will respond to user input (handled by the handleInput method).
The moveRight and moveLeft methods will increase the paddle's x position by either 10 or -10.

There is also a method to change the width of the paddle by a specified multiplier which is used
when a specific power up is caught. This method is also used to initialize the size of the paddle for different 
levels, since the paddle gets smaller as the player moves from level to level. There is also an override to the 
collision method.


######Ball jtm86

The Ball class also extends the CollidableObject abstract class since we want it to be considered
as an object that collides with other objects within the game. The ball is implemented in the shape
of a circle, giving it position and radius attributes.
The Ball is initialized with final size
and position variables. The starting Y-velocity is also set, but the X-velocity is randomized every time the 
ball is shot off of the user's paddle using the getRandomInRange method. Initially,
the ball is set in the resetBall position since we want the user to shoot the ball first. 

The main inputs handles by the ball class in the handleInput method are KeyCode.UP and KeyCode.R. 
The UP input shoots the ball from the paddle with a final y velocity and a random x-variable. The R
input reset the ball to be "stuck" to the user's paddle, where it must then be shot. The S and F input keys
are used to either Slow down the ball or make the ball Faster (respectively) by adjusting
both the x and y velocity in the changeSpeed method.

The other primary chunk of methods deals with the collision physics, there are methods to adjust
the ball if it is hitting a side wall, hitting a top wall, or encountering a situation where there
is a horizontal or vertical collision, which are determined by the overridden collision method.

Finally, there is a method to determine whether the ball is passing the bottom wall of the scene.
These methods are all checked and updated in the update method of the ball which is updated on each
timestep of the game.

######StatusDisplay jmt86

The StatusDisplay class is the class that controls the top bar of the scene which displays the 
current number of lives, the current level, and the current score. The constructor initializes these items and places them
in the correct area using the createStatusPiece method and the setTextFont method, which set the 
correct font and location for each of these counters.

There are methods to get the current life count, level, and score, and there are also methods
to update the life count, level, and score based on situations within the game. The score is updated
when a block is hit or a power up is caught. The lives are updated when the ball passes the bottom wall
or the L key is pressed. The level is updated when the 1, 2, or 3 cheat keys are pressed to take the
user to a specific level, or if the user clears all of the blocks on a specific level.

This class also contains the methods displayFinalStatus, createCloseWindowMessage,
createGameOverMessage, and showCloseWindowMessage. These methods all work together to determine if
the user has run out of lives (where it displays the loss message and the close window message) or if
the user has cleared all the blocks on the last level (where it displays the win message and the close
window message). 

Additionally, this class contains a method to change the life count if the L cheat key is pressed.


## Assumptions that Affect the Design jmt86

We tried ot limit our assumptions in the design of this game, however, they were not all
avoidable. Here are our assumptions:

    1. The block configurations would be read in by a text file that is contained in the data folder. 
    2. The cheat codes would be hidden

#### Features Affected by Assumptions jmt86

The configurations above will be addressed by number in this section.
    
    1. This assumption limits our method of reading in configurations. For example, if a user
    wanted to import their own configuration they would have to create a text file and put it in the data
    folder. It might be easier to allow the user to provide a link to a specific configuration so that
    they did not have to toy around with the data files.
    
    2. The cheat codes in this project are not given to the user, so we assumed that the user
    would be able to eventually figure them out in some manner. Without the cheat keys, the user does
    not have the ability to skip to levels, change the ball speed on command, or add lives to keep their game
    going longer. 
## New Features HowTo

There are 3 main features we wanted to add but were unable to:
    
    1. One feature we wanted to add was a configuration with moving blocks to make the level
    more interesting to the player. This would be simple to add by creating a specific text configuration that
    was built on blocks noted by an 'M', which would tell the program to make these blocks a MovingBlock object,
    which would be a subclass of the Block class. The only difference in these blocks would be that they
    would move across the screen, which would be a simple feature to add now that we are
    comfortable with how to make objects move on the screen and bounce off the walls.
    
    2. Another feature we wanted to add (a potential substantial feature) was to create a "Thanos
    cheat key" which would erase half of the blocks on the screen (based on the Avengers movie character
    Thanos). This would be simple to add because it would just be adding a conditional for a KeyCode input
    and making it act similar to the D cheat key (which erases the top-left-most block) but it would
    just eliminate half of the blocks left on the screen.
    
    3. The final feature we wanted to add was to add in a second paddle and a second ball to make the 
    game two-player. This feature seems like it would be simple enough to implement because it would just be a
    matter of creating another Paddle object and another Ball object, we would just have to adjust the second-player
    Paddle to respond to the A and D as inputs instead of left and right. This would mean
    changing the D cheat key, but all of this would be simple enough to do given enough time.
