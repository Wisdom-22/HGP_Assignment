//HDC-HGP Assignment 01 
//Chukwuemeka Wisdom Arinze, 2970177 (April 2024)

//Complete as per specification and delete TODO comments

package application;
	
import java.awt.Paint;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Random;

//basic javafx imports
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
//imports for layouts
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
//imports for controls
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;

public class StudentListFX extends Application {
	//declare components that require class scope
	Label lblClass;
	ListView <String> lvClass;
	Button btnAdd, btnRemove, changeColorButton; 
	Label lblProfile; 
	Image img; 
	ImageView imv; 
	Label lblName, lblSurname, lblNumber, lblProg, firstnameLabel, surnameLabel, studentnumberLabel, programmeLabel;
	ColorPicker colorPicker;
	Color color;
	
	//TODO update with your own details
	String yourName = "Chukwuemeka Wisdom Arinze";
	String yourStudentNum = "2970177";

	//image section for the add student section
	Image image;
	ImageView imageView;
	
	//boolean variables to validate values in the add student section
	boolean checkfirstname = false;
	boolean checksurname = false;
	boolean checkstudentnumber = false;
	boolean checkprogramme = false;
	
	//string variables that will contain problems when each input field in validated in the add student section
	String firstnameproblem = "";
	String surnameproblem = "";
	String studentnumberproblem = "";
	String programmeproblem = "";
	
	//this string variable will hold the directory of our student photo in the add section
	String studentPhotoDirectory = "";
	
	
	//constructor - instantiate components
	public StudentListFX() {
		//initializing the lables that will display the text "Class List" and "Student Profile"
		lblClass = new Label("Class List:");
		lblProfile = new Label("Student Profile:");
		
		lblName = new Label("Name:");//label that will display the text Name
		firstnameLabel = new Label(); //label that will hold the text being typed
		
		lblSurname = new Label("Surname:");
		surnameLabel = new Label();
		
		
		lblNumber = new Label("Student Number:");
		studentnumberLabel = new Label();
		
		lblProg = new Label("Programme:");
		programmeLabel = new Label();
		
		lvClass = new ListView<String>();
		
		btnAdd = new Button("Add Student");
		btnRemove = new Button("Remove Student");
	
		//try catch block that will initialize the image and image view in the main UI and in the add UI
		try {
			//image section for the main section
			img = new Image("Assets/profilephotos/profile.jpg");
			imv = new ImageView(img);
			
			//image section for the add student
			image = new Image("Assets/profilephotos/profile.jpg");
			imageView = new ImageView(image);
			
		}catch(Exception exception) {
			System.err.println("Something went wrong with the photo section!");
			exception.printStackTrace();
		}		
		
		//calls the method that populates the list view
		populateListView("Assets/studentlist.csv");
	}
	
	//event handling
	public void init() {
		//event that triggers the populateListView method
		lvClass.setOnMouseClicked(event -> populateListView("Assets/studentlist.csv"));
		
		//event that triggers the add student method
		btnAdd.setOnAction(event -> addStudent());
	
	
		//event that triggers the remove student method
		btnRemove.setOnAction(event -> removeStudent());
		
		lvClass.setOnMouseClicked(event ->{
			if(lvClass.getSelectionModel().getSelectedItem() != null) {
				String selectedfirstname = lvClass.getSelectionModel().getSelectedItem().toString();
				System.out.println("Selected Name " + selectedfirstname);
				
				//buffered reader that reads the contents of the cvs file and populates all the appropriate UIs with their
				//respective contents
				try {
					BufferedReader reader = new BufferedReader(new FileReader("Assets/studentlist.csv"));
					String line = "";
					
					while((line = reader.readLine()) != null) {
						String [] readData = line.split(":");
						
						if(readData[0].equals(selectedfirstname)) {
							System.out.println("Found: " + selectedfirstname);
							selectedfirstname = readData[0];
							String selectedsurname = readData[1];
							String selectedstudentnumber = readData[2];
							String selectedprogramme = readData[3];
							String selectedphoto = readData[4];
							//System.out.println("Selected Photo: "+selectedphoto);
							
							//update the labels with the appriopriate student info
							firstnameLabel.setText(selectedfirstname);
							surnameLabel.setText(selectedsurname);
							studentnumberLabel.setText(selectedstudentnumber);
							programmeLabel.setText(selectedprogramme);
							studentPhotoDirectory = selectedphoto;
							
							//populates the image view with the appropriate photos
							try {
								img = new Image(selectedphoto);
								imv.setImage(img);
								
							}catch(Exception exception) {
								System.err.println("Error occured with the student image");
								exception.printStackTrace();
							}
							
						}
					}
					
					reader.close();//closes the reader
				}catch(Exception exception) {
					System.err.println("Error reading firstname, surname, student number, programme");
					exception.printStackTrace();
				}
			}
		});
		
	}
	
	//this method populates the list view with contents from the csv file
	public void populateListView(String filename) {
		try {
			//buffer reader that will read all the student info
			BufferedReader bufReader = new BufferedReader(new FileReader(filename));
			String line = "";
			
			while((line = bufReader.readLine()) != null) {
				String [] readData = line.split(":");
				
				System.out.println(readData[0]);
				
				//populate listview with firstname of all students
				lvClass.getItems().add(readData[0]);
				
			}
			bufReader.close();
		}catch(Exception exception){
			System.err.println("Could not read all the text from the file!");
		}
		
	}
	
	//TODO complete this method
	public void addStudent() { 				
		Stage stage = new Stage(); //stage for the add student section
		stage.setTitle("Add a new student");
		
		//set width and height for the stage
		stage.setWidth(500);
		stage.setHeight(280);
		
		//label that will hold the text firstname and textfield that will allow users to type their firstname
		Label lblfirstname = new Label("FirstName: ");
		TextField firstnameTF = new TextField();
		
		//label that will hold the text surname and textfield that will allow users to type their surname
		Label lblsurname = new Label("Surname: ");
		TextField surnameTF = new TextField();
		
		//label that will hold the text firstname and textfield that will allow users to type into
		Label lblstudentnumber = new Label("Student Number: ");
		TextField studentnumberTF = new TextField();
		
		
		//label that will hold the text programme and combo that will allow users to type select frpm a list of option
		Label lblprogramme = new Label("Programme: ");
		ComboBox<String> combo = new ComboBox<String>();
		combo.setItems(FXCollections.observableArrayList("Masters of Science in Computing", "Bachelor of Science in Computing",
				"Higher Diploma in Computing"));
		combo.setValue("Higher Diploma in Computing");
		
		////label that will hold the text Student Photo and Vutton that will allows users to choose images
		Label lblstudentphoto = new Label("Student Photo");
		Button selectPhoto = new Button("Select Photo");
		
		//Button Ok will validate and write content to the csv file
		//Button cancel will close add student window
		Button buttonOk = new Button("OK");
		Button buttonCancel = new Button("Cancel");		
		
		//events that will trigger the close function and get selectd photo of students
		buttonCancel.setOnAction(event -> stage.close());
		selectPhoto.setOnAction(event -> getStudentPicture());

		//this event will rigger the add student function
		buttonOk.setOnAction(event -> {
			int studentnumber = 0;
			
			//validation of the firstname text field
			if(firstnameTF.getText().isBlank()) { 
				firstnameproblem = "The Fistname field is empty!";
				checkfirstname = false;
				
			}else if(Character.isLowerCase(firstnameTF.getText().charAt(0))){
				firstnameproblem = firstnameproblem + "\nThe first alphabet of your firstname must be uppercase!";
			}	else {
				checkfirstname = true;
			}
			
			//validation of the surname text field
			if(surnameTF.getText().isBlank()) {
				surnameproblem = "The Surname field is empty!";
			}else if(Character.isLowerCase(surnameTF.getText().charAt(0))){
				surnameproblem = surnameproblem + "\nThe first alphabet of your surname must be uppercase!";
			}
			else {
				checksurname = true;
			}
			
			//validation of the student number text field text field
			boolean isnumber = false;
			if(studentnumberTF.getText().isBlank() || studentnumberTF.getText().equals("")) { //checks if the field is empty 
				studentnumberproblem = "Student number is blank!";
				
			}else if(studentnumberTF.getText().length() < 6) {//check if the contents of the field are less than 6 characters
				studentnumberproblem = "Student number is less than 6!";				
				
			}else if(studentnumberTF.getText().length() > 8) {//check if the contents of the field are more than 8 characters
				studentnumberproblem = "Student number is greater than 8!";
				System.out.println("Length of student number: " + studentnumberTF.getText().length());
				
			
			}else {
				try {
					studentnumber = Integer.parseInt(studentnumberTF.getText()); //trying to get to convert text to numbers
				}catch(NumberFormatException exception) {
					System.out.println("Please Enter only numbers");
					studentnumberproblem = "Please Enter only numbers or your input is not a number";
				}
				
				if(studentnumber < 99 && studentnumber > 9999999 ) {//check if numbers are less than 99 and more than 9999999
					//System.out.println("Student number is blank");
					studentnumberproblem = "The Student number is not correct!";
					System.out.println(studentnumberproblem);
				}
				else {
					checkstudentnumber = true;
				}
			}
			
			
			//make sure a value from the combo box has been selected
			if(combo.getValue() != "") {
				checkprogramme = true;
			}
			else {
				programmeproblem = "Programme has not been selected!";
			}
			
			//if all the validation has any problems the showAddStudentProblems will be invoked
			if(checkfirstname == false || checksurname == false || checkstudentnumber == false || checkprogramme == false) {
				showAddStudentProblems();
				
				//sets just the student number text field to "" because it is the most tricky. Trying to get numbers from a text field
				studentnumberTF.setText("");
	
			}
			
			//if all fields passed all the validation then write the contents to the list view 
			if(checkfirstname == true && checksurname == true && checkstudentnumber == true && checkprogramme == true){
				//this displays the firstname on the listview
				lvClass.getItems().add(firstnameTF.getText());//+ ":" + surnameTF.getText() + ":" + studentnumberTF.getText()
				//+ ":"+combo.getValue() + ":" + studentPhotoDirectory );
				
				//this loop helps to pin point the index of the last fbackward slash \\ so we can cut out the picture string from the long directory string
				int start = 0;
				for(int a = studentPhotoDirectory.length()-1; a > 0; a--) {
					if(studentPhotoDirectory.charAt(a) == '\\') {
						//System.out.println("\\"+studentPhotoDirectory.charAt(a));
						start = a+1;
						a = studentPhotoDirectory.length()-1;
						break;
					}
					
				}
				String picture = studentPhotoDirectory.substring(start); //uses substring the extract the picture from the long directory for the student photo 
				File studentPhotoPath = new File(studentPhotoDirectory);//create a file with the long directory for the student photo. This is the source path
				//System.out.println("Picture Path: " + studentPhotoDirectory);
				//System.out.println("Picture: " + picture);
				File file = new File("Assets/profilephotos/"+picture);//this is the directory where the selected photo will be copied to. the destination folder
				
				//String destination = "Assets/profilephotos/"+picture;
				try {
					//copies the selected photo to the destination folder 
					Files.copy(studentPhotoPath.toPath() , file.toPath(), StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					System.err.println("Unable to copy file!");
					e.printStackTrace();
				}
				
				try { //when dealing with files, it is bst wrap it in a try catch block
					//writes the content of all the fields to the csv file 
					BufferedWriter writer = new BufferedWriter(new FileWriter("Assets/studentlist.csv", true));
					String studentInfo = (firstnameTF.getText() + ":" + surnameTF.getText() + ":" + studentnumberTF.getText() + ":" +combo.getValue()
					+":" + file).toString();
					
					writer.append(studentInfo + "\n");
					writer.close();
				
				}catch(IOException exception) {
					System.err.println("Trying to write to the file failed!");
					exception.printStackTrace();
				}
				
					stage.close();
			}
				
		});
	
		
		BorderPane border = new BorderPane(); //main layout
		
		GridPane grid1 = new GridPane(); //sublayout
		//assign all the UIs to the different locations in the sub layout
		grid1.add(lblfirstname, 0, 0);
		grid1.add(firstnameTF, 1, 0);
		grid1.add(lblsurname, 0, 1);
		grid1.add(surnameTF, 1, 1);
		grid1.add(lblstudentnumber, 0, 2);
		grid1.add(studentnumberTF, 1, 2);
		grid1.add(lblprogramme, 0, 3);
		grid1.add(combo, 1, 3);
		
		GridPane grid2 = new GridPane(); //sublayout
		//assigns all the UIs to the different locations in the sub layout
		grid2.add(lblstudentphoto, 0, 0);
		grid2.add(imageView, 0, 1);
		grid2.add(selectPhoto, 0, 2);
		
		//makes sure that the image view is 1/4 of the width of the screen
		imageView.fitWidthProperty().bind(stage.widthProperty().divide(4));
		imageView.setPreserveRatio(true);
		
		HBox hbox = new HBox(); //sublayout
		//aligns all the UIs horizontally
		hbox.getChildren().addAll(buttonCancel, buttonOk);
		hbox.setSpacing(20);
		hbox.setAlignment(Pos.BOTTOM_CENTER);//position in the HBox where all the children will be displayed
		hbox.setPadding(new Insets(20));
	
		border.setLeft(grid1); //the position where the first grid/sub layout will be placed 
		border.setRight(grid2); //the position where the second grid/sub layout will be placed 
		border.setBottom(hbox); //the position where the third sub layout/hbox will be placed
		
		Scene scene = new Scene(border); //create the scene for the add student stage
		
		stage.setScene(scene); //set the scene for the add studnt stage
		
		stage.show();//show the stage for the add student stage
		
	}

	//this method displays if there are any problems with the validation 
	public void showAddStudentProblems() {
		//this method displays all the problems in an Alert
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Student Entry Failed");
		
		alert.setHeaderText("Errors occured in some fields");
		alert.setContentText(firstnameproblem + "\n" + surnameproblem + "\n" + studentnumberproblem + "\n" + programmeproblem);
		
		alert.showAndWait();
	}
	
	//this method allows the user to select a picture from any location in their system
	public void getStudentPicture(){
		FileChooser filechooser = new FileChooser();
		filechooser.setTitle("Select an Image for Student");
		
		ExtensionFilter filter = new ExtensionFilter("Only png and jpg", "*.png", "*.jpg"); //only allows for .jpg and .png files
		
		filechooser.getExtensionFilters().add(filter);
		
		//opens the windows dialog 
		File choosenFile = filechooser.showOpenDialog(null);
		
		if(choosenFile != null) {
			studentPhotoDirectory = choosenFile.getPath().toString();
			//places the image string in the image variable and places the image in the image view
			try {
				image = new Image(choosenFile.toURI().toString());
				imageView.setImage(image);
				
			}catch(Exception exception) {
				System.err.println("Could not load Image!");
				exception.printStackTrace();
			}
		}
		
	}
	
	//this this method removes an student form the list view 
	public void removeStudent() {
			if(lvClass.getSelectionModel().getSelectedItem() != null) {
				String selectedfirstname = lvClass.getSelectionModel().getSelectedItem().toString();
				System.out.println("Selected Name " + selectedfirstname);
				
				try {
					//buffered reader and writer
					BufferedReader reader = new BufferedReader(new FileReader("Assets/studentlist.csv"));
					BufferedWriter writer = new BufferedWriter(new FileWriter("Assets/studentlist.csv", true));
					String line = "";
					
					while((line = reader.readLine()) != null) {
						String [] readData = line.split(":");
						
						if(readData[0].equals(selectedfirstname)) {
							System.out.println("Found: " + selectedfirstname);
							selectedfirstname = readData[0];
							String selectedsurname = readData[1];
							String selectedstudentnumber = readData[2];
							String selectedprogramme = readData[3];
							String selectedphoto = readData[4];
							//System.out.println("Selected Photo: "+selectedphoto);
							line = lvClass.getSelectionModel().getSelectedItem().toString();
							
							writer.append("");
							System.out.println("Student has been removed!");
							//update the labels 
							/*firstnameLabel.setText(selectedfirstname);
							surnameLabel.setText(selectedsurname);
							studentnumberLabel.setText(selectedstudentnumber);
							programmeLabel.setText(selectedprogramme);
							studentPhotoDirectory = selectedphoto;*/
							
							/*try {
								img = new Image(selectedphoto);
								imv.setImage(img);
								
							}catch(Exception exception) {
								System.err.println("Error occured with the student image");
								exception.printStackTrace();
							}*/
							
						}
					}
					
					reader.close();
					writer.close();
				}catch(Exception exception) {
					System.err.println("Error, could not remove student!");
					exception.printStackTrace();
				}
			}
		
	}
	
	//this method is called everytime the program is executed
	public String changeColor() {
		//this method randomly generates a number and this number will be the index for a string of colors.
		//this colors will be used as the background color for the main layout of our application
		Random random = new Random();
		int number = random.nextInt(10);
		System.out.println("Random " + number);
		//array of colors
		String [] colors = {"Purple", "Red","Green", "Blue", "Orange", "Yellow", "Indigo", "#e2971d", "#8623dc", "Pink", "Brown"};
		
		return colors[number];
	}
	
	//Window management and layouts
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("StudentListFX - " + yourName + " - " + yourStudentNum);
			
		//manage default width and height of window
		primaryStage.setWidth(900);
		primaryStage.setHeight(500);
		
		//create a main layout
		BorderPane border = new BorderPane();
			
		//create sublayouts as needed
		GridPane grid1 = new GridPane();
		VBox vbox = new VBox();
		GridPane grid3 = new GridPane();
			
		//manage spacing and padding of layouts
		//add components to sublayouts
		grid1.add(lblClass, 0, 0);
		grid1.add(lvClass, 0, 1, 1, 2);
		
		//add all the UI elements to the second sub layout
		vbox.getChildren().addAll(btnAdd, btnRemove);
		vbox.setSpacing(10);
		
		//add all the UI elements and position it in the in the third sub layout 
		grid3.add(lblProfile, 0, 0);
		grid3.add(imv, 0, 1);
		grid3.add(lblName, 0, 2);
		grid3.add(firstnameLabel, 1, 2);
		grid3.add(lblSurname, 0, 3);
		grid3.add(surnameLabel, 1, 3);
		grid3.add(lblNumber, 0, 4);
		grid3.add(studentnumberLabel, 1, 4);
		grid3.add(lblProg, 0, 5);
		grid3.add(programmeLabel, 1, 5); 
	
		//add sublayouts to main layout and use the main layout to position all the sub layouts
		border.setLeft(grid1);
		border.setCenter(vbox);
		border.setRight(grid3);
			
		//bind width of image and preserve ratio
		imv.fitWidthProperty().bind(primaryStage.widthProperty().divide(4));
		imv.setPreserveRatio(true);
		
		//get a color from the method changeColor()
		String color = changeColor();
		border.setStyle("-fx-background-color:"+ color + ";");//the gives the background a color
		//border.setBackground(new Background(new BackgroundFill(color, null, null)));
		
		//Create a scene
		Scene scene = new Scene(border);
		
		//applying stylesheet
		scene.getStylesheets().add("student_stylesheet.css");
			
		//Set the scene
		primaryStage.setScene(scene);
			
		//Show the stage
		primaryStage.show();
	}
	
	// Main - launch the application
	public static void main(String[] args) {
		launch(args);
	}
}