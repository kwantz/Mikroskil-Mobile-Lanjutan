const { sequelize, Sequelize } = require('../models')

module.exports = {
  async findAll (profileId) {
    const data = await sequelize.query(`
      SELECT *
      FROM favorite
      WHERE profile_id = :profile_id`, {
      type: Sequelize.QueryTypes.SELECT,
      replacements: {
        profile_id: profileId
      }
    })

    return data
  },

  async findOneByUserAndItem (user, item) {
    const data = await sequelize.query(`
      SELECT favorite.*
      FROM favorite
      WHERE favorite.profile_id = :user
      AND favorite.item_id = :item`, {
      type: Sequelize.QueryTypes.SELECT,
      replacements: {
        user: user,
        item: item
      }
    })

    return data[0]
  }
}
