let err = ["", "", "", "", ""];
let enable = false;

const validateName = _ => {
  let name = document.querySelector("#fullName").value;
  if (name.length === 0) {
    // Name is not valid
    enable = false;

    console.log("INVALD NAME");

    err[0] = "Invalid Name";
    document.querySelector("#fullName").style.background = "rgb(255, 102, 102)";
  } else {
    enable = true;
    console.log("GOOD NAMES");

    err[0] = "";
    document.querySelector("#fullName").style.background = "white";
  }
  updateError();
};

const validateEmail = _ => {
  let email = document.querySelector("#email").value;
  if (email.match(/^[a-zA-Z0-9\.\-\_]+@(hotmail|gmail)\.[a-z]{2,}$/) === null) {
    // Email not valid
    enable = false;

    err[1] = "Invalid Email, Only Hotmail and Gmail allowed";
    document.querySelector("#email").style.background = "rgb(255, 102, 102)";
  } else {
    enable = true;
    console.log("GOOD NAMES");

    err[1] = "";
    document.querySelector("#email").style.background = "white";
  }
  updateError();
};

const validateUser = _ => {
  let username = document.querySelector("#username").value;
  if (username.match(/^\$[a-zA-Z\_]+[a-zA-Z0-9\_]*$/) === null) {
    // Username not valid
    enable = false;
    console.log("BAD USER");

    err[2] = "Invalid Username, should be PHP variable complient";
    document.querySelector("#username").style.background = "rgb(255, 102, 102)";
  } else {
    enable = true;
    console.log("GOOD USERNAMES");

    err[2] = "";
    document.querySelector("#username").style.background = "white";
  }
  updateError();
};

const validatePass = _ => {
  let pass1 = document.querySelector("#pass1").value;

  if (
    /[a-z]/.test(pass1) &&
    /[A-Z]/.test(pass1) &&
    /[0-9]/.test(pass1) &&
    /[^\w]/.test(pass1) &&
    pass1.length >= 8
  ) {
    // Good Password
    enable = true;
    console.log("GOOD PASS");
    err[3] = "";
    document.querySelector("#pass1").style.background = "white";
  } else {
    enable = false;
    console.log("BAD PASS");
    err[3] =
      "Bad Password, Should contain 1 lowercase, 1 uppercase, 1 digit, 1 special character and minimum 8 characters";
    document.querySelector("#pass1").style.background = "rgb(255, 102, 102)";
  }
  updateError();
};

const validatePass2 = _ => {
  let pass1 = document.querySelector("#pass1").value;
  let pass2 = document.querySelector("#pass2").value;

  if (pass1 === pass2) {
    // Password matches
    enable = true;
    console.log("GOOD MATCH");
    err[4] = "";
    document.querySelector("#pass2").style.background = "white";
  } else {
    enable = false;
    console.log("BAD MATCH");
    err[4] = "Both Passwords do not match";
    document.querySelector("#pass2").style.background = "rgb(255, 102, 102)";
  }
  updateError();
};

const updateButton = allow => {
  document.getElementById("submit-btn").disabled = !allow;
};

const updateError = _ => {
  document.querySelector(".validation-error").innerHTML = "";

  updateButton(enable);
  for (let x of err) {
    let toAdd = document.createElement("p");
    toAdd.innerHTML = x;

    document.querySelector(".validation-error").appendChild(toAdd);
  }
};
