# E4ObjectPassingDemo 
### A simple example of a passing an object between Parts via the IEventBroker in an app built with the Eclipse E4 RCP.

----------
#### Main Page
![Main Page](/SamplePhotos/MainPage.PNG?raw=true)

#### Passed Object
![Passed Object](/SamplePhotos/PassedObject.PNG?raw=true)


----------
### Using IEventBroker

#### Sending
- Inject IEventBroker in the sending class.
```java
@Inject
IEventBroker eventBroker;
```


- Use the IEventBroker object to 'post' an object with an associated 'topic', used for identification by the receiver.
```java
eventBroker.post( "SendingClass/SentObject" , ObjectType object );
```
--NOTE: The 'topic' format is specific to '*/*'. Anything you'd like can be substituted before and after the forward slash '/', but it is required to include SOMETHING. There are of course exceptions to this rule and I am not the expert. Learn more from the [Eclipse IEventBroker API](http://help.eclipse.org/neon/index.jsp?topic=%2Forg.eclipse.platform.doc.isv%2Freference%2Fapi%2Forg%2Feclipse%2Fe4%2Fcore%2Fservices%2Fevents%2FIEventBroker.html), an [article on the Eclipse4/RCP/Event Model](https://wiki.eclipse.org/Eclipse4/RCP/Event_Model), [this Vogella tutorial](http://www.vogella.com/tutorials/Eclipse4EventSystem/article.html) on the Eclipse 4 Event System, or of course from checking out this repo. 


#### Receiving
- Inject IEventBroker in the receiving class.
```java
@Inject
IEventBroker eventBroker;
```


- Build a method to listen for/receive the posted object.
```java
@Inject
@Optional
public void receiveEvent( @UIEventTopic ( "SendingClass/SentObject" ) ObjectType object )
{
	this.object = object;
	
	processObject(object);
	object.doAThing();	
}
```


----------
### Installation
**Using the IEventBroker requires installing additional Dependencies:**

 1. Navigate to your project hierarchy, open the 'META-INF' folder double click on MANIFEST.MF.
 
 2. Choose the 'Dependencies' tab at the bottom.
 
 3. Navigate to the 'Required Plug-ins' section and click 'Add'.
 
 4. Add the following:
 		
 		-org.eclipse.osgi.services
 		-org.eclipse.e4.core.services
 		-org.eclipse.e4.core.di.extensions
  
**This project requires the Eclipse E4 Tools to be installed:**

 1. Open the "Install New Software" Window.
	 *- Navigate to the far right of the top menu and click on "Help" followed by "Install Software" near the bottom of the drop down menu.*
	 
 2. Choose the download site for your version of Eclipse.
	 *- In the drop down menu labeled "Works With" at the top of the "Install Software" window, choose the site for your Eclipse Version.*
	 
 3. Search for "E4" and install "Eclipse e4 Tools Developer Resources".
	 *- Look just below the drop down menu to find the search bar and type in "E4". Click the check box to the left of the package labeled "Eclipse e4 Tools Developer Resources" and click the "Next" button at the bottom of the window.*
	 
 4. Follow the Wizard prompts to finish installing the software.
 

**Once the Tools have been installed, you can add this project like you would any other Java Project:**

 1. Download the .zip file of this project [here](https://github.com/William-Lake/E4WizardDemo/archive/master.zip).
 2. Import the project.
	 *- Navigate to Eclipse, and right click in your [Project Explorer](http://help.eclipse.org/luna/topic/org.eclipse.platform.doc.user/images/Image275_project_explorer.png), choosing "Import" about halfway down the pop-up menu.* 
	 *- Type "archive" into the search bar to easily locate the option titled "Archive File". Select it and click the "Next" button at the bottom of the window.*
	 *- Use the "Browse" button at the top right of the next page to locate your .zip file. Select it and click the "Finish" button at the bottom of the window.*


----------
### Running the Program

 1. Open E4ObjectPassingDemo.product
 2. On the "Overview" tab, in the "Testing" section, under item "2. Test the product by launching a runtime instance of it:", click the first link - "Launch an Eclipse Application".


----------
### Motivation
I'm relatively new to Computer Programming and as part of my work have been learning more about building software with the Eclipse E4 Rich Client Platform. There are a number of things I've learned and one of them is how to pass objects within an app. Using the IEventBroker is just one of many ways, but it is the most recent one I've figured out and the simplest in my opinion. This example program exists to provide others a jumping off point for applying this functionality into their own apps.


----------

If you have any questions or need any help please don't hesitate to get a hold of me here, on [LinkedIn](www.linkedin.com/in/william-lake-543535a5), or [Twitter](https://twitter.com/SynapseDynamo) and I will do the best I can to assist.

-Will
