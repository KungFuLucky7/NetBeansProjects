<?php

/**
 * This is the create category page for admins
 */
include_once 'Includes/db.php';
session_start();
$_SESSION['curr_page'] = 'Admin';
include_once 'Includes/header.php';
print '<div class="well well-mini">
       <h4>Create a category</h4></div>';
if (!isset($_SESSION['signed_in']) || $_SESSION['signed_in'] == false) {
    // Print message if user is not signed in
    print '<div class="alert alert-block">
           <h4>You have to be signed in to create a category.<br />
           If you don\'t have an account, please <a class="btn" href="register.php">Register</a></h4></div>';
} else {
    if ($_SERVER['REQUEST_METHOD'] != 'POST' || isset($_POST['login'])) {
        // Display the form
        print '<form method="post" action=""/>
               General category name:<br /><br /> <input type="text" name="general_cat_name" /><br /><br />
               <input type="submit" name="general_cat" value="Add general category" class="btn btn-success btn-large"/>
               </form>
               <form method="post" action=""/>
               Cuisine category name:<br /><br /> <input type="text" name="cuisine_cat_name" /><br /><br />
               <input type="submit" name="cuisine_cat" value="Add cuisine category" class="btn btn-warning btn-large"/>
               </form>';
    } else if (isset($_POST['general_cat']) && $_POST['general_cat_name'] != NULL) {
// Enter the values in the database if the form has been posted
        $sql = "INSERT INTO general_categories(cat_name)
                VALUES('" . mysql_real_escape_string($_POST['general_cat_name']) . "')";
        $result = mysql_query($sql);
        if (!$result) {
// Print the error message
            print 'Error' . mysql_error();
        } else {
            $category_id = mysql_insert_id();
            print '<h4>New category <a href="category.php?cat_id=' . $category_id . '">' . $_POST['general_cat_name'] . '</a> was successfully added!<br /><br />';
            print '<a class="btn btn-primary" href="index.php">Proceed to the home page</a></h4>';
        }
    } else if (isset($_POST['cuisine_cat']) && $_POST['cuisine_cat_name'] != NULL) {
        // Enter the values in the database if the form has been posted
        $sql = "INSERT INTO cuisine_categories(cat_name)
                VALUES('" . mysql_real_escape_string($_POST['cuisine_cat_name']) . "')";
        $result = mysql_query($sql);
        if (!$result) {
            // Print the error message
            print 'Error' . mysql_error();
        } else {
            $category_id = mysql_insert_id();
            print '<h4 class="text-success">New category <a href="category.php?cat_id=' . $category_id . '">' . $_POST['cuisine_cat_name'] . '</a> was successfully added!<br /><br />';
            print '<a class="btn btn-primary" href="index.php">Proceed to the home page</a></h4>';
        }
    } else {
        print '<div class="alert alert-error">
               <button type="button" class="close" data-dismiss="alert">×</button>
               The category name input field is required.</div>
               <form method="post" action=""/>
               General category name:<br /><br /> <input type="text" name="general_cat_name" /><br /><br />
               <input type="submit" name="general_cat" value="Add general category" class="btn btn-success btn-large"/>
               </form><br />
               <form method="post" action=""/>
               Cuisine category name:<br /><br /> <input type="text" name="cuisine_cat_name" /><br /><br />
               <input type="submit" name="cuisine_cat" value="Add cuisine category" class="btn btn-warning btn-large"/>
               </form>';
    }
}
include_once 'Includes/footer.php';
?>
