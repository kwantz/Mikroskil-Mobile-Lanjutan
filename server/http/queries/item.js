const { sequelize, Sequelize } = require('../models')
const moment = require('moment')

module.exports = {
  async findOneById (id) {
    const data = await sequelize.query(`SELECT * FROM item WHERE id = :id`, {
      type: Sequelize.QueryTypes.SELECT,
      replacements: {
        id: id
      }
    })

    return data[0]
  },

  async findByNewItems () {
    const data = await sequelize.query(`
      SELECT *
      FROM item
      WHERE created_at > :date
    `, {
      type: Sequelize.QueryTypes.SELECT,
      replacements: {
        date: moment().subtract(7, 'days').toDate()
      }
    })

    return data
  },

  async findByHotDealItems () {
    const average = `SELECT AVG(seen) AS seen FROM item`
    const data = await sequelize.query(`
      SELECT *
      FROM item
      WHERE seen >= (${average})
    `, {
      type: Sequelize.QueryTypes.SELECT
    })

    return data
  },

  async findByRecommendedItems () {
    const data = await sequelize.query(`SELECT * FROM item`, {
      type: Sequelize.QueryTypes.SELECT
    })

    return data
  },

  async findBySearchItems (searchReq, categoryReq) {
    const data = await sequelize.query(`
      SELECT *
      FROM item
      WHERE name LIKE :name
      AND category_id LIKE :category
    `, {
      type: Sequelize.QueryTypes.SELECT,
      replacements: {
        name: '%' + searchReq + '%',
        category: '%' + categoryReq + '%'
      }
    })

    return data
  },

  async findByFavorite (profile) {
    const data = await sequelize.query(`
      SELECT item.*
      FROM item
      INNER JOIN favorite ON item.id = favorite.item_id
      WHERE favorite.profile_id = :profile
    `, {
      type: Sequelize.QueryTypes.SELECT,
      replacements: {
        profile: profile
      }
    })

    return data
  },

  async findByTransaction (transaction, profile) {
    const data = await sequelize.query(`
      SELECT item.*
      FROM item
      INNER JOIN cart ON item.id = cart.item_id
      INNER JOIN order ON cart.id = order.cart_id
      INNER JOIN transaction on transaction.id = order.transaction_id
      WHERE transaction.id = :transaction
    `, {
      type: Sequelize.QueryTypes.SELECT,
      replacements: {
        transaction: transaction
      }
    })

    return data
  }
}
