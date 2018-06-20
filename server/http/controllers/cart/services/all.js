const itemRepo         = require('../../../queries/item')
const cartRepo         = require('../../../queries/cart')
const { errorHandler } = require('../../global')

module.exports = async (req, res, next) => {
  let cart = await cartRepo.findAll(req.profile)

  if (cart.length === 0) {
    const error = errorHandler(404, 'tidak ada barang di keranjang')
    return next(error)
  }

  for (let i = 0, maxLen = cart.length; i < maxLen; i++) {
    const item = await itemRepo.findOneById(cart[i].item_id)

    cart[i].item = item
    cart[i].item_id = undefined
  }

  return res.json({
    code: 200,
    message: 'favorite tersedia',
    carts: cart
  })
}
