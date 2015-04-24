<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Survey</title>
</head>
<body>
<form id="Survey" action="SurveyServlet" method="POST">
<p>What are your favorite color(s)?</p>
<p>
<input type="checkbox" name="color" value="1"/>Red<br/>
<input type="checkbox" name="color" value="2"/>Black<br/>
<input type="checkbox" name="color" value="3"/>Blue<br/>
<input type="checkbox" name="color" value="4"/>Green<br/>
<input type="checkbox" name="color" value="5"/>White<br/>
<input type="checkbox" name="color" value="6"/>Gold<br/>
<input type="checkbox" name="color" value="7"/>Silver<br/>
</p>
<p>What type of electronic gadgets are you looking for?</p>
<p>
<input type="checkbox" name="category" value="1"/>Cell Phones<br/>
<input type="checkbox" name="category" value="2"/>Laptops<br/>
<input type="checkbox" name="category" value="4"/>Tablets<br/>
<input type="checkbox" name="category" value="5"/>Televisions<br/>
<input type="checkbox" name="category" value="6"/>Video Games<br/>
</p>
<p>What price range would you like it to be in?</p>
<p>
<select name="price">
<option value="" selected>- Select -</option> 
<option  value="1">000.00 to 100.00</option>
<option  value="2">100.00 to 200.00</option>
<option  value="3">200.00 to 300.00</option>
<option  value="4">300.00 to 400.00</option>
<option  value="5">400.00 to 500.00</option>
<option  value="6">500.00 to 600.00</option>
<option  value="7">600.00 to 700.00</option>
<option  value="8">700.00 to 800.00</option>
<option  value="9">800.00 to 900.00</option>
<option  value="10">900.00 to 1000.00</option>
</select>
</p>
<p>Purpose of use?</p>
<p>
<input type="checkbox" name="use" value="1"/>Student<br/>
<input type="checkbox" name="use" value="2"/>Commercial<br/>
<input type="checkbox" name="use" value="3"/>Personal<br/>
</p>
<p>What manufacturers do you prefer?</p>
<p>
<input type="checkbox" name="manufacturer" value="1"/>Microsoft<br/>
<input type="checkbox" name="manufacturer" value="2"/>Apple<br/>
<input type="checkbox" name="manufacturer" value="3"/>Dell<br/>
</p>
<button type="submit" name="submit" value="submit">Submit Survey</button>
</form>

</body>
</html>