<?php

/**
 * This is the web site home page
 * 
 * Used icons downloaded from:
 * http://findicons.com/
 */
include_once 'Includes/db.php';
session_start();
$_SESSION['curr_page'] = 'Home';
include_once 'Includes/header.php';
//<!--Main hero unit for a primary marketing message or call to action-->
print '<div class="hero-unit">
       <h5>Welcome to Dine-A-Mite, our fellow campus friends!</h5>
       <p>Rather than to depend on the rigid menu choices from the campus cafeteria, 
       why not use Dine-A-Mite.com? Dine-A-Mite.com has a great collection of recipes
       targeted for but not limited to students. The recipes are written by contributors
       who share the same interests with us! So please dive right in and enjoy our recipes!
       <a href="about.php">Learn more »</a><br /></p></div>
       <div class="row-fluid" id="submit-center">
       <h5 class="text-success" id="featured-recipe">Featured Recipes:</h5></div>';
// Select the desired recipes
$sql = "SELECT recipe_id, recipe_title, recipe_date, recipe_by, recipe_general_cat, recipe_cuisine_cat,
               recipe_pt, recipe_cost, recipe_mp, recipe_desc, 
               recipe_rating, user_id, user_name,
               general_categories.cat_name AS general_cat_name,
               cuisine_categories.cat_name AS cuisine_cat_name
        FROM recipes 
        LEFT JOIN users ON recipe_by = users.user_id 
        LEFT JOIN general_categories ON recipe_general_cat=general_categories.cat_id 
        LEFT JOIN cuisine_categories ON recipe_cuisine_cat=cuisine_categories.cat_id
        ";
//print 'sql: ' . $sql . '<br />';
$result = mysql_query($sql);
if (!$result) {
    print '<h4 class="text-error"><h4>The recipes could not be displayed, please try again later.</h4></div>';
} else {
    if (mysql_num_rows($result) == 0) {
        print '<h4 class="text-error"><h4>The recipes have not been created yet.</h4></div>';
    } else {
        $row = Array();
        for ($i = 0; $i < 6; $i++) {
            $row[] = mysql_fetch_assoc($result);
        }
        print '<div class="row-fluid">
               <div id="myCarousel" class="carousel slide">
               <!-- Carousel items -->
               <div class="carousel-inner">
               <div class="active item">
               <div class="row-fluid" id="row-fluid-carousel">
               <h5><a href="recipe.php?recipe_id=' . $row[0]['recipe_id'] . '">
               ' . $row[0]['recipe_title'] . '</a></h5>
               <div class="row-fluid">
               <a href="recipe.php?recipe_id=' . $row[0]['recipe_id'] . '">
               <img class="img-rounded" src="' . $row[0]['recipe_mp'] . '" 
                    id="carousel-img"></a></div>
               <div class="row-fluid">
               <h5>Author: ' . $row[0]['user_name'] . '
               &nbsp&nbsp&nbsp&nbsp&nbspCreated on: ' . date('m-d-Y', strtotime($row[0]['recipe_date'])) . '</h5></div>';
        // Print icons for "Recipe rating"
        if ($row[0]['recipe_rating'] != NULL) {
            print '<div class="row-fluid">
                   <div class="tooltip-demo">
                   <a href="#" rel="tooltip" data-placement="top" 
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
            for ($j = 0; $j < $row[0]['recipe_rating']; $j++) {
                print '<img src="http://sfsuswe.com/~f12g02/dine-a-mite/star.png" alt="Recipe rating"
                            id="recipe-icon">';
            }
            print '</a></div></div>';
        }
        print '</div></div>';
        for ($i = 1; $i < 6; $i++) {
            print '<div class="item">
                   <div class="row-fluid" id="row-fluid-carousel">
                   <h5><a href="recipe.php?recipe_id=' . $row[$i]['recipe_id'] . '">
                   ' . $row[$i]['recipe_title'] . '</a></h5>
                   <div class="row-fluid">
                   <a href="recipe.php?recipe_id=' . $row[$i]['recipe_id'] . '">
                   <img class="img-rounded" src="' . $row[$i]['recipe_mp'] . '" 
                        id="carousel-img"></a></div>
                   <div class="row-fluid">
                   <h5>Author: ' . $row[$i]['user_name'] . '
                   &nbsp&nbsp&nbsp&nbsp&nbspCreated on: ' . date('m-d-Y', strtotime($row[$i]['recipe_date'])) . '</h5></div>';
            // Print icons for "Recipe rating"
            if ($row[$i]['recipe_rating'] != NULL) {
                print '<div class="row-fluid">
                       <div class="tooltip-demo">
                       <a href="#" rel="tooltip" data-placement="top" 
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
                for ($j = 0; $j < $row[$i]['recipe_rating']; $j++) {
                    print '<img src="http://sfsuswe.com/~f12g02/dine-a-mite/star.png" alt="Recipe rating"
                                id="recipe-icon">';
                }
                print '</a></div></div>';
            }
            print '</div></div>';
        }
        print '</div>
               <!-- Carousel nav -->
               <a class="carousel-control left" href="#myCarousel" data-slide="prev">&lsaquo;</a>
               <a class="carousel-control right" href="#myCarousel" data-slide="next">&rsaquo;</a>
               </div></div>';
    }
}
include_once 'Includes/footer.php';
?>