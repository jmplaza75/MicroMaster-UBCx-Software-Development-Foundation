package ui.tools;

import model.Oval;
import ui.DrawingEditor;

import javax.swing.*;
import java.awt.event.MouseEvent;

/**
 * OvalTool is a tool in the drawing editor that allows the user to create and add oval shapes
 * to the current drawing. It extends the ShapeTool class, which provides basic shape creation functionality.
 */
public class OvalTool extends ShapeTool {

    /**
     * Constructs an OvalTool with the specified drawing editor and parent component.
     *
     * @param editor the drawing editor that manages the canvas and shapes
     * @param parent the parent component to which this tool belongs
     */
    public OvalTool(DrawingEditor editor, JComponent parent) {
        super(editor, parent);
    }

    /**
     * Returns the label for this tool, which will be displayed in the UI.
     *
     * @return a string label for the Oval tool
     */
    @Override
    protected String getLabel() {
        return "Oval";
    }

    /**
     * Creates a new Oval shape at the location of the mouse event and initializes it with the MIDI synthesizer.
     * This method is called when the user interacts with the canvas to draw a shape.
     *
     * @param e the mouse event containing the coordinates where the shape is to be created
     */
    @Override
    protected void makeShape(MouseEvent e) {
        shape = new Oval(e.getPoint(), editor.getMidiSynth());
    }
}
