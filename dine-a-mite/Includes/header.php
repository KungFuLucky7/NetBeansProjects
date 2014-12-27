<!DOCTYPE html>
<!-- This is the header for every web page -->
<html lang="en"><head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <title>Welcome to Dine-A-Mite</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <!-- Le styles -->
        <link href="bootstrap_files/css/bootstrap.min.css" rel="stylesheet">
        <link href="bootstrap_files/css/bootstrap-responsive.css" rel="stylesheet">
        <link href="bootstrap_files/css/bootstrap.css" rel="stylesheet">
        <link href="star-rating/jquery.rating.css" rel="stylesheet">
        <link rel="stylesheet" href="jqueryprintpage/css/jquery.printpage.css" type="text/css" media="screen" />

        <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
        <!--[if lt IE 9]>
          <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->

        <!-- Le fav and touch icons -->
        <link rel="shortcut icon" href="http://twitter.github.com/bootstrap/assets/ico/favicon.ico">
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="http://twitter.github.com/bootstrap/assets/ico/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="http://twitter.github.com/bootstrap/assets/ico/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="http://twitter.github.com/bootstrap/assets/ico/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="http://twitter.github.com/bootstrap/assets/ico/apple-touch-icon-57-precomposed.png">
        <script type="text/javascript">
            function openKCFinder(field) {
                window.KCFinder = {
                    callBack: function(url) {
                        field.value = url;
                        window.KCFinder = null;
                    }
                };
                window.open('./kcfinder/browse.php?type=files&dir=files/public', 'kcfinder_textbox',
                'status=0, toolbar=0, location=0, menubar=0, directories=0, ' +
                    'resizable=1, scrollbars=0, width=800, height=600'
            );
            }
        </script>
    </head>
    <body>
        <header>
            <div class="banner">
                <a id="header-link" href="index.php"></a>
                <ul id="login-pane" class="nav nav-list pull-right">
                    <!--<li class="nav-header">List header</li>-->
                    <?php
                    ob_start();
                    if (isset($_SESSION['signed_in']) && $_SESSION['signed_in'] == true) {
                        print '<br /><form class="navbar-form" method="post" action="">
                               <div class="span3"><h4>Logged in as:</h4></div>
                               <div class="span3" id="login-name"><a href="profile.php" class="navbar-link"><h4>' . $_SESSION['user_name'] . '</h4></a></div>                           
                               <div class="span3" id="#submit-center">
                               <a class="btn" href = "logout.php?redirect_url=' . $_SERVER["REQUEST_URI"] . '">Sign out</a>&nbsp&nbsp&nbsp&nbsp&nbsp
                               <a class="btn" href = "profile.php">Profile</a></div>
                               </form>';
                    } else {
                        if ($_SERVER['REQUEST_METHOD'] != 'POST' || !isset($_POST['login'])) {
                            // Display the form if it isn't posted
                            // action="" will post the form to the current page
                            print '<form class="navbar-form" method="post" action="">
                                   <div class="control-group">';
                            if (isset($_COOKIE["remember"]) && $_COOKIE["remember"] && $_COOKIE["user_name"] != NULL) {
                                print '<input class="span2" placeholder="Username" type="text" name="user_name" value="' . $_COOKIE["user_name"] . '"><br />
                                       <input class="span2" placeholder="Password" type="password" name="user_pass"><br />
                                       <div class="span3" id="remember">
                                       <label class="checkbox pull-left"><input type="checkbox" name="checkbox" checked="yes">Remember me</label></div>';
                            } else {
                                print '<input class="span2" placeholder="Username" type="text" name="user_name"><br />
                                       <input class="span2" placeholder="Password" type="password" name="user_pass"><br />
                                       <div class="span3" id="remember">
                                       <label class="checkbox pull-left"><input type="checkbox" name="checkbox" checked="yes">Remember me</label></div>';
                            }
                            print '<button type="submit" name="login" class="btn">Sign in</button>&nbsp&nbsp&nbsp&nbsp&nbsp
                                   <a class="btn" href = "register.php">Register</a></div>
                                   </form>';
                        } else if (isset($_POST['login'])) {
                            // Process the data if the form has been posted for user login
                            // Validate and submit inputs and return the according response
                            $errors = array();
                            if ($_POST['user_name'] == NULL) {
                                $errors[] = 'The "Username" field is required.';
                            }
                            if ($_POST['user_pass'] == NULL) {
                                $errors[] = 'The "Password" field is required.';
                            } else {
                                // Use mysql_real_escape_string() to escape special characters in a string for use in an SQL statement
                                // Use sha1() function to encrypt the password
                                $sql = "SELECT user_id, user_name, user_level
                                        FROM users
                                        WHERE user_name = '" . mysql_real_escape_string($_POST['user_name']) . "'
                                        AND user_pass = '" . sha1($_POST['user_pass']) . "'";
                                $result = mysql_query($sql);
                                if (!$result) {
                                    // Display the error with the database access
                                    $errors[] = 'Sign-in Error: Please try again later.';
                                    // Debug code
                                    //print mysql_error();
                                } else if (mysql_num_rows($result) == 0) {
                                    // Print an error message if the query returns an empty result set from wrong credentials
                                    $errors[] = 'You have entered the wrong "Username" or "Password". Please try again!';
                                }
                            }
                            if (!empty($errors)) {
                                print '<form class="navbar-form" method="post" action="">
                                       <div class="control-group">';
                                if (isset($_COOKIE["remember"]) && $_COOKIE["remember"] && $_COOKIE["user_name"] != NULL) {
                                    print '<input class="span2" placeholder="Username" type="text" name="user_name" value="' . $_COOKIE["user_name"] . '"><br />
                                           <input class="span2" placeholder="Password" type="password" name="user_pass"><br />
                                           <div class="span2" id="remember">
                                           <label class="checkbox pull-left"><input type="checkbox" name="checkbox" checked="yes">Remember me</label></div>';
                                } else {
                                    print '<input class="span2" placeholder="Username" type="text" name="user_name"><br />
                                           <input class="span2" placeholder="Password" type="password" name="user_pass"><br />
                                           <div class="span2" id="remember">
                                           <label class="checkbox pull-left"><input type="checkbox" name="checkbox" checked="yes">Remember me</label></div>';
                                }
                                print '<button type="submit" name="login" class="btn">Sign in</button>&nbsp&nbsp&nbsp&nbsp&nbsp
                                       <a class="btn" href = "register.php">Register</a></div>
                                       </form>';
                            } else {
                                // The form has been submitted without errors, enter the values in the database
                                // Set the $_SESSION['signed_in'] variable to TRUE
                                $_SESSION['signed_in'] = true;
                                // Store the user_id, user_name and user_level values in the $_SESSION for other pages
                                while ($row = mysql_fetch_assoc($result)) {
                                    $_SESSION['user_id'] = $row['user_id'];
                                    $_SESSION['user_name'] = $row['user_name'];
                                    $_SESSION['user_level'] = $row['user_level'];
                                }
                                if (isset($_POST['checkbox'])) {
                                    setcookie("remember", TRUE);
                                    setcookie("user_name", $_POST['user_name']);
                                } else {
                                    setcookie("remember", FALSE);
                                    unset($_COOKIE['user_name']);
                                }
                                header('Location: http://sfsuswe.com' . $_SERVER["REQUEST_URI"]);
                            }
                        }
                    }
                    ?>
                </ul>
                <div class="row" id="search">
                    <form class="form-search" method="get" action="search.php" accept-charset="UTF-8">
                        <div class="input-append">
                            <input type="text" placeholder="Search for recipes" class="span4 search-query" name="query" autocomplete="on">
                            <button class="btn btn-success" type="submit">Search&nbsp&nbsp<i class="icon-search"></i></button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </header>
    <div class="navbar navbar-inverse">
        <div class="navbar-inner">
            <div class="container-fluid">
                <div class="nav-collapse collapse">
                    <a class="brand" href="index.php">Dine-A-Mite</a>
                    <ul class="nav">
                        <?php
                        if (isset($_SESSION['curr_page']) && $_SESSION['curr_page'] != 'Admin') {
                            if ($_SESSION['curr_page'] == 'Home') {
                                print '<li class="active"><a href="index.php">Home</a></li>
                                       <li><a href="create_recipe.php">Upload</a></li>
                                       <li><a href="about.php">About</a></li>
                                       <li><a href="contact.php">Contact</a></li>
                                       <li><a href="help.php">Help</a></li>';
                            } else if ($_SESSION['curr_page'] == 'Upload') {
                                print '<li><a href="index.php">Home</a></li>
                                       <li class="active"><a href="create_recipe.php">Upload</a></li>
                                       <li><a href="about.php">About</a></li>
                                       <li><a href="contact.php">Contact</a></li>
                                       <li><a href="help.php">Help</a></li>';
                            } else if ($_SESSION['curr_page'] == 'About') {
                                print '<li><a href="index.php">Home</a></li>
                                       <li><a href="create_recipe.php">Upload</a></li>
                                       <li class="active"><a href="about.php">About</a></li>
                                       <li><a href="contact.php">Contact</a></li>
                                       <li><a href="help.php">Help</a></li>';
                            } else if ($_SESSION['curr_page'] == 'Contact') {
                                print '<li><a href="index.php">Home</a></li>
                                       <li><a href="create_recipe.php">Upload</a></li>
                                       <li><a href="about.php">About</a></li>
                                       <li class="active"><a href="contact.php">Contact</a></li>
                                       <li><a href="help.php">Help</a></li>';
                            } else if ($_SESSION['curr_page'] == 'Help') {
                                print '<li><a href="index.php">Home</a></li>
                                       <li><a href="create_recipe.php">Upload</a></li>
                                       <li><a href="about.php">About</a></li>
                                       <li><a href="contact.php">Contact</a></li>
                                       <li class="active"><a href="help.php">Help</a></li>';
                            }
                        } else {
                            print '<li><a href="index.php">Home</a></li>
                                   <li><a href="create_recipe.php">Upload</a></li>
                                   <li><a href="about.php">About</a></li>
                                   <li><a href="contact.php">Contact</a></li>
                                   <li><a href="help.php">Help</a></li>';
                        }
                        // Display the Admin menu if user_level is 1
                        if (isset($_SESSION['user_level']) && $_SESSION['user_level'] == 1) {
                            if (isset($_SESSION['curr_page']) && $_SESSION['curr_page'] == 'Admin') {
                                print '<li class="active dropdown">';
                            } else {
                                print '<li class="dropdown">';
                            }
                            print '<a href="#" class="dropdown-toggle" data-toggle="dropdown">Admin <b class="caret"></b></a>
                                   <ul class="dropdown-menu">
                                   <li><a href="create_cat.php">Create a category</a></li>
                                   <li class="divider"></li>
                                   <li><a href="http://sfsuswe.com/phpmyadmin">phpMyAdmin</a></li>
                                   </ul></li>';
                        }
                        print '</ul>';
                        unset($_SESSION['curr_page']);
                        ?>
                </div><!--/.nav-collapse -->
            </div>
        </div>
    </div>

    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span2">
                <div class="well sidebar-nav">
                    <ul class="nav nav-list">
                        <?php

                        // Function for sorting the categories
                        function custom_sort(&$array, $key) {
                            $sorter = array();
                            $ret = array();
                            reset($array);
                            foreach ($array as $i => $va) {
                                $sorter[$i] = $va[$key];
                            }
                            asort($sorter);
                            foreach ($sorter as $i => $va) {
                                $ret[] = $array[$i];
                            }
                            $array = $ret;
                        }

                        // List the indented sidebar links
                        $sql = "SELECT cat_id, cat_name FROM student_categories";
                        $result = mysql_query($sql);
                        print '<h4><b>Browse:</b></h4>
                               <li class="nav-header">Student</li>';
                        if (mysql_num_rows($result) == 0) {
                            print 'No categories found!';
                        } else {
                            for ($i = 0; $i < 3; $i++) {
                                $row = mysql_fetch_assoc($result);
                                if (isset($_GET['student_cat_id']) && $_GET['student_cat_id'] === $row['cat_id']) {
                                    print '<li class="active"><a href="category.php?student_cat_id=' . $row['cat_id'] . '">' . $row['cat_name'] . '</a></li>';
                                } else {
                                    print '<li><a href="category.php?student_cat_id=' . $row['cat_id'] . '">' . $row['cat_name'] . '</a></li>';
                                }
                            }
                        }
                        print '<li class="nav-header">General</li>';
                        $sql = "SELECT cat_id, cat_name FROM general_categories";
                        $result = mysql_query($sql);
                        if (!$result) {
                            print 'Categories display error!';
                        } else {
                            if (mysql_num_rows($result) == 0) {
                                print 'No categories found!';
                            } else {
                                for ($i = 0; $i < 3; $i++) {
                                    $row = mysql_fetch_assoc($result);
                                    if (isset($_GET['general_cat_id']) && $_GET['general_cat_id'] === $row['cat_id']) {
                                        print '<li class="active"><a href="category.php?general_cat_id=' . $row['cat_id'] . '">' . $row['cat_name'] . '</a></li>';
                                    } else {
                                        print '<li><a href="category.php?general_cat_id=' . $row['cat_id'] . '">' . $row['cat_name'] . '</a></li>';
                                    }
                                }
                                print '<li class="dropdown-submenu"><a href="#">More</a>
                                       <ul class="dropdown-menu" role="menu">';
                                for ($i = 3; $i < mysql_num_rows($result); $i++) {
                                    $row = mysql_fetch_assoc($result);
                                    if (isset($_GET['general_cat_id']) && $_GET['general_cat_id'] === $row['cat_id']) {
                                        print '<li class="active"><a tabindex="-1" href="category.php?general_cat_id=' . $row['cat_id'] . '">' . $row['cat_name'] . '</a></li>';
                                    } else {
                                        print '<li><a tabindex="-1" href="category.php?general_cat_id=' . $row['cat_id'] . '">' . $row['cat_name'] . '</a></li>';
                                    }
                                }
                                print '</ul></li>';
                            }
                            print '<li class="nav-header">Cuisine</li>';
                            $sql = "SELECT cat_id, cat_name FROM cuisine_categories";
                            $result = mysql_query($sql);
                            if (!$result) {
                                print 'Categories display error!';
                            } else {
                                if (mysql_num_rows($result) == 0) {
                                    print 'No categories found!';
                                } else {
                                    $row = Array();
                                    for ($i = 0; $i < mysql_num_rows($result); $i++) {
                                        $row[] = mysql_fetch_assoc($result);
                                    }
                                    custom_sort($row, 'cat_name');
                                    for ($i = 0; $i < mysql_num_rows($result); $i++) {
                                        if ($row[$i]['cat_name'] == 'Vegetarian') {
                                            array_unshift($row, $row[$i]);
                                        }
                                    }
                                    for ($i = 0; $i < 3; $i++) {
                                        if (isset($_GET['cuisine_cat_id']) && $_GET['cuisine_cat_id'] === $row[$i]['cat_id']) {
                                            print '<li class="active"><a href="category.php?cuisine_cat_id=' . $row[$i]['cat_id'] . '">' . $row[$i]['cat_name'] . '</a></li>';
                                        } else {
                                            print '<li><a href="category.php?cuisine_cat_id=' . $row[$i]['cat_id'] . '">' . $row[$i]['cat_name'] . '</a></li>';
                                        }
                                    }
                                    print '<li class="dropdown-submenu"><a href="#">More</a>
                                           <ul class="dropdown-menu" role="menu">';
                                    for ($i = 3; $i < mysql_num_rows($result); $i++) {
                                        if (isset($_GET['cuisine_cat_id']) && $_GET['cuisine_cat_id'] === $row[$i]['cat_id']) {
                                            print '<li class="active"><a tabindex="-1" href="category.php?cuisine_cat_id=' . $row[$i]['cat_id'] . '">' . $row[$i]['cat_name'] . '</a></li>';
                                        } else {
                                            print '<li><a tabindex="-1" href="category.php?cuisine_cat_id=' . $row[$i]['cat_id'] . '">' . $row[$i]['cat_name'] . '</a></li>';
                                        }
                                    }
                                    print '</ul></li>';
                                }
                            }
                        }
                        ?>
                    </ul>
                </div><!--/.well-->
            </div><!--/span-->
            <div class = "span10">
                <?php
                // Generate a list of errors
                if (!empty($errors)) {
                    print '<div class="alert alert-error">
                           <button type="button" class="close" data-dismiss="alert">×</button>
                           Error: Some fields are not entered correctly!<ul>';
                    foreach ($errors as $key => $value) {
                        print '<li>' . $value . '</li>';
                    }
                    print '</ul></div>';
                }
                ob_flush();
                ?>