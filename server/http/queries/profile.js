const { sequelize, Sequelize } = require('../models')

module.exports = {
  async findOneByUserId (user) {
    const data = await sequelize.query(`
      SELECT profile.*
      FROM profile
      WHERE profile.user_id = :user
    `, {
      type: Sequelize.QueryTypes.SELECT,
      replacements: {
        user: user
      }
    })

    return data[0]
  }
}
