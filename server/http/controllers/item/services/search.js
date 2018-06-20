const itemRepo         = require('../../../queries/item')
const categoryRepo     = require('../../../queries/category')
const { errorHandler } = require('../../global')

const suffleArrayWithLimit = (baseArray, limit) => {
  let minLimit = Math.min(limit, baseArray.length)
  let currentIndex = baseArray.length
  let temporaryValue
  let randomIndex
  let newArray = []

  while (currentIndex !== 0) {
    randomIndex = Math.floor(Math.random() * currentIndex)
    currentIndex -= 1

    temporaryValue = baseArray[currentIndex]
    baseArray[currentIndex] = baseArray[randomIndex]
    baseArray[randomIndex] = temporaryValue
  }

  for (let i = 0; i < minLimit; i++) {
    newArray.push(baseArray[i])
  }

  return newArray
}

module.exports = async (req, res, next) => {
  let items

  if (req.query.title === 'new-items') items = await itemRepo.findByNewItems()
  else if (req.query.title === 'hot-deal-items') items = await itemRepo.findByHotDealItems()
  else if (req.query.title === 'recommended-items') items = await itemRepo.findByRecommendedItems()
  else {
    if (typeof req.query.search === 'undefined') req.query.search = ''
    if (typeof req.query.category === 'undefined') req.query.category = ''

    items = await itemRepo.findBySearchItems(req.query.search, req.query.category)
  }

  if (items.length === 0) {
    const error = errorHandler(404, 'tidak ada barang')
    return next(error)
  }

  if (req.query.title) items = suffleArrayWithLimit(items, 10)

  for (let i = 0, maxLen = items.length; i < maxLen; i++) {
    items[i].category = await categoryRepo.findOneById(items[i].category_id)
    items[i].category_id = undefined
  }

  return res.json({
    code: 200,
    message: 'barang tersedia',
    items: items
  })
}
