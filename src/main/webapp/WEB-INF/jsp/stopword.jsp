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
							<h4><i class="icon-arrow-left52 position-left"></i> <span class="text-semibold">Home</span> - Stop Words</h4>
						</div>
				</div>
				<div class="breadcrumb-line">
						<ul class="breadcrumb">
							<li><i class="icon-home2 position-left"></i> What are Stop words?</li>
							<li class="active">In computer search engines, a stop word is a commonly used word (such as "the") that a search engine has been programmed to ignore, both when indexing entries for searching and when retrieving them as the result of a search query.
									Stop words are deemed irrelevant for searching purposes because they occur frequently in the language for which the indexing engine has been tuned. In order to save both space and time, these words are dropped at indexing time and then ignored at search time.		
							</li>
						</ul>
					</div>
				</div>
				<!-- /page header -->

				<div id="error"></div>
				<!-- Content area -->
				<div class="content">
					<div id="error"></div>
					<!-- Main charts -->
					<div class="row">
					 <div class="search-area">
								<div class="col-lg-6">
								
											<div class="input-group">
												<textarea class="form-control" required name="stopword" id="stopword" placeholder="Add comma seperated stop word" ></textarea>
												<span class="input-group-btn">
													<button class="btn bg-teal stopword-submit" onclick="saveStopWord()">Save</button>
												</span>
											</div>
								</div>
						
					</div>
					<div id="noResults">
					</div>	
					<div id="stopwords-container">
						<span class="select2-selection select2-selection--multiple" role="combobox" aria-haspopup="true" aria-expanded="false" tabindex="-1">
								<ul class="select2-selection__rendered" id="stopword-container">
									
								</ul>
						</span>
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
  getStopWords();
});

</script>
</html>
