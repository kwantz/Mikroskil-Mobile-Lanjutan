const models           = require('../../../models')
const { errorHandler } = require('../../global')

module.exports = async (req, res, next) => {
  const removeFavorite = await models.favorite.destroy({
    where: {
      item_id: req.body.item,
      profile_id: req.profile
    }
  })

  if (removeFavorite) {
    return res.json({
      code: 200,
      message: 'barang berhasil dibuang dari favorite'
    })
  } else {
    const error = errorHandler(404, 'barang tidak ditemukan di dalam favorite')
    return next(error)
  }
}
