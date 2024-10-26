package ui.tools;


import model.Shape;
import ui.DrawingEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

/**
 * ShapeTool is an abstract class that provides the basic functionality for tools
 * that create and manipulate shapes within the drawing editor. It handles the creation
 * of buttons, response to user input, and interaction with the drawing editor.
 */
public abstract class ShapeTool extends Tool {
	protected Shape shape;

	/**
	 * Constructs a ShapeTool with the specified drawing editor and parent component.
	 *
	 * @param editor the drawing editor that manages the canvas and shapes
	 * @param parent the parent component to which this tool belongs
	 */
	public ShapeTool(DrawingEditor editor, JComponent parent) {
		super(editor, parent);
		shape = null;
	}

	/**
	 * Creates a new button for this tool and adds it to the parent component.
	 *
	 * @param parent the parent component where the button will be added
	 */
	@Override
	protected void createButton(JComponent parent) {
		button = new JButton(getLabel());
		button = customizeButton(button);
	}

	/**
	 * Associates the button with a new ClickHandler, which defines the action
	 * to be taken when the button is clicked.
	 */
	@Override
	protected void addListener() {
		button.addActionListener(new ShapeToolClickHandler());
	}

	/**
	 * Handles the mouse press event in the drawing area. It creates a new shape
	 * at the location of the mouse event, plays the associated sound, and adds
	 * the shape to the drawing editor.
	 *
	 * @param e the mouse event containing the coordinates where the shape is to be created
	 */
	@Override
	public void mousePressedInDrawingArea(MouseEvent e) {
		makeShape(e);
		shape.selectAndPlay();
		shape.setBounds(e.getPoint());
		editor.addToDrawing(shape);
	}

	/**
	 * Handles the mouse release event in the drawing area. It stops the sound associated
	 * with the shape and deselects it, setting the shape reference to null.
	 *
	 * @param e the mouse event that triggered the release
	 */
	@Override
	public void mouseReleasedInDrawingArea(MouseEvent e) {
		shape.unselectAndStopPlaying();
		shape = null;
	}

	/**
	 * Handles the mouse drag event in the drawing area. It updates the bounds of the shape
	 * to follow the current mouse position.
	 *
	 * @param e the mouse event containing the current coordinates of the drag
	 */
	@Override
	public void mouseDraggedInDrawingArea(MouseEvent e) {
		shape.setBounds(e.getPoint());
	}

	/**
	 * Returns the label for this tool, which will be displayed in the UI.
	 *
	 * @return a string label for the tool
	 */
	protected abstract String getLabel();

	/**
	 * Constructs and returns a new shape at the location of the mouse event.
	 *
	 * @param e the mouse event containing the coordinates where the shape is to be created
	 */
	protected abstract void makeShape(MouseEvent e);

	/**
	 * ShapeToolClickHandler is an inner class that handles the click event for this tool.
	 * When the tool's button is clicked, it sets this tool as the active tool in the editor.
	 */
	private class ShapeToolClickHandler implements ActionListener {

		/**
		 * Sets the active tool to the shape tool when the button is clicked.
		 *
		 * @param e the action event triggered by the button click
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			editor.setActiveTool(ShapeTool.this);
		}
	}
}