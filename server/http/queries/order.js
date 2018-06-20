const { sequelize, Sequelize } = require('../models')

module.exports = {
  async findTotalAmount (transaction) {
    const data = await sequelize.query(`
      SELECT SUM(item.price * cart.quantity) as amount
      FROM order
      INNER JOIN cart ON cart.id = order.cart_id
      INNER JOIN item ON item.id = cart.item_id
      WHERE order.transaction_id = :transaction
      GROUP BY *
    `, {
      type: Sequelize.QueryTypes.SELECT,
      replacements: {
        transaction: transaction
      }
    })

    return data
  }
}
