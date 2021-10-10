# Mocha
A stack-based language written in Java. I am utilizing concepts that I like from other stack-oriented languages such as Forth, Post-Script, and Porth.

Why the name Mocha? Because it is written in Java, and Mocha is a type of coffee.

## Principles
Because this language is actively in development, none of the design principles are set in stone, however, these are some of the ideas that I have in mind:
* Both an interpreter and a compiler
* Compiled to JVM bytecode
* Stack based
* Minimal syntax analysis
* Dynamically and strongly typed
* Relatively human readable, provided familiarity with the concept of a stack based language
* Turing-complete
* Cross platform

## Quick Start
To build the interpreter, run the `gradlew jar` or `gradlew.bat jar` if on Windows.
Run it to see the basic command line usage.

# Reference
These are the features implemented currently. Of course, keep in mind the syntax will most likely change as the language is developed and certain parts are reworked.

## Data Types
### Integer
An integer is any positive integer. While the language does support negative integers, it does not currently identify them in code, so you will need to create them manually, like so:
```
0 4 -
```
This will push -4 onto the stack.

### Floats
Floats are any floating point numbers such as 5.2, 7.8, 200.0. Floats and ints can be used interchangably in operations, however they will always return a float provided even a single float is used in the operation.
```
20 5.0 /
```
will return 4.0.

## Operations
All operations work in a similar sense. `+`, `-`, `*`, `/`, `%` will all pop the top two items off of the stack and push the resulting number atop the stack.
Example:
```
5 2 -
```
will push 2 onto the stack, then 5, and then subtract 2 from 5 and push 3 onto the stack. 

All of the operations work the way you expect them to, with + adding numbers, - subtracting numbers, * multiplying, / dividing, and % returning remainders.

## Misc
At the moment there are no keywords, however there are plans to add other operations, along with conditional statements.
There are two additional things you can call: `.` and `;`.
`.` is a stand in for print line, and will print whatever value is atop the stack.
`;` clears the entire stack.
Both of these are temporary and will be replaced.
