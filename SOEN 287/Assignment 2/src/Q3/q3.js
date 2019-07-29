var inputString = "";
var inputChars = [];
var charCounts = [];
var graphExists = false;

const layout = {
  title: "Character Presence Histogram",
  font: {
    family: "Comic Sans MS, serif"
  },
  showlegend: false,
  bargap: 0.05
};

const getInfo = () => {
  console.log("boutton");
  inputString = document.getElementById("inString").value;
  console.log(inputString);
  inputChars = document.getElementById("inChars").value.split("");
  console.log(inputChars);
};

const countChars = () => {
  for (let i in inputChars) {
    let currentChar = inputChars[i];
    let currentCount = (inputString.match(new RegExp(currentChar, "g")) || [])
      .length;
    charCounts.push(currentCount);
  }
};

const generateGraph = () => {
  if (graphExists) {
    Plotly.deleteTraces("histogram", 0);
    graphExists = false;
  }
  getInfo();
  countChars();
  let data = [
    {
      x: inputChars,
      y: charCounts,
      type: "bar",
      marker: {
        color: "rgb(255,48,48)"
      }
    }
  ];

  Plotly.newPlot("histogram", data, layout);
  graphExists = true;
};
