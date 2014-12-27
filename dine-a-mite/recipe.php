<?php

/**
 * This is the recipe display page
 * 
 * Used icons downloaded from:
 * http://findicons.com/
 */
include_once 'Includes/db.php';
session_start();
include_once 'Includes/header.php';
if (isset($_GET['recipe_id'])) {
// Select the recipe based on $_GET['recipe_id']
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
} else {
    $result = false;
}
if (!$result) {
    print '<h4 class="text-error">The recipe could not be displayed, please try again later.</h4></div>';
} else {
    if (mysql_num_rows($result) == 0) {
        print '<h4 class="text-error">This recipe does not exist.</h4></div>';
    } else {
        // Display the recipe
        while ($row = mysql_fetch_assoc($result)) {
            // Replace newline with <br />
            $row['recipe_desc'] = nl2br($row['recipe_desc']);
            $row['recipe_ingredients'] = nl2br($row['recipe_ingredients']);
            $row['recipe_content'] = nl2br($row['recipe_content']);
            print '<div id="recipe">
                   <div class="well well-mini">
                   <h4>' . $row['recipe_title'] . '</h4></div>
                   <div class="row-fluid">
                   <div class="span5">
                   <img src="' . $row['recipe_mp'] . '" alt="Recipe Main Picture"
                        id="recipe-main-picture"></div>';
            // Print icons for "Recipe rating"
            if ($row['recipe_rating'] != NULL) {
                print '<div class="span6"><h4>Rating: </h4>
                       <div class="tooltip-demo">
                       <a href="#" rel="tooltip" data-placement="bottom"
                          title="Rating:
                          &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                          &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                          &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                          &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                          1 icon denotes <Poor>
                          &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                          &nbsp&nbsp&nbsp
                          2 icons denote <Mediocre>
                          &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                          3 icons denote <Good>
                          &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                          &nbsp&nbsp&nbsp
                          4 icons denote <Exellent>
                          &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                          5 icons denote <Top>">';
                for ($i = 0; $i < $row['recipe_rating']; $i++) {
                    print '<img src="http://sfsuswe.com/~f12g02/dine-a-mite/star.png" alt="Recipe rating"
                                id="recipe-icon">';
                }
                print '</a></div></div><br />
                       <div class="span6"><br />
                       <h4 id="recipe-print"><span class="print">Print</span></h4></div>
                       <div class="span6"><br />
                       <span class="help-block" id="recipe-note">&nbsp&nbspNote: Hover over icons or click on the "Help" menu option for help</span>
                       </div>';
            }
            print '</div><br />
                   <fieldset id = "fieldset-title">
                   <h5>Recipe description:</h5></fieldset>
                   <fieldset id = "fieldset-content">
                   <div class = "row-fluid"><p>' . $row['recipe_desc'] . '</p></div></fieldset><br />
                   <fieldset id = "fieldset-title">
                   <h5>Recipe general information:</h5></fieldset>
                   <fieldset id = "fieldset-content">
                   <div class = "row-fluid">
                   <div class = "span5">Author: ' . $row['user_name'] . '</div>
                   <div class = "span4">Created on: ' . date('m-d-Y', strtotime($row['recipe_date'])) . '</div>
                   <div class = "row-fluid">
                   <div class = "span5">General category: ' . $row['general_cat_name'] . '</div>
                   <div class = "span6">
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
                      5 icons denote above 12 mins">';
            // Print icons for "Preparation time"
            for ($j = 0; $j < $row['recipe_pt']; $j++) {
                print '<img src = "http://sfsuswe.com/~f12g02/dine-a-mite/clock.png" alt = "Preparation time"
                            id = "recipe-icon">';
            }
            print '</a></div></div></div>
                   <div class = "row-fluid">
                   <div class = "span5">Cuisine category: ' . $row['cuisine_cat_name'] . '</div>
                   <div class = "span6">
                   <div class="tooltip-demo">
                   <a href="#" rel="tooltip" data-placement="bottom"
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
                      5 icons denote above 12 dollars">';
            // Print icons for "Cost"
            for ($j = 0; $j < $row['recipe_cost']; $j++) {
                print '<img src = "http://sfsuswe.com/~f12g02/dine-a-mite/money.png" alt = "Cost"
                            id = "recipe-icon">';
            }
            print '</a></div></div></div></fieldset><br />
                   <fieldset id = "fieldset-title">
                   <h5>Main ingredients:</h5></fieldset>
                   <fieldset id = "fieldset-content">
                   ' . $row['recipe_ingredients'] . '</fieldset><br />
                   <fieldset id = "fieldset-title">
                   <h5>Preparation instructions:</h5></fieldset>
                   <fieldset id = "fieldset-content">
                   ' . $row['recipe_content'] . '</fieldset><br /></div>';
            if ((isset($_SESSION['user_level']) && $_SESSION['user_level'] == 1) || (isset($_SESSION['user_name']) && $_SESSION['user_name'] == $row['user_name'])) {
                print '<div id = "submit-center">
                       <a href = "edit_recipe.php?recipe_id=' . $row['recipe_id'] . '" class = "btn btn-primary btn-large">Edit recipe</a></div>';
            }
            print '<hr><h4>Would you like to write a review for this recipe?';
            print '&nbsp&nbsp<a class = "btn btn-success" href = "review.php?recipe_id=' . $row['recipe_id'] . '">Rate it now!</a></h4><hr />';
            // Execute the query for the reviews
            $sql = "SELECT review_id, review_content, review_date, review_recipe, review_by, review_rating, user_id, user_name
                    FROM reviews LEFT JOIN users
                    ON review_by = users.user_id
                    WHERE review_recipe = " . mysql_real_escape_string($_GET['recipe_id']);
            $result = mysql_query($sql);
            if (!$result) {
                print '<h4 class = "text-error">Reviews display error.</h4>';
            } else {
                if (mysql_num_rows($result) == 0) {
                    //print '<h4 class = "text-error">No reviews have been written!</h4>';
                } else {
                    // Print the reviews
                    if (isset($_GET['reviews_count'])) {
                        $count = $_GET['reviews_count'];
                    } else {
                        $count = 3;
                    }
                    while ($row[] = mysql_fetch_assoc($result));
                    for ($i = 0; $i < $count && $i < mysql_num_rows($result); $i++) {
                        print '<fieldset id = "fieldset-title">
                               <div class = "row-fluid">
                               <div class = "span5"><h5>Reviewed by: ' . $row[$i]['user_name'] . '</h5></div>
                               <div class = "span4"><h5>On ' . date('m-d-Y', strtotime($row[$i]['review_date'])) . '
                               </h5></div></div></fieldset>
                               <fieldset id = "fieldset-content">
                               <div class = "row-fluid">
                               <p>Review rating: </p>';
                        for ($j = 1; $j < 6; $j++) {
                            if ($row[$i]['review_rating'] == $j) {
                                print '<input name = "' . $row[$i]['review_id'] . '" type = "radio" class = "star" disabled = "disabled" checked = "checked"/>';
                            } else {
                                print '<input name = "' . $row[$i]['review_id'] . '" type = "radio" class = "star" disabled = "disabled"/>';
                            }
                        }
                        print '</div><br />
                               <div class = "row-fluid"><p>Review comments: <br />' . $row[$i]['review_content'] . '</p></div></fieldset><br />';
                    }
                    if ($count < mysql_num_rows($result)) {
                        print '<div id = "submit-center">
                               <a href = "recipe.php?recipe_id=' . $_GET['recipe_id'] . '&reviews_count=' . ($count + 3) . '" class = "btn btn-large">More reviews</a></div>';
                    }
                }
            }
        }
    }
}
include_once 'Includes/footer.php';
?>