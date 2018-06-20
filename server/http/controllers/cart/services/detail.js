const cartRepo = require('../../../queries/cart')

module.exports = async (req, res, next) => {
  let cart = await cartRepo.findOneByUserAndItem(req.profile, req.params.item)

  if (cart) {
    return res.json({
      code: 200,
      message: 'barang tersedia'
    })
  }

  return res.json({
    code: 404,
    message: 'barang tidak tersedia'
  })
}
