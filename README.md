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
## How to run it
#### 1. Prerequisites:
- Install Build Gradle
- Install JavaFX (ensure it is configured in your IDE or added to your classpath).
- Set up an API key for the Groq API by adding it to your system environment variables under API_KEY in the following link: https://console.groq.com/keys

#### 2. Clone the Repository:

#### 3. Running the program
After cloning the repository, check a look through our class. Note that CrosswordFXMain is our main class to run the program. There are 2 ways to run our program:
1. Go to build.gradle and run the program 
2. In the terminal, make sure you are in the correct directory, type the following command: API_KEY=yourkey /gradlew run.

After running the program, you will be greeted with a main menu screen. From there, two main sections will be readily available. The first and primary section is titled “Epic Education.” Once clicking this button, you will be taken to a new scene that presents multiple options. Starting at the top left, there is an area where the crossword will be displayed. To the right is a vertical container with the heading “Word Bank” followed by a “Generate Puzzle” button. Below this heading and button, you can enter the words they wish to be included in the crossword. Once a word is written, press the “Enter” key on the keyboard to start a new line to write the next word. When all of the words are placed, the user can press the “Generate Puzzle” button and see their crossword appear on the left side. In the current implementation, Hints are also generated below, which replace the placeholders present in the beginning. If you want to create new hints, the “Regenerate Hints” button is available with ease. To return to the main menu, the user presses the “Go Back” button placed above the “Generate Hints” button. 