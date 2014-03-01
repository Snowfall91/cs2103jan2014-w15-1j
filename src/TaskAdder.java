import java.util.StringTokenizer;

/*
 *TaskAdder class will perform its run method as a drive method
 *It will determine what kind of tasks to add and call on storage to store the data thereafter
 *Passes a task object to storage.java
 *V0.1 - Supports specific keywords and date only
 * 
 *Floating tasks:
 *add repair watch
 *add wash car
 *add go to gardens by the bay
 *add watch the day after tomorrow movie
 *
 *Timed tasks:
 *add meeting on 27-2-2014 from 2pm to 4pm
 *add hackathon from 27-2-2014 2pm to 28-2-2014 3pm
 *
 *Deadline tasks:
 *add submit minutes on 28-2-2014 by 8pm
 */

public class TaskAdder {
	
	private final String KEYWORD_BY = "by";
	private final String KEYWORD_TO = "to";
	private final String KEYWORD_FROM = "from";
	private final String KEYWORD_ON = "on";
	private final int NUM_INTEGER_IN_DATE = 3;
	private final int NUM_DAY_INDEX = 0;
	private final int NUM_MONTH_INDEX = 1;
	private final int NUM_YEAR_INDEX = 2;
	
	private String taskDesc = "";
	private Integer startTime = null;
	private Integer endTime = null;
	private Integer day = null;
	private Integer month = null;
	private Integer year = null;
	
	/*
	 *Public method that will be called by Parser.java
	 *This is just a driver method that will pass the input String into a parser method, determineTask
	 */
	public void run(String inputString) {
		parseTask(inputString);
	}
	
	/*
	 *This method will determine what kind of tasks that the user wants to add
	 *Floating tasks: tasks that only contains task description
	 *Time tasks: tasks that has a start and end time
	 *Deadline tasks: tasks that has an end time
	 */
	private void parseTask(String inputString) {
		StringTokenizer st = new StringTokenizer(inputString);	
		while(st.hasMoreTokens()) {
			String token = st.nextToken();
			if(isKeyWord(token)) {
				st = parseParameter(token, st);
			} else {
				appendDescription(token);
			}
		}		
		debug();
		addTask();
	}
	
	/*
	 *This method will check if token passed by determineTask method
	 *contains keyword: "on", "by", "from" and "to"
	 *Post condition: Returns true if token is a key word, false otherwise
	 */
	private boolean isKeyWord(String token) {
		token = token.toLowerCase();
		if(token.equals(KEYWORD_ON) ||
			token.equals(KEYWORD_BY) ||
				token.equals(KEYWORD_FROM) ||
					token.equals(KEYWORD_TO))
			return true;
		else
			return false;
	}
	
	//Method will append give token argument into task description
	private void appendDescription(String tokenString) {
		taskDesc += tokenString + " ";
	}
	
	/*
	 *Given specific keyword, this method will check if parameter is suitable.
	 *If parameter is wrong, it is assumed to be part of task description
	 *eg. add put cup "ON" table, add go to garden "BY" the day
	 */
	private StringTokenizer parseParameter(String keyWord, StringTokenizer st) {
		if(keyWord.equals(KEYWORD_ON)) {
			st = parseDate(keyWord, st);
		}
		if(keyWord.equals(KEYWORD_BY)) {
			st = parseTime(keyWord, st);
		}
		return st;
	}
	
	/*
	 *Given keyword "on", method will try to parse parameter as a date format
	 *Post condition: appends to task description if parameter is not of date format
	 *Post condition: parses day, month and year if it is of date format. 
	 */
	private StringTokenizer parseDate(String keyWord, StringTokenizer st) {
		String parameter = st.nextToken();
		System.out.println("Parsing:" + keyWord + ", " + parameter);
		// parameter should be a date format
		String[] stringArray = parameter.split("-");
		if(stringArray.length != NUM_INTEGER_IN_DATE) {
			// does not match date format 
			appendDescription(keyWord);
			appendDescription(parameter);
		} else {
			// parsing date format as day, month and time
			try {
				day = Integer.parseInt(stringArray[NUM_DAY_INDEX]);
				month = Integer.parseInt(stringArray[NUM_MONTH_INDEX]);
				year = Integer.parseInt(stringArray[NUM_YEAR_INDEX]);
			} catch (Exception e) { // cannot be parsed as integers
				// 2 things i can do here, either 1. output invalid date format
				// or 2. treat it as task description e.g. asds-asa-das input
				appendDescription(parameter);
			}
		}
		return st;
	}
	
	private StringTokenizer parseTime(String keyWord, StringTokenizer st) {
		return st;
	}
	
	private void addTask() {
		//add(new Task());
	}
	
	private void debug() {
		// Debugging purposes
		System.out.println("Task Description: " + taskDesc);
		System.out.println("Start Time: " + startTime);
		System.out.println("End Time: " + endTime);
		System.out.println("Date deadline: " + day + "-" + month + "-" + year);
	}
}
