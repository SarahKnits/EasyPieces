# EasyPieces

Information about the CS111 EasyPieces project by Sarah Gilkinson can be found
here.

This will be kept up-to-date with implementation plan, work completed, and 
any additional resources.

The notebook recording progress on this project can be found 
[here](https://github.com/SarahKnits/EasyPieces-notebook).

See the 
[project requirements](http://www.cs.hmc.edu/~benw/teaching/cs111_fa14/project.html) 
for instructions on setting up your project.

### Introduction

This project is designed to make piecewise graphing easy and intuitive. By
using a syntax as close as possible to that shown in mathematics textbooks,
PiecewiseGrapher quickly produces graphs with a variety of output options.

### Program Syntax

The syntax for this language is designed to closely reflect the syntax shown
in mathematics textbooks. Each line consists of three main parts - the function
name, the equation, and the boundary conditions. 
For example, the function shown here

![Sample Graph](https://github.com/SarahKnits/EasyPieces/blob/November30/SampleGraph.png)

is represented as

```
f(x) = { x^2, 0 < x < 2
f(x) = { x + 3, 2 <= x < 4
f(x) = { sin(x), 4 <= x < 10
```

Functions can be given any name, and any variable can be used. Currently 
supported operations are:
* Addition
* Subtraction
* Multiplication
* Division
* Powers
* Absolute value
* Sine
* Cosine
* Square roots
* e
* pi

Limits must be presented in ascending order, with either < or <=. See
sampleProgram.txt and sampleProgram2.txt for examples. 

### Tutorial

To use this program, make sure you have downloaded GNUplot and sbt. You will 
also need to download the source code for PiecewiseGrapher. 

Once you have downloaded the project, enter the PiecewiseGrapher directory. Type 

`` sbt compile ``

If there are any errors, make sure you have downloaded all dependencies and
that you are connected to the internet. After this has been completed, type

`` sbt run ``

You will be presented with two options: 

``` 
[1] GraphDemo

[2] Grapher
```

To run the full grapher, select Grapher and hit enter. You will then be prompted
to  type the name of the file containing the graph you want to make. Enter the 
name of your own file or use one of the included examples. These examples are

```
samplePrograms/sampleProgram.txt

samplePrograms/sampleProgram2.txt
```

The graph output will be placed in the docs/img directory and will be named
Graph.png. 

If you encounter any strange behavior, please submit an issue. 

