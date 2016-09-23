<?php
require 'db_con.php';

$fName = $_POST["first_Name"];
$lName = $_POST["last_Name"];
$sync = $_POST["sync"];
$emailID = $_POST["email_id"];
$psw = $_POST["password"];
$phoneNumber = $_POST["phone_No"];

$query = "insert into User(online) values( '$emailID','$phoneNumber','$fName','$lName','$psw','$sync')";
$q = "Select * from User(online) where Email_id like '$emailID'";
$insq = "insert into Login(online) values('$emailID','$psw','$sync')";
$r = mysqli_query($connect,$q);
if($r->num_rows == 0){
	$res = mysqli_query($connect,$query);	
	$resins = mysqli_query($connect,$insq);
	echo "New user added";
	
}
else{
	echo "Already Exists";
}
?>