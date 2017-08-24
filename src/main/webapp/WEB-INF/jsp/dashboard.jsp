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
							<h4><i class="icon-arrow-left52 position-left"></i> <span class="text-semibold">Home</span> - Dashboard</h4>
						</div>
				</div>
				<div class="breadcrumb-line">
						<ul class="breadcrumb">
							<li><i class="icon-home2 position-left"></i> Home</li>
							<li class="active">Dashboard</li>
						</ul>
					</div>
				</div>
				<!-- /page header -->

				<div id="error"></div>
				<!-- Content area -->
				<div class="content">

					<!-- Main charts -->
					<div class="row">
						<div class="col-lg-8">

							<!-- Quick stats boxes -->
							<div class="row">
								
							
								<div id="server-status-1" class="col-lg-4 hidden">
									<div class="panel bg-blue-400 dashboard-panel">
										<div class="pannelHeader">
											<h3 class="no-margin" id="server-name-1">Node 1</h3>
											<span id="server-state-1" ></span>
										</div>
										<div id="server-load-1"></div>
										<div class="server-url">Pinging : <span class="blink  url-1"></span></div>
									</div>
								</div>
								
								
								<div id="server-status-2" class="col-lg-4 hidden">
									<div class="panel bg-pink-400 dashboard-panel">
										<div class="pannelHeader">
											<h3 class="no-margin" id="">Node 2</h3>
											<span id="server-state-2"></span>
										</div>
										<div id="server-load-2"></div>
										<div class="server-url">Pinging : <span class="blink url-2"></span></div>
									</div>
								</div>
								
								<div id="server-status-3" class="col-lg-4 hidden">
									<div class="panel bg-blue-400 dashboard-panel">
										<div class="pannelHeader">
											<h3 class="no-margin" id="">Node 3</h3>
											<span id="server-state-3"></span>
										</div>
										<div id="server-load-3"></div>
										<div class="server-url">Pinging : <span class="blink url-3"></span></div>
									</div>
								</div>
								
								<div id="server-status-4" class="col-lg-4 hidden">
									<div class="panel bg-pink-400 dashboard-panel">
										<div  class="pannelHeader" >
											<h3 class="no-margin" id="">Node 4</h3>
											<span id="server-state-4"></span>
										</div>
										<div id="server-load-4"></div>
										<div class="server-url">Pinging : <span class="blink url-4"></span></div>
									</div>
								</div> 
								
								<div id="server-status-5" class="col-lg-4 hidden">
									<div class="panel bg-blue-400 dashboard-panel">
										<div class="pannelHeader">
											<h3 class="no-margin" id="">Node 5</h3>
											<span id="server-state-5"></span>
										</div>
										<div id="server-load-5"></div>
										<div class="server-url">Pinging : <span class="blink url-5"></span></div>
									</div>
								</div>
								
								<div class="col-lg-4">
									<div class="panel bg-pink-400 dashboard-panel">
										<div class="panel-body cursor-pointer"  onclick="redirectTO('schedular')">
											<h3 class="no-margin" id=""></h3>
											<span id="full-import"></span>
										</div>
									</div>
								</div>
								
								<div class="col-lg-4">
									<div class="panel bg-pink-400 dashboard-panel">
										<div class="panel-body cursor-pointer"  onclick="redirectTO('schedular')">
											<h3 class="no-margin" id=""></h3>
											<span id="delta-import"></span>
										</div>
									</div>
								</div>
								
								<div class="col-lg-4">
									<div class="panel bg-teal-400 dashboard-panel">
										<div class="panel-body cursor-pointer"  onclick="redirectTO('abbr')" >
												Total Abbreviations Added : <h3 class="no-margin" id="abbr-count"></h3>
												<div id="abbr-modif" class="modifiedon"></div>
										</div>
									</div>
								</div>

								<div class="col-lg-4">
									<div class="panel bg-pink-400 dashboard-panel">
										<div class="panel-body cursor-pointer"  onclick="redirectTO('alias')">
											Total Aliases Added : <h3 class="no-margin" id="alias-count"></h3>
											<div id="alias-modif" class="modifiedon"></div>
										</div>
									</div>
								</div>

								<div class="col-lg-4">
									<div class="panel bg-blue-400 dashboard-panel">
										<div class="panel-body cursor-pointer"  onclick="redirectTO('stopword')">
											Total Stop Words Added : <h3 class="no-margin" id="stopword-count"></h3>
											
											<div id="stopwords-modif" class="modifiedon"></div>
										</div>
										
									</div>
								</div>
								
								<div class="col-lg-4">
									<div class="panel bg-teal-400 dashboard-panel">
										<div class="panel-body cursor-pointer"  onclick="redirectTO('weightage')">
											<h3 class="no-margin" id="nameWeight"></h3>
											Name Weightage
											</div>
										</div>
								</div>
								
								<div class="col-lg-4">
									<div class="panel bg-pink-400 dashboard-panel">
										<div class="panel-body cursor-pointer"  onclick="redirectTO('weightage')">
											<h3 class="no-margin" id="addWeight"></h3>
											Address Weightage
											</div>
										</div>
								</div>
							</div>
							

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
	abbrCount();
	aliasCount();
	stopWordCount();
	getWeightAge();
	getSchedualar();
	geModifications();
	getServerStaus();
	 function fn60sec() {
		  getServerStaus();
		 }
		 setInterval(fn60sec, 60*1000);
});
</script>

</html>

