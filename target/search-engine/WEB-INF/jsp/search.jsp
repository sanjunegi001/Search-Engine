<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Auth Bridge Search Engine</title>

<%@ include file="includes/includes.jsp"%>

</head>

<body>
	<%@ include file="includes/header.jsp"%>
	<!-- Page container -->
	<div class="page-container">

		<!-- Page content -->
		<div class="page-content">
			<%@ include file="includes/left-nav.jsp"%>
			<!-- Main content -->
			<div class="content-wrapper">

				<!-- Page header -->
				<div class="page-header">
					<div class="page-header-content">
						<div class="page-title">
							<h4>
								<i class="icon-arrow-left52 position-left"></i> <span
									class="text-semibold">Home</span> - Search
							</h4>
						</div>
					</div>
					<div class="breadcrumb-line">
						<ul class="breadcrumb">
							<li class="srch-how-to"><i class="icon-home2 position-left"></i> How do you
								search? <img alt="Hover to view" src="/search-engine/image/info.png"></li>
							<li class="active srch-how-content">The search engine matches name and
								addresses of probable candidates from internal data and provide
								probability scores to the results. To search the given database
								with the user-defined keywords currently limited to 'Name' and
								'Address'.</li>
						</ul>
					</div>
				</div>
				<c:if test="${not empty solrException}">
					<div id="error">
						<div
							class="alert alert-info alert-styled-left alert-arrow-left alert-component">
							<h6 class="class">Is your solr Connected!!</h6>
							<span>Seems your solr has some issues.Check the
								connectivity or restart the server again.</span>
						</div>
					</div>
				</c:if>
				<!-- /page header -->
				<div class="content">

					<!-- Main charts -->
					<div class="row">
						<form:form modelAttribute="searchCaseDetailDTO" id="search"
							name="search" action="./searchQuery" method="GET"
							class="clearfix">
							
							<!-- If this was a search results page, collapse form by default and show criteria as a brief -->
							<c:set var="hideCriteria" value="false" />
							<c:if test="${not empty searchCaseDetailDTO.name}" >
								<c:set var="hideCriteria" value="true" />
								<c:set var="hiddenClass" value="hidden" />
							</c:if>
							<c:if test="${hideCriteria == true}" >
								<span class="criteria-msg">
									Showing search results. <a href="javascript:;" id="showCriteria">Expand criteria</a>.
								</span>
							</c:if>
							
							<div id="searchArea" class="search-area <c:out value="${hiddenClass}" />">
								<div class="has-feedback has-feedback-left col-md-4">
									<label class="required"> Name</label>
									<form:input class="form-control" path="name" type="text"
										placeholder="Search Name" required="true" name="name"
										id="name" />
								</div>
								<div class="has-feedback has-feedback-left col-md-8">
									<label class="required"> Address</label>
									<form:input class="form-control" path="address" type="text"
										placeholder="Search Address" required="true" name="address"
										id="address" />
								</div>
								<div class="has-feedback has-feedback-left col-md-4">
									<label>Father's Name</label>
									<form:input class="form-control" path="fatherName" type="text"
										placeholder="Father's name" name="fatherName" id="fatherName" />
								</div>
								<!-- Autofill select and district list in the dropdown -->
								<div class="has-feedback has-feedback-left col-md-4">
									<label>Select State</label>
									<div class="form-group">
										<form:select path="stateId" id="stateId" name="stateId" class="selectpicker form-control state-select" >													
										<form:option value="">-- Select State --</form:option>
										</form:select>
									</div>
								</div>
								<div class="has-feedback has-feedback-left col-md-4">
									<label>Select District</label>
									<div class="form-group">
										<form:select path="districtId" id="districtId" name="districtId" class="selectpicker form-control district-select">
										<form:option value="">-- Select District --</form:option>
										</form:select>
									</div>
								</div>
								<!-- End  -->
								<div class="has-feedback has-feedback-left col-md-4">
									<label>Name Minimum Match Threshold</label>
									<form:input class="form-control" path="nameThreshold" name="nameThreshold" 
										id="nameThreshold" type="number" min = "0" max = "100" 
										placeholder="Name Minimum Match Threshold" />
								</div>
								<div class="has-feedback has-feedback-left col-md-4">
									<label>Address Minimum Match Threshold</label>
									<form:input class="form-control" path="addrThreshold" name="addrThreshold" 
										id="addrThreshold" type="number" min = "0" max = "100"
										placeholder="Address Minimum Match Threshold" />
								</div>
								<div class="search-button col-md-2">
									<input class="btn btn-lg btn-primary btn-block" type="submit"
										value="Search" />
								</div>
								<form:input path="start" type="hidden" name="start" id="start" />
								<form:input path="sort" type="hidden" name="sort" id="sort" />
								<form:input path="sortField" type="hidden" name="sortField"
									id="sortField" />
								<c:set var="sortparam" value="${searchCaseDetailDTO.sort}" />
								<c:set var="sortFiledparam"
									value="${searchCaseDetailDTO.sortField}" />

							</div>


						</form:form>
						<div id="noResults" class="hide">You have no results for
							this search!!!</div>
						<div id="searchresult" class="col-md-12">
							<div id="paginate">
								<ul id="pagination">

								</ul>
							</div>

							<div id="result-table" class="result-table hide context">
								<table class="table table-striped table-bordered dataTable">
									<thead>
										<tr>
											<th>Rank</th>
											<th>ID</th>
											<th>Name</th>
											<th>Father Name</th>
											<th class="address">Address</th>
											<th>Case Id</th>
											<th>Link</th>
											<c:choose>
												<c:when test="${sortFiledparam eq 'fatherMatch'}">
													<c:choose>
														<c:when test='${sortparam eq "ASC"}'>
															<th id="fatherMatch" class="sort sort_asc cursor-pointer">Father's
																Name (%)</th>
														</c:when>
														<c:otherwise>
															<th id="fatherMatch"
																class="sort sort_desc cursor-pointer">Father's Name
																(%)</th>
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise>
													<th id="fatherMatch" class="sort cursor-pointer">Father's
														Name (%)</th>
												</c:otherwise>
											</c:choose>
											<c:choose>
												<c:when test="${sortFiledparam eq 'nameMatch'}">
													<c:choose>
														<c:when test='${sortparam eq "ASC"}'>
															<th id="nameMatch" class="sort sort_asc cursor-pointer">Name
																Matching (%)</th>
														</c:when>
														<c:otherwise>
															<th id="nameMatch" class="sort sort_desc cursor-pointer">Name
																Matching (%)</th>
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise>
													<th id="nameMatch" class="sort cursor-pointer">Name
														Matching (%)</th>
												</c:otherwise>
											</c:choose>

											<c:choose>
												<c:when test="${sortFiledparam eq 'addressMatch'}">
													<c:choose>
														<c:when test='${sortparam eq "ASC"}'>
															<th id="addressMatch" class="sort sort_asc">Address
																Matching (%)</th>
														</c:when>
														<c:otherwise>
															<th id="addressMatch" class="sort sort_desc">Address
																Matching (%)</th>
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise>
													<th id="addressMatch" class="sort cursor-pointer">Address
														Matching (%)</th>
												</c:otherwise>
											</c:choose>

											<c:choose>
												<c:when test="${sortFiledparam eq 'bothMatch'}">
													<c:choose>
														<c:when test='${sortparam eq "ASC"}'>
															<th id="bothMatch" class="sort sort_asc cursor-pointer">Name
																and Address Matching (%)</th>
														</c:when>
														<c:otherwise>
															<th id="bothMatch" class="sort sort_desc cursor-pointer">Name
																and Address Matching (%)</th>
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise>
													<th id="bothMatch" class="sort cursor-pointer">Name
														and Address Matching (%)</th>
												</c:otherwise>
											</c:choose>
											<c:choose>
												<c:when test="${sortFiledparam eq 'weightMatch'}">
													<c:choose>
														<c:when test='${sortparam eq "ASC"}'>
															<th id="weightMatch" class="sort sort_asc cursor-pointer">Weighted
																Percentage</th>
														</c:when>
														<c:otherwise>
															<th id="weightMatch"
																class="sort sort_desc cursor-pointer">Weighted
																Percentage</th>
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise>
													<th id="weightMatch" class="sort cursor-pointer">Weighted
														Percentage</th>
												</c:otherwise>
											</c:choose>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>


							</div>
						</div>
						<!-- /main charts -->
						<!-- Footer -->
					</div>
					<%@ include file="includes/footer.jsp"%>
					<!-- /footer -->
				</div>
				<!-- /content area -->

			</div>
			<!-- /main content -->

		</div>
		<!-- /page content -->

	</div>
	<!-- /page container -->

</body>
<script src="<c:url value='js/authbridge.js' />" type="text/javascript"></script>

<script type="text/javascript">
getActiveTab();


function populateDistrictNames(stateId,stateDistrictList){
	//input:stateid
	$('.district-select').html('');
	for(var i=0;i<stateDistrictList.length;i++){
		
		if(stateDistrictList[i].id == stateId){
			for(var j=0;j<stateDistrictList[i].district.length;j++){
			$('.district-select').append('<option value='+stateDistrictList[i].district[j].id+'>'+stateDistrictList[i].district[j].districtName+'</option>');
			}
		}
	}
	//output:List of districtName appened to option

	}

	// End:Ranjitha
$( document ).ready(function() {
	var stateDistrictList = [];
	// Code for autopopulate district on state selection:Ranjitha
	$('.state-select').on('change',function(){
		 var stateId =  $(this).find('option:selected').val();
		 populateDistrictNames(stateId,stateDistrictList);
	});
	 $.ajax({
		url : "./rest/districtState",
		type : "GET",
		success : function(res) {
				stateDistrictList = res.data;
				console.info(res);
				// Appende values to the dropdown:Ranjitha
				
				for(var i =0;i<res.data.length;i++){
					$('.state-select').append('<option value="'+res.data[i].id+'">'+res.data[i].stateName+'</option>');
					/* for(var j =0;j<res.data[i].district.length;j++){
                    $('.district-select').append('<option>'+res.data[i].district.districtName+'</option>');
					} */
				}
				
			},
			error: function(response){
				console.info("error resp"+response);
			}
		
		}); 
    console.log( "ready!" );
    $("#name").autocomplete({
    	source : function(request, response) {
    	
    		$.ajax({
    		url : "./rest/autocomplete/"+$("#name").val(),
    		success : function(res) {
    				var queryString=$("#name").val();
    				var jsonData = res.data.ABSuggester;
    				response($.map(jsonData, function(value, key) {
    				return {
    					 label : value,
    				}
    				}));
    			},
    			error: function(response){
    				console.info("error resp"+response);
    			}
    	//	dataType : "application/json"
    		
    		});
    	},
    	minLength : 2
    });
});

var searchResults = JSON.parse(JSON.stringify(${searchResultDTO}));
var caseDetails = new Array(searchResults.totalResult);
caseDetails = searchResults.data;
if(caseDetails.length==0){
	$("#noResults").removeClass("hide");
}else{
	$("#result-table").removeClass("hide");
}

var totalPages = Math.ceil((searchResults.totalResult)/searchResults.perPage);

var showMore = false;
if(totalPages == 10 && parseInt($("#start").val())*searchResults.perPaginQuery < searchResults.numFound)showMore=true;

setPagination(totalPages ,showMore);
/* paintTable(caseDetails.data); */


$("#pagination li").click(function(){
	var page = $( this ).attr("page");
	
	if(page=="more"){
		getMoreResult();
		return;
	}
	
	if(page=="first"){
		getFirstResult();
		return;
	}
	
	if(page=="prev"){
		getPrevResult();
		return;
	}
	
	$("#pagination li").removeClass("current");
	$( this ).addClass("current")
	var start = (page-1)*100;
	var end = start+100;
	var results = new Array();
	for(var i=start;i<end;i++ ){
		if(caseDetails[i]== undefined)
			 break;
		results.push(caseDetails[i]);
	}
	$('table > tbody').empty();
	paintTable(results);
});

$( "#pagination li" ).each(function( index ) {
	 if(! isNaN($( this ).text()) ){
		 $( this ).click();
		 return false;
	 }
});


/**
 * Called on click on any column header. Supports only "descending" sort.
 * If the clicked header already is sorted (descending), it returns without doing anything.
 */
$(".sort").click(function(){
	var id = $( this ).attr("id");
	
	if(id !== $("#sortField").val()){
		$("#sortField").val(id);
		$("#sort").val("DESC");
		$("#start").val(0);
		$("#search").submit();
	}
});

/**
 * Trigger on click "Expand criteria" button on search results page. Opens up the criteria 
 * section.
 */
$("a#showCriteria").click(function() {
	$header = $(this);
    $content = $("div#searchArea");

    // toggle class using animation; modify link text based upon whether criteria 
    // section is visible or not
    $content.toggleClass("hidden");
    $header.text($content.hasClass("hidden")
			? "Expand criteria"
			: "Collapse criteria");
});

$('table td:nth-child(1)').addClass('ignore');
$('table td:nth-child(2)').addClass('ignore');
$('table td:nth-child(4)').addClass('ignore');
$('table td:nth-child(6)').addClass('ignore');
$('table td:nth-child(7)').addClass('ignore');
$('table td:nth-child(8)').addClass('ignore');
$('table td:nth-child(9)').addClass('ignore');
$('table td:nth-child(10)').addClass('ignore');
$('table td:nth-child(11)').addClass('ignore');
$('table td:nth-child(12)').addClass('ignore');
$('table td:nth-child(13)').addClass('ignore');

var options = {
		exclude: [".ignore"]
};
var context = document.querySelector(".context"); // requires an element with class "context" to exist
//var context = document.getElementById("pname");
var instance = new Mark(context);
var name = $("#name").val();
//instance.mark(name);
instance.mark(name,options);

var context = document.querySelector(".context"); // requires an element with class "context" to exist
//var context = document.getElementById("paddr");
var instance = new Mark(context);
var address = $("#address").val();
//instance.mark(address);
instance.mark(address,options);


</script>

</html>