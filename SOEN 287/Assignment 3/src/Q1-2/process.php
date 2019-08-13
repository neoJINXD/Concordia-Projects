<!-- 
You must use “POST” method to submit the user data 
and you mustuse appropriate input type 
and also use placeholders. 

A file named process.php
will collect the data from registration page. -->
<?php

$success = true;
$STORED_USER = "users.txt";
$tocheck = true;

if(isset($_POST["fullName"]) && isset($_POST["email"]) && isset($_POST["username"]) && isset($_POST["pass1"]) && isset($_POST["pass2"])){

    if (!file_exists($STORED_USER)){
        $createfile = fopen($STORED_USER, "w");
        fclose($createfile);
        $tocheck = false;
    }


    if($tocheck){
        $fileread = fopen($STORED_USER, 'r');
        while(!feof($fileread)){
            $line = fgets($fileread);
            $linearr = explode(" ", $line);
            if($linearr[0] == $_POST["username"]){
                $success = false;
                break;
            } 
        }
    }
    $file = fopen($STORED_USER, "a+");
    if ($success){
        fwrite($file, $_POST["username"].' ');
        fwrite($file, $_POST["fullName"].' ');
        fwrite($file, $_POST["email"].' ');
        fwrite($file, $_POST["pass1"].' ');
        fwrite($file, $_POST["pass2"]."\n");
        print('User has been registered');


        //Sets up session
        session_start();
        $_SESSION['username'] = $_POST['username'];
        
    } else {
        print('The user you have specified already exists');
    }

    fclose($file);


}


//CHECK IF USER EXISTS AND HANDLE FILES

?>



