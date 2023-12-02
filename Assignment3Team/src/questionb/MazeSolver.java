package questionb;

public class MazeSolver {

	public boolean mazeTraversal(char[][] maze, int x, int y) {
		maze[x][y] = 'x';

		if (!isWall(maze, x + 1, y)) { 
			maze[x + 1][y] = 'x';

			if (x + 1 == 11) { 
				printMaze(maze);
				return true;
			}

			if (mazeTraversal(maze, x + 1, y))
				return true;
		}

		if (!isWall(maze, x, y + 1)) { 
			maze[x][y + 1] = 'x';

			if (y + 1 == 11) { 
				printMaze(maze);
				return true;
			}

			if (mazeTraversal(maze, x, y + 1))
				return true;
		}

		if (!isWall(maze, x - 1, y)) { 
			maze[x - 1][y] = 'x';

			if (x - 1 == 0) { 
				printMaze(maze);
				return true;
			}

			if (mazeTraversal(maze, x - 1, y))
				return true;
		}

		if (!isWall(maze, x, y - 1)) { 
			maze[x][y - 1] = 'x';

			if (y - 1 == 0) { 
				printMaze(maze);
				return true;
			}

			if (mazeTraversal(maze, x, y - 1))
				return true;
		}

		maze[x][y] = '0';
		return false;
	}

	private boolean isWall(char[][] maze, int x, int y) {
		try {
			if (maze[x][y] == '#' || maze[x][y] == 'x' || maze[x][y] == '0')
				return true;
		} catch (Exception e) {
			return true;
		}

		return false;
	}

	public void printMaze(char[][] maze) {
		System.out.printf("%n%n");
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze.length; j++)
				System.out.print(maze[i][j] == '0' ? " ." : " " + maze[i][j]);

			System.out.println();
		}
	}

	public static void main(String[] args) {
		MazeSolver demo = new MazeSolver();

		char[][] maze = { { '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#' },
						  { '#', '.', '.', '.', '#', '.', '.', '.', '.', '.', '.', '#' },
						  { '.', '.', '#', '.', '#', '.', '#', '#', '#', '#', '.', '#' },
						  { '#', '#', '#', '.', '#', '.', '.', '.', '.', '#', '.', '#' },
						  { '#', '.', '.', '.', '.', '#', '#', '#', '.', '#', '.', '.' },
						  { '#', '#', '#', '#', '.', '#', '.', '#', '.', '#', '.', '#' },
						  { '#', '.', '.', '#', '.', '#', '.', '#', '.', '#', '.', '#' },
						  { '#', '#', '.', '#', '.', '#', '.', '#', '.', '#', '.', '#' },
						  { '#', '.', '.', '.', '.', '.', '.', '.', '.', '#', '.', '#' },
						  { '#', '#', '#', '#', '#', '#', '.', '#', '#', '#', '.', '#' },
						  { '#', '.', '.', '.', '.', '.', '.', '#', '.', '.', '.', '#' },
						  { '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#' } };

		if (demo.mazeTraversal(maze, 2, 0))
			System.out.println("It have a exit!");
		else
			System.out.println("It have not a exit!");
	}
}