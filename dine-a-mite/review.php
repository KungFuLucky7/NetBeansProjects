<?php

/**
 * This is the review page for the users
 */
include_once 'Includes/db.php';
session_start();
include_once 'Includes/header.php';
print '<div class="well well-mini">
       <h4>Review a recipe</h4></div>';
if (!isset($_SESSION['signed_in']) || $_SESSION['signed_in'] == false) {
    // Print message if user is not signed in
    print '<div class="alert alert-block">
           <h4>You have to be signed in to review a recipe.<br />
           If you don\'t have an account, please <a class="btn" href="register.php">Register</a></h4></div>';
} else if (!isset($_GET['recipe_id'])) {
    print '<h4 class="text-error">The review form could not be displayed, please try again later.</h4></div>';
} else {
    if ($_SERVER['REQUEST_METHOD'] != 'POST' || isset($_POST['login'])) {
        print '<div class="alert">
               <strong><strong class="text-error">* </strong>indicates required field</strong></div>
               <form method="post" action="" class="form-horizontal">
               <div class="control-group">
               <fieldset id="fieldset-title">
               <h5>Recipe rating:</h5></fieldset>
               <fieldset id="fieldset-content">
               <span class="help-block" id="review-rating"><strong class="text-error">* </strong>Please rate the recipe on a scale of 1 to 5 with 5 being the best</span>
               <input name="review_rating" type="radio" class="star" value="1"/>
               <input name="review_rating" type="radio" class="star" value="2"/>
               <input name="review_rating" type="radio" class="star" value="3"/>
               <input name="review_rating" type="radio" class="star" value="4"/>
               <input name="review_rating" type="radio" class="star" value="5"/></fieldset></div>
               <div class="control-group">
               <fieldset id="fieldset-title">
               <h5>Recipe review:</h5></fieldset>
               <fieldset id="fieldset-content">
               <span class="help-block"><strong class="text-error">* </strong>Please write a review for the recipe</span>
               <textarea name="review_content" rows ="3" class="field span5"></textarea></fieldset></div>
               <div class="control-group">
               <div id="submit-center">
               <button type="submit" class="btn btn-primary btn-large">Review recipe</button></div></div>
               </form>';
    } else {
        // Process the values if the form has been posted
        $errors = array();
        if (!isset($_POST['review_rating'])) {
            $errors[] = 'The recipe rating field is required.';
        }
        if ($_POST['review_content'] == NULL) {
            $errors[] = 'The recipe review field is required.';
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
                   <div class="alert">
                   <strong><strong class="text-error">* </strong>indicates required field</strong></div>
                   <form method="post" action="" class="form-horizontal">
                   <div class="control-group">
                   <fieldset id="fieldset-title">
                   <h5>Recipe rating:</h5></fieldset>
                   <fieldset id="fieldset-content">
                   <span class="help-block" id="review-rating"><strong class="text-error">* </strong>Please rate the recipe on a scale of 1 to 5 with 5 being the best</span>
                   <input name="review_rating" type="radio" class="star" value="1"/>
                   <input name="review_rating" type="radio" class="star" value="2"/>
                   <input name="review_rating" type="radio" class="star" value="3"/>
                   <input name="review_rating" type="radio" class="star" value="4"/>
                   <input name="review_rating" type="radio" class="star" value="5"/></fieldset></div>
                   <div class="control-group">
                   <fieldset id="fieldset-title">
                   <h5>Recipe review:</h5></fieldset>
                   <fieldset id="fieldset-content">
                   <span class="help-block"><strong class="text-error">* </strong>Please write a review for the recipe</span>
                   <textarea name="review_content" rows ="3" class="field span5"></textarea></fieldset></div>
                   <div class="control-group">
                   <div id="submit-center">
                   <button type="submit" class="btn btn-primary btn-large">Review recipe</button></div></div>
                   </form>';
        } else {
            $sql = "INSERT INTO reviews (review_content, review_date, review_recipe, review_by, review_rating)
                    VALUES ('" . mysql_real_escape_string($_POST['review_content']) . "', 
                             NOW(),
                             " . $_GET['recipe_id'] . ", 
                             " . $_SESSION['user_id'] . ",
                             " . $_POST['review_rating'] . "
                           )";
            $result1 = mysql_query($sql);
            // Select the recipe based on $_GET['recipe_id']
            $sql = "SELECT review_recipe, review_rating
                    FROM reviews
                    WHERE review_recipe = " . mysql_real_escape_string($_GET['recipe_id']);
            $result2 = mysql_query($sql);
            $row = Array();
            while ($row[] = mysql_fetch_assoc($result2));
            $sum = 0;
            foreach ($row as $value) {
                $sum += $value['review_rating'];
            }
            $recipe_rating = ceil($sum / (sizeof($row) - 1));
            //print 'sum ' . $sum . 'size ' . (sizeof($row) - 1) . 'rating ' . $recipe_rating;
            $sql = "UPDATE recipes
                    SET recipe_rating = " . $recipe_rating . " 
                    WHERE recipe_id = " . mysql_real_escape_string($_GET['recipe_id']);
            $result3 = mysql_query($sql);
            if (!$result2 || !$result3) {
                print '<h4 class="text-error">An error occured while inserting the recipe rating.</h4><br />';
            }
            if (!$result1) {
                // Print the error message
                print '<h4 class="text-error">An error occured while inserting your data. Please try again later. ' . mysql_error() . '</h4>';
            } else {
                print '<h4 class="text-success">Your review has been submitted!
                       <a href = "recipe.php?recipe_id=' . $_GET['recipe_id'] . '">Click here</a> to return to the recipe.</h4>';
            }
        }
    }
}
include_once 'Includes/footer.php';
?>