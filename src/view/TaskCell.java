package view;

import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Separator;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import logic.CommandFactory;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import core.Task;

public class TaskCell extends ListCell<Task> {

	private static final int OFFSET = 1;

	// grid
	private static final int VGAP = 5;
	private static final int INDEX_WIDTH = 40;
	private static final int INDEX_HEIGHT = 60;

	// css id for UI elements
	private static final String HBOX_ID = "hbox";
	private static final String GRID_ID = "grid";
	private static final String DESC_ID = "description";
	private static final String HASHTAG_ID = "hashtag";
	private static final String INDEX_ID = "index";
	private static final String START_ID = "start";
	private static final String END_ID = "end";
	private static final String TIME_CLASS = "timestamp";
	private static final String OVERDUE_ID = "overdue-task";
	private static final String FLOATING_ID = "floating";
	private static final String DEADLINE_ID = "deadline";
	private static final String TIMED_ID = "timed";
	private static final String DONE_ID = "done";
	private static final String SEPARATOR_ID = "separator";

	private GridPane grid = new GridPane();
	private HBox hbox = new HBox();
	private Label index = new Label();
	private Label desc = new Label();
	private Label start = new Label();
	private Label end = new Label();
	private Label hashtag = new Label();
	private Separator separator = new Separator(Orientation.VERTICAL);
	private ObservableList<Task> tasks = CommandFactory.INSTANCE.getDisplayTasks();

	public TaskCell() {
		configureGrid();
		configureHBox();
		configureIndex(); // id label
		configureDesc(); // description label
		configureTimestamp();
		configureSeparator();
		configureHashtag();
		addControls();
	}
	

	private void configureGrid() {
		grid.setHgap(VGAP);
		grid.setVgap(VGAP);
		grid.setId(GRID_ID);
		grid.getColumnConstraints().addAll(new ColumnConstraints(),
				new ColumnConstraints(120));
	}

	private void configureHBox() {
		hbox.setId(HBOX_ID);
	}

	private void configureIndex() {
		index.setPrefSize(INDEX_WIDTH, INDEX_HEIGHT);
		index.setId(INDEX_ID);
		index.setAlignment(Pos.CENTER);
	}

	private void configureDesc() {
		desc.setId(DESC_ID);
	}

	private void configureTimestamp() {
		start.getStyleClass().add(TIME_CLASS);
		start.setId(START_ID);
		end.setId(END_ID);
		end.getStyleClass().add(TIME_CLASS);
	}

	private void configureHashtag() {
		hashtag.setId(HASHTAG_ID);
	}
	
	private void configureSeparator() {
		separator.setId(SEPARATOR_ID);
	}

	private void addControls() {
		grid.add(index, 0, 0, 1, 3);
		grid.add(desc, 1, 0, 3, 1);
		grid.add(start, 1, 1, 1, 1);
		grid.add(end, 3, 1, 1, 1);
//		grid.gridLinesVisibleProperty().set(true);
		hbox.getChildren().addAll(grid, separator, hashtag);
		HBox.setHgrow(grid, Priority.ALWAYS);
	}

	private void addContent(Task task) {
		setText(null);
  
		index.setText(String.valueOf(super.indexProperty().get() + OFFSET));
		desc.setText(task.getTaskDescription());
		desc.setMaxWidth(PandaUI.APP_WIDTH - 100);
		if(task.getTaskTags().isEmpty()) {
			separator.setVisible(false);
		}
		hashtag.setText(task.getTags());

		if (task.getTaskDone() == false) {
			if (task.getLabel().equals("D")) {
				hbox.setId(DEADLINE_ID);
			} else if (task.getLabel().equals("T")) {
				hbox.setId(TIMED_ID);
			} else {
				hbox.setId(FLOATING_ID);
			}
		} else {
			hbox.setId(DONE_ID);
		}

		// overdue status
		if (task.isOverdue()) {
			// hbox.getStyleClass().add(OVERDUE_CLASS);
			hbox.setId(OVERDUE_ID);
		}

		DateTimeFormatter fmt = DateTimeFormat.forPattern("d MMM yy  HH:mm a");
		DateTime startTimestamp = task.getTaskStartTime();
		if(startTimestamp == null) {
			start.setText("");
		} else {
			start.setText(fmt.print(startTimestamp));
		}

		DateTime endTimestamp = task.getTaskEndTime();
		if (endTimestamp == null) {
			end.setText("");
		} else {
			end.setText(fmt.print(endTimestamp));
		}

		setGraphic(hbox);
	}

	private void clearContent() {
		setText(null);
		setGraphic(null);
	}

	@Override
	public void updateItem(Task task, boolean empty) {
		super.updateItem(task, empty);
		if (empty) {
			clearContent();
		} else {
			addContent(task);
		}
	}
}
