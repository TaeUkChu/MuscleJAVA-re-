<?php
	$con = mysqli_connect("localhost", "musclejava", "musclejava2021!", "musclejava");

	$userID = $_POST["userID"];

	$statement = mysqli_prepare($con, "UPDATE USER SET userEntrance = 'true' WHERE userID = ?");
	mysqli_stmt_bind_param($statement, "s", $userID);
	mysqli_stmt_execute($statement);

	$response = array();
	$response["success"] = true;

	echo json_encode($response);
?>