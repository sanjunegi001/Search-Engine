package com.authbridge.DTO;

public class SchedulingStatusInfoDTO {
private String rowsFetched;
private String docsProcessed;
private String docsSkipped;
private String timeTaken;
public String getRowsFetched() {
	return rowsFetched;
}
public void setRowsFetched(String rowsFetched) {
	this.rowsFetched = rowsFetched;
}
public String getDocsProcessed() {
	return docsProcessed;
}
public void setDocsProcessed(String docsProcessed) {
	this.docsProcessed = docsProcessed;
}
public String getDocsSkipped() {
	return docsSkipped;
}
public void setDocsSkipped(String docsSkipped) {
	this.docsSkipped = docsSkipped;
}
public String getTimeTaken() {
	return timeTaken;
}
public void setTimeTaken(String timeTaken) {
	this.timeTaken = timeTaken;
}
}
