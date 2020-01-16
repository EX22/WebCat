

function validateRegistrationForm() {
  var x = document.forms["registrationForm"]["inputPassword"].value;
  var y = document.forms["registrationForm"]["confirmPassword"].value;
  if (x != y) {
   document.getElementById("errorMessageText").innerHTML="Passwords are not equal";
   document.getElementById("errorMessage").classList.remove("invisible");
   document.getElementById("errorMessage").classList.add("visible");
    return false;
  }
}

function validateProfileSettingsForm() {
  var x = document.forms["profileSettingsForm"]["newPassword3"].value;
  var y = document.forms["profileSettingsForm"]["confirmNewPassword3"].value;
  if (x != y) {

  document.getElementById("errorMessageText").innerHTML="Passwords are not equal";
  document.getElementById("errorMessage").classList.remove("invisible");
  document.getElementById("errorMessage").classList.add("visible");

    return false;
  }
}