const cors       = require('cors')
const morgan     = require('morgan')
const express    = require('express')
const bodyParser = require('body-parser')
const route      = require('./route')
const config     = require('./config')
const models     = require('./models')
const app        = express()

app.use(cors())
app.use(morgan('combine'))
app.use(bodyParser.json())

route(app)

models.sequelize
  .sync()
  .then(() => {
    app.listen(config.port)
    console.log('')
    console.log('+=======================================================+')
    console.log('| Status: Server is running on http://localhost:' + config.port + '... |')
    console.log('+=======================================================+')
    console.log('')
  })
