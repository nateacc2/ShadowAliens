# SWEN20003 Semester 1, 2026
# Project 2b
# Shadow Aliens

## Running Instructions
##### IntelliJ run configuration:
* Java SDK: open-jdk 26
* Class path: shadow-aliens
* Main class: game.ShadowAliens
* Working directory: [Your Path] \ SWEN20003-Project1-Skeleton.2026.APR.14
* VM Options: -DgameData=gameData.properties
## Assumptions
No assumptions made.

## AI Statement
I acknowledge the use of Claude [claude.ai] to help me work through project 2b software requirements

* I utilised Claude to help me summarise the requirements for project 2b into concise dot points
* I utilised Claude to help fix compile and runtime errors in my program such as ArrayIndexOutBounds exception that would cause my application to crash
* Claude was used to help me further understand OOP concepts such as polymorphism, inheritance, etc in the context of my game design
* Claude was used to explain how to use bounding box for collision detection between game objects\
* Claude was used to help me understand how to implement different screen types such as battle screen, end screen and gave me a guide on how to separate the logic to each class.

A full record of prompts and outputs is available upon request.
_____________
## Code References
Following Google's Java Style Guide 

No code references used.
## Design Report

### Extension:
A single class of Enemy was present during the implementation of the project 1 code design. In order to fulfill project 2 software requirements which required multiple enemy types with different behaviours. I used the OOP concept of inheritance, creating a parent abstract class "Enemy" with three child classes to represent the different types. 

I modified the original Enemy class to be abstract and created new child classes for each enemy type, each enemy needed to implement their own move and update method as well as additional methods such as shoot() method for the ShootingEnemy class. Due to these changes, the ShadowAliens and Wave class were also updated to work with these new objects.

Project 1's overall design concepts was uncohesive with no abstraction or inheritance present resulting in a "God class" -- a class that does and knows too much. This poor decision inhibited project 2 changes as the code was more difficult to work with as my original code contained overly long methods that did complex operations which made it very prone to errors. Due to all the enemy logic being in one class it took more time for me to move the code into separate classes with their own logic, making a change to one feature had the possibility to break completely unrelated features which required more time for me to figure out and fix. 
****

### Outcome
The final state of my code now has a higher cohesion with abstract classes, multiple inheritance relationships and also interfaces. 
#### Polymorphism
Principles that are now present in my code design are polymorphism, as the wave class is being passed in an enemy array with multiple different enemy types. It does not need to know which enemy type it is currently dealing with, for e.g. the wave classes only has to call the enemy's update method which would automatically activate the enemy's specific behaviour depending on its particular type. 

#### Single Responsibility principle
Information related to a game object lives in separate classes, for e.g. instead of letting ShadowAliens handle the shooting logic of a ShootingEnemy, this behaviour is implemented inside the ShootingEnemy class as it does not make sense for ShadowAliens to handle this as its unrelated.

#### Open/Closed Principle
The addition of an enemy type is made easier in the final state of this code, for e.g. adding a fourth enemy type would only require creating a new subclass of Enemy and implementing move() and update() — no existing classes would need modification.

#### Abstraction
Through abstraction, this enforces that an abstract class cannot be instantiated which makes sense for this game design as it would not make sense to create a plain Enemy object.

## Design Report References
No design report references used.