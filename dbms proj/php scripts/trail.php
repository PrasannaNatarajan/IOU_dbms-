<?php
require 'db_con.php';

$from_id = $_POST["From_ID"];
$q = "select * from Transaction(online) where (down_sync = 0) and (to_id like '$from_id')";
$query = "update Transaction(online) set down_sync = 1 where (down_sync = 0) and (to_id like '$from_id')";
$res = mysqli_query($connect,$q);

if($res->num_rows > 0){
	
	while($post = mysqli_fetch_array($res,MYSQLI_ASSOC)) {
         echo json_encode($post);
      }
	$r = mysqli_query($connect,$query);
	
}else{
	//echo $from_id;
	echo "num row <0";
}

?>