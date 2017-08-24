
	<!-- Main navbar -->
	<div class="navbar navbar-inverse">
		<div class="navbar-header">
			
			<ul class="nav navbar-nav visible-xs-block">
				<li><a data-toggle="collapse" data-target="#navbar-mobile"><i class="icon-tree5"></i></a></li>
				<li><a class="sidebar-mobile-main-toggle"><i class="icon-paragraph-justify3"></i></a></li>
			</ul>
		</div>

		<div class="navbar-collapse collapse" id="navbar-mobile">
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown dropdown-user">
					<a class="dropdown-toggle" data-toggle="dropdown">
						<span>Hello, <c:out value="${pageContext.request.remoteUser}"></c:out></span>
						<i class="caret"></i>
					</a>
					<ul class="dropdown-menu dropdown-menu-right">
						<form action="./logout" name="logout" method="post">
						 <input type="hidden"
								name="${_csrf.parameterName}" value="${_csrf.token}" />
						 <li class="logout" onclick="return document.forms.logout.submit();"><i class="icon-switch2"></i><a href="#"> Logout</a></li>
						</form>
						
					</ul>
				</li>
			</ul>
		</div>
	</div>
	<!-- /main navbar -->



