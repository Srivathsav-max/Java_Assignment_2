package questionb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class FractalController {

    // constants
    private static final int MIN_LEVEL = 0;
    private static final int MAX_LEVEL = 15;

    // instance variables that refer to GUI components
    @FXML private Canvas canvas;
    @FXML private ColorPicker colorPicker;
    @FXML private Label levelLabel;

    private Color currentColor = Color.BLUE;
    private int level = MIN_LEVEL; // initial fractal level
    private GraphicsContext gc; // used to draw on Canvas

    // initialize the controller
    @FXML
    public void initialize() {
        levelLabel.setText("Level: " + level);
        colorPicker.setValue(currentColor); // start with purple
        gc = canvas.getGraphicsContext2D(); // get the GraphicsContext
        drawFractal();
    }

    // sets currentColor when the user chooses a new Color
    @FXML
    void colorSelected(ActionEvent event) {
        currentColor = colorPicker.getValue(); // get new Color
        drawFractal();
    }

    // decrease level and redraw the fractal
    @FXML
    void decreaseLevelButtonPressed(ActionEvent event) {
        if (level > MIN_LEVEL) {
            --level;
            levelLabel.setText("Level: " + level);
            drawFractal();
        }
    }

    // increase level and redraw the fractal
    @FXML
    void increaseLevelButtonPressed(ActionEvent event) {
        if (level < MAX_LEVEL) {
            ++level;
            levelLabel.setText("Level: " + level);
            drawFractal();
        }
    }

    // clear Canvas, set drawing color, and draw the fractal
    private void drawFractal() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setStroke(currentColor);
        drawFractal(level, 40, 40, 350, 350);
    }

    // draw fractal recursively
    public void drawFractal(int level, int xA, int yA, int xB, int yB) {
        // base case: draw a line connecting two given points
        if (level == 0) {
            Line line = new Line(xA, yA, xB, yB);
            gc.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
        } else { // recursion step: determine new points, draw the next level
            int xC = (xA + xB) / 2;
            int yC = (yA + yB) / 2;
            int xD = xA + (xC - xA) / 2 - (yC - yA) / 2;
            int yD = yA + (yC - yA) / 2 + (xC - xA) / 2;

            drawFractal(level - 1, xD, yD, xA, yA);
            drawFractal(level - 1, xD, yD, xC, yC);
            drawFractal(level - 1, xD, yD, xB, yB);
        }
    }
}
