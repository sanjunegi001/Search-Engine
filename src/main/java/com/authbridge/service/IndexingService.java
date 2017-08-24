package com.authbridge.service;

public interface IndexingService {
	//to get the total count of records
	public long totalCount();
	
	//to perform full indexing of data
	public void fullIndexing();
	
	//delete this method once testing is done
	public void testing();

}
