<?php

/**
 * This is the register page for the users
 */
include_once 'Includes/db.php';
session_start();
include_once 'Includes/header.php';
print '<div class="well well-mini">
       <h4>User Register Form</h4></div>';
if ($_SERVER['REQUEST_METHOD'] != 'POST' || isset($_POST['login'])) {
    // Display the form if it hasn't been posted yet
    print '<div class="alert" id="required-alert">
           <strong><strong class="text-error">* </strong>indicates required field</strong></div>
           <form method="post" action="" class="form-horizontal">
           <div class="control-group">
           <label class="control-label"><strong class="text-error">* </strong>Username:</label>
           <div class="controls">
           <input type="text" name="user_name"></div></div>
           <div class="control-group">
           <label class="control-label"><strong class="text-error">* </strong>Password:</label>
           <div class="controls">
           <input type="password" name="user_pass"></div></div>
           <div class="control-group">
           <label class="control-label"><strong class="text-error">* </strong>Re-enter Password:</label>
           <div class="controls">
           <input type="password" name="user_pass_check"></div></div>
           <div class="control-group">
           <label class="control-label">E-mail:</label>
           <div class="controls">
           <input type="email" name="user_email"></div></div>
           <div class="control-group">
           <h4>Terms & Privacy:</h4>
           <textarea rows="6" class="field span7" readonly>
           Terms of Service:
           Thank you for your interest in Dine-A-Mite! This Terms of Service
           document ("Agreement") describes the terms and conditions of your use
           of any online service provided by the Dine-A-Mite website, including
           your participation in sharing recipes, providing ratings for existing
           recipes and preparation of the recipes featured on Dine-A-Mite.
           Please read this agreement carefully.By using Dine-A-Mite, you are
           agreeing to comply with the terms of this Agreement.If for any reason
           you do not agree with any part of this document, you must discontinue
           your use of Dine-A-Mite.Please also be aware that Dine-A-Mite may
           revise this Agreement at any point.All registered users will be
           advised via their email address on record that such changes have
           ccurred.Your use of Dine-A-Mite must comply with the terms and
           conditions in effect at the time of your use.
           
           Privacy Statement:
           You agree to provide Dine-A-Mite with accurate information at
           the time of registration.Dine-A-Mite, in turn, guarantees that your
           personal information will be kept confidential and protected from
           unauthorized access by non-Dine-A-Mite related parties at all times.
           You agree that any content uploaded to Dine-A-Mite by you
           will contain no copyrighted or otherwise legally restricted materials,
           including all images and text provided by you.Your recipe information,
           in its entirety, is thus available through Dine-A-Mite for public use
           and reproduction, as no copyrighted materials are allowed on
           Dine-A-Mite.Any information provided by you to Dine-A-Mite shall be
           subject to review by Dine-A-Mite for compliance with copyright
           restrictions and for removal of any questionable or offensive
           material.Dine-A-Mite reserves the right to refuse the inclusion of any
           material which is determined to be offensive, abusive or
           illegal.Failure to comply with Dine-A-Mite content policies on your
           part means that you agree to accept all legal and financial
           responsibilities which may result from such noncompliance.
           Any questions or concerns about any of the terms or conditions listed
           here should be directed to Dine-A-Mite via our contact page.
           </textarea></div>
           <div class="control-group">
           <label class="control-label"><strong class="text-error">* </strong>Agree?</label>
           <div class="controls">
           <input type="radio" name="terms" value="yes"/>&nbspYes&nbsp&nbsp&nbsp
           <input type="radio" name="terms" value="no" checked/>&nbspNo
           </div></div>
           <div class = "control-group">
           <div class="controls">
 	   <input type="submit" value="Register" class="btn btn-primary btn-large"></div></div>
 	   </form>';
} else {
    // Process the values if the form has been posted
    $errors = array();
    if (isset($_POST['user_name'])) {
        // Validate the user name
        if ($_POST['user_name'] == NULL) {
            $errors[] = 'The "Username" field is required.';
        } else {
            if (!ctype_alnum($_POST['user_name'])) {
                $errors[] = 'The username can only contain letters and digits.';
            }
            if (strlen($_POST['user_name']) > 30) {
                $errors[] = 'The username cannot be longer than 30 characters.';
            }
        }
    }
    if (isset($_POST['user_pass']) || isset($_POST['user_pass_check'])) {
        if ($_POST['user_pass'] == NULL) {
            $errors[] = 'The "Password" field is required.';
        }
        if ($_POST['user_pass_check'] == NULL) {
            $errors[] = 'The "Re-enter Password" field is required.';
        } else if ($_POST['user_pass'] != $_POST['user_pass_check']) {
            $errors[] = 'The two passwords don\'t match.';
        }
    }
    if ($_POST['terms'] == 'no') {
        $errors[] = 'You have to agree with our terms & privacy.';
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
               <label class="control-label"><strong class="text-error">* </strong>Username:</label>
               <div class="controls">
               <input type="text" name="user_name"></div></div>
               <div class="control-group">
               <label class="control-label"><strong class="text-error">* </strong>Password:</label>
               <div class="controls">
               <input type="password" name="user_pass"></div></div>
               <div class="control-group">
               <label class="control-label"><strong class="text-error">* </strong>Re-enter Password:</label>
               <div class="controls">
               <input type="password" name="user_pass_check"></div></div>
               <div class="control-group">
               <label class="control-label">E-mail:</label>
               <div class="controls">
               <input type="email" name="user_email"></div></div>
               <div class="control-group">
               <h4>Terms & Privacy:</h4>
               <textarea rows="6" class="field span7" readonly>
               Terms of Service:
               Thank you for your interest in Dine-A-Mite! This Terms of Service
               document ("Agreement") describes the terms and conditions of your use
               of any online service provided by the Dine-A-Mite website, including
               your participation in sharing recipes, providing ratings for existing
               recipes and preparation of the recipes featured on Dine-A-Mite.
               Please read this agreement carefully.By using Dine-A-Mite, you are
               agreeing to comply with the terms of this Agreement.If for any reason
               you do not agree with any part of this document, you must discontinue
               your use of Dine-A-Mite.Please also be aware that Dine-A-Mite may
               revise this Agreement at any point.All registered users will be
               advised via their email address on record that such changes have
               ccurred.Your use of Dine-A-Mite must comply with the terms and
               conditions in effect at the time of your use.

               Privacy Statement:
               You agree to provide Dine-A-Mite with accurate information at
               the time of registration.Dine-A-Mite, in turn, guarantees that your
               personal information will be kept confidential and protected from
               unauthorized access by non-Dine-A-Mite related parties at all times.
               You agree that any content uploaded to Dine-A-Mite by you
               will contain no copyrighted or otherwise legally restricted materials,
               including all images and text provided by you.Your recipe information,
               in its entirety, is thus available through Dine-A-Mite for public use
               and reproduction, as no copyrighted materials are allowed on
               Dine-A-Mite.Any information provided by you to Dine-A-Mite shall be
               subject to review by Dine-A-Mite for compliance with copyright
               restrictions and for removal of any questionable or offensive
               material.Dine-A-Mite reserves the right to refuse the inclusion of any
               material which is determined to be offensive, abusive or
               illegal.Failure to comply with Dine-A-Mite content policies on your
               part means that you agree to accept all legal and financial
               responsibilities which may result from such noncompliance.
               Any questions or concerns about any of the terms or conditions listed
               here should be directed to Dine-A-Mite via our contact page.
               </textarea></div>
               <div class="control-group">
               <label class="control-label"><strong class="text-error">* </strong>Agree?</label>
               <div class="controls">
               <input type="radio" name="terms" value="yes"/>&nbspYes&nbsp&nbsp&nbsp
               <input type="radio" name="terms" value="no" checked/>&nbspNo
               </div></div>
               <div class = "control-group">
               <div class="controls">
               <input type="submit" value="Register" class="btn btn-primary btn-large"></div></div>
               </form>';
    } else {
        // Submit the form in the database if it has been posted without errors
        $sql = "INSERT INTO users (user_name, user_pass, user_email, user_date, user_level)
                VALUES ('" . mysql_real_escape_string($_POST['user_name']) . "',
					   '" . sha1($_POST['user_pass']) . "',
					   '" . mysql_real_escape_string($_POST['user_email']) . "',
						NOW(),
						0)";
        $result = mysql_query($sql);
        if (!$result) {
            // Display the error message
            print '<div class="alert alert-error">
                   <button type="button" class="close" data-dismiss="alert">×</button>
                   Something went wrong while registering. Please try again later.';
            // Debug code
            //print mysql_error();
            print '</div>';
            print '<form method="post" action="" class="form-horizontal">
                   <div class="control-group">
                   <label class="control-label">Username:</label>
                   <div class="controls">
                   <input type="text" name="user_name"></div></div>
                   <div class="control-group">
                   <label class="control-label">Password:</label>
                   <div class="controls">
                   <input type="password" name="user_pass"></div></div>
                   <div class="control-group">
                   <label class="control-label">Re-enter Password:</label>
                   <div class="controls">
                   <input type="password" name="user_pass_check"></div></div>
                   <div class="control-group">
                   <label class="control-label">E-mail:</label>
                   <div class="controls">
                   <input type="email" name="user_email"></div></div>
                   <div class = "control-group">
                   <div class="controls">
                   <input type="submit" value="Register" class="btn btn-primary btn-large"></div></div>
                   </form>';
        } else {
            print '<h4 class="text-success">Successfully registered. You can now sign in and start uploading recipes!</h4>';
        }
    }
}
include_once 'Includes/footer.php';
?>