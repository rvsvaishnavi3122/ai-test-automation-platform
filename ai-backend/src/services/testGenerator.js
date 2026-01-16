function generateTest(steps, framework) {
  if (framework === "selenium") {
    return `
@Test
public void generatedTest() {
    DriverFactory.getDriver().get("https://example.com");
    // Steps: ${steps}
}
`;
  }

  if (framework === "playwright") {
    return `
def test_generated(page):
    page.goto("https://example.com")
    # Steps: ${steps}
`;
  }

  return "Unsupported framework";
}

module.exports = { generateTest };
