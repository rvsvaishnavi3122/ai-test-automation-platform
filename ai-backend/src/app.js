require("dotenv").config();

console.log("KEY CHECK:", process.env.OPENAI_API_KEY?.startsWith("sk-"));

const express = require("express");
const cors = require("cors");

const generateTestRoute = require("./routes/generateTest");

const app = express();
app.use(cors());
app.use(express.json());

app.use("/generate-test", generateTestRoute);

const PORT = 3001;
app.listen(PORT, () => {
  console.log(`AI Backend running on port ${PORT}`);
});
console.log(
  "OPENAI_API_KEY loaded:",
  process.env.OPENAI_API_KEY?.startsWith("sk-")
);
console.log("API KEY PREFIX:", process.env.OPENAI_API_KEY?.slice(0, 7));

