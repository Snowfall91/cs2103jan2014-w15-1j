package view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import core.Task;

public class TaskCell extends ListCell<Task> {

	private static final String FONT_ROBOTO = "Roboto";
	
	private static final int GRID_HGAP = 7;
	private static final int GRID_VGAP = 4;
	private static final String GRID_ID = "grid";
	
	private static final String HBOX_ID = "hbox";
	
	private static final String ICON_CLASS = "icon";
	private static final String ICON_TIMED_ID = "timed";
	private static final String ICON_DEADLINE_ID = "deadline";
	private static final String ICON_FLOATING_ID = "floating";
	private static final int ICON_HEIGHT = 35;
	private static final int ICON_WIDTH = ICON_HEIGHT;
	
	private static final String DESC_ID = "taskDescription";
	private static final String HASHTAG_ID = "hashtag";
	
	private static final String START_ID = "start";
	private static final String END_ID = "end";
	
	private GridPane grid = new GridPane();
	private Label icon = new Label();
	private Label desc = new Label();
	private Label id = new Label();
	private Label startTimestamp = new Label();
	private Label endTimestamp = new Label();
	private Label timestamp = new Label();
	private Label hashtag = new Label();
	
	private HBox hbox = new HBox();
	
	public TaskCell() {
		configureGrid();
//		configureHBox();
		configureIcon();
		configureID();
		configureDesc();
		configureTimestamp();
		configureStartTimestamp();
		configureEndTimestamp();
		configureHashTag();
		addControlsToGrid();
		addControlsToHBox();
	}
	
	private void configureGrid() {
//		grid.setHgap(GRID_HGAP);
//		grid.setVgap(GRID_VGAP);
		grid.setId(GRID_ID); 
	}
	
	private void configureHBox() {
		hbox.setId(HBOX_ID);
	}
	
	private void configureID() {
		icon.setMaxHeight(Double.MAX_VALUE);
//		icon.setPrefSize(ICON_WIDTH, ICON_HEIGHT);
	}
	
	private void configureTimestamp() {
		
	}
	
	private void configureHashTag() {
		hashtag.setId(HASHTAG_ID);
	}
	
	private void configureIcon() {
		icon.setPrefSize(ICON_WIDTH, ICON_HEIGHT);
		icon.setAlignment(Pos.CENTER);
		icon.getStyleClass().add(ICON_CLASS);
	}
	
	private void configureDesc() {
		desc.setId(DESC_ID);
	}
	
	private void configureStartTimestamp() {
		startTimestamp.setId(START_ID);
	}
	
	private void configureEndTimestamp() {
		endTimestamp.setId(END_ID);
	}
	
	private void addControlsToHBox() {
//		hbox.getChildren().addAll(icon, id, desc, timestamp, hashtag);
		hbox.getChildren().addAll(grid, hashtag);
		HBox.setHgrow(grid, Priority.ALWAYS);
	}
	
	private void addControlsToGrid() {
		grid.add(icon, 0, 0, 1, 3);
		grid.add(desc, 1, 0, 2, 1);
		grid.add(startTimestamp, 1, 1);
		grid.add(endTimestamp, 2, 1);
//		grid.gridLinesVisibleProperty().set(true);		// debugging purpose
	}

	@Override
	public void updateItem(Task task, boolean empty) {
		super.updateItem(task, empty);
		if(empty) {
			clearContent();
		} else {
			addContent(task);
		}
	}
	
	private void clearContent() {
		setText(null);
		setGraphic(null);
	}
	
//	private void addContent(Task task) {
//		setText(null);
//		
//		String label = task.getLabel();
//		if(label.equals("T")) {
//			icon.setId(ICON_TIMED_ID);
//		} else if (label.equals("D")) {
//			icon.setId(ICON_DEADLINE_ID);
//		} else {
//			icon.setId(ICON_FLOATING_ID);
//		}
//		
//		icon.setText(label);
//		desc.setText(task.getTaskDescription());
//		
//		DateTimeFormatter fmt = DateTimeFormat.forPattern("MMM d, yy HH:mm");
//		
//		setGraphic(hbox);
//		
//	}
	
	private void addContent(Task task) {
		setText(null);

		String label = task.getLabel();
		if(label.equals("T")) {
			icon.setId(ICON_TIMED_ID);
		}else if(label.equals("D")) {
			icon.setId(ICON_DEADLINE_ID);
		} else {
			icon.setId(ICON_FLOATING_ID);
		}
		icon.setText(label);
//		icon.setText(" ");
		desc.setText(task.getTaskDescription());
		desc.setMaxWidth(650);
		hashtag.setText(task.getTaskTags().toString());
		
		DateTimeFormatter fmt = DateTimeFormat.forPattern("MMM d, yy HH:mm");
		DateTime start = task.getTaskStartTime();
		if(start == null) {
			startTimestamp.setText("");
		} else {
			startTimestamp.setText(fmt.print(start));
		}
		
		DateTime end = task.getTaskEndTime();
		if(end == null) {
			endTimestamp.setText("");
		} else {
			// TODO: rewrite it property with paddings
			if(start != null) {
				endTimestamp.setText("        " + fmt.print(end));
			} else {
                endTimestamp.setText(fmt.print(end));
			}
		}

		setGraphic(hbox);
	}
	
}
