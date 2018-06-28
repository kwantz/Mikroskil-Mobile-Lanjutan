const { sequelize, Sequelize } = require('../models')

module.exports = {
  async findAll (profile) {
    const data = await sequelize.query(`
      SELECT address.*
      FROM address
      WHERE address.profile_id = :profile
    `, {
      type: Sequelize.QueryTypes.SELECT,
      replacements: {
        profile: profile
      }
    })

    return data
  },

  async findOneById (profile, address) {
    const data = await sequelize.query(`
      SELECT address.*
      FROM address
      WHERE address.profile_id = :profile
      AND address.id = :address
    `, {
      type: Sequelize.QueryTypes.SELECT,
      replacements: {
        profile: profile,
        address: address
      }
    })

    return data[0]
  },
}
