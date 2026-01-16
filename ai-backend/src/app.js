//creation of Express app
const express = require("express");//importing cors
const cors = require("cors");//importing routes
const generateTestRoute = require("./routes/generateTest");//creating express app
const app = express();//using middleware
app.use(cors());//enabling CORS for all routes
app.use(express.json());//  parsing JSON request bodies

//routes
app.use("/generate-test", generateTestRoute);
const PORT = 3001;//defining the port
app.listen(PORT, () => {
  console.log(`AI Backend running on port ${PORT}`);
});