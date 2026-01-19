const OpenAI = require("openai");
const { buildPrompt } = require("./promptBuilder");

const client = new OpenAI({
  apiKey: process.env.OPENAI_API_KEY
});

async function generateTest(steps, framework) {
  const prompt = buildPrompt(steps, framework);

  const response = await client.chat.completions.create({
    model: "gpt-4o-mini",
    messages: [{ role: "user", content: prompt }]
  });

  return response.choices[0].message.content;
}

module.exports = { generateTest };
