function hide(text, position) {
  let count = 0;
  let hasChanged = false;

  console.log(`input: ${text}`);
  for (let check of TO_REDACT) {
    console.log(`checking: ${check} and count: ${count}`);
    let checkRegEx = new RegExp(`^${check}$`, "i");
    if (checkRegEx.test(text)) {
      userInputArray[position] = REDACTED[count];
      hasChanged = true;
    }
    count++;
  }
  // console.log(`HAS ANYTHING CHANGED? ${hasChanged}`);
  return hasChanged;
}
