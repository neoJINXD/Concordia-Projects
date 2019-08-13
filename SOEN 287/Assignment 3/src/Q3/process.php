<!-- 
You must use “POST” method to submit the user data 
and you mustuse appropriate input type 
and also use placeholders. 

A file named process.php
will collect the data from registration page. -->
<?php

$visibility = 'visible';

if (isset($_POST['vis-check'])){
    $name = $_POST['vis-check'];

    $_SESSION['visibility'] = $name[0];
} else {
    $_SESSION['visibility'] = 'visible'; 
} 

if (isset($_POST['select-box'])){
    // echo $_POST['select-box'];
    $_SESSION['selected'] = $_POST['select-box'];
} else {
    $_SESSION['selected'] = "ctrl1";
}

if (isset($_POST['font'])){
    $_SESSION['font'] = $_POST['font'];
} else {
    $_SESSION['font'] = "12";
}


if (isset($_POST['color-picker'])){
    $_SESSION['color'] = $_POST['color-picker'];
} else {
    $_SESSION['color'] = "black";
}


$_SESSION['style'] = $_SESSION['style']."<style type='text/css' rel='stylesheet'>
label {
    font: normal normal 12pt \"Comic Sans MS\", serif;
}
.ctrl-form {
    padding: 20px;
    border: solid 1px black;
}

.control-box{
    
    font: normal normal 12pt \"Comic Sans MS\", serif;

}

#".$_SESSION['selected']."{
    visibility: ".$_SESSION['visibility'].";
    font-size: ".$_SESSION['font']."pt;
    color: ".$_SESSION['color'].";
}

</style>";

// foreach($_SESSION as $k => $v){
//     echo "key : $k value: $v <br/>";
// }


print($_SESSION['style']);





// $success = true;
// $STORED_USER = "users.txt";
// $tocheck = true;

// if(isset($_POST["fullName"]) && isset($_POST["email"]) && isset($_POST["username"]) && isset($_POST["pass1"]) && isset($_POST["pass2"])){

//     if (!file_exists($STORED_USER)){
//         $createfile = fopen($STORED_USER, "w");
//         fclose($createfile);
//         $tocheck = false;
//     }


//     if($tocheck){
//         $fileread = fopen($STORED_USER, 'r');
//         while(!feof($fileread)){
//             $line = fgets($fileread);
//             $linearr = explode(" ", $line);
//             if($linearr[0] == $_POST["username"]){
//                 $success = false;
//                 break;
//             } 
//         }
//     }
//     $file = fopen($STORED_USER, "a+");
//     if ($success){
//         fwrite($file, $_POST["username"].' ');
//         fwrite($file, $_POST["fullName"].' ');
//         fwrite($file, $_POST["email"].' ');
//         fwrite($file, $_POST["pass1"].' ');
//         fwrite($file, $_POST["pass2"]."\n");
//         print('User has been registered');


//         //Sets up session
//         session_start();
//         $_SESSION['username'] = $_POST['username'];
        
//     } else {
//         print('The user you have specified already exists');
//     }

//     fclose($file);


// }


//CHECK IF USER EXISTS AND HANDLE FILES

?>



