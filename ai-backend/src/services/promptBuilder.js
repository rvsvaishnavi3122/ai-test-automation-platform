function buildPrompt(steps, framework) {
  if (framework === "selenium") {
    return `
You are a senior SDET.
Generate ONLY Selenium Java TestNG test code.

Rules:
- Use TestNG @Test
- Use DriverFactory.getDriver()
- No explanations
- No markdown
- No comments outside code
- Follow Page Object style
- Use clean Java syntax

Steps:
${steps}
`;
  }

  if (framework === "playwright") {
    return `
You are a senior SDET.
Generate ONLY Playwright Python test code.

Rules:
- Use pytest
- Use Playwright sync API
- No explanations
- No markdown
- No extra text

Steps:
${steps}
`;
  }

  return "";
}

module.exports = { buildPrompt };
