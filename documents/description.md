# Project description and plan

## Motivation
This project is motivated by some of my personal experiences. I was frustrated
by the lack of an easy-to-use and complete grapher that included piece-wise
functions with multiple parts and proper identification of <= or < conditions.
My goal is to make a DSL that can be written in math notation and produces
graphs.

## Language domain
This language will be in the domain of graphing for math, focusing on the
needs of freshmen taking Math 30. 

## Language design
The language will allow users to write in math notation. At present, I intend
to use the Mac Grapher tool to produce the graphs. As such, the intermediate
representation will be somewhat based on what Grapher takes in.

During class discussions, it was mentioned that Grapher enables the user to 
create piece-wise graphs. Upon greater investigation, I found that, although
graphing was possible, it was far from ideal. My goal will be to make graphing
piece-wise functions complete (allowing multiple parts with open or closed dots
for endpoints) and easy (better matching the notation used in math).

## Example computations

This is an example of a program with a window from (-10,-10) to (10,10):
-10 <= x <= 10    
-10 <= y <= 10    
y = {2x, x=0    
  = {3x, x>0    
  = {x^2, otherwise    

The user will define the window in which to display their graph, followed by
the mathematical notation for the graph. 

