import OpenAI from "openai";

export async function generateSeleniumTest(steps) {
  // OpenAI client is created ONLY when function is called
  const client = new OpenAI({
    apiKey: process.env.OPENAI_API_KEY
  });

  const prompt = `
Convert the following steps into a Selenium Java TestNG test.
Rules:
- Use Page Object pattern
- Use LocatorUtils
- Keep code clean and runnable

Steps:
${steps}
`;

  const response = await client.chat.completions.create({
    model: "gpt-4o-mini",
    messages: [{ role: "user", content: prompt }]
  });

  return response.choices[0].message.content;
}
