const { sequelize, Sequelize } = require('../models')

module.exports = {
  async findOneById (transaction) {
    const data = await sequelize.query(`SELECT * FROM transaction WHERE id = :id`, {
      type: Sequelize.QueryTypes.SELECT,
      replacements: {
        id: transaction
      }
    })

    return data[0]
  }
}
