<!DOCTYPE html>
<html lang="en">
  <head>

    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <link href='./styles.css' type='text/css' rel='stylesheet'</link>
    <title>Question 1-2</title>

  </head>

  <body>

    <h3>Sign up today!</h3>
    <form action="<?php $_PHP_SELF ?>" method="POST">
      <label>
        Full Name<br />
        <input id="fullName" type="text" name="fullName" placeholder="Full Name" onfocusout="validateName()" required />
      </label>
      
      <br />
      <label>
        Email<br />
        <input id='email' type="text" name="email" placeholder="Email" onfocusout='validateEmail()' required />
      </label>

      <br />
      <label>
        Username<br />
        <input id='username' type="text" name="username" placeholder="Username" onfocusout='validateUser()' required />
      </label>

      <br />
      <label>
        Password<br />
        <input id='pass1' type="password" name="pass1" onfocusout='validatePass()' required />
      </label>

      <br />
      <label>
        Verify Password<br />
        <input id='pass2' type="password" name="pass2" onfocusout='validatePass2()' required />
      </label>

      <br />
      <label>
        <input type="checkbox" name="tos" required /> I agree to the
        <a
          target="_blank"
          href="https://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=1&cad=rja&uact=8&ved=2ahUKEwiS8-mClvHjAhVkg-AKHbJTDh8QyCkwAHoECA0QBQ&url=https%3A%2F%2Fwww.youtube.com%2Fwatch%3Fv%3DdQw4w9WgXcQ&usg=AOvVaw0aHtehaphMhOCAkCydRLZU"
        >
          Terms of Service
        </a>
      </label>
      <br />
      <div class="btn">
        <input type="submit" value="Sign Up" id="submit-btn" disabled="true"/>
      </div>
    </form>


    <div class="validation-error">
    
    
    </div>
    <?php
      include('./process.php');
      // echo 'works';
   
    ?>

    <script src='./validation.js' type='text/javascript'></script>
  </body>

</html>
<!-- MAKE SURE USER EXISTS ON SERVER -> Q2 -->
