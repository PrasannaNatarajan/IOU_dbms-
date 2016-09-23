<?php
require 'db_con.php';
$q = "select * from debt";
$res = mysqli_query($connect,$q);
if($res->num_rows > 0){
	
?>
<table>
<tr id="header"><td>UserName</td><td>Amount</td></tr>
<?php
	while($row=mysqli_fetch_array($res,MYSQLI_ASSOC)){
		echo json_encode($row);
		$temp = json_encode($row);
		$obj = (json_decode($temp));
		print $obj->{'NAME'};
?>
<tr>
<td><span><?php echo $row["NAME"] ?></span></td>
<td><span><?php echo $row["AMOUNT"] ?></span></td>
</tr>
<?php
}
?>
</table>
<?php

}
else{
	echo "none";
}

?>