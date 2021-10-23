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
### Strings
String literals are denoted with " ". At the moment no operations on strings is supported, i.e. adding to a string. Example of a valid string:
```
"This is a valid string"
"Don't do this
Or this"
```

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

## Control Flow
### If/Else
`if` statements read the boolean value on top of a stack and execute the procedure that follows. Example:
```
<condition> if {
	1 2 + printlnd
}
```
`else` statements go after if statements and will execute the code within the procedure if the condition before the if statement is false. Example:
```
1 0 == 
# 1 is not equal to 0 and hence pushes `false` onto the stack.
if {
	1 2 + printlnd
} 
# Therefore, `else` will be executed.
else {
	3 4 + printlnd
	
}
```

### And/Or 
`and` compares the top two boolean values on the stack and if both are `true` it will push `true` onto the stack, otherwise it will push `false`.
`or` compares the top two boolean values on the stack and if either are `true` it will push `true` onto the stack, if both are false it will push `false`.

### While/Do 
While statements are constructed with the `while` keyword, then the condition you want to be evaluated, and then `do`, followed by the procedure. Example:
```
# Defines i as equal to 0.
0 i =
while i 10 < do {
	i 1 +
	i =
}
```
will loop until `i` becomes 10. For a better example see `fizzbuzz.mocha` in examples.

## Keywords
`println` will print the value that is atop the stack.

`printlnd` will print the value that is atop the stack and then drop it from the stack.

`drop` will drop the item that is atop the stack.

`dup` will duplicate the item that is atop the stack.

`swap` will swap the top two items on the stack.

`rot` will rotate the first three items on the stack. This is may be temporary behavior as I'm experimenting with how this feature is implemented.

## Variables
Variables can store data and its type (NOTE: getting the type of a variable is not yet implemented). To initialize a variable, you need to push data onto the stack, put the variable name and then the assignment operator. Example:
```
2 x =
```
assigns 2 to the variable `x`. You cannot simply declare a variable; you must have data associated with it.
To use the value stored in the variable, just use the variable name. Variable names can utilize letters and underscores at the moment.
Reassignment of variables is done exactly like assignment.

## Return Stack
The return stack is a separate stack that allows you to temporarily store values. You cannot perform operations or manipulate it. It works similarly to the operational stack in that pushing items moves the previously added items downwards. It's primary purpose is to "return" values that you need to temporarily store.
`store` pops the item atop the operational stack and pushes it onto the return stack.
`load` pops the item atop the return stack and pushes it onto the main stack.
`fetch` copies the item atop the return stack and pushes it onto the main stack.

## Comments
Comments are denoted with `#`. Anything following the comment symbol will be skipped by the interpreter. Example:
```
# This is a comment.
# This is another comment.
This is not a comment.
```

