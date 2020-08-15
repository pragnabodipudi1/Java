# Java
A Java application using graphics and concurrency (multithreading) to display an animated version of various common sorting algorithms

The Classes 

A class to represent the sorting animation application and inherits from JFrame.  It has a pair of SortPanel objects as data members.  The class has a custom constructor that passes a title bar string to the superclass constructor and adds the two SortPanel objects to the application’s layout.  It also has a main() method that creates an instance of the application, sets the default close operation and size, and makes the application visible. 

The SortPanel class encapsulates the display and controls for the sorting animation and inherits from JPanel.  It has buttons to populate the array and start the sort, as well as a control (such as a JComboBox or JSpinner) that allows selection of the sorting algorithm to use in the sort.  It also has a SortAnimationPanel as one of its data members.  The constructor for the class manages the layout for the controls and animation panel.  This class also handle events from the buttons. 

The SortAnimationPanel class will display the visual results of sorting and inherit from JPanel and implement the Runnable interface.  When the “Populate Array” button is pressed, it creates a new array of integers; the size of this array  is the width of the animation panel. Next Fills the array with random values in the range 0 to the height of the animation panel.  Once the array is populated, calls repaint() to display it, disable the “Populate Array” button, and enable the “Sort” button.

I override the paintComponent() method for the SortAnimationPanel class.  After I call the superclass version of the method, got the dimensions of the panel and cleared it.  Then, if the array of integers is not null, drawn a series of lines on the panel surface representing the array elements.  The height of each line represents the integer value of the corresponding integer array element. 

When the “Sort” button is pressed, I disabled the “Sort” button and created a new Thread object from my Runnable SortAnimationPanel.  Later Called the Thread object’s start() method to start the sort. 

The run() method calls the appropriate sort method based on the user’s selection to sort the array of integers in ascending order.  As the sorting algorithm progresses, calls repaint() any time two elements are swapped.  After each pass through the sort algorithm’s outer loop (or equivalent), had the thread sleep for a short time.   Effectively, the thread will pause for a short time every time an element is put into sorted order. 

I provide three different sorting algorithms that the user can choose from for each panel. 

When the array is completely sorted, enabled the “Populate Array” button so that the user can start the process over. 
