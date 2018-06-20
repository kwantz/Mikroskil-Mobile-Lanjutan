const fs        = require('fs')
const path      = require('path')
const config    = require('../config')
const Sequelize = require('sequelize')

const sequelize = new Sequelize(
  config.database.name,
  config.database.user,
  config.database.pass,
  config.database.options
)

const models = {
  sequelize: sequelize,
  Sequelize: Sequelize
}

fs.readdirSync(__dirname)
  .filter((file) => {
    if (file !== 'index.js') return file
  })
  .forEach((file) => {
    const model = sequelize.import(path.join(__dirname, file))
    models[model.name] = model
  })

module.exports = models
