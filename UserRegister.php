<?php
	$con = mysqli_connect("localhost", "musclejava", "musclejava2021!", "musclejava");
	mysqli_query($con, 'SET NAMES utf8');

	$userID = $_POST["userID"];
	$userPassword = $_POST["userPassword"];
	$userGender = $_POST["userGender"];
	$userEmail = $_POST["userEmail"];
	$userEntrance = $_POST["userEntrance"];

	$statement = mysqli_prepare($con, "INSERT INTO USER VALUES (?, ?, ?, ?, ?)");
	mysqli_stmt_bind_param($statement, "sssss", $userID, $userPassword, $userGender, $userEmail, $userEntrance);
	mysqli_stmt_execute($statement);

	$response = array();
	$response["success"] = true;

	echo json_encode($response);
?>