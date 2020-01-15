var number;

function validateNumber() {
  number = document.getElementById("input-number").value;
  if (isNaN(number)) {
    alert("Please enter a numeric value!");
    number = null;
  }
}

function isPrimeNumber() {
  validateNumber();
  if (!number) {
    // Checking if previous failed or not
    return;
  }
  let isPrime = true;

  // Checks for prime status
  for (let i = 2; i < number; i++) {
    if (number % i == 0) {
      isPrime = false;
    }
  }

  // Exceptions
  if (number == 2 || number == 1) {
    isPrime = false;
  }

  // Prints alerts
  if (isPrime) {
    let reverse = reverseNumber();
    alert(`This is a Prime and the Reverse is: ${reverse}`);
  } else {
    alert("This is not a Prime!");
  }
}

function reverseNumber() {
  let temp = number.toString();
  let reversed = [];
  for (let i = 0; i < temp.length; i++) {
    reversed.unshift(temp.charAt(i));
  }
  return reversed.join("");
}
