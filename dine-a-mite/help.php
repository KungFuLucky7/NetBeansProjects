<?php

/**
 * This is for the "Help Guide" page
 */
include_once 'Includes/db.php';
session_start();
$_SESSION['curr_page'] = 'Help';
include_once 'Includes/header.php';
print '<div class="hero-unit">
       <h4>Help Guide</h4>
       <hr>
       <h5 class="text-info">Icons usages:</h5>
       <h6>Preparation time:</h6><img src="http://sfsuswe.com/~f12g02/dine-a-mite/clock.png" alt="Preparation time"
                                      id="recipe-icon">
       <p>1 icon denotes 1-3 mins<br />
          2 icons denote 3-6 mins<br />
          3 icons denote 6-9 mins<br />
          4 icons denote 9-12 mins<br />
          5 icons denote above 12 mins</p>                     
          
       <h6>Cost:</h6><img src="http://sfsuswe.com/~f12g02/dine-a-mite/money.png" alt="Cost"
                          id="recipe-icon">
       <p>1 icon denotes 1-3 dollars<br />
          2 icons denote 3-6 dollars<br />
          3 icons denote 6-9 dollars<br />
          4 icons denote 9-12 dollars<br />
          5 icons denote above 12 dollars</p>
          
       <h6>Recipe rating:</h6><img src="http://sfsuswe.com/~f12g02/dine-a-mite/star.png" alt="Recipe rating"
                                   id="recipe-icon">
       <p>1 icon denotes &#60;Poor&#62;<br />  
          2 icons denote &#60;Mediocre&#62;<br />
          3 icons denote &#60;Good&#62;<br />
          4 icons denote &#60;Exellent&#62;<br />
          5 icons denote &#60;Top&#62;</p><br />
                         
       </div>';
include_once 'Includes/footer.php';
?>
