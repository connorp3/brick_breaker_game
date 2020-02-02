# Game Plan
## NAMEs
Connor Penny
John Taylor

### Breakout Variant

### General Level Descriptions

### Bricks Ideas
We plan on having 5 different types of bricks:

- ```Easy Brick```: This brick is light colored, and requires only one hit to be broken. 

- ```Medium Brick```: This brick is darker than the easy brick, but not as dark as the hard brick.
This brick requires 2 hits to be broken, and will change to a lighter color after the first hit.

- ```Hard Brick```: This brick is the darkest color and requires 3 hits to be broken. Like the medium
brick, this brick will change color to become lighter after the first, and again after the second hit.

- ```Moving Brick```: This brick is one that will be an easy brick that moves left and right on the screen.
It is utilized on level 4, and it will move within a specified range.

- ```Power-Up Brick```: This brick will require 2 hits, and once it is broken it will drop a 
random power up to help the player.

### Power Up Ideas

### Cheat Key Ideas
- ```Thanos Cheat```: If the player presses a specific key sequence, half of the bricks
remaining on the screen will be erased.

- ```Level Cheat```: Each level has a specific key sequence associated with it to
take the player directly to that level.

### Something Extra

### Possible Classes
We believe there will be 4 primary classes:
- ```Brick Superclass```: Contains a "death" method that deletes the brick if it has been collided
with.
- ```Power-Up Superclass```: Contains a "show" method that shows the power-up on the
screen once a power-up brick has been hit.
- ```Paddle Class```: Contains a "move" method that allows it to move left and right based
on the user key input. (May be split into two separate methods to control the specific directions).
- ```Ball Class```: Contains a "bounce" method to create a bounce effect once the 
ball collides with an object like a wall or a brick.

