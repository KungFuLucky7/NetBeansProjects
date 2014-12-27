<?php

/**
 * This is the profile page of the user
 */
include_once 'Includes/db.php';
session_start();
include_once 'Includes/header.php';
print '<div class="well well-mini">
       <h4>User Profile Page</h4></div>';
if (!isset($_SESSION['signed_in']) || $_SESSION['signed_in'] == false) {
    // Print message if user is not signed in
    print '<div class="alert alert-block">
           <h4>You have to be signed in to edit your profile.<br />
           If you don\'t have an account, please <a class="btn" href="register.php">Register</a></h4></div>';
} else {
    $sql = "SELECT user_name, user_pass, user_email
            FROM users
            WHERE user_id = " . mysql_real_escape_string($_SESSION['user_id']);
    $result = mysql_query($sql);
    if (!$result) {
        print '<h4 class="text-error">
               User Display Error: Please try again later. ' . mysql_error() . '</h4>';
    } else {
        if (mysql_num_rows($result) == 0) {
            print '<h4 class="text-error">
                   This user does not exist.</h4>';
        } else {
            $row = mysql_fetch_assoc($result);
            if ($_SERVER['REQUEST_METHOD'] != 'POST') {
                // Display the form if it hasn't been posted yet
                print '<div class="alert" id="required-alert">
                       <strong><strong class="text-error">* </strong>indicates required field</strong></div>
                       <form method="post" action="" class="form-horizontal">
                       <div class="control-group" id="profile-name">
                       <label class="control-label">Username:</label>
                       <div class="controls">
                       <p class="lead">' . $row['user_name'] . '</p></div></div>
                       <div class="control-group">
                       <label class="control-label"><strong class="text-error">* </strong>Current Password:</label>
                       <div class="controls">
                       <input type="password" name="user_pass"></div></div>
                       <div class="control-group">
                       <label class="control-label">New Password:</label>
                       <div class="controls">
                       <input type="password" name="new_user_pass"></div></div>
                       <div class="control-group">
                       <label class="control-label">Re-enter New Password:</label>
                       <div class="controls">
                       <input type="password" name="new_user_pass_check"></div></div>
                       <div class="control-group">
                       <label class="control-label">E-mail:</label>
                       <div class="controls">
                       <input type="email" name="user_email" value="' . $row['user_email'] . '"></div></div>
                       <div class = "control-group">
                       <div class="controls">
                       <input type="submit" value="Update profile" class="btn btn-primary btn-large"></div></div>
                       </form>';
            } else {
                // Process the values if the form has been posted
                $errors = array();
                if (isset($_POST['user_pass'])) {
                    if ($_POST['user_pass'] == NULL) {
                        $errors[] = 'The current password field is required.';
                    } else {
                        if (sha1($_POST['user_pass']) != $row['user_pass']) {
                            $errors[] = 'The current password entered is incorrect!';
                        }
                        if (isset($_POST['new_user_pass']) && $_POST['new_user_pass'] != NULL) {
                            if ($_POST['new_user_pass'] != $_POST['new_user_pass_check']) {
                                $errors[] = 'The two new passwords don\'t match.';
                            }
                        }
                    }
                }
                if (empty($errors)) {
                    // Submit the form in the database if it has been posted without errors
                    if ($_POST['new_user_pass'] != NULL) {
                        $user_pass = $_POST['new_user_pass'];
                    } else {
                        $user_pass = $_POST['user_pass'];
                    }
                    $sql = "UPDATE users
                            SET user_pass='" . sha1($user_pass) . "', 
                                user_email='" . mysql_real_escape_string($_POST['user_email']) . "'
                            WHERE user_id = " . mysql_real_escape_string($_SESSION['user_id']);
                    $result = mysql_query($sql);
                    if (!$result) {
                        $errors[] = 'Something went wrong while updating profile. Please try again later.';
                    }
                }
                if (!empty($errors)) {
                    print '<div class="alert alert-error">
                           <button type="button" class="close" data-dismiss="alert">×</button>
                           Error: Some fields are not entered correctly.<ul>';
                    // Generate a list of errors
                    foreach ($errors as $key => $value) {
                        print '<li>' . $value . '</li>';
                    }
                    print '</ul></div>
                           <div class="alert" id="required-alert">
                           <strong><strong class="text-error">* </strong>indicates required field</strong></div>
                           <form method="post" action="" class="form-horizontal">
                           <div class="control-group">
                           <label class="control-label">Username:</label>
                           <div class="controls">
                           <p class="lead">' . $row['user_name'] . '</p></div></div>
                           <div class="control-group">
                           <label class="control-label"><strong class="text-error">* </strong>Current Password:</label>
                           <div class="controls">
                           <input type="password" name="user_pass"></div></div>
                           <div class="control-group">
                           <label class="control-label">New Password:</label>
                           <div class="controls">
                           <input type="password" name="new_user_pass"></div></div>
                           <div class="control-group">
                           <label class="control-label">Re-enter New Password:</label>
                           <div class="controls">
                           <input type="password" name="new_user_pass_check"></div></div>
                           <div class="control-group">
                           <label class="control-label">E-mail:</label>
                           <div class="controls">
                           <input type="email" name="user_email" value="' . $row['user_email'] . '"></div></div>
                           <div class = "control-group">
                           <div class="controls">
                           <input type="submit" value="Update profile" class="btn btn-primary btn-large"></div></div>
                           </form>';
                } else {
                    print '<h4 class="text-success">You have successfully updated the user profile!
                           <a href = "profile.php">Click here</a> to return to the profile page.</h4>';
                }
            }
        }
    }
}
include_once 'Includes/footer.php';
?>