# Preliminary evaluation
### What works well? What are you particularly pleased with?

I'm pleased with the general user experience. I believe I accomplished
my goal of creating an intuitive syntax and graphing tool that can be
used by non-programmers. 

General functionality works properly, including polynomials (powers,
addition, subtraction, multiplication, division), parentheses, sine,
cosine, absolute value, square roots, pi, and e. Graphing properly
displays multiple graphs with proper coloring and limit representation
(filled in vs open circle for boundaries). 

### What could be improved? For example, how could the user's experience be better? How might your implementation be simpler or more cohesive?

Currently, the error checking and error messages aren't as complete as they
should be. Future steps will be taken to improve the errors returned in 
failed parsing. 

I require the user to provide both minimum and maximum limits for each piece of 
the graph. I have received mixed feedback about whether this or having global 
limits with the possibility of one sided limits in the graph is better. I will
explore the possibility of one sided limits and attempt to determine if this is
a better option. 

I am still having some issues with horizontal graphs, where occassionally the 
line doesn't show up when the y-axis is too spread out. This can be dealt with
by adjusting the step size, which I will be working on this coming week. 

### Re-visit your evaluation plan from the beginning of the project. Which tools have you used to evaluate the quality of your design? What have you learned from these evaluations? Have you made any significant changes as a result of these tools, the critiques, or user tests?

I have graphed a sample set of programs given in Math 30G. I have also given 
the program to non-Computer Scientists and observed what parts of the program
were easy to figure out and which were more difficult. I found that the open
curly brace wasn't as intuitive as I had hoped initially, but after explaining
my reasoning behind it, fewer people struggled with it.

### Where did you run into trouble and why? For example, did you come up with some syntax that you found difficult to implement, given your host language choice? Did you want to support multiple features, but you had trouble getting them to play well together?

One of the biggest struggles with this project was figuring out what to use as
the backend for my project. I tested quite a few different graphing tools, and
finally selected ScalaPlot for the graphing tool. ScalaPlot can do most of the 
things I wanted from the grapher easily, but I have run into a couple of issues.
The difficulty in choosing step size has resulted in an attempt to balance
between having smooth graphs for functions and having all parts show up. 

I have had some difficulty providing as many different colors as I wanted, as
ScalaPlot has limited color options for graphs. 

### What's left to accomplish before the end of the project?

Before the end of the project, I have the following to accomplish: 
* Adding useful error messages targeted at specific users
* Correct issue with lines not showing up for horizontal lines
* Add support for personalizing labels for axes, titles, and file names

At this point, I feel that the graphing ability matches my goals. My future
work will focus on improving the user experience, making the graph output
clearner, and fixing any bugs I find. 
