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
							<h4><i class="icon-arrow-left52 position-left"></i> <span class="text-semibold">Home</span> - Schedule Import Management</h4>
						</div>
				</div>
				<div class="breadcrumb-line">
						<ul class="breadcrumb">
							<li><i class="icon-home2 position-left"></i> What is a scheduler component?</li>
							<li class="active">The scheduler component performs the delta/full import at a configured time.
							</li>
						</ul>
					</div>
				</div>
				<!-- /page header -->

				<div id="error"></div>
				<!-- Content area -->
				<div class="content">
					<div id="error"></div>
					<c:if test="${code eq '500'}">
						<div class="alert-danger">Some Problem in changing configuration.</div>
					</c:if>
					<c:if test="${code eq '200'}">
						<div class="alert-normal">Successfully changed.</div>
					</c:if>
					
					<div class="row">
						<div class="col-lg-8">
						<c:if test="${ empty fullImport}">
							<a href="./addeditschedular?importType=${importType[0]}"><button type="button" class="btn bg-teal-400 btn-labeled"><b><i class="icon-reading"></i></b>Add Full Import</button></a>
						</c:if>
						<c:if test="${ empty deltaImport}">
							<a href="./addeditschedular?importType=${importType[1]}"><button type="button" class="btn bg-teal-400 btn-labeled"><b><i class="icon-reading"></i></b>Add Delta Import</button></a>
						</c:if>
						<c:choose>
							<c:when test="${isImportRunning}">
								<button type="button" class="btn disabled bg-teal-400 btn-labeled"><b><i class="icon-reading"></i></b>Run Full Import</button>
								<button type="button" class="btn disabled bg-teal-400 btn-labeled"><b><i class="icon-reading"></i></b>Run Delta Import</button>
							</c:when>
							<c:otherwise>
								<button onclick="confirmSchduling('FullImport','executeFullImport')" type="button" class="btn bg-teal-400 btn-labeled"><b><i class="icon-reading"></i></b>Run Full Import</button>
								<button onclick="confirmSchduling('DeltaImport','executeDeltaImport')"  type="button" class="btn bg-teal-400 btn-labeled"><b><i class="icon-reading"></i></b>Run Delta Import</button>
							</c:otherwise>
						</c:choose>
						
						<c:if test="">
							
						</c:if>
						</div>
					
						<div class="col-lg-10">
							<c:if test="${not empty fullImport}">
								<div id="full-import-section" class="import-section">
								<fieldset class="content-group">
									<legend class="text-bold">Scheduler Configured for Full Import</legend>
									
									<table class="table text-nowrap">
										<thead>
											<tr>
												<th>SNO</th>
												<th>Date & Time</th>
												<th>Recurring</th>
												<th>Edit</th>
												<th>Delete</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td>1</td>
												<td>${fullImport.dateTime}</td>
												<td>${fullImport.recurring}</td>
												<td>
													<div class="">
													<a href="./addeditschedular?importType=${importType[0]}">
														<span class="label bg-pink dropdown-toggle" >EDIT</span>
													</a>
													</div>
												</td>
												<td>
													<div class="">
													<a href="./deleteSchedular?importType=${importType[0]}">
														<span class="label label-primary dropdown-toggle" >DELETE</span>
													</a>
													</div>
												</td>
											</tr>
										</tbody>
									</table>
								 </fieldset>	
								</div>
							</c:if>
							<c:if test="${not empty deltaImport}">
								<div id="delta-import-section" class="import-section">
								 <fieldset class="content-group">
									<legend class="text-bold">Scheduler Configured for Delta Import</legend>
									<table class="table text-nowrap">
										<thead>
											<tr>
												<th>SNO</th>
												<th>Date & Time</th>
												<th>Recurring</th>
												<th>Edit</th>
												<th>Delete</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td>1</td>
												<td>${deltaImport.dateTime}</td>
												<td>${deltaImport.recurring}</td>
												<td>
													<div>
													<a href="./addeditschedular?importType=${importType[1]}">
														<span class="label bg-pink cursor-pointer dropdown-toggle" >Edit</span>
													</a>
													</div>
												</td>
												<td>
													<div class="">
													<a href="./deleteSchedular?importType=${importType[1]}">
														<span class="label label-primary  dropdown-toggle" >DELETE</span>
													</a>
													</div>
												</td>
											</tr>
										</tbody>
									</table>
								 </fieldset>	
								</div>
							</c:if>	
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
});

</script>
</html>
