# Easy Pieces 

## Introduction: Describe your domain and motivate the need for a DSL (i.e., how could domain-experts use and benefit from a DSL?). What is the essence of your language, and why is it a good language for this domain?

This project is motivated by some of my personal experiences. I was frustrated
by the lack of an easy-to-use and complete grapher that included piece-wise
functions with multiple parts and proper identification of <= or < conditions.
My goal is to make a DSL that can be written in math notation and produces
graphs that can be used by students in LaTeX homework assignments without
low quality drawings and scans.

In this case, my domain experts are early mathematics students with little
or no experience with programming. As such, one of the major goals is to make
the syntax as close as possible to the problem statements given in assignments
and mathematics textbooks. My language is superior to others in the domain
because it is accessible to students without programming experience, and it
is good for the domain because it allows the vast majority of mathematical
operations to be used. 

## Language design details: Give a high-level overview of your language's design. Be sure to answer the following questions:

#### How does a user write programs in your language (e.g., do they type in commands, use a visual/graphical tool, speak, etc.)?

Programs in my language will be written in a text editor and executed by running
my Easy Pieces program and entering the name of the file. Future goals 
(beyond the scope of this project) would be creating a nice graphical
interface to allow the user to enter their program. I have provided a guide to
using the language in the README that has been successfully used by people
without experience with programming. 

#### How does the syntax of your language help users write programs more easily than the syntax of a general-purpose language?

The syntax of my language was designed to intentionally mirror the statement
of problems in textbooks and assignments in a way that other languages don't.
Following are some examples of a program written in various existing languages
followed by the program written using the Easy Pieces DSL. 

**Matlab**

```
x = 0:0.01:20;
if (-10 < x) & (x < 10)
    y = 2 * x
elseif (10 <= x) & (x < 15)
    y = 20
elseif (15 <= x) & (x < 20)
    y = sqrt(x)
end
plot(x, y)
```

**Grapher for Mac**

```
y = -10 < x ? (x < 10 ? 2 * x : (x < 15 ? 20 : (x < 20 ? sqrt(x))))
```

**Easy Pieces**   
 
```
f(x) = { 2*x, -10 < x < 10
f(x) = { 20, 10 <= x < 15
f(x) = { sqrt(x), 15 <= x < 20
```

Although the Matlab program may be fairly intuitive to a more experienced
programmer, it would have a steeper learning curve for students just starting
out. The Grapher for Mac program, on the other hand, is fairly unintuitive
even for someone with programming experience. In both of these cases, Easy 
Pieces provides an advantage for the inexperienced programmer, allowing a quick
translation from problem to program without having to learn and use more
complicated control flow. 

#### What is the basic computation that your language performs (i.e., what is the computational model)?

The program takes an input of text and parses it into a list of pairs, where 
each pair has the name of the function and a list of the bounds and equations
for each. Each of these parts are then checked for correctness and a list of 
data points is created. This list of data points is then graphed and output in 
the form the user has specified (PNG, PDF, ASCII, or GUI).

#### What are the basic data structures in your DSL, if any? How does a the user create and manipulate data?

My DSL can basically be seen as a list of functions. The user creates data by 
creating a text file and passing it to the processing program. The user simply
enters lines of text for the name of the function, the variable, the bounds,
and the equation. 

#### What are the basic control structures in your DSL, if any? How does the user specify or manipulate control flow?

My DSL parses and processes input linearly. There are no changes in control
flow.

#### What kind(s) of input does a program in your DSL require? What kind(s) of output does a program produce?

A program requires a text input of a list of functions. The program is also able
to take in the name of the output file, the title of the graph, the label for
both axes, and the format in which the output
should be given. The output can be a PNG file, PDF, ASCII art, or a pop-up
GUI allowing the user to change the aspect ratio before saving. Following is an
example input text file and the output graph.

```
g(x) = { x + 2, -10 < x < 2    
g(x) = { x * x, 2 <= x < 4  
g(x) = { x + 4, 4 <= x < 10 
```

![Program 2 Graph](https://github.com/SarahKnits/project/blob/master/piecewiseGrapher/docs/img/SecondProgram.png)

#### Error handling: How can programs go wrong, and how does your language communicate those errors to the user?

Programs can be wrong by having an invalid format, having math errors (such as
division by zero), or having undefined variables in a given function. The
language currently returns useful errors reporting which line a parsing error
occurred in and what the issue was (missing open curly brace, comma, invalid
expression, etc). It also reports some logical errors, such as taking the
square root of a negative number, dividing by 0, or using the wrong variable.

Although I had initially considered checking that the limits of a single
function never overlapped, my critique partner mentioned that this wouldn't be
particularly helpful in most cases and may limit some use cases. As such, I 
decided against checking that there was one and only one y value for each x.

#### What tool support (e.g., error-checking, development environments) does your project provide?

Since the input to my program will be a simple text file, there will not be tool
support initially. If I create a graphical interface for input, I would add some
error-checking in that interface. 

When the Easy Pieces program is run with the file as input, the file reading
has error-checking, as does the parsing, evaluation, and graphing. I tested
my error-checking on my inexperienced users, and found that they were able to
use those errors to correct the broken code.

#### Are there any other DSLs for this domain? If so, what are they, and how does your language compare to these other languages?

There are some existing DSLs for this domain. One of these is ScalaPlot, which
is an internal DSL for Scala. I am using it to produce my graphs. ScalaPlot 
requires the user to enter the programs in a typical Scala format, while my 
language intends to make piecewise graphing easy and accessible to non-CS
students by allowing plain-text input as close as possible to the format these
problems are given in math textbooks. 

Other piecewise graphing programs include Grapher for Mac and Matlab, both of 
which are discussed above. My DSL is more intuitive to a person with limited
programming experience. 

#### Example program(s): Provide one or more examples that give the casual reader a good sense of your language. Include inputs and outputs. Think of this section as “Tutorial By Example”. You might combine this section with the previous one, i.e., use examples to help describe your language.


**Example 1**
```
Title: "Distance traveled over time"
Filename: "Problem1"
xLabel: "Time"
yLabel: "Distance"
f(x) = { 2 * x, -1 < x < 3
f(x) = { 6, 3 <= x < 4
f(x) = { sqrt(x), 4 <= x < 10
```
This results in the following graph:

![Example 1 Graph](https://github.com/SarahKnits/EasyPieces/blob/master/supportFiles/Problem1.png)

**Example 2**
```
Title: "Problem 2"
Filename: "Problem2"
xLabel: "x"
yLabel: "y"
f(x) = { 2, 0 < x < 2
f(x) = { x, 2 <= x < 5
f(x) = { sin(x), 5 <= x < 3 * π
```

This results in the following graph:

![Example 2 Graph](https://github.com/SarahKnits/EasyPieces/blob/master/supportFiles/Problem2.png)

**Example 3**
```
g(x) = { e^x, 0 < x < 1
g(x) = { cos(x), 1 <= x < 4
g(x) = { abs(x), -1 < x <= 0
```
This results in the following graph:

![Example 3 Graph](https://github.com/SarahKnits/EasyPieces/blob/master/supportFiles/GraphDemo.png)

For a more complete tutorial in learning the language, please see the README.


## Language implementation: Describe your implementation. In particular, answer the following questions:

#### What host language did you use (i.e., in what language did you implement your DSL)? Why did you choose this host language (i.e., why is it well-suited for your language design)?

I chose to host this program in Scala. Although one major influence was my 
previous experience with parsing in Scala, I also found several good graphing
tools using Scala libraries. I think that Scala is the best choice (or at least
tied with other best choices) for each step from input to graphical output. 

#### Is yours an external or an internal DSL (or some combination thereof)? Why is that the right design?

My language is an external DSL. Since I am trying to make the input as 
human-readable as possible, I felt that an external DSL would make this easier
as well as allowing more flexibility for the users. 

#### Provide an overview of the architecture of your language: front, middle, and back-end, along with any technologies used to implement these components.

My system follows a very similar structure to the structure we used in the 
external PicoBot project. The program is read in and parsed into an AST tree.
It is then processed and made into a list of functions and equations. Next,
this list is processed to create a list of data points. Finally, this list of
data points is graphed and the output is created. 

#### “Parsing”: How does your DSL take a user program and turn it into something that can be executed? How do the data and control structures of your DSL connect to the underlying semantic model?

In writing an external DSL, I uses the JavaTokenParser with PackratParsers. 
The file is read in based on the path provided by the user at runtime. The
language has a fairly strict format that is required, with meaningful error
messages returned when the format is incorrect. The individual function lines
are parsed and used in the list of functions to graph. The first few characters 
in each line (the name of the function and the variable used) are used to
determine the color of the graph and to check the correctness of the equation.
The equation is used to calculate each of the points, and the range is used
to determine which range should be graphed. 

#### Intermediate representation: What data structure(s) in the host language do you use to represent a program in your DSL?

A program in my DSL initially consists of a list of functions. After these
functions have been parsed, they are converted to a list of pairs, where the
first value is the name of the function and the second value is the list of 
expressions and limits. This is then processed and graphed. The intermediate
representation is the list of pairs and the list of points to be graphed.  

#### Execution: How did you implement the computational model? Describe the structure of your code and any special programming techniques you used to implement your language. In particular, how do the semantics of your host language differ from the semantics of your DSL?

To implement the computational model, I used the standard structure of parsing
the input to an intermediate format followed by a semantic interpretation of the
data resulting in the output of a graph. Since I am implementing an entirely
external language, the input is only related to the output by meaning. 

I based the portion of the interpreter that evaluated the function at each 
point on the calculator DSL we wrote earlier in the semester. I included
polynomials, periodic functions, and other helpful operations in addition
to having error-checking for the correct variable, valid operations, and 
proper syntax. 

## Evaluation: Provide some analysis of the work you did. In particular:

#### How “DSL-y” is your language? How close or far away is it from a general-purpose language?

My language is extremely "DSL-y." You are only able to produce graphs of 
piecewise or continuous functions. As such, it is very far away from a general-purpose language. 

#### What works well in your language? What are you particularly pleased with?

I'm pleased with the general user experience. I believe I accomplished
my goal of creating an intuitive syntax and graphing tool that can be
used by non-programmers. 

General functionality works properly, including polynomials (powers,
addition, subtraction, multiplication, division), parentheses, sine,
cosine, absolute value, square roots, pi, and e. Graphing properly
displays multiple graphs with proper coloring and limit representation
(filled in vs open circle for boundaries). 

#### What could be improved? For example, how could the user's experience be better? How might your implementation be simpler or more cohesive? Are there more features you'd like to have? Does your current implementation differ from your larger vision for the language?

I require the user to provide both minimum and maximum limits for each piece of 
the graph. I have received mixed feedback about whether this or having global 
limits with the possibility of one sided limits in the graph is better. I will
explore the possibility of one sided limits and attempt to determine if this is
a better option. 

As mentioned above, I would like to have a graphical interface at some point
to make the graph production even more accessible to those without programming
experience. I also have occasional issues with aliasing, as the step size is a
value calculated based on the width of the graph. 

#### Re-visit your evaluation plan from the beginning of the project. Which tools have you used to evaluate the quality of your design? What have you learned from these evaluations? Have you made any significant changes as a result of these tools, the critiques, or user tests?

I tested my style goals using user tests with inexperienced programmers 
(mostly my family) and more experienced programmers (classmates from DSLs). 
I found that they were able to follow the README tutorial well and produce
correct code. I also found that they were able to use the returned error 
message to correct code with errors in it. I successfully graphed functions
both from tutorials online and from past assignments. 

User testing helped me choose between some options for formatting the input.
I had been debating removing the open curly brace from the functions after 
getting feedback from experienced programmers that they weren't as fond of it
(stressed out by having an open curly brace without a close curly brace). In
my user testing, I found that the tutorial and examples helped my users to 
see why there was the open curly brace, and my test users didn't have
difficulty with it. User testing also resulted in adding labels for the axes,
the title, and the output options. 

#### Where did you run into trouble and why? For example, did you come up with some syntax that you found difficult to implement, given your host language choice? Did you want to support multiple features, but you had trouble getting them to play well together?

One of the biggest struggles with this project was figuring out what to use as
the backend for my project. I tested quite a few different graphing tools, and
finally selected ScalaPlot for the graphing tool. ScalaPlot can do most of the 
things I wanted from the grapher easily, but I have run into a couple of issues.

The difficulty in choosing step size has resulted in an attempt to balance
between having smooth graphs for functions and having all parts show up. At
present, I calculate the step size based on the range of the inputs. This 
results in everything showing up smoothly for the graphs I tested. It may run
into aliasing issues for periodic functions with an extremely high frequency,
but ScalaPlot has difficulty displaying these anyway. 

I have had some difficulty providing as many different colors as I wanted, as
ScalaPlot has limited color options for graphs. 

