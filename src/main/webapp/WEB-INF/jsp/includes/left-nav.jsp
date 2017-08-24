<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Main sidebar -->
			<div class="sidebar sidebar-main">
				<div class="sidebar-content">

					<!-- User menu -->
					<div class="sidebar-user">
						<div class="category-content">
							<a href="./dashboard"><img src="<c:url value='/image/logo.png' />" alt="Auth Bridge"></a>
							
						</div>
					</div>
					<!-- /user menu -->


					<!-- Main navigation -->
					<div class="sidebar-category sidebar-category-visible">
						<div class="category-content no-padding">
							<ul class="navigation navigation-main navigation-accordion">

								<!-- Main -->
								<c:if test="${pageContext.request.isUserInRole('ROLE_ADMIN')}">
								<li id="dashboard-page" class="active"><a href="./dashboard"><i class="icon-home4"></i> <span>Dashboard</span></a></li>
								</c:if>
								<li id="search-page"><a href="./search"><i class="icon-search4"></i> <span>Search</span></a></li>
								<c:if test="${pageContext.request.isUserInRole('ROLE_ADMIN')}">
								<li id="abbr-page">
									<a href="./abbr"><i class="icon-copy"></i> <span>Abbreviations</span></a>
									
								</li>
								<li id="alias-page">
									<a href="./alias"><i class="icon-droplet2"></i> <span>Aliases</span></a>
									
								</li>
								<li id="stopword-page">
									<a href="./stopword"><i class="icon-stack"></i> <span>Stop Words</span></a>
								</li>
								<li id="weightage-page"><a href="./weightage"><i class="icon-list-unordered"></i> <span>Weightage Management </span></a></li>
								<li id="schedular-page"><a href="./schedular"><i class="icon-calendar5"></i> <span>Schedule Management </span></a></li>
								<!-- /main -->
								</c:if>

								
								<!-- /page kits -->

							</ul>
						</div>
					</div>
					<!-- /main navigation -->

				</div>
			</div>
			<!-- /main sidebar -->