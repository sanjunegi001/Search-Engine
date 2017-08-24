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
							<h4><i class="icon-arrow-left52 position-left"></i> <span class="text-semibold">Home</span> - Weightage Management</h4>
						</div>
				</div>
				<div class="breadcrumb-line">
						<ul class="breadcrumb">
							<li><i class="icon-home2 position-left"></i> How to use weight management?</li>
							<li class="active">In weightage matching the occurrence of each word is found out, and a weightage is given to its ratio of its length to the length of given query.  </li>
						</ul>
					</div>
				</div>
				<!-- /page header -->


				<!-- Content area -->
				<div class="content">

					<!-- Main charts -->
					<div class="row">
					<div id="resWeight"></div>
					 <div class="search-area">
					  <form  onsubmit="return changeWeight();">
						<div class="has-feedback has-feedback-left col-md-4">
							<label> Name Weightage</label>
							<input required name="nameWeight" id="nameWeight"  type="number" class="form-control" placeholder="Name Weightage">
						</div>
						<div class="has-feedback has-feedback-left col-md-4">
							<label>Address Weightage</label>
							<input required name="addWeight" id="addWeight" type="number" class="form-control" placeholder="Address Weightage">
						</div>
						<div class="has-feedback has-feedback-left col-md-4">
							<label>Father Weightage</label>
							<input required name="fatherWeight" id="fatherWeight" type="number" class="form-control" placeholder="Father Weightage">
						</div>
						<div class="has-feedback has-feedback-left col-md-4">
							<label>Father Weightage Threshold</label>
							<input required name="fatherWeightThreshold" id="fatherWeightThreshold" type="number" class="form-control" placeholder="Father Weightage">
						</div>
						<div class="has-feedback has-feedback-left col-md-4">
							<label>Weightage score for state</label>
							<input required name="stateWeight" id="stateWeight" type="number" class="form-control" placeholder="State Weightage">
						</div>
						<div class="has-feedback has-feedback-left col-md-4">
							<label>Weightage score for district</label>
							<input required name="districtWeight" id="districtWeight" type="number" class="form-control" placeholder="District Weightage">
						</div>
						<div class="has-feedback has-feedback-left col-md-4">
							<label>Minimum Name Match Threshold</label>
							<input required name="nameThreshold" id="nameThreshold" type="number" class="form-control" placeholder="Minimum Name Match Threshold">
						</div>
						<div class="has-feedback has-feedback-left col-md-4">
							<label>Minimum Address Match Threshold</label>
							<input required name="addrThreshold" id="addrThreshold" type="number" class="form-control" placeholder="Minimum Address Match Threshold">
						</div>
						<div class="col-md-2">
							<button type="submit" class="btn weightage-btn btn-primary" >Save 
								<i class="icon-arrow-right14 position-right"></i></button>
						</div>
					   </form>	
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
	getWeightAge();
});

</script>
</html>
