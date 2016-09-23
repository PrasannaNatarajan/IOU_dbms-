<?php
require_once 'db_con.php';
$json = $_POST["usersJSON"];
if (get_magic_quotes_gpc()){
$json = stripslashes($json);
}
$data = json_decode($json);
$a=array();
$b=array();
for($i=0; $i<count($data) ; $i++)
{
	$result = mysqli_query($connect,"INSERT INTO debt VALUES(NAME,AMOUNT)('$Name',$Amount)");
	if($result){
		$b["NAME"] = $data[$i]->NAME;
		array_push($a,$b);
	}else{
		$b["NAME"] = $data[$i]->NAME;
		array_push($a,$b);
		echo "did not insert user";	
	}
	
}

echo json_encode($a);
?>