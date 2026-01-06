import pytest 
from playwright.sync_api import sync_playwright#imports synchronous api,it is the entry point for using Playwright in a synchronous manner.
@pytest.fixture(scope="function")# a fixture is reusable setup/teardown code for tests. Here, the fixture is named "page" and has a function scope, meaning it will be set up and torn down for each test function that uses it.
def page():
    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        context = browser.new_context()
        page = context.new_page()
        yield page
        context.close()
        browser.close()