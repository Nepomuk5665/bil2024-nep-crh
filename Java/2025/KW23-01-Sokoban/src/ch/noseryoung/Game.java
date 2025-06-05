package ch.noseryoung;
import java.awt.Point;

public class Game {

  private int[][] gameField;
  private int[][] initialField;

  public Game() {
    initialField = new int[][] { 
      { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
      { 0, 0, 0, 1, 1, 1, 1, 1, 0, 0 },
      { 0, 1, 1, 1, 0, 0, 0, 1, 0, 0 }, 
      { 0, 1, 4, 2, 3, 0, 0, 1, 0, 0 },
      { 0, 1, 1, 1, 0, 3, 4, 1, 0, 0 }, 
      { 0, 1, 4, 1, 1, 3, 0, 1, 0, 0 },
      { 0, 1, 0, 1, 0, 4, 0, 1, 1, 0 }, 
      { 0, 1, 3, 0, 4, 3, 3, 4, 1, 0 },
      { 0, 1, 0, 0, 0, 4, 0, 0, 1, 0 }, 
      { 0, 1, 1, 1, 1, 1, 1, 1, 1, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
    };
    gameField = new int[initialField.length][initialField[0].length];
    for (int i = 0; i < initialField.length; i++) {
      for (int j = 0; j < initialField[0].length; j++) {
        gameField[i][j] = initialField[i][j];
      }
    }
    gameField[7][4] = 5;
  }
  private Point findPlayer() {
    int indexLength = gameField.length;
    int indexWidth = gameField[0].length;
    int player = 2;

    for (int i = 0; i < indexLength; i++) {
      for (int j = 0; j < indexWidth; j++) {
        if (gameField[i][j] == player) {
          return new Point(i, j);
        }
      }
    }
    return new Point(400, 400);
  }

  public void moveUp() {
    System.out.println("UP");
    move(0);
  }
  // 0 = up, 1 = down, 2 = left, 3 = Down
  private void move(int choice){
    Point playerPosition = findPlayer();
    int x = playerPosition.x;
    int y = playerPosition.y;
    int targetX = x;
    int targetY = y;
    int beyondX = x;
    int beyondY = y;
    
    switch (choice) {
      case 0:
        targetX = x - 1;
        beyondX = x - 2;
        break;
      case 1:
        targetX = x + 1;
        beyondX = x + 2;
        break;
      case 2:
        targetY = y - 1;
        beyondY = y - 2;
        break;
      case 3:
        targetY = y + 1;
        beyondY = y + 2;
        break;
      default:
        return;
    }
    
    if (targetX < 0 || targetX >= gameField.length || targetY < 0 || targetY >= gameField[0].length) {
      return;
    }
    
    int currentPos = gameField[x][y];
    int targetPos = gameField[targetX][targetY];
    
    if (targetPos == 1) {
      return;
    }
    
    if (targetPos == 0 || targetPos == 4) {
      if (initialField[x][y] == 4) {
        gameField[x][y] = 4;
      } else {
        gameField[x][y] = 0;
      }
      gameField[targetX][targetY] = 2;
    } else if (targetPos == 3 || targetPos == 5) {
      if (beyondX < 0 || beyondX >= gameField.length || beyondY < 0 || beyondY >= gameField[0].length) {
        return;
      }
      int beyondPos = gameField[beyondX][beyondY];
      if (beyondPos == 1 || beyondPos == 3 || beyondPos == 5) {
        return;
      }
      if (targetPos == 5 && initialField[targetX][targetY] == 4) {
        gameField[targetX][targetY] = 2;
      } else {
        gameField[targetX][targetY] = 2;
      }
      if (initialField[x][y] == 4) {
        gameField[x][y] = 4;
      } else {
        gameField[x][y] = 0;
      }
      if (beyondPos == 0) {
        gameField[beyondX][beyondY] = 3;
      } else if (beyondPos == 4) {
        gameField[beyondX][beyondY] = 5;
      }
      checkWin();
    }
  }
  
  private void checkWin() {
    boolean hasUnplacedBox = false;
    for (int i = 0; i < gameField.length; i++) {
      for (int j = 0; j < gameField[0].length; j++) {
        if (gameField[i][j] == 3) {
          hasUnplacedBox = true;
          break;
        }
      }
    }
    if (!hasUnplacedBox) {
      System.out.println("YOU WIN!");
    }
  }

  public void moveDown() {
    System.out.println("DOWN");
    move(1);

  }

  public void moveLeft() {
    System.out.println("LEFT");
    move(2);
  }

  public void moveRight() {
    System.out.println("RIGHT");
    move(3);
  }

  public void resetGame() {
    System.out.println("ESC");
    for (int i = 0; i < initialField.length; i++) {
      for (int j = 0; j < initialField[0].length; j++) {
        gameField[i][j] = initialField[i][j];
      }
    }
    gameField[7][4] = 5;
  }

  public int[][] getField() {
    return gameField;
  }

  public int getColCount() {
    return gameField.length;
  }

  public int getRowCount() {
    return gameField[0].length;
  }
}
