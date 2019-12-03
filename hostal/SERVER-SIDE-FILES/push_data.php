<?php
	require 'db_conn.php';
	require 'functions.php';


	if (isset($_POST['add_hostel_data']) && isset($_POST['hostel_data']))  //for adding hostel
	{
		$hostelObj = json_decode($_POST['hostel_data'], true);
		$hostelid = -1;

		$query = "INSERT INTO hostel_table (hostel_name, hostel_address, hostel_city, hostel_extras, hostel_rating, hostel_rooms, hostel_floors, hostel_owner_email, hostel_img)
								   VALUES ('".$hostelObj['hostelName']."', '".$hostelObj['hostelAddress']."', '".$hostelObj['hostelCity']."', '".$hostelObj['hostelExtras']."', '".$hostelObj['rating']."', '".$hostelObj['no_rooms']."', '".$hostelObj['no_floors']."', '".$hostelObj['owner_email']."', '".$hostelObj['hostel_img']."');";

		if(mysqli_query($conn, $query))
		{
			$sql = "SELECT hostel_id FROM hostel_table ORDER BY hostel_id DESC LIMIT 1;";
			$result = mysqli_query($conn, $sql);
			$hostelid = (mysqli_fetch_assoc($result))['hostel_id'];
		}

		echo $hostelid;
	}
	else if (isset($_POST['add_hostel_image']) && isset($_POST['image_res1']) && isset($_POST['image_res2']) && isset($_POST['set_default']))
	{
		$sql = "SELECT pic_id FROM hostel_pic";
		$rows = mysqli_query($conn, $sql);
		$pic_id = mysqli_num_rows($rows) / 2;

		$hostel_id = $_POST['add_hostel_image'];
		$res1 = $_POST['image_res1'];
		$res2 = $_POST['image_res2'];

		$sql1 = "INSERT INTO hostel_pic (pic_id, pic_res_level, hostel_id, hostel_pic) VALUES('$pic_id', '1', '$hostel_id', '$res1');";
		$result1 = mysqli_query($conn, $sql1);
		$sql2 = "INSERT INTO hostel_pic (pic_id, pic_res_level, hostel_id, hostel_pic) VALUES('$pic_id', '2', '$hostel_id', '$res2');";
		$result2 = mysqli_query($conn, $sql2);
		
		if ($_POST['set_default'] == "1")
		{
			$sql = "UPDATE hostel_table SET hostel_img = '$pic_id' WHERE hostel_id = '$hostel_id';";
			mysqli_query($conn, $sql);
		}

		echo json_encode(($result1 && $result2));
	}
	else if (isset($_POST['add_user_data']) && isset($_POST['user_data']))  //for adding user
	{
		$userObj = json_decode($_POST['user_data'], true);
		$password = password_hash($userObj['password'], PASSWORD_DEFAULT);

		$query = "INSERT INTO user_table (user_email, user_name, user_pwd, account_type, user_phone) VALUES ('".$userObj['email']."', '".$userObj['userName']."', '".$password."', '".$userObj['accountType']."', '".$userObj['phoneNumber']."');";

		if(mysqli_query($conn, $query))
			echo "True";
		else
			echo "False";
	}
	else if (isset($_POST['update_user_data']) && isset($_POST['user_data']))  //for updating user
	{
		$userObj = json_decode($_POST['user_data'], true);

		$password = password_hash($userObj['password'], PASSWORD_DEFAULT);
		$email = $userObj['email'];
		$name = $userObj['userName'];
		$phone = $userObj['phoneNumber'];

		$query = "UPDATE user_table SET user_name='$name', user_pwd='$password', user_phone='$phone' WHERE user_email='$email';";

		if(mysqli_query($conn, $query))
			echo "True";
		else
			echo "False";
	}
	else if (isset($_POST['add_hostel_review']) && isset($_POST['userEmail']) && isset($_POST['hostelID']) && isset($_POST['comment']) && isset($_POST['rating']))  //for adding hostel review
	{
		$user = $_POST['userEmail'];
		$hostel = $_POST['hostelID'];
		$rating = $_POST['rating'];
		$comment = $_POST['comment'];

		$query = "INSERT INTO hostel_user_review (user_email, hostel_id, review_text, review_rating) VALUES ('$user', '$hostel', '$comment', '$rating');";
		$result = mysqli_query($conn, $query);
		if($result)
		{
			calculateReview($conn, $hostel);
			echo "True";
		}
		else
			echo "False";
	}
	else if (isset($_POST['update_hostel_data']) && isset($_POST['hostel_data']))  //for adding hostel
	{
		$hostelObj = json_decode($_POST['hostel_data'], true);
		$hostelid = $_POST['update_hostel_data'];

		$name = $hostelObj['hostelName'];
		$extras = $hostelObj['hostelExtras'];
		$rooms = $hostelObj['no_rooms'];
		$floors = $hostelObj['no_floors'];

		$query = "UPDATE hostel_table SET hostel_name = '$name', hostel_extras = '$extras', hostel_rooms = '$rooms', hostel_floors = '$floors'
					WHERE hostel_id = '$hostelid';";

		if(mysqli_query($conn, $query))
			echo "True";
		else
			echo "False";
	}
	else if (isset($_POST['delete_hostel_data']))  //for adding hostel
	{
		$hostelid = $_POST['delete_hostel_data'];

		$query = "DELETE FROM hostel_pic WHERE hostel_id = '$hostelid';";
		mysqli_query($conn, $query);

		$query = "DELETE FROM hostel_user_review WHERE hostel_id = '$hostelid';";
		mysqli_query($conn, $query);

		$query = "DELETE FROM hostel_table WHERE hostel_id = '$hostelid';";

		if(mysqli_query($conn, $query))
			echo "True";
		else
			echo "False";
	}