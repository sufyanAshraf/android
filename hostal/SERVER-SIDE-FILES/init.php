<?php
	$servername = "localhost";
	$username = "root";
	$password = "";

	$connCreate = mysqli_connect($servername, $username, $password);
	$sql = "CREATE DATABASE IF NOT EXISTS hostel_info_db";
	mysqli_query($connCreate, $sql);
	mysqli_close($connCreate);

	require 'db_conn.php';

	// table to store user data

	$sql = 'CREATE TABLE IF NOT EXISTS `user_table` (
			  `user_email` varchar(30) NOT NULL,
			  `user_name` varchar(30) NOT NULL,
			  `user_phone` varchar(30) DEFAULT NULL,
			  `user_pwd` varchar(255) NOT NULL,
			  `account_type` boolean NOT NULL,
			  PRIMARY KEY (`user_email`)
			);';
	mysqli_query($conn, $sql);

	//table to store hostel data

	$sql = 'CREATE TABLE IF NOT EXISTS `hostel_table` (
			  `hostel_id` int(11) NOT NULL AUTO_INCREMENT,
			  `hostel_name` varchar(60) NOT NULL,
			  `hostel_city` varchar(30) NOT NULL,
			  `hostel_address` varchar(100) NOT NULL,
			  `hostel_rooms` int(3) NOT NULL,
			  `hostel_floors` int(2) NOT NULL,
			  `hostel_extras` varchar(255) NOT NULL,
			  `hostel_owner_email` varchar(30) NOT NULL,
			  `hostel_rating` float NOT NULL,
			  `hostel_img` int(11) DEFAULT NULL,
			  	PRIMARY KEY (`hostel_id`),
			  	FOREIGN KEY (`hostel_owner_email`) REFERENCES `user_table` (`user_email`)
			);';
	mysqli_query($conn, $sql);

	// table to store universities and their address

	$sql = 'CREATE TABLE IF NOT EXISTS `university_table` (
			  `university_id` int(11) NOT NULL AUTO_INCREMENT,
			  `university_name` varchar(60) NOT NULL,
			  `university_city` varchar(30) NOT NULL,
			  `university_address` varchar(100) NOT NULL,
			  	PRIMARY KEY (`university_id`)
			);';
	mysqli_query($conn, $sql);

	// table to store hostels nearest to universities

	$sql = 'CREATE TABLE IF NOT EXISTS `hostel_university` (
			  `hostel_id` int(11) NOT NULL,
			  `university_id` int(11) NOT NULL,
			  	PRIMARY KEY (`hostel_id`, `university_id`),
			  	FOREIGN KEY (`university_id`) REFERENCES `university_table` (`university_id`),
			  	FOREIGN KEY (`hostel_id`) REFERENCES `hostel_table` (`hostel_id`)
			);';
	mysqli_query($conn, $sql);

	// table to store profile pics of users

	$sql = 'CREATE TABLE IF NOT EXISTS `user_profile_pic` (
			  `user_email` varchar(30) NOT NULL,
			  `profile_pic` MEDIUMTEXT NOT NULL,
			  	PRIMARY KEY (`user_email`),
			  	FOREIGN KEY (`user_email`) REFERENCES `user_table` (`user_email`)
			);';
	mysqli_query($conn, $sql);

	// table to store the preferences of a user

	$sql = 'CREATE TABLE IF NOT EXISTS `user_hostel_pref` (
			  `user_email` varchar(30) NOT NULL,
			  `hostel_id` int(11) NOT NULL,
			  	PRIMARY KEY (`user_email`, `hostel_id`),
			  	FOREIGN KEY (`user_email`) REFERENCES `user_table` (`user_email`),
			  	FOREIGN KEY (`hostel_id`) REFERENCES `hostel_table` (`hostel_id`)
			);';
	mysqli_query($conn, $sql);

	// table to store the pictures of the hostels

	$sql = 'CREATE TABLE IF NOT EXISTS `hostel_pic` (
			  `pic_id` int(11) NOT NULL,
			  `pic_res_level` TINYINT NOT NULL,
			  `hostel_id` int(11) NOT NULL,
			  `hostel_pic` MEDIUMTEXT NOT NULL,
			    PRIMARY KEY (`pic_id`,`pic_res_level`,`hostel_id`),
			  	FOREIGN KEY (`hostel_id`) REFERENCES `hostel_table` (`hostel_id`)
			);';
	mysqli_query($conn, $sql);

	// table to store the user reviews of the hostels

	$sql = 'CREATE TABLE IF NOT EXISTS `hostel_user_review` (
			  `hostel_id` int(11) NOT NULL,
			  `user_email` varchar(30) NOT NULL,
			  `review_text` varchar(255) NOT NULL,
			  `review_rating` float NOT NULL,
			  	PRIMARY KEY (`user_email`, `hostel_id`),
			  	FOREIGN KEY (`hostel_id`) REFERENCES `hostel_table` (`hostel_id`),
			  	FOREIGN KEY (`user_email`) REFERENCES `user_table` (`user_email`)
			);';
	mysqli_query($conn, $sql);