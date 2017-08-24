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

	<%@ include file="includes/includes.jsp" %>

	

</head>

<body>
	<%@ include file="includes/header.jsp" %>
	<!-- Page container -->
	<div class="page-container">

		<!-- Page content -->
		<div class="page-content">
			<%@ include file="includes/left-nav.jsp" %>
			


			<!-- Main content -->
			<div class="content-wrapper">

				<!-- Page header -->
				<div class="page-header">
					<div class="page-header-content">
						<div class="page-title">
							<h4><i class="icon-arrow-left52 position-left"></i> <span class="text-semibold">Home</span> - Abbreviations</h4>
						</div>
				</div>
				<div class="breadcrumb-line">
						<ul class="breadcrumb">
							<li><i class="icon-home2 position-left"></i>  What are abbreviation?</li>
							<li class="active">Abbreviation is the most widely used term for a shortened or contracted form of a word or phrase. Abbreviations of single words are typically formed using the first letter or letters of the word ( n. = noun; adj. = adjective), the first letter and the last letter ( hr. = hour; Sr. = Senior), or the most significant letters ( TNT = trinitrotoluene; Pvt. = Private). </li>
						</ul>
					</div>
				</div>
				<!-- /page header -->

				<div id="error"></div>
				<!-- Content area -->
				<div class="content">

					<!-- Main charts -->
					<div class="row">
					 <div class="search-area">
							<div class="has-feedback has-feedback-left col-md-4">
										<input required name="synonymKey" id="synonymKey"  type="search" class="form-control" placeholder="Add Abbreviation">
							</div>
							<div class="has-feedback has-feedback-left col-md-4">
										<input required name="synonymValues" id="synonymValues" type="search" class="form-control" placeholder="Comma seperated values">
							</div>
							<div class="col-md-2">
								<button onclick="saveAbbreviations()" class="btn btn-primary"  >Save 
									<i class="icon-arrow-right14 position-right"></i></button>
							</div>
					</div>
					<div id="abbr-container">
						
					</div>
					<div id="noResults">
					</div>	
					
				</div>
					<!-- /main charts -->
					<!-- Footer -->
					<%@ include file="includes/footer.jsp" %>
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
<script type="text/javascript">
$( document ).ready(function() {
  getActiveTab();
  getAbbreviations();
});

</script>
</html>
