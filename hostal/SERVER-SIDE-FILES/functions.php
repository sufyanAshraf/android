<?php
	
	function calculateReview($conn, $hostelID)    // function to recalculate the rating of a hoste;
	{
		$sql = "SELECT review_rating FROM hostel_user_review WHERE hostel_id='$hostelID';";
		$result = mysqli_query($conn, $sql);
		$count = mysqli_num_rows($result);
		$final = 0;
		if ($count > 0)
		{
			for ($var = 0; $var < $count; $var++)
			{
				$assoc = mysqli_fetch_assoc($result);
				$final += $assoc['review_rating'];
			}

			$final /= $count;
		}

		$sql = "UPDATE hostel_table SET hostel_rating='$final' WHERE hostel_id='$hostelID';";
		mysqli_query($conn, $sql);
	}