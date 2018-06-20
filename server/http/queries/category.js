const { sequelize, Sequelize } = require('../models')

module.exports = {
  async findAll () {
    const data = await sequelize.query(`SELECT * FROM category`, {
      type: Sequelize.QueryTypes.SELECT
    })

    return data
  },

  async findOneById (id) {
    const data = await sequelize.query(`SELECT * FROM category WHERE id = :id`, {
      type: Sequelize.QueryTypes.SELECT,
      replacements: {
        id: id
      }
    })

    return data[0]
  }
}
