# Language design and implementation overview

## Language design
 
### How does a user write programs in your language (e.g., do they type in commands, use a visual/graphical tool, speak, etc.)?

Programs in my language will be written in a text editor and executed by running
my PiecewiseGrapher program and entering the name of the file. 

### What is the basic computation that your language performs (i.e., what is the computational model)?



### What are the basic data structures in your DSL, if any? How does a the user create and manipulate data?

My DSL can basically be seen as a list of functions. The user creates data by 
creating a text file and passing it to the processing program. 

### What are the basic control structures in your DSL, if any? How does the user specify or manipulate control flow?



### What kind(s) of input does a program in your DSL require? What kind(s) of output does a program produce?

A program requires a text input of a list of functions. The program is also able
to take in the desired window size, point interval, and which format the output
should be given in. The output can be a PNG file, PDF, ASCII art, or a pop-up
GUI allowing the user to change the aspect ratio before saving. 

### Error handling: How can programs go wrong, and how does your language communicate those errors to the user?

Programs can be wrong by having an invalid format, having math errors (such as
division by zero), or having undefined variables in a given function. 


### What tool support (e.g., error-checking, development environments) does your project provide?
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
tools using Scala libraries. 

### Any significant syntax design decisions you've made and the reasons for those decisions.
### An overview of the architecture of your system.

