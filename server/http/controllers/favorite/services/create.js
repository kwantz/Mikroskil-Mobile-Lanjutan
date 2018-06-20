const models           = require('../../../models')
const itemRepo         = require('../../../queries/item')
const { errorHandler } = require('../../global')

module.exports = async (req, res, next) => {
  const item = await itemRepo.findOneById(req.body.item)
  if (!item) {
    const error = errorHandler(404, 'tidak ada barang')
    return next(error)
  }

  const newFavorite = await models.favorite.create({
    item_id: req.body.item,
    profile_id: req.profile
  })

  return res.json({
    code: 200,
    message: 'favorite berhasil ditambah',
    favorite: newFavorite
  })
}
