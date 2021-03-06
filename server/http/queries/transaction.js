const { sequelize, Sequelize } = require('../models')

module.exports = {
  async findAll (profile) {
    const data = await sequelize.query(`
      SELECT transaction.*
      FROM transaction
      WHERE transaction.profile_id = :profile
    `, {
      type: Sequelize.QueryTypes.SELECT,
      replacements: {
        profile: profile
      }
    })

    return data
  },

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
