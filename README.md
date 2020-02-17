game
====

This project implements the game of Breakout. The primary goal of this game is to hit every block on the 
screen with the ball in order to clear the screen and move on to the next level. The player's score increases
with every block that is hit, and the goal is to clear all the blocks on the last level to win the game.

- Set ```data``` folder as Resources Root.
- Set ```test``` folder as Test Sources Root.
- Make sure to import the TestFX library from Maven: ```org.testfx:testfx-junit5:4.0.16-alpha```

#####Names:
- John Taylor - jmt86
- Connor Penny - cgp19

---
### Timeline

Start Date: 01-29-2020

Finish Date: 02-17-2020

Hours Spent: 55-60 Hours

---
### Resources Used
- StackOverflow
- Office Hours

---
### Running the Program

Main class: ```Main.java```

Data files needed: 
- ```level1.txt``` - A block configuration for the first level of the game.
- ```level2.txt``` - A block configuration for the second level of the game.
- ```level3.txt``` - A block configuration for the third level of the game.

Key/Mouse inputs:
- ```Z``` - Start the game from the Title Screen. Start a new level.
- ```LEFT ARROW``` - Move the paddle to the left.
- ```RIGHT ARROW``` - Move the paddle to the right.
- ```UP ARROW``` - Shoot the ball while it is "stuck" to the paddle.
- ```SPACEBAR``` - Pause and resume the game.

Cheat keys:
- ```1``` - Jump to level 1.
- ```2``` - Jump to level 2.
- ```3``` - Jump to level 3.
- ```R``` - Reset the ball to its initial position on the paddle.
- ```L``` - Add extra lives to the player.
- ```F``` - Make the ball move 2x faster.
- ```S``` - Make the ball move 2x slower.
- ```Q``` - Clear all blocks on the screen.
- ```D``` - Destroy the top-most, left-most block on the screen.
- ```P``` - Create a power-up that immediately drops to the paddle.

Known Bugs:
- ```Double Block Hit```: When the ball hits perfectly on the edge of two blocks with equal depth,
both blocks are destroyed and the ball continues to move up and not bounce off of the blocks.
- ```Side Paddle Hit```: If the ball intercepts the side of the paddle, there is a phenomenon
where the ball will appear to be "trapped inside" the paddle until it reaches the end of the paddle
and bounces back out.

Extra credit:
N/A

---
### Notes/Assumptions
Possible Power-Ups:
- ```Paddle Grower```: This power-up increases the width of the paddle.
- ```Ball Slower```: This power-up decreases the speed of the ball (never greater than 2x the initial velocity).
- ```Score Adder```: This power-up adds 50 points to the player's current score.

Types of Blocks:
- ```Easy Block```: This block takes one hit to eliminate.
- ```Medium Block```: This block takes two hits to eliminate.
- ```Hard Block```: This block takes three hits to eliminate.
- ```Power-Up Block```: This block takes one hit to eliminate and drops a random power-up that the player
must catch with his/her paddle in order for the power-up to take effect.

Levels:
- ```Level 1```: This level is easy and consists of only Easy Blocks with a large paddle.
- ```Level 2```: This level is harder because it mixes in medium and hard blocks, and the paddle is smaller.
- ```Level 3```: This level is much harder because there are many medium and hard blocks, and the paddle is significantly
smaller than in Level 1.

---
### Impressions
At first, we thought that the Test and Basic implementations were easy to complete. We ran into
very few issues when it came to creating basic functionality of the game. However, we
learned that our design was flawed, and it was difficult to add some of the features for the Complete
implementation. We then did a lot of refactoring to create new classes and methods that made it easier
to add new features. Overall, this has been a good project for learning how to use JavaFX and TestFX to 
become more proficient in Java and basic design principles that will help us become
better coders.
