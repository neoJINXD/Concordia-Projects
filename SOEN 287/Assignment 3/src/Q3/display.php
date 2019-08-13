<?php
    session_start();

?>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>Question 3</title>
  </head>
  <body>
    <form class="ctrl-form" action="<?php $_PHP_SELF ?>" method="POST">
      <label>
        What to style:
        <select name="select-box" id="select-box">
          <option value="ctrl1">Control 1</option>
          <option value="ctrl2">Control 2</option>
          <option value="ctrl3">Control 3</option>
          <option value="ctrl4">Control 4</option>
        </select>
        <br />
      </label>
      <br />
      <label> 
        Font Size: 
        <input name="font" id="font" type="range" min="12" max="20" value="12" /><br />
      </label>
      <br />
      <label>
        Text Color: 
        <input id="color-picker" name="color-picker" type="color" /><br /> 
      </label>
      <br />

      <label id="visibility">
        <input id="vis-check" name="vis-check[]" type="checkbox" value='hidden'/>Hidden?<br/>
      </label>
      <br/>
      <input type="submit" value="Update" />
    </form>

    <br />
    <br />
    <br />

    <div class="control-box">
      <div id="ctrl1">
        Control 1<br />
      </div>
      <div id="ctrl2">
        Control 2<br />
      </div>
      <div id="ctrl3">
        Control 3<br />
      </div>  
      <div id="ctrl4">  
        Control 4<br />
      </div>
    </div>
    <br />

    <div id="style-control">
      <?php
        include('./process.php');
      ?>
    </div>
  </body>
</html>
