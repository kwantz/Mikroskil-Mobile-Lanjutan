const { sequelize, Sequelize } = require('../models')

module.exports = {
  async findAll (profileId) {
    const data = await sequelize.query(`
      SELECT cart.*
      FROM cart
      LEFT JOIN orders ON cart.id = orders.cart_id
      WHERE orders.id IS NULL
      AND cart.profile_id = :profile_id
    `, {
      type: Sequelize.QueryTypes.SELECT,
      replacements: {
        profile_id: profileId
      }
    })

    return data
  },

  async findOneByUserAndItem (user, item) {
    const data = await sequelize.query(`
      SELECT cart.*
      FROM cart
      WHERE cart.profile_id = :user
      AND cart.item_id = :item`, {
      type: Sequelize.QueryTypes.SELECT,
      replacements: {
        user: user,
        item: item
      }
    })

    return data[0]
  }
}
