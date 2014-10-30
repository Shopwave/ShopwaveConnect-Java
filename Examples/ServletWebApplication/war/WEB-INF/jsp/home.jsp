<jsp:useBean id="ShopwaveOauthConnectorServlet" scope="request" class="java.lang.String" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PAGE TITLE</title>
        <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">
        
        <script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="navbar navbar-inverse navbar-fixed-top">
            <div class="container">
                <div class="navbar-header">
                    <a href="" class="navbar-brand">YOUR APP NAME</a>
                </div>
            </div>
        </div>
        <div class="container body-content">
            <div class="jumbotron" style="margin:0px auto; text-align:center; background:#ffffff;">
                <h1>YOUR APP LOGO</h1>
                <% if(request.getAttribute("isLoggedIn") != null && (Boolean) request.getAttribute("isLoggedIn") != true){ %>
                    <p class="lead">Click to login with Shopwave.</p>
                    <a href="<%=request.getAttribute("connectionUrl") %>">
                        <img src="images/LoginWithShopwaveSmall.png" alt="Shopwave Connect" width="134" />
                    </a>
                    <%} else { %>

                    <p class="lead">Logged in <a href="/?logout=true" class="btn btn-success btn-sm">Logout</a></p>

                    <ul style="list-style:none">
                        <li><strong>Name: </strong><%=request.getAttribute("firstName") %>&nbsp;<%=request.getAttribute("lastName") %></li>
                        <li><strong>Merchant Id: </strong><%=request.getAttribute("merchantId") %></li>
                    </ul>
                    <%} %>
            </div>
            <hr/>
            <footer>
                <p>&copy; <%= new java.util.Date() %> - YOUR COMPANY NAME</p>
            </footer>
        </div>
    </body>
</html>