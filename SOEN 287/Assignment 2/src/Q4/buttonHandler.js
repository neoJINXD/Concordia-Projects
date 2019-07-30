const TO_REDACT = ["soen287", "summer", "19"];
const REDACTED = ["XXXXXXX", "XXXXXX", "XX"];

var userInputArray;

const HideMe = () => {
  let userInput = document.getElementById("user-input").value;
  let toAdd = document.createElement("span");
  let hasChanged = false;
  toAdd.setAttribute("class", "output-text");

  userInputArray = userInput.split(" ");
  console.log(userInputArray);
  for (let i in userInputArray) {
    if (hasChanged) {
      hide(userInputArray[i], i);
    } else {
      hasChanged = hide(userInputArray[i], i);
    }
  }
  // console.log(`IS ANYTHING CHANGING? ${hasChanged}`);
  if (hasChanged) {
    toAdd.setAttribute("id", "redacted");
    toAdd.innerHTML = `${userInputArray.join(" ")}<br/>`;
  } else {
    toAdd.setAttribute("id", "no-redact");
    toAdd.innerHTML = `${userInput}<br/>`;
  }
  document.getElementById("output").appendChild(toAdd);
};
