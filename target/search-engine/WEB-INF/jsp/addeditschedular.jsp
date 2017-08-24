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
							<h4><i class="icon-arrow-left52 position-left"></i> <span class="text-semibold">Home</span> - Schedule Modification Management</h4>
						</div>
				</div>
				<div class="breadcrumb-line">
						<ul class="breadcrumb">
							<li><i class="icon-home2 position-left"></i> What is a scheduler component?</li>
							<li class="active">The scheduler component performs the delta/full import at a configured time.
							In the page you can specify the date and time with type of recurring to run import
							</li>
						</ul>
					</div>
				</div>
				<!-- /page header -->

				<div id="error"></div>
				<!-- Content area -->
				<div class="content">
					<div id="error"></div>
					
					
					<div class="row">
						<div class="col-lg-8">
							<form:form class="form-horizontal" method="POST" name="saveScheduler" action="./saveScheduler" modelAttribute="schedulerDTO">
								<fieldset class="content-group">
									<legend class="text-bold">Configure Scheduler's Date time and Recurring</legend>
									<form:input type="hidden" path="id" name="id"/>
									<div class="form-group">
										<label class="control-label col-lg-4">Date & Time</label>
										<div class="input-group">
										    <span class="input-group-addon"><i class="icon-alarm"></i></span>
											<form:input type="text" id="dateTime" path="dateTime" class="form-control"/>
										</div>
									</div>
									
									 <div class="form-group">
										<label class="control-label col-lg-4">Recurring</label>
										<div class="input-group">
											<span class="input-group-addon"><i class="icon-grid"></i></span>
											<form:select path="recurring" class="form-control">
												<c:forEach items="${recurringList}" var="recurr">
													<form:option value="${recurr}">
														<c:out value="${recurr}"></c:out> 
												    </form:option>
												</c:forEach>
											</form:select>
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-lg-4">Type of Import</label>
										<div class="input-group">
											<span class="input-group-addon"><i class="icon-grid"></i></span>
											<form:input path="type" type="text" class="form-control" readonly="true"/>
										</div>
									</div> 
									<div class="col-md-2">
										<a href="./schedular"><button type="button" class="btn weightage-btn btn-primary">
										<i class="icon-arrow-left13 position-left"></i>
										Cancel </button>
										</a>
									</div>
									<div class="col-md-2">
										<button type="submit" class="btn weightage-btn btn-primary">Save 
											<i class="icon-arrow-right14 position-right"></i></button>
									</div>
								</fieldset>
							</form:form >
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
		$('#dateTime').datetimepicker({
			 	 minDate : new Date(),
			 	 format: 'DD-MM-YYYY HH:mm'
		});
	 
		$('#dateTime').datetimepicker('update')
});

</script>
<style>
.bootstrap-datetimepicker-widget{
display:block !important;}
</style>
</html>
