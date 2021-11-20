<?php
	$con = mysqli_connect("localhost", "musclejava", "musclejava2021!", "musclejava");
/*
	$userID = $_GET['userID'];
	$sql = mysqli_query($con, "SELECT COUNT(*) FROM USER WHERE userEntrance = 'true' ");
	$response = array();

	while ($row = mysqul_fetch_assoc($sql))
	{
		array_push($response, array(
			'sum' => $row['COUNT(*)']))
	});

	echo json_encode($response);
*/

	$result = mysqli_query($con, "SELECT COUNT(*) AS sum FROM USER WHERE userEntrance = 'true'");
	$response = array();
	while($row = mysqli_fetch_array($result)) {
		array_push($response, array("sum"=>$row[0]));
	}

	echo json_encode(array("response"=>$response));
	mysqli_close($con);

/*
	//$sum = NULL;

	$statement = mysql_prepare($con, "SELECT COUNT(*) AS sum FROM USER WHERE userEntrance = 'true'"); 
	mysqli_stmt_execute($statement);
	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $sum);

	// $response["sum"] = $sum;
	$response = array();
    $response["success"] = true;
    $response["sum"]=$sum;
/*
    while(mysqli_stmt_fetch($statement)){
        $response["success"]=true;
        $response["sum"]=$sum;
    }
*/
	//echo json_encode($response);

?>