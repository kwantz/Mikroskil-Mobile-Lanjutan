const models           = require('../../../models')
const { errorHandler } = require('../../global')

module.exports = async (req, res, next) => {
  const updateCart = await models.cart.update({
    quantity: req.body.qty,
    note: req.body.note
  }, {
    where: {
      item_id: req.body.item,
      profile_id: req.profile
    }
  })

  if (updateCart) {
    return res.json({
      code: 200,
      message: 'barang berhasil diedit ke dalam keranjang'
    })
  } else {
    const error = errorHandler(404, 'barang tidak ditemukan di dalam keranjang')
    return next(error)
  }
}
