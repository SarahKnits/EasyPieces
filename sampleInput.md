=============
First Program
=============

-10 < x < 10  
-10 < y < 10  

f(x) = { 2 > x, x^2  
f(x) = { otherwise, 2 * x  


-------------------------------------------------------------------------------
The corresponding equation in Grapher:

y = x <= 2? x^2 : 2 * x

Although this isn't too bad, it's not as intuitive. Grapher also tries to set
the proper bounds, but requires a bit of work to get desired bounds.

Additionally, Grapher doesn't automatically have open or closed circles for
boundaries.

-------------------------------------------------------------------------------


==============
Second Program
==============

-10 < x < 10  
-10 < y < 10  

g(x) = { 2 >= x, x + 2  
g(x) = { 4 > x > 2, x + 3  
g(x) = { otherwise, x + 4  

-------------------------------------------------------------------------------
The corresponding equation in Grapher:

y = x <= 2? x + 2 : (x< 4 ? x + 3 : x + 4)

This is a bit harder to follow. It again doesn't define the boundaries.


-------------------------------------------------------------------------------



