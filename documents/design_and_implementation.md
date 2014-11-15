# Language design and implementation overview

## Language design
 
### How does a user write programs in your language (e.g., do they type in commands, use a visual/graphical tool, speak, etc.)?

Programs in my language will be written in a text editor and executed by running
my PiecewiseGrapher program and entering the name of the file. Future goals 
(beyond the scope of this project) would be creating a nice graphical
interface to allow the user to enter their program. 

### What is the basic computation that your language performs (i.e., what is the computational model)?

The program takes an input of text and parses it into a list of pairs, where 
each pair has the name of the function and a list of the bounds and equations
for each. Each of these parts are then checked for logic and a list of data
points is created. This list of data points is then graphed and output in the 
form the user has specified (PNG, PDF, ASCII, or GUI).

### What are the basic data structures in your DSL, if any? How does a the user create and manipulate data?

My DSL can basically be seen as a list of functions. The user creates data by 
creating a text file and passing it to the processing program. The user simply
enters lines of text for the name of the function, the variable, the bounds,
and the equation. 

### What are the basic control structures in your DSL, if any? How does the user specify or manipulate control flow?

There are no control structures in my DSL. 

### What kind(s) of input does a program in your DSL require? What kind(s) of output does a program produce?

A program requires a text input of a list of functions. The program is also able
to take in the desired window size, point interval, and which format the output
should be given in. The output can be a PNG file, PDF, ASCII art, or a pop-up
GUI allowing the user to change the aspect ratio before saving. 

### Error handling: How can programs go wrong, and how does your language communicate those errors to the user?

Programs can be wrong by having an invalid format, having math errors (such as
division by zero), or having undefined variables in a given function. The 
language will be able to return very useful errors for the math errors and
undefined variables, but may have some issues returning a meaningful error
for parsing errors. As of now, I think the best I can do is return an error
saying that parsing failed and try to tell about where it happened. 


### What tool support (e.g., error-checking, development environments) does your project provide?

Since the input to my program will be a simple text file, there will not be tool
support initially. If I create a graphical interface for input, I would add some
error-checking in that interface. 

### Are there any other DSLs for this domain? If so, what are they, and how does your language compare to these other languages?

There are some existing DSLs for this domain. One of these is ScalaPlot, which
is an internal DSL for Scala. I am using it to produce my graphs. ScalaPlot 
requires the user to enter the programs in a typical Scala format, while my 
language intends to make piecewise graphing easy and accessible to non-CS
students by allowing plain-text input as close as possible to the format these
problems are given in math textbooks. 


## Language implementation
 
### Your choice of an internal vs. external implementation and how and why you made that choice.

My language is an external DSL. Since I am trying to make the input as 
human-readable as possible, I felt that an external DSL would make this easier
as well as allowing more flexibility for the users. 

### Your choice of a host language and how and why you made that choice.

I chose to host this program in Scala. Although one major influence was my 
previous experience with parsing in Scala, I also found several good graphing
tools using Scala libraries. I think that Scala is the best choice (or at least
tied with other best choices) for each step from input to graphical output. 

### Any significant syntax design decisions you've made and the reasons for those decisions.

My goal was to make my language as close as possible to the programs given in
piecewise graphing homework assignments. My syntax decisions were made to
attempt to accomplish this goal. 

```
f(x) = {2*x, x > 0
f(x) = {x^2, otherwise
```

### An overview of the architecture of your system.

