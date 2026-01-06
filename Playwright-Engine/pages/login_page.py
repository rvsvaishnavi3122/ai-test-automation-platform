class LoginPage:
    def __init__(self,page):
        self.page=page
    def open(self):
        self.page.goto("https://the-internet.herokuapp.com/login")
    def login(self,user, pwd):
        self.page.fill("#username",user)
        self.page.fill("#password", pwd)
        self.page.click("button[type='submit']")