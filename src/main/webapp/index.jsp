<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Navjeevan Hospital - Login</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<style>
body { 
	
 background: url('images/login-bg.jpg') no-repeat center center fixed; 
 -webkit-background-size: cover;
 -moz-background-size: cover;
 -o-background-size: cover;
 background-size: cover;
}

.panel-default {
 opacity: 0.9;
 margin-top:175px;
}
.form-group.last {
 margin-bottom:0px;
}

div img {
  margin-top: 50px;
  margin-left: 70px;
}
</style>
</head>
<body>
<div class="logo"><img src="images/logo.gif" /></div>
<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-8">
            <div class="panel panel-default">
                <div class="panel-heading"> <strong class="">Login</strong>

                </div>
                <div class="panel-body">
                    <form class="form-horizontal" role="form" action="loginservlet" method="post">
                        <div class="form-group">
                            <label for="username" class="col-sm-3 control-label">User</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" id="username" name="username" placeholder="Username" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="password" class="col-sm-3 control-label">Password</label>
                            <div class="col-sm-9">
                                <input type="password" class="form-control" id="password" name="password" placeholder="Password" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-3 col-sm-9">
                                <div class="checkbox">
                                    <label class="">
                                        <input type="checkbox" class="">Remember me</label>
                                </div>
                            </div>
                        </div>
                        <div class="form-group last">
                            <div class="col-sm-offset-3 col-sm-9">
                                <button type="submit" class="btn btn-success btn-sm">Sign in</button>
                                <button type="reset" class="btn btn-default btn-sm">Reset</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>