import express from "express";
import { generateSeleniumTest } from "../services/openaiService.js";

const router = express.Router();

router.post("/", async (req, res) => {
  try {
    const { steps } = req.body;
    if (!steps) {
      return res.status(400).json({ error: "steps are required" });
    }
    const code = await generateSeleniumTest(steps);
    res.json({ code });
  } catch (e) {
    res.status(500).json({ error: e.message });
  }
});

export default router;
