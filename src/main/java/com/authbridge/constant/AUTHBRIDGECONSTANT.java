package com.authbridge.constant;

public class AUTHBRIDGECONSTANT {

	 public static final String SORT_ASC = "ASC";
	 public static final String SORT_DESC = "DESC";

	 public static final String N_W = "Name_Weigtage";
	 public static final String N_W_T = "Name_Weigtage_Threshold";
	 public static final String A_W = "Address_Weigtage";
	 public static final String A_W_T = "Address_Weigtage_Threshold";
	 public static final String F_W = "Father_Weigtage";
	 public static final String F_W_T = "Father_Weigtage_Threshold";
	 public static final String S_W = "State_Weigtage";
	 public static final String D_W = "District_Weigtage";

	 public static class OPERATION {
		 
		 public static final String ADD = "Add";
		 public static final String EDIT = "Edit";
		 public static final String DELETE = "Delete";
		 public static final String FULL_IMPORT = "Full_Import";
		 public static final String DELTA_IMPORT = "Delta_Import";
		 public static final String INDEXING = "Indexing";
		 
	 }
	 public static class SCHEDULINGSTATUS{
		 public static final String INPROGRESS="In_Progress";
		 public static final String IDLE="Idle";
		 
	 }
	 public static class SCREEN {
		 
		 public static final String ABBR = "Abbreviations";
		 public static final String ALIAS = "Alias";
		 public static final String STOPWORD = "Stopwords";
		 public static final String SCHEDULING = "Scheduling";
		 
	 }
	 
	 public static class REST {
	      
		 public static final Integer SUCCESS_CODE = 200;
		 public static final String SUCCESS = "success";
		 
		 public static final Integer ERROR_CODE = 500;
		 public static final String ERROR = "error";
		 
	 }
	 public static class SORT_FIELD{
		 public static final String NAME_MATCH = "nameMatch";
		 public static final String ADDRESS_MATCH = "addressMatch";
		 public static final String BOTH_MATCH = "bothMatch";
		 public static final String WEIGHT_MATCH = "weightMatch";
		 public static final String FATHER_MATCH = "fatherMatch";
	 }
	 
}
