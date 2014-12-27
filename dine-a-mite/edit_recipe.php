<?php

/**
 * This is the recipe edit page
 */
include_once 'Includes/db.php';
session_start();
include_once 'Includes/header.php';
print '<div class="well well-mini">
       <h4>Edit a recipe</h4></div>';
if (!isset($_SESSION['signed_in']) || $_SESSION['signed_in'] == false) {
    // Print message if user is not signed in
    print '<div class="alert alert-block">
           <h4>You have to be signed in to edit a recipe.<br />
           If you don\'t have an account, please <a class="btn" href="register.php">Register</a></h4></div>';
} else {
    if ($_SERVER['REQUEST_METHOD'] != 'POST' || isset($_POST['login'])) {
        include_once 'Includes/fileUpload.php';
        // Display the form if it hasn't been posted
        // Select the recipe based on $_GET['recipe_id']
        if (isset($_GET['recipe_id'])) {
            $sql = "SELECT recipe_id, recipe_title, recipe_date, recipe_by, recipe_general_cat, recipe_cuisine_cat,
                           recipe_pt, recipe_cost, recipe_mp, recipe_desc,
                           recipe_ingredients, recipe_content, recipe_rating, user_id, user_name,
                           general_categories.cat_name AS general_cat_name,
                           cuisine_categories.cat_name AS cuisine_cat_name
                    FROM recipes
                    LEFT JOIN users ON recipe_by = users.user_id
                    LEFT JOIN general_categories ON recipe_general_cat=general_categories.cat_id
                    LEFT JOIN cuisine_categories ON recipe_cuisine_cat=cuisine_categories.cat_id
                    WHERE recipe_id = " . mysql_real_escape_string($_GET['recipe_id']);
            //print 'sql: ' . $sql . '<br />';
            $result = mysql_query($sql);
            // Retrieve the categories from the database for use in the dropdown
            $sql1 = "SELECT
					cat_id,
					cat_name
                     FROM
					general_categories";
            $result1 = mysql_query($sql1);
            $sql2 = "SELECT
					cat_id,
					cat_name
                     FROM
					cuisine_categories";
            $result2 = mysql_query($sql2);
        } else {
            $result = $result1 = $result2 = false;
        }
        if (!$result || !$result1 || !$result2) {
            // Print the error message if the query fails
            print '<h4 class="text-error">Database Access Error: Please try again later.</h4>';
        } else {
            if (mysql_num_rows($result) == 0) {
                print '<h4 class="text-error"><h4>This recipe does not exist.</h4></div>';
            } else if (mysql_num_rows($result1) == 0 || mysql_num_rows($result2) == 0) {
                // Print the error message if no categories are found
                if ($_SESSION['user_level'] == 1) {
                    print '<h4 class="text-error">No categories has been created!</h4>';
                } else {
                    print '<h4 class="text-error">You can only upload a recipe if some categories have been created by an administrator.</h4>';
                }
            } else {
                while ($row = mysql_fetch_assoc($result)) {
                    print '<div class="alert" id="required-alert">
                           <strong><strong class="text-error">* </strong>indicates required field</strong></div>
                           <form method="post" action="" class="form-horizontal">
                           <div class="control-group">
                           <fieldset id="fieldset-title">
                           <h5>Recipe general information:</h5></fieldset>
                           <fieldset id="fieldset-content">
                           <div class="control-group">
                           <div class="span4" id="recipe-general">
                           <label class="control-label"><strong class="text-error">* </strong>Recipe title:</label>
                           <div class="controls">
                           <input type="text" name="recipe_title" value="' . $row['recipe_title'] . '"></div></div>
                           <div class="span8">
                           <label class="control-label">
                           <div class="tooltip-demo">
                           <a href="#" rel="tooltip" data-placement="top" 
                              title="Preparation time:
                                     &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                                     &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                                     1 icon denotes 1-3 mins
                                     &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                                     2 icons denote 3-6 mins
                                     &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                                     3 icons denote 6-9 mins
                                     &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                                     4 icons denote 9-12 mins
                                     &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                                     5 icons denote above 12 mins">
                           <img src="http://sfsuswe.com/~f12g02/dine-a-mite/clock.png" alt="Preparation time"
                                id="recipe-icon"></a>:</div></label>
                           <div class="controls" id="recipe-radio">';
                    for ($i = 1; $i < 6; $i++) {
                        if ($row['recipe_pt'] == $i) {
                            print '<input name="recipe_pt" type="radio" value="' . $i . '" checked/>&nbsp' . $i . '&nbsp&nbsp&nbsp&nbsp';
                        } else {
                            print '<input name="recipe_pt" type="radio" value="' . $i . '"/>&nbsp' . $i . '&nbsp&nbsp&nbsp&nbsp';
                        }
                    }
                    print '</div></div></div>
                           <div class="control-group">
                           <div class="span4" id="recipe-general">
                           <label class="control-label">General category:</label>
                           <div class="controls">
                           <select name="recipe_general_cat">
                           <option selected="selected" value="' . $row['recipe_general_cat'] . '">' . $row['general_cat_name'] . '</option>';
                    while ($row1 = mysql_fetch_assoc($result1)) {
                        print '<option value="' . $row1['cat_id'] . '">' . $row1['cat_name'] . '</option>';
                    }
                    print '</select></div></div>
                           <div class="span8">
                           <label class="control-label">
                           <div class="tooltip-demo">
                           <a href="#" rel="tooltip" data-placement="top" 
                              title="Cost:
                                     &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                                     &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                                     &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                                     &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                                     1 icon denotes 1-3 dollars
                                     &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                                     2 icons denote 3-6 dollars
                                     &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                                     3 icons denote 6-9 dollars
                                     &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                                     4 icons denote 9-12 dollars
                                     &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                                     5 icons denote above 12 dollars">
                           <img src="http://sfsuswe.com/~f12g02/dine-a-mite/money.png" alt="Cost"
                                id="recipe-icon"></a>:</div></label>
                           <div class="controls" id="recipe-radio">';
                    for ($i = 1; $i < 6; $i++) {
                        if ($row['recipe_cost'] == $i) {
                            print '<input name="recipe_cost" type="radio" value="' . $i . '" checked/>&nbsp' . $i . '&nbsp&nbsp&nbsp&nbsp';
                        } else {
                            print '<input name="recipe_cost" type="radio" value="' . $i . '"/>&nbsp' . $i . '&nbsp&nbsp&nbsp&nbsp';
                        }
                    }
                    print '</div></div></div>
                           <div class="control-group">
                           <div class="span4" id="recipe-general">
                           <label class="control-label">Cuisine category:</label>
                           <div class="controls">
                           <select name="recipe_cuisine_cat">';
                    $row2 = Array();
                    for ($i = 0; $i < mysql_num_rows($result2); $i++) {
                        $row2[] = mysql_fetch_assoc($result2);
                    }
                    custom_sort($row2, 'cat_name');
                    print '<option selected="selected" value="' . $row['recipe_cuisine_cat'] . '">' . $row['cuisine_cat_name'] . '</option>';
                    foreach ($row2 as $value) {
                        if ($value['cat_name'] != $row['cuisine_cat_name']) {
                            print '<option value="' . $value['cat_id'] . '">' . $value['cat_name'] . '</option>';
                        }
                    }
                    print '</select></div></div>
                           <div class="span8">
                           <div class="controls">
                           <span class="help-block" id="recipe-help">&nbsp&nbspNote: Hover over icons or click on the "Help" menu option for help</span>
                           </div></div></fieldset></div>
                           <div class="control-group">
                           <fieldset id="fieldset-title">
                           <h5>Recipe main picture:</h5></fieldset>
                           <fieldset id="fieldset-content">
                           <span class="help-block"><strong class="text-error">* </strong>Upload the main picture of the recipe</span>
                           <input type="text" name="recipe_mp" readonly="readonly" onclick="openKCFinder(this)" style="width:500px;cursor:pointer" value="' . $row['recipe_mp'] . '"></div></fieldset>
                           <div class="control-group">
                           <fieldset id="fieldset-title">
                           <h5>Recipe description:</h5></fieldset>
                           <fieldset id="fieldset-content">
                           <span class="help-block"><strong class="text-error">* </strong>Enter the brief description of the recipe</span>
                           <textarea name="recipe_desc" rows="3" class="field span5">' . $row['recipe_desc'] . '</textarea></fieldset></div>
                           <div class="control-group">
                           <fieldset id="fieldset-title">
                           <h5>Main ingredients:</h5></fieldset>
                           <fieldset id="fieldset-content">
                           <span class="help-block"><strong class="text-error">* </strong>List the main ingredients (and their locations) of the recipe</span>
                           <textarea name="recipe_ingredients" rows ="3" class="field span5">' . $row['recipe_ingredients'] . '</textarea></fieldset></div>
                           <div class="control-group">
                           <fieldset id="fieldset-title">
                           <h5>Preparation instructions:</h5></fieldset>
                           <fieldset id="fieldset-content">
                           <span class="help-block"><strong class="text-error">* </strong>Enter the cooking steps here</span>
                           <textarea name="recipe_content" rows="4" class="field span5">' . $row['recipe_content'] . '</textarea></fieldset></div>
                           <div class="control-group">
                           <div id="submit-center">
                           <button type="submit" class="btn btn-primary btn-large">Update recipe</button></div></div>
                           </form>';
                }
            }
        }
    } else {
        // Process the values if the form has been posted
        $errors = array();
        if ($_POST['recipe_title'] == NULL) {
            $errors[] = 'The recipe title field is required.';
        }
        if ($_POST['recipe_mp'] == NULL) {
            $errors[] = 'The recipe main picture field is required.';
        }
        if ($_POST['recipe_desc'] == NULL) {
            $errors[] = 'The recipe description field is required.';
        }
        if ($_POST['recipe_ingredients'] == NULL) {
            $errors[] = 'The main ingredients field is required.';
        }
        if ($_POST['recipe_content'] == NULL) {
            $errors[] = 'The preparation instructions field is required.';
        }
        if (!empty($errors)) {
            print '<div class="alert alert-error">
                   <button type="button" class="close" data-dismiss="alert">×</button>
                   Error: Some fields are not entered correctly.<ul>';
            // Generate a list of errors
            foreach ($errors as $key => $value) {
                print '<li>' . $value . '</li>';
            }
            print '</ul></div>';
            include_once 'Includes/fileUpload.php';
            // Display the form if it hasn't been posted
            // Select the recipe based on $_GET['recipe_id']
            if (isset($_GET['recipe_id'])) {
                $sql = "SELECT recipe_id, recipe_title, recipe_date, recipe_by, recipe_general_cat, recipe_cuisine_cat,
                           recipe_pt, recipe_cost, recipe_mp, recipe_desc,
                           recipe_ingredients, recipe_content, recipe_rating, user_id, user_name,
                           general_categories.cat_name AS general_cat_name,
                           cuisine_categories.cat_name AS cuisine_cat_name
                    FROM recipes
                    LEFT JOIN users ON recipe_by = users.user_id
                    LEFT JOIN general_categories ON recipe_general_cat=general_categories.cat_id
                    LEFT JOIN cuisine_categories ON recipe_cuisine_cat=cuisine_categories.cat_id
                    WHERE recipe_id = " . mysql_real_escape_string($_GET['recipe_id']);
                //print 'sql: ' . $sql . '<br />';
                $result = mysql_query($sql);
                // Retrieve the categories from the database for use in the dropdown
                $sql1 = "SELECT
					cat_id,
					cat_name
                     FROM
					general_categories";
                $result1 = mysql_query($sql1);
                $sql2 = "SELECT
					cat_id,
					cat_name
                     FROM
					cuisine_categories";
                $result2 = mysql_query($sql2);
            } else {
                $result = $result1 = $result2 = false;
            }
            if (!$result || !$result1 || !$result2) {
                // Print the error message if the query fails
                print '<h4 class="text-error">Database Access Error: Please try again later.</h4>';
            } else {
                if (mysql_num_rows($result) == 0) {
                    print '<h4 class="text-error"><h4>This recipe does not exist.</h4></div>';
                } else if (mysql_num_rows($result1) == 0 || mysql_num_rows($result2) == 0) {
                    // Print the error message if no categories are found
                    if ($_SESSION['user_level'] == 1) {
                        print '<h4 class="text-error">No categories has been created!</h4>';
                    } else {
                        print '<h4 class="text-error">You can only upload a recipe if some categories have been created by an administrator.</h4>';
                    }
                } else {
                    while ($row = mysql_fetch_assoc($result)) {
                        print '<div class="alert" id="required-alert">
                               <strong><strong class="text-error">* </strong>indicates required field</strong></div>
                               <form method="post" action="" class="form-horizontal">
                               <div class="control-group">
                               <fieldset id="fieldset-title">
                               <h5>Recipe general information:</h5></fieldset>
                               <fieldset id="fieldset-content">
                               <div class="control-group">
                               <div class="span4" id="recipe-general">
                               <label class="control-label"><strong class="text-error">* </strong>Recipe title:</label>
                               <div class="controls">
                               <input type="text" name="recipe_title" value="' . $row['recipe_title'] . '"></div></div>
                               <div class="span8">
                               <label class="control-label">
                               <div class="tooltip-demo">
                               <a href="#" rel="tooltip" data-placement="top" 
                                  title="Preparation time:
                                         &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                                         &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                                         1 icon denotes 1-3 mins
                                         &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                                         2 icons denote 3-6 mins
                                         &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                                         3 icons denote 6-9 mins
                                         &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                                         4 icons denote 9-12 mins
                                         &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                                         5 icons denote above 12 mins">
                               <img src="http://sfsuswe.com/~f12g02/dine-a-mite/clock.png" alt="Preparation time"
                                    id="recipe-icon"></a>:</div></label>
                               <div class="controls" id="recipe-radio">';
                        for ($i = 1; $i < 6; $i++) {
                            if ($row['recipe_pt'] == $i) {
                                print '<input name="recipe_pt" type="radio" value="' . $i . '" checked/>&nbsp' . $i . '&nbsp&nbsp&nbsp&nbsp';
                            } else {
                                print '<input name="recipe_pt" type="radio" value="' . $i . '"/>&nbsp' . $i . '&nbsp&nbsp&nbsp&nbsp';
                            }
                        }
                        print '</div></div></div>
                               <div class="control-group">
                               <div class="span4" id="recipe-general">
                               <label class="control-label">General category:</label>
                               <div class="controls">
                               <select name="recipe_general_cat">
                               <option selected="selected" value="' . $row['recipe_general_cat'] . '">' . $row['general_cat_name'] . '</option>';
                        while ($row1 = mysql_fetch_assoc($result1)) {
                            print '<option value="' . $row1['cat_id'] . '">' . $row1['cat_name'] . '</option>';
                        }
                        print '</select></div></div>
                               <div class="span8">
                               <label class="control-label">
                               <div class="tooltip-demo">
                               <a href="#" rel="tooltip" data-placement="top" 
                                  title="Cost:
                                         &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                                         &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                                         &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                                         &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                                         1 icon denotes 1-3 dollars
                                         &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                                         2 icons denote 3-6 dollars
                                         &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                                         3 icons denote 6-9 dollars
                                         &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                                         4 icons denote 9-12 dollars
                                         &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                                         5 icons denote above 12 dollars">
                               <img src="http://sfsuswe.com/~f12g02/dine-a-mite/money.png" alt="Cost"
                                    id="recipe-icon"></a>:</div></label>
                               <div class="controls" id="recipe-radio">';
                        for ($i = 1; $i < 6; $i++) {
                            if ($row['recipe_cost'] == $i) {
                                print '<input name="recipe_cost" type="radio" value="' . $i . '" checked/>&nbsp' . $i . '&nbsp&nbsp&nbsp&nbsp';
                            } else {
                                print '<input name="recipe_cost" type="radio" value="' . $i . '"/>&nbsp' . $i . '&nbsp&nbsp&nbsp&nbsp';
                            }
                        }
                        print '</div></div></div>
                               <div class="control-group">
                               <div class="span4" id="recipe-general">
                               <label class="control-label">Cuisine category:</label>
                               <div class="controls">
                               <select name="recipe_cuisine_cat">';
                        $row2 = Array();
                        for ($i = 0; $i < mysql_num_rows($result2); $i++) {
                            $row2[] = mysql_fetch_assoc($result2);
                        }
                        custom_sort($row2, 'cat_name');
                        print '<option selected="selected" value="' . $row['recipe_cuisine_cat'] . '">' . $row['cuisine_cat_name'] . '</option>';
                        foreach ($row2 as $value) {
                            if ($value['cat_name'] != $row['cuisine_cat_name']) {
                                print '<option value="' . $value['cat_id'] . '">' . $value['cat_name'] . '</option>';
                            }
                        }
                        print '</select></div></div>
                               <div class="span8">
                               <div class="controls">
                               <span class="help-block" id="recipe-help">&nbsp&nbspNote: Hover over icons or click on the "Help" menu option for help</span>
                               </div></div></fieldset></div>
                               <div class="control-group">
                               <fieldset id="fieldset-title">
                               <h5>Recipe main picture:</h5></fieldset>
                               <fieldset id="fieldset-content">
                               <span class="help-block"><strong class="text-error">* </strong>Upload the main picture of the recipe</span>
                               <input type="text" name="recipe_mp" readonly="readonly" onclick="openKCFinder(this)" style="width:500px;cursor:pointer" value="' . $row['recipe_mp'] . '"></div></fieldset>
                               <div class="control-group">
                               <fieldset id="fieldset-title">
                               <h5>Recipe description:</h5></fieldset>
                               <fieldset id="fieldset-content">
                               <span class="help-block"><strong class="text-error">* </strong>Enter the brief description of the recipe</span>
                               <textarea name="recipe_desc" rows="3" class="field span5">' . $row['recipe_desc'] . '</textarea></fieldset></div>
                               <div class="control-group">
                               <fieldset id="fieldset-title">
                               <h5>Main ingredients:</h5></fieldset>
                               <fieldset id="fieldset-content">
                               <span class="help-block"><strong class="text-error">* </strong>List the main ingredients (and their locations) of the recipe</span>
                               <textarea name="recipe_ingredients" rows ="3" class="field span5">' . $row['recipe_ingredients'] . '</textarea></fieldset></div>
                               <div class="control-group">
                               <fieldset id="fieldset-title">
                               <h5>Preparation instructions:</h5></fieldset>
                               <fieldset id="fieldset-content">
                               <span class="help-block"><strong class="text-error">* </strong>Enter the cooking steps here</span>
                               <textarea name="recipe_content" rows="4" class="field span5">' . $row['recipe_content'] . '</textarea></fieldset></div>
                               <div class="control-group">
                               <div id="submit-center">
                               <button type="submit" class="btn btn-primary btn-large">Update recipe</button></div></div>
                               </form>';
                    }
                }
            }
        } else {
            // Start a transaction
            $query = "BEGIN WORK;";
            $result = mysql_query($query);
            if (!$result) {
                // If the query fails, quit
                print '<h4 class="text-error">An error occured while creating your recipe. Please try again later.</h4>';
            } else {
                // Submit the form in the database if it has been posted without errors
                $sql = "UPDATE recipes
                        SET recipe_title = '" . mysql_real_escape_string($_POST['recipe_title']) . "',
                            recipe_general_cat = " . mysql_real_escape_string($_POST['recipe_general_cat']) . ",
                            recipe_cuisine_cat = " . mysql_real_escape_string($_POST['recipe_cuisine_cat']) . ",
                            recipe_pt = '" . mysql_real_escape_string($_POST['recipe_pt']) . "',
                            recipe_cost = '" . mysql_real_escape_string($_POST['recipe_cost']) . "',
                            recipe_mp = '" . mysql_real_escape_string($_POST['recipe_mp']) . "',
                            recipe_desc = '" . mysql_real_escape_string($_POST['recipe_desc']) . "',
                            recipe_ingredients = '" . mysql_real_escape_string($_POST['recipe_ingredients']) . "',
                            recipe_content = '" . mysql_real_escape_string($_POST['recipe_content']) . "'
                        WHERE recipe_id = " . mysql_real_escape_string($_GET['recipe_id']);
                //print "sql: " . $sql . "<br />";
                $result = mysql_query($sql);
                if (!$result) {
                    // Print the error message
                    print '<h4 class="text-error">An error occured while updating your data. Please try again later. ' . mysql_error() . '</h4>';
                    $sql = "ROLLBACK;
                        ";
                    $result = mysql_query($sql);
                } else {
                    $sql = "COMMIT;";
                    $result = mysql_query($sql);
                    print '<h4 class="text-success">You have successfully updated the <a href = "recipe.php?recipe_id=' . $_GET['recipe_id'] . '">' . $_POST['recipe_title'] . '</a> recipe.</h4>';
                }
            }
        }
    }
}
include_once 'Includes/footer.php';
?>