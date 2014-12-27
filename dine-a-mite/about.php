<?php

/**
 * This is for the "About Us" page
 */
include_once 'Includes/db.php';
session_start();
$_SESSION['curr_page'] = 'About';
include_once 'Includes/header.php';
print '<div class="hero-unit">
       <h4>About Us</h4>
       <p>Dine-A-Mite is a food recipe web site created by a team of students from San Francisco State University with 
       the main focus on students. The central idea is for students to have a place where they can find the quickest,
       easiest and most convenient way of making a nutritious, economical and delicious home-made meal. 
       It is not intended to be a commercial web site. However, we welcome contributions from anyone 
       for sharing their own original and secret recipes. Your shared materials are promoted in our site 
       and your efforts are highly appreciated!</p>
       </div>';
include_once 'Includes/footer.php';
?>
