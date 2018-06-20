const itemRepo         = require('../../../queries/item')
const categoryRepo     = require('../../../queries/category')
const { errorHandler } = require('../../global')

module.exports = async (req, res, next) => {
  let item = await itemRepo.findOneById(req.params.id)

  if (!item) {
    const error = errorHandler(404, 'tidak ada barang')
    return next(error)
  }

  item.category = await categoryRepo.findOneById(item.category_id)
  item.category_id = undefined

  return res.json({
    code: 200,
    message: 'barang tersedia',
    item: item
  })
}
