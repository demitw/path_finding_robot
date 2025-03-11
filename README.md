# Robot Path Planning Simulation

## Overview
This project is a Java-based simulation of a robot navigating through a grid with obstacles using a simple path planning algorithm. The program allows users to define a grid, place obstacles, set a starting position for the robot, and specify a goal position. It then finds the optimal path using the Breadth-First Search (BFS) algorithm and visualizes the result.

## Features
- Define a custom grid size
- Add obstacles to the grid
- Set an initial position for the robot
- Specify a goal position
- Find the optimal path using BFS
- Display the grid and path visually in the console

## Installation & Usage
### Prerequisites
- Java Development Kit (JDK) installed (JDK 8 or higher)

### Steps to Run
1. Clone this repository:
   ```sh
   git clone https://github.com/yourusername/repository-name.git
   ```
2. Navigate to the project directory:
   ```sh
   cd repository-name
   ```
3. Compile the Java files:
   ```sh
   javac Main.java
   ```
4. Run the program:
   ```sh
   java Main
   ```
5. Follow the on-screen prompts to configure the grid, obstacles, robot position, and goal position.

## Example Output
```
Enter the number of rows in the grid:
5
Enter the number of columns in the grid:
5
Enter the number of obstacles:
2
Enter the row and column of obstacle 1:
1 2
Enter the row and column of obstacle 2:
2 3
Enter the initial position of the robot (row column):
0 0
Enter the goal position of the robot (row column):
4 4
Grid:
R . . . .
. . X . .
. . . X .
. . . . .
. . . . G
Optimal Path: [0,0] [1,0] [2,0] ... [4,4]
```

## Project Structure
```
- Grid.java            # Represents the grid and obstacles
- RobotState.java      # Maintains robot's position and orientation
- PathPlanningAlgorithm.java # Implements BFS pathfinding algorithm
- Main.java            # Handles user input and runs the simulation
```

## Potential Improvements
- Implement more advanced pathfinding algorithms (e.g., A* or Dijkstra's)
- Add graphical visualization instead of console output
- Enable real-time robot movement simulation

## Author
Demini Waidyanatha.

