<?php

/**
 * This is for access to the database
 */
$host = 'sfsuswe.com';
$username = 'f12g02';
$password = '1234';
$database = 'student_f12g02';

if (!mysql_connect($host, $username, $password)) {
    exit('Error: could not establish database connection');
}
if (!mysql_select_db($database)) {
    exit('Error: could not select the database');
}
?>
	