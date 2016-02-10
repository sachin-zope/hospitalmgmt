<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Upload data</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<script src="js/jquery.min.js"></script>
</head>
<body>
Select a file to upload: <br />
<form action="UploadServlet" method="post"
                        enctype="multipart/form-data">
<input type="file" name="file" />
<br />
<input type="submit" value="Upload File" />
</form>
</body>
</html>