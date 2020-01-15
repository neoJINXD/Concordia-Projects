window.onload = () => {
  let fails = 0;
  let userInput1 = [];
  let userInput2 = [];

  const checkAnagram = (first, second) => {
    if (first.length != second.length) {
      return false;
    }
    firstChar = first.split("");
    for (let i in second) {
      if (firstChar.includes(second.charAt(i))) {
        continue;
      } else {
        return false;
      }
    }
    return true;
  };

  const checkNumber = str => {
    return /\d/.test(str);
  };

  while (fails < 5) {
    let tempInput = window.prompt(
      "Enter 2 strings seperated by a space, -1 to stop"
    );
    let tempInputArray = tempInput.split(" ");
    console.log(tempInputArray);

    let i = tempInputArray[0];
    console.log(i);
    if (i == -1) {
      //Gets out of while loop
      break;
    }

    if (
      tempInputArray.length > 2 ||
      tempInputArray.length < 2 ||
      checkNumber(tempInputArray[0]) ||
      checkNumber(tempInputArray[1])
    ) {
      fails++;
    } else {
      userInput1.push(tempInputArray[0]);
      userInput2.push(tempInputArray[1]);
    }
  }

  if (fails >= 5) {
    alert("Too many incorrect values where entered, shutting down...");
  } else {
    for (let i = 0; i < userInput1.length; i++) {
      let toAdd = document.createElement("div");
      if (checkAnagram(userInput1[i], userInput2[i])) {
        toAdd.innerHTML = `${userInput1[i]} is an anagram of ${userInput2[i]}`;
      } else {
        toAdd.innerHTML = `${userInput1[i]} is not an anagram of ${
          userInput2[i]
        }`;
      }
      document.getElementById("statsOut").appendChild(toAdd);
    }
  }
};
