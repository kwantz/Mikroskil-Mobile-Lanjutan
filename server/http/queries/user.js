const { sequelize, Sequelize } = require('../models')

module.exports = {
  byUsername (usernameReq) {
    return { where: { username: usernameReq }, raw: true }
  },

  async findOneById (user) {
    const data = await sequelize.query(`
      SELECT user.*
      FROM user
      WHERE user.id = :user
    `, {
      type: Sequelize.QueryTypes.SELECT,
      replacements: {
        user: user
      }
    })

    return data[0]
  }
}
