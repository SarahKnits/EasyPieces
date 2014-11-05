# Project description and plan

## Motivation
This project is motivated by some of my personal experiences. I was frustrated
by the lack of an easy-to-use and complete grapher that included piece-wise
functions with multiple parts and proper identification of <= or < conditions.
My goal is to make a DSL that can be written in math notation and produces
graphs.

## Language domain
This language will be in the domain of graphing for math, focusing on the
needs of freshmen taking Math 30. This will hopefully benefit all math students
who are using LaTeX for their math homework and want a cleaner graph than the
result of hand-drawing and scanning. There are existing programs such as
Grapher for Mac that are in this domain, but existing programs fall short 
either in the intuitiveness of the input or in the ease of producing the 
desired result.  

## Language design
The language will allow users to write in math notation. At present, I intend
to use the Mac Grapher tool to produce the graphs. As such, the intermediate
representation will be somewhat based on what Grapher takes in.

During class discussions, it was mentioned that Grapher enables the user to 
create piece-wise graphs. Upon greater investigation, I found that, although
graphing was possible, it was far from ideal. My goal will be to make graphing
piece-wise functions complete (allowing multiple parts with open or closed dots
for endpoints) and easy (better matching the notation used in math).

When a program runs, Grapher will produce a graph including piece-wise graphs
following the specifications fo the user. There are certainly possible errors
in this language. The input will likely have a tightly defined syntax and I
will have to determine how best to produce errors for this. The more tightly
defined the syntax is, the easier I imagine error messages will be.

## Example computations

The language will allow specification of the window and the desired pieces of
the final graph. It will then produce a graph using Grapher that matches these
requirement, including having the open or closed dots for endpoints. 

