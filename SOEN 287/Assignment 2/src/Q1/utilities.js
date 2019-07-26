// var number;

function validateNumber() {
  let number = document.getElementById("input-number").value;
  if (isNaN(number)) {
    alert("Please enter a numeric value!");
    number = null;
  }
  return number;
}

function isPrimeNumber() {
  let number = validateNumber();
  if (!number) {
    //Checking if previous failed or not
    return;
  }
  let isPrime = true;
  for (let i = 2; i < number; i++) {
    if (number % i == 0) {
      isPrime = false;
    }
  }
  if (isPrime) {
    let reverse = reverseNumber(number);
    alert(`This is a Prime and the Reverse is: ${reverse}`);
  } else {
    alert("This is not a Prime!");
  }
}

function reverseNumber(number) {
  let temp = number.toString();
  let reversed = [];
  for (let i = 0; i < temp.length; i++) {
    reversed.unshift(temp.charAt(i));
  }
  return reversed.join("");
}
