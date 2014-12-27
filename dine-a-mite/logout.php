<?php

/**
 * This is for logging out the user
 */
include_once 'Includes/db.php';
session_start();
if (!isset($_SESSION['signed_in']) || $_SESSION['signed_in'] == false) {
    include_once 'Includes/header.php';
    // Print message if user is not signed in
    print '<div class="alert alert-block">
           <h4>You have to be signed in to access this page.<br />
           If you don\'t have an account, please <a class="btn" href="register.php">Register</a></h4></div>';
    include_once 'Includes/footer.php';
} else {
    session_destroy();
    $_SESSION['signed_in'] = false;
    if (!$_SESSION['signed_in']) {
        header('Location: http://sfsuswe.com' . $_GET['redirect_url']);
    }
}
?>