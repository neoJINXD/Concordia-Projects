var inputString;
var inputChars;

const getInfo = () => {
  console.log("boutton");
  inputString = document.getElementById("inString").value;
  console.log(inputString);
  inputChars = document.getElementById("inChars").value.split("");
  console.log(inputChars);
};

var data = [
  {
    x: ["a", "b", "c"],
    y: [13, 5, 35],
    type: "bar",
    marker: {
      color: "rgb(255,0,0)"
    },
    width: 0.05
  }
];

var layout = {
  title: "TITLE",
  font: {
    family: "Comic Sans MS, serif"
  },
  showlegend: false,
  bargap: 0.05
};

Plotly.newPlot("histogram", data, layout, { showSendToCloud: true });
