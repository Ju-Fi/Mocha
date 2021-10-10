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
An integer is any positive or negative integer. Negative integers are denoted like normal. Example:
```
1 9000 450 -70
```
are all valid integers.
### Floats
Floats are any floating point numbers such as 5.2, 7.8, -200.0. Floats and ints can be used interchangably in operations, however they will always return a float provided even a single float is used in the operation.
```
20 5.0 /
```
will return 4.0.
### Bools
Bools are boolean values.
```
True 
False
```
are valid booleans that can be pushed onto the stack.

## Operations
All operations work in a similar sense. `+`, `-`, `*`, `/`, `%` will all pop the top two items off of the stack and push the resulting number atop the stack.
Example:
```
5 2 -
```
will push 2 onto the stack, then 5, and then subtract 2 from 5 and push 3 onto the stack. 

All of the operations work the way you expect them to, with + adding numbers, - subtracting numbers, * multiplying, / dividing, and % returning remainders.

## Conditionals
Conditionals will pop two items off the stack and compare them. Valid conditional statements are '>', '<', '>=', '<=', '==', and '!='. They will push the corresponding boolean value to the stack. Example:
```
4 5 <
```
will push `true` onto the stack, as 4 is less than 5.

## Keywords
`println` will print the value that is atop the stack.

`printlnd` will print the value that is atop the stack and then drop it from the stack.

`drop` will drop the item that is atop the stack.

`dup` will duplicate the item that is atop the stack.

`swap` will swap the top two items on the stack.

`rot` will rotate the first three items on the stack. This is may be temporary behavior as I'm experimenting with how this feature is implemented.

## Comments
Comments are denoted with `#`. Anything following the comment symbol will be skipped by the interpreter. Example:
```
# This is a comment.
# This is another comment.
This is not a comment.
```

