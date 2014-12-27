<?php

/**
 * This is for the "Contact Us" page
 */
include_once 'Includes/db.php';
session_start();
$_SESSION['curr_page'] = 'Contact';
include_once 'Includes/header.php';
print '<div class="hero-unit">
         <h4>Contact Us</h4>
           <p>Please send your comments and concerns to:<br />
             <a href="f12g02list@sfsuswe.com">f12g02list@sfsuswe.com</a>       
           </p>
       </div>';
include_once 'Includes/footer.php';
?>
