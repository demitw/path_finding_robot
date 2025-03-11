import java.util.Scanner;

class Grid {
    public final int rows;
    public final int cols;
    private boolean[][] grid;

    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        grid = new boolean[rows][cols];
    }

    public void addObstacle(int row, int col) {
        if (isValidPosition(row, col)) {
            grid[row][col] = true;
        } else {
            System.out.println("Invalid position!");
        }
    }

    public boolean isObstacle(int row, int col) {
        if (isValidPosition(row, col)) {
            return grid[row][col];
        } else {
            System.out.println("Invalid position!");
            return false;
        }
    }

    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }
}

class RobotState {
    private int row;
    private int col;
    private int orientation;

    public RobotState(int row, int col, int orientation) {
        this.row = row;
        this.col = col;
        this.orientation = orientation;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }
}

class PathPlanningAlgorithm {
    private Grid grid;
    private RobotState robot;

    public PathPlanningAlgorithm(Grid grid, RobotState robot) {
        this.grid = grid;
        this.robot = robot;
    }

    // Breadth-First Search (BFS) algorithm for pathfinding
    public int[][] findOptimalPath(int[] goalPosition) {
        int[][] queue = new int[grid.rows * grid.cols][2];
        int front = 0, rear = -1;
        queue[++rear] = new int[]{robot.getRow(), robot.getCol()};

        int[][] cameFrom = new int[grid.rows][grid.cols];
        boolean[][] visited = new boolean[grid.rows][grid.cols];
        visited[robot.getRow()][robot.getCol()] = true;

        while (front <= rear) {
            int[] current = queue[front++];

            if (current[0] == goalPosition[0] && current[1] == goalPosition[1]) {
                return reconstructPath(cameFrom, current);
            }

            for (int[] neighbor : getNeighbors(current)) {
                if (!visited[neighbor[0]][neighbor[1]] && !grid.isObstacle(neighbor[0], neighbor[1])) {
                    queue[++rear] = neighbor;
                    cameFrom[neighbor[0]][neighbor[1]] = current[0] * grid.cols + current[1];
                    visited[neighbor[0]][neighbor[1]] = true;
                }
            }
        }

        System.out.println("Optimal Path: No path found!");
        return new int[0][]; // Return an empty path if no path is found
    }

    // Obstacle detection and avoidance mechanisms can be integrated here
    // To be implemented based on specific requirements

    // Get neighboring cells of a given node
    private int[][] getNeighbors(int[] node) {
        int[][] neighbors = new int[4][2];
        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}}; // Up, right, down, left

        int index = 0;
        for (int[] dir : directions) {
            int newRow = node[0] + dir[0];
            int newCol = node[1] + dir[1];

            if (grid.isValidPosition(newRow, newCol)) {
                neighbors[index][0] = newRow;
                neighbors[index][1] = newCol;
                index++;
            }
        }

        int[][] actualNeighbors = new int[index][2];
        for (int i = 0; i < index; i++) {
            actualNeighbors[i][0] = neighbors[i][0];
            actualNeighbors[i][1] = neighbors[i][1];
        }
        return actualNeighbors;
    }

    // Reconstruct path from BFS algorithm result
    private int[][] reconstructPath(int[][] cameFrom, int[] current) {
        int row = current[0];
        int col = current[1];
        int length = 1;
        while (cameFrom[row][col] != 0) {
            length++;
            int index = cameFrom[row][col];
            row = index / grid.cols;
            col = index % grid.cols;
        }

        int[][] path = new int[length][2];
        row = current[0];
        col = current[1];
        for (int i = length - 1; i >= 0; i--) {
            path[i][0] = row;
            path[i][1] = col;
            int index = cameFrom[row][col];
            row = index / grid.cols;
            col = index % grid.cols;
        }

        return path;
    }
}


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of rows in the grid:");
        int rows = scanner.nextInt();

        System.out.println("Enter the number of columns in the grid:");
        int cols = scanner.nextInt();

        Grid grid = new Grid(rows, cols);

        System.out.println("Enter the number of obstacles:");
        int numObstacles = scanner.nextInt();
        for (int i = 0; i < numObstacles; i++) {
            System.out.println("Enter the row and column of obstacle " + (i + 1) + ":");
            int obstacleRow = scanner.nextInt();
            int obstacleCol = scanner.nextInt();
            while (!grid.isValidPosition(obstacleRow, obstacleCol) || grid.isObstacle(obstacleRow, obstacleCol)) {
                System.out.println("Invalid position or position already occupied! Enter again:");
                obstacleRow = scanner.nextInt();
                obstacleCol = scanner.nextInt();
            }
            grid.addObstacle(obstacleRow, obstacleCol);
        }

        System.out.println("Enter the initial position of the robot (row column):");
        int robotRow = scanner.nextInt();
        int robotCol = scanner.nextInt();
        while (!grid.isValidPosition(robotRow, robotCol) || grid.isObstacle(robotRow, robotCol)) {
            System.out.println("Invalid position or position already occupied! Enter again:");
            robotRow = scanner.nextInt();
            robotCol = scanner.nextInt();
        }
        RobotState robot = new RobotState(robotRow, robotCol, 0);

        System.out.println("Enter the goal position of the robot (row column):");
        int goalRow = scanner.nextInt();
        int goalCol = scanner.nextInt();
        while (!grid.isValidPosition(goalRow, goalCol) || grid.isObstacle(goalRow, goalCol) || (goalRow == robotRow && goalCol ==             robotCol)) {
            System.out.println("Invalid position! Goal position cannot be an obstacle position or the same as the robot's position. Enter again:");
            goalRow = scanner.nextInt();
            goalCol = scanner.nextInt();
        }
        int[] goalPosition = {goalRow, goalCol};

        PathPlanningAlgorithm planner = new PathPlanningAlgorithm(grid, robot);

        int[][] optimalPath = planner.findOptimalPath(goalPosition);

        visualize(grid, robot, goalPosition, optimalPath);
    }

    // Visualization method
    private static void visualize(Grid grid, RobotState robot, int[] goalPosition, int[][] path) {
        System.out.println("Grid:");
        for (int row = 0; row < grid.rows; row++) {
            for (int col = 0; col < grid.cols; col++) {
                if (row == robot.getRow() && col == robot.getCol()) {
                    System.out.print("R ");
                } else if (row == goalPosition[0] && col == goalPosition[1]) {
                    System.out.print("G ");
                } else if (grid.isObstacle(row, col)) {
                    System.out.print("X ");
                } else if (pathContainsPosition(path, row, col)) {
                    System.out.print("P ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }

        System.out.print("Optimal Path: ");
        if (path.length == 0) {
            System.out.println("No path found!");
        } else {
            for (int i = 0; i < path.length; i++) {
                System.out.print("[" + path[i][0] + ", " + path[i][1] + "] ");
            }
            System.out.println();
        }
    }

    // Check if a given position is in the path
    private static boolean pathContainsPosition(int[][] path, int row, int col) {
        for (int i = 0; i < path.length; i++) {
            if (path[i][0] == row && path[i][1] == col) {
                return true;
            }
        }
        return false;
    }
}

