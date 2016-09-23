<?php
require 'db_con.php';


if( isset($_POST["Timestamp"]) && !empty($_POST["Timestamp"]) && !is_null($_POST["Timestamp"]) ) {
	
		
		$from_id = $_POST["From_ID"];
		$to_id = $_POST["To_ID"];
		$amount = $_POST["Amount"];
		$time = $_POST["Timestamp"];
		$sync = $_POST["Sync"];
		
		
		$query = "insert into Transaction(online) values('$from_id','$to_id','$amount','$time','$sync',0)";
		$res = mysqli_query($connect,$query);
		if($res){
			echo $time;
			/*echo "success";*/
		}else{
			echo "unsuccessful";	
		}
}else{
echo "no timestamp";	
	
}



?>