import dotenv from "dotenv";
dotenv.config();

import express from "express";
import generateTest from "./routes/generateTest.js";

const app = express();
app.use(express.json());

app.use("/generate-test", generateTest);

app.listen(3001, () =>
  console.log("AI backend running on http://localhost:3001")
);
