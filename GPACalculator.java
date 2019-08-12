import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/* Dustin Nguyen
 * 
 * ddn3aq
 * 
 * Homework 5
 * 
 * Sources:

*/
public class GPACalculator extends JFrame {//class needs to extend JFrame to make GUI possible
	//my fields
	private JLabel summaryLabel;
	private JLabel gpaLabel;
	private JLabel requiredGpaLabel;
	private JLabel suggestionLabel;
	private JLabel instructions;
	private JTextField courseField;
	private JTextField creditField;
	private JTextField gpaField;
	private JTextField targetField;
	private JButton addCourseButton;
	private JButton addCreditHours;
	private JButton addGpaButton;
	private JButton removeCourse;
	private JButton removeAll;
	private JButton showGpa;
	private JButton targetGpa;
	private FlowLayout layout = new FlowLayout();
	
	public void addComponentsToPane(Container pane) {
		//create new objects and add them into a panel, and then the pane HERE
        Course[] myList = new Course[15];
        
		JPanel panel1 = new JPanel();
		panel1.setLayout(layout);
		courseField = new JTextField(10); // making my fields
		creditField = new JTextField(10);
		gpaField = new JTextField(10);
		targetField = new JTextField(10);
		JPanel panel3 = new JPanel();
		panel3.setLayout(layout); 
		String[] courses = {"                                                                      ","               ", "               ", "               "
                , "               ", "               ", "               ", "               ", "               "
                , "               ", "               ", "               ", "               ", "               "
                , "               "};
				
		JList<String> coursesList = new JList<String>(courses); //this will be my visual list of all the courses i have
		class ButtonListener implements ActionListener {
			int i = 0;			
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("click")) {
					//ACTION HERE ,... BASICALLY TELL WHAT THE CODE SHOULD DO WHEN THE USER CLICKS THE BUTTON
					int location = coursesList.getSelectedIndex();
					String enteredCourse = courseField.getText().toString();			        
			        myList[location].setCourseName(enteredCourse);
			        courses[location] = myList[location].toString();	
			        coursesList.revalidate(); // this code allows the GUI to automatically update the JList when a course is added or removed
					coursesList.repaint();
				}
			}
		}
		
        panel3.add(coursesList);
        
        //ALL MY BUTTON AND BUTTON CLASSES
		addCourseButton = new JButton("Set Name");
		addCourseButton.setActionCommand("click");
		addCourseButton.addActionListener(new ButtonListener());
		
		
		class CreditHoursButtonListener implements ActionListener {
			int totalHours = 0;
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("click")) {
					//ACTION HERE ,... BASICALLY TELL WHAT THE CODE SHOULD DO WHEN THE USER CLICKS THE BUTTON
								
					String credhrs = creditField.getText();
					totalHours = totalHours + Integer.parseInt(credhrs); //takes in what the user inputs and changes to int
					Course myCourse = new Course(Integer.parseInt(credhrs));
					int location = coursesList.getSelectedIndex();
					courses[location] = myCourse.toString(); // ads the .toString course to the visual list, and the actual course to my real lsit
					myList[location] = myCourse;
					
					coursesList.revalidate();
					coursesList.repaint();
				}
			}
		}
		addCreditHours = new JButton("Add Credit Hours");
		addCreditHours.setActionCommand("click");
		addCreditHours.addActionListener(new CreditHoursButtonListener());
		
		class GpaButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("click")) {
					//ACTION HERE ,... BASICALLY TELL WHAT THE CODE SHOULD DO WHEN THE USER CLICKS THE BUTTON
										
					String gpaString = gpaField.getText();
					
					int location = coursesList.getSelectedIndex();
			        myList[location].setGrade(Double.parseDouble(gpaString)); //changes user input to a double for ease of use
			        courses[location] = myList[location].toString();
			        
			        coursesList.revalidate(); // update visuals
					coursesList.repaint();
				}
			}
		}
		
		addGpaButton = new JButton("Add GPA");
		addGpaButton.setActionCommand("click");
		addGpaButton.addActionListener(new GpaButtonListener());
		
		//adding all my objects to panel 1
		panel1.add(creditField);
		panel1.add(addCreditHours);
		panel1.add(courseField);
		panel1.add(addCourseButton);
		panel1.add(gpaField);
		panel1.add(addGpaButton);

		panel1.setBackground(Color.getHSBColor(36, 366, 34)); //making things visually appealing!

		//-----------------------------------------------
		
		JPanel panel2 = new JPanel();
		panel2.setLayout(layout);
		layout.setAlignment(FlowLayout.CENTER);
		summaryLabel = new JLabel("~Summary~"); //setting up labels for the summary part
		panel2.add(summaryLabel);
		gpaLabel = new JLabel("Current GPA: ");
		panel2.add(gpaLabel, BorderLayout.WEST);
		
		
		JPanel panel4 = new JPanel();
		panel4.setLayout(layout);
		layout.setAlignment(FlowLayout.CENTER);
		
		instructions = new JLabel("<html>1. Click on a white slot and use Credit Hour button to add a Course.<br/> "
				+ "2. Click on a course and use GPA and Name button to add gpa/name. <br/> 3. Click on a course then remove button to remove it/all.  </html>");

		panel4.add(instructions);
		class ShowGpaButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("click")) {
					//ACTION HERE ,... BASICALLY TELL WHAT THE CODE SHOULD DO WHEN THE USER CLICKS THE BUTTON
					
					double gpa = 0.0;
					int totalCredits = 0;
					
					for(int i = 0; i < myList.length; i++) {
						if (myList[i] != null) {		//calculates the current gpa for all the courses with a grade
							if (myList[i].getGrade() != 0.0) {
								gpa = gpa + (myList[i].getGrade() * myList[i].getCreditHours());
								totalCredits = totalCredits + myList[i].getCreditHours();
							}
						}
					}
					gpaLabel.setText("GPA: " + gpa / totalCredits);
				}
			}
		}
		showGpa = new JButton("Show GPA"); // implementing my showGpa button w the class
		showGpa.setActionCommand("click");
		showGpa.addActionListener(new ShowGpaButtonListener());
		
		class TargetGpaButtonListener implements ActionListener {
			
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("click")) {
					//ACTION HERE ,... BASICALLY TELL WHAT THE CODE SHOULD DO WHEN THE USER CLICKS THE BUTTON
					
					String targetGpaString = targetField.getText();
					double targetGpa = Double.parseDouble(targetGpaString);
					
					int totalCredits = 0;
					double existingGradePoints = 0.0;	//calculates the remaing GPA required that will boost the user to their target GPA
					int remainingCredits = 0;
					for(int i = 0; i < myList.length; i++) {
						if (myList[i] != null) {
							totalCredits = totalCredits + myList[i].getCreditHours();
							existingGradePoints = existingGradePoints + (myList[i].getGrade() * myList[i].getCreditHours());
							if (myList[i].getGrade() == 0.0) {
								remainingCredits = remainingCredits + myList[i].getCreditHours();
							}
						}
				}
					double requiredGpa = (targetGpa * totalCredits - existingGradePoints) / remainingCredits;
					requiredGpaLabel.setText("Required GPA: " + requiredGpa);
					if (requiredGpa < 2.0) {
						suggestionLabel.setText("You can take fewer credits if you want!"); //gives the user a suggestion based on their required goa
					}
					if (requiredGpa > 4.0) {
						suggestionLabel.setText("Try to add more credit hours and try again!");	
					}
					if (requiredGpa >= 2.0 && requiredGpa <= 4.0) {
						suggestionLabel.setText("Good Luck!");	
					}
			}
		}
				}
		targetGpa = new JButton("Set Your Target GPA / Show Req. GPA");
		targetGpa.setActionCommand("click");
		targetGpa.addActionListener(new TargetGpaButtonListener());
		requiredGpaLabel = new JLabel("Required GPA: ");
		suggestionLabel = new JLabel();
		//adding all my objects into the panel 
		panel2.add(showGpa);
		panel2.add(targetField);
		panel2.add(targetGpa);
		panel2.add(requiredGpaLabel);
		panel2.add(suggestionLabel);
		panel2.setBackground(Color.orange);
		//--------------------------------------------------
		
		class RemoveButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("click")) {
					//ACTION HERE ,... BASICALLY TELL WHAT THE CODE SHOULD DO WHEN THE USER CLICKS THE BUTTON
					int location = coursesList.getSelectedIndex();
					myList[location] = null; //removes everything from the actual list of courses
					courses[coursesList.getSelectedIndex()] = ""; //visually remove all courses from the JList
					
					coursesList.revalidate();
					coursesList.repaint();
				}
			}
		}
		removeCourse = new JButton("Remove a Course");
		removeCourse.setActionCommand("click");
		removeCourse.addActionListener(new RemoveButtonListener());
		
		class RemoveAllButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("click")) {
					//ACTION HERE ,... BASICALLY TELL WHAT THE CODE SHOULD DO WHEN THE USER CLICKS THE BUTTON
										
					for (int i = 0 ; i < courses.length ; i++) {
						courses[i] = ""; //removes all courses from list and Jlist
						myList[i] = null;
					}
					
					coursesList.revalidate();
					coursesList.repaint();
				}
			}
		}
		removeAll = new JButton("Remove All Courses");
		removeAll.setActionCommand("click");
		removeAll.addActionListener(new RemoveAllButtonListener());
		
		panel3.add(removeCourse);
		panel3.add(removeAll);
		panel3.setVisible(true);
		panel3.setBackground(Color.pink);
        
        //---------------------------
		//adding my panels to the pane

		pane.add(panel1, BorderLayout.PAGE_START);
		pane.add(panel2, BorderLayout.CENTER);
		pane.add(panel3, BorderLayout.SOUTH);
		pane.add(panel4, BorderLayout.EAST);
	
		//-------------------------		
	}
	private static void createAndShowGUI() {
		//code here to set up and show the window
		GPACalculator frame = new GPACalculator();
		frame.setTitle("GPA Calculator and Planner");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addComponentsToPane(frame.getContentPane());
		frame.pack();
		frame.setVisible(true);	
	}
	public static void main(String[] args) {
		//main method with invokeLater and a run() method ... will add elements to GUI and show it
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {     
				createAndShowGUI();
			}
		});
	}
}
