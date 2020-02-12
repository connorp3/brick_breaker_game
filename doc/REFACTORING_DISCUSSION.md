Team 13:
-
- John Taylor - jmt86
- Connor Penny - cgp19
---
Primary Issues:
- 
#### Longest Method
- ```handleInput``` method is too long. It needs to be refactored
so that it does not contain a long chain of if-statements. We will look
at the frogger lab to determine the best way to split the input handling into different classes.
#### Duplication
- There was duplicate code when it came to setting the font of the status display
text objects (life count, score, level count). This has been refactored into a method to 
take in the desired text to change along with the desired font type and size. Since this 
line was duplicated 3 times, it made sense to make a method like this.
#### Magic Numbers
- The design checking program detected 29 magic numbers in our project. In many of these cases,
we will have to analyze how many times the number is used and what the exact context of the number is.
For example, in many cases, the number is used for positioning things on the screen (paddle, ball, text, etc...),
so these values may not need to be changed in every case. However, there are a lot of areas (numLives) that can
be refactored to be a variable so the game is more customizable.
#### Unused private fields
- The unused private fields are not a big factor right now. We are still deciding the best way to keep track
of certain properties between objects (stuckToPaddle) and these unused variables were just left over
even after the implementation was changed. These will be easy to remove once we start to finalize our code.
#### Useless Imports
- A lot of these imports are not necessary to the functionality of the code and are left over from previous 
implementations of the way to add blocks to the game elements. These imports can be removed.
#### Boolean expressions should not be wrapped into an if-then-else statement
- This is a big no-no that Professor Duvall mentioned in class. It has been fixed
with swiftness.
#### General Points
- This program has shown us a lot of areas where we can improve the design of our code to be more
flexible. Moving forward, we aim to first fix a lot of those refactoring issues to make it easier to implement new features.
This will help us in the long run.