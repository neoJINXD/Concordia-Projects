let fails = 0;
let userInput1 = [];
let userInput2 = [];

function checkNeg(inp) {
  if (inp == -1) {
    return true;
  } else {
    return false;
  }
}

while (fails < 5) {
  let tempInput = window.prompt("Enter 2 strings seperated by a space");
  let tempInputArray = tempInput.split(" ");
  console.log(tempInputArray);
  for (let i in tempInputArray) {
    if (checkNeg(i)) {
      fails = 5; //Gets out of while loop
    }
  }
  if (tempInputArray.length > 2 || tempInputArray < 2) {
    fail++;
  } else {
    userInput1.push(tempInputArray[0]);
    userInput2.push(tempInputArray[1]);
  }
}

let toWrite = document.getElementById("statsOut");
// toWrite.
