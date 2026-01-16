const express = require("express");//importing express
const router = express.Router();//creating router

const { generateTest } = require("../services/testGenerator");//importing test generation service

router.post("/", (req, res) => {//handling POST requests
  const { steps, framework } = req.body;

  if (!steps || !framework) {//validating request body
    return res.status(400).json({
      error: "steps and framework are required"//
    });
  }

  const testCode = generateTest(steps, framework);//generating test code

  res.json({
    framework,
    generatedCode: testCode
  });
});

module.exports = router;//exporting router
