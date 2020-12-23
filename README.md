## Monitor
A JavaFX component that can be used to visualize either stored or live data

Donations are welcome at [Paypal](https://paypal.me/hans0l0)

### Description
![Overview](https://raw.githubusercontent.com/HanSolo/monitor/main/monitorBig.jpg)

The monitor control is NOT an oscilloscope (even if it looks similar). In principle it moves a dot
over it's screen with a defined speed where the y value for the dot either comes from data that is
provided in a list or that comes from livedata.
There is some example data available (heart rate data) that you can find in the file EcgData.java.
If you provide this data to the control it will visualize this data simply in a loop over and over again.
In case you would like to visualize live data you simple can add the current live y-value by calling
the addDataPoint(Number number) method. This will add the data point at the current position of the dot.
There are several options available to change the visualization.
You could either have a line that fades out after a given number of segments (0-250) or if you prefer
to have the line stay on the screen and only refresh it at the current x position of the dot you can
switch the lineFading to false.
You could also switch of the glow effect of the dot if you like and there is also a crystal effect
overlay that can be switched off.
If you like you can of course switch off the raster and the text. Also the dotSize can be defined (1-5).
The lineWidth can also be set in the range from 0.5 - 5.
Another value that you can adjust is the timespan. This can be set between 1-10 seconds and will define
the time the dot needs to move from left to right.
If you would like to adjust the speed of the dot you can set the speedFactor (0.1 - 10).
Just keep in mind that if you increase the speed the scale will also change!!!
If you have a timespan of 5 seconds and a speedFactor of 1 the dot will move from left to right
in 5 seconds. If you set the speedFactor to 2 the beam will move from left to right in 2.5 seconds
and therefore the scaling will be adjusted.
In addition one can define the colors for the background, the line, the raster and the text.
Because there some common oscilloscope colors out there I've provided a Theme enum that already contains
some common color themes. To use them the Monitor control has a method setTheme() which simply sets
all color parameters according to the given theme. 
Theme implements the ColorTheme interface so that you can also define our own themes and use them
with the same method.
If the signal amplitude is to high to show it on the screen you can set the scaleFactorY to a value
between 0.05 - 10 to scale the signal in y-direction to fit it on the screen.

### Here is an image that shows the available themes
![Themes](https://raw.githubusercontent.com/HanSolo/monitor/main/monitor.jpg)