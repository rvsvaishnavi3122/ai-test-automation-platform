const express = require("express");
const router = express.Router();

const { generateTest } = require("../services/testGenerator");

router.post("/", async (req, res) => {
  const { steps, framework } = req.body;

  if (!steps || !framework) {
    return res.status(400).json({
      error: "steps and framework are required"
    });
  }

  try {
    const code = await generateTest(steps, framework);
    res.json({
      framework,
      generatedCode: code
    });
  } catch (err) {
    res.status(500).json({
      error: err.message
    });
  }
});

module.exports = router;
