<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>Question 4</title>
  </head>
  <body>
    <div class="counter">
        <?php 

            
            if (isset($_COOKIE['counter'])){
                setcookie('counter', ++$_COOKIE['counter'], time()+60);
                print("Welcome back friend :)!<br/> This is visit number ".$_COOKIE['counter']);
            } else {
                setcookie('counter', 1, time()+60);
                print("Welcome first time user!");
            }
        
        ?>
           
    </div>
    <br/>
    <br/> 

    <p>Timer Left on Cookie:</p>
    <div class="timer">
      60 seconds left
    </div>

    <script>
      let end = Math.floor(new Date().getTime()/1000) + 60;
      setInterval(() => {
        let current = Math.floor(new Date().getTime()/1000);
        let timeLeft = end - current;
        document.querySelector('.timer').innerHTML = `${timeLeft} seconds left`;
        if (timeLeft < 0){
          document.querySelector('.timer').innerHTML = "COOKIE HAS EXPIRED";
        }
      }, 1000);
    </script>
    <br/>
    <br/> 

    <div>
      <form action="<?php $_PHP_SELF ?>" method="POST">
            <input type="submit" id="kill" name="kill" value="Refresh" >
      </form>
    </div>
  </body>
</html>
