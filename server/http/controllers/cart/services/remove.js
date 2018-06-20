const models           = require('../../../models')
const { errorHandler } = require('../../global')

module.exports = async (req, res, next) => {
  const removeCart = await models.cart.destroy({
    where: {
      item_id: req.params.item,
      profile_id: req.profile
    }
  })

  if (removeCart) {
    return res.json({
      code: 200,
      message: 'barang berhasil dibuang dari keranjang'
    })
  } else {
    const error = errorHandler(404, 'barang tidak ditemukan di dalam keranjang')
    return next(error)
  }
}
