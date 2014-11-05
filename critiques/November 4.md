What are one or more things that you like about this project? What's exciting?

I love that this project could provide an easy way to graph piecewise functions, 
and functions in general. Personally I find a lot of graphing software very cumbersome
so I would love to see this project come to fruition. 

What's the balance of language design vs sheer programming / engineering in this project?

I think that will all come down to choosing the right tools for the job. If you choose
graphing software with a good API that's easy for you to call there shouldn't be too 
much sheer programming, but it's hard to say. Make sure you have a "hello world" working 
in your potential graphing API before you commit to it. 

How can the project maximize the time spent on language design? How to focus on interesting, possibly new ideas?

I'm not sure what there is to do beyond trying hard to select a good API for rendering your graphs.
I would encourage you to focus on piecewise functions and simple 2D functions for now. If you want to
add something like 3D capability I think it will probably add a lot to the sheer programming without 
adding much complexity to the underlying language. 

The really interesting idea here is - what is the best way to describe the content
of a graph in text?

What are some interesting language design questions that the work will have to answer? In other words, what are the design challenges? Which design problems' solutions are you looking forward to hearing about at the end of the project?

I'm looking forward to seeing what kind of syntax you can come up with. Fundamentally youre trying to represent
a picture (albeit a very well structured picture) as an easily readible bit of text, which is really 
cool! I think that for 2D you can make some assumptions about the nature of graphs - e.g. the user
only has to specify how y varies with x, and the graph can be drawn from there. 

What are the primitives in this language?

I think the primitives of this language are segments of the number line. 

Fundamentally the user is specifying how y varies from x along the whole number line, which we want
to break up into pieces to represent piecewise functions. The user will specify how y varies with x 
over each subsection of the number line. A subsection in this case can easily be the entire number
line itself. 

Maybe we want to go another level deeper, and say that a primitive in this language is a 
function determining how y varies in x? There are several interesting questions here - are you 
going to support me saying y = sin(x)? What about e^x? You'll have to build up a full mathematical
parser as well as the translation from the IR to a graph. 

Another interesting question is will you provide support for user defined functions? If I
want to graph 

y = sin(cos(sin(cos(x)))) + sin(cos(sin(cos(x)))) 

Can I say

f(x) := sin(cos(sin(cos(x))))

y = f(x) + f(x)

I would steer you away from providing support for that, at least on the first pass. 

I'm also curious about the way the conversion from the mathematical formula to graph
will work. Are you planning on just getting the formula and then sampling that formula
at some number of points and graphing those points? Or do you think there is an API that
will let you feed in the formula? 

Do you know of any libraries, languages, or projects that might help this project?

The only graphing software I know of is GNUPlot, which is probably only worth looking
into if your other options don't pan out for whatever reason. 

With regard to your specific questions: Yes I would want to see 3D graphs supported, but
my advice would be to worry about that after you have 2D working and polished. 


I think one idea for piecewise input could be


NInf - 0: 0

0 - 2: x^2

2 - Inf: x


Specify a piecewise function that is zero from negative infinity to 0, 
x^2 from 0 to 2, and x from 2 to infinity. I'd love to hear what you've come up with though! 
