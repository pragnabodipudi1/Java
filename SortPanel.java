/*******************************************************************************
  *                                                                        *
  *                                                                        *
  * Programmer : Bodipudi Pragna                                           *
  *                                                                        *
  *                                                                        *
  * Purpose : This program implements two Jspinners, five buttons and      *
  * implements bubble,selection,quick sorts                                *
  *                                                                        *
  ******************************************************************************/

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;

import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SortPanel extends JPanel 
{ 
	private static final long serialVersionUID = 1L;

	// JButtons 
	private final JButton populateButton, // to populate random data
						  sortButton, // To sort data or resume the sorting process
						  populateAscend, // To populate data in ascending order
						  populateDescend, // To populate data in descending order
						  stop; // To stop or pause the sorting process

    // JSpinners
	private final JSpinner sortTypeSpinner, // Spinner that has 'bubble sort', 'selection sort', ' Quick sort'
						   speedTypeSpinner; // Spinner that has 'Fast', 'Medium', 'Slow' pace
 
	private final SortAnimationPanel sortAnimationPanel; // animation panel 

 
	private boolean populated = false;
	private int[] arrayOfHeight; // array of arrayOfHeight for sorting
 
	// String arrays to display in JSpinners
	private final String[] sortTypes = { "Bubble Sort", "Selection Sort", "Quick Sort" };
	private final String[] speedTypes = { "Fast", "Medium", "Slow" };
 
 
	/* sort panel constructor, builds panel */
	public SortPanel() 
	{
		setLayout(new GridBagLayout());

		// specifying gridbag constraints for animation panel
		GridBagConstraints constraints = new GridBagConstraints();
  
		constraints.fill = GridBagConstraints.BOTH; 
		constraints.gridx = 0; 
		constraints.gridy = 0;
		constraints.ipady = 300;
  
		sortAnimationPanel = new SortAnimationPanel();
		add(sortAnimationPanel, constraints); // adding panel to frame

		Thread thread = new Thread(sortAnimationPanel); //creation of new thread
  
		// Creating an object for buttonsPanel and setting constraints for it.
		JPanel buttonsPanel = new JPanel();            
		constraints.gridx = 0;
		constraints.gridy = 1;
		buttonsPanel.setLayout(new FlowLayout());
  
		//Ascend button which populates the data in ascending order and its listener
		populateAscend = new JButton("Ascend");
		buttonsPanel.add(populateAscend);
  
		populateAscend.addActionListener(new ActionListener() {       
			@Override
			public void actionPerformed(ActionEvent e) {   
				arrayOfHeight = generateIncreasingRandoms(sortAnimationPanel.getWidth(),  sortAnimationPanel.getHeight());   //Setting the different dimensions of array.
				populateButton.setEnabled(false);
				sortButton.setEnabled(true);
				populated = true;
				repaint();
			}
		});
		//Ascend ends
  
		//Descend button which populates the data in descending order and its listener
		populateDescend = new JButton("Descend");
		buttonsPanel.add(populateDescend);
  
		populateDescend.addActionListener(new ActionListener() {       
			@Override
			public void actionPerformed(ActionEvent e) {
				arrayOfHeight = generateDecreasingRandoms(sortAnimationPanel.getWidth(),  sortAnimationPanel.getHeight());   //Setting the different dimensions of array.
				populateButton.setEnabled(false);
				sortButton.setEnabled(true);
				populated = true;
				repaint();
			}
		});
		//Descend ends
  
		//populateButton which populates data in random order and its listner
		populateButton = new JButton("Populate");
		buttonsPanel.add(populateButton);

		populateButton.addActionListener(new ActionListener() {       
			@Override
			public void actionPerformed(ActionEvent e) {
				Random random = new Random();
				arrayOfHeight = random.ints(sortAnimationPanel.getWidth(), 0, sortAnimationPanel.getHeight()).toArray();   //Setting the different dimensions of array.
				populateButton.setEnabled(false);
				sortButton.setEnabled(true);
				populated = true;
				repaint();
			}
		});
		//populate button ends

		//sort/resume button which is used to sort the data or resume the sorting process and its listener
		sortButton = new JButton("Sort/Resume");
		ButtonModel model = sortButton.getModel();
		sortButton.setEnabled(false);
		buttonsPanel.add(sortButton);

		// Action listener to start the thread (sorting process)
		sortButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sortButton.setEnabled(false);
				thread.start();
			}
		});
  
		//state change listener to resume the thread (sorting process)
		sortButton.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if(model.isPressed())
				{
					thread.resume();
				}
			}
		});
		//sort/resume button ends
		
		// 'stop/pause' button which is used to stop or resume the sorting process and its listener
		stop = new JButton("stop/Pause");
		buttonsPanel.add(stop);
		stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stop.setEnabled(false);
				thread.suspend();
				stop.setEnabled(true);
				sortButton.setEnabled(true);
			}
		});
		//'stop,pause' button ends
  

		// JSpinner for sort-types to list out the different sorting techniques available (bubble sort, selection sort, quick sort)
		SpinnerListModel sortModel = new SpinnerListModel(sortTypes);
		sortTypeSpinner = new JSpinner(sortModel);
		sortTypeSpinner.setEditor(new JSpinner.DefaultEditor(sortTypeSpinner));
		buttonsPanel.add(sortTypeSpinner);

  
		//JSpinner for speed types to list out the different speed types available (fast, medium, slow)
 		SpinnerListModel speedModel = new SpinnerListModel(speedTypes);
		speedTypeSpinner = new JSpinner(speedModel);
		speedTypeSpinner.setEditor(new JSpinner.DefaultEditor(speedTypeSpinner));
		buttonsPanel.add(speedTypeSpinner);
		//ends speed
  
		add(buttonsPanel, constraints);
}

	
	
public class SortAnimationPanel extends JPanel implements Runnable
{
	private static final long serialVersionUID = 1L;   //Constant related to index returned to JComboBox index.

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int height = getHeight();
		this.setBackground(Color.WHITE);
		g.setColor(Color.BLUE);

		if (populated)
		{ 
			for (int i = 0; i < arrayOfHeight.length; i++)
			{ 
				g.drawLine(i, height, i, height - arrayOfHeight[i]);
			}
		}
	}

	@Override
	/* runs when Thread.start() is called */
	public void run()
	{ 
		populateButton.setEnabled(true);
		// sorts according to drop down selection
		if (sortTypeSpinner.getValue().equals("Bubble Sort")) 
		{
			bubbleSort();
		}
		else if (sortTypeSpinner.getValue().equals("Selection Sort"))
		{
			selectionSort();
		}
		else
		{
			quickSort(0, arrayOfHeight.length - 1);
		}
  }
 }
 
 //Ascending order of random numbers which will be supplied on pressing Ascend button 
   int[] generateIncreasingRandoms(int amount, int max) 
   {
	    int[] randomNumbers = new int[amount];
	    Random random = new Random();
	    for (int i = 0; i < randomNumbers.length; i++)
	    {
	        randomNumbers[i] = random.nextInt(max);   
	    }
	    Arrays.sort(randomNumbers);
	    return randomNumbers;
	}
   
 //Descending order of random numbers which will be supplied on pressing Descend button
   int[] generateDecreasingRandoms(int amount, int max)
   {
	    int[] randomNumbers = new int[amount];
	    Random random = new Random();
	    
	    for (int i = 0; i < randomNumbers.length; i++)
	    {
	        randomNumbers[i] = random.nextInt(max);   
	    }
	 
	    Arrays.sort(randomNumbers);
	    
	    int k = randomNumbers.length-1;
	    int temp;
	    
	    for(int i = 0; i < randomNumbers.length/2 ; i++)
	    {
	    	temp = randomNumbers[k];// swaping
	    	randomNumbers[k] = randomNumbers[i];
	    	randomNumbers[i] = temp;
	    	k--;
	    }
	    
	    return randomNumbers;
	}

 
/* Bubble sort algorithm code ascending */
 
 void bubbleSort()
 {
	 int n = arrayOfHeight.length;
	 for (int i = 0; i < n - 1; i++)
	 {
		 for (int j = 0; j < n - i - 1; j++)
		 {
			 if (arrayOfHeight[j] > arrayOfHeight[j + 1])
			 {
				 swap(j, j + 1);  // Swapping the array height values.
			 }
		 }
		 try
		 {
			 if(speedTypeSpinner.getValue().equals("Fast"))
			 {
				 Thread.sleep(100);  //fast sleep time
			 }
			 else if(speedTypeSpinner.getValue().equals("Medium"))
			 {
				 Thread.sleep(1000); //Medium sleep time
			 }
			 else
			 {
				 Thread.sleep(5000);  //slow sleep time
			 }
		 }
		 catch (Exception e)
		 {
			 e.printStackTrace();
		 }
  }
  populateButton.setEnabled(true);
  populateAscend.setEnabled(true);
  populateDescend.setEnabled(true);
 }

 /* Selection Sort: sorts an array by repeatedly finding the minimum element 
 from unsorted part and putting it at the beginning. ascending order */
 
 void selectionSort()
 {
	 int minValue, n = arrayOfHeight.length;
	 for (int i = 0; i < n - 1; i++)
	 {
		 minValue = i;
		 for (int j = i + 1; j < n; j++)
			 if (arrayOfHeight[j] < arrayOfHeight[minValue])
			 {
				 minValue = j;
			 }
		 swap(minValue, i);  // Swapping values based on the logic.

		 try 
		 {
			 if(speedTypeSpinner.getValue().equals("Fast"))
			 {
				 Thread.sleep(100);  //fast sleep time
			 }
			 else if(speedTypeSpinner.getValue().equals("Medium"))
			 {
				 Thread.sleep(1000); //Medium sleep time
			 }
			 else
			 {
				 Thread.sleep(5000);  //slow sleep time
			 }
		} 
		 catch (Exception e)
		 {
			 e.printStackTrace();    // Sending an exception to be resolved so that it doesn't terminate the program.
		 }
	 }
	 populateButton.setEnabled(true);
	 populateAscend.setEnabled(true);
	 populateDescend.setEnabled(true);
 }

 
 // quick sort to sort the data in ascending order
 void quickSort(int minValue, int maxValue)
 {
	 if (maxValue > minValue)
	 {
		 int pi = partition(minValue, maxValue);
		 quickSort(minValue, pi - 1); // recursive calling 
		 quickSort(pi + 1, maxValue);
	 }
	 populateButton.setEnabled(true);
	 populateAscend.setEnabled(true);
	 populateDescend.setEnabled(true);
 }

 // method for partition in quick sort
 int partition(int minValue, int maxValue)
 {
	 int i = (minValue - 1); // index of smaller element
	 for (int j = minValue; j < maxValue; j++)
	 {
		 if (arrayOfHeight[j] <= arrayOfHeight[maxValue])
		 {
			 i++;
			 swap(i, j);
		 }
	 }

	 swap(i + 1, maxValue);  //Swapping values of height.
	 try
	 {
		 if(speedTypeSpinner.getValue().equals("Fast"))
		 {
			 Thread.sleep(100);  //fast sleep time
		 }
		 else if(speedTypeSpinner.getValue().equals("Medium"))
		 {
			 Thread.sleep(1000); //Medium sleep time
		 }
		 else
		 {
			 Thread.sleep(5000);  //slow sleep time
		 }
	 }
	 catch (Exception e)
	 {
		 e.printStackTrace();
	 }
	 return i + 1;
 }

 /* Swap function to swap values */
 void swap(int index1, int index2) 
 {
	 int temp = arrayOfHeight[index1];
	 arrayOfHeight[index1] = arrayOfHeight[index2];
	 arrayOfHeight[index2] = temp;

	 repaint();
 }
}