<?php
require 'db_con.php';

$username = $_POST["email_id"];
$password = $_POST["password"];
$sync = $_POST["sync"];
$user = "pn337@snu.edu.in";
$psw = "password";
$query = "Select * from Login(online)
where `Email_ID` like '$username' and `Password` like '$password'";

$res = mysqli_query($connect,$query);
if($res->num_rows > 0){
	echo "login success";
	$upq = "update Login(online) set sync=1";
	$r = mysqli_query($connect,$upq);
}
else{
	echo "login unsuccessful";
	echo $username;
	echo $password;
}

?>