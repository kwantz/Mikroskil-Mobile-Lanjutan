const itemRepo         = require('../../../queries/item')
const categoryRepo     = require('../../../queries/category')
const favoriteRepo     = require('../../../queries/favorite')
const { errorHandler } = require('../../global')

module.exports = async (req, res, next) => {
  let favorites = await favoriteRepo.findAll(req.profile)

  if (favorites.length === 0) {
    const error = errorHandler(404, 'tidak ada favorite')
    return next(error)
  }

  let items = []
  for (let i = 0, maxLen = favorites.length; i < maxLen; i++) {
    const item = await itemRepo.findOneById(favorites[i].item_id)
    item.category = await categoryRepo.findOneById(item.category_id)
    item.category_id = undefined
    items.push(item)
  }

  return res.json({
    code: 200,
    message: 'favorite tersedia',
    items: items
  })
}
