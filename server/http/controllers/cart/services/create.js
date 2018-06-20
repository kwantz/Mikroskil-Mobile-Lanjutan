const models           = require('../../../models')
const itemRepo         = require('../../../queries/item')
const { errorHandler } = require('../../global')

module.exports = async (req, res, next) => {
  const item = await itemRepo.findOneById(req.body.item)
  if (!item) {
    const error = errorHandler(404, 'tidak ada barang')
    return next(error)
  }

  const newCart = await models.cart.create({
    profile_id: req.profile,
    item_id: req.body.item,
    quantity: req.body.qty,
    note: req.body.note
  })

  return res.json({
    code: 200,
    message: 'barang berhasil ditambah ke dalam keranjang',
    cart: newCart
  })
}
