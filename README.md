# Bubble_Tracker_Android_App
User Interface:
Layout has 3 parts
First part contains a Bubble which is Green in color and is placed inside a Circle. This Circle changes to a Square and color of Bubble changes to Red if Tolerance exceeds the given range.
Second part is a graph with three horizontal lines. The center line indicates a tolerance of zero, the line above indicates positive range and line below indicates negative range. The gap between the lines is based on the value of tolerance.
Third part is a display of Tolerance with a custom input value. 
Implementation:
There are three core files.
Bubble.java implemented by Gaurav
Graph.java implemented by Victor
MainActivity.java done by both

Bubble.java
Implements a Bubble class which extends View
The two variables paintFrame and paintBubble gives color to our Bubble Frame and Bubble.
It has following methods
Bubble():
It is a constructor for the class
We also gives color to our Frame and our Bubble
setMode():
It takes one integer value as parameter
The default value which is 0 defines a Circle
This value will be updated through updateMode() in our MainActivity.class
Value = 1 will change to Rectangle
setValues():
For bubble to move horizontally and vertically. 
This value will be updated through onSensorChanged() method in our MainActivity.class
Values[0] & Values[1] are assigned to x & y variables respectively in onDraw() method. These values help to draw Bubble inside our Circle Frame which displays as Bubble movement to the viewer. 
Values[2] is assigned to x variable in onDraw() method. This value help to draw Bubble inside our Rectangle Frame which displays as Bubble movement to the viewer
We get these values from our Sensor and we divide it by 10 to get a decimal value to draw
setTolerance():
To set Tolerance for our Bubble.
Bubble will change to Red if it exceeds the limit
The default value which is 5 is given in onCreate() method of our MainActivity class.
This value in updated through updateTolerance() method of our MainActivity class where it is divided by 10 to get a decimal value.

OnDraw():
It contains two important method
The float variables w & h represents width & height of our canvas which is retrieve through getWidth() & getHeight() methods.
The float variables cx & cy are divided by 2 to cover half portion of our canvas. These values are then use to draw Circle frame and draw Rectangle frame.
drawCircle():
If frame is a circle, it allows our bubble to move in all directions, 360 degrees.
The first time is draws Circle Frame using dimensions cx, cy, r. 4th parameter paintFrame defines the color of the frame.
The second time it draws our Bubble.
	drawRect(): 
If frame is a rectangle, our bubble will only move horizontally. These values are retrieved from values[2]
		

Graph.java
Implements a Graph class which extends View
The variables paintData and paintAxis defines color for our points on graph and axis respectively.
It defines a floating LinkedList called history use to store all values from sensor required for ploting.
It contains following methods
Graph():
It is a constructor for the class
We also give color to our points on graph and Axis.
setMode():
It takes one integer value as parameter
The default value which is 0 which defines a Circle Frame
This value will be updated through updateMode() in our MainActivity.class
Value =1 represents a Rectangle Frame
addValues():
This value will be updated through onSensorChanged() method in our MainActivity.class
We get these values from our Sensor and we divide it by 10 to get a decimal value to draw
It will remove values from the list once the size exceeds 50
setTolerance():
To set Tolerance for our points.
Points will change to Red if it exceeds the limit
The default value which is 5 is given in onCreate() method of our MainActivity class.
This value is updated through updateTolerance() method of our MainActivity class where it is divided by 10 to get a decimal value.
OnDraw():
It contains two important method
The float variables w & h represents width & height of our canvas which is retrieve through getWidth() & getHeight() methods.
We then compute our ArrayList to consider Circle Frame values or Rectangle Frame values
The float variables cx & cy are then use to plot these values to our graph.
	drawLine():
It is used thrice to draw three straight horizontal lines on our graph as centered, up and down
	It also gives color to our Axis
MainActivity.java
This class extends AppCompatActivity class and implements SensorEventListener
In our onCreate() method, SensorManager is use to access sensor service and we will required ACCELEROMETER as our SENSOR. We will also give our tolerance a default value of 5 using setText(). 
updateMode():
This will change the parameter value of our setMode() method in Bubble.java and Graph.java
updateTolerance():
This will change the Tolerance of our bubble and graph to the input given by user in the field.
Here we divide the value by 10 to get decimal value which will change the parameter value of our setTolerance() method in Bubble.java and Graph.java
onResume():
This will notify our Sensor to run if it is paused due to some change in the activity.
For example, if the application is put in the background, onPause() will unable the sensor to work and this method will again start once the activity is started.
onPause():
This is to stop our sensor from running if there is any change in our activity.
For example, if the application is put in the background.
onSensorChanged():
We will take values from our sensor as it rotates. Values[0] & Values[1] are our values for Circle enabling it to rotate 360 degrees. And Values[2] is for Rectangle enabling it to move only horizontally.
These values will help our bubble to move and are stored in LinkedList to plot them in our graph.
RotateSensorValues():
This method is for rotation
The representation is as follows
{ 1, -1, 0, 1 }, // ROTATION_0
{-1, -1, 1, 0 }, // ROTATION_90
{-1,  1, 0, 1 }, // ROTATION_180
{ 1,  1, 1, 0 }  // ROTATION_270

The matrix transformation over here states that irrespective of the mobile movement in vertical position of in slanting position it will makes sure it gets the same value.

The link to the source is given in the comment for more explanation
