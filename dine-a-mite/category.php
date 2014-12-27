<?php

/**
 * This is for displaying the digested recipes in a specific category
 *
 * Used icons downloaded from:
 * http://findicons.com/
 */
include_once 'Includes/db.php';
session_start();
include_once 'Includes/header.php';
//Select the category based on $_GET['general_cat_id'] or $_GET['cuisine_cat_id']
if (isset($_GET['general_cat_id'])) {
    $sql = "SELECT
			cat_id,
			cat_name
		FROM
			general_categories
		WHERE
			cat_id = " . mysql_real_escape_string($_GET['general_cat_id']);
    $result = mysql_query($sql);
} else if (isset($_GET['cuisine_cat_id'])) {
    $sql = "SELECT
			cat_id,
			cat_name
		FROM
			cuisine_categories
		WHERE
			cat_id = " . mysql_real_escape_string($_GET['cuisine_cat_id']);
    $result = mysql_query($sql);
} else if (isset($_GET['student_cat_id'])) {
    $sql = "SELECT
			cat_id,
                        cat_name,
                        search_by
                FROM student_categories
		WHERE
			cat_id = " . mysql_real_escape_string($_GET['student_cat_id']);
    $result = mysql_query($sql);
} else {
    $result = false;
}
if (!$result) {
    print '<h4 class="text-error">Category Display Error: Please try again later.</h4>';
} else {
    if (mysql_num_rows($result) == 0) {
        print '<h4 class="text-error">This category does not exist.</h4>';
    } else {
        while ($row = mysql_fetch_assoc($result)) {
            print '<div class="well well-mini">
                   <h4>Category: ' . $row['cat_name'] . '</h4></div>';
            if (isset($_GET['student_cat_id'])) {
                $sort = $row['search_by'];
            }
        }
// Execute a query for the recipes
        $sql = "SELECT
					recipe_id,
					recipe_title,
					recipe_date,
                                        recipe_by,
                                        recipe_pt,
                                        recipe_cost,
                                        recipe_mp,
                                        recipe_desc,
                                        recipe_rating,
                                        user_id,
                                        user_name
				FROM
					recipes LEFT JOIN users
                                ON
                                        recipe_by=users.user_id";
        if (isset($_GET['general_cat_id'])) {
            $sql .=" WHERE recipe_general_cat=" . mysql_real_escape_string($_GET['general_cat_id']);
        } else if (isset($_GET['cuisine_cat_id'])) {
            $sql .=" WHERE recipe_cuisine_cat=" . mysql_real_escape_string($_GET['cuisine_cat_id']);
        } else if (isset($_GET['student_cat_id'])) {
            $row = mysql_fetch_assoc($result);
            $sql .=" ORDER BY " . $sort;
        }
        //print 'sql: ' . $sql . '<br />';
        $result = mysql_query($sql);
        if (!$result) {
            print $sql;
            print '<h4 class="text-error">Recipes Display Error: Please try again later.</h4>';
        } else {
            if (mysql_num_rows($result) == 0) {
                print '<h4>There are no recipes in this category yet.</h4>';
            } else {
                $row = Array();
                while ($row[] = mysql_fetch_assoc($result));
                if (!isset($_GET['page_num'])) {
                    $_GET['page_num'] = 0;
                }
                for ($i = 6 * $_GET['page_num']; $i < 6 * ($_GET['page_num'] + 1) && $i < mysql_num_rows($result); $i++) {
                    $recipe_desc = substr($row[$i]['recipe_desc'], 0, strpos(wordwrap($row[$i]['recipe_desc'], 100), "\n"));
                    if ($recipe_desc == NULL) {
                        $recipe_desc = $row[$i]['recipe_desc'];
                    }
                    print '<div class="row-fluid">
                           <div class="media">
                           <div class="span4">
                           <a class="pull-left" href="recipe.php?recipe_id=' . $row[$i]['recipe_id'] . '">
                           <img class="media-object img-polaroid" src="' . $row[$i]['recipe_mp'] . '"
                                id="recipe-thumbnail"></a></div>
                           <div class="media-body">
                           <h4 class="media-heading">&nbsp&nbsp&nbsp&nbsp
                           <a href="recipe.php?recipe_id=' . $row[$i]['recipe_id'] . '">' . $row[$i]['recipe_title'] . '</a></h4>
                           <div class="span3">Author: ' . $row[$i]['user_name'] . '</div>
                           <div class="span3">Created on: ' . date('m-d-Y', strtotime($row[$i]['recipe_date'])) . '</div>
                           <div class="span3">
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
                    for ($j = 0; $j < $row[$i]['recipe_pt']; $j++) {
                        print '<img src="http://sfsuswe.com/~f12g02/dine-a-mite/clock.png" alt="Preparation time"
                                    id="recipe-icon">';
                    }
                    print '</a></div></div><div class="span3">
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
                                     5 icons denote above 12 dollars">';
                    // Print icons for "Cost"
                    for ($j = 0; $j < $row[$i]['recipe_cost']; $j++) {
                        print '<img src="http://sfsuswe.com/~f12g02/dine-a-mite/money.png" alt="Cost"
                                    id="recipe-icon">';
                    }
                    print '</a></div></div>';
                    // Print icons for "Recipe rating"
                    if ($row[$i]['recipe_rating'] != NULL) {
                        print '<div class="span3" id="category-padding">
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
                        for ($j = 0; $j < $row[$i]['recipe_rating']; $j++) {
                            print '<img src="http://sfsuswe.com/~f12g02/dine-a-mite/star.png" alt="Recipe rating"
                                        id="recipe-icon">';
                        }
                        print '</a></div></div>';
                    }
                    print '<div class="span6" id="category-padding">Recipe description: ' . $recipe_desc . '
                           <a href="recipe.php?recipe_id=' . $row[$i]['recipe_id'] . '">more</a></div>
                           </div></div></div><div class="row-fluid"><hr></div>';
                }
                // Add pagination to page
                print '<div class="pagination">
                       <ul>';
                if (isset($_GET['general_cat_id'])) {
                    for ($i = 0; $i < ceil(mysql_num_rows($result) / 6); $i++) {
                        if ($i == $_GET['page_num']) {
                            print '<li class="active"><a href="category.php?general_cat_id=' . $_GET['general_cat_id'] . '&page_num=' . $i . '">' . ($i + 1) . '</a></li>';
                        } else {
                            print '<li><a href="category.php?general_cat_id=' . $_GET['general_cat_id'] . '&page_num=' . $i . '">' . ($i + 1) . '</a></li>';
                        }
                    }
                    print '</ul></div>';
                } else if (isset($_GET['cuisine_cat_id'])) {
                    for ($i = 0; $i < ceil(mysql_num_rows($result) / 6); $i++) {
                        if ($i == $_GET['page_num']) {
                            print '<li class="active"><a href="category.php?cuisine_cat_id=' . $_GET['cuisine_cat_id'] . '&page_num=' . $i . '">' . ($i + 1) . '</a></li>';
                        } else {
                            print '<li><a href="category.php?cuisine_cat_id=' . $_GET['cuisine_cat_id'] . '&page_num=' . $i . '">' . ($i + 1) . '</a></li>';
                        }
                    }
                    print '</ul></div>';
                } else if (isset($_GET['student_cat_id'])) {
                    for ($i = 0; $i < ceil(mysql_num_rows($result) / 6); $i++) {
                        if ($i == $_GET['page_num']) {
                            print '<li class="active"><a href="category.php?student_cat_id=' . $_GET['student_cat_id'] . '&page_num=' . $i . '">' . ($i + 1) . '</a></li>';
                        } else {
                            print '<li><a href="category.php?student_cat_id=' . $_GET['student_cat_id'] . '&page_num=' . $i . '">' . ($i + 1) . '</a></li>';
                        }
                    }
                    print '</ul></div>';
                }
            }
        }
    }
}
include_once 'Includes/footer.php';
?>