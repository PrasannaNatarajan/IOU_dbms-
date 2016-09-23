<?php
class DB_Connect{

function __construct() {
	 
}
function __destruct() {
    // $this->close();
}

public function connect(){
	define("servername", "mysql.hostinger.in");
	define("username", "u633331925_iou");
	define("password", "RumaGy");
	define("database", "u633331925_debt");	
	$conn = new mysqli(servername, username, password);

	if ($conn->connect_error || !mysqli_select_db($conn,database)) {
		die("Connection failed: " . $conn->connect_error);
	} 
	echo "Connected successfully";
	return $con;
}
public function close() {
	mysql_close();
}
}
?>