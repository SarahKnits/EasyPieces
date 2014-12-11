Critique for week ending in November 18:

I didn't have time to try downloading the package before class, but I'll try in class on wednesday and
get back to you then. With regards to the rest of your questions:

Does the sample graph output (see below) look like you would want the piecewise graph to look?

Yes! I think the output looks great so far, is there a way to explicitly specify the bounds the graph
shows? I tried to find GraphDemo.scala but I don't think theres a file with that name in your github,
are you sure it's online?

I think it would be really useful to be able to set names for the x and y axis. Since we're selling this
as a tool for core math homework, and setting axis labels would be important for that application, that
seems like an important thing to add.

I would also focus on adding order of operations, parenthases, and building block functions (like sin, cos, etc.).

I would't prioritize allowing variables other than x too highly, as long as you create some mechanism for labeling
the x and y axis as something other than x and f(x). It's ok to make the user write x every time (although that's
not ideal) as long as you create some way for the user to avoid having the x axis always be labeled "x".

I think the "rigurous" way of determinign the step size would be to have some epsilon, and then have the step size
change dynamically as you plot the graph with the constraint that the value of the function never change by more
than epsilon over any given step. I think an easier way to do it would be to divide the x-range you're plotting
over by 10,000 or so and just use that as your step size. You definitely don't want to just use some static number
because then if I want to plot over (0, .001) my graph will look sketchy.
