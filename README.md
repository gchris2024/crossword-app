# CSCI 205 - Software Engineering and Design
Bucknell University
Lewisburg, PA
### Course Info
Instructor: Prof. Romano

Semester: Fall 2024
## Team Information

Meiers: Senior, Neuroscience

Chris: Sophomore, Computer Science & Engineering

Nhi: Sophomore, Computer Science

Molly: Junior, Applied Mathematical Sciences
## Project Information: Crossword Program
The Crossword Generator Application is a Java-based system designed for both educators and crossword enthusiasts. It allows users to generate crosswords dynamically, either by inputting custom word lists in Educational Mode or generating random puzzles in Game Mode. The system uses advanced algorithms to create compact and interactive grids and integrates with external APIs to provide dynamic hints and word validation. This project demonstrates object-oriented design principles with a focus on modularity, scalability, and user experience.

#### Key Features:

- Educational Mode: Teachers can create customized crosswords by entering their own word lists and generating hints.
- Game Mode: Users can play random crosswords generated from a preloaded database (currently under development).
- Dynamic Hint Generation: External API integration for creating word-specific hints.
- Interactive Grid Display: Crosswords are visualized dynamically with hints for vertical and horizontal words.

Some new libraries (Gradle dependencies) that we used in this project to call API is OkHttp and Gson.
* OkHttp is a popular HTTP client library for Java and Kotlin, created by Square. It simplifies making HTTP requests and handling responses.
* Gson is a library developed by Google for serializing and deserializing Java objects to and from JSON. It allows you to convert JSON strings into Java objects (deserialization), convert Java objects into JSON strings (serialization) and handle custom mapping between JSON and Java objects.

## How to run it
#### 1. Prerequisites:
- Install Build Gradle
- Install JavaFX (ensure it is configured in your IDE or added to your classpath).
- Set up an API key for the Groq API by adding it to your system environment variables under API_KEY in the following link: https://console.groq.com/keys

#### 2. Clone the Repository:

#### 3. Running the program
After cloning the repository, go to build.gradle and run the dependencies first. Close the project and open it again. You should check a look through our classes to see how our codebase structure. Note that CrosswordFXMain is our main class to run the program. There are 2 ways to run our program:
1. Go to build.gradle and then run the whole program.
2. In the terminal, make sure you are in the correct directory, type the following command: API_KEY=yourkey /gradlew run.

After running the program, you will be greeted with a main menu screen. The first and primary section is titled “Epic Education.” Once clicked, you will be taken to a new scene that presents multiple windows. Starting at the top left, there is an area where the crossword will be displayed. To the right is a vertical container with the heading “Word Bank” followed by a “Generate Puzzle” button. Below this heading and button, you can enter the words you wish to be included in the crossword. Once a word is written, press the “Enter” key on the keyboard to start a new line to write the next word. When all desired the words are placed, press the “Generate Puzzle” button and see the crossword appear on the left side. In the current implementation, hints are automatically generated. If you want to create new hints, the “Regenerate Hints” button is available. To return to the main menu, press the “Go Back” button placed above the “Generate Hints” button. 

## Video Explanation
Link: https://mediaspace.bucknell.edu/media/CS205+FA24+Team+3+Final+Project+Video/1_1tm74wkq