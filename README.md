# Introduction 
VioletDroid is a CS educational app for drawing UML diagrams on Android devices.  
• Draws class & sequence diagrams to visualize the design of program   
• Synchronizes diagrams in backend database using LeanCloud   
• Enables sharing completed diagrams via email, Facebook, SMS, etc.   

<img src="https://github.com/dilyar85/VioletDroid/blob/master/screenshots/main_screen.png" alt="Main Screenshot" height="500" >


# Android Framework
* VioletDroid uses Activity - Fragment template to accomplish its major feature. 

* RecyclerView with corresponding adapter displays the elements needed in UML diagrams.  

* Custom View class to display and modify current diagrams.   


# Design Patterns
VioletDroid uses three major design patterns: Decorator Pattern, Composite Pattern, and Observer Pattern.

* **Decorator Pattern**：  
RecyclerAdapter class extends Android’s native RecyclerView.Adapter to display the icons in the toolbar menu as a scrolling dataset of images.  This is an example of the decorator pattern used in the Android API—RecyclerView essentially decorates an Android ViewGroup to provide the additional scrolling functionality and link to a dataset of assets for the view.  The following table represents this pattern:  
<img src="https://github.com/dilyar85/VioletDroid/blob/master/screenshots/decorator_pattern.png" alt="pattern_img" height="200">

* **Composite Pattern**：  
The composite pattern is used in the VioletDroid CanvasLayout class which extends Android’s native RelativeLayout class, which extends the ViewGroup abstract class which extends View.  So CanvasLayout is a ViewGroup, which is a View that can contain other Views.  The following table represents this pattern in our project:  
<img src="https://github.com/dilyar85/VioletDroid/blob/master/screenshots/composite_pattern.png" alt="pattern_img" height="200">

* **Observer Pattern**：  
The observer pattern is another very common occurrence in User Interface design, particularly with Graphical User Interfaces, since the signal for the application to do something comes from the user’s interaction with elements on-screen, those elements must somehow notify the application what has happened - hence the listeners involved in an observer pattern:  
<img src="https://github.com/dilyar85/VioletDroid/blob/master/screenshots/observer_pattern.png" alt="pattern_img" height="200">

# Service Framework
We utilized a cloud service framework called **LeanCloud** in our project to facilitate user account maintenance and saving of diagrams. It's based on AWS but it is much more convenient for developers:
* No need to write codes for backend service and database.
* Easy to implement, support importing through Gradle.
* Free if there are under **1 million** requests per day.  
<img src= "https://github.com/dilyar85/VioletDroid/blob/master/screenshots/leancloud_icon.png" alt="LeanCloud Logo" height="200">


# How to Run
1. Start your emulator (highly recommend Genymotion)
2. Via bash, navigate to the VioletDroid project folder:  
`~ $ cd the_path_to_violetdroid_in_your_laptop/`
3. Execute with just one line of script in your terminal:  
`~ $ ./exec.sh`


## Installation Note
This project is based on the buildToolsVersion `"24.0.2"` and gradle version `"2.2.2"`, plase make sure you have upaded your Android Studio and Gradle to the newest version before running it on your own machine. 


## Demonstration
- Swipe tool bar to view all available digrams there.  
- Double tap to add one to the canvas below.   
- Drag digram on cavans can make it moving with your finger.   
- Single tap to show its indicator.   
- Click the resizing button and scale the diagram as you want.   
- Long press to remove the diagram on canvas.  
<img src="https://github.com/dilyar85/VioletDroid/blob/master/screenshots/gif-11:16.gif" alt="current_screenshots_image" width="300">

## Add Your Contribution
Before start your own implement on this project, please make sure you are in a new branch other than "master". Type these in terminal:
```
cd "path_to_this_project"
git pull origin master
git branch "new_feature_name"
git checkout "new_feature_name"
```
Now you can modify the codes and add new features required for our project. When you are done, push from the current branch:
```
git add .
git commit -m "Your commit message"
git push origin "current_branch_name"
```
After doing this, you can see a green button appeared on the top of project's GitHub page. 
<img src="https://github.com/dilyar85/VioletDroid/blob/master/screenshots/compare&pullrequest.png" alt="create_pull_request_image">
Click it to creat a new pull request, feel free to add some comments on what new features you have added to the project, and clcik the "Creat pull request" button at the bottom. I will merge it if there is nothing goes wrong. 


